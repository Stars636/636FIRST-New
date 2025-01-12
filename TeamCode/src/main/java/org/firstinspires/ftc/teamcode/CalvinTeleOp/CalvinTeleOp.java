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

    public boolean changedBB = false;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);
        SlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        SlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");
        SlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        SlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        SlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        SlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        //calvin.checkHardwareInitialization(telemetry);
        calvin.initialPositions();
        //calvin.kindlyRelax();




        //Initial!!
        //we will create macros in the future, to remove room for error
        boolean buttonNew = false;
        boolean buttonNewer = false;

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //We need to fix the controls.
            // For your ease, all of the functions can take either a boolean or a double
            // so you don’t need to re code anything for the left and right triggers

            calvin.activateRotateElbow(gamepad2.b);
            //if rotateElbow still doesn't work use this
            /*if (gamepad2.b && !changedBB) {
                if (calvin.elbowLeft.getPosition() == Calvin.elbowInsidePosition) {
                    calvin.elbowOut();
                    changedBB = true;
                } else if (calvin.elbowLeft.getPosition() == Calvin.elbowOutsidePosition) {
                    calvin.elbowIn();
                    changedBB = true;
                } else {
                    calvin.elbowIn();
                    changedBB = true;
                }
            } else if (!gamepad2.b) {
                changedBB = false;
            }*/

            calvin.activateIntake(gamepad1.a);

            if(!gamepad1.a) {calvin.activateEject(gamepad1.right_stick_button);}

           calvin.activateSpecimen(gamepad2.x); //Hold for one second, the telemetry should agree

            calvin.activateFullExtension(gamepad2.left_trigger);

            calvin.activateClaw(gamepad2.y);

            calvin.activateExtendo(gamepad2.right_trigger);

            //calvin.activateClaw(gamepad2.y);

            calvin.activateScore(gamepad2.right_bumper);

            calvin.activateCollectIntake(gamepad2.left_bumper);

            calvin.activatePassiveOrInitial(gamepad2.dpad_up);

            //calvin.activateVerticalSlides(gamepad2.left_stick_y);
                if (SlidesRight.getCurrentPosition() < verticalSlideHighScoringPositionLimit && SlidesRight.getCurrentPosition() >= 0) {
                    SlidesLeft.setPower(-gamepad2.left_stick_y);
                    SlidesRight.setPower(-gamepad2.left_stick_y);
                } else if ( SlidesRight.getCurrentPosition() > 0) {
                    SlidesLeft.setPower(Math.min(-gamepad2.left_stick_y, 0));  // Only allow positive power
                    SlidesRight.setPower(Math.min(-gamepad2.left_stick_y, 0));
                } else if (SlidesRight.getCurrentPosition() < verticalSlideHighScoringPositionLimit) {
                    SlidesLeft.setPower(Math.max(-gamepad2.left_stick_y, 0));  // Only allow negative power
                    SlidesRight.setPower(Math.max(-gamepad2.left_stick_y, 0));
                }


           //calvin.driveMotors(calvin.leftFront, calvin.rightFront, calvin.leftBack, calvin.rightBack, gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);


            //calvin.cheat1(gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.a, gamepad1.b, gamepad1.x, gamepad1.y, gamepad1.right_bumper, gamepad1.left_bumper, gamepad1.right_stick_button, gamepad1.left_stick_button, gamepad1.options, gamepad1.start, gamepad1.right_trigger, gamepad1.left_trigger, telemetry);

            telemetry.addData("Scoring Mode:", calvin.scoringMode);
            telemetry.addData("Scoring Mode:", calvin.passiveOrInitialState);
            telemetry.addData("Slides Height:", SlidesLeft.getCurrentPosition());
            telemetry.addData("Slides Height:", SlidesRight.getCurrentPosition());
            telemetry.addData("calvin MotorPower Left", SlidesLeft.getPower());
            telemetry.addData("calvin MotorPower Right", SlidesRight.getPower());

            telemetry.addData("Claw",calvin.claw.getPosition());
            telemetry.addData("ElbowLeft",calvin.elbowLeft.getPosition());
            telemetry.addData("ElbowRight",calvin.elbowRight.getPosition());
            telemetry.addData("horizontalSlides",calvin.horizontalSlidesRight.getPosition());
            telemetry.addData("horizontalSlides",calvin.horizontalSlidesLeft.getPosition());
            telemetry.addData("shaq",calvin.shaq.getPosition());
            telemetry.addData("rotator",calvin.clawRotator.getPosition());
            telemetry.addData("BB",changedBB);
           //telemetry.addData("BB", calvin.changedB);
           // telemetry.addData("Inputs?", calvin.cheatCode1.inputTracker);



            telemetry.update();

        }

    }

}
/// horizontal slides easily blocked by clawrotator and shaq servos especially their wires
//screw them in more?

