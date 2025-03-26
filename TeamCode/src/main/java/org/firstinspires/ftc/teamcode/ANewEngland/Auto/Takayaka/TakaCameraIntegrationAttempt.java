package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Takayaka;


import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.increment;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawGrabPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawGrabRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawHoverPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawHoverRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristFlat;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristNormalLeft;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltLeft;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltRight;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.BlueObjectPipeline;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.RedObjectPipeline;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.YellowObjectPipeline;
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

    public static class XOffset{
        Calvin calvin;
        double margin = 10;
        public XOffset(HardwareMap hardwareMap){
            calvin = new Calvin(hardwareMap);
        }
        public class RXOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(rDetection.getYOffset() == 100000){
                    return false;
                }
                if(rDetection.getYOffset() < -margin && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                if(rDetection.getYOffset() > margin && hSlidesPos < hSlidesInside){
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
                if(yDetection.getYOffset() < -margin && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                if(yDetection.getYOffset() > margin && hSlidesPos < hSlidesInside){
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
                if(bDetection.getYOffset() < -margin && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                if(bDetection.getYOffset() > margin && hSlidesPos < hSlidesInside){
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
        Calvin calvin;
        PinpointDrive drive;
        public YOffset(HardwareMap hardwareMap){
            calvin = new Calvin(hardwareMap);
            drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));
        }
        double margin = 10;
        double moveY = 1;
        public class RYOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                boolean sampleFound = rDetection.getIsFound();
                double xOffset = rDetection.getXOffset();

                if(xOffset > margin && sampleFound){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, moveY), 0));
                    return true;
                }
                if(xOffset < margin && sampleFound){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -moveY), 0));
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        public Action rYOffset(){
            return new RYOffset();
        }
        public class YYOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                boolean sampleFound = yDetection.getIsFound();
                double xOffset = yDetection.getXOffset();
                if(xOffset > margin && sampleFound){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, moveY), 0));
                    return true;
                }
                if(xOffset < margin && sampleFound){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -moveY), 0));
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        public Action yYOffset(){
            return new YYOffset();
        }
        public class BYOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                boolean sampleFound = bDetection.getIsFound();
                double xOffset = bDetection.getXOffset();
                if(xOffset > margin && sampleFound){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, moveY), 0));
                    return true;
                }
                if(xOffset < margin && sampleFound){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -moveY), 0));
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        public Action bYOffset(){
            return new BYOffset();
        }
    }
    public static class AngleOffset{
        Calvin calvin;
        public AngleOffset(HardwareMap hardwareMap){
            calvin = new Calvin(hardwareMap);
        }
        public class RAOffset implements Action{
            double angle = rDetection.getDetectedAngle();
            boolean sampleFound = rDetection.getIsFound();
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                if(sampleFound && ((angle >= 0 && angle < 30) || (angle <= 180 && angle > 150))){
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                }
                if(sampleFound && angle >= 30 && angle <60){
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                }
                if(sampleFound && angle >= 60 && angle <= 120){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                if(sampleFound && angle > 120 && angle <= 150){
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                }
                return false;
            }
        }
        public Action rAOffset(){
            return new RAOffset();
        }
        public class YAOffset implements Action{
            double angle = yDetection.getDetectedAngle();
            boolean sampleFound = yDetection.getIsFound();
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                if(sampleFound && ((angle >= 0 && angle < 30) || (angle <= 180 && angle > 150))){
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                }
                if(sampleFound && angle >= 30 && angle <60){
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                }
                if(sampleFound && angle >= 60 && angle <= 120){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                if(sampleFound && angle > 120 && angle <= 150){
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                }
                return false;
            }
        }
        public Action yAOffset(){
            return new YAOffset();
        }
        public class BAOffset implements Action{
            double angle = bDetection.getDetectedAngle();
            boolean sampleFound = bDetection.getIsFound();
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                if(sampleFound && ((angle >= 0 && angle < 30) || (angle <= 180 && angle > 150))){
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                }
                if(sampleFound && angle >= 30 && angle <60){
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                }
                if(sampleFound && angle >= 60 && angle <= 120){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                if(sampleFound && angle > 120 && angle <= 150){
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                }
                return false;
            }
        }
        public Action bAOffset(){
            return new BAOffset();
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {

        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));


        calvin = new Calvin(hardwareMap);
        XOffset xOffset = new XOffset(hardwareMap);
        YOffset yOffset = new YOffset(hardwareMap);
        AngleOffset angleOffset = new AngleOffset(hardwareMap);



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

        FtcDashboard.getInstance().startCameraStream(webcam, 10);

        while(opModeIsActive()){
            telemetry.update();
            sleep(100);

            //hang set stuff idk

            Actions.runBlocking(
                    new ParallelAction(
                            xOffset.yXOffset(),
                            yOffset.yYOffset(),
                            angleOffset.yAOffset()
                    )
            );
        }
        // Stop camera streaming when OpMode ends
        webcam.stopStreaming();
    }
}
