package org.firstinspires.ftc.teamcode.CalvinTeleOp.Deprecated;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Disabled
@Config
public class CalviningAgain {
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public CRServo intakeUp;

    public ServoImplEx claw;
    public ServoImplEx shaq;
    public ServoImplEx clawRotator;

    public ServoImplEx elbowLeft;
    public ServoImplEx elbowRight;

    public ServoImplEx horizontalSlidesLeft;
    public ServoImplEx horizontalSlidesRight;

    public DcMotorEx rightBackCalvin;

    public DcMotorEx rightFrontCalvin;

    public DcMotorEx leftBackCalvin;

    public DcMotorEx leftFrontCalvin;
    public DcMotorImplEx verticalSlidesRight;

    public DcMotorImplEx verticalSlidesLeft;

    public Limelight3A limelight;

    public static double intakeUpSpeed = 1;


    public static double clawOpenPosition = 0;
    public static double clawClosedPosition = 0.17;


    public static double clawPassivePosition = 0.7;

    public static double clawPassiveRotation = 0.06;

    public static double clawRetrievePosition = 0.83;

    public static double clawPickUpRotation = 1;

    public static double clawScorePosition = 0.29;

    public static double clawScoreRotation = 0.8;

    public static double clawHangRotation = 1;

    public static double elbowInsidePosition = 0.02;
    public static double elbowOutsidePosition = 0.75;






    public static int verticalSlideHighScoringPositionLimit = 3000; //kindly note that gunner will use joystick

    //public static int verticalSlideLowScoringPositionLimit = 1500;
    public static double horizontalSlidesInitialPosition = 0.99;

    public static double horizontalSlidesExtendedPosition = 0.68;


    public static double specimenPickupPosition = 0;

    public static double specimenClawPosition = 0.85;


    public static int specimenStartPickupVerticalSlides = 0;

    public static double specimenDepositClawRotation = 0.3;


    // public static int specimenFinishPickupVerticalSlides = 1000;

    public static int specimenStartDepositVerticalSlides = 1000;
    public static int specimenFinishDepositVerticalSlides = 0;



    public boolean changedRightTrigger = false;
    public boolean changedLeftTrigger = false;


    public boolean changedB = false;


    // public boolean changedRightBumper = false;
    public boolean changedZhang = false;

    public boolean changedUchida = false;

    // public boolean changedPresanna = false;
    public boolean changedY = false;

    // public boolean changedLeftBumper = false;

    //
    private ElapsedTime buttonTimer = new ElapsedTime();
    // private boolean buttonPreviouslyPressed = false;


    //public static int testerZ = 0;





    public CalviningAgain(HardwareMap hardwareMap, Telemetry telemetry) {
        //


        //Initializing all the motors. Do not change this unless we change the wiring
        rightBackCalvin = hardwareMap.get(DcMotorEx.class, "rightBack");
        leftBackCalvin = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFrontCalvin = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftFrontCalvin = hardwareMap.get(DcMotorEx.class, "leftFront");

        rightFrontCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBackCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFrontCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBackCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFrontCalvin.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackCalvin.setDirection(DcMotorSimple.Direction.REVERSE);


        verticalSlidesLeft = hardwareMap.get(DcMotorImplEx.class, "verticalSlidesLeft");
        verticalSlidesRight = hardwareMap.get(DcMotorImplEx.class, "verticalSlidesRight");

        verticalSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        verticalSlidesLeft.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.FLOAT);
        verticalSlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        horizontalSlidesLeft = hardwareMap.get(ServoImplEx.class, "horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(ServoImplEx.class, "horizontalSlidesRight");
        //one of these must be reversed
        horizontalSlidesLeft.setDirection(Servo.Direction.FORWARD);
        horizontalSlidesRight.setDirection(Servo.Direction.REVERSE);
        intakeLeft = hardwareMap.get(CRServo.class, "continuousIntakeLeft"); //setPower
        intakeRight = hardwareMap.get(CRServo.class, "continuousIntakeRight"); //setPower
        intakeUp = hardwareMap.get(CRServo.class, "continuousIntakeUp"); //setPower

        claw = hardwareMap.get(ServoImplEx.class, "claw");
        shaq = hardwareMap.get(ServoImplEx.class, "shaq");

        clawRotator = hardwareMap.get(ServoImplEx.class, "clawRotator");
        elbowLeft = hardwareMap.get(ServoImplEx.class, "elbowLeft");
        elbowRight = hardwareMap.get(ServoImplEx.class, "elbowRight");
        elbowRight.setDirection(Servo.Direction.REVERSE);


    }

    public void wait(double seconds) {
        ElapsedTime calvinTimer = new ElapsedTime();
        calvinTimer.reset();
        while(calvinTimer.seconds() < seconds);
    }

    public void initialPositions(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        claw.setPosition(clawOpenPosition);
        shaq.setPosition(clawPassivePosition);
        clawRotator.setPosition(clawPassiveRotation);
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);

    }



    public void checkHardwareInitialization(Telemetry telemetry) {
        if (rightFrontCalvin == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "rightFront");
            telemetry.update();
        }
        if (leftFrontCalvin == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "leftFront");
            telemetry.update();
        }
        if (rightBackCalvin == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "rightBack");
            telemetry.update();
        }
        if (leftBackCalvin == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "leftBack");
            telemetry.update();
        }

        if (verticalSlidesLeft == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "verticalSlidesLeft");
            telemetry.update();
        }
        if (verticalSlidesRight == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "verticalSlidesRight");
            telemetry.update();
        }
        if (limelight == null || !limelight.isConnected()) {
            telemetry.addData("ERROR", "Camera initialization failed");
            telemetry.addData("ERROR", "Limelight3A");
            telemetry.update();
        }
        if (elbowLeft == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "Elbow");
            telemetry.update();
        }
        if (horizontalSlidesRight == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "horizontalSlidesRight");
            telemetry.update();
        }
        if (horizontalSlidesLeft == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "horizontalSlidesLeft");
            telemetry.update();
        }
        if (elbowRight == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "intakeRotator");
            telemetry.update();
        }
        if (intakeRight == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "continuousIntakeRight");
            telemetry.update();
        }
        if (intakeLeft == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "continuousIntakeLeft");
            telemetry.update();
        }
        if (shaq == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "shaq");
            telemetry.update();
        }
        if (claw == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "claw");
            telemetry.update();
        }
        if (clawRotator == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "clawRotator");
            telemetry.update();
        }

    }

    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPosition);
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }
    public void elbowIn(){
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);
    }
    public void elbowOut(){
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }
    public void extendoIn(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
    }
    public void extendoOut(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPosition);

    }

    public void intake(){
        intakeLeft.setPower(-1);
        intakeRight.setPower(1);
        intakeUp.setPower(intakeUpSpeed);

    }

    public void eject() {
        intakeLeft.setPower(1);
        intakeRight.setPower(-1);
        intakeUp.setPower(-intakeUpSpeed);

    }
    public void intakePassive() {
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
        intakeUp.setPower(0);
    }

    public void passive() {
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
        intakeUp.setPower(0);
        shaq.setPosition(clawPassivePosition);
        clawRotator.setPosition(clawPassiveRotation);
        moveVerticalSlidesTo(0);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);
    }

    public void grabSample(){
        claw.setPosition(clawClosedPosition);
    }

    public void dropSample(){
        claw.setPosition(clawOpenPosition);
    }

    public void grab(){
        shaq.setPosition(clawRetrievePosition);
        clawRotator.setPosition(clawPickUpRotation);
    }

    public void lift(){
        moveVerticalSlidesTo(verticalSlideHighScoringPositionLimit);
    }
    public void fall(){
        moveVerticalSlidesTo(0);
    }
    public void hang() {
        clawRotator.setPosition(clawHangRotation);
    }

    public void dunk() {
        shaq.setPosition(clawScorePosition);
        clawRotator.setPosition(clawScoreRotation);
    }
    public void specimenScore(){
        clawRotator.setPosition(specimenDepositClawRotation);
        shaq.setPosition(specimenClawPosition);
    }



    public void moveVerticalSlidesTo(int targetPosition) {
        verticalSlidesLeft.setTargetPosition(targetPosition);
        verticalSlidesLeft.setPower(1);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        verticalSlidesRight.setTargetPosition(targetPosition);
        verticalSlidesRight.setPower(1);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}
