package org.firstinspires.ftc.teamcode.Team636Code;

// Import necessary classes

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

// In your Autonomous OpMode
@Autonomous
public class AutoTest2 extends LinearOpMode {
    @Override
    public void runOpMode() {
        ElapsedTime et = new ElapsedTime();
        // Initialize the drive
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, 0);

        // Set the initial pose of the robot
        drive.setPoseEstimate(startPose);

        // Define the trajectory for moving forward
        Trajectory forwardTrajectory = drive.trajectoryBuilder(startPose)

                .forward(24)
                .build();


        Trajectory backTrajectory = drive.trajectoryBuilder(startPose)
                .back(10)
                .build();

        TrajectorySequence turntfAround = drive.trajectorySequenceBuilder(startPose)
                .turn((Math.PI))
                .build();

        TrajectorySequence sequence1 = drive.trajectorySequenceBuilder(startPose)
                .forward(25)
                .turn(Math.PI)
                .lineTo(new Vector2d(15))
                .build();


        // Wait for the game to start
        waitForStart();

        // Follow each trajectory sequentially
        while (opModeIsActive()) {
            drive.followTrajectorySequence(sequence1);

            et.reset();
            while (et.milliseconds() < 30000);



        }
    }
}
