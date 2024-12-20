package org.firstinspires.ftc.teamcode.Team636Code;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class AutoTest4 extends LinearOpMode {
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

        TrajectorySequence a1 = drive.trajectorySequenceBuilder(startPose)
            .splineToLinearHeading(new Pose2d(3, 3, Math.toRadians(-45)), Math.toRadians(0))
            .build();

        TrajectorySequence a2 = drive.trajectorySequenceBuilder(a1.end())
                .splineToLinearHeading(new Pose2d(3, -3, Math.toRadians(0)), Math.toRadians(-45))
                .build();
        TrajectorySequence a3 = drive.trajectorySequenceBuilder(a2.end())
                .splineToLinearHeading(new Pose2d(-3, 3, Math.toRadians(-45)), Math.toRadians(0))
                .build();
        TrajectorySequence a4 = drive.trajectorySequenceBuilder(a3.end())
                .splineToLinearHeading(new Pose2d(3, 0, Math.toRadians(0)), Math.toRadians(-45))
                .build();
        TrajectorySequence a5 = drive.trajectorySequenceBuilder(a4.end())
                .splineToLinearHeading(new Pose2d(-3, 0, Math.toRadians(-45)), Math.toRadians(0))
                .build();
        TrajectorySequence a6 = drive.trajectorySequenceBuilder(a5.end())
                .splineToLinearHeading(new Pose2d(3, -3, Math.toRadians(0)), Math.toRadians(-45))
                .build();
        TrajectorySequence a7 = drive.trajectorySequenceBuilder(a6.end())
                .splineToLinearHeading(new Pose2d(-3, 3, Math.toRadians(-45)), Math.toRadians(0))
                .build();

        // Wait for the game to start
        waitForStart();

        // Follow each trajectory sequentially
        while (opModeIsActive()) {
            drive.followTrajectorySequence(a1);

            et.reset();
            while (et.milliseconds() < 30000);



        }
    }

}
