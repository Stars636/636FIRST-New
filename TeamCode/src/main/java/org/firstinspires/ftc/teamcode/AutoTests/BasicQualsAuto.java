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






        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, Math.PI/2);
        //if you are coming from meep meep, define your initial here
        //double xStart;
        //double yStart;

        // Set the initial pose of the robot
        drive.setPoseEstimate(startPose);

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:


        // Define the trajectories for moving forward


        //we will create macros in the future, to remove room for error
        waitForStart();

        calvin.initialPositions();
        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {
            calvin.extend();
            calvin.wait(30000);
        }
    }
}