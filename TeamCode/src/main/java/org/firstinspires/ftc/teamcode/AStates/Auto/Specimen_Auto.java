package org.firstinspires.ftc.teamcode.AStates.Auto;


import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassivePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassiveRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawScorePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawScoreRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciPosFinish;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciPosStart;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciRotFinish;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciRotStart;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawTransferPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawTransferRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.highBucket;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.highSpecimen;
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
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Config
@Autonomous (name = "Specimen_Auto", group = "Autonomous")

public class Specimen_Auto extends LinearOpMode {




    public static double FOREVER = 30;

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
        public static double integralSum = 0;
        public static double lastError = 0;

        ElapsedTime timer = new ElapsedTime();
        public VerticalSlides(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        /*
        public void moveVerticalSlidesTo(int targetPosition) {
            // obtain the encoder position
            double encoderPosition = calvin.vSlidesLeft.getCurrentPosition();
            // calculate the error
            double error = targetPosition - encoderPosition;
            // rate of change of the error
            double derivative = (error - lastError) / timer.seconds();
            // sum of all error over time
            integralSum = integralSum + (error * timer.seconds());

            double power = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

            calvin.vSlidesLeft.setPower(power);
            calvin.vSlidesRight.setPower(power);
            lastError = error;
            // reset the timer for next time
            timer.reset();
        } */

        public void moveVerticalSlidesTo(int targetPosition) {

            calvin.vSlidesLeft.setTargetPosition(targetPosition);
            calvin.vSlidesLeft.setPower(0.8);
            calvin.vSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            calvin.vSlidesRight.setTargetPosition(targetPosition);
            calvin.vSlidesRight.setPower(0.8);
            calvin.vSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        public class SlidesHighBasket implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                moveVerticalSlidesTo(highBucket);


                return false;
            }
        }

        public Action slidesHighBasket() {
            return new SlidesHighBasket();
        }

        public class SlidesHighChamber implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                moveVerticalSlidesTo(highSpecimen);

                return false;
            }
        }

        public Action slidesHighChamber() {
            return new SlidesHighChamber();
        }


        public class SlidesDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                moveVerticalSlidesTo(5);

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

        public class DepositArmSpeciPosStart implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawSpeciPosStart);
                return false;
            }
        }

        public Action depositArmSpeciPosStart() {
            return new DepositArmSpeciPosStart();
        }

        public class DepositArmSpeciPosFinish implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawSpeciPosFinish);
                return false;
            }
        }

        public Action depositArmSpeciPosFinish() {
            return new DepositArmSpeciPosFinish();
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

        public class DepositWristSpeciRotStart implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawSpeciRotStart);
                return false;
            }
        }

        public Action depositWristSpeciRotStart() {
            return new DepositWristSpeciRotStart();
        }

        public class DepositWristSpeciRotFinish implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawSpeciRotFinish);
                return false;
            }
        }

        public Action depositWristSpeciRotFinish() {
            return new DepositWristSpeciRotFinish();
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


        double xStart = 0;
        double yStart = 0;
        int fraudOffset = 15;
        double fraudWait = 2.5;
        double fraudMediumWait = 0.75;
        double fraudSmallWait = 0.5;



        // Define the starting pose (e.g., starting point on the field)
        //if you are coming from meep meep, define your initial here


        // Set the initial pose of the robot

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:


        // Set the initial pose of the robot
        //drive.setPoseEstimate(startPose);

        // Define the trajectory for moving forward

        Pose2d pickup = new Pose2d(xStart - 2, yStart + 38, Math.toRadians(180));
        Pose2d deposit = new Pose2d(xStart - 34, yStart, Math.toRadians(0));

        TrajectoryActionBuilder b1 = drive.actionBuilder(new Pose2d(0, 0, 0))
                .splineToLinearHeading(new Pose2d(xStart - fraudOffset, yStart , Math.toRadians(0)), Math.toRadians(0));

        TrajectoryActionBuilder b2 = b1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 26, yStart + 32, Math.toRadians(0)), Math.toRadians(0));

        TrajectoryActionBuilder b3 = b2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 40, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b4 = b3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 48, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b5 = b4.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 48, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b6 = b5.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 48, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b7 = b6.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 58, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b8 = b7.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 58, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b9 = b8.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 58, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b10 = b9.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 64, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b11 = b10.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 64, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b12 = b11.endTrajectory().fresh()
                .splineToLinearHeading(pickup, Math.toRadians(90));

        TrajectoryActionBuilder bDeposit = b12.endTrajectory().fresh()
                .splineToLinearHeading(deposit, Math.toRadians(90));

        TrajectoryActionBuilder bPickup = bDeposit.endTrajectory().fresh()
                .splineToLinearHeading(pickup, Math.toRadians(90));


        Action s1 = b1.build();
        Action s2 = b2.build();
        Action s3 = b3.build();
        Action s4 = b4.build();
        Action s5 = b5.build();
        Action s6 = b6.build();
        Action s7 = b7.build();
        Action s8 = b8.build();
        Action s9 = b9.build();
        Action s10 = b10.build();
        Action s11 = b11.build();
        Action s12 = b12.build();
        Action sDeposit = bDeposit.build();
        Action sPickup = bPickup.build();


        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            calvin.servHangLeft.setPosition(0);
            calvin.servHangRight.setPosition(0);
            Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    depositArm.depositArmSpeciPosFinish(),
                                    depositWrist.depositWristSpeciRotFinish(),
                                    depositClaw.depositClawOpen(),
                                    hSlides.hSlidesInside(),
                                    intakeArm.armPassive(),
                                    intakeElbow.elbowPassive(),
                                    intakeWrist.neutralPos(),
                                    intakeClaw.openIntakeClaw(),
                                    vSlides.slidesHighChamber()
                            ),
                            new SequentialAction(
                                    new SleepAction(fraudSmallWait),
                                    s1
                            // we scored trust
                            ),
                            new SequentialAction(
                                    new SleepAction(fraudSmallWait),
                                    s2,
                                    new SleepAction(fraudSmallWait),
                                    s3,
                                    new SleepAction(fraudSmallWait),
                                    s4,
                                    new SleepAction(fraudSmallWait),
                                    s5,
                                    new SleepAction(fraudSmallWait),
                                    s6,
                                    new SleepAction(fraudSmallWait),
                                    s7,
                                    new SleepAction(fraudSmallWait),
                                    s8,
                                    new SleepAction(fraudSmallWait),
                                    s9,
                                    new SleepAction(fraudSmallWait),
                                    s10,
                                    new SleepAction(fraudSmallWait),
                                    s11,
                                    new SleepAction(fraudSmallWait),
                                    s12,
                                    new SleepAction(fraudSmallWait)


                            ),
                            new SequentialAction(

                            )


                    )
            );

        }
    }

}
