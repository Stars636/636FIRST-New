package org.firstinspires.ftc.teamcode.AutoTests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;


@Autonomous(name = "Auto Park", group = "A+ Qualifier")

public class BasicParkAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();
        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.initialPositions();
        waitForStart();

        while (opModeIsActive()) {

            et.reset();
            telemetry.update();
            while (et.milliseconds() < 1000) {
                double joystickX = 0;
                double joystickY = -0.3;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }

            et.reset();
            telemetry.update();

            calvin.wait(30000);

        }
    }
}
