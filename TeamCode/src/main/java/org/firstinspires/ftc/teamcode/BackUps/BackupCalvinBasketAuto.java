package org.firstinspires.ftc.teamcode.BackUps;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class BackupCalvinBasketAuto extends LinearOpMode {
    CRServo continuousIntakeLeft;
    CRServo continuousIntakeRight;
    Servo claw;
    Servo shaq;
    Servo clawRotator;

    Servo elbow;
    Servo intakeRotator;

    Servo horizontalSlidesLeft;
    Servo horizontalSlidesRight;

    DcMotor rightBack;

    DcMotor rightFront;

    DcMotor leftBack;

    DcMotor leftFront;

    DcMotor verticalSlidesRight;

    DcMotor verticalSlidesLeft;


    public static double clawOpenPosition;
    public static double clawClosedPosition;


    public static double clawPassivePositionRight;

    public static double clawPassiveRotation;

    public static double clawRetrievePositionRight;

    public static double clawPickUpRotation;

    public static double clawScorePositionRight;

    public static double clawScoreRotation;

    public static double elbowInsidePositionRight;
    public static double elbowOutsidePositionRight;

    public static double intakePassiveRotation;

    public static double intakeActiveRotation;

    public static double intakePickUpRotation;
    public static int verticalSlideHighScoringPositionLimit; //kindly note that gunner will use joystick

    public static double horizontalSlidesInitialPositionLeft;
    public static double horizontalSlidesInitialPositionRight;

    public static double horizontalSlidesExtendedPositionLeft;
    public static double horizontalSlidesExtendedPositionRight;


    public static double specimenPickupPositionRight;
    public static int specimenStartPickupVerticalSlides;

    public static double specimenClawRotation;
    public static int specimenFinishPickupVerticalSlides;
    public static int specimenStartDepositVerticalSlides;
    public static int specimenFinishDepositVerticalSlides;

    @Override
    public void runOpMode() throws InterruptedException {
        //IDEALLY
        //if you copy paste the correct trajectories from another test you've run, then this auto should work
        //or you can test in meep meep but we dont know the correct constants yet cuz the bots not done
        //so yeah

        ElapsedTime et = new ElapsedTime();

        //Initializing all the motors. Do not change this unless we change the wiring
        rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        leftFront = hardwareMap.get(DcMotor.class,"leftFront");

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        verticalSlidesLeft = hardwareMap.get(DcMotor.class,"verticalSlidesLeft");
        verticalSlidesRight = hardwareMap.get(DcMotor.class,"verticalSlidesRight");
        verticalSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        verticalSlidesRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        verticalSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setDirection(DcMotorSimple.Direction.REVERSE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        horizontalSlidesLeft = hardwareMap.get(Servo.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(Servo.class,"horizontalSlidesRight");
        continuousIntakeLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft"); //setPower
        continuousIntakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        claw = hardwareMap.get(Servo.class,"claw");
        shaq = hardwareMap.get(Servo.class,"shaq");
        intakeRotator = hardwareMap.get(Servo.class,"intakeRotator");
        elbow = hardwareMap.get(Servo.class,"elbow");
        intakeRotator = hardwareMap.get(Servo.class,"intakeRotator");

        initialPositions();

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
            grabSample();
            drive.followTrajectorySequence(a1);

            et.reset();
            while (et.milliseconds() < 250);

            lift();
            dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            dropSample();
            passive();

            et.reset();
            while (et.milliseconds() < 250);

            fall();

            et.reset();
            while (et.milliseconds() < 250);
            drive.followTrajectorySequence(a2);

            extend();

            et.reset();
            while (et.milliseconds() < 2000){
                intake();
            }

            passive();
            retrieve();

            et.reset();
            while (et.milliseconds() < 250);

            grab();

            et.reset();
            while (et.milliseconds() < 1000);

            grabSample();

            drive.followTrajectorySequence(a3);

            lift();
            dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            dropSample();
            passive();

            et.reset();
            while (et.milliseconds() < 250);

            fall();

            et.reset();
            while (et.milliseconds() < 250);
            drive.followTrajectorySequence(a4);

            extend();

            et.reset();
            while (et.milliseconds() < 2000){
                intake();
            }

            passive();
            retrieve();

            et.reset();
            while (et.milliseconds() < 250);

            grab();

            et.reset();
            while (et.milliseconds() < 1000);

            grabSample();

            drive.followTrajectorySequence(a5);

            lift();
            dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            dropSample();
            passive();

            et.reset();
            while (et.milliseconds() < 250);

            fall();

            et.reset();
            while (et.milliseconds() < 250);
            drive.followTrajectorySequence(a6);

            extend();

            et.reset();
            while (et.milliseconds() < 2000){
                intake();
            }

            passive();
            retrieve();

            et.reset();
            while (et.milliseconds() < 250);

            grab();

            et.reset();
            while (et.milliseconds() < 1000);

            grabSample();

            drive.followTrajectorySequence(a7);

            lift();
            dunk();

            et.reset();
            while (et.milliseconds() < 2000);

            dropSample();
            passive();

            et.reset();
            while (et.milliseconds() < 250);

            fall();

            drive.followTrajectorySequence(a8);

            dunk();

            et.reset();
            while (et.milliseconds() < 30000);


        }


    }
    public void initialPositions(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        claw.setPosition(clawOpenPosition);
        shaq.setPosition(clawPassivePositionRight);
        clawRotator.setPosition(clawPassiveRotation);
        elbow.setPosition(intakePassiveRotation);
        intakeRotator.setPosition(elbowInsidePositionRight);
    }

    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPositionRight);
        elbow.setPosition(elbowOutsidePositionRight);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void intake(){
        continuousIntakeLeft.setPower(1);
        continuousIntakeRight.setPower(-1);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void eject() {
        continuousIntakeLeft.setPower(-1);
        continuousIntakeRight.setPower(1);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void passive() {
        continuousIntakeLeft.setPower(0);
        continuousIntakeRight.setPower(0);
        shaq.setPosition(clawPassivePositionRight);
        clawRotator.setPosition(clawPassiveRotation);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        intakeRotator.setPosition(intakePickUpRotation);
    }

    public void grabSample(){
        claw.setPosition(clawClosedPosition);
    }

    public void dropSample(){
        claw.setPosition(clawOpenPosition);
    }

    public void grab(){
        shaq.setPosition(clawRetrievePositionRight);
        clawRotator.setPosition(clawPickUpRotation);
    }



    public void rise(){
        verticalSlidesLeft.setPower(gamepad2.left_stick_y);
        verticalSlidesRight.setPower(gamepad2.left_stick_y);
    }
    public void lift(){
        verticalSlidesLeft.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void fall(){
        verticalSlidesLeft.setTargetPosition(0);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(0);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void dunk() {
        shaq.setPosition(clawScorePositionRight);
        clawRotator.setPosition(clawScoreRotation);
    }

    public void specimenPickUp() {
        claw.setPosition(clawOpenPosition);


        verticalSlidesLeft.setTargetPosition(specimenStartPickupVerticalSlides);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(specimenStartPickupVerticalSlides);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shaq.setPosition(specimenPickupPositionRight);
        clawRotator.setPosition(specimenPickupPositionRight);

        ElapsedTime et = new ElapsedTime();
        et.reset();
        while(et.milliseconds() < 5000);
        claw.setPosition(clawClosedPosition);
        et.reset();
        while(et.milliseconds() < 1000);
        verticalSlidesLeft.setTargetPosition(specimenFinishPickupVerticalSlides);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(specimenFinishPickupVerticalSlides);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



}

