package client;

import common.ConcentrationProtocol;
import game.ConcentrationBoard;
import game.ConcentrationGame;
import server.Duplexer;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: Dhruv Rajpurohit
 * Description: The Code is the client of the Concentration game
 */

public class ConcentrationClient implements ConcentrationProtocol {
    private Socket socket;
    private Duplexer server;
    private ConcentrationGame game;

    /**
     * The constructor of the client
     * This makes an instance of duplexer and new game
     * @param socket socket of client
     * @throws IOException
     */
    public ConcentrationClient (Socket socket) throws IOException {
        this.socket = socket;
        server = new Duplexer(socket);
        this.game=new ConcentrationGame();
    }

    /**
     * The function is the client side of the game where the instruction
     * are sent to the server and asked to do specific tasks
     * @throws Exception
     */
    public void run() throws Exception {
        while (true) {
            String message = server.receive();
            String[] move = message.split(" ");
            if (move[0].equals(BOARD_DIM)) {
                System.out.println(BOARD_DIM_MSG);
            }else if (move[0].equals(REVEAL)) {
                System.out.print("col: ");
                Scanner in = new Scanner(System.in);
                String col = in.nextLine();
                System.out.print("row: ");
                String row = in.nextLine();
                server.send(REVEAL_MSG + " " + col + " " + row);
                System.out.println();
            } else if (move[0].equals(CARD)) {
                System.out.println(CARD_MSG);
                new ConcentrationBoard(Integer.parseInt(move[1]));
                System.out.println(game);
            } else if (move[0].equals(MISMATCH)) {
                System.out.println(MISMATCH_MSG);
                break;
            } else if (move[0].equals(MATCH)) {
                System.out.println(MATCH_MSG);
                break;
            } else if (move[0].equals(GAME_OVER)) {
                System.out.println(GAME_OVER_MSG);
                break;
            }
            else if(move[0].equals(ERROR)){
                System.err.println(ERROR_MSG);
                break;
            }
        }

        server.close();
    }

    /**
     * The main function of the client
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java ConcentrationClient host port");
            System.exit(1);
        } else {
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
            Socket socket = new Socket("yes.cs.rit.edu", 54005);
            ConcentrationClient client = new ConcentrationClient(socket);
            client.run();
        }
    }
}
