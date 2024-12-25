package org.firstinspires.ftc.teamcode.LastYearsCode.TeleOp;



import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class my600PoundLifeMecanum extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        DcMotor motorLinearSlides1 = hardwareMap.dcMotor.get("motorLinearSlides1");
        DcMotor motorLinearSlides2 = hardwareMap.dcMotor.get("motorLinearSlides2");

        DcMotor activeIntake = hardwareMap.dcMotor.get("activeIntake");

        motorBackRight.setZeroPowerBehavior(BRAKE);
        motorBackLeft.setZeroPowerBehavior(BRAKE);
        motorFrontRight.setZeroPowerBehavior(BRAKE);
        motorFrontLeft.setZeroPowerBehavior(BRAKE);

        motorLinearSlides1.setZeroPowerBehavior(BRAKE);
        motorLinearSlides2.setZeroPowerBehavior(BRAKE);

        activeIntake.setZeroPowerBehavior(BRAKE);

        // first the pickup
        Servo leftPickup = hardwareMap.servo.get("leftPickup");
        Servo rightPickup = hardwareMap.servo.get("rightPickup");

        leftPickup.setDirection(Servo.Direction.FORWARD);
        rightPickup.setDirection(Servo.Direction.FORWARD);

        // next the airplane
        Servo airplane = hardwareMap.servo.get("airplane");
        airplane.setDirection(Servo.Direction.FORWARD);

        Servo twisty = hardwareMap.servo.get("twisty");
        twisty.setDirection(Servo.Direction.FORWARD);

        //next the 180 wrist
        Servo wristServo = hardwareMap.servo.get("wristServo");
        wristServo.setDirection(Servo.Direction.FORWARD);

        // finally the arm thingy
        Servo armServoRight = hardwareMap.servo.get("armServoRight");
        Servo armServoLeft = hardwareMap.servo.get("armServoLeft");

        // one of these will have to be reversed
        armServoRight.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setDirection(Servo.Direction.FORWARD);

        boolean rb_pressedLastIterationplane = false;
        boolean rb_toggleplane = false;
        boolean rb_pressedLastIteration = false;
        boolean rb_toggle = false;

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        airplane.setPosition(.65);
        waitForStart();

        telemetry.addLine("good luck fuckers!");
        telemetry.update();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.right_bumper) {
                motorFrontLeft.setPower(.6 * frontLeftPower);
                motorBackLeft.setPower(.6 * backLeftPower);
                motorFrontRight.setPower(.6 * frontRightPower);
                motorBackRight.setPower(.6 * backRightPower);
            } else {
                motorFrontLeft.setPower(frontLeftPower);
                motorBackLeft.setPower(backLeftPower);
                motorFrontRight.setPower(frontRightPower);
                motorBackRight.setPower(backRightPower);
            }

            if (gamepad2.left_bumper) {
                motorLinearSlides1.setPower(-gamepad2.left_stick_y);
                motorLinearSlides2.setPower(gamepad2.left_stick_y);
            }
//            else {
//                motorLinearSlides1.setPower(.1);
//                motorLinearSlides2.setPower(-.1);
//            }

            if (gamepad1.left_bumper) {
                activeIntake.setPower(-.4);
                twisty.setPosition(.15);
            } else if (gamepad1.a) {
                activeIntake.setPower(-.55);
                twisty.setPosition(.15);
            } else if (gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_left || gamepad1.dpad_right) {
                activeIntake.setPower(.5);
            } else {
                activeIntake.setPower(0);
                twisty.setPosition(1);
            }

            if (gamepad2.dpad_up) {
                armServoLeft.setPosition(armServoLeft.getPosition() - 0.005);
                armServoRight.setPosition(armServoRight.getPosition() + 0.005);
            }

            if (gamepad2.dpad_down) {
                armServoLeft.setPosition(armServoLeft.getPosition() + 0.005);
                armServoRight.setPosition(armServoRight.getPosition() - 0.005);
            }

            if (gamepad2.dpad_left) {
                wristServo.setPosition(wristServo.getPosition() - 0.005);
            }

            if (gamepad2.dpad_right) {
                wristServo.setPosition(wristServo.getPosition() + 0.005);
            }

            if (gamepad2.b) {
                leftPickup.setPosition(.5);
                rightPickup.setPosition(.18);
            }

            //gunner controls drop off, which is seperate for both
            if (gamepad2.x) {
                rightPickup.setPosition(.8);
            }

            if (gamepad2.a) {
                leftPickup.setPosition(1);
            }

            if (gamepad2.right_bumper && !rb_pressedLastIteration) {
                rb_toggle = !rb_toggle;
                rb_pressedLastIteration = true;
                if (rb_toggle) {
                    armServoLeft.setPosition(.88);
                    armServoRight.setPosition(.07);
                    wristServo.setPosition(.73);
                    rightPickup.setPosition(.18);
                    leftPickup.setPosition(.5);
                } else {
                    armServoLeft.setPosition(.1);
                    armServoRight.setPosition(.85);
                    wristServo.setPosition(.05);
                }
            } else if (!gamepad2.right_bumper) {
                rb_pressedLastIteration = false;
            }

            if (gamepad1.y && !rb_pressedLastIterationplane) {
                rb_toggleplane = !rb_toggleplane;
                rb_pressedLastIterationplane = true;
                if (rb_toggleplane) {
                    airplane.setPosition(.65);
                } else {
                    airplane.setPosition(.25);
                }
                //Toggle your motor state here.
            } else if (!gamepad1.y) {
                rb_pressedLastIterationplane = false;
            }
        }
    }
}
