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
public class AutoTest3 extends LinearOpMode {
    @Override
    public void runOpMode() {
        ElapsedTime et = new ElapsedTime();
        // Initialize the drive
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, 0);

        // Set the initial pose of the robot
        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(10)
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .splineTo(new Vector2d(5, 6), 0)
                .splineTo(new Vector2d(9, -10), 0)
                .build();

        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                .splineTo(new Vector2d(5, 6), 0)
                .splineTo(new Vector2d(9, -10), 0)
                .build();

        waitForStart();

        while (opModeIsActive()) {
            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            drive.followTrajectory(traj3);

            et.reset();
            while (et.milliseconds() < 30000);

        }

    }
}
