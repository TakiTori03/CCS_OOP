package hust.ccs;

/**
 * Interface to the speed (and maximum speed) of a Vehicle, for use in
 * speedometers.
 */
public interface VehicleSpeed {
    /**
     * Determine the forward component of the vehicle's inertial velocity.
     *
     * @param speedUnit the unit of measurement to use (not null)
     * @return the speed (in the specified units, may be negative)
     */
    float forwardSpeed(SpeedUnit speedUnit);

    /**
     * Estimate the vehicle's maximum forward speed.
     *
     * @param speedUnit the unit of measurement to use (not null)
     * @return the speed (in the specified units, &ge;0)
     */
    float maxForwardSpeed(SpeedUnit speedUnit);

    /**
     * Estimate the vehicle's maximum reverse speed.
     *
     * @param speedUnit the unit of measurement to use (not null)
     * @return the speed (in the specified units, &le;0)
     */
    float maxReverseSpeed(SpeedUnit speedUnit);
}
