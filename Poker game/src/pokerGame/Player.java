package pokerGame;

import playingCards.Card;
import playingCards.Rank;
import playingCards.Suit;

import java.util.ArrayList;

/**
 * An abstract Player for 2-card poker.
 *
 * @author RIT CS
 * @author Dhruv Rajpurohit
 */

public abstract class Player implements Comparable< Player > {
    private Rank rank;
    private String name;
    private ArrayList<Card> hand;
    private int wins;

    /**
     * Initialize a player for 2-card poker.
     */
    public Player() {
        hand = new ArrayList<>(2);
        wins = 0;
    }

    /**
     * Ask the player if they want to stand..
     *
     * @return a boolean value specifying if the human wants to stand
     */
    public abstract boolean stand();

    /**
     * Retrun the number of wins for this player.
     *
     * @return the number of wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Increment the number of wins for this player.
     */
    public void incrWins() {
        wins += 1;

    }

    /**
     * Add a card to the player's hand.
     *
     * @param c the card to add
     */
    public void addCard( Card c ) {
        hand.add(c);
    }

    /**
     * Print the hand in some "nice" format.
     */
    public void printHand() {
        int height = 0;
        System.out.println("*********************");
        while (height != 5){
            height++;
            if (height == 3){
                System.out.println("*  "+ hand.get(0) +"   *");
                System.out.println("*  "+ hand.get(1) +"   *");
            }
            System.out.println("*                    *");
        }
        System.out.println("");

    }

    /**
     * Clear out all the player's cards.
     */
    public void newHand() {
        hand.clear();
    }

    /**
     * Get string representation of Player.
     * @return the part of the string representation common to all players:
     *         the player number
     */
    char playerType;
    @Override
    public String toString() {
        return playerType + "Player: ";
    }

    /**
     * Compare this player's hand with the specified player's hand for order.
     * Returns <table BORDER="1">
     *     <caption>compareTo standard semantics</caption>
     * <tr><td>negative integer</td><td>player hand &lt; computers hand</td>
     * <tr><td>zero</td><td>player hand == computers hand</td>
     * <tr><td>positive integer</td><td>player hand &gt; computers hand</td>
     * </table>
     *
     * @return a negative integer, zero, or a positive integer as this
     *         player is less than, equal to, or greater than the other
     */
    @Override
    public int compareTo( Player other ) {
        if(this.hand == other.hand)
        {
            return this.rank.compareTo(other.rank);
        }
        else {
            return other.rank.compareTo(this.rank);
        }
    }
}
