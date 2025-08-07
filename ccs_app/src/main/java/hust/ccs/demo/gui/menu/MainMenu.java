package hust.ccs.demo.gui.menu;


import com.jme3.app.state.AppStateManager;
import com.simsilica.lemur.Button;
import hust.ccs.Vehicle;
import hust.ccs.demo.debug.DebugTabState;
import hust.ccs.demo.debug.EnginePowerGraphState;
import hust.ccs.demo.debug.TireDataState;
import hust.ccs.demo.debug.VehicleEditorState;
import hust.ccs.demo.gui.lemur.DriverHud;
import hust.ccs.demo.input.DrivingInputMode;
import hust.ccs.demo.lemurdemo.AppDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainMenu extends AnimatedMenu {
    // *************************************************************************
    // constants and loggers

    /**
     * message logger for this class
     */
    final private static Logger logger
            = Logger.getLogger(MainMenu.class.getName());
    // *************************************************************************
    // AnimatedMenuState methods

    /**
     * Generate the items for this menu.
     *
     * @return a new array of GUI buttons
     */
    @Override
    protected List<Button> createItems() {
        AppDemo application = AppDemo.getApplication();
        List<Button> result = new ArrayList<>(6);

        addButton(result, "Drive",
                source -> animateOut(this::drive));
        addButton(result, "Change World",
                source -> animateOut(() -> goTo(new WorldMenu())));
        addButton(result, "Change Vehicle",
                source -> animateOut(() -> goTo(new VehicleMenu())));
        addButton(result, "Customize",
                source -> animateOut(() -> goTo(new CustomizationMenu())));
        addButton(result, "Quit the Demo",
                source -> animateOut(application::stop));

        return result;
    }
    // *************************************************************************
    // private methods

    /**
     * Drive the selected Vehicle in the selected World.
     */
    private void drive() {
        Vehicle vehicle = AppDemo.getVehicle();
        vehicle.getEngine().setRunning(true);
        DriverHud hud = getState(DriverHud.class);
        hud.setVehicle(vehicle);
        hud.setEnabled(true);

        getState(DrivingInputMode.class).setEnabled(true);

        // engine graph GUI for viewing torque/power @ revs
        EnginePowerGraphState enginePowerGraphState
                = new EnginePowerGraphState(vehicle);
        enginePowerGraphState.setEnabled(false);
        AppStateManager stateManager = getStateManager();
        stateManager.attach(enginePowerGraphState);

        // tire data GUI for viewing how much grip each tire has according to the Pacejka formula
        TireDataState tireDataState = new TireDataState(vehicle);
        tireDataState.setEnabled(false);
        stateManager.attach(tireDataState);

        // the main vehicle editor to modify aspects of the vehicle in real time
        VehicleEditorState vehicleEditorState = new VehicleEditorState(vehicle);
        stateManager.attach(vehicleEditorState);

        // vehicle debug add-on to enable/disable debug screens
        DebugTabState debugTabState = new DebugTabState();
        stateManager.attach(debugTabState);

        stateManager.detach(this);
    }
}
