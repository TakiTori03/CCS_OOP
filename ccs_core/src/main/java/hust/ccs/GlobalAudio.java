package hust.ccs;

/**
 * Interface to the global audio controls.
 */
public interface GlobalAudio {
    /**
     * Determine the effective global audio volume.
     *
     * @return the volume (linear scale, &ge;0, &le;1)
     */
    float effectiveVolume();
}
