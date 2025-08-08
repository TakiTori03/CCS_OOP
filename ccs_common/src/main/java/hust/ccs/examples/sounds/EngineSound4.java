package hust.ccs.examples.sounds;


import hust.ccs.Sound;

/**
 * A Sound built around "engine-4.ogg".

 */
public class EngineSound4 extends Sound {
    // *************************************************************************
    // constructors

    /**
     * Instantiate the Sound.
     */
    public EngineSound4() {
        super.addAssetPath("/Audio/engine-4d8.ogg", 10.7f);
        super.addAssetPath("/Audio/engine-4d4.ogg", 21.4f);
        super.addAssetPath("/Audio/engine-4d2.ogg", 42.9f);
        super.addAssetPath("/Audio/engine-4.ogg", 85.75f);
        super.addAssetPath("/Audio/engine-4x2.ogg", 171.5f);
    }
}
