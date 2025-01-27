package org.firstinspires.ftc.teamcode.AStates.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp
public class TeleOpTester extends LinearOpMode {

    public DcMotorEx leftFront, rightFront, leftBack, rightBack;
    public DcMotorImplEx vSlidesLeft, vSlidesRight;

    public DcMotorImplEx hangLeft, hangRight;

    public ServoImplEx intakeClaw, intakeWrist, intakeElbow, intakeArm;

    public ServoImplEx depositClaw, depositArm, depositWrist;

    public ServoImplEx hSlidesLeft, hSlidesRight;

    public static double hSlidesTest = 0;
    public static double intakeClawTest = 0;
    public static double intakeWristTest = 0;
    public static double intakeElbowTest = 0;
    public static double intakeArmTest = 0;
    public static double depositClawTest = 0;
    public static double depositWristTest = 0.65;
    public static double depositArmTest = 0.43;
    public static double hangServoTest = 0;

    public static double SlideTesting = 0;
    public static double IntakeTesting = 0;
    public static double IntakeClawTesting = 0;
    public static double DepositClawTesting = 0;
    public static double DepositTesting = 0;
    public static double HangTesting = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        intakeClaw = hardwareMap.get(ServoImplEx.class,"intakeClaw");//
        intakeWrist = hardwareMap.get(ServoImplEx.class,"intakeWrist");//
        intakeElbow = hardwareMap.get(ServoImplEx.class,"intakeElbow");//
        intakeArm = hardwareMap.get(ServoImplEx.class,"intakeArm");//

        depositClaw = hardwareMap.get(ServoImplEx.class,"depositClaw");//
        depositArm = hardwareMap.get(ServoImplEx.class,"depositArm");//
        depositWrist = hardwareMap.get(ServoImplEx.class,"depositWrist");//

        hSlidesLeft  = hardwareMap.get(ServoImplEx.class,"hSlidesLeft");//
        hSlidesRight = hardwareMap.get(ServoImplEx.class,"hSlidesRight");//
        hSlidesLeft.setDirection(Servo.Direction.FORWARD);
        hSlidesRight.setDirection(Servo.Direction.REVERSE);

        depositArm.setPwmRange(new PwmControl.PwmRange(500,2500));
        depositWrist.setPwmRange(new PwmControl.PwmRange(500,2500));
        intakeArm.setPwmRange(new PwmControl.PwmRange(500,2500));
        intakeElbow.setPwmRange(new PwmControl.PwmRange(500,2500));
        intakeWrist.setPwmRange(new PwmControl.PwmRange(500,2500));
        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //if something doesn't work start here
            if (SlideTesting != 0) {
                hSlidesLeft.setPosition(hSlidesTest);
                hSlidesRight.setPosition(hSlidesTest);
            }
            if (IntakeTesting != 0) {
                intakeWrist.setPosition(intakeWristTest);
                intakeElbow.setPosition(intakeElbowTest);
                intakeArm.setPosition(intakeArmTest);
            }

            if (IntakeClawTesting != 0){
                intakeClaw.setPosition(intakeClawTest);

            }
            if (DepositClawTesting != 0) {
                depositClaw.setPosition(depositClawTest);
            }
            if (DepositTesting != 0) {
                depositWrist.setPosition(depositWristTest);
                depositArm.setPosition(depositArmTest);
            }
            



        }

    }



}