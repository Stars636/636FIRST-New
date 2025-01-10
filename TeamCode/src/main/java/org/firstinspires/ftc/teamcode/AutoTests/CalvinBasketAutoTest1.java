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
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;


@Autonomous

public class CalvinBasketAutoTest1 extends LinearOpMode {
    DcMotorImplEx SlidesLeft;
    DcMotorImplEx SlidesRight;
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.initialPositions();
        SlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        SlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");
        SlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        SlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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

            calvin.wait(0.5);

            moveVerticalSlidesTo(3000);
            calvin.dunk();

            calvin.wait(2);

            calvin.dropSample();
            calvin.passive();

            calvin.wait(0.5);

            //calvin.fall();
            moveVerticalSlidesTo(0);

            calvin.wait(2);
            drive.followTrajectorySequence(a2);

            calvin.extend();

            et.reset();
            while (et.milliseconds() < 2000){
                calvin.intake();
            }

            calvin.passive();
            calvin.retrieve();


            calvin.wait(0.5);
            calvin.grab();


            calvin.wait(1);

            calvin.grabSample();

            drive.followTrajectorySequence(a3);

            //calvin.lift();
            moveVerticalSlidesTo(3000);
            calvin.dunk();


            calvin.wait(2);

            calvin.dropSample();
            calvin.passive();

            calvin.wait(0.5);

            //calvin.fall();
            moveVerticalSlidesTo(0);

            calvin.wait(0.5);
            drive.followTrajectorySequence(a4);

            calvin.extend();

            et.reset();
            while (et.milliseconds() < 2000){
                calvin.intake();
            }

            calvin.passive();
            calvin.retrieve();

            calvin.wait(0.5);

            calvin.grab();

            calvin.wait(1);

            calvin.grabSample();

            drive.followTrajectorySequence(a5);

            //calvin.lift();
            calvin.dunk();

            calvin.wait(2);

            calvin.dropSample();
            calvin.passive();

            calvin.wait(0.5);

            //calvin.fall();
            moveVerticalSlidesTo(0);

            et.reset();
            calvin.wait(0.5);
            drive.followTrajectorySequence(a6);

            calvin.extend();

            et.reset();
            while (et.milliseconds() < 2000){
                calvin.intake();
            }

            calvin.passive();
            calvin.retrieve();


            calvin.wait(0.5);

            calvin.grab();


            calvin.wait(1);

            calvin.grabSample();

            drive.followTrajectorySequence(a7);

            moveVerticalSlidesTo(3000);
            calvin.dunk();

            calvin.wait(2);

            calvin.dropSample();
            calvin.passive();

            calvin.wait(0.5);

            moveVerticalSlidesTo(0);

            drive.followTrajectorySequence(a8);

            calvin.hang();

            calvin.wait(0.5);


            calvin.wait(30000);


        }
    }
    public void moveVerticalSlidesTo(int targetPosition) {
        SlidesLeft.setTargetPosition(targetPosition);
        SlidesLeft.setPower(0.8);
        SlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        SlidesRight.setTargetPosition(targetPosition);
        SlidesRight.setPower(0.8);
        SlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
