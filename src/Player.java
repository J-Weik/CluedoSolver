import java.util.LinkedList;

public class Player {
    private String name;
    private final int id;
    private int amountCards;
    private LinkedList<Accusation> accusations;
    private LinkedList<Card> cardsOwned;
    private LinkedList<Card> cardsNotOwned;
    private boolean allCardsKnown;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.amountCards = 0;
        this.accusations = new LinkedList<>();
        this.cardsOwned = new LinkedList<>();
        this.cardsNotOwned = new LinkedList<>();
        this.allCardsKnown = false;
    }

    public int getAmountCards() {
        return amountCards;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAmountCards(int amountCards) {
        this.amountCards = amountCards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAccusation(Accusation accusation) {
        accusations.add(accusation);
    }

    void setAllCardsKnown(boolean allCardsKnown) {
        this.allCardsKnown = allCardsKnown;
    }

    boolean addKnownCard(Card card) {
        for (Card c : cardsOwned) {
            if (c.equals(card))
                return false;
        }
        cardsOwned.add(card);
        return true;
    }

    boolean addCardNotOwned(Card card) {
        for (Card c : cardsNotOwned) {
            if (c.equals(card))
                return false;
        }
        cardsNotOwned.add(card);
        return true;
    }

    boolean checkForNewInfo)() {
    for (Accusation acc : accusations) {

    }
    }

}
