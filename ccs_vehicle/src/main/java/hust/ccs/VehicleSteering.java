package hust.ccs;

/**
 * Interface to the steering system of a vehicle.
 */
public interface VehicleSteering {
    /**
     * Determine the rotation angle of the steering wheel, handlebars, or
     * tiller.
     *
     * @return the angle (in radians, negative = left, 0 = neutral, positive =
     * right)
     */
    float steeringWheelAngle();
}
