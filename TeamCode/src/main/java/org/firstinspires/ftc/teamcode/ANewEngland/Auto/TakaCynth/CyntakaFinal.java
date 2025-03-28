package org.firstinspires.ftc.teamcode.ANewEngland.Auto.TakaCynth;

import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;

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
@Autonomous (name = "PowerOfConnectionPt2", group = "NEAuto")
public class CyntakaFinal extends LinearOpMode {

    public static int numX = 20;
    public static int numY = 20;
    public static double increment = 0.0003;
    public static double power = 0.4;
    public static int pipeline = 0; //0 = red; 1 = yellow; 2 = blue;


    public static class Cameraing {

        Calvin calvin;
        PinpointDrive drive;
        OpenCvWebcam webcam;
        RedObjectPipeline redObjectPipeline;
        //private SampleDetectionFinal.RedObjectPipeline redObjectPipeline;
        BlueObjectPipeline blueObjectPipeline;
        //private SampleSplitPlusColor.YellowObjectPipeline yellowObjectPipeline;
        YellowObjectPipeline yellowObjectPipeline;

        public Cameraing(HardwareMap hardwareMap, PinpointDrive drive) {

            calvin = new Calvin(hardwareMap);
            this.drive = drive;
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
            );
            webcam = OpenCvCameraFactory.getInstance().createWebcam(
                    hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "Webcam 1"),
                    cameraMonitorViewId
            );
            redObjectPipeline = new RedObjectPipeline(webcam);
            blueObjectPipeline = new BlueObjectPipeline(webcam);
            yellowObjectPipeline = new YellowObjectPipeline(webcam);

            webcam.setPipeline(blueObjectPipeline);

            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    FtcDashboard.getInstance().startCameraStream(webcam,10);
                    webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode) {
                    //telemetry.addData("Camera Error:", errorCode);
                }
            });

        }

        public boolean XOffset(@NonNull TelemetryPacket telemetryPacket, double xOffset, boolean found) {
            telemetryPacket.put("xOffset", xOffset);
            telemetryPacket.put("isFound", found);

            if (found) {
                if (xOffset > numX) {
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -power), 0));
                    return true;
                } else if (xOffset < -numX) {
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, power), 0));
                    return true;
                } else {
                    drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                    return false;
                }
            }
            return false;
        }

        public boolean YOffset (@NonNull TelemetryPacket telemetryPacket, double yOffset, boolean found) {
            telemetryPacket.put("yOffset", yOffset);
            telemetryPacket.put("isFound", found);

            double hSlidesPos = calvin.hSlidesLeft.getPosition();

            if(!found){
                return false;
            }
            if(yOffset < -numY && hSlidesPos > hSlidesOutside){
                hSlidesPos -= increment;
                calvin.hSlidesLeft.setPosition(hSlidesPos);
                calvin.hSlidesRight.setPosition(hSlidesPos);
                return true;
            }
            if(yOffset > numY && hSlidesPos < hSlidesInside){
                hSlidesPos += increment;
                calvin.hSlidesLeft.setPosition(hSlidesPos);
                calvin.hSlidesRight.setPosition(hSlidesPos);
                return true;
            }

            return false;

        }

        public class YOffsetRed implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (pipeline != 0) {
                    webcam.setPipeline(redObjectPipeline);
                    pipeline = 0;
                }

                double yOffset = redObjectPipeline.getYOffset();
                boolean isFound = redObjectPipeline.getIsFound();
                return YOffset(telemetryPacket, yOffset, isFound);

            }
        }

        public Action yOffsetRed() {
            return new YOffsetRed();
        }

        public class YOffsetBlue implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (pipeline != 2) {
                    webcam.setPipeline(blueObjectPipeline);
                    pipeline = 2;
                }

                double yOffset = blueObjectPipeline.getYOffset();
                boolean isFound = blueObjectPipeline.getIsFound();
                return YOffset(telemetryPacket, yOffset, isFound);

            }
        }

        public Action yOffsetBlue() {
            return new YOffsetBlue();
        }

        public class YOffsetYellow implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (pipeline != 1) {
                    webcam.setPipeline(yellowObjectPipeline);
                    pipeline = 1;
                }

                double yOffset = yellowObjectPipeline.getYOffset();
                boolean isFound = yellowObjectPipeline.getIsFound();
                return YOffset(telemetryPacket, yOffset, isFound);

            }
        }

        public Action yOffsetYellow() {
            return new YOffsetYellow();
        }

        public class XOffsetRed implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (pipeline != 0) {
                    webcam.setPipeline(redObjectPipeline);
                    pipeline = 0;
                }

                double xOffset = redObjectPipeline.getXOffset();
                boolean isFound = redObjectPipeline.getIsFound();
                return XOffset(telemetryPacket, xOffset, isFound);

            }
        }

        public Action xOffsetRed() {
            return new XOffsetRed();
        }

        public class XOffsetBlue implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (pipeline != 2) {
                    webcam.setPipeline(blueObjectPipeline);
                    pipeline = 2;
                }

                double xOffset = blueObjectPipeline.getXOffset();
                boolean isFound = blueObjectPipeline.getIsFound();
                return XOffset(telemetryPacket, xOffset, isFound);

            }
        }

        public Action xOffsetBlue() {
            return new XOffsetBlue();
        }

        public class XOffsetYellow implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (pipeline != 1) {
                    webcam.setPipeline(yellowObjectPipeline);
                    pipeline = 1;
                }

                double xOffset = yellowObjectPipeline.getXOffset();
                boolean isFound = yellowObjectPipeline.getIsFound();
                return XOffset(telemetryPacket, xOffset, isFound);

            }
        }

        public Action xOffsetYellow() {
            return new XOffsetYellow();
        }


    }



    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addLine("Camera Streaming...");
        telemetry.update();

        PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));
        Cameraing camera = new Cameraing(hardwareMap, drive);

        waitForStart();

        while (opModeIsActive()) {

            Actions.runBlocking(
                    new ParallelAction(
                            camera.xOffsetBlue(),
                            camera.yOffsetBlue()
                    )

            );

        }

        //webcam.stopStreaming();

    }
}
