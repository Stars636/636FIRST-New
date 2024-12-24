package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class BackUpTeleOp extends LinearOpMode {
    CRServo continuousIntakeLeft;
    CRServo continuousIntakeRight;
    Servo claw;
    Servo shaq;
    Servo clawRotator;

    Servo elbow;
    Servo intakeRotator;

    Servo horizontalSlidesLeft;
    Servo horizontalSlidesRight;

    DcMotor rightBack;

    DcMotor rightFront;

    DcMotor leftBack;

    DcMotor leftFront;

    DcMotor verticalSlidesRight;

    DcMotor verticalSlidesLeft;


    public static double clawOpenPosition;
    public static double clawClosedPosition;


    public static double clawPassivePositionRight;

    public static double clawPassiveRotation;

    public static double clawRetrievePositionRight;

    public static double clawPickUpRotation;

    public static double clawScorePositionRight;

    public static double clawScoreRotation;

    public static double elbowInsidePositionRight;
    public static double elbowOutsidePositionRight;

    public static double intakePassiveRotation;

    public static double intakeActiveRotation;

    public static double intakePickUpRotation;
    public static int verticalSlideHighScoringPositionLimit; //kindly note that gunner will use joystick

    public static double horizontalSlidesInitialPositionLeft;
    public static double horizontalSlidesInitialPositionRight;

    public static double horizontalSlidesExtendedPositionLeft;
    public static double horizontalSlidesExtendedPositionRight;


    public static double specimenPickupPositionRight;
    public static int specimenStartPickupVerticalSlides;

    public static double specimenClawRotation;
    public static int specimenFinishPickupVerticalSlides;
    public static int specimenStartDepositVerticalSlides;
    public static int specimenFinishDepositVerticalSlides;



    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        //Initializing all the motors. Do not change this unless we change the wiring
        rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        leftFront = hardwareMap.get(DcMotor.class,"leftFront");

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        verticalSlidesLeft = hardwareMap.get(DcMotor.class,"verticalSlidesLeft");
        verticalSlidesRight = hardwareMap.get(DcMotor.class,"verticalSlidesRight");
        verticalSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        verticalSlidesRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        verticalSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setDirection(DcMotorSimple.Direction.REVERSE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        horizontalSlidesLeft = hardwareMap.get(Servo.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(Servo.class,"horizontalSlidesRight");
        continuousIntakeLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft"); //setPower
        continuousIntakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        claw = hardwareMap.get(Servo.class,"claw");
        shaq = hardwareMap.get(Servo.class,"shaq");
        intakeRotator = hardwareMap.get(Servo.class,"intakeRotator");
        elbow = hardwareMap.get(Servo.class,"elbow");
        intakeRotator = hardwareMap.get(Servo.class,"intakeRotator");


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

        initialPositions();

        //we will create macros in the future, to remove room for error
        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //Moves the elbow. TEST these positions
            if (gamepad2.b && !changedB) {
                if (elbow.getPosition() == elbowInsidePositionRight) {
                    extend();
                    changedB = true;
                } else if (elbow.getPosition() == elbowOutsidePositionRight) {
                    retrieve();
                    changedB = true;
                }
            } else if (!gamepad2.b) {
                changedB = false;
            }

            //Activate  the intake

            if (gamepad2.a) {
                intake();
            } else {
                passive();
            }
            //Reverse. I chose this button(because the old bot had no equivalent), so if you want another one then do it
            if (gamepad2.x){
                eject();
            } else {
                passive();
            }
            //Pushes the extendo. Positions need to be tested.
            //Test these positionsssss
            if (gamepad2.right_trigger != 0 && !changedRightTrigger) {
                if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPositionRight){
                    extend();
                    changedRightTrigger = true;
                } else if (horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPositionRight) {
                    retrieve();
                    changedRightTrigger = true;
                }
            } else if (gamepad2.right_trigger == 0) {
                changedRightTrigger = false;
            }

            //code for l claw
            if (gamepad2.y && !changedY) {
                if (claw.getPosition() == clawOpenPosition) {
                    grabSample();
                    changedY = true;
                } else if (claw.getPosition() == clawClosedPosition) {
                    dropSample();
                    changedY = true;
                }
            } else if(!gamepad2.y) {
                changedY = false;
            }
            //rotates the claw. there is much better way to do this, but this works for now
            //you have to continuously hold to dunk it
            if (gamepad2.left_trigger != 0 && !changedLeftTrigger) {
                if(claw.getPosition() == clawClosedPosition) {
                    dunk();
                } else if(claw.getPosition() == clawOpenPosition) {
                    grab();
                } else {
                    passive();
                }
            } else if (gamepad2.left_trigger == 0) {
                passive();
                changedLeftTrigger = true;
            }
            if (verticalSlidesLeft.getCurrentPosition() < verticalSlideHighScoringPositionLimit && verticalSlidesLeft.getCurrentPosition() >= 0) {
                verticalSlidesLeft.setPower(gamepad2.left_stick_y);
                verticalSlidesRight.setPower(gamepad2.left_stick_y);
            } else if (verticalSlidesLeft.getCurrentPosition() < 0) {
                verticalSlidesLeft.setPower(Math.max(gamepad2.left_stick_y, 0));  // Only allow positive power
                verticalSlidesRight.setPower(Math.max(gamepad2.left_stick_y, 0));
            } else if (verticalSlidesLeft.getCurrentPosition() > verticalSlideHighScoringPositionLimit) {
                verticalSlidesLeft.setPower(Math.min(gamepad2.left_stick_y, 0));  // Only allow negative power
                verticalSlidesRight.setPower(Math.min(gamepad2.left_stick_y, 0));
            }

            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;


            rightFront.setPower(joystickY - joystickX - joystickR);
            leftFront.setPower(joystickY + joystickX + joystickR);
            rightBack.setPower(joystickY + joystickX - joystickR);
            leftBack.setPower(joystickY - joystickX + joystickR);


        }

    }

    public void initialPositions(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        claw.setPosition(clawOpenPosition);
        shaq.setPosition(clawPassivePositionRight);
        clawRotator.setPosition(clawPassiveRotation);
        elbow.setPosition(intakePassiveRotation);
        intakeRotator.setPosition(elbowInsidePositionRight);
    }

    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPositionRight);
        elbow.setPosition(elbowOutsidePositionRight);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void intake(){
        continuousIntakeLeft.setPower(1);
        continuousIntakeRight.setPower(-1);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void eject() {
        continuousIntakeLeft.setPower(-1);
        continuousIntakeRight.setPower(1);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void passive() {
        continuousIntakeLeft.setPower(0);
        continuousIntakeRight.setPower(0);
        shaq.setPosition(clawPassivePositionRight);
        clawRotator.setPosition(clawPassiveRotation);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        intakeRotator.setPosition(intakePickUpRotation);
    }

    public void grabSample(){
        claw.setPosition(clawClosedPosition);
    }

    public void dropSample(){
        claw.setPosition(clawOpenPosition);
    }

    public void grab(){
        shaq.setPosition(clawRetrievePositionRight);
        clawRotator.setPosition(clawPickUpRotation);
    }



    public void rise(){
        verticalSlidesLeft.setPower(gamepad2.left_stick_y);
        verticalSlidesRight.setPower(gamepad2.left_stick_y);
    }
    public void lift(){
        verticalSlidesLeft.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void fall(){
        verticalSlidesLeft.setTargetPosition(0);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(0);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void dunk() {
        shaq.setPosition(clawScorePositionRight);
        clawRotator.setPosition(clawScoreRotation);
    }

    public void specimenPickUp() {
        claw.setPosition(clawOpenPosition);


        verticalSlidesLeft.setTargetPosition(specimenStartPickupVerticalSlides);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(specimenStartPickupVerticalSlides);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shaq.setPosition(specimenPickupPositionRight);
        clawRotator.setPosition(specimenClawRotation);

        ElapsedTime et = new ElapsedTime();
        et.reset();
        while(et.milliseconds() < 5000);
        claw.setPosition(clawClosedPosition);
        et.reset();
        while(et.milliseconds() < 1000);
        verticalSlidesLeft.setTargetPosition(specimenFinishPickupVerticalSlides);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(specimenFinishPickupVerticalSlides);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }





}