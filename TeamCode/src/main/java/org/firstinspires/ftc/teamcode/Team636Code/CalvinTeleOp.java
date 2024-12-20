package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class CalvinTeleOp extends LinearOpMode {
    CRServo continuousIntakeLeft;
    CRServo continuousIntakeRight;
    Servo claw;
    Servo servoLeftSlide;
    Servo servoRightSlide;

    Servo elbowServoLeft;
    Servo elbowServoRight;

    Servo horizontalSlidesLeft;
    Servo horizontalSlidesRight;


    public static double clawOpenPosition;
    public static double clawClosedPosition;
    public static double clawRetrievePosition;
    public static double clawScorePosition;

    public static double elbowInsidePositionLeft;
    public static double elbowOutsidePositionLeft;
    public static double elbowInsidePositionRight;
    public static double elbowOutsidePositionRight;

    public static double verticalSlideHighScoringPositionLimit; //kindly note that gunner will use joystick



    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        //Initializing all the motors. Do not change this unless we change the wiring
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");

        DcMotor verticalSlide = hardwareMap.get(DcMotor.class, "verticalSlide");

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        verticalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        horizontalSlidesLeft = hardwareMap.get(Servo.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(Servo.class,"horizontalSlidesRight");
        continuousIntakeLeft = hardwareMap.get(CRServo.class," continuousIntakeLeft"); //setPower
        continuousIntakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        claw = hardwareMap.get(Servo.class,"claw");
        servoLeftSlide = hardwareMap.get(Servo.class,"clawLeft");
        servoRightSlide = hardwareMap.get(Servo.class,"clawRight");
        elbowServoLeft = hardwareMap.get(Servo.class,"elbowServoLeft");
        elbowServoRight = hardwareMap.get(Servo.class,"elbowServoRight");




        waitForStart();

        while (opModeIsActive()) {


        }

    }

}