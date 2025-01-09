package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.verticalSlideHighScoringPositionLimit;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;


@TeleOp
public class CalvinTeleOp extends LinearOpMode {

    DcMotorImplEx SlidesLeft;
    DcMotorImplEx SlidesRight;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);
        SlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        SlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");
        waitForStart();

        //calvin.checkHardwareInitialization(telemetry);
        calvin.initialPositions();
        //calvin.kindlyRelax();
        SlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        SlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlidesRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //Initial!!
        //we will create macros in the future, to remove room for error


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {


            calvin.activateRotateElbow(gamepad2.b);


            calvin.activateIntake(gamepad2.a);

            calvin.activateSwitchScoring(gamepad2.x); //Hold for one second, the telemetry should agree

            calvin.activateClaw(gamepad2.y);

            //Reverse. I chose this button(because the old bot had no equivalent), so if you want another one then do it
            calvin.activateEject(gamepad2.right_stick_button);
            //Pushes the extendo. Positions need to be tested.
            //Test these positionsssss
            calvin.activateExtendo(gamepad2.right_trigger);
            //code for moving claw
            calvin.activateClaw(gamepad2.y);
            //rotates the claw. there is much better way to do this, but this works for now
            //you have to continuously hold to dunk it
            calvin.activateClawRotator(gamepad2.left_trigger);

            calvin.activatePassiveOrInitial(gamepad2.dpad_up);

            //calvin.activateVerticalSlides(gamepad2.left_stick_y);
            if (gamepad2.left_stick_y != 0) {
                if (SlidesLeft.getCurrentPosition() < verticalSlideHighScoringPositionLimit && SlidesLeft.getCurrentPosition() >= 0) {
                    SlidesLeft.setPower(gamepad2.left_stick_y);
                    SlidesRight.setPower(gamepad2.left_stick_y);
                } else if ( SlidesLeft.getCurrentPosition() < 0) {
                    SlidesLeft.setPower(Math.max(gamepad2.left_stick_y, 0));  // Only allow positive power
                    SlidesRight.setPower(Math.max(gamepad2.left_stick_y, 0));
                } else if (SlidesLeft.getCurrentPosition() > verticalSlideHighScoringPositionLimit) {
                    SlidesLeft.setPower(Math.min(gamepad2.left_stick_y, 0));  // Only allow negative power
                    SlidesRight.setPower(Math.min(gamepad2.left_stick_y, 0));
                }
            }






           //calvin.driveMotors(calvin.leftFront, calvin.rightFront, calvin.leftBack, calvin.rightBack, gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;


            calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
            calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
            calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
            calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);

            telemetry.addData("Scoring Mode:", calvin.scoringMode);
            telemetry.addData("Scoring Mode:", calvin.passiveOrInitialState);
            telemetry.addData("Slides Height:", calvin.verticalSlidesLeft.getCurrentPosition());

            telemetry.addData("calvin MotorPower Left", calvin.verticalSlidesLeft.getPower());
            telemetry.addData("calvin MotorPower Right", calvin.verticalSlidesRight.getPower());


            telemetry.addData("MotorPower Left", SlidesLeft.getPower());
            telemetry.addData("MotorPower Right", SlidesRight.getPower());

            telemetry.update();

        }

    }

}