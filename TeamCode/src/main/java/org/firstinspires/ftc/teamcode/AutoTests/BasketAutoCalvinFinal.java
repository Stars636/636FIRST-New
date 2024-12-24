package org.firstinspires.ftc.teamcode.AutoTests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CalvinFunctions.Calvin;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous

public class BasketAutoCalvinFinal extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap);

        calvin.initialPositions();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, Math.PI/2);
        double xInitial = 0;
        double yInitial = 0;
        // Set the initial pose of the robot
        drive.setPoseEstimate(startPose);

        // Define the trajectory for moving forward

        Pose2d scorePose = new Pose2d(xInitial - 12, yInitial + 12, Math.toRadians(45));
        TrajectorySequence a1 = drive.trajectorySequenceBuilder(startPose)
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .build();
        TrajectorySequence a2 = drive.trajectorySequenceBuilder(a1.end())
                .splineToLinearHeading(new Pose2d(xInitial - 4, yInitial + 30, Math.toRadians(90)), Math.toRadians(45))
                .build();
        TrajectorySequence a3 = drive.trajectorySequenceBuilder(a2.end())
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .build();
        TrajectorySequence a4 = drive.trajectorySequenceBuilder(a3.end())
                .splineToLinearHeading(new Pose2d(xInitial - 14, yInitial + 30, Math.toRadians(90)), Math.toRadians(45))
                .build();
        TrajectorySequence a5 = drive.trajectorySequenceBuilder(a4.end())
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .build();
        TrajectorySequence a6 = drive.trajectorySequenceBuilder(a5.end())
                .splineToLinearHeading(new Pose2d(xInitial - 20, yInitial + 30, Math.toRadians(120)), Math.toRadians(45))
                .build();
        TrajectorySequence a7 = drive.trajectorySequenceBuilder(a6.end())
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .build();
        TrajectorySequence a8 = drive.trajectorySequenceBuilder(a7.end())
                .splineToLinearHeading(new Pose2d(xInitial + 23, yInitial + 64, Math.toRadians(180)), Math.toRadians(45))
                .build();

        //we will create macros in the future, to remove room for error
        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {
            calvin.grabSample();
            drive.followTrajectorySequence(a1);

            et.reset();
            while (et.milliseconds() < 250);

            calvin.lift();
            calvin.dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            calvin.dropSample();
            calvin.passive();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.fall();

            et.reset();
            while (et.milliseconds() < 250);
            drive.followTrajectorySequence(a2);

            calvin.extend();

            et.reset();
            while (et.milliseconds() < 2000){
                calvin.intake();
            }

            calvin.passive();
            calvin.retrieve();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.grab();

            et.reset();
            while (et.milliseconds() < 1000);

            calvin.grabSample();

            drive.followTrajectorySequence(a3);

            calvin.lift();
            calvin.dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            calvin.dropSample();
            calvin.passive();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.fall();

            et.reset();
            while (et.milliseconds() < 250);
            drive.followTrajectorySequence(a4);

            calvin.extend();

            et.reset();
            while (et.milliseconds() < 2000){
                calvin.intake();
            }

            calvin.passive();
            calvin.retrieve();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.grab();

            et.reset();
            while (et.milliseconds() < 1000);

            calvin.grabSample();

            drive.followTrajectorySequence(a5);

            calvin.lift();
            calvin.dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            calvin.dropSample();
            calvin.passive();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.fall();

            et.reset();
            while (et.milliseconds() < 250);
            drive.followTrajectorySequence(a6);

            calvin.extend();

            et.reset();
            while (et.milliseconds() < 2000){
                calvin.intake();
            }

            calvin.passive();
            calvin.retrieve();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.grab();

            et.reset();
            while (et.milliseconds() < 1000);

            calvin.grabSample();

            drive.followTrajectorySequence(a7);

            calvin.lift();
            calvin.dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            calvin.dropSample();
            calvin.passive();

            et.reset();
            while (et.milliseconds() < 250);

            calvin.fall();

            drive.followTrajectorySequence(a8);

            calvin.dunk();

            et.reset();
            while (et.milliseconds() < 30000);


        }
    }
}
