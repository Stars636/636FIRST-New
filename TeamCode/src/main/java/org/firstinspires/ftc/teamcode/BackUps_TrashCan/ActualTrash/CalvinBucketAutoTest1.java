package org.firstinspires.ftc.teamcode.BackUps_TrashCan.ActualTrash;


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

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.QualsCalvin.OGCalvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Autonomous
@Config
public class CalvinBucketAutoTest1 extends LinearOpMode {
    
    PinpointDrive drive;
    public static int fraudOffset = 15;
    public static int fraudWait = 2;
    
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();



        OGCalvin calvin = new OGCalvin(hardwareMap, telemetry);





        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, 0);
        //if you are coming from meep meep, define your initial here
        double xStart = 0;
        double yStart = 0;


        // Set the initial pose of the robot

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:

        double xInitial = 0;
        double yInitial = 0;

        // Set the initial pose of the robot
        //drive.setPoseEstimate(startPose);

        // Define the trajectory for moving forward

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




                 

        //we will create macros in the future, to remove room for error
        waitForStart();
        //calvin.initialPositions();

        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {

            Actions.runBlocking(
                    new SequentialAction(
                            s1,
                            new SleepAction(fraudWait ),
                            s2,
                            new SleepAction(fraudWait ),
                            s3,
                            new SleepAction(fraudWait ),

                            s4,
                            new SleepAction(fraudWait ),
                            s5,
                            new SleepAction(fraudWait ),
                            s6,
                            new SleepAction(fraudWait),
                            s7
                    )

                            //sPickup

                            

            );
            
            /*drive.followTrajectorySequence(b1);

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
