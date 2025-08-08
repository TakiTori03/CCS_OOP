package hust.ccs.demo.debug.parts;


import com.simsilica.lemur.Container;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.props.PropertyPanel;
import hust.ccs.Vehicle;
import hust.ccs.part.Brake;

public class BrakesEditor extends Container {


    final private Vehicle vehicle;
    // *************************************************************************
    // constructors

    public BrakesEditor(Vehicle vehicle) {
        super();

        this.vehicle = vehicle;
        addChild(createBrakesRollup());
    }
    // *************************************************************************
    // private methods

    private RollupPanel createBrakesRollup() {
        PropertyPanel propertyPanel = new PropertyPanel("glass");

        for (int i = 0; i < vehicle.countWheels(); ++i) {
            Brake mainBrake = vehicle.getWheel(i).getMainBrake();
            propertyPanel.addFloatProperty(
                    "Wheel " + i, mainBrake, "peakForce", 0f, 1_000f, 0.1f);
        }

        return new RollupPanel("Strength", propertyPanel, "glass");
    }
}
