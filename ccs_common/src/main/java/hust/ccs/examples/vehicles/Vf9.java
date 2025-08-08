package hust.ccs.examples.vehicles;


import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import hust.ccs.Sound;
import hust.ccs.Steering;
import hust.ccs.Vehicle;
import hust.ccs.WheelModel;
import hust.ccs.examples.engines.FlexibleEngine;
import hust.ccs.examples.sounds.EngineSound1;
import hust.ccs.examples.sounds.HornSound1;
import hust.ccs.examples.tires.Tire02;
import hust.ccs.examples.wheels.DarkAlloyWheel;
import hust.ccs.part.Engine;
import hust.ccs.part.GearBox;
import hust.ccs.part.Suspension;
import hust.ccs.part.Wheel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Vf9 extends Vehicle {

    final public static Logger logger2 = Logger.getLogger(Vf9.class.getName());

    public Vf9() {
        super("VF9");
    }

    @Override
    public void load(AssetManager assetManager) {
        if (isLoaded()) {
            logger2.log(Level.SEVERE, "Model is already loaded.");
            return;
        }

        // Khối lượng thực tế VF9 ~ 2695 kg
        float mass = 2695f;
        float linearDamping = 0.004f;

        // Tên file chassis model (không có bánh)
        setChassis("vf9", "scene", assetManager, mass, linearDamping);

        // Đường kính bánh VF9 (ước lượng ~0.75m)
        float diameter = 0.75f;
        WheelModel lFrontWheel = new DarkAlloyWheel(diameter);
        WheelModel rFrontWheel = new DarkAlloyWheel(diameter);
        WheelModel lRearWheel = new DarkAlloyWheel(diameter);
        WheelModel rRearWheel = new DarkAlloyWheel(diameter);

        lFrontWheel.load(assetManager);
        rFrontWheel.load(assetManager);
        lRearWheel.load(assetManager);
        rRearWheel.load(assetManager);

        // Flip bánh phải
        rFrontWheel.flip();
        rRearWheel.flip();

        // Offset bánh so với chassis
        float wheelX = 0.75f; // Bán trục (nửa chiều rộng xe)
        float wheelY = 0f;    // Chiều cao bánh so với chassis (nếu có)
        float frontZ = 12.5f; // Vị trí trục bánh trước theo trục Z
        float rearZ = 0.0f;   // Vị trí trục bánh sau theo trục Z

        addWheel(lFrontWheel, new Vector3f(+wheelX, wheelY, frontZ),
                Steering.DIRECT, 5000f, 25000f, 0.025f);
        addWheel(rFrontWheel, new Vector3f(-wheelX, wheelY, frontZ),
                Steering.DIRECT, 5000f, 25000f, 0.025f);
        addWheel(lRearWheel, new Vector3f(+wheelX, wheelY, rearZ),
                Steering.UNUSED, 5000f, 0f, 0.025f);
        addWheel(rRearWheel, new Vector3f(-wheelX, wheelY, rearZ),
                Steering.UNUSED, 5000f, 0f, 0.025f);

        // Suspension setup
        for (Wheel wheel : listWheels()) {
            Suspension suspension = wheel.getSuspension();
            suspension.setStiffness(25f);
            suspension.setCompressDamping(0.6f);
            suspension.setRelaxDamping(0.8f);
        }

        // Lốp và lực ma sát
        for (Wheel wheel : listWheels()) {
            wheel.setTireModel(new Tire02());
            wheel.setFriction(1.0f); // VF9 bám đường tốt
        }

        // Phân phối lực dẫn động 4 bánh đều
        for (int i = 0; i < 4; i++) {
            getWheel(i).setPowerFraction(0.25f);
        }

        // Hộp số 2 cấp điện (giả lập)
        GearBox gearBox = new GearBox(2, 1);
        gearBox.getGear(-1).setName("reverse").setMinMaxRedKph(0f, -60f, -60f);
        gearBox.getGear(1).setName("low").setMinMaxRedKph(0f, 150f, 180f);
        gearBox.getGear(2).setName("high").setMinMaxRedKph(120f, 210f, 210f);
        setGearBox(gearBox);

        // Động cơ điện ~300 kW (402 HP), rpm giả lập
        float idleRpm = 600f; // idle rpm tối thiểu (không để 0)
        float redlineRpm = 16000f;

        Engine engine = new FlexibleEngine("300kW electric",
                402f * Engine.HP_TO_W, idleRpm, redlineRpm);

        setEngine(engine);

        Sound engineSound = new EngineSound1();
        engineSound.load(assetManager);
        engine.setSound(engineSound);

        Sound hornSound = new HornSound1();
        hornSound.load(assetManager);
        setHornSound(hornSound);

        // Build xe
        build();
    }

    @Override
    public void locateDashCam(Vector3f storeResult) {
        storeResult.set(0f, 1.4f, 1.0f);
    }

    @Override
    protected void locateTarget(Vector3f storeResult) {
        storeResult.set(0f, 0.6f, -2.0f);
    }
}
