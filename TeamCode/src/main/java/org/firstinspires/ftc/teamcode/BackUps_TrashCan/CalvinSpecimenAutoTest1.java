package org.firstinspires.ftc.teamcode.BackUps_TrashCan;

<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/BackUps_TrashCan/CalvinSpecimenAutoTest1.txt
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.QualsCalvin.OGCalvin.specimenStartDepositVerticalSlides;
=======
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/BackUps_TrashCan/CalvinSpecimenAutoTest1.java


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/BackUps_TrashCan/CalvinSpecimenAutoTest1.txt
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.QualsCalvin.OGCalvin;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
=======
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/BackUps_TrashCan/CalvinSpecimenAutoTest1.java


@Autonomous

public class CalvinSpecimenAutoTest1 extends LinearOpMode {
    
    PinpointDrive drive;
    
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);



        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));

        // Define the starting pose (e.g., starting point on the field)
        Pose2d startPose = new Pose2d(0, 0, Math.PI/2);
        //if you are coming from meep meep, define your initial here
        double xStart = 0;
        double yStart = 0;


        // Set the initial pose of the robot

        // Define the trajectories for moving forward

        //If  a Pose2d is repetitive, define it here:

        Pose2d pickup = new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90));
        Pose2d deposit = new Pose2d(xStart, yStart + 34, Math.toRadians(270));

        TrajectoryActionBuilder b1 = drive.actionBuilder(new Pose2d(0, 0, Math.PI/2))
                .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(270));
        
        TrajectoryActionBuilder b2 = b1.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 32, yStart + 26, Math.toRadians(270)), Math.toRadians(270));
        
        TrajectoryActionBuilder b3 = b2.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 50, Math.toRadians(90)), Math.toRadians(270));

        TrajectoryActionBuilder b4 = b3.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270));
        
        TrajectoryActionBuilder b5 = b4.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 10, Math.toRadians(90)), Math.toRadians(270));
                
        TrajectoryActionBuilder b6 = b5.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270));
                
        TrajectoryActionBuilder b7 = b6.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270));
                 
        TrajectoryActionBuilder b8 = b7.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 10, Math.toRadians(90)), Math.toRadians(270));
                 
        TrajectoryActionBuilder b9 = b8.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270));
                 
        TrajectoryActionBuilder b10 = b9.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 50, Math.toRadians(90)), Math.toRadians(270));
                 
        TrajectoryActionBuilder b11 = b10.endTrajectory().fresh()
                .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 10, Math.toRadians(90)), Math.toRadians(270));
                 
        TrajectoryActionBuilder b12 = b11.endTrajectory().fresh()
                .splineToLinearHeading(pickup, Math.toRadians(270));
                 
        TrajectoryActionBuilder bDeposit = b12.endTrajectory().fresh()
                .splineToLinearHeading(deposit, Math.toRadians(90));
                 
        TrajectoryActionBuilder bPickup = bDeposit.endTrajectory().fresh()
                .splineToLinearHeading(pickup, Math.toRadians(270));


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
                            sDeposit,
                            sPickup,
                            sDeposit,
                            sPickup,
                            sDeposit,
                            sPickup,
                            sDeposit,
                            sPickup
                            
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
