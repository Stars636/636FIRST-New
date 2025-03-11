package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="Four Motor Tank Drive", group="Linear Opmode")
public class FourMotorTankDrive extends LinearOpMode {

    // Declare the motors
    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the motors
        leftMotor1 = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        leftMotor2 = hardwareMap.get(DcMotor.class, "backLeftMotor");
        rightMotor1 = hardwareMap.get(DcMotor.class, "frontRightMotor");
        rightMotor2 = hardwareMap.get(DcMotor.class, "backRightMotor");

        // Set the motor directions (if needed)
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.REVERSE); // Reverse if needed
        rightMotor2.setDirection(DcMotor.Direction.REVERSE); // Reverse if needed

        // Wait for the game to start
        waitForStart();

        // Run the robot until the stop button is pressed
        while (opModeIsActive()) {
            // Tank drive control
            double leftPower = -gamepad1.left_stick_y / 2; // Invert to match forward/backward movement
            double rightPower = -gamepad1.right_stick_y / 2; // Invert to match forward/backward movement

            // Set motor powers for tank drive
            leftMotor1.setPower(leftPower);
            leftMotor2.setPower(leftPower);
            rightMotor1.setPower(rightPower);
            rightMotor2.setPower(rightPower);

            // Sleep to avoid using too much CPU time
            idle();
        }
    }
}
