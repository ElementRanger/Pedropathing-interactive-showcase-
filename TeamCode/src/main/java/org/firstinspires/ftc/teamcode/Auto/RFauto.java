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

@Autonomous(name = "RFauto", group = "Autonomous")
public class RFauto extends LinearOpMode {

    private Follower follower; // Pedro Pathing follower

    private final Pose RFScore = new Pose(55, 11.5, Math.toRadians(160)); // may want to use 155
    private final Pose RFStart = new Pose(62.5, 9, Math.toRadians(180));
    private final Pose R1A = new Pose(24.25, 9, Math.toRadians(90));
    private final Pose R1C = new Pose(24.25, 36, Math.toRadians(90));
    private final Pose RFScore2 = new Pose(53, 10.5, Math.toRadians(160));
    private PathChain RscorePreload, Ralign1, Rintake1, Rscore2;


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
                .addPath(new BezierLine(RFStart, RFScore))
                .setLinearHeadingInterpolation(RFStart.getHeading(), RFScore.getHeading())
                .build();

        Ralign1 = follower.pathBuilder()
                .addPath(new BezierLine(RFScore, R1A))
                .setLinearHeadingInterpolation(RFScore.getHeading(), R1A.getHeading())
                .build();

        Rintake1 = follower.pathBuilder()
                .addPath(new BezierLine(R1A, R1C))
                .setLinearHeadingInterpolation(R1A.getHeading(), R1C.getHeading())
                .build();

        Rscore2 = follower.pathBuilder()
                .addPath(new BezierLine(R1C, RFScore2))
                .setLinearHeadingInterpolation(R1C.getHeading(), RFScore2.getHeading())
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
        follower.setStartingPose(RFStart);

        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        GamepadStates newGamePad2 = new GamepadStates(gamepad2);

        buildPaths();

        waitForStart();

        while (opModeIsActive()) {

            follower.update();
            currentPose = follower.getPose();

            rFStateMachine();


        }
    }

    private void rFStateMachine() {
        switch (pathState) {
            case 0:
                follower.followPath(RscorePreload, true);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {

                    Launch.autoLaunchFar();
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(Ralign1, true);
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
                    follower.followPath(Rintake1, true);
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
                    follower.followPath(Rscore2, true);
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
                    follower.followPath(Ralign1, true);
                    setPathState(-1);
                }
                break;

        }
    }
}



