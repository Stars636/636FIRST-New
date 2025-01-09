package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class teltwo extends LinearOpMode {

    DcMotorImplEx SlidesLeft;
    DcMotorImplEx SlidesRight;

    CRServo servoLeft;
    CRServo servoRight;

    CRServo servoUpWard;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();




        waitForStart();
        SlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        SlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");
        servoLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft");
        servoRight =  hardwareMap.get(CRServo.class,"continuousIntakeRight");
        servoUpWard =  hardwareMap.get(CRServo.class,"continuousIntakeUp");


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            SlidesLeft.setPower(gamepad2.left_stick_y);
            SlidesRight.setPower(-gamepad2.left_stick_y);



        }

    }



}