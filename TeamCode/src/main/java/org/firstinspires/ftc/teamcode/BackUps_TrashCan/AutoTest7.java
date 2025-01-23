package org.firstinspires.ftc.teamcode.BackUps_TrashCan;


import static org.firstinspires.ftc.teamcode.BackUps_TrashCan.AutoTest5.turn;

import static java.lang.Math.PI;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.QualsCalvin.OGCalvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Autonomous
@Config
public class AutoTest7 extends LinearOpMode {
    
    PinpointDrive drive;
    public static int fraudOffset = 20;
    
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();



        OGCalvin calvin = new OGCalvin(hardwareMap, telemetry);





        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));

        // Define the starting pose (e.g., starting point on the field)

       TrajectoryActionBuilder TrajectoryAction1 = drive.actionBuilder(new Pose2d(0,0,0))
               .lineToX(fraudOffset)
               .setTangent(PI/2)
               .lineToY(fraudOffset)
                ;


        Action t1 = TrajectoryAction1.build();



        waitForStart();


        Actions.runBlocking(

                new SequentialAction(
                        t1
                )

        );



        }
}

