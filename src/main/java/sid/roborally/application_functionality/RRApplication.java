package sid.roborally.application_functionality;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import sid.roborally.application_functionality.reference.Map;

import java.util.HashSet;

/**
 * <h3>RoboRallyApplication</h3>
 *
 * <p>The goal of this class is to provide the program with mechanisms for
 *    passing input both when in Menu-mode or Game-mode. <br>
 *    To be a convenient way to tie together the different parts of the
 *    program.</p><br>
 *
 * <p>This class will be instantiated in Main, both to
 *    recieve and pass information and requests.<br>
 *    It will contain instances of several parts of the program as this class
 *    is central in tying together the program.</p>
 *
 * @author Daniel Janols
 */
public class RRApplication {

    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;

    /* Where to route input */
    private enum InputHolder {Menu, GameRunner}
    private InputHolder inputHolder;

    /* Application-running */
    private GameRunner grunner; //Sets up and starts a game.

    /**
     * <p>RoboRallyApplication constructor.</p>
     */
    public RRApplication() {
        inputHolder = InputHolder.Menu;
        grunner = new GameRunner();
    }

    public GameRunner getGameRunner() {
        return grunner;
    }


    /*
     * Functionality:
     */

    public void startGame() {
        inputHolder = InputHolder.GameRunner;
    }

    /**
     * <p>Quits/Shuts down the whole application.</p>
     */
    public void quitApplication() { Gdx.app.exit(); }

    /*
     * Tiled methods:
     */

    /**
     * <p>TiledMap instance-getter</p>
     * @return map - Local TiledMap instance.
     */
    public TiledMap getMap() { return grunner.getMap(); }

    /**
     * <p>TiledMapTileLayer (player-layer>-getter</p>
     * @return playerLayer - Local player-layer.
     */
    public TiledMapTileLayer getPlayerLayer() { return grunner.getPlayerLayer(); }

    /**
     * <p>Returns a set of the Player-instances associated with the current game.</p>
     * @return players - Set of Player-instances.
     */
    public HashSet<Player> getPlayers() { return grunner.getPlayers(); }

    /*
     * Keyboard-input
     */

    /**
     * <p>Tells the application that it has recieved a UP-input
     * <br>W and ArrowUp</p>
     */
    public void moveUpInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.moveUpInput(); }

    /**
     * <p>Tells the application that it has recieved a DOWN-input
     * <br>S and ArrowDown</p>
     */
    public void moveDownInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.moveDownInput(); }

    /**
     * <p>Tells the application that it has recieved a LEFT-input
     * <br>A and ArrowLeft</p>
     */
    public void moveLeftInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.moveLeftInput(); }

    /**
     * <p>Tells the application that it has recieved a RIGHT-input
     * <br>D and ArrowRight</p>
     */
    public void moveRightInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.moveRightInput(); }

    public void rotateLeftInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.rotateLeftInput(); }

    public void rotateRightInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.rotateRightInput(); }

    public void stepOneForwardInput()
    { if(inputHolder == InputHolder.GameRunner) grunner.stepOneForwardInput(); }

    /**
     * <p>Tells the application that it has recieved a ESCAPE-input
     * <br>ESCAPE/ESC</p>
     */
    public void escapeInput() { quitApplication(); }
}
