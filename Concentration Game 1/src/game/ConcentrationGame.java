package game;

import common.ConcentrationException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static common.ConcentrationProtocol.*;

/**
 * A complete, non-networked version of the concentration game.  The user
 * enters coordinates via standard input.  The game shows the progress of
 * the board, as well as the eventual messages that will be used when the
 * game becomes networked.
 *
 * $ java Concentration DIM cheat
 *      - DIM is the square dimension of the board (2,4 or 6)
 *      - cheat is a flag that displays the fully revealed board initially
 *
 * This version of the game does not deal with errors like the user
 * entering invalid coordinates so that the error message can be shown.
 *
 * @author RIT CS
 */
public class ConcentrationGame {
    /* simulated protocol message sent from client to server */
    private final static String CLIENT_TO_SERVER = "*** Client -> Server: ";
    /* simulated protocol message sent from server to client */
    private final static String SERVER_TO_CLIENT = "*** Server -> Client: ";

    /**
     * The main program runs the entire game.
     *
     * @param args command line arguments (board dimension and cheat flag)
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("java ConcentrationGame DIM cheat");
        } else {
            try {
                // create the board
                ConcentrationBoard board = new ConcentrationBoard(Integer.parseInt(args[0]), Boolean.parseBoolean(args[1]));
                // show the networked server to client message
                System.out.println(SERVER_TO_CLIENT + String.format(BOARD_DIM_MSG, board.getDIM()));
                Scanner in = new Scanner(System.in);
                // play until the game is over
                while (!board.gameOver()) {
                    // display the current board
                    System.out.println(board);
                    // prompt the user to enter the coordinates
                    System.out.print("row: ");
                    int row = in.nextInt();
                    System.out.print("col: ");
                    int col = in.nextInt();

                    // get the card from the board
                    System.out.println(CLIENT_TO_SERVER + String.format(REVEAL_MSG, row, col));
                    ConcentrationCard card = board.getCard(row, col);

                    // reveal the card and check if a match was made or not
                    System.out.println(SERVER_TO_CLIENT + String.format(CARD_MSG, row, col, card.getLetter()));
                    ConcentrationBoard.CardMatch match = board.reveal(row, col);

                    // if the match is not ready this was the first of two cards revealed
                    if (match.isReady()) {
                        if (match.isMatch()) {
                            // if there is a match show the server to client match message
                            System.out.println(SERVER_TO_CLIENT + String.format(MATCH_MSG,
                                    match.getCard1().getRow(), match.getCard1().getCol(),
                                    match.getCard2().getRow(), match.getCard2().getCol()));
                        } else {
                            // if there is not a match show the server to client mismatch message
                            System.out.println(SERVER_TO_CLIENT + String.format(MISMATCH_MSG,
                                    match.getCard1().getRow(), match.getCard1().getCol(),
                                    match.getCard2().getRow(), match.getCard2().getCol()));
                        }
                    }
                }
                System.out.println(SERVER_TO_CLIENT + GAME_OVER_MSG);
            } catch (ConcentrationException | InputMismatchException e) {
                System.out.println(SERVER_TO_CLIENT + String.format(ERROR_MSG, e.getMessage()));
                e.printStackTrace();
            }
        }
    }
}
