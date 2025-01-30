package org.firstinspires.ftc.teamcode.AStates.Auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.Claw;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;



@Config
@Autonomous (name = "Specimen_Bucket_Auto", group = "Autonomous")



public class CalvinBucketAutoTest2 extends LinearOpMode {

    //Todo: Claw
    public class Claw {

    }




    PinpointDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));


        ElapsedTime et = new ElapsedTime();
        Calvin calvin = new Calvin(hardwareMap);
        Calvin.Claw claw = new Calvin.Claw();


        double xInitial = 0;
        double yInitial = 0;
        int fraudOffset = 15;
        int fraudWait = 2;

        Pose2d scorePose = new Pose2d(xInitial + 9, yInitial + 14, Math.toRadians(-45));
        TrajectoryActionBuilder a1 = drive.actionBuilder(startPose)
                .splineToLinearHeading(scorePose, Math.toRadians(0));
        TrajectoryActionBuilder a2 = a1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 10, Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder a3 = a2.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(0));
        TrajectoryActionBuilder a4 = a3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 17, Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder a5 = a4.endTrajectory().fresh()
                .splineToLinearHeading(scorePose, Math.toRadians(0));
        TrajectoryActionBuilder a6 = a5.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 15, Math.toRadians(30)), Math.toRadians(0));
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
                            s1,
                            new SleepAction(fraudWait),
                            Calvin.Claw.CloseClaw(),
                            s2,
                            new SleepAction(fraudWait),
                            s3,
                            new SleepAction(fraudWait),

                            s4,
                            new SleepAction(fraudWait),
                            s5,
                            new SleepAction(fraudWait),
                            s6,
                            new SleepAction(fraudWait),
                            s7
                    )
            );
        }
    }

}
