package org.firstinspires.ftc.teamcode.AutoTests;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.specimenStartDepositVerticalSlides;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;


@Autonomous

public class CalvinSpecimenAutoTest1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.initialPositions();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, Math.PI/2);
        //if you are coming from meep meep, define your initial here
        double xStart = 0;
        double yStart = 0;


        // Set the initial pose of the robot
        drive.setPoseEstimate(startPose);

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:

        Pose2d pickup = new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90));
        Pose2d deposit = new Pose2d(xStart, yStart + 34, Math.toRadians(270));

        TrajectorySequence b1 = drive.trajectorySequenceBuilder(startPose)
                .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(270))
                .build();
        TrajectorySequence b2 = drive.trajectorySequenceBuilder(b1.end())
                .splineToLinearHeading(new Pose2d(xStart + 32, yStart + 26, Math.toRadians(270)), Math.toRadians(270))
                .build();
        TrajectorySequence b3 = drive.trajectorySequenceBuilder(b2.end())
                .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b4 = drive.trajectorySequenceBuilder(b3.end())
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b5 = drive.trajectorySequenceBuilder(b4.end())
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b6 = drive.trajectorySequenceBuilder(b5.end())
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b7 = drive.trajectorySequenceBuilder(b6.end())
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b8 = drive.trajectorySequenceBuilder(b7.end())
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b9 = drive.trajectorySequenceBuilder(b8.end())
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b10 = drive.trajectorySequenceBuilder(b9.end())
                .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b11 = drive.trajectorySequenceBuilder(b10.end())
                .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                .build();
        TrajectorySequence b12 = drive.trajectorySequenceBuilder(b11.end())
                .splineToLinearHeading(pickup, Math.toRadians(270))
                .build();
        TrajectorySequence bDeposit = drive.trajectorySequenceBuilder(b12.end())
                .splineToLinearHeading(deposit, Math.toRadians(90))
                .build();
        TrajectorySequence bPickup = drive.trajectorySequenceBuilder(bDeposit.end())
                .splineToLinearHeading(pickup, Math.toRadians(270))
                .build();

        //we will create macros in the future, to remove room for error
        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {
            /*
            drive.followTrajectorySequence(b1);

            calvin.moveVerticalSlidesTo(specimenStartDepositVerticalSlides);
            calvin.wait(0.5);
            calvin.specimenDeposit();
            calvin.wait(2);

            drive.followTrajectorySequence(b2);
            drive.followTrajectorySequence(b3);
            drive.followTrajectorySequence(b4);
            drive.followTrajectorySequence(b5);
            drive.followTrajectorySequence(b6);
            drive.followTrajectorySequence(b7);
            drive.followTrajectorySequence(b8);
            drive.followTrajectorySequence(b9);
            drive.followTrajectorySequence(b10);
            drive.followTrajectorySequence(b11);
            drive.followTrajectorySequence(b12);

            //should i just do a loop?

            //scoring 1st specimen
            calvin.specimenPickUp();
            calvin.wait(1);
            drive.followTrajectorySequence(bDeposit);
            calvin.specimenDeposit();
            calvin.wait(2);
            drive.followTrajectorySequence(bPickup);

            //scoring 2nd specimen
            calvin.specimenPickUp();
            calvin.wait(1);
            drive.followTrajectorySequence(bDeposit);
            calvin.specimenDeposit();
            calvin.wait(2);
            drive.followTrajectorySequence(bPickup);

            //scoring 3rd specimen
            calvin.specimenPickUp();
            calvin.wait(1);
            drive.followTrajectorySequence(bDeposit);
            calvin.specimenDeposit();
            calvin.wait(2);

            //park
            drive.followTrajectorySequence(bPickup);

            calvin.wait(30000);*/

        }
    }
}
