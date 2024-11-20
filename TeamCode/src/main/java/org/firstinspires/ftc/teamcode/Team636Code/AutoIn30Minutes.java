package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class AutoIn30Minutes extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        if (opModeIsActive()) {


        }
        /*
        double joystickX = -gamepad1.left_stick_x;
        double joystickY = gamepad1.left_stick_y;
        double joystickR = -gamepad1.right_stick_x;

        rightFront.setPower(joystickY - joystickX - joystickR);
        leftFront.setPower(joystickY + joystickX + joystickR);
        rightBack.setPower(joystickY + joystickX - joystickR);
        leftBack.setPower(joystickY - joystickX + joystickR);*/
    }


}
