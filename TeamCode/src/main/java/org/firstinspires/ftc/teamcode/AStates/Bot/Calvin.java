package org.firstinspires.ftc.teamcode.AStates.Bot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Calvin {
    public DcMotorEx leftFront, rightFront, leftBack, rightBack;
    public DcMotorImplEx vSlidesLeft, vSlidesRight;

    public DcMotorImplEx hangLeft, hangRight;

    public ServoImplEx intakeClaw, intakeWrist, intakeElbow, intakeArm;

    public ServoImplEx depositClaw, depositArm, depositWrist;

    public ServoImplEx hSlidesLeft, hSlidesRight;

    public ServoImpl hangServo;

    public static int lowBucket;

    public static int highBucket;

    public static int hangStart; //note this is the verticalSlides

    public static int hangFinish; //add this to the current position of the hang motors

    public static double depositClawOpen = 0.17;
    public static double depositClawClosed = 0.30;


    public static double depositClawPassivePos = 0.65;

    public static double depositClawPassiveRot = 0.43;

    public static double depositClawTransferPos = 0.9;

    public static double depositClawTransferRot = 0.06;

    public static double depositClawScorePos = 0.4;

    public static double depositClawScoreRot = 1;

    public static double depositClawSpeciPosStart = 0.05;

    public static double depositClawSpeciRotStart = 0.95;

    public static double depositClawSpeciPosMiddle = 0.3;
    public static double depositClawSpeciRotMiddle = 0.8;
    public static double depositClawSpeciPosFinish = 0;
    public static double depositClawSpeciRotFinish = 1;



    public static double hSlidesInside = 0.99;
    public static double hSlidesOutside = 0.68;
    public static double intakeClawOpen;
    public static double intakeClawClosed;
    public static double intakeClawTransferPos;
    public static double intakeClawTransferRot;
    public static double intakeClawPassivePos;
    public static double intakeClawPassiveRot;
    public static double intakeClawHoverPos;
    public static double intakeClawHoverRot;
    public static double intakeClawGrabPos;
    public static double intakeClawGrabRot;
    public static double intakeWristFlat;
    public static double intakeWristTiltRight;
    public static double intakeWristNormalLeft;
    public static double intakeWristNormalRight; //as in perpendicular
    //Todo: refactor this to a better name
    public static double intakeWristTiltLeft;
    public static double hangServoInitial;
    public static double hangServoFinish;

    public static double hSlidesLeftTest;
    public static double hSlidesRightTest;
    public static double intakeClawTest;
    public static double intakeWristTest;
    public static double intakeElbowTest;
    public static double intakeArmTest;
    public static double depositClawTest;
    public static double depositWristTest;
    public static double depositArmTest;
    public static double hangServoTest;


    private ElapsedTime transferTime = new ElapsedTime();
    public static double transferPart1 = 3;
    public static double transferPart2 = 2.6;
    public static double transferPart3 = 2.5;
    public static double transferPart4 = 3;

    private ElapsedTime pickUpTime = new ElapsedTime();

    public static double pickUp1 = 3;//lower this over time LOL
    public static double pickUp2 = 3;
    private ElapsedTime specimenTime = new ElapsedTime();
    public static double specimenPart1 = 3;


    public Calvin(HardwareMap hardwareMap) {
        rightBack = hardwareMap.get(DcMotorEx.class,"rightBack");
        leftBack = hardwareMap.get(DcMotorEx.class,"leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class,"leftFront");

        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        vSlidesLeft = hardwareMap.get(DcMotorImplEx.class,"vSlidesLeft");
        vSlidesRight = hardwareMap.get(DcMotorImplEx.class,"vSlidesRight");

        vSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vSlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        vSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        vSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        vSlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        hangLeft = hardwareMap.get(DcMotorImplEx.class,"hangLeft");
        hangRight = hardwareMap.get(DcMotorImplEx.class,"hangRight");

        hangRight.setDirection(DcMotorSimple.Direction.REVERSE);

        hangLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hangRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        hangRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hangRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hangLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hangServo = hardwareMap.get(ServoImpl.class, "hangServo");

        intakeClaw = hardwareMap.get(ServoImplEx.class,"intakeClaw");
        intakeWrist = hardwareMap.get(ServoImplEx.class,"intakeWrist");
        intakeElbow = hardwareMap.get(ServoImplEx.class,"intakeElbow");
        intakeArm = hardwareMap.get(ServoImplEx.class,"intakeArm");

        depositClaw = hardwareMap.get(ServoImplEx.class,"depositClaw");
        depositArm = hardwareMap.get(ServoImplEx.class,"depositArm");
        depositWrist = hardwareMap.get(ServoImplEx.class,"depositWrist");

        hSlidesLeft  = hardwareMap.get(ServoImplEx.class,"hSlidesLeft");
        hSlidesRight = hardwareMap.get(ServoImplEx.class,"hSlidesRight");
        hSlidesLeft.setDirection(Servo.Direction.FORWARD);
        hSlidesRight.setDirection(Servo.Direction.REVERSE);

        depositArm.setPwmRange(new PwmControl.PwmRange(500,2500));
        depositWrist.setPwmRange(new PwmControl.PwmRange(500,2500));
        intakeArm.setPwmRange(new PwmControl.PwmRange(500,2500));
        intakeElbow.setPwmRange(new PwmControl.PwmRange(500,2500));
        intakeWrist.setPwmRange(new PwmControl.PwmRange(500,2500));

    }

    public void initialTele() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
        intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
        depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
        hangServo.setPosition(hangServoInitial);
    }
    public void initialSpecimen() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
        intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
        depositClaw.setPosition(depositClawClosed);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
        hangServo.setPosition(hangServoInitial);
    }
    public void initialBucket() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
        intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
        depositClaw.setPosition(depositClawClosed);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
        hangServo.setPosition(hangServoInitial);
    }

    public void hSlidesTester(){
        hSlidesLeft.setPosition(hSlidesLeftTest);
        hSlidesRight.setPosition(hSlidesRightTest);
    }
    public void clawTester(){
        intakeClaw.setPosition(intakeClawTest);
        intakeWrist.setPosition(intakeWristTest);
        depositClaw.setPosition(depositClawTest);
    }
    public void intakePosition(){
        intakeElbow.setPosition(intakeElbowTest);
        intakeArm.setPosition(intakeArmTest);
    }
    public void depositPosition() {
        depositWrist.setPosition(depositWristTest);
        depositArm.setPosition(depositArmTest);
    }
    public void hangTest(){
        hangServo.setPosition(hangServoTest);
    }
    //FIXME
    // -Add all the basic functions
    public void intakeClawOpen(){
        intakeClaw.setPosition(intakeClawOpen);
    }
    public void intakeClawClosed(){
        intakeClaw.setPosition(intakeClawClosed);
    }
    public void depositClawOpen(){
        depositClaw.setPosition(depositClawOpen);
    }
    public void depositClawClosed(){
        depositClaw.setPosition(depositClawClosed);
    }
    public void hSlidesIn(){
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
    }
    public void hSlidesOut(){
        hSlidesLeft.setPosition(hSlidesOutside);
        hSlidesRight.setPosition(hSlidesOutside);
    }
    public void intakePassive(){
        //intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
    }
    public void depositPassive(){
        //depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
    }
    public void depositScore(){
        depositWrist.setPosition(depositClawScoreRot);
        depositArm.setPosition(depositClawScorePos);
    }
    public void depositSpecimenStart(){
        depositWrist.setPosition(depositClawSpeciPosStart);
        depositArm.setPosition(depositClawSpeciRotStart);
    }

    public void hangPassive(){
        hangServo.setPosition(hangServoInitial);
    }
    public void hangSet(){
        hangServo.setPosition(hangServoFinish);
    }

    //Todo: Create a function for scoring specimens, whether using the timer or not
    // -Currently questionable
    // -Function for the pickup
    // -Function for score (maybe with timer) (if you use a statemachine move it to tele)
    // -I'll leave this to you. :)

    enum SpecimenSteps {
        READY, FINAL
    }
    SpecimenSteps specimenStep = SpecimenSteps.READY;
    public void scoreSpecimen(boolean buttonPressed, boolean lastButtonPressed) { //so on so forth
        switch (specimenStep) {
            case READY:
                if (buttonPressed && !lastButtonPressed) {
                    depositClaw.setPosition(depositClawClosed);
                    specimenStep = SpecimenSteps.FINAL;
                }
            case FINAL:
                specimenTime.reset();
                if (specimenTime.seconds() >= specimenPart1) {
                    depositWrist.setPosition(depositClawSpeciRotFinish);
                    depositArm.setPosition(depositClawSpeciPosFinish);
                }
                specimenStep = SpecimenSteps.READY;
        }
    }

    //Todo: Create a function for automating the transfer, whether using the timer or not
    // -Functions for everything needs for intake
    //      -Will we need to have modes to switch between for all the controls?
    // -Functions for the transfer
    // -Functions for the scoring
    public void hover() {
        intakeElbow.setPosition(intakeClawHoverRot);
        intakeArm.setPosition(intakeClawHoverPos);
    }
    public void grab() {
        intakeElbow.setPosition(intakeClawGrabRot);
        intakeArm.setPosition(intakeClawGrabPos);
    }
    public void tiltLeft(){
        intakeWrist.setPosition(intakeWristTiltLeft);
    }
    public void tiltRight(){
        intakeWrist.setPosition(intakeWristTiltRight);
    }
    public void flip(){
        intakeWrist.setPosition(intakeWristNormalLeft);
    }
    //Todo: find out if set all this positions simultaneuously actually works
    // -otherwise, use timers

    //Todo: timer version of the pickup from chamber
    public enum PickUpSteps {
        READY, MOVE, GRAB
    }
    PickUpSteps pickUpStep = PickUpSteps.READY;

    public void pickUp(boolean buttonPressed, boolean lastButtonPressed) {
        switch(pickUpStep) {
            case READY:
                if (buttonPressed && !lastButtonPressed) {
                    pickUpStep = PickUpSteps.MOVE;
                }
            case MOVE:
                grab();
                pickUpTime.reset();
                if (pickUpTime.seconds() >= pickUp1) {
                    pickUpStep = PickUpSteps.GRAB;
                }
            case GRAB:
                if (intakeClaw.getPosition() == intakeClawClosed) {
                    intakeClawOpen();
                } else if(intakeClaw.getPosition() == intakeClawOpen) {
                    intakeClawClosed();
                }
                pickUpTime.reset();
                if (pickUpTime.seconds() >= pickUp2) {
                    hover();
                }

        }
    }

    //Todo: this is the timer version (i.e only one button press after closing a claw on sample
    //i.e just use a state machine you guys know what that is
    //Todo: move this to your teleop of choice, it only works in the while loop
    public enum TransferSteps {
        READY, MOVE, GRAB, LETGO, RETURN;
    }
    TransferSteps transferStep = TransferSteps.READY;


    public void transferStartClosed(){
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawTransferRot);
        intakeArm.setPosition(intakeClawTransferRot);
        depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawTransferRot);
        depositArm.setPosition(depositClawTransferPos);
    }
    public void transferStartOpen(){
        intakeWrist.setPosition(intakeWristNormalLeft);
        intakeElbow.setPosition(intakeClawTransferRot);
        intakeArm.setPosition(intakeClawTransferRot);
        depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawTransferRot);
        depositArm.setPosition(depositClawTransferPos);
    }

    public void transferEnd(boolean buttonPressed, boolean lastButtonPressed){
        switch(transferStep) {
            case READY:
                if (buttonPressed && !lastButtonPressed) {
                    transferStep = TransferSteps.MOVE;
                }

            case MOVE:
                transferTime.reset();
                if (transferTime.seconds() >= transferPart1){
                    if(intakeClaw.getPosition() == intakeClawClosed){
                        transferStartClosed();
                    }
                    if(intakeClaw.getPosition() == intakeClawOpen) {
                        transferStartOpen();
                    }

                }
                transferStep = TransferSteps.GRAB;
            case GRAB:
                transferTime.reset();
                if (transferTime.seconds() >= transferPart2) {
                    depositClaw.setPosition(depositClawClosed);
                }
                transferStep = TransferSteps.LETGO;
            case LETGO:
                transferTime.reset();
                if (transferTime.seconds() >= transferPart3) {
                    intakeClaw.setPosition(intakeClawOpen);
                }
                transferStep = TransferSteps.RETURN;
            case RETURN:
                transferTime.reset();
                if (transferTime.seconds() >= transferPart4) {
                    intakeWrist.setPosition(intakeWristFlat);
                    intakeElbow.setPosition(intakeClawPassiveRot);
                    intakeArm.setPosition(intakeClawPassivePos);

                    depositWrist.setPosition(depositClawPassiveRot);
                    depositArm.setPosition(depositClawPassivePos);
                }
                transferStep = TransferSteps.READY;
        }
    }
    //We can always make the timer faster, so please don't worry

}
