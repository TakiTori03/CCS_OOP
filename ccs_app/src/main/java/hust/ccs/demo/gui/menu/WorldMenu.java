package hust.ccs.demo.gui.menu;


import com.jme3.asset.AssetManager;
import com.simsilica.lemur.Button;
import hust.ccs.World;
import hust.ccs.demo.lemurdemo.AppDemo;
import hust.ccs.examples.worlds.EndlessPlain;
import hust.ccs.examples.worlds.Mountains;
import hust.ccs.examples.worlds.Racetrack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * An AnimatedMenu to choose among the available worlds.

 */
class WorldMenu extends AnimatedMenu {

    /**
     * message logger for this class
     */
    final private static Logger logger
            = Logger.getLogger(WorldMenu.class.getName());
    // *************************************************************************
    // AnimatedMenuState methods

    /**
     * Generate the items for this menu.
     *
     * @return a new array of GUI buttons
     */
    @Override
    protected List<Button> createItems() {
        List<Button> result = new ArrayList<>(5);

        addButton(result, "Endless Plain",
                source -> setWorld(new EndlessPlain()));
        addButton(result, "Mountains",
                source -> setWorld(new Mountains()));
        addButton(result, "Racetrack",
                source -> setWorld(new Racetrack()));
        addButton(result, "<< Back",
                source -> animateOut(() -> goTo(new MainMenu())));

        return result;
    }
    // *************************************************************************
    // private methods

    private static void setWorld(World newWorld) {
        AppDemo main = AppDemo.getApplication();
        AssetManager assetManager = main.getAssetManager();
        newWorld.load(assetManager);

        main.setWorld(newWorld);
    }
}
