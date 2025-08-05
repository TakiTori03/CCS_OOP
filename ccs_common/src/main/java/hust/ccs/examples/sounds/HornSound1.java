package hust.ccs.examples.sounds;


import hust.ccs.Sound;

/**
 * A Sound built around "horn-1.ogg".
 */
public class HornSound1 extends Sound {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the Sound.
     */
    public HornSound1() {
        super.addAssetPath("/Audio/horn-1.ogg", 823f);
    }
}
