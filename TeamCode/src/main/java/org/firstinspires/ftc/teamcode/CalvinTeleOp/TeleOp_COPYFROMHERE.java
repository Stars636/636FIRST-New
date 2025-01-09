package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;

@TeleOp
public class TeleOp_COPYFROMHERE extends LinearOpMode {





    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);


        waitForStart();

        calvin.checkHardwareInitialization(telemetry);


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            calvin.testInitial(gamepad1.a);

        }

    }



}