package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.util.PoseHistory;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.paths.*;
import com.pedropathing.util.*;

import org.firstinspires.ftc.teamcode.Subassys.Intake;
import org.firstinspires.ftc.teamcode.Subassys.Launcher;
import org.firstinspires.ftc.teamcode.Subassys.Drivetrain;

@Autonomous
public class programTheRobot extends LinearOpMode {
    private Follower follower; // Pedro Pathing follower

    private final Pose ExamplePoint1 = new Pose(17.75, -17, Math.toRadians(225));
    private final Pose ExamplePoint2 = new Pose(0, 0, Math.toRadians(225));
    private PathChain ExamplePath;


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

        ExamplePath = follower.pathBuilder()
                .addPath(new BezierLine(ExamplePoint1, ExamplePoint2))
                .setLinearHeadingInterpolation(ExamplePoint1.getHeading(), ExamplePoint2.getHeading())
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

        // names subassembly classes
        Drive.init(this);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(ExamplePoint2);

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
                follower.followPath(ExamplePath);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    Launch.stop();
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    setPathState(-1);
                }
                break;

        }
    }
}
