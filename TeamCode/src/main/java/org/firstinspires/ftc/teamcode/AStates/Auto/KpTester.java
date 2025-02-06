package org.firstinspires.ftc.teamcode.AStates.Auto;


import static org.firstinspires.ftc.teamcode.AStates.Auto.PIDController.Kd;
import static org.firstinspires.ftc.teamcode.AStates.Auto.PIDController.Ki;
import static org.firstinspires.ftc.teamcode.AStates.Auto.PIDController.Kp;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassivePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassiveRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawScorePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawScoreRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawTransferPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawTransferRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.highBucket;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawGrabPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawGrabRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawHoverPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawHoverRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawPassivePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawPassiveRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawTransferPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawTransferRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristFlat;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Config
@Autonomous
@Disabled


public class KpTester extends LinearOpMode {


    //Todo: have hang open before auto

    public static class HorizontalSlides {

        Calvin calvin;

        public HorizontalSlides(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class HSlidesOutside implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.hSlidesLeft.setPosition(hSlidesOutside);
                calvin.hSlidesRight.setPosition(hSlidesOutside);
                return false;
            }
        }

        public Action hSlidesOutside() {
            return new HSlidesOutside();
        }

        public class HSlidesInside implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.hSlidesLeft.setPosition(hSlidesInside);
                calvin.hSlidesRight.setPosition(hSlidesInside);
                return false;
            }
        }

        public Action hSlidesInside() {
            return new HSlidesInside();
        }

    }

    public static class IntakeClaw {
        Calvin calvin;

        public IntakeClaw(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class CloseIntakeClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeClaw.setPosition(intakeClawClosed);
                return false;
            }
        }

        public Action closeIntakeClaw() {
            return new CloseIntakeClaw();
        }

        public class OpenIntakeClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeClaw.setPosition(intakeClawOpen);
                return false;
            }
        }

        public Action openIntakeClaw() {
            return new OpenIntakeClaw();
        }
    }

    public static class VerticalSlides {
        Calvin calvin;

        public static double kP = 0.001, kI = 0.001, kD = 0.001;
        public static double integralSum = 0;
        public static double lastError = 0;

        ElapsedTime timer = new ElapsedTime();


        public VerticalSlides(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public void moveVerticalSlidesTo(int targetPosition) {
                // obtain the encoder position
                double encoderPosition = calvin.vSlidesLeft.getCurrentPosition();
                // calculate the error
                double error = targetPosition - encoderPosition;
                // rate of change of the error
                double derivative = (error - lastError) / timer.seconds();
                // sum of all error over time
                integralSum = integralSum + (error * timer.seconds());

                double power = (kP * error) + (kI * integralSum) + (kD * derivative);

                calvin.vSlidesLeft.setPower(power);
                calvin.vSlidesRight.setPower(power);
                lastError = error;
                // reset the timer for next time
                timer.reset();
        }
        public class SlidesUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while(Math.abs(calvin.vSlidesLeft.getCurrentPosition() - highBucket) < 5) {
                    moveVerticalSlidesTo(highBucket);
                }
                return false;
            }
        }

        public Action slidesUp() {
            return new SlidesUp();
        }

        public class SlidesDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while(Math.abs(calvin.vSlidesLeft.getCurrentPosition()) < 5) {
                    moveVerticalSlidesTo(0);
                }
                return false;
            }
        }

        public Action slidesDown() {
            return new SlidesDown();
        }
    }

    public static class IntakeWrist {
        Calvin calvin;

        public IntakeWrist(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class NeutralPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeWrist.setPosition(intakeWristFlat);
                return false;
            }
        }

        public Action neutralPos() {
            return new NeutralPos();
        }

    }

    public static class IntakeElbow {
        Calvin calvin;

        public IntakeElbow(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class ElbowIntake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeElbow.setPosition(intakeClawGrabRot);
                return false;
            }
        }

        public Action elbowIntake() {
            return new ElbowIntake();
        }

        public class ElbowTransfer implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeElbow.setPosition(intakeClawTransferRot);
                return false;
            }
        }

        public Action elbowTransfer() {
            return new ElbowTransfer();
        }

        public class ElbowPassive implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeElbow.setPosition(intakeClawPassiveRot);
                return false;
            }
        }

        public Action elbowPassive() {
            return new ElbowPassive();
        }
        public class ElbowHover implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeElbow.setPosition(intakeClawHoverRot);
                return false;
            }
        }

        public Action elbowHover() {
            return new ElbowPassive();
        }

    }

    public static class IntakeArm {
        Calvin calvin;

        public IntakeArm(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class ArmIntake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeArm.setPosition(intakeClawGrabPos);
                return false;
            }
        }

        public Action armIntake() {
            return new ArmIntake();
        }

        public class ArmTransfer implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeArm.setPosition(intakeClawTransferPos);
                return false;
            }
        }

        public Action armTransfer() {
            return new ArmTransfer();
        }

        public class ArmPassive implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeArm.setPosition(intakeClawPassivePos);
                return false;
            }
        }

        public Action armPassive() {
            return new ArmPassive();
        }
        public class ArmHover implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeArm.setPosition(intakeClawHoverPos);
                return false;
            }
        }

        public Action armHover() {
            return new ArmPassive();
        }

    }

    public static class DepositClaw {
        Calvin calvin;

        public DepositClaw(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class DepositClawOpen implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositClaw.setPosition(depositClawOpen);
                return false;
            }
        }

        public Action depositClawOpen() {
            return new DepositClawOpen();
        }

        public class DepositClawClose implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositClaw.setPosition(depositClawClosed);
                return false;
            }
        }

        public Action depositClawClose() {
            return new DepositClawClose();
        }

    }

    public static class DepositArm {
        Calvin calvin;

        public DepositArm(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class DepositArmPassive implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawPassivePos);
                return false;
            }
        }

        public Action depositArmPassive() {
            return new DepositArmPassive();
        }

        public class DepositArmTransfer implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawTransferPos);
                return false;
            }
        }

        public Action depositArmTransfer() {
            return new DepositArmTransfer();
        }

        public class DepositArmScore implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawScorePos);
                return false;
            }
        }

        public Action depositArmScore() {
            return new DepositArmScore();
        }

    }

    public static class DepositWrist {

        Calvin calvin;

        public DepositWrist(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class DepositWristPassive implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawPassiveRot);
                return false;
            }
        }

        public Action depositWristPassive() {
            return new DepositWristPassive();
        }

        public class DepositWristTransfer implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawTransferRot);
                return false;
            }
        }

        public Action depositWristTransfer() {
            return new DepositWristTransfer();
        }

        public class DepositWristScore implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawScoreRot);
                return false;
            }
        }

        public Action depositWristScore() {
            return new DepositWristScore();
        }

    }

    PinpointDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));


        ElapsedTime et = new ElapsedTime();
        Calvin calvin = new Calvin(hardwareMap);
        IntakeClaw intakeClaw = new IntakeClaw(hardwareMap);
        VerticalSlides vSlides = new VerticalSlides(hardwareMap);
        IntakeWrist intakeWrist = new IntakeWrist(hardwareMap);
        IntakeElbow intakeElbow = new IntakeElbow(hardwareMap);
        IntakeArm intakeArm = new IntakeArm(hardwareMap);
        DepositWrist depositWrist = new DepositWrist(hardwareMap);
        DepositClaw depositClaw = new DepositClaw(hardwareMap);
        DepositArm depositArm = new DepositArm(hardwareMap);
        HorizontalSlides hSlides = new HorizontalSlides(hardwareMap);


        //where hslides cynthia? where hslides
        //please add idk how to do ur thing LOL


        //Zhang we need using encode for auto but in teleop we need run without encoder
        // so im putting this here for you

        //Calvin.Claw claw = new Calvin.Claw();


        double xInitial = 0;
        double yInitial = 0;
        int fraudOffset = 15;
        double fraudWait = 2.5;
        double fraudMediumWait = 0.75;
        double fraudSmallWait = 0.5;

        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));

        // Define the starting pose (e.g., starting point on the field)
        //if you are coming from meep meep, define your initial here


        // Set the initial pose of the robot

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:


        // Set the initial pose of the robot
        //drive.setPoseEstimate(startPose);

        // Define the trajectory for moving forward

        Pose2d scorePose = new Pose2d(xInitial + 9, yInitial + 14, Math.toRadians(-45));
        TrajectoryActionBuilder a1 = drive.actionBuilder(startPose)
                .splineToLinearHeading(scorePose, Math.toRadians(0));
        TrajectoryActionBuilder a2 = a1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 8, Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder a3 = a2.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(0));
        TrajectoryActionBuilder a4 = a3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 17, Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder a5 = a4.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(0));
        TrajectoryActionBuilder a6 = a5.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 15, Math.toRadians(25)), Math.toRadians(0));
        TrajectoryActionBuilder a7 = a6.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(0));


        Action s1 = a1.build();
        Action s2 = a2.build();
        Action s3 = a3.build();
        Action s4 = a4.build();
        Action s5 = a5.build();
        Action s6 = a6.build();
        Action s7 = a7.build();


        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            Actions.runBlocking(
                    new SequentialAction(

                    )
            );

        }
    }

}
