package org.firstinspires.ftc.teamcode.CalvinAutonomous;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmBucketScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmIntakeGrab;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.BucketAutoStartPosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.FullExtension;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.FullRetraction;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.IntakeFullPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.LowBucket;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ReturnToZero;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.SpecimenAutoStartPosition;
import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNate;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Autonomous(name = "Bucket Auto")
public class BucketAutoAttempt1 extends LinearOpMode {
    PinpointDrive drive;
    BigNate calvin = new BigNate(hardwareMap);
    // runs on init press
    @Override
    public void runOpMode() {
        //initial stuff
        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));
       // calvin.vSlidesLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //calvin.vSlidesRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double xInitial = 0;
        double yInitial = 0;
        Pose2d scorePose = new Pose2d(xInitial - 17, yInitial + 4, Math.toRadians(45));

        //Make your splines here

        //Scores the bucket, likely predictably
        TrajectoryActionBuilder bucket1 = drive.actionBuilder(new Pose2d(xInitial, yInitial, PI/2))
                .splineToLinearHeading(scorePose, Math.toRadians(90));


        //Scores the bucket, unpredictably because of submersible
        TrajectoryActionBuilder bucketRisky = drive.actionBuilder(new Pose2d(xInitial, yInitial, PI/2))
                .splineToLinearHeading(scorePose, Math.toRadians(50));

        //all the turn is the try and make up for error
        //first spike mark
        TrajectoryActionBuilder sample1 = bucket1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial - 4, yInitial + 20, Math.toRadians(90)), Math.toRadians(45))
                .turn(5)
                .turn(-5)
                .turn(10)
                .turn(-10);


        TrajectoryActionBuilder bucket2 = sample1.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(90));
        //second spike mark
        TrajectoryActionBuilder sample2 = bucket2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial - 14, yInitial + 20, Math.toRadians(90)), Math.toRadians(45))
                .turn(5)
                .turn(-5)
                .turn(10)
                .turn(-10);
        TrajectoryActionBuilder bucket3 = sample2.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(90));
        //third spike mark
        TrajectoryActionBuilder sample3 = bucket3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial - 20, yInitial + 20, Math.toRadians(110)), Math.toRadians(45))
                .turn(5)
                .turn(-5)
                .turn(10)
                .turn(-10);
        TrajectoryActionBuilder bucket4 = sample3.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(90));

        //a lot more turning haha
        //Submersible attempt one
        TrajectoryActionBuilder sample4 = bucket4.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + 23, yInitial + 65, Math.toRadians(0)), Math.toRadians(50));
        TrajectoryActionBuilder sampleSweep1 = sample4.endTrajectory().fresh()
                .turn(5)
                .turn(-10)
                .turn(15)
                .turn(-20)
                .turn(10);
        TrajectoryActionBuilder bucket5 = sampleSweep1.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(50));

        //submersible attempt two
        TrajectoryActionBuilder sample5 = bucket5.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + 23, yInitial + 55, Math.toRadians(0)), Math.toRadians(50));

        TrajectoryActionBuilder sampleSweep2 = sample5.endTrajectory().fresh()
                .turn(5)
                .turn(-10)
                .turn(15)
                .turn(-20)
                .turn(10);

        TrajectoryActionBuilder bucket6 = sampleSweep2.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(50));









        //Build splines here

        Action b1 = bucket1.build();
        Action b2 = bucket2.build();
        Action b3 = bucket3.build();
        Action b4 = bucket4.build();
        Action b5 = bucket5.build();
        Action b6 = bucket6.build();

        Action s1 = sample1.build();
        Action s2 = sample2.build();
        Action s3 = sample3.build();
        Action s4 = sample4.build();
        Action sS1 = sampleSweep1.build();
        Action s5 = sample5.build();
        Action sS2 = sampleSweep2.build();

        //...
        waitForStart();

        //breaks loop when you stop
        //i think...
        if (isStopRequested()) {
            return;
        }
        //run your actions
        //I would be very surprised if this works
        //Un comment whats below this
        //place your actions in sequential actions


        //if this doesn't work, make actionMacros runMacros
        //if that doesn't work, turn on run using encoder
        //if that doesn't work, work on teleop

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                calvin.actionMacro(BucketAutoStartPosition),
                                calvin.actionMacro(ClawClosed),

                                b1,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000),


                                new ParallelAction(
                                        new SequentialAction(s1),
                                        calvin.actionMacro(FullExtension),
                                        calvin.actionMacro(IntakeFullPower),
                                        calvin.actionWait(3000)
                                ),

                                calvin.actionMacro(FullRetraction),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmIntakeGrab),
                                calvin.actionWait(300),

                                b2,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000),

                                new ParallelAction(
                                        new SequentialAction(s2),
                                        calvin.actionMacro(FullExtension),
                                        calvin.actionMacro(IntakeFullPower),
                                        calvin.actionWait(3000)

                                ),

                                calvin.actionMacro(FullRetraction),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmIntakeGrab),
                                calvin.actionWait(300),

                                b3,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000),

                                new ParallelAction(
                                        new SequentialAction(s3),
                                        calvin.actionMacro(FullExtension),
                                        calvin.actionMacro(IntakeFullPower),
                                        calvin.actionWait(3000)

                                ),

                                calvin.actionMacro(FullRetraction),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmIntakeGrab),
                                calvin.actionWait(300),

                                b4,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000),

                                s4,
                                new ParallelAction(
                                        new SequentialAction(sS1),
                                        calvin.actionMacro(FullExtension),
                                        calvin.actionMacro(IntakeFullPower),
                                        calvin.actionWait(3000)
                                ),

                                calvin.actionMacro(FullRetraction),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmIntakeGrab),
                                calvin.actionWait(300),

                                b5,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000),
                                s5,

                                new ParallelAction(
                                        new SequentialAction(sS2),
                                        calvin.actionMacro(FullExtension),
                                        calvin.actionMacro(IntakeFullPower),
                                        calvin.actionWait(3000)
                                ),

                                calvin.actionMacro(FullRetraction),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmIntakeGrab),
                                calvin.actionWait(300),

                                b6,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000)

                        ),
                        calvin.actionTick()
                )
        );

    }
}
// Scoring Action
/*
bucket,
                                calvin.actionWait(200),
                                calvin.actionMacro(LowBucket),
                                calvin.actionWait(1000),
                                calvin.actionMacro(ArmBucketScore),
                                calvin.actionWait(300),
                                calvin.actionMacro(ClawOpen),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmPassive),
                                calvin.actionWait(300),
                                calvin.actionMacro(ReturnToZero),
                                calvin.actionWait(1000),

 */
//Retrieve Action
/*
calvin.actionMacro(FullRetraction),
                                calvin.actionWait(300),
                                calvin.actionMacro(ArmIntakeGrab),
                                calvin.actionWait(300),
                                calvin.actionMacro(200)
 */
//Sample Action
/*
 new ParallelAction(
                                        new SequentialAction(s1),
                                        calvin.actionMacro(FullExtension),
                                        calvin.actionMacro(IntakeFullPower),
                                        calvin.actionWait(3000)

                                ),
 */
