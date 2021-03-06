package sid.roborally.application_functionality;

import org.junit.Before;
import org.junit.Test;
import sid.roborally.game_mechanics.Direction;
import sid.roborally.game_mechanics.Game;
import sid.roborally.game_mechanics.IDComparator;
import sid.roborally.game_mechanics.card.Card;
import sid.roborally.game_mechanics.grid.ArchiveMarker;
import sid.roborally.game_mechanics.card.CardAction;
import sid.roborally.game_mechanics.card.StepCard;
import sid.roborally.game_mechanics.card.TurnCard;
import sid.roborally.game_mechanics.grid.Flag;
import sid.roborally.game_mechanics.grid.Position;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * <h3>GameTest</h3>
 * <p>This class tests the Game-class to check that all
 *    it features work as intended.</p>
 */
public class GameTest {

    Game game;
    Player p1, p2, p3;

    @Before
    public void addElements()
    {
        game = new Game();
        game.newGrid(10,10);

        p1 = new Player(1, false);
        p2 = new Player(2, false);
        p3 = new Player(3, false);
        p1.giveRobotStartPosition(new Position(1,1));
        p2.giveRobotStartPosition(new Position(2,2));
        p3.giveRobotStartPosition(new Position(3,3));
    }


    /**
     * <p>Checks that a game-instance will keep track of its players.</p>
     */
    @Test
    public void gameKeepsTrackOfPlayers()
    {
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        assertTrue(game.hasPlayer(p2));

        game.remove(p2);

        assertFalse(game.hasPlayer(p2));
    }

    /**
     * <p>Checks that we can create a correct sequence
     *    for what cards are played when and by who.</p>
     */
    @Test
    public void canSortCardsByPriority() {
        /* p1 cards */
        ArrayList<Card> p1Cards = new ArrayList<>();
        Card p1Card1 = new StepCard(100,3,CardAction.FORWARD);
        p1Cards.add(p1Card1);
        Card p1Card2 = new TurnCard(600,CardAction.TURN_LEFT);
        p1Cards.add(p1Card2);

        /* p2 cards */
        ArrayList<Card> p2Cards = new ArrayList<>();
        Card p2Card1 = new TurnCard(200,CardAction.FORWARD);
        p2Cards.add(p2Card1);
        Card p2Card2 = new TurnCard(150,CardAction.TURN_LEFT);
        p2Cards.add(p2Card2);

        /* p3 cards */
        ArrayList<Card> p3Cards = new ArrayList<>();
        Card p3Card1 = new TurnCard(105,CardAction.FORWARD);
        p3Cards.add(p3Card1);
        Card p3Card2 = new StepCard(5,2,CardAction.TURN_LEFT);
        p3Cards.add(p3Card2);

        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.setPlayerChosenCards(p1,p1Cards);
        game.setPlayerChosenCards(p2,p2Cards);
        game.setPlayerChosenCards(p3,p3Cards);

        ArrayList<HashMap<Player,Card>> sortedPlayerCardPairs = game.getCardsByPriority();

        /* Correct sequence is (what player it is is implicit)
            p2Card1;
            p2Card2;
            p3Card1;
            p1Card1;
            p1Card2;
            p3Card2;
        */

        assertFalse(sortedPlayerCardPairs.get(0).containsKey(p1));

        /* Checking cards in order */
        assertEquals(sortedPlayerCardPairs.remove(0).get(p2),p2Card1);
        assertEquals(sortedPlayerCardPairs.remove(0).get(p2),p2Card2);
        assertEquals(sortedPlayerCardPairs.remove(0).get(p3),p3Card1);
        assertEquals(sortedPlayerCardPairs.remove(0).get(p1),p1Card1);
        assertEquals(sortedPlayerCardPairs.remove(0).get(p1),p1Card2);
        assertEquals(sortedPlayerCardPairs.remove(0).get(p3),p3Card2);
    }

    /**
     * <p>Checks to see if game can move the player's robot in grid.</p>
     */
    @Test
    public void canMovePlayerRobot()
    {
        game.addPlayer(p1); //Player's robot should have position (1,1)
        assertEquals(new Position(1,1).toString(),
                p1.getRobot().getPosition().toString());

        /* Moving player's robot. */
        game.movePlayerRobot(p1, Direction.NORTH,1);
        game.movePlayerRobot(p1, Direction.EAST,1);
        game.movePlayerRobot(p1, Direction.EAST,1);

        /* New position should be (1+1+1, 1+1) = (3,2) */
        assertEquals(new Position(3,2).toString(),
                p1.getRobot().getPosition().toString());
    }

    @Test
    public void canMovePlayerForwardAndBackWithCards() {
        Player player = new Player(99,false);
        player.giveRobotStartPosition(new Position(4,1));
        game.addPlayer(player);
        player.getRobot().setOrientation(Direction.NORTH);

        int lastPosY = player.getRobot().getPosition().getY();

        StepCard move2forwards = new StepCard(100, 2, CardAction.FORWARD);
        StepCard moveBack = new StepCard(100, 1, CardAction.BACKWARD);

        /* Moving back */
        game.useCardOnPlayerRobot(player,moveBack);
        assertNotEquals(lastPosY, player.getRobot().getPosition().getY());
        assertEquals(lastPosY-1, player.getRobot().getPosition().getY());
        lastPosY = player.getRobot().getPosition().getY();

        /* Testing */
        game.useCardOnPlayerRobot(player,move2forwards);
        assertNotEquals(lastPosY, player.getRobot().getPosition().getY());
        assertEquals(lastPosY + 2, player.getRobot().getPosition().getY());
    }

    /**
     * <p>Tests to se if you can move playerRobots with cards.</p>
     */
    @Test
    public void canMovePlayerRobotWithCards() {
        Player player = new Player(99,false);
        player.giveRobotStartPosition(new Position(4,1));
        game.addPlayer(player);

        StepCard move2 = new StepCard(100, 2, CardAction.FORWARD);
        StepCard moveBack = new StepCard(100, 1, CardAction.BACKWARD);
        TurnCard turnRight = new TurnCard(100, CardAction.TURN_RIGHT);

        /* Sets the direction robot should be facing */
        player.getRobot().setOrientation(Direction.NORTH);

        /* Moves the robot with given cards */
        game.useCardOnPlayerRobot(player,move2);
        game.useCardOnPlayerRobot(player,moveBack);
        game.useCardOnPlayerRobot(player,turnRight);
        game.useCardOnPlayerRobot(player,move2);

        assertEquals(new Position(6,2).toString(),
                player.getRobot().getPosition().toString());
    }

    @Test
    public void gameCanRotatePlayerRobot() {
        TurnCard rotateRight, rotateLeft, rotate180;
        rotateRight = new TurnCard(256, CardAction.TURN_RIGHT);
        rotateLeft = new TurnCard(32, CardAction.TURN_LEFT);
        rotate180 = new TurnCard(8, CardAction.TURN_AROUND);

        Direction originalDirection = Direction.NORTH;
        game.addPlayer(p1);
        p1.getRobot().setOrientation(Direction.NORTH);

        game.useCardOnPlayerRobot(p1,rotateRight);
        assertEquals(p1.getRobot().getOrientation(), originalDirection.rotateRight());

        game.useCardOnPlayerRobot(p1,rotate180);
        assertEquals(p1.getRobot().getOrientation(), originalDirection.rotateRight().rotate180());

        game.useCardOnPlayerRobot(p1,rotateLeft);
        assertEquals(p1.getRobot().getOrientation(), originalDirection.rotate180());
    }

    /**
     * <p>Checks to see that the game can retrieve a local player.</p>
     */
    @Test
    public void canGetLocalPlayer()
    {
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        p2.setLocal();

        assertFalse(game.getLocal().equals(p1));
        assertTrue(game.getLocal().equals(p2));
        assertFalse(game.getLocal().equals(p3));

        p2.setAI();
        p3.setLocal();

        assertFalse(game.getLocal().equals(p1));
        assertFalse(game.getLocal().equals(p2));
        assertTrue(game.getLocal().equals(p3));
    }

    @Test
    public void AddFlagToFlagsListTest() {
        Flag f = new Flag(0,0, 1);
        game.addFlag(f);
        assertTrue(game.getFlags().contains(f));
    }

    @Test
    public void sortFlagsListBasedOffOfID() {
        ArrayList<Flag> expected = new ArrayList<>();
        
        Flag f1 = new Flag(0,0,1);
        Flag f2 = new Flag(1,1,2);
        Flag f3 = new Flag(2,2,3);

        expected.add(f1);
        expected.add(f2);
        expected.add(f3);

        game.addFlag(f3);
        game.addFlag(f1);
        game.addFlag(f2);

        assertNotEquals(expected,game.getFlags());

        game.getFlags().sort(new IDComparator<>());
        assertEquals(expected, game.getFlags());
    }

    @Test
    public void sortArchiveMarkerListBasedOffOfID() {
        ArrayList<ArchiveMarker> expected = new ArrayList<>();

        ArchiveMarker a1 = new ArchiveMarker(0,0,1);
        ArchiveMarker a2 = new ArchiveMarker(1,1,2);
        ArchiveMarker a3 = new ArchiveMarker(2,2,3);

        expected.add(a1);
        expected.add(a2);
        expected.add(a3);

        game.addArchiveMarker(a3);
        game.addArchiveMarker(a1);
        game.addArchiveMarker(a2);

        assertNotEquals(expected,game.getArchiveMarkers());

        game.getArchiveMarkers().sort(new IDComparator<>());
        assertEquals(expected, game.getArchiveMarkers());
    }

    @Test
    public void addArchiveMarkerAddsArchiveMarkerTest() {
        ArchiveMarker am = new ArchiveMarker(0,1,1);
        game.addArchiveMarker(am);
        assertTrue(game.getArchiveMarkers().contains(am));
    }
    @Test
    public void flagsListContainsFlagWithIDOneTest(){
        game.addFlag(new Flag(1,1,1));
        assertTrue(game.containsFlagWithID(1));
    }
    @Test
    public void emptyFlagsListTest() {
        game.addFlag(new Flag(1,1,1));
        ArrayList<Flag> expected = new ArrayList<>();

        assertNotEquals(expected,game.getFlags());

        game.emptyFlags();
        assertEquals(expected,game.getFlags());
    }
    @Test
    public void archiveListContainsArchiveWithIDOneTest() {
        game.addArchiveMarker(new ArchiveMarker(1,2,1));
        assertTrue(game.containsArchiveMarkerWithID(1));
    }
}
