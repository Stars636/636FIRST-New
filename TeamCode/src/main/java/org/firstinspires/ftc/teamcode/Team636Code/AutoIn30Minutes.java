package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AutoIn30Minutes extends LinearOpMode {
    Servo rotatorJamal;
    Servo clawEthan;
    Servo pushRight;
    Servo pushLeft;

    Servo clawLeft;

    Servo clawRight;

    Servo clawMiddle;
    @Override
    public void runOpMode() {
        ElapsedTime et = new ElapsedTime();
        //Initializing all the motors. Do not change this unless we change the wiring
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        DcMotor verticalSlide = hardwareMap.get(DcMotor.class,"verticalSlide");
        // Reset the motor encoder for the slides.
        verticalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        //correcting the motors
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        verticalSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //Initializing all of the servos
        rotatorJamal = hardwareMap.get(Servo.class,"Servo1");
        clawEthan = hardwareMap.get(Servo.class,"Servo2");
        pushRight = hardwareMap.get(Servo.class,"pushRight");
        pushLeft = hardwareMap.get(Servo.class,"pushLeft");
        clawLeft = hardwareMap.get(Servo.class,"clawLeft");
        clawRight = hardwareMap.get(Servo.class,"clawRight");

        clawMiddle = hardwareMap.get(Servo.class,"clawMiddle");

        //Initial position of all the servos. When the wiring is perfected, RETEST all initial positions
        //There is definitely a better position for all of these
        double rotatorPosition = 0.1; //should start facing the claw
        double intakeClawPosition = 0.45; //should start open
        double pushPositionRight = 0.5; //1 is moving forward
        double pushPositionLeft = 0.5; // 0 is moving forward
        double verticalClawRight = 0;
        double verticalClawLeft = 1;// there is an ideal starting position. Let's test for that.
        double clawMiddlePosition = 0.48;
        rightBack.setPower(0.1);
        rightFront.setPower(0.1);
        leftBack.setPower(0.1);
        leftFront.setPower(0.1);

        if (opModeIsActive()) {
            clawMiddle.setPosition(clawMiddlePosition);

            et.reset();
            while (et.milliseconds() < 500);
            sleep(1000);

            et.reset();
            while (et.milliseconds() < 500) {
                double joystickX = 0;
                double joystickY = -0.01;
                double joystickR = 0;
                rightFront.setPower(joystickY - joystickX - joystickR);
                leftFront.setPower(joystickY + joystickX + joystickR);
                rightBack.setPower(joystickY + joystickX - joystickR);
                leftBack.setPower(joystickY - joystickX + joystickR);
            }
            sleep(1000);
            et.reset();
            while (et.milliseconds() < 500) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = 0.01;
                rightFront.setPower(joystickY - joystickX - joystickR);
                leftFront.setPower(joystickY + joystickX + joystickR);
                rightBack.setPower(joystickY + joystickX - joystickR);
                leftBack.setPower(joystickY - joystickX + joystickR);
            }
            sleep(1000);
            et.reset();
            while (et.milliseconds() < 500) {
                double joystickX = 0;
                double joystickY = -0.01;
                double joystickR = 0;
                rightFront.setPower(joystickY - joystickX - joystickR);
                leftFront.setPower(joystickY + joystickX + joystickR);
                rightBack.setPower(joystickY + joystickX - joystickR);
                leftBack.setPower(joystickY - joystickX + joystickR);
            }
            sleep(1000);
            et.reset();
            while (et.milliseconds() < 500) {
                int desiredPosition = 3614;
                verticalSlide.setTargetPosition(desiredPosition);
                verticalSlide.setPower(0.6); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            sleep(1000);
            







        }
        /*
        double joystickX = -gamepad1.left_stick_x;
        double joystickY = gamepad1.left_stick_y;
        double joystickR = -gamepad1.right_stick_x;

        rightFront.setPower(joystickY - joystickX - joystickR);
        leftFront.setPower(joystickY + joystickX + joystickR);
        rightBack.setPower(joystickY + joystickX - joystickR);
        leftBack.setPower(joystickY - joystickX + joystickR);*/

        //first, y becomes negative
        //second, r becomes negative
        //third, y becomes positive
    }


}
