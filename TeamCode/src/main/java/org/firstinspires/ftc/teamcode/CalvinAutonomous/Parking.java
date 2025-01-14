package org.firstinspires.ftc.teamcode.CalvinAutonomous;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinMacros.FullExtension;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinMacros.SpecimenAutoStartPosition;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.BigNate;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;


@Autonomous(name = "Bucket Auto")
public class Parking extends LinearOpMode {
    PinpointDrive drive;
    BigNate calvin = new BigNate(hardwareMap);
    // runs on init press

    ElapsedTime timer = new ElapsedTime();
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

        calvin.actionMacro(SpecimenAutoStartPosition) ;
        calvin.actionMacro(FullExtension);
        //updates the bot after 7 seconds
        //i think the human player can place something in the zone too right?
        telemetry.addData("timer", timer.milliseconds());
        while (timer.milliseconds() < 7000) calvin.tick();



    }
}
