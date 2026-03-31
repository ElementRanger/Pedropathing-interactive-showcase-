package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.bylazar.

public class Launcher {

    private DcMotorEx LLM = null;
    private DcMotorEx RLM = null;
    private LinearOpMode opmode = null;

    public Launcher() {
    }

    public void init(LinearOpMode opMode) {
        HardwareMap hwmap;

        opmode = opMode;
        hwmap = opMode.hardwareMap;

        LLM = (DcMotorEx) hwmap.dcMotor.get("LLM");
        RLM = (DcMotorEx) hwmap.dcMotor.get("RLM");


        LLM.setDirection(DcMotorSimple.Direction.REVERSE);
        RLM.setDirection(DcMotorSimple.Direction.FORWARD);

        LLM.setPower(0);
        RLM.setPower(0);
    }

    public void autoLaunch() {
        RLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RLM.setVelocity(3000);
    }

    public void manualLauncher(){
        RLM.setPower(1);
        LLM.setPower(1);
    }

    public void getV() {
        RLM.setPower(1);
        opmode.telemetry.addLine("Velocity: " + RLM.getVelocity());
        opmode.telemetry.update();
    }

    public void resetEncoders() {
        RLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LLM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runEncoders() {
//        LLM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stopEncoders() {
        LLM.setPower(0);
        RLM.setPower(0);
    }
    public void stop(){
        RLM.setPower(0);
        LLM.setPower(0);
    }
}