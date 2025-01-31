package org.firstinspires.ftc.teamcode.AStates.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

@Disabled
//remove fdeprecated
@TeleOp
public class TeleOpTester3 extends LinearOpMode {

    Gamepad lastGamepad1 = new Gamepad(), lastGamepad2 = new Gamepad();





    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();
        Calvin calvin = new Calvin(hardwareMap);

        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //if something doesn't work start here
            telemetry.addData("gamepad a", gamepad2.a);
            telemetry.addData("gamepad right trigger", gamepad2.right_trigger);
            telemetry.addData("gamepad right stick button", gamepad2.right_stick_button);
            telemetry.addData("gamepad dpad left", gamepad2.dpad_left);
            telemetry.update();

        }

    }



}