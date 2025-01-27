package org.firstinspires.ftc.teamcode.ATests.SideQuests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class MotorTester extends LinearOpMode {
    public static double kP, kI, kD,kF;
    public DcMotorImplEx SlidesLeft, SlidesRight;

    @Override
    public void runOpMode() {
        SlidesLeft = hardwareMap.get(DcMotorImplEx.class,"vSlidesLeft");
        SlidesRight = hardwareMap.get(DcMotorImplEx.class,"vSlidesRight");

        SlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        SlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kP, kI, kD, kF);
        SlidesLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        SlidesRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        SlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.a) {
                SlidesLeft.setTargetPosition(1000);
                SlidesRight.setTargetPosition(1000);
                SlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                SlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                SlidesLeft.setPower(0.5);
                SlidesRight.setPower(0.5);
            }
            if(gamepad1.b) {
                SlidesLeft.setTargetPosition(0);
                SlidesRight.setTargetPosition(0);
                SlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                SlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                SlidesLeft.setPower(0.5);
                SlidesRight.setPower(0.5);
            }
        }

    }
}
