package org.firstinspires.ftc.teamcode.ANewEngland.Auto;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ANewEngland.Camera.MightBeFunctional.SampleDetectionSupreme;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.openftc.easyopencv.OpenCvWebcam;


@Config
@Autonomous


public class AdarshCamera extends LinearOpMode {

    @Config
    public static class Offset {


        Calvin calvin;
        PinpointDrive drive;
        OpenCvWebcam webcam;
        SampleDetectionSupreme.RedObjectPipeline rFind;
        SampleDetectionSupreme.YellowObjectPipeline yFind;
        SampleDetectionSupreme.BlueObjectPipeline bFind;











    }

    @Override
    public void runOpMode() throws InterruptedException {



    }

}


