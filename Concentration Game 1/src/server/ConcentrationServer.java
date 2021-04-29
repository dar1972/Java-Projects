package server;

import common.ConcentrationException;
import common.ConcentrationProtocol;
import game.ConcentrationBoard;
import game.ConcentrationCard;
import game.ConcentrationGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import static common.ConcentrationProtocol.*;
import static common.ConcentrationProtocol.ERROR_MSG;

/**
 * Author: Dhruv Rajpurohit
 * Description: The Code is the server of the Concentration game
 */

public class ConcentrationServer implements Runnable {
    private ConcentrationGame game;
    private Duplexer duplexer;

    /**
     * The Constructor of server
     * makes an instance of duplexer and creates a new game
     * @param socket server socket
     * @throws IOException
     * @throws ConcentrationException
     */
    public ConcentrationServer (ServerSocket socket) throws IOException, ConcentrationException {
        Socket client = socket.accept();
        duplexer = new Duplexer(client);
        game = new ConcentrationGame();
    }

    /**
     * The function is the server side of the game where the instruction are made and the game
     * is made to do some specific tasks with respect to the input from client
     */
    @Override
    public void run() {
        String request="";
        String response="";
        int turn = 0;
        while (true){
            Duplexer.send(ConcentrationProtocol.BOARD_DIM);
            try {
                request = duplexer.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ConcentrationBoard board = new ConcentrationBoard(BOARD_DIM, Boolean.parseBoolean(args[1]));
                System.out.println("*** Server -> Client: " + String.format(BOARD_DIM_MSG, board.getDIM()));
                Scanner in = new Scanner(System.in);

                while (!board.gameOver()) {

                    System.out.println(board);

                    System.out.print("row: ");
                    int row = in.nextInt();
                    System.out.print("col: ");
                    int col = in.nextInt();
                    ConcentrationCard card = board.getCard(row, col);
                    duplexer.send(String.format(CARD_MSG, row, col, card.getLetter()));
                    ConcentrationBoard.CardMatch match = board.reveal(row, col);
                    if (match.isReady()) {
                        if (match.isMatch()) {
                            duplexer.send(String.format(MATCH_MSG,
                                    match.getCard1().getRow(), match.getCard1().getCol(),
                                    match.getCard2().getRow(), match.getCard2().getCol()));
                        } else {
                            duplexer.send(String.format(MISMATCH_MSG,
                                    match.getCard1().getRow(), match.getCard1().getCol(),
                                    match.getCard2().getRow(), match.getCard2().getCol()));
                        }
                    }
                }
                duplexer.send(GAME_OVER_MSG);
            } catch (ConcentrationException | InputMismatchException e) {
                duplexer.send(String.format(ERROR_MSG, e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    /**
     * The main program of the server
     * @param args
     * @throws IOException
     * @throws ConcentrationException
     */
    public static void main(String[] args) throws IOException, ConcentrationException {
        if (args.length != 2) {
            System.out.println("Usage: java ConcentrationServer port DIM");
            System.exit(1);
        } else {
            int port = Integer.parseInt(args[0]);
            ServerSocket server=new ServerSocket(54005);
            ConcentrationServer GameServer=new ConcentrationServer(server);
            Duplexer.send(ConcentrationProtocol.BOARD_DIM);
            GameServer.run();
        }
    }
}
