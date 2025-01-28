package org.firstinspires.ftc.teamcode.ZMemorable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MyCode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class,"backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class,"backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setPower(1);

        double joyStickX = gamepad1.left_stick_x;
        double joyStickY = gamepad1.left_stick_y;

        //Alt enter to fix error;
    }
}
