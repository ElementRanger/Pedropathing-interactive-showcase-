package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadStates;
import org.firstinspires.ftc.teamcode.Subassys.Drivetrain;
import org.firstinspires.ftc.teamcode.Subassys.Intake;
import org.firstinspires.ftc.teamcode.Subassys.Launcher;

@TeleOp(name = "state", group = "Teleop")

public class stateChamps extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain Train = new Drivetrain();
        Launcher Launcher = new Launcher();
        Intake Intake = new Intake();
        Train.init(this);
        Intake.init(this);
        Launcher.init(this);

        GamepadStates newGamePad1 = new GamepadStates(gamepad1);
        GamepadStates newGamePad2 = new GamepadStates(gamepad2);

        double speed = 0.7;

        waitForStart();

        while(opModeIsActive()) {

            newGamePad1.updateState();
            newGamePad2.updateState();

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

            if (gamepad2.left_stick_y > .4 || newGamePad2.left_trigger.state) {
                // grab ball
                Intake.intake(.8);
//                Intake.feedSlow();
                Launcher.stop();
            } else if ((gamepad2.left_stick_y < -.4) && gamepad2.dpad_down) {
                // expel ball
                Intake.reverse(0.75);
            } else if (newGamePad2.a.released) {
                Train.stop();
//                Launcher.closeLaunch(.55);
                Launcher.autoLaunchClose();
            } else if (newGamePad2.x.released) {
                Train.stop();
                Launcher.autoLaunchFar();
//                Launcher.farLaunch(0.67);
            } else if (newGamePad2.y.state) {
                Intake.Launch(0.5, 0.6);
            }
            // launcher control
            else if (newGamePad2.right_trigger.state) {
                Intake.Sort();
//                Launcher.manualLauncher(1050);
//                Launcher.getV();

            } else {
                Launcher.stop();
                Intake.FeedStop();
                Intake.stop();
            }
        }
    }
}
