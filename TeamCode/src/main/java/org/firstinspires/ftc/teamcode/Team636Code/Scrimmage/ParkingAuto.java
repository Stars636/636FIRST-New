package org.firstinspires.ftc.teamcode.Team636Code.Scrimmage;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "RightScrimmageAuto", group = "Quals")
public class ParkingAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        ElapsedTime et = new ElapsedTime();
        //Initializing all the motors. Do not change this unless we change the wiring
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");

        // Reset the motor encoder for the slides.


        //correcting the motors
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //Initializing all of the servos




        waitForStart();

        while (opModeIsActive()) {
            while (et.milliseconds() < 1000) {
                double joystickX = -0.4;
                double joystickY = 0;
                double joystickR = 0;
                rightFront.setPower(joystickY - joystickX - joystickR);
                leftFront.setPower(joystickY + joystickX + joystickR);
                rightBack.setPower(joystickY + joystickX - joystickR);
                leftBack.setPower(joystickY - joystickX + joystickR);
            }
            sleep(1000);
            while (et.milliseconds() < 28000) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = 0;
                rightFront.setPower(joystickY - joystickX - joystickR);
                leftFront.setPower(joystickY + joystickX + joystickR);
                rightBack.setPower(joystickY + joystickX - joystickR);
                leftBack.setPower(joystickY - joystickX + joystickR);
            }

            

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
