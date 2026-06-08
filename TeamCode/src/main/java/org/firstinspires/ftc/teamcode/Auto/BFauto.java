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
//import org.firstinspires.ftc.teamcode.Limelight;


@Autonomous(name = "BFauto", group = "Autonomous")
public class BFauto extends LinearOpMode {

    private Follower follower; // Pedro Pathing follower

    private final Pose BFStart = new Pose(62.5, -15.5, Math.toRadians(180));
    private final Pose BFScore = new Pose(53, -10.5, Math.toRadians(210));
    private final Pose B1A = new Pose(24.25, -10, Math.toRadians(270));
    private final Pose B1C = new Pose(24.25, -40, Math.toRadians(270));
    private final Pose BFScore2 = new Pose(53, -10.5, Math.toRadians(205));
    private final Pose B2A = new Pose(0, -10, Math.toRadians(270));
    private final Pose B2C = new Pose(0, -40, Math.toRadians(270));
    private final Pose BFScore3 = new Pose(53, -10.5, Math.toRadians(200));


    private PathChain BscorePreload, Balign1, Bintake1, Bscore1, Balign2, Bintake2, Bscore2;


    private Pose currentPose; // Current pose of the robot
    static PoseHistory poseHistory;
    private Timer pathTimer, opModeTimer;

    Launcher Launch = new Launcher();
    org.firstinspires.ftc.teamcode.Subassys.Intake Intake = new Intake();
    Drivetrain Drive = new Drivetrain();

    private int pathState;

    public enum PathState {
        // START POSITION I GUESS ALSO END POSITION I DONT KNOW WHAT THIS GUY IS TALKING ABOUT
        // DRIVE > MOVEMENT
    }


//    private Path scorePre;
//    private PathChain grab1, score1;

    public void buildPaths() {
//        scorePreload = new PathChain(new BezierLine(BFStart, BFScore));
//        scorePre.setLinearHeadingInterpolation(start.getHeading(), score.getHeading());

        BscorePreload = follower.pathBuilder()
                .addPath(new BezierLine(BFStart, BFScore))
                .setLinearHeadingInterpolation(BFStart.getHeading(), BFScore.getHeading())
                .build();

        Balign1 = follower.pathBuilder()
                .addPath(new BezierLine(BFScore, B1A))
                .setLinearHeadingInterpolation(BFScore.getHeading(), B1A.getHeading())
                .build();

        Bintake1 = follower.pathBuilder()
                .addPath(new BezierLine(B1A, B1C))
                .setLinearHeadingInterpolation(B1A.getHeading(), B1C.getHeading())
                .build();

        Bscore1 = follower.pathBuilder()
                .addPath(new BezierLine(B1C, BFScore2))
                .setLinearHeadingInterpolation(B1C.getHeading(), BFScore2.getHeading())
                .build();
        Balign2 = follower.pathBuilder()
                .addPath(new BezierLine(BFScore2, B2A))
                .setLinearHeadingInterpolation(BFScore.getHeading(), B1A.getHeading())
                .build();

        Bintake2 = follower.pathBuilder()
                .addPath(new BezierLine(B2A, B2C))
                .setLinearHeadingInterpolation(B1A.getHeading(), B1C.getHeading())
                .build();

        Bscore2 = follower.pathBuilder()
                .addPath(new BezierLine(B2C, BFScore3))
                .setLinearHeadingInterpolation(B1C.getHeading(), BFScore2.getHeading())
                .build();


        // caffeine is the only thing that can numb my brain enough for this to be possible 💔💔💔
        // nobody can stop me from complaining here sighhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
        // robotics is fun but like this is really difficult ...
        // coding is probably the worst job to have on this team bc if we go to worlds its just straight code work

    }


    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // declare subassembly classes


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
        follower.setStartingPose(BFStart);

        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        GamepadStates newGamePad2 = new GamepadStates(gamepad2);


//        follower.setStartingPose(new Pose());
//
//        poseHistory = follower.getPoseHistory();

//        boolean red = false;
//        boolean done = false;

//        while(!done) {
//
//            newGamPad2.updateState();
//
//            if (newGamPad2.b.released) {
//                red = true;
//            }
//
//            if(newGamPad2.a.released) {
//                done  = true;
//            }
//        }

        buildPaths();

        //code for the auto phase
        waitForStart();


//
//        Fun.forwardForDist(12, .5);

        while (opModeIsActive()) {
            follower.update();
            currentPose = follower.getPose();
            bFStateMachine();
        }


    }

    public void bFStateMachine() {
        switch (pathState) {
            case 0:
                follower.followPath(BscorePreload, true);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    Launch.autoLaunchFar();
//                    Launch.autoLaunchSingle();
//                    Launch.autoLaunchFar();
                    Launch.stop();
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {

                    follower.followPath(Balign1, true);
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
                    follower.followPath(Bintake1, true);
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
                    follower.followPath(Bscore2, true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    Launch.autoLaunchFar();
//                    Launch.autoLaunchSingle();
//                    Launch.autoLaunchFar();
                    Launch.stop();
                    setPathState(9);
                }
                break;
//            case 8:
//                if (!follower.isBusy()) {
//                    follower.followPath(Balign1, true);
//                    setPathState(9);
//                }
//                break;
            case 9:
                if (!follower.isBusy()) {

                    follower.followPath(Balign2, true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!follower.isBusy()) {
                    Intake.intake(1);
                    setPathState(11);
                }
                break;
            case 11:
                if (!follower.isBusy()) {
                    follower.followPath(Bintake2, true);
                    setPathState(12);
                }
                break;
            case 12:
                if (!follower.isBusy()) {
                    Intake.stop();
                    setPathState(13);
                }
                break;
            case 13:
                if (!follower.isBusy()) {
                    follower.followPath(Bscore2, true);
                    setPathState(14);
                }
                break;
            case 14:
                if (!follower.isBusy()) {
                    Launch.autoLaunchFar();
//                    Launch.autoLaunchSingle();
//                    Launch.autoLaunchFar();
                    Launch.stop();
                    setPathState(15);
                }
                break;
            case 15:
                if (!follower.isBusy()) {
                    follower.followPath(Balign2, true);
                    setPathState(-1);
                }
                break;
        }
    }
}
