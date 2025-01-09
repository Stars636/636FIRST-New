package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.verticalSlideHighScoringPositionLimit;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;


@TeleOp
public class CalvinTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);
        waitForStart();

        calvin.checkHardwareInitialization(telemetry);
        calvin.initialPositions();
        calvin.kindlyRelax();
        calvin.verticalSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        calvin.verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Initial!!
        //we will create macros in the future, to remove room for error


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            //calvin.initialPositions();
            //calvin.cheat1(telemetry);
            //Moves the elbow. TEST these positions
            calvin.rotateElbow(gamepad2.b);
            //Activate  the intake
            //calvin.IntakeRight.setPower(1);
            calvin.activateIntake(gamepad2.a);
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

            //calvin.specimenPickupMacro(gamepad2.left_bumper, gamepad2.right_bumper,telemetry);

            calvin.passiveOrInitial(gamepad2.dpad_up);

            //calvin.activateVerticalSlides(gamepad2.left_stick_y);
            /*if (gamepad2.left_stick_y != 0) {
                if (calvin.verticalSlidesLeft.getCurrentPosition() < verticalSlideHighScoringPositionLimit && calvin.verticalSlidesLeft.getCurrentPosition() >= 0) {
                    calvin.verticalSlidesLeft.setPower(gamepad2.left_stick_y);
                    calvin.verticalSlidesRight.setPower(gamepad2.left_stick_y);
                } else if ( calvin.verticalSlidesLeft.getCurrentPosition() < 0) {
                    calvin.verticalSlidesLeft.setPower(Math.max(gamepad2.left_stick_y, 0));  // Only allow positive power
                    calvin.verticalSlidesRight.setPower(Math.max(gamepad2.left_stick_y, 0));
                } else if ( calvin.verticalSlidesLeft.getCurrentPosition() > verticalSlideHighScoringPositionLimit) {
                    calvin.verticalSlidesLeft.setPower(Math.min(gamepad2.left_stick_y, 0));  // Only allow negative power
                    calvin.verticalSlidesRight.setPower(Math.min(gamepad2.left_stick_y, 0));
                }
            }*/
            calvin.verticalSlidesLeft.setPower(gamepad2.left_stick_y);
            calvin.verticalSlidesRight.setPower(-gamepad2.left_stick_y);

            calvin.switchScoring(gamepad1.x);



           //calvin.driveMotors(calvin.leftFront, calvin.rightFront, calvin.leftBack, calvin.rightBack, gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;


            calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
            calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
            calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
            calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);

            telemetry.addData("Scoring Mode:", calvin.scoringMode);
            telemetry.addData("Slides Height:", calvin.verticalSlidesLeft.getCurrentPosition());
            telemetry.addData("Specimen Macro Step", calvin.specimenPickupState);
            telemetry.update();

        }

    }

}