package hust.ccs.demo.debug.parts;


import com.simsilica.lemur.Container;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.props.PropertyPanel;
import hust.ccs.Vehicle;

public class ChassisEditor extends Container {


    public ChassisEditor(Vehicle vehicle) {
        super();

        String styleName = "glass";
        PropertyPanel propertyPanel = new PropertyPanel(styleName);
        propertyPanel.addFloatProperty(
                "Mass", vehicle, "mass", 1f, 5000f, 0.1f);
        RollupPanel panel = new RollupPanel("Mass", propertyPanel, styleName);
        addChild(panel);
    }
}
