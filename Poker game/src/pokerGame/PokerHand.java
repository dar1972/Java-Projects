package pokerGame;

import playingCards.Card;
import playingCards.Rank;
import playingCards.Suit;

import java.util.ArrayList;

/**
 * A class to encapsulate a hand of cards for a 2-card poker game
 *
 * @author RIT CS
 * @author Dhruv Rajpurohit
 */
public class PokerHand implements Comparable< PokerHand > {


    private ArrayList<Card> hand;
    private Rank rank;
    private Suit suit;
    private Card card;
    private int value;


    /**
     * Initialize a poker hand.
     * @rit.post The hand has no cards
     */
    public PokerHand() {
        hand = new ArrayList<>(2);
    }

    /**
     * Add a card to the hand.
     * This method must be called exactly twice because a hand size is 2.
     *
     * @param card the card to add to hand
     * @rit.post cards are in sorted order
     */
    public void addCard( Card card ) {
        hand.add(card);
    }

    /**
     * What kind of hand is this?
     *
     * @return one of the {@link HandType} designations
     * @rit.pre addCard has been called twice
     */
    HandType getType() {
        if (hand.get(0).getSuit().equals(hand.get(1).getSuit())){
            return HandType.FLUSH;
        }
        else if (hand.get(0).getRank().equals(hand.get(1).getRank())){
            return HandType.TWO_OF_KIND;
        }
        else {
            return HandType.REGULAR;
        }
    }

    /**
     * Show this hand's contents.
     *
     * @return a string containing all the cards in the hand
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(hand.get(0).toString());

        for (int i = 1; i < 5; i++) {
            result.append(", ").append(hand.get(i).toString());
        }

        return result.toString();
    }

    /**
     * Does this hand beat another hand?<br>
     * Rules
     * <ul>
     *     <li>
     *         Pair beats flush, which beats two arbitrary cards.
     *     </li>
     *     <li>
     *         Otherwise compare higher card ranks.
     *     </li>
     *     <li>
     *         If higher card ranks are the same, compare lower card ranks.
     *     </li>
     *     <li>
     *         If both card ranks are the same, return 0 -- hands are identical.
     *     </li>
     * </ul>
     */
    @Override
    public int compareTo( PokerHand other ) {
        int result = 0;
        int value = Math.max(hand.get(0).value(), hand.get(1).value());

        int thisIntRank = (int)this.value;
        int otherIntRank = (int)other.value;

        double tempThisHighCard = rank.getValue() - thisIntRank;
        double tempOtherHighCard = rank.getValue() - otherIntRank;

        int thisHighCard = (int)tempThisHighCard;
        int otherHighCard = (int)tempOtherHighCard;

        if (thisIntRank == otherIntRank){ // If the same rank.
            if (thisHighCard > otherHighCard) {
                result = 1;
            } else if (otherHighCard > thisHighCard) {
                result = -1;
            } else {
                result = 0;
            }
        } else if (thisIntRank > otherIntRank) { // If this is of greater rank.
            result = 1;
        } else if (thisIntRank < otherIntRank) { // If the other is of greater rank.
            result = -1;
        } else {
            result = 0;
        }

        return result;

    }

}
