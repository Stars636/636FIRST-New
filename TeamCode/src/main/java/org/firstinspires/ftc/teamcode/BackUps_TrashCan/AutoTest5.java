package org.firstinspires.ftc.teamcode.BackUps_TrashCan;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.QualsCalvin.OGCalvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

@Autonomous
@Config
public class AutoTest5 extends LinearOpMode {
    PinpointDrive drive;
    public static int fraudOffset = 25;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();


        OGCalvin calvin = new OGCalvin(hardwareMap, telemetry);


        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));


    }
}
