package org.firstinspires.ftc.teamcode.Team636Code;

// Import necessary classes

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

// In your Autonomous OpMode
@Autonomous
public class AutoTest1 extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Initialize the drive
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, 0);

        // Set the initial pose of the robot
        drive.setPoseEstimate(startPose);

        // Define the trajectory for moving forward
        Trajectory forwardTrajectory = drive.trajectoryBuilder(startPose)

                .forward(0.01)
                .build();

        Trajectory backTrajectory = drive.trajectoryBuilder(startPose)
                .back(0.01)
                .build();

        TrajectorySequence turntfAround = drive.trajectorySequenceBuilder(startPose)
                .turn((Math.PI/2))


                .build();

        // Wait for the game to start
        waitForStart();

        // Follow each trajectory sequentially
        if (opModeIsActive()) {
            drive.followTrajectory(forwardTrajectory);
            drive.followTrajectorySequence(turntfAround);
            drive.followTrajectory(forwardTrajectory);
        }
    }
}
