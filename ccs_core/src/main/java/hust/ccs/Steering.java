package hust.ccs;
/**
 * Enumerate the ways a steering system can relate to a specific wheel.
 */
public enum Steering {
    /**
     * steering left turns the wheel leftward (and vice versa), for example the
     * front wheels of most passenger cars
     */
    DIRECT,
    /**
     * steering left turns the wheel rightward (and vice versa), for example in
     * active 4-wheel steering systems
     */
    FLIPPED,
    /**
     * the wheel isn't steered, for example the rear wheels of most passenger
     * cars
     */
    UNUSED
}
