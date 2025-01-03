package org.firstinspires.ftc.teamcode.Zhang.TrueZhang.Memorable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ScrimmageBot extends LinearOpMode {
    Servo Jamal;
    Servo Ethan;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        Jamal = hardwareMap.get(Servo.class,"Servo1");
        Ethan = hardwareMap.get(Servo.class,"Servo2");
        double positionJamal = 0.0;
        double positionEthan = 0.05;

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        boolean changeda = false;
        boolean changedb = false;
        boolean changedx = false;
        boolean changedy = false;

        waitForStart();




        while (opModeIsActive()) {
            if (gamepad1.a && !changeda) {
                positionJamal += 0.01;
                changeda = true;


            } else if (!gamepad1.a) {
                changeda = false;
            }
            if (gamepad1.b && !changedb) {
                positionJamal -= 0.01;
                changedb = true;
            } else if(!gamepad1.b) {
                changedb = false;
            }

            if (gamepad1.x && !changedx) {
                positionEthan += 0.01;
                changedx = true;


            } else if (!gamepad1.x) {
                changedx = false;
            }
            if (gamepad1.y && !changedy) {
                positionEthan -= 0.01;
                changedy = true;
            } else if(!gamepad1.y) {
                changedy = false;
            }

            double joystickX = -gamepad1.left_stick_x;
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;


            rightFront.setPower(joystickY - joystickX - joystickR);
            leftFront.setPower(joystickY + joystickX + joystickR);
            rightBack.setPower(joystickY + joystickX - joystickR);
            leftBack.setPower(joystickY - joystickX + joystickR);

            Jamal.setPosition(positionJamal);
            Ethan.setPosition(positionEthan);

            telemetry.addData("Jamal Position", Jamal.getPosition());
            telemetry.addData("Ethan Position", Ethan.getPosition());
            telemetry.update();

            idle();
        }
    }

}
