package sid.roborally.game_mechanics.card;

/**
 * This is the general card class
 * It describes variables all cards need
 * If anything is added to this class,
 * those variables must then be added
 * to the call to this constructor of
 * classes inheriting from this class
 *
 * @author Emil ELdooen
 */
public class Card {
    //Priority, determines the order a card is played in, higher priority wins
    private int priority;

    //Constructor giving card its priority
    public Card(int pri){
        this.priority = pri;
    }

    //Allows other classes to access a card's priority
    public int getPriority(){
        return priority;
    }
}