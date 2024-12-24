package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CalvinFunctions.Functions;

@TeleOp
public class CalvinTeleOpEfficient extends LinearOpMode {

    private Functions functions;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        functions = new Functions(hardwareMap);

        functions.initialPositions();

        //These booleans are also for testing positions at small intervals at a time
        boolean changedRightTrigger = false;
        boolean changedLeftTrigger = false;
        boolean changedSlide = false;
        boolean changedA = false;
        boolean changedB = false;
        boolean changedRightBumper = false;
        boolean changedZhangCynthia = false;
        boolean changedY = false;
        //Initial!!

        functions.initialPositions();

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