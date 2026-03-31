package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    //intalise motors
    private DcMotorEx OuttakeRM = null;
    private DcMotorEx OuttakeLM = null;

    static final double     COUNTS_PER_MOTOR_REV    = 28 ;

    private LinearOpMode opmode = null;

    public void init(LinearOpMode opMode) {
        HardwareMap hwMap;

        opmode = opMode;
        hwMap = opMode.hardwareMap;

        //names the motors
        OuttakeRM = hwMap.get(DcMotorEx.class, "OuttakeRM");
        OuttakeLM = hwMap.get(DcMotorEx.class, "OuttakeLM");

        //sets motor directions
        OuttakeRM.setDirection(DcMotorSimple.Direction.FORWARD);
        OuttakeLM.setDirection(DcMotorSimple.Direction.REVERSE);

        // power set to 0 as usual
        OuttakeRM.setPower(0);
        OuttakeLM.setPower(0);

        // allows to run motors for amounts
        OuttakeRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        OuttakeLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //creates functions
    public void outtake() {

        // starts the outtake motors when called ^w^
        OuttakeRM.setVelocity(2800);
        OuttakeLM.setVelocity(2800);
    }

    public void outtakestop() {
        // unsurprisingly stops the outtake
        OuttakeRM.setPower(0);
        OuttakeLM.setPower(0);


    }
}
