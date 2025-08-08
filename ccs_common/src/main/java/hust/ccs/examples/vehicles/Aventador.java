package hust.ccs.examples.vehicles;


import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import hust.ccs.Sound;
import hust.ccs.Steering;
import hust.ccs.Vehicle;
import hust.ccs.WheelModel;
import hust.ccs.examples.engines.PeakyEngine;
import hust.ccs.examples.sounds.EngineSound2;
import hust.ccs.examples.sounds.HornSound1;
import hust.ccs.examples.tires.Tire01;
import hust.ccs.examples.wheels.DarkAlloyWheel;
import hust.ccs.part.Engine;
import hust.ccs.part.GearBox;
import hust.ccs.part.Suspension;
import hust.ccs.part.Wheel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Aventador extends Vehicle {
    final public static Logger logger2 = Logger.getLogger(Aventador.class.getName());

    public Aventador() {
        super("Aventador");
    }

    @Override
    public void load(AssetManager assetManager) {
        if (isLoaded()) {
            logger.log(Level.SEVERE, "The model is already loaded.");
            return;
        }

        // Trọng lượng & damping
        float mass = 1575f;
        float linearDamping = 0.01f; // tăng damping để bớt rung khi tăng tốc
        setChassis("aventador", "scene.gltf", assetManager, mass, linearDamping);

        // Đường kính bánh xe gần với Aventador thật
        float frontDiameter = 0.68f; // ~20 inch lốp 255/30ZR20
        float rearDiameter  = 0.70f; // ~21 inch lốp 355/25ZR21

        WheelModel lFrontWheel = new DarkAlloyWheel(frontDiameter);
        WheelModel rFrontWheel = new DarkAlloyWheel(frontDiameter);
        WheelModel lRearWheel  = new DarkAlloyWheel(rearDiameter);
        WheelModel rRearWheel  = new DarkAlloyWheel(rearDiameter);

        lFrontWheel.load(assetManager);
        rFrontWheel.load(assetManager);
        lRearWheel.load(assetManager);
        rRearWheel.load(assetManager);

        rFrontWheel.flip();
        rRearWheel.flip();

        // Vị trí gắn bánh (đo thủ công từ Blender, apply scale trước khi export)
        float wheelX = 0.85f;
        float axleY = 0.28f;  // hạ nhẹ trục bánh để trọng tâm thấp hơn
        float frontZ = 1.05f;
        float rearZ = -1.58f;
        float parkingBrake = 30000f;
        float wheelDamping = 0.01f;

        addWheel(lFrontWheel, new Vector3f(+wheelX, axleY, frontZ),
                Steering.DIRECT, 8000f, 0f, wheelDamping);
        addWheel(rFrontWheel, new Vector3f(-wheelX, axleY, frontZ),
                Steering.DIRECT, 8000f, 0f, wheelDamping);
        addWheel(lRearWheel, new Vector3f(+wheelX, axleY, rearZ),
                Steering.UNUSED, 4000f, parkingBrake, wheelDamping);
        addWheel(rRearWheel, new Vector3f(-wheelX, axleY, rearZ),
                Steering.UNUSED, 4000f, parkingBrake, wheelDamping);

        // Chỉnh suspension
        int index = 0;
        for (Wheel wheel : listWheels()) {
            Suspension suspension = wheel.getSuspension();
            if (index < 2) { // bánh trước
                suspension.setMaxForce(9000f);
                suspension.setStiffness(15.0f);
                suspension.setCompressDamping(0.65f);
                suspension.setRelaxDamping(0.85f);
            } else { // bánh sau
                suspension.setMaxForce(9000f);
                suspension.setStiffness(14.0f);
                suspension.setCompressDamping(0.55f);
                suspension.setRelaxDamping(0.75f);
            }
            index++;
        }

        // Lốp
        for (Wheel wheel : listWheels()) {
            wheel.setTireModel(new Tire01());
            wheel.setFriction(2.1f); // tăng độ bám đường
        }

        // Phân bổ lực truyền động: AWD nhưng ưu tiên bánh sau
        getWheel(0).setPowerFraction(0.20f); // FL
        getWheel(1).setPowerFraction(0.20f); // FR
        getWheel(2).setPowerFraction(0.30f); // RL
        getWheel(3).setPowerFraction(0.30f); // RR

        // Hộp số
        GearBox gearBox = new GearBox(7, 1);
        gearBox.getGear(-1).setName("reverse").setMinMaxRedKph(0f, -50f, -50f);
        gearBox.getGear(1).setName("low").setMinMaxRedKph(0f, 50f, 55f);
        gearBox.getGear(2).setName("2nd").setMinMaxRedKph(40f, 90f, 95f);
        gearBox.getGear(3).setName("3rd").setMinMaxRedKph(80f, 140f, 150f);
        gearBox.getGear(4).setName("4th").setMinMaxRedKph(130f, 190f, 200f);
        gearBox.getGear(5).setName("5th").setMinMaxRedKph(180f, 240f, 250f);
        gearBox.getGear(6).setName("6th").setMinMaxRedKph(230f, 300f, 310f);
        gearBox.getGear(7).setName("high").setMinMaxRedKph(290f, 360f, 360f);
        setGearBox(gearBox);

        // Động cơ Aventador V12
        float idleRpm = 850f;
        float redlineRpm = 8500f;
        Engine engine = new PeakyEngine("770-hp V12 850-8500 RPM",
                770f * Engine.HP_TO_W, idleRpm, redlineRpm);
        setEngine(engine);

        // Âm thanh
        Sound engineSound = new EngineSound2();
        engineSound.load(assetManager);
        engine.setSound(engineSound);

        Sound hornSound = new HornSound1();
        hornSound.load(assetManager);
        setHornSound(hornSound);

        build();
    }

    @Override
    public void locateDashCam(Vector3f storeResult) {
        storeResult.set(0f, 1.3f, 0.6f);
    }

    @Override
    protected void locateTarget(Vector3f storeResult) {
        storeResult.set(0f, 0.5f, -2.5f);
    }
}
