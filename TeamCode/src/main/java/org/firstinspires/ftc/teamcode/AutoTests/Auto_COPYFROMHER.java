package org.firstinspires.ftc.teamcode.AutoTests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Config
@Autonomous

public class Auto_COPYFROMHER extends LinearOpMode {

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


        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {

            telemetry.addData("leftfront encoder", calvin.leftFrontCalvin.getCurrentPosition());
            telemetry.addData("leftBack enconder", calvin.leftBackCalvin.getCurrentPosition());
            telemetry.addData("rightfront encoder ???", calvin.rightFrontCalvin.getCurrentPosition());
            telemetry.addData("rightBack enconder ???" , calvin.rightBackCalvin.getCurrentPosition());
            telemetry.addData("have fun", 0);
            telemetry.update();


        }
    }
}
