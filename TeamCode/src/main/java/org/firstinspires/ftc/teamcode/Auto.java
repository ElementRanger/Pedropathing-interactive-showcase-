package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;


import com.pedropathing.util.PoseHistory;
import com.pedropathing.follower.Follower;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.math.*;
import com.pedropathing.paths.*;
import com.pedropathing.telemetry.SelectableOpMode;
import com.pedropathing.util.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Training;
import org.firstinspires.ftc.teamcode.GamepadStates;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.SensorTraining;
import org.firstinspires.ftc.teamcode.ServoTraining;
import org.firstinspires.ftc.teamcode.Limelight;
import org.firstinspires.ftc.teamcode.Functions;

@Autonomous(name = "Auto", group = "Autonomous")
public class Auto extends LinearOpMode {

    public static Follower follower;
    static PoseHistory poseHistory;


    @Override
    public void runOpMode() throws InterruptedException {
        // declare subassembly classes
        Training Drive = new Training();
//        Intake Intake = new Intake();
//        Outtake Outtake = new Outtake();
//        SensorTraining Sensor = new SensorTraining();
//        ServoTraining Servo = new ServoTraining();
//        Limelight Limelight = new Limelight();
        Functions Fun = new Functions();

        // names subassembly classes
        Drive.init(this);
//        Intake.init(this);
//        Outtake.init(this);
//        Servo.init(this);
//        Limelight.init(this);
        Fun.init(this);

//        follower.setStartingPose(new Pose());
//
//        poseHistory = follower.getPoseHistory();
//
        //code for the auto phase
        waitForStart();
//
//        Fun.forwardForDist(12, .5);

        while (opModeIsActive()) {
        Drive.forward(1);
        sleep(500);
        Drive.stop();
        break;
        }
    }
}
