package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Takayaka;


import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
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
import org.openftc.easyopencv.OpenCvCamera;
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
    public static double increment = 0.0001;
    @Config
    public static class YOffset{
        Calvin calvin;
        public static double margin = 20;
        public YOffset(HardwareMap hardwareMap){
            calvin = new Calvin(hardwareMap);
        }
        public class RYOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(!rDetection.getIsFound()){
                    return false;
                }
                else if(rDetection.getYOffset() < -margin && hSlidesPos > hSlidesOutside){
                    hSlidesPos -= increment;
                    calvin.hSlidesLeft.setPosition(hSlidesPos);
                    calvin.hSlidesRight.setPosition(hSlidesPos);
                    return true;
                }
                else if(rDetection.getYOffset() > margin && hSlidesPos < hSlidesInside){
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
        public Action rYOffset(){
            return new RYOffset();
        }
        public class YYOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(!yDetection.getIsFound()){
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
        public Action yYOffset(){
            return new YYOffset();
        }
        public class BYOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double hSlidesPos = calvin.hSlidesLeft.getPosition();

                if(!bDetection.getIsFound()){
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
        public Action bYOffset(){
            return new BYOffset();
        }

    }
    @Config
    public static class XOffset{
        Calvin calvin;
        PinpointDrive drive;
        public XOffset(HardwareMap hardwareMap){
            calvin = new Calvin(hardwareMap);
            drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));
        }

        public static double margin = 20;
        public static double moveY = 0.3;
        public class RXOffset implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                double xOffset = rDetection.getXOffset();

                if(!rDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                    return false;
                }
                if(xOffset > margin && rDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, moveY), 0));
                    return true;
                }
                if(xOffset < margin && rDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -moveY), 0));
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
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                double xOffset = yDetection.getXOffset();
                if(!yDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                    return false;
                }
                if(xOffset > margin && yDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, moveY), 0));
                    return true;
                }
                if(xOffset < margin && yDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -moveY), 0));
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
            public boolean run(@NonNull TelemetryPacket telemetryPacket){
                double xOffset = bDetection.getXOffset();
                if(!bDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                    return false;
                }
                if(xOffset > margin && bDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, moveY), 0));
                    return true;
                }
                if(xOffset < margin && bDetection.getIsFound()){
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -moveY), 0));
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
                if(!rDetection.getIsFound()){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                else if(sampleFound && ((angle >= 0 && angle < 30) || (angle <= 180 && angle > 150))){
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                }
                else if(sampleFound && angle >= 30 && angle <60){
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                }
                else if(sampleFound && angle >= 60 && angle <= 120){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                else if(sampleFound && angle > 120 && angle <= 150){
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
                if(!yDetection.getIsFound()){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                else if(sampleFound && ((angle >= 0 && angle < 30) || (angle <= 180 && angle > 150))){
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                }
                else if(sampleFound && angle >= 30 && angle <60){
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                }
                else if(sampleFound && angle >= 60 && angle <= 120){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                else if(sampleFound && angle > 120 && angle <= 150){
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
                if(!bDetection.getIsFound()){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                else if(sampleFound && ((angle >= 0 && angle < 30) || (angle <= 180 && angle > 150))){
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                }
                else if(sampleFound && angle >= 30 && angle <60){
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                }
                else if(sampleFound && angle >= 60 && angle <= 120){
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
                else if(sampleFound && angle > 120 && angle <= 150){
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

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error:", errorCode);
            }
        });

        rDetection = new RedObjectPipeline(webcam);
        yDetection = new YellowObjectPipeline(webcam);
        bDetection = new BlueObjectPipeline(webcam);
        webcam.setPipeline(bDetection);

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Yellow X Offset", yDetection.getXOffset());
            telemetry.addData("Yellow Y Offset", yDetection.getYOffset());
            telemetry.addData("Yellow Angle", yDetection.getDetectedAngle());
            telemetry.addData("Blue X Offset", bDetection.getXOffset());
            telemetry.addData("Blue Y Offset", bDetection.getYOffset());
            telemetry.addData("Blue Angle", bDetection.getDetectedAngle());
            telemetry.update();
            FtcDashboard.getInstance().startCameraStream(webcam, 10);

            //hang set stuff idk

            Actions.runBlocking(
                    new ParallelAction(
                            xOffset.bXOffset(),
                            yOffset.bYOffset(),
                            angleOffset.bAOffset()
                    )
            );
        }
        // Stop camera streaming when OpMode ends
        webcam.stopStreaming();
    }
}
