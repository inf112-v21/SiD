package sid.roborally.application_functionality;

import sid.roborally.application_functionality.reference.PlayerTexture;
import sid.roborally.game_mechanics.grid.Position;
import sid.roborally.game_mechanics.grid.Robot;
import sid.roborally.gfx_and_ui.PlayerGraphic;

import java.io.Serializable;

/**
 * <h3>Player</h3>
 *
 * <p>The goal of this class is to provide a mechanism and entity to keep
 *    information on a current player (user/AI).<br>
 *    To connect the graphics-part of a player and player-mechanisms for taking
 *    part in a game and communicating player-graphics and other player-information.</p><br>
 *
 * <p>This class will be instantiated in GameRunner and will also be associated with one or more
 *    game-instances.<br>
 *    The Player-instance shouldn't communicate it's orders directly with robot or grid; it should
 *    communicate its input trough the game-instance.</p>
 *
 *  @author Daniel Janols
 * */
public class Player implements Serializable {

    private enum OwnerLocation {Local, AI, External} //External is a off-site-user (multiplayer)
    private enum State {Active, Won, Dead}

    private State playerState;
    private OwnerLocation ownerLocation;
    private PlayerGraphic p_graphic;
    private Robot robot; //The player-instance's robot
    private int startID; //What startPosition ID player should have

    /**
     * <p>Player constructor that specifies a chosen skin-texture index.</p>
     *
     * @param startID What start position should player have
     */
    public Player(int startID) {
        playerState = State.Active;
        ownerLocation = OwnerLocation.AI; //AI basic setting if nothing else given.
        p_graphic = null;
        this.startID = startID;
    }

    /**
     * <p>Player constructor that does not specify a chosen texture and will
     * pick a basic texture if you don't tell it to not have a texture.</p>
     *
     * @param startID StartID. 1,2,3,4,5...
     * @param giveGraphics Should the player-instance have graphics.
     */
    public Player(int startID, boolean giveGraphics) {
        playerState = State.Active;
        p_graphic = giveGraphics ? new PlayerGraphic(this, PlayerTexture.Player1) : null;
        this.startID = startID;
    }

    public void givePlayerTexture(PlayerTexture pt) {
        p_graphic = new PlayerGraphic(this, pt);
    }

    /**
     * <p>Sets player-robot start position. Must be called before the game starts</p>
     * @param pos Start-position
     */
    public void giveRobotStartPosition(Position pos) {
        if(robot == null) robot = new Robot(pos.getX(), pos.getY());
        else robot.setPosition(pos);
    }

    /*
     * * * * * Player-state methods
     */

    public void playerWon() { playerState = State.Won; }
    public boolean isDead() { return playerState == State.Dead; }
    public boolean hasWon() { return playerState == State.Won; }
    public void killPlayer() { playerState = State.Dead; }

    /*
     * * * * * Owner locality methods:
     */

    /**
     * <p>Sets Player-instance as a local-player</p>
     */
    public void setLocal() { ownerLocation = OwnerLocation.Local; }

    /**
     * <p>Sets Player-instance as a AI-player</p>
     */
    public void setAI() { ownerLocation = OwnerLocation.AI; }

    /**
     * <p>Sets Player-instance as a external-player</p>
     */
    public void setExternal() { ownerLocation = OwnerLocation.External; }

    /**
     * <p>Informs if Player-instance is Local</p>
     * @return boolean Is player local
     */
    public boolean isLocal() { return ownerLocation == OwnerLocation.Local; }

    /**
     * <p>Informs if Player-instance is AI</p>
     * @return boolean Is player AI
     */
    public boolean isAI() { return ownerLocation == OwnerLocation.AI; }

    /**
     * <p>Informs if Player-instance is External</p>
     * @return boolean Is player external
     */
    public boolean isExternal() { return ownerLocation == OwnerLocation.External; }

    /*
     * * * * * Getters and Setters
     */

    /**
     * <p>Gets Robot associated with this Player-instance.</p>
     * @return robot Robot-instance
     */
    public Robot getRobot() { return robot; }

    /**
     * <p>Gets PlayerGraphic associated with this Player-instance.</p>
     * @return p_graphic PlayerGraphic-instance.
     */
    public PlayerGraphic getPlayerGraphic() { return p_graphic; }

    /**
     * <p>Gets startID</p>
     * @return startID
     */
    public int getStartID() { return startID; }
}
