package miniPoker;


import playingCards.Card;
import playingCards.Rank;
import playingCards.Suit;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The user playing a 2-Card poker game.
 *
 * Author: Dhruv Rajpurohit, dar1972@rit.edu
 */

public class Human extends Object{
    private Scanner in;
    private Rank rank;
    private Suit suit;


    /**
     * this is a constructor to initialize the human to play 2 - card poker game
     * @param in the scanner connected to the console
     */
    public Human(Scanner in) {
        this.in = in;
    }

    ArrayList<Card> humanHand = new ArrayList<>(2);
    /**
     * The function asks if the user wants to stand or fold.
     *
     * @return true or false depending on the input from the user
     */
    public Boolean stand(){
        Scanner fold = new Scanner(System.in);
        System.out.print("Do you want to Stand (y/n): ");
        String answer = fold.next();
        if (answer.equals("y")){
            return true;
        }
        return false; // if answer "n" then human folds
    }

    /**
     * The function adds a card to users hand
     *
     * @param c the card to be added
     */
    public void addCard(Card c) {
        humanHand.add(c);
    }

    /**
     * The function resets the users hand for the next round
     */
    public void newHand(){
        humanHand.clear();
    }

    /**
     * The function prints the hand the user currently has
     */
    public void printHand(){
        int height = 0;
        System.out.println("*********************");
        while (height != 5){
            height++;
            if (height == 3){
                System.out.println("*  "+ humanHand.get(0) +"   *");
                System.out.println("*  "+ humanHand.get(1) +"   *");
            }
            System.out.println("*                    *");
        }
        System.out.println("");
        System.out.println("score: " + value());
    }

    /**
     * The function computes the value of the hand
     *
     * @return the value of the hand
     */
    public int value(){
        if (humanHand.get(0).getSuit().equals(humanHand.get(1).getSuit())){
            return 100 + humanHand.get(0).getRank().getValue() * 14 + humanHand.get(1).getRank().getValue();
        }
        else if (humanHand.get(0).getRank().equals(humanHand.get(1).getRank())){
            return 1000 + humanHand.get(0).getRank().getValue() * 14 + humanHand.get(1).getRank().getValue();
        }
        else {
            return humanHand.get(0).getRank().getValue() * 14 + humanHand.get(1).getRank().getValue();
        }
    }

    /**
     * The function compares the user's hand to computer's hand
     *
     * @param computer The computer
     *
     * @return an integer depending on the comparision
     */
    public int compareTo(Computer computer)
    {
        int h = value();
        int c = computer.value();
        for (int x=0; x<6; x++)
        {
            if (h > c) {
                return 1;
            }
            else if (c > h) {
                return -1;
            }
        }
        return 0; //if hands are equal
    }

}
