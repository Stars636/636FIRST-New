package org.firstinspires.ftc.teamcode.Team636Code;

// Import necessary classes

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

// In your Autonomous OpMode
@Autonomous
public class AutoTest1 extends LinearOpMode {
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

                .forward(25)
                .build();


        TrajectorySequence turnAround = drive.trajectorySequenceBuilder(forwardTrajectory.end())
                .turn((Math.PI))
                .build();


        Trajectory forwardTrajectory2 = drive.trajectoryBuilder(forwardTrajectory.end())

                .forward(25)
                .build();

        Trajectory strafeRight = drive.trajectoryBuilder(forwardTrajectory.end())
                .strafeRight(30)
                .build();

        Trajectory strafeLeft = drive.trajectoryBuilder(strafeRight.end())
                .strafeLeft(30)
                .build();

        Trajectory backTrajectory = drive.trajectoryBuilder(turnAround.end())
                .back(40)
                .build();

        // Wait for the game to start
        waitForStart();

        // Follow each trajectory sequentially
        while (opModeIsActive()) {
            drive.followTrajectory(forwardTrajectory);
            drive.turn(Math.PI);
            drive.followTrajectorySequence(turnAround);
            drive.followTrajectory(strafeRight);
            drive.followTrajectory(strafeLeft);
            drive.followTrajectory(backTrajectory);

            et.reset();
            while (et.milliseconds() < 30000);



        }
    }
}
