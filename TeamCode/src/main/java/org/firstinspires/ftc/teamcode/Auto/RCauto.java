package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.util.PoseHistory;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.paths.*;
import com.pedropathing.util.*;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.GamepadStates;
import org.firstinspires.ftc.teamcode.Subassys.Intake;
import org.firstinspires.ftc.teamcode.Subassys.Launcher;
import org.firstinspires.ftc.teamcode.Subassys.Drivetrain;

@Autonomous(name = "RCauto", group = "Autonomous")
public class RCauto extends LinearOpMode {

    private Follower follower; // Pedro Pathing follower

    private final Pose RCScore = new Pose(-48, 0, Math.toRadians(135)); // may want to use 155
    private final Pose RCStart = new Pose(0, 0, Math.toRadians(135));
    private final Pose R3A = new Pose(36.5, -10, Math.toRadians(90));
    private final Pose R3C = new Pose(36.5, 13.5, Math.toRadians(90));
    private final Pose RCScore2 = new Pose(53, 10.5, Math.toRadians(160));
    private PathChain RscorePreload, Ralign3, Rintake3, Rscore3;


    private Pose currentPose; // Current pose of the robot
    static PoseHistory poseHistory;
    private Timer pathTimer, opModeTimer;

    Launcher Launch = new Launcher();
    org.firstinspires.ftc.teamcode.Subassys.Intake Intake = new Intake();

    private int pathState;

    public enum PathState {
        // START POSITION I GUESS ALSO END POSITION I DONT KNOW WHAT THIS GUY IS TALKING ABOUT
        // DRIVE > MOVEMENT
    }


//    private Path scorePre;
//    private PathChain grab1, score1;

    public void buildPaths() {

        RscorePreload = follower.pathBuilder()
                .addPath(new BezierLine(RCStart, RCScore))
                .setLinearHeadingInterpolation(RCStart.getHeading(), RCScore.getHeading())
                .build();

        Ralign3 = follower.pathBuilder()
                .addPath(new BezierLine(RCScore, R3A))
                .setLinearHeadingInterpolation(RCScore.getHeading(), R3A.getHeading())
                .build();

        Rintake3 = follower.pathBuilder()
                .addPath(new BezierLine(R3A, R3C))
                .setLinearHeadingInterpolation(R3A.getHeading(), R3C.getHeading())
                .build();

        Rscore3 = follower.pathBuilder()
                .addPath(new BezierLine(R3C, RCScore2))
                .setLinearHeadingInterpolation(R3C.getHeading(), RCScore2.getHeading())
                .build();


    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // declare subassembly classes
        Drivetrain Drive = new Drivetrain();

//        SensorTraining Sensor = new SensorTraining();
//        ServoTraining Servo = new ServoTraining();
//        Limelight Limelight = new Limelight();


        // names subassembly classes
        Drive.init(this);
        Intake.init(this);
//        Outtake.init(this);
//        Servo.init(this);
//        Limelight.init(this);
        Launch.init(this);


        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(RCStart);

        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        GamepadStates newGamePad2 = new GamepadStates(gamepad2);

        waitForStart();

        while (opModeIsActive()) {

            follower.update();
            currentPose = follower.getPose();
            buildPaths();
            rFStateMachine();


        }
    }

    private void rFStateMachine() {
        switch (pathState) {
            case 0:
                follower.followPath(RscorePreload, true);
                setPathState(11);
                break;
            case 1:
                if (!follower.isBusy()) {
                    Launch.autoLaunchFar();
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(Ralign3, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    Intake.intake(1);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(Rintake3, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    Intake.stop();
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(Rscore3, true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    Launch.autoLaunchFar();
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(Ralign3, true);
                    setPathState(-1);
                }
                break;

        }
    }
}
