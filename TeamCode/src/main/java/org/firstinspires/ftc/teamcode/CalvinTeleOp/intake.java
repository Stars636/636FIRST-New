package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class intake extends LinearOpMode {

    CRServo servoLeft;
    CRServo servoRight;
    @Override
    public void runOpMode() throws InterruptedException {
        servoLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft");
        servoRight =  hardwareMap.get(CRServo.class,"continuousIntakeRight");
        waitForStart();

        while(opModeIsActive()) {
            servoLeft.setPower(-1);
            servoRight.setPower(1);
        }
    }
}
