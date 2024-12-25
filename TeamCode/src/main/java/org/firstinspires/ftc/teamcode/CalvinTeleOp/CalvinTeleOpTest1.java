package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;

@TeleOp
public class CalvinTeleOpTest1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.checkHardwareInitialization(telemetry);
        calvin.initialPositions();
        //Initial!!
        //we will create macros in the future, to remove room for error
        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {


            calvin.cheat1(telemetry);
            //Moves the elbow. TEST these positions
            calvin.rotateElbow(gamepad2.b);
            //Activate  the intake
            calvin.activateIntake(gamepad2.a);
            //Reverse. I chose this button(because the old bot had no equivalent), so if you want another one then do it
            calvin.activateEject(gamepad2.x);
            //Pushes the extendo. Positions need to be tested.
            //Test these positionsssss
            calvin.activateExtendo(gamepad2.right_trigger);
            //code for moving claw
            calvin.activateClaw(gamepad2.y);
            //rotates the claw. there is much better way to do this, but this works for now
            //you have to continuously hold to dunk it
            calvin.activateClawRotator(gamepad2.left_trigger);

            calvin.specimenPickupMacro(gamepad2.left_bumper, gamepad2.right_bumper);


            calvin.passiveOrInitial(gamepad2.dpad_up);

            calvin.activateVerticalSlides(gamepad2.left_stick_y);



            calvin.driveMotors(calvin.leftFront, calvin.rightFront, calvin.leftBack, calvin.rightBack, gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        }

    }

}