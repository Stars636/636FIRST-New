package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Takayaka;


import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.increment;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.BlueObjectPipeline;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.RedObjectPipeline;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.YellowObjectPipeline;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.SampleDetectionFinal;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Config
@Autonomous

public class TakaCameraIntegrationAttempt extends LinearOpMode {

    Calvin calvin;
    PinpointDrive drive;
    OpenCvWebcam webcam;
    static RedObjectPipeline rDetection;
    static YellowObjectPipeline yDetection;
    static BlueObjectPipeline bDetection;




    /*public static class Stream{
        OpenCvWebcam webcam;

        public class StartStream{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                return false;
            }
        }

        public class EndStream{

        }
    }
    */

    public static class XOffset{

        Calvin calvin;

        public class RXOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(rDetection.getYOffset() == 100000){
                    return false;
                }
                if(rDetection.getYOffset() < -10 && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                if(rDetection.getYOffset() > 10 && hSlidesPos < hSlidesInside){
                    hSlidesPos += increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                else{
                    return false;
                }
            }

        }

        public Action rXOffset(){
            return new RXOffset();
        }

        public class YXOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(yDetection.getYOffset() == 100000){
                    return false;
                }
                if(yDetection.getYOffset() < -10 && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                if(yDetection.getYOffset() > 10 && hSlidesPos < hSlidesInside){
                    hSlidesPos += increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                else{
                    return false;
                }
            }

        }

        public Action yXOffset(){
            return new YXOffset();
        }

        public class BXOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(bDetection.getYOffset() == 100000){
                    return false;
                }
                if(bDetection.getYOffset() < -10 && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                if(bDetection.getYOffset() > 10 && hSlidesPos < hSlidesInside){
                    hSlidesPos += increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                else{
                    return false;
                }
            }

        }

        public Action bXOffset(){
            return new BXOffset();
        }



    }

    public static class YOffset{

    }



    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));


        calvin = new Calvin(hardwareMap);

        double xInitial = 0;
        double yInitial = 0;
        double offset = 0;

        //insert path stuff below

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );

        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "Webcam 1"),
                cameraMonitorViewId
        );

        rDetection = new RedObjectPipeline(webcam);
        yDetection = new YellowObjectPipeline(webcam);
        bDetection = new BlueObjectPipeline(webcam);
        webcam.setPipeline(rDetection);

        waitForStart();

        while(opModeIsActive()){
            telemetry.update();
            FtcDashboard.getInstance().startCameraStream(webcam, 10);
            sleep(100);

            //hang set stuff idk

            Actions.runBlocking(
                    new SequentialAction(

                    )
            );
        }
        // Stop camera streaming when OpMode ends
        webcam.stopStreaming();
    }
}
