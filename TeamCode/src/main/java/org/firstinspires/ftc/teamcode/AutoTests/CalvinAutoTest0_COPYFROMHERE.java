package org.firstinspires.ftc.teamcode.AutoTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class CalvinAutoTest0_COPYFROMHERE extends LinearOpMode {
    CRServo continuousIntakeLeft;
    CRServo continuousIntakeRight;
    Servo claw;
    Servo shaqLeft;
    Servo shaqRight;

    Servo elbowServoLeft;
    Servo elbowServoRight;

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

    public static double clawPassivePositionLeft;

    public static double clawPassivePositionRight;
    public static double clawRetrievePositionLeft;

    public static double clawRetrievePositionRight;
    public static double clawScorePositionLeft;
    public static double clawScorePositionRight;

    public static double elbowInsidePositionLeft;
    public static double elbowOutsidePositionLeft;
    public static double elbowInsidePositionRight;
    public static double elbowOutsidePositionRight;

    public static int verticalSlideHighScoringPositionLimit; //kindly note that gunner will use joystick

    public static double horizontalSlidesInitialPositionLeft;
    public static double horizontalSlidesInitialPositionRight;

    public static double horizontalSlidesExtendedPositionLeft;
    public static double horizontalSlidesExtendedPositionRight;

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
        shaqLeft = hardwareMap.get(Servo.class,"shaqLeft");
        shaqRight = hardwareMap.get(Servo.class,"shaqRight");
        elbowServoLeft = hardwareMap.get(Servo.class,"elbowServoLeft");
        elbowServoRight = hardwareMap.get(Servo.class,"elbowServoRight");

        initialPositions();

        //we will create macros in the future, to remove room for error
        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

        }


    }
    public void initialPositions(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        claw.setPosition(clawOpenPosition);
        shaqLeft.setPosition(clawPassivePositionLeft);
        shaqRight.setPosition(clawPassivePositionRight);
        elbowServoLeft.setPosition(elbowInsidePositionLeft);
        elbowServoRight.setPosition(elbowInsidePositionRight);
    }

    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPositionRight);
        elbowServoLeft.setPosition(elbowOutsidePositionLeft);
        elbowServoRight.setPosition(elbowOutsidePositionRight);
    }

    public void intake(){
        continuousIntakeLeft.setPower(1);
        continuousIntakeRight.setPower(-1);
    }

    public void eject() {
        continuousIntakeLeft.setPower(-1);
        continuousIntakeRight.setPower(1);
    }

    public void passive() {
        continuousIntakeLeft.setPower(0);
        continuousIntakeRight.setPower(0);
        shaqLeft.setPosition(clawPassivePositionLeft);
        shaqRight.setPosition(clawPassivePositionRight);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);

    }

    public void grabSample(){
        claw.setPosition(clawClosedPosition);
    }

    public void grab(){
        shaqLeft.setPosition(clawRetrievePositionLeft);
        shaqRight.setPosition(clawRetrievePositionRight);
    }

    public void dropSample(){
        claw.setPosition(clawOpenPosition);
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
        verticalSlidesLeft.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void dunk() {
        shaqLeft.setPosition(clawScorePositionLeft);
        shaqRight.setPosition(clawScorePositionRight);
    }



}

