package org.firstinspires.ftc.teamcode.Subassys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    //creates motor
    private DcMotor IntakeM = null;
    private DcMotor FeederM = null;
    private LinearOpMode opmode = null;

    public Intake() {
    }

    org.firstinspires.ftc.teamcode.Subassys.Launcher Launcher = new Launcher();

    public void init(LinearOpMode opMode) {

        HardwareMap hwMap;

        opmode = opMode;
        hwMap = opMode.hardwareMap;


        Launcher.init(opmode);

        //names the motor
        IntakeM = hwMap.dcMotor.get("IntakeM");
        FeederM = hwMap.dcMotor.get("FeederM");


        // sets the motors direction
        IntakeM.setDirection(DcMotorSimple.Direction.FORWARD);
        IntakeM.setDirection(DcMotorSimple.Direction.FORWARD);
        FeederM.setDirection(DcMotorSimple.Direction.REVERSE);


        // sets power to 0 to make sure nothing moves at first
        IntakeM.setPower(0);
        FeederM.setPower(0);


    }
    //creates intake functions
    public void intake(double speed){
        // when intake is called, moves the motor forward with power value 1
        IntakeM.setPower(speed);
    }
    public void reverse(double speed){
        // when intake is called, moves the motor reverse with power value 1
        IntakeM.setPower(-speed);
    }
    public void stop(){
        // stops the intake, obviously
        IntakeM.setPower(0);
    }
    public void Feed(){
        FeederM.setPower(0.4);
        // no witnesses
    }

    public void feedSlow() {
        FeederM.setPower(0.25);
    }

    public void FeedR() {
        FeederM.setPower(-0.2);
    }
    public void Sort(){
        FeederM.setPower(.5);
        IntakeM.setPower(.5);
        Launcher.sort();
        opmode.sleep(200);
        FeederM.setPower(0);
        Launcher.stop();
        opmode.sleep(200);
        IntakeM.setPower(0);
    }
    public void FeedStop(){
        FeederM.setPower(0);
    }

    public void Launch(double feedSpeed, double intakeSpeed) {
        FeederM.setPower(feedSpeed);
        IntakeM.setPower(intakeSpeed);
    }

    public void Bunch(double feedSpeed, double intakeSpeed){
        FeederM.setPower(-feedSpeed);
        IntakeM.setPower(intakeSpeed);
    }
    public void SLaunch() {
        FeederM.setPower(0);
        IntakeM.setPower(0);
    }


}
