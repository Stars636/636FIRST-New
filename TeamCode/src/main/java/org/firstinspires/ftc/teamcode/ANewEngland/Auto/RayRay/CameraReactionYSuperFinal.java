package org.firstinspires.ftc.teamcode.ANewEngland.Auto.RayRay;


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

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
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
public class CameraReactionYSuperFinal extends LinearOpMode {
    @Config
    public static class OffsetY {
        Calvin calvin;
        PinpointDrive drive;
        OpenCvWebcam webcam;
        RedObjectPipeline rPipeline;
        YellowObjectPipeline yPipeline;
        BlueObjectPipeline bPipeline;

        public static double INVALID = 100000;
        public static double power = 0.35;
        public static int pipeline = 0;
        private int tickerX = 0;
        private int tickerY = 0;
        public static final int checker = 5;

        private static int notFoundTickerY = 0;
        private static int notFoundTickerX = 0;
        public static int moveOn = 5;
        public static double minPosition = 0.74;
        public static double maxPosition = 1;
        public static double step = 0.005;
        public static double deadzone = 40;
        public static double maxOffset = 100;
        public static double minPower = 0.1;

        public OffsetY(HardwareMap hardwareMap, Pose2d pose) {
            calvin = new Calvin(hardwareMap);
            //this.drive = new PinpointDrive(hardwareMap, pose);
            drive = new PinpointDrive(hardwareMap, pose);
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

            // OR...  Do Not Activate the Camera Monitor View
            //webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));

            /*
             * Specify the image processing pipeline we wish to invoke upon receipt
             * of a frame from the camera. Note that switching pipelines on-the-fly
             * (while a streaming session is in flight) *IS* supported.
             */

            rPipeline = new RedObjectPipeline(webcam);
            yPipeline = new YellowObjectPipeline(webcam);
            bPipeline = new BlueObjectPipeline(webcam);
            webcam.setPipeline(bPipeline);

            /*
             * Open the connection to the camera device. New in v1.4.0 is the ability
             * to open the camera asynchronously, and this is now the recommended way
             * to do it. The benefits of opening async include faster init time, and
             * better behavior when pressing stop during init (i.e. less of a chance
             * of tripping the stuck watchdog)
             *
             * If you really want to open synchronously, the old method is still available.
             */
            // Timeout for obtaining permission is configurable. Set before opening.
            webcam.setMillisecondsPermissionTimeout(5000);
            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    /*
                     * Tell the webcam to start streaming images to us! Note that you must make sure
                     * the resolution you specify is supported by the camera. If it is not, an exception
                     * will be thrown.
                     *
                     * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                     * supports streaming from the webcam in the uncompressed YUV image format. This means
                     * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                     * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                     *
                     * Also, we specify the rotation that the webcam is used in. This is so that the image
                     * from the camera sensor can be rotated such that it is always displayed with the image upright.
                     * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                     * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                     * away from the user.
                     */
                    FtcDashboard.getInstance().startCameraStream(webcam,10);
                    webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode)
                {
                    /*
                     * This will be called if the camera could not be opened
                     */

                }
            });


        }
        public class startStreaming implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
                return false;
            }
        }
        public Action streamStart() {
            return new startStreaming();
        }


        public class stopStreaming implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                webcam.stopStreaming();
                return false;
            }
        }
        public Action streamStop() {
            return new stopStreaming();
        }

        public boolean XOffsetAction(@NonNull TelemetryPacket telemetryPacket, double xOffset, boolean found) {
            if (!found) { //if you don't detect anything, don't move
                notFoundTickerX++;

                if (notFoundTickerX >= moveOn) {
                    notFoundTickerX = 0;
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, 0),
                            0
                    ));
                    return false;
                }
                return true;
            }
            notFoundTickerX = 0;

            if (Math.abs(xOffset) < deadzone) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0),
                        0
                ));
                tickerX++;
                if (tickerX >= checker) {
                    tickerX = 0;
                    return false;
                }
                return true;
            }


            double adjustedPower = power;

            if (xOffset > 0) {
                tickerX = 0;
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, -adjustedPower),
                        0
                ));
            } else {
                tickerX = 0;
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, adjustedPower),
                        0
                ));
            }


            telemetryPacket.put("X Offset", xOffset);
            telemetryPacket.put("Drive Power", adjustedPower);

            tickerX = 0;
            return true;
        }

        public boolean YOffsetAction(@NonNull TelemetryPacket telemetryPacket,double yOffset, boolean found) {
            double currentPos = calvin.hSlidesLeft.getPosition();
            telemetryPacket.put("position",currentPos);
            telemetryPacket.put("Y Offset", yOffset);
            if (!found) { //if you don't detect anything, don't move
                notFoundTickerY++;
                if (notFoundTickerY >= moveOn) {
                    notFoundTickerY = 0;
                    return false;
                }
                return true;
            }
            notFoundTickerY = 0;

            if (Math.abs(yOffset) < deadzone) { //if its in range, don't move
                tickerY++;
                if (tickerY >= checker) { //only consider it done when its been in range for 5 calculations
                    //this is so it doesn't stop immediately if it sweeps past it
                    tickerY = 0;
                    return false;
                }
                return true;
            }


            double targetPos = currentPos;

            if (yOffset > 10) {
                //targetPos = Math.max(minPosition, currentPos - step);
                targetPos = Math.min(maxPosition, currentPos + step);
            } else if (yOffset < -10) {
                //targetPos = Math.min(maxPosition, currentPos + step);
                targetPos = Math.max(minPosition, currentPos - step);
            }
          
            //i live my life in fear
            if (targetPos <= minPosition) {
                targetPos = minPosition; //double checking, i dont want our servos breaking
            }
            if (targetPos >= maxPosition) {
                targetPos = maxPosition;
            }
            calvin.hSlidesLeft.setPosition(targetPos);
            calvin.hSlidesRight.setPosition(targetPos);

            telemetryPacket.put("Slide Position", targetPos);

            tickerY = 0;
            return true;
        }
        public class YOffsetRedSide implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                 //prevents setting the pipelines at the same time
                //synchronized (Offset.this) {
                    if (pipeline != 0) {
                        webcam.setPipeline(rPipeline);
                        pipeline = 0; // 0 is red, 1 is yellow, 2 is blue
                    }
                //}
                double yOffset = rPipeline.getYOffset(); //
                boolean found = rPipeline.getIsFound();
                return YOffsetAction(telemetryPacket,yOffset, found);
            }
        }
        public Action YOffsetRed() {
            return new YOffsetRedSide();
        }
        public class YOffsetBlueSide implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                //synchronized (Offset.this) {
                    if (pipeline != 2) {
                        webcam.setPipeline(bPipeline);
                        pipeline = 2;
                    }
                //}
                double yOffset = bPipeline.getYOffset();
                boolean found = bPipeline.getIsFound();
                return YOffsetAction(telemetryPacket,yOffset, found);
            }
        }
        public Action YOffsetBlue() {
            return new YOffsetBlueSide();
        }
        public class YOffsetYellow implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
               // synchronized (Offset.this) {
                    if (pipeline != 1) {
                        webcam.setPipeline(yPipeline);
                        pipeline = 1;
                    }
                //}
                double yOffset = yPipeline.getYOffset();
                boolean found = yPipeline.getIsFound();
                return YOffsetAction(telemetryPacket,yOffset, found);
            }
        }
        public Action YOffsetYellow() {
            return new YOffsetYellow();
        }
        public class XOffsetYellow implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
               // synchronized (Offset.this) {
                    if (pipeline != 1) {
                        webcam.setPipeline(yPipeline);
                        pipeline = 1;
                    }
               //}
                double xOffset = yPipeline.getXOffset();
                boolean found = yPipeline.getIsFound();
                return XOffsetAction(telemetryPacket,xOffset,found);
            }
        }
        public Action XOffsetYellow() {
            return new XOffsetYellow();
        }


        public class XOffsetRed implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                //synchronized (Offset.this) {
                    if (pipeline != 0) {
                        webcam.setPipeline(rPipeline);
                        pipeline = 0;
                    }
              // }
                double xOffset = rPipeline.getXOffset();
                boolean found = rPipeline.getIsFound();
                return XOffsetAction(telemetryPacket,xOffset,found);
            }
        }
        public Action XOffsetRed() {
            return new XOffsetRed();
        }
        public class XOffsetBlue implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                //synchronized (Offset.this) {
                    if (pipeline != 2) {
                        webcam.setPipeline(bPipeline);
                        pipeline = 2;
                    }
                //}
                double xOffset = bPipeline.getXOffset();
                boolean found = bPipeline.getIsFound();
                return XOffsetAction(telemetryPacket,xOffset,found);

            }
        }
        public Action XOffsetBlue() {
            return new XOffsetBlue();
        }
    }
    OffsetY offset;

    @Override
    public void runOpMode() throws InterruptedException {

        offset = new OffsetY(hardwareMap,new Pose2d(0,0,0));
        waitForStart();
        while(opModeIsActive()) {
            Actions.runBlocking(
                    new ParallelAction(
                            offset.YOffsetBlue()
                    )
            );
        }

    }
}
// if (yOffset > 10) {
//                    targetPos = Math.min(maxPosition, currentPos + step);
//                } else if (yOffset < -10) {
//                    targetPos = Math.max(minPosition, currentPos - step);
//                }
