package org.firstinspires.ftc.teamcode.AStates.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp
public class TeleOpTester extends LinearOpMode {

    Calvin calvin = new Calvin(hardwareMap);

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


        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //if something doesn't work start here
            if (SlideTesting != 0) {
                calvin.hSlidesLeft.setPosition(hSlidesTest);
                calvin.hSlidesRight.setPosition(hSlidesTest);
            }
            if (IntakeTesting != 0) {
                calvin.intakeWrist.setPosition(intakeWristTest);
                calvin.intakeElbow.setPosition(intakeElbowTest);
                calvin.intakeArm.setPosition(intakeArmTest);
            }

            if (IntakeClawTesting != 0){
                calvin.intakeClaw.setPosition(intakeClawTest);

            }
            if (DepositClawTesting != 0) {
                calvin.depositClaw.setPosition(depositClawTest);
            }
            if (DepositTesting != 0) {
                calvin.depositWrist.setPosition(depositWristTest);
                calvin.depositArm.setPosition(depositArmTest);
            }
            if (HangTesting != 0){
                calvin.hangServo.setPosition(hangServoTest);
            }



        }

    }



}