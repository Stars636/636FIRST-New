package org.firstinspires.ftc.teamcode.BackUps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CalvinFunctions.Calvin;

@TeleOp
public class CalvinPositionTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin functions = new Calvin(hardwareMap, telemetry);

        functions.initialPositions();
        //Initial!!
        //we will create macros in the future, to remove room for error
        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //Moves the elbow. TEST these positions
            functions.rotateElbow(gamepad2.b);

            //Activate  the intake

            functions.activateIntake(gamepad2.a);
            //Reverse. I chose this button(because the old bot had no equivalent), so if you want another one then do it
            functions.activateEject(gamepad2.x);
            //Pushes the extendo. Positions need to be tested.
            //Test these positionsssss
            functions.activateExtendo(gamepad2.right_trigger);
            //code for moving claw
            functions.activateClaw(gamepad2.y);
            //rotates the claw. there is much better way to do this, but this works for now
            //you have to continuously hold to dunk it
            functions.activateClawRotator(gamepad2.left_trigger);

            functions.activateVerticalSlides(gamepad2.left_stick_y);

            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;

            functions.rightFront.setPower(joystickY - joystickX - joystickR);
            functions.leftFront.setPower(joystickY + joystickX + joystickR);
            functions.rightBack.setPower(joystickY + joystickX - joystickR);
            functions.leftBack.setPower(joystickY - joystickX + joystickR);


        }

    }




}