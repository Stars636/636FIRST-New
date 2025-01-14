package org.firstinspires.ftc.teamcode.CalvinAutonomous;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.BigNate;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Autonomous(name = "Bucket Auto")
public class Auto_COPYFROMHERE extends LinearOpMode {
    PinpointDrive drive;
    BigNate calvin = new BigNate(hardwareMap);
    // runs on init press
    @Override
    public void runOpMode() {
        //initial stuff
        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));
        //declare your variable from meep meep
        double xInitial;
        double yInitial;

        //Make your splines here
        //


        //Build splines here

        //...
        waitForStart();

        //breaks loop when you stop
        //i think...
        if (isStopRequested()) {
            return;
        }
        //run your actions
        //I would be very surprised if this works

        //place your actions in sequential actions

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(

                        ),
                        calvin.actionTick()
                )
        );

    }
}
