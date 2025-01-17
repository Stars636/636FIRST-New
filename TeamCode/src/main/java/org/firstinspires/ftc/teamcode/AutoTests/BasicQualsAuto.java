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


@Autonomous(name = "Qualifier Auto Park", group = "A+ Qualifier")

public class BasicQualsAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();
        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.initialPositions();
        waitForStart();

        while (opModeIsActive()) {

            calvin.extend();
            calvin.wait(30);

        }
    }
}
