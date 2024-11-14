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

    Servo clawLeft;

    Servo clawRight;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        DcMotor verticalSlide = hardwareMap.get(DcMotor.class,"verticalSlide");

        verticalSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        verticalSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        rotatorJamal = hardwareMap.get(Servo.class,"Servo1");
        clawEthan = hardwareMap.get(Servo.class,"Servo2");
        pushRight = hardwareMap.get(Servo.class,"pushRight");
        pushLeft = hardwareMap.get(Servo.class,"pushLeft");
        clawLeft = hardwareMap.get(Servo.class,"clawLeft");
        clawRight = hardwareMap.get(Servo.class,"clawRight");
        double positionJamal = 0.1;
        double positionEthan = 0.05;
        double pushPositionRight = 0.5; //1 is moving forward
        double pushPositionLeft = 0.5; // 0 is moving forward
        double verticalClawPosition = 0;

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        boolean changedR = false;
        boolean changedL = false;
        boolean changedClaw = false;
        boolean changedSlide = false;


        waitForStart();




        while (opModeIsActive()) {
            if (gamepad1.a && positionJamal <= 0.88) {
                positionJamal += 0.01;

            }
            if (gamepad1.b && positionJamal >= 0.05) {
                positionJamal -= 0.01;
            }

            if (gamepad1.x) {
                positionEthan = 0.51500;
            }
            if (gamepad1.y) {
                positionEthan = 0.01;
            }
            if (gamepad1.right_trigger != 0 && pushPositionRight < 1 && !changedR) {
                pushPositionRight += 0.01;
                pushPositionLeft -= 0.01;
                changedR = true;
            } else if(gamepad1.right_trigger == 0) {
                changedR = false;
            }
            if (gamepad1.left_trigger != 0 && pushPositionRight > 0  && !changedL) {
                pushPositionRight -= 0.01;
                pushPositionLeft += 0.01;
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
            pushLeft.setPosition(pushPositionLeft);
            pushRight.setPosition(pushPositionRight);

            double worlds = 540; // constant we used to make sure the values or revolutions and angles make sense

            double position = verticalSlide.getCurrentPosition();
            double revolutions = position/worlds; //takahiro is revolutions
            double angle = revolutions * 360;
            double angleNormalized = angle % 360;

            double diameter = 3.5; // In cm
            double circumference = Math.PI * diameter;
            double distance = circumference * revolutions;




            /*

             */
            if(gamepad1.dpad_up && !changedSlide) {
                int desiredPosition = -10; // The position (in ticks) that you want the motor to move to
                verticalSlide.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                verticalSlide.setPower(0.3);
                changedSlide = true;
            } else if (!gamepad1.dpad_up) {
                changedSlide = false;
            }
            if(gamepad1.dpad_down) {
                int desiredPosition = 0; // The position (in ticks) that you want the motor to move to
                verticalSlide.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
                verticalSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                verticalSlide.setPower(0.3);
            }
            telemetry.addData("Encoder Position", position);
            telemetry.addData("Encoder Angle", angle);
            telemetry.addData("Encoder Normal Angle", angleNormalized);
            telemetry.addData("Encoder Revolution", revolutions);
            telemetry.addData("Distance Traveled", distance);

          
            telemetry.addData("Slide Position via Encoder", verticalSlide.getCurrentPosition());
            telemetry.addData("Jamal Position", rotatorJamal.getPosition());
            telemetry.addData("Ethan Position", clawEthan.getPosition());
            telemetry.addData("pushLeft Position", pushLeft.getPosition());
            telemetry.addData("pushRight Position", pushRight.getPosition());
            telemetry.update();

            idle();
        }
    }
}

