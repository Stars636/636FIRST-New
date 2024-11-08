package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Scrimmage extends LinearOpMode{
    Servo rotatorJamal;
    Servo clawEthan;
    Servo pushRight;
    Servo pushLeft;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        rotatorJamal = hardwareMap.get(Servo.class,"Servo1");
        clawEthan = hardwareMap.get(Servo.class,"Servo2");
        pushRight = hardwareMap.get(Servo.class,"pushRight");
        pushLeft = hardwareMap.get(Servo.class,"pushLeft");
        double positionJamal = 0.1;
        double positionEthan = 0.05;
        double pushPositionRight = 0;
        double pushPositionLeft = 1;

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        boolean changedR = false;
        boolean changedL = false;


        waitForStart();




        while (opModeIsActive()) {
            if (gamepad1.a && positionJamal <= 0.88) {
                positionJamal += 0.01;

            }
            if (gamepad1.b && positionJamal >= 0.05) {
                positionJamal -= 0.01;
            }

            if (gamepad1.x && positionEthan <= 0.51500) {
                positionEthan += 0.01;
            }
            if (gamepad1.y && positionEthan >= 0.01) {
                positionEthan -= 0.01;
            }
            if (gamepad1.right_trigger != 0 && pushPositionRight < 1 && !changedR) {
                pushPositionRight += 0.001;
                pushPositionLeft -= 0.001;
                changedR = true;
            } else if(gamepad1.right_trigger == 0) {
                changedR = false;
            }
            if (gamepad1.left_trigger != 0 && pushPositionLeft > 0 && !changedL) {
                pushPositionRight -= 0.001;
                pushPositionLeft += 0.001;
                changedL = true;
            } else if(gamepad1.left_trigger == 0) {
                changedL = false;
            }

            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;


            rightFront.setPower(joystickY - joystickX - joystickR);
            leftFront.setPower(joystickY + joystickX + joystickR);
            rightBack.setPower(joystickY + joystickX - joystickR);
            leftBack.setPower(joystickY - joystickX + joystickR);

            rotatorJamal.setPosition(positionJamal);
            clawEthan.setPosition(positionEthan);
            //pushLeft.setPosition(pushPositionLeft); we will test one servo at a time
            //pushRight.setPosition(pushPositionRight);

            telemetry.addData("Jamal Position", rotatorJamal.getPosition());
            telemetry.addData("Ethan Position", clawEthan.getPosition());
            telemetry.addData("Ethan Position", pushLeft.getPosition());
            telemetry.addData("Ethan Position", pushRight.getPosition());
            telemetry.update();

            idle();
        }
    }
}

