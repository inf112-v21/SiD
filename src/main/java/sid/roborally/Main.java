package sid.roborally;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import sid.roborally.application_functionality.connection.Client;
import sid.roborally.application_functionality.connection.Server;
import sid.roborally.application_functionality.reference.Map;
import sid.roborally.game_mechanics.card.Card;
import sid.roborally.game_mechanics.card.CardDeck;
import sid.roborally.gfx_and_ui.AppListener;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.util.Scanner;

/**
 * <h3>Main</h3>
 */
public class Main {

    private static final int WIDTH = 1024, HEIGHT = 780;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Robo-Rally");
        config.setWindowedMode(WIDTH, HEIGHT);
        new Lwjgl3Application(new AppListener(), config);
    }
}
