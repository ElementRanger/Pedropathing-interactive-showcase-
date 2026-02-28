package org.firstinspires.ftc.teamcode.Subassys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.bylazar.

public class Launcher {

    private DcMotorEx LLM;
    private DcMotorEx RLM;
    private LinearOpMode opmode = null;

    private ElapsedTime runtime = new ElapsedTime();

    public Launcher() {
    }

    org.firstinspires.ftc.teamcode.Subassys.Intake Intake = new Intake();

    public void init(LinearOpMode opMode) {
        HardwareMap hwmap;

        opmode = opMode;
        hwmap = opMode.hardwareMap;

        LLM = (DcMotorEx) hwmap.dcMotor.get("LLM");
        RLM = (DcMotorEx) hwmap.dcMotor.get("RLM");


        LLM.setDirection(DcMotorSimple.Direction.FORWARD);
        RLM.setDirection(DcMotorSimple.Direction.REVERSE);

        LLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LLM.setPower(0);
        RLM.setPower(0);

        double velocity = LLM.getVelocity();

        Intake.init(opMode);
    }

    public void autoLaunch() {
        RLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RLM.setVelocity(950);
        LLM.setVelocity(950);

//        Intake.Launch(1,1);
    }

    public void manualLauncher(double velocity) {
//        RLM.setPower(.67);
//        LLM.setPower(.67);
        RLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RLM.setVelocity(velocity);
        LLM.setVelocity(velocity);
        // maybe 1050?
        // it is 1050 lol
    }

    public void getV() {
        opmode.telemetry.addLine("RLM Velocity: " + RLM.getVelocity());
        opmode.telemetry.addLine("LLM Velocity: " + LLM.getVelocity());
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

    public void stop() {
        RLM.setPower(0);
        LLM.setPower(0);
    }

    public void autoLaunchFar() {
//        Intake.FeedR();
        launch(1050);
        opmode.sleep(100);
        launch(1050);
        opmode.sleep(100);
        launch(1050);
        opmode.sleep(250);
        stop();
    }

    public void autoLaunchClose() {
//        Intake.FeedR();
        launch(950);
        opmode.sleep(100);
        launch(950);
        opmode.sleep(100);
        launch(950);
        opmode.sleep(250);
        stop();
    }

    public void farLaunch(double speed) {

        // run feeder backwards
        Intake.FeedR();
        opmode.sleep(250);

        //set motor speed
        RLM.setPower(speed);
        LLM.setPower(speed);

        //wait for spinup
        opmode.sleep(600);

        //launch an artifact
        Intake.Launch(0.4, 0.8);
        opmode.sleep(225);
        Intake.FeedStop();

        //wait for spinup
        opmode.sleep(475);

        //launch an artifact
        Intake.Launch(0.4, 0.8);
        opmode.sleep(300);
        Intake.FeedStop();

        //wait for spinup
        opmode.sleep(475);

        //launch an artifact
        Intake.Launch(0.4, 0.8);
        opmode.sleep(300);
        Intake.FeedStop();
        Intake.stop();

        opmode.sleep(100);
        RLM.setPower(0);
        LLM.setPower(0);
    }

    public void closeLaunch(double speed) {

        // run feeder backwards
        Intake.FeedR();
        opmode.sleep(350);

        //set motor speed
        RLM.setPower(speed);
        LLM.setPower(speed);

        //wait for spinup
        opmode.sleep(600);

        //launch an artifact
        Intake.Launch(0.4, 0.8);
        opmode.sleep(200);
        Intake.FeedStop();

        //wait for spinup
        opmode.sleep(500);

        //launch an artifact
        Intake.Launch(0.4, 0.8);
        opmode.sleep(300);
        Intake.FeedStop();

        //wait for spinup
        opmode.sleep(500);

        //launch an artifact
        Intake.Launch(0.4, 0.8);
        opmode.sleep(300);
        Intake.FeedStop();
        Intake.stop();

        opmode.sleep(250);
        RLM.setPower(0);
        LLM.setPower(0);
    }

    private void launch(double velocity) {
        manualLauncher(velocity);
        opmode.sleep(750);
        while (opmode.opModeIsActive() && !isAtVelocity(velocity)) {
            opmode.telemetry.addData("SpinUp isAtVelocity is", isAtVelocity(velocity));
            opmode.telemetry.addLine("RLM Velocity: " + RLM.getVelocity());
            opmode.telemetry.addLine("LLM Velocity: " + LLM.getVelocity());
            opmode.telemetry.update();
        }
        runtime.reset();
        while (opmode.opModeIsActive() && RLM.getVelocity() > (velocity - 50) && LLM.getVelocity() > (velocity - 50)
        && runtime.milliseconds() < 2500) {
            Intake.intake(.9);
            Intake.Feed();
        }
        Intake.stop();
        Intake.FeedStop();
    }


//    public void publicExecution(double speed){
//        Intake.intake(1);
//        Intake.Feed();
//        RLM.setPower(1);
//        LLM.setPower(1);
//    }



    public boolean isAtVelocity(double targetVelocity) {
        double tolerence = 12.0;
        boolean rightRdy = false;
        boolean leftRdy = false;
        leftRdy = Math.abs(LLM.getVelocity() - targetVelocity) <= tolerence;
        rightRdy = Math.abs(RLM.getVelocity() - targetVelocity) <= tolerence;
        if (rightRdy == true && leftRdy == true) {
            return true;
        } else {
            return false;
        }

    }

}