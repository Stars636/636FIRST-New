package org.firstinspires.ftc.teamcode.AStates.Auto;


import static org.firstinspires.ftc.teamcode.AStates.Auto.Bucket_AutoTest3.fraudSmallWait;
import static org.firstinspires.ftc.teamcode.AStates.Auto.Bucket_Paths.fraudWait;
import static org.firstinspires.ftc.teamcode.AStates.Auto.Specimen_PathsTwo.fraudOffset;
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
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltRight;

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
@Autonomous(name = "Specimen Auto 2/6/2025", group = "STATES")

public class Specimen_Auto_Four extends LinearOpMode {

    //Todo: have hang open before auto
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

        public void moveVerticalSlidesTo(int targetPosition) {
            calvin.vSlidesLeft.setTargetPosition(targetPosition);
            calvin.vSlidesLeft.setPower(0.8);
            calvin.vSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            calvin.vSlidesRight.setTargetPosition(targetPosition);
            calvin.vSlidesRight.setPower(0.8);
            calvin.vSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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

                moveVerticalSlidesTo(0);

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

        public class IntakeWristClockwise implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.intakeWrist.setPosition(intakeWristTiltRight);
                return false;
            }
        }

        public Action intakeWristClockwise() {
            return new IntakeWristClockwise();
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
            return new ElbowHover();
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
            return new ArmHover();
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

        public class DepositArmSpeciPosStart implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawSpeciPosStart);
                return false;
            }
        }

        public Action depositArmSpeciPosStart() {
            return new DepositArm.DepositArmSpeciPosStart();
        }

        public class DepositArmSpeciPosFinish implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositArm.setPosition(depositClawSpeciPosFinish);
                return false;
            }
        }

        public Action depositArmSpeciPosFinish() {
            return new DepositArm.DepositArmSpeciPosFinish();
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

        public class DepositWristSpeciRotStart implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawSpeciRotStart);
                return false;
            }
        }

        public Action depositWristSpeciRotStart() {
            return new DepositWrist.DepositWristSpeciRotStart();
        }

        public class DepositWristSpeciRotFinish implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                calvin.depositWrist.setPosition(depositClawSpeciRotFinish);
                return false;
            }
        }

        public Action depositWristSpeciRotFinish() {
            return new DepositWrist.DepositWristSpeciRotFinish();
        }

    }
    public static  double fraudTurn = 130;

    public static double fraudWait = 1;
    public static double fraudMediumWait = 0.5;
    public static double fraudSmallWait = 0.25;
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

        //Zhang we need using encode for auto but in teleop we need run without encoder
        // so im putting this here for you


        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));

        // Define the starting pose (e.g., starting point on the field)
        //if you are coming from meep meep, define your initial here
        double xStart = 0;
        double yStart = 0;



        Pose2d pickup = new Pose2d(xStart - 8.2, yStart + 40, Math.toRadians(180));
        Pose2d deposit = new Pose2d(xStart - 34, yStart, Math.toRadians(0));

        TrajectoryActionBuilder b1 = drive.actionBuilder(new Pose2d(0, 0, 0))
                .splineToLinearHeading(new Pose2d(xStart - fraudOffset, yStart , Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder fraud = b1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 12, yStart + 38, Math.toRadians(180)),Math.toRadians(90));
        TrajectoryActionBuilder b2 = fraud.endTrajectory().fresh()
                .splineToLinearHeading(pickup, Math.toRadians(90));
        TrajectoryActionBuilder happiness = b2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 19, yStart + 3.5, Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder b3 = happiness.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - fraudOffset, yStart + 3.5, Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder b4 = b3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 26, yStart + 32, Math.toRadians(0)), Math.toRadians(0));

        TrajectoryActionBuilder b5 = b4.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 40, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b6 = b5.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 48, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b7 = b6.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 48, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b8 = b7.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 48, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b9 = b8.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 58, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b10 = b9.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 58, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b11 = b10.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 58, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b12 = b11.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 50, yStart + 64, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder b13 = b12.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 64, Math.toRadians(180)), Math.toRadians(0));

        TrajectoryActionBuilder bPark = b3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - 10, yStart + 64, Math.toRadians(180)), Math.toRadians(0));

        Action s1 = b1.build();
        Action sFraud = fraud.build();
        Action s2 = b2.build();
        Action sJoy = happiness.build();
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
        Action s13 = b13.build();
        Action sPark = bPark.build();




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
                                    depositClaw.depositClawClose(),
                                    hSlides.hSlidesInside(),
                                    intakeArm.armPassive(),
                                    intakeElbow.elbowPassive(),
                                    intakeWrist.neutralPos(),
                                    intakeClaw.openIntakeClaw()
                            ),
                            new ParallelAction(
                                    vSlides.slidesHighChamber(),
                                    new SequentialAction(
                                        new SleepAction(fraudSmallWait),
                                        s1,
                                            new SequentialAction(
                                            new SleepAction(fraudWait),
                                            depositClaw.depositClawOpen()
                                        )
                                    )

                                    // we scored trust
                            ),
                            new SleepAction(fraudSmallWait),
                            new ParallelAction(
                                    sFraud,
                                    new ParallelAction(
                                           vSlides.slidesDown(),
                                            new SequentialAction(
                                                    new ParallelAction(
                                                            vSlides.slidesDown()
                                                    ),
                                                    new SleepAction(fraudMediumWait),
                                                    new ParallelAction(
                                                            vSlides.slidesDown()
                                                    ),
                                                    depositArm.depositArmSpeciPosStart(),
                                                    depositWrist.depositWristSpeciRotStart(),
                                                    new SleepAction(fraudWait+fraudWait+fraudWait+fraudWait),
                                                    new ParallelAction(
                                                            vSlides.slidesDown()
                                                    ),
                                                    s2,
                                                    new ParallelAction(
                                                            vSlides.slidesDown()
                                                    ),
                                                    new SleepAction(fraudWait+fraudMediumWait),
                                                    depositClaw.depositClawClose(),
                                                    new SleepAction(fraudSmallWait),
                                                    new SleepAction(fraudSmallWait)

                                            )
                                    ),
                                    new ParallelAction(
                                            vSlides.slidesHighChamber(),
                                            new SequentialAction(
                                                    new SleepAction(fraudWait)
                                            )
                                    )
                            ),
                            new ParallelAction(
                                    vSlides.slidesHighChamber(),
                                    new SequentialAction(
                                            new ParallelAction(
                                                    vSlides.slidesHighChamber()
                                            ),
                                            new SleepAction(fraudSmallWait),
                                            new ParallelAction(
                                                    vSlides.slidesHighChamber(),
                                                    new SleepAction(fraudSmallWait),
                                                    depositArm.depositArmSpeciPosFinish(),
                                                    depositWrist.depositWristSpeciRotFinish()
                                            ),
                                            new SleepAction(fraudSmallWait),
                                            new SleepAction(fraudSmallWait),
                                            new SleepAction(fraudSmallWait),
                                            sJoy,
                                            new SleepAction(fraudSmallWait),
                                            new SleepAction(fraudSmallWait),
                                            s3,
                                            new SequentialAction(
                                                    new ParallelAction(
                                                            vSlides.slidesHighChamber(),
                                                            depositArm.depositArmSpeciPosFinish(),
                                                            depositWrist.depositWristSpeciRotFinish()
                                                    ),
                                                    new SleepAction(fraudSmallWait),
                                                    depositArm.depositArmSpeciPosFinish(),
                                                    depositWrist.depositWristSpeciRotFinish(),
                                                    new SleepAction(fraudWait),
                                                    depositClaw.depositClawOpen()
                                            )
                                    )

                            ),
                            new ParallelAction(
                                    sPark,
                                    new SequentialAction(
                                            new ParallelAction(
                                                    new SleepAction(fraudWait),
                                                    vSlides.slidesDown(),
                                                    intakeArm.armPassive(),
                                                    intakeElbow.elbowPassive()

                                            )
                                    ),
                                    new SleepAction(FOREVER)//,
                                    /*s5,
                                    s6,
                                    s7,
                                    s8,
                                    s9,
                                    s10,
                                    s11,
                                    s12,
                                    s13*/
                            )
                    )
            );

        }
    }

}
