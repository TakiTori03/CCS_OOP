package hust.ccs.demo.gui.menu;


import com.jme3.asset.AssetManager;
import com.simsilica.lemur.Button;
import hust.ccs.Vehicle;
import hust.ccs.demo.input.CameraInputMode;
import hust.ccs.demo.lemurdemo.AppDemo;
import hust.ccs.examples.vehicles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * An AnimatedMenu to choose among the available vehicles.
 *
 * Derived from the CarSelectorState class in the Advanced Vehicles project.
 */
class VehicleMenu extends AnimatedMenu {

    /**
     * message logger for this class
     */
    final private static Logger logger
            = Logger.getLogger(VehicleMenu.class.getName());
    // *************************************************************************
    // AnimatedMenuState methods

    /**
     * Generate the items for this menu.
     *
     * @return a new array of GUI buttons
     */
    @Override
    protected List<Button> createItems() {
        List<Button> result = new ArrayList<>(9);


        addButton(result, "Grand Tourer",
                source -> setVehicle(new GrandTourer()));
        addButton(result, "Nismo",
                source -> setVehicle(new Nismo()));
        addButton(result, "Pickup Truck",
                source -> setVehicle(new PickupTruck()));
        addButton(result, "Hatchback",
                source -> setVehicle(new HatchBack()));
        addButton(result, "Lamborghini",
                source -> setVehicle(new Aventador()));
        addButton(result, "Hover Tank",
                source -> setVehicle(new HoverTank()));
        addButton(result, "<< Back",
                source -> animateOut(() -> goTo(new MainMenu())));

        return result;
    }
    // *************************************************************************
    // private methods

    private void setVehicle(Vehicle vehicle) {
        AppDemo main = AppDemo.getApplication();
        AssetManager assetManager = main.getAssetManager();
        vehicle.load(assetManager);
        AppDemo.setVehicle(vehicle);

        getState(CameraInputMode.class).setVehicle(vehicle);
    }
}
