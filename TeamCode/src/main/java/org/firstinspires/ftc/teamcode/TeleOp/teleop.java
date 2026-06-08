package org.firstinspires.ftc.teamcode.TeleOp;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadStates;
import org.firstinspires.ftc.teamcode.Subassys.Drivetrain;
import org.firstinspires.ftc.teamcode.Subassys.Intake;
import org.firstinspires.ftc.teamcode.Subassys.Launcher;
//import com.bylazar.


 // this is the thing that we run
@TeleOp(name = "Teleop", group = "Teleop")
public class teleop extends LinearOpMode {

    View relativeLayout;

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException
    {

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // declare subassembly classes
        Drivetrain Train = new Drivetrain();
        Launcher Launcher = new Launcher();
        Intake Intake = new Intake();

        // Is this still/will be needed?
//        ServoTraining Servo = new ServoTraining();

////        Limelight LL = new Limelight();
//        Color Color = new Color();

        // initialize subassembly classes
        Train.init(this);
        Intake.init(this);
        Launcher.init(this);
//        Servo.init(this);
//        LL.init(this);
//        Color.init(this);

        //List fiducialResult;

        //limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        telemetry.setMsTransmissionInterval(11);
//        limelight.pipelineSwitch(7);
//        limelight.start();

        GamepadStates newGamePad1 = new GamepadStates(gamepad1);
        GamepadStates newGamePad2 = new GamepadStates(gamepad2);

//        LL.getStatus();

        double speed = 0.7;

        waitForStart();

        while (opModeIsActive()) {

            newGamePad1.updateState();
            newGamePad2.updateState();

//            LL.detectPattern();

            // controls movement

            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0) {
                if (newGamePad1.left_trigger.state) {
                    Train.multi(-gamepad1.left_stick_y / 2, gamepad1.left_stick_x / 2,
                            gamepad1.right_stick_x / 2);
                } else {
                    Train.multi(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
                }
            } else {
                Train.stop();
            }

//            if (gamepad1.left_stick_y < -.4) {
//                // run the forward function from Functions program
//                Train.forward(speed);
//            } else if (gamepad1.left_stick_y > .4) {
//                // backwards
//                Train.backwards(speed);
//            } else if (gamepad1.left_stick_x < -.4) {
//                // Strafe left
//                Train.StraffLeft(speed);
//            } else if (gamepad1.left_stick_x > .4) {
//                // Strafe right
//                Train.StraffRight(speed);
//            } else if (gamepad1.right_stick_x < -.4) {
//                // left
//                Train.left(speed);
//            } else if (gamepad1.right_stick_x > .4) {
//                // right
//                Train.right(speed);
//            } else {
//                // run the stop function from training
//                Train.stop();
//            }


            //initialize speed as a variable
            telemetry.addData("Speed: ", speed);

//            if (Color.isGreen()) {
//                relativeLayout.setBackgroundColor(Color.greenV());
//            } else {
//                relativeLayout.setBackgroundColor(-1);
//            }

            // intake control
            if (gamepad2.left_stick_y > .4 || newGamePad2.left_trigger.state) {
                // grab ball
                Intake.intake(.8);
//                Intake.feedSlow();
                Intake.intake(.9);
                Launcher.stop();

            } else if ((gamepad2.left_stick_y < -.8) || gamepad2.dpad_down) {
                // expel ball
                Intake.reverse(0.75);
            } else if (newGamePad2.a.state) {
                Train.stop();
                Launcher.autoLaunchClose();
            } else if (newGamePad2.x.released) {
                Train.stop();
                Launcher.autoLaunchFar();
            } else if (newGamePad2.y.state) {
                Intake.Launch(0.5, 0.6);
            } else if (newGamePad2.b.state) {
                Launcher.publicExecution();
            }

            // launcher control
            else if (newGamePad2.right_trigger.state) {
                Launcher.manualLauncher(1050);
//                Launcher.getV();
            } else {
                Launcher.stop();
                Intake.FeedStop();
                Intake.stop();
            }

            // speed control
            // decrease drive speed variable
            if (newGamePad1.left_bumper.released) {
                // when left bumper is pressed, slows down movement universally
                speed -= (0.1);
                if (speed <= 0.1) {
                    speed = 0.1;
                }
                //increase speed variable
            } else if (newGamePad1.right_bumper.released) {
                // when right bumper is pressed, speeds up movement universally
                speed += (0.1);
                if (speed >= 1) {
                    speed = (1);
                }
            }

//            LLResult result = limelight.getLatestResult();
//            LL.getResult();
        }
    }
}