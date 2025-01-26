package org.firstinspires.ftc.teamcode.ATests;


import static java.lang.Math.PI;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

@Autonomous
@Config
public class AutoTest5 extends LinearOpMode {
    PinpointDrive drive;
    public static int fraudOffset = 20;
    public static double turn = Math.PI;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();



        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));

        Action TrajectoryAction1 = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(0, fraudOffset), PI)
                .build();
        Action Traj2 = drive.actionBuilder(new Pose2d(fraudOffset,0,0))
                .splineTo(new Vector2d(fraudOffset, fraudOffset), PI)
                .build();


        waitForStart();


        Actions.runBlocking(
                new SequentialAction(
                        TrajectoryAction1,
                        Traj2
                )

        );



    }
}
