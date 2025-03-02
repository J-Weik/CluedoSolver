import java.util.LinkedList;

public class Accusation {
    private Card Person;
    private Card Weapon;
    private Card Room;

    public Accusation(Card Person, Card Weapon, Card Room) {
        this.Person = Person;
        this.Weapon = Weapon;
        this.Room = Room;
    }

    public Card getPerson() {
        return Person;
    }

    public Card getWeapon() {
        return Weapon;
    }

    public Card getRoom() {
        return Room;
    }

    public LinkedList<Card> getIntersectingCards(Accusation a) {
        LinkedList<Card> intersectingCards = new LinkedList<>();
        if(Person.equals(a.getPerson()))
            intersectingCards.add(Person);
        if(Weapon.equals(a.getWeapon()))
            intersectingCards.add(Weapon);
        if(Room.equals(a.getRoom()))
            intersectingCards.add(Room);
        return intersectingCards;
    }
}
