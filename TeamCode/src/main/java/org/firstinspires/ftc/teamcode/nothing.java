package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "nothing", group = "Teleop")
public class nothing extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException{


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("working");
            telemetry.update();
        }
    }
}
