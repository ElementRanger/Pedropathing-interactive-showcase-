package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Color {
    private LinearOpMode opmode = null;

    private ColorSensor Color;

    public Color() {
    }

    public void init(LinearOpMode opMode) {
        HardwareMap hwMap;
        opMode.telemetry.addLine("Initializing color sensor...");
        opMode.telemetry.update();

        opmode = opMode;
        hwMap = opMode.hardwareMap;

        Color = hwMap.get(ColorSensor.class,"Color");
    }

    public boolean isGreen() {
        if (Color.green() > 20) {
            return true;
        } else {
            return false;
        }
    }

    public int greenV() {
        return Color.green();
    }
}
