package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class SuperDuperFinalScrimmage extends LinearOpMode{
    Servo rotatorJamal;
    Servo clawEthan;
    Servo pushRight;
    Servo pushLeft;

    Servo clawLeft;

    Servo clawRight;

    Servo clawMiddle;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();



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
        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        double verticalClawRight = 0.02;
        double verticalClawLeft = 0.98;// there is an ideal starting position. Let's test for that.
        double clawMiddlePosition = 0.05;

        //These booleans are for testing positions at small intervals at a time. I
        // Ideally by scrimmage these should be removed in favor of correct positions
        boolean changedRightTrigger = false;
        boolean changedLeftTrigger = false;
        boolean changedSlide = false;
        boolean changedA = false;
        boolean changedB = false;
        boolean changedRightBumper = false;
        boolean changedZhangCynthia = false;

        int sequenceRB = 0;


        // I currently don't have an efficient solution for the motor that controls the slides.
        //If you've thought of something tell me!!


        
        waitForStart();
        
        while (opModeIsActive()) {
            //Moves the rotator. RETEST these positions
            if (gamepad2.b && !changedA) {
                if (rotatorPosition == 0.88) {
                    rotatorPosition = 0.18;
                    changedA = true;
                } else if (rotatorPosition == 0.18) {
                    rotatorPosition = 0.88;
                    changedA = true;
                } else {
                    rotatorPosition = 0.88;
                    changedA = true;
                }

            } else if (!gamepad2.b) {
                changedA = false;
            }
            //Moves the intake claw.
            // The positions seem fine right now but alongside the vertical claw there may be issues
            if (gamepad2.a && !changedB) {
                if (intakeClawPosition == 0.515) {
                    intakeClawPosition = 0.40;
                    changedB = true;
                } else if (intakeClawPosition == 0.40) {
                    intakeClawPosition = 0.515;
                    changedB = true;
                } else {
                    intakeClawPosition = 0.515;
                    changedB = true;
                }
            } else if(!gamepad2.a){
                changedB = false;
            }
            //Pushes the extendo. Positions need to be tested.
            // Currently the servos make a high pitched noise that I can't stand so you may have to test this
            //The code works, I think they are slightly offset so the servos are working when they should be rested

            if (gamepad2.right_trigger != 0 && !changedRightTrigger) {
                if (pushPositionRight == 0.447) {
                    pushPositionRight = 0.55;
                    pushPositionLeft = 0.45;
                    changedRightTrigger = true;
                } else if (pushPositionRight == 0.55) {
                    pushPositionRight = 0.447;
                    pushPositionLeft = 0.553;
                    changedRightTrigger = true;
                } else {
                    pushPositionRight = 0.55;
                    pushPositionLeft = 0.45;
                    changedRightTrigger = true;
                }
            } else if (gamepad2.right_trigger == 0) {
                changedRightTrigger = false;
            }

            //code for vertical claw
            if (gamepad2.y && !changedRightBumper) {
                if (clawMiddlePosition == 0.48) {
                    clawMiddlePosition = 0.05;
                    changedRightBumper = true;
                } else if (clawMiddlePosition == 0.05) {
                    clawMiddlePosition = 0.48;
                    changedRightBumper = true;
                }
            } else if (!gamepad2.y) {
                changedRightBumper = false;
            }
            //should rotate the huge thingamajig at the top towards the basket
            if (gamepad2.left_trigger != 0 && !changedLeftTrigger) {
                if(verticalClawLeft == 0.983) {
                    verticalClawRight = 1;
                    verticalClawLeft = 0;
                    changedLeftTrigger = true;
                } else if (verticalClawLeft == 0) {
                    verticalClawRight = 0.017;
                    verticalClawLeft = 0.983;
                    changedLeftTrigger = true;
                } else {
                    verticalClawRight = 1;
                    verticalClawLeft = 0;
                }
            } else if(gamepad2.left_trigger == 0) {
                changedLeftTrigger = false;
            }
            //Transfer!! In a single motion

            if (gamepad2.right_bumper && !changedZhangCynthia) {
                if(sequenceRB == 0) {
                    clawMiddlePosition = 0.05;
                    verticalClawRight = 0.017;
                    verticalClawLeft = 0.983;
                    rotatorPosition = 0.18;
                    pushPositionRight = 0.447;
                    pushPositionLeft = 0.553;
                    sequenceRB++;
                    changedZhangCynthia = true;
                } else if (sequenceRB == 1) {
                    clawMiddlePosition = 0.48;
                    sequenceRB--;
                    changedZhangCynthia = true;
                }

            } else if (!gamepad2.right_bumper) {
                changedZhangCynthia = false;
            }
            if (gamepad2.left_bumper) {
                intakeClawPosition = 0.45;
                pushPositionRight = 0.55;
                pushPositionLeft = 0.45;
            }




            //messy code for the motor for the slides

            double constant = 540;
            // constant we used to make sure the values or revolutions and angles make sense
            double position = verticalSlide.getCurrentPosition();
            double revolutions = position/constant;
            double angle = revolutions * 360;
            double angleNormalized = angle % 360;
            double diameter = 3.5; // In cm
            double circumference = Math.PI * diameter;
            double distance = circumference * revolutions;



            if(gamepad2.dpad_up && !changedSlide) {
                int desiredPosition = 3614;
                verticalSlide.setTargetPosition(desiredPosition);
                verticalSlide.setPower(0.6); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                changedSlide = true;
            } else if (!gamepad2.dpad_up) {
                changedSlide = false;
            }
            if(gamepad2.dpad_down) {
                int desiredPosition = 1;
                // The position (in ticks) that you want the motor to move to
                verticalSlide.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setPower(0.6);
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                verticalClawRight = 0.02;
                verticalClawLeft = 0.98;

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
            //This part feels like clutter
           // telemetry.addData("Encoder Position", position);
            //telemetry.addData("Encoder Angle", angle);
            //telemetry.addData("Encoder Normal Angle", angleNormalized);
            //telemetry.addData("Encoder Revolution", revolutions);
            //telemetry.addData("Distance Traveled", distance);

          //Telemetry
            telemetry.addData("Slide Position via Encoder", verticalSlide.getCurrentPosition());
            telemetry.addData("Jamal Position", rotatorJamal.getPosition());
            telemetry.addData("Ethan Position", clawEthan.getPosition());
            telemetry.addData("pushLeft Position", pushLeft.getPosition());
            telemetry.addData("pushRight Position", pushRight.getPosition());
            telemetry.addData("Vertical", verticalClawRight);

            telemetry.addData("X", joystickX );
            telemetry.addData("Y", joystickY );
            telemetry.addData("R", joystickR);
            telemetry.update();

            idle();
        }
    }
}

