
package miniPoker;

import playingCards.Card;
import playingCards.Rank;
import playingCards.Suit;

import java.util.ArrayList;

/**
 * A computer playing a 2-Card poker game.
 *
 * Author: Dhruv Rajpurohit, dar1972@rit.edu
 */

public class Computer {
    private Rank rank;
    private Suit suit;



    /**
     * This is constructor to initialize the computer to play a 2 card poker game
     */
    public Computer(){

    }

    ArrayList<Card> compHand = new ArrayList<>(2);  // array list of cards in computers hand

    /**
     * The function checks if the computer should continue playing or fold.
     *
     * @return true or false depending on the condition
     */
    public boolean stand(){
        int val = value();
        if (val > 100){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * The function adds a card to the computers hand
     *
     * @param c the card being added
     */
    public void addCard(Card c){
        compHand.add(c);
    }

    /**
     * The function resets the computers hand
     */
    public void newHand(){
        compHand.clear();

    }

    /**
     * the function prints the hand the computer has currently
     */
    public void printHand(){
        int height = 0;
        System.out.println("#################");
        while (height != 5){
            height++;
            if (height == 3){
                System.out.println("#  "+ compHand.get(0) +"   #");
                System.out.println("#  "+ compHand.get(1) +"   #");
            }
            System.out.println("#                    #");
        }
        System.out.println("");
        System.out.println("score: " + value());
    }

    /**
     * the function computes the value of the hand
     *
     * @return the value of the hand
     */
    public int value(){
        if (compHand.get(0).getSuit().equals(compHand.get(1).getSuit())){
            return 100 + compHand.get(0).getRank().getValue() * 14 + compHand.get(1).getRank().getValue();
        }
        else if (compHand.get(0).getRank().equals(compHand.get(1).getRank())){
            return 1000 + compHand.get(0).getRank().getValue() * 14 + compHand.get(1).getRank().getValue();
        }
        else {
            return compHand.get(0).getRank().getValue() * 14 + compHand.get(1).getRank().getValue();
        }
    }
}
