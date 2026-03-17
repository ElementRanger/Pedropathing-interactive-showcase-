package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.util.PoseHistory;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.paths.*;
import com.pedropathing.util.*;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Functions;
import org.firstinspires.ftc.teamcode.GamepadStates;
import org.firstinspires.ftc.teamcode.Subassys.Intake;
import org.firstinspires.ftc.teamcode.Subassys.Launcher;
import org.firstinspires.ftc.teamcode.Subassys.Drivetrain;

@Autonomous(name = "BCauto", group = "Autonomous")
public class BCauto extends LinearOpMode {

    private Follower follower; // Pedro Pathing follower

    private final Pose BCScore = new Pose(17.75, -17, Math.toRadians(225)); // may want to use 155
    private final Pose BCStart = new Pose(0, 0, Math.toRadians(225));
    private final Pose B3A = new Pose(36.5, -10, Math.toRadians(90));
    private final Pose B3C = new Pose(36.5, 13.5, Math.toRadians(90));
    private final Pose BCScore2 = new Pose(53, 10.5, Math.toRadians(225));
    private PathChain BscorePreload, Balign3, Bintake3, Bscore3;


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

        BscorePreload = follower.pathBuilder()
                .addPath(new BezierLine(BCStart, BCScore))
                .setLinearHeadingInterpolation(BCStart.getHeading(), BCScore.getHeading())
                .build();

        Balign3 = follower.pathBuilder()
                .addPath(new BezierLine(BCScore, B3A))
                .setLinearHeadingInterpolation(BCScore.getHeading(), B3A.getHeading())
                .build();

        Bintake3 = follower.pathBuilder()
                .addPath(new BezierLine(B3A, B3C))
                .setLinearHeadingInterpolation(B3A.getHeading(), B3C.getHeading())
                .build();

        Bscore3 = follower.pathBuilder()
                .addPath(new BezierLine(B3C, BCScore2))
                .setLinearHeadingInterpolation(B3C.getHeading(), BCScore2.getHeading())
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

        Functions Fun = new Functions();

        // names subassembly classes
        Drive.init(this);
        Intake.init(this);
//        Outtake.init(this);
//        Servo.init(this);
//        Limelight.init(this);
        Launch.init(this);
        Fun.init(this);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(BCStart);

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
                follower.followPath(BscorePreload, true);
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
                    Launch.stop();
                    follower.followPath(Balign3, true);
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
                    follower.followPath(Bintake3, true);
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
                    follower.followPath(Bscore3, true);
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
                    Launch.stop();
                    follower.followPath(Balign3, true);
                    setPathState(-1);
                }
                break;

        }
    }
}

