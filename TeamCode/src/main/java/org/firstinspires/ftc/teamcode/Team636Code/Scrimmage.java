package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class Scrimmage extends LinearOpMode{
    Servo rotatorJamal;
    Servo clawEthan;
    Servo pushRight;
    Servo pushLeft;

    Servo clawLeft;

    Servo clawRight;

    Servo clawMiddle;
    @Override
    public void runOpMode() throws InterruptedException {
        //Initializing all the motors. Do not change this unless we change the wiring
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        DcMotor verticalSlide = hardwareMap.get(DcMotor.class,"verticalSlide");
        // Reset the motor encoder for the slides.
        verticalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        //correcting the motors
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //Initializing all of the servos
        rotatorJamal = hardwareMap.get(Servo.class,"Servo1");
        clawEthan = hardwareMap.get(Servo.class,"Servo2");
        pushRight = hardwareMap.get(Servo.class,"pushRight");
        pushLeft = hardwareMap.get(Servo.class,"pushLeft");
        clawLeft = hardwareMap.get(Servo.class,"clawLeft");
        clawRight = hardwareMap.get(Servo.class,"clawRight");

        clawMiddle = hardwareMap.get(Servo.class,"clawMiddle");

        //Initial position of all the servos. When the wiring is perfected, RETEST all initial positions
        //There is definitely a better position for all of these
        double rotatorPosition = 0.1; //should start facing the claw
        double intakeClawPosition = 0.45; //should start open
        double pushPositionRight = 0.5; //1 is moving forward
        double pushPositionLeft = 0.5; // 0 is moving forward
        double verticalClawRight = 0;
        double verticalClawLeft = 1;// there is an ideal starting position. Let's test for that.
        double clawMiddlePosition = 0.05;

        //These booleans are for testing positions at small intervals at a time. I
        // Ideally by scrimmage these should be removed in favor of correct positions
        boolean changedR = false;
        boolean changedL = false;
        boolean changedVerticalClaw = false;
        boolean changedSlide = false;
        boolean changedCleave = false;
        // I currently don't have an efficient solution for the motor that controls the slides.
        //If you've thought of something tell me!!
        ElapsedTime mStateTime = new ElapsedTime();
        int v_state = 0;

        
        waitForStart();
        
        while (opModeIsActive()) {
            //Moves the rotator. RETEST these positions
            if (gamepad1.a) {
                rotatorPosition = 0.88;
            }
            if (gamepad1.b) {
                rotatorPosition = 0.16;
            }
            //Moves the intake claw.
            // The positions seem fine right now but alongside the vertical claw there may be issues
            if (gamepad1.x) {
                intakeClawPosition = 0.51500;
            }
            if (gamepad1.y) {
                intakeClawPosition = 0.40;
            }
            //Pushes the extendo. Positions need to be tested.
            // Currently the servos make a high pitched noise that I can't stand so you may have to test this
            //The code works, I think they are slightly offset so the servos are working when they should be rested
            if (gamepad1.right_trigger != 0 && pushPositionRight < 1 && !changedR) {
                pushPositionRight = 0.43;
                pushPositionLeft = 0.57;
                changedR = true;
            }else if(gamepad1.right_trigger == 0) {
                changedR = false;
            }
            if (gamepad1.left_trigger != 0 && pushPositionRight > 0  && !changedL) {
                pushPositionRight = 0.55;
                pushPositionLeft = 0.45;
                changedL = true;
            } else if(gamepad1.left_trigger == 0) {
                changedL = false;
            }

            //code for middle claw
            if (gamepad1.left_bumper) {
                clawMiddlePosition = 0.05;
            }
            if (gamepad1.right_bumper) {
                clawMiddlePosition = 0.48;
            }

            //should rotate the huge thingamajig at the top towards the basket
            if (gamepad1.dpad_left && !changedCleave ) {
                verticalClawRight = 0;
                verticalClawLeft = 1;
                changedCleave = true;
            } else if (!gamepad1.dpad_left) {
                changedCleave = false;
            }
            if (gamepad1.dpad_right && !changedCleave ) {
                verticalClawRight = 1;
                verticalClawLeft = 0;
                changedCleave = true;
            } else if (!gamepad1.dpad_right) {
                changedCleave = false;
            }
            //Transfer!! In a single motion
            if (gamepad1.left_stick_button) {
                clawMiddlePosition = 0.05;
                verticalClawRight = 0.07;
                verticalClawLeft = 0.93;
                rotatorPosition = 0.16;
                pushPositionRight = 0.44;
                pushPositionLeft = 0.56;
            }
            if (gamepad1.right_stick_button) {
                clawMiddlePosition = 0.48;
                sleep(500);
                intakeClawPosition = 0.45;
                sleep(500);
                pushPositionRight = 0.55;
                pushPositionLeft = 0.45;

            }



            //messy code for the motor for the slides.

            double constant = 540;
            // constant we used to make sure the values or revolutions and angles make sense
            double position = verticalSlide.getCurrentPosition();
            double revolutions = position/constant;
            double angle = revolutions * 360;
            double angleNormalized = angle % 360;
            double diameter = 3.5; // In cm
            double circumference = Math.PI * diameter;
            double distance = circumference * revolutions;

            if(gamepad1.dpad_up && !changedSlide) {
                int desiredPosition = 3614;
                verticalSlide.setTargetPosition(desiredPosition);
                verticalSlide.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                changedSlide = true;
            } else if (!gamepad1.dpad_up) {
                changedSlide = false;
            }
            if(gamepad1.dpad_down) {

                int desiredPosition = 1;
                // The position (in ticks) that you want the motor to move to
                verticalSlide.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setPower(0.5);
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }


            //natural code for driving
            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;


            rightFront.setPower(joystickY - joystickX - joystickR);
            leftFront.setPower(joystickY + joystickX + joystickR);
            rightBack.setPower(joystickY + joystickX - joystickR);
            leftBack.setPower(joystickY - joystickX + joystickR);

            //Moves the servos
            rotatorJamal.setPosition(rotatorPosition);
            clawEthan.setPosition(intakeClawPosition);
            pushLeft.setPosition(pushPositionLeft);
            pushRight.setPosition(pushPositionRight);
            clawRight.setPosition(verticalClawRight);
            clawLeft.setPosition(verticalClawLeft);

            clawMiddle.setPosition(clawMiddlePosition);

            //Telemetry
            telemetry.addData("Encoder Position", position);
            telemetry.addData("Encoder Angle", angle);
            telemetry.addData("Encoder Normal Angle", angleNormalized);
            telemetry.addData("Encoder Revolution", revolutions);
            telemetry.addData("Distance Traveled", distance);

          
            telemetry.addData("Slide Position via Encoder", verticalSlide.getCurrentPosition());
            telemetry.addData("Jamal Position", rotatorJamal.getPosition());
            telemetry.addData("Ethan Position", clawEthan.getPosition());
            telemetry.addData("pushLeft Position", pushLeft.getPosition());
            telemetry.addData("pushRight Position", pushRight.getPosition());
            telemetry.addData("Vertical", verticalClawRight);
            telemetry.update();

            idle();
        }
    }
}

