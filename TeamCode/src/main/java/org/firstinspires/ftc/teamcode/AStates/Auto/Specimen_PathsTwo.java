package org.firstinspires.ftc.teamcode.AStates.Auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Autonomous
@Config
@Disabled
public class Specimen_PathsTwo extends LinearOpMode {
    
    PinpointDrive drive;
    public static double fraudOffset = 26.5;
    
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();



        Calvin calvin = new Calvin(hardwareMap);
        //Zhang we need using encode for auto but in teleop we need run without encoder
        // so im putting this here for you


        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));

        // Define the starting pose (e.g., starting point on the field)
        //if you are coming from meep meep, define your initial here
        double xStart = 0;
        double yStart = 0;


        // Set the initial pose of the robot

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:

        Pose2d pickup = new Pose2d(xStart - 8, yStart + 38, Math.toRadians(180));
        Pose2d deposit = new Pose2d(xStart - 34, yStart, Math.toRadians(0));

        TrajectoryActionBuilder b1 = drive.actionBuilder(new Pose2d(0, 0, 0))
                .splineToLinearHeading(new Pose2d(xStart - fraudOffset, yStart , Math.toRadians(0)), Math.toRadians(0));
        TrajectoryActionBuilder b2 = b1.endTrajectory().fresh()
                .splineToLinearHeading(pickup, Math.toRadians(90));
        TrajectoryActionBuilder b3 = b2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart - fraudOffset, yStart , Math.toRadians(0)), Math.toRadians(0));
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
        Action s13 = b13.build();


                 

        //we will create macros in the future, to remove room for error
        waitForStart();
        //calvin.initialPositions();

        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {

            Actions.runBlocking(
                    new SequentialAction(

                            s1,
                            s2,
                            s3,
                            s4,
                            s5,
                            s6,
                            s7,
                            s8,
                            s9,
                            s10,
                            s11,
                            s12,
                            s13

                    )
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
