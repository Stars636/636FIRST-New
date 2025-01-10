package org.firstinspires.ftc.teamcode.AutoTests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Config
@Autonomous

public class AutoSlides extends LinearOpMode {
    DcMotorImplEx SlidesLeft;
    DcMotorImplEx SlidesRight;

    public static int slideHeight = 3000;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);



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

            moveVerticalSlidesTo(slideHeight);
            calvin.dunk();

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
