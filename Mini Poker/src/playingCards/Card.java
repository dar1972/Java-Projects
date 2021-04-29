package playingCards;

/**
 * The function creates a card
 */
public class Card extends Object
{
    private Rank rank;
    private Suit suit;
    private Object Rank;
    private Object Suit;

    /**
     * The function is the constructor for the card
     * @param rank the number on the card
     * @param suit the type the card is of
     */
    public Card(Rank rank, Suit suit)
    {
        this.rank=rank;
        this.suit=suit;
    }

    /**
     * The function returns the value of the rank
     * @return the value of the rank
     */
    public int value() {
        return rank.getValue();
    }

    /**
     * the functions returns the card number
     *
     * @return the number on card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * The function returns the type the card is of
     *
     * @return the suit card belongs to
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * The function computes the information of card as a String
     * @return String of information
     */
    public String toString()
    {
        return rank.toString() + " of " + suit.toString();
    }

    /**
     * The function computes the short name of the card
     * @return String of short name
     */
    public String getShortName(){
        return rank.name() + suit.name();
    }
}
