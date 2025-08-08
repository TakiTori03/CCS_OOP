package hust.ccs.demo.gui.menu;


import com.jme3.asset.AssetManager;
import com.simsilica.lemur.Button;
import hust.ccs.Sky;
import hust.ccs.demo.lemurdemo.AppDemo;
import hust.ccs.examples.skies.AnimatedDaySky;
import hust.ccs.examples.skies.AnimatedNightSky;
import hust.ccs.examples.skies.PurpleNebulaSky;
import hust.ccs.examples.skies.QuarrySky;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * An AnimatedMenu to choose among the available skies.
 */
class SkyMenu extends AnimatedMenu {


    /**
     * message logger for this class
     */
    final private static Logger logger
            = Logger.getLogger(SkyMenu.class.getName());
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

        addButton(result, "Animated Day",
                source -> setSky(new AnimatedDaySky()));
        addButton(result, "Animated Night",
                source -> setSky(new AnimatedNightSky()));
        addButton(result, "Purple Nebula",
                source -> setSky(new PurpleNebulaSky()));
        addButton(result, "Quarry Day",
                source -> setSky(new QuarrySky()));
        addButton(result, "<< Back",
                source -> animateOut(() -> goTo(new CustomizationMenu())));

        return result;
    }
    // *************************************************************************
    // private methods

    private static void setSky(Sky sky) {
        AppDemo main = AppDemo.getApplication();
        AssetManager assetManager = main.getAssetManager();
        sky.load(assetManager);

        AppDemo.setSky(sky);
    }
}
