package org.firstinspires.ftc.teamcode.AStates.Auto.Trash;


import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.Camera.SampleSplitPlusColor;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Config
@Autonomous

public class CameraReactions extends LinearOpMode {

    public static class Offset {

        Calvin calvin;
        PinpointDrive drive;
        OpenCvCamera webcam;
        SampleSplitPlusColor.RedObjectPipeline rPipeline;
        SampleSplitPlusColor.YellowObjectPipeline yPipeline;
        SampleSplitPlusColor.BlueObjectPipeline bPipeline;
        static FtcDashboard dashboard;
        static Handler backgroundHandler;


        public static double power = 0.3;
        private int tickerX = 0;
        private int tickerY = 0;
        int checker = 5;


        public Offset(HardwareMap hardwareMap, Telemetry telemetry, Pose2d pose) {
            calvin = new Calvin(hardwareMap);
            MecanumDrive drive = new MecanumDrive(hardwareMap, pose);
            drive = new PinpointDrive(hardwareMap, pose);
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
            rPipeline = new SampleSplitPlusColor.RedObjectPipeline();
            yPipeline = new SampleSplitPlusColor.YellowObjectPipeline();
            bPipeline = new SampleSplitPlusColor.BlueObjectPipeline();
            webcam.setPipeline(rPipeline);

            dashboard = FtcDashboard.getInstance();
            telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
            HandlerThread handlerThread = new HandlerThread("FrameStreamThread");
            handlerThread.start();
            backgroundHandler = new Handler(handlerThread.getLooper());

            webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {

                    webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
                    //FtcDashboard.getInstance().sendImage(rPipeline.getOutput());
                    //if it doesn't work comment this out
                }

                @Override
                public void onError(int errorCode)
                {
                    /*
                     * This will be called if the camera could not be opened
                     */
                    //emo
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

        public class XOffsetRedSide implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                webcam.setPipeline(rPipeline);
                double xOffset = rPipeline.getXOffset();

                if (xOffset == 100000) {
                    return false;
                }

                if (Math.abs(xOffset) < 10) {
                    tickerX++;
                    if (tickerX >= checker) {
                        return false;
                    }
                    return true;
                }

                // Adjust extendo
                double currentPos = calvin.hSlidesLeft.getPosition();
                if (currentPos < 0.999) {
                    calvin.hSlidesLeft.setPosition(currentPos + 0.01);
                    calvin.hSlidesRight.setPosition(currentPos + 0.01);
                }
                if (currentPos > 0.741) {
                    calvin.hSlidesLeft.setPosition(currentPos - 0.01);
                    calvin.hSlidesRight.setPosition(currentPos - 0.01);
                }

                tickerX = 0; // Reset ticker on movement
                return true;
            }
        }
        public Action XOffsetRed() {
            return new XOffsetRedSide();
        }

        public class XOffsetBlueSide implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                webcam.setPipeline(bPipeline);
                double xOffset = bPipeline.getXOffset();

                if (xOffset == 100000) {
                    return false;
                }

                if (Math.abs(xOffset) < 10) {
                    tickerX++;
                    if (tickerX >= checker) {
                        return false;
                    }
                    return true;
                }

                // Adjust extendo
                double currentPos = calvin.hSlidesLeft.getPosition();
                if (currentPos < 0.999) {
                    calvin.hSlidesLeft.setPosition(currentPos + 0.01);
                    calvin.hSlidesRight.setPosition(currentPos + 0.01);
                }
                if (currentPos > 0.741) {
                    calvin.hSlidesLeft.setPosition(currentPos - 0.01);
                    calvin.hSlidesRight.setPosition(currentPos - 0.01);
                }

                tickerX = 0; // Reset ticker on movement
                return true;
            }
        }
        public Action XOffsetBlue() {
            return new XOffsetBlueSide();
        }
        public class XOffsetYellow implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                webcam.setPipeline(yPipeline);
                double xOffset = yPipeline.getXOffset();

                if (xOffset == 100000) {
                    return false;
                }

                if (Math.abs(xOffset) < 10) {
                    tickerX++;
                    if (tickerX >= checker) {
                        return false;
                    }
                    return true;
                }

                // Adjust extendo
                double currentPos = calvin.hSlidesLeft.getPosition();
                if (xOffset > 10 && currentPos < 0.999) {
                    calvin.hSlidesLeft.setPosition(currentPos + 0.01);
                    calvin.hSlidesRight.setPosition(currentPos + 0.01);
                }
                if (xOffset < -10 && currentPos > 0.741) {
                    calvin.hSlidesLeft.setPosition(currentPos - 0.01);
                    calvin.hSlidesRight.setPosition(currentPos - 0.01);
                }

                tickerX = 0; // Reset ticker on movement
                return true;
            }
        }
        public Action XOffsetYellow() {
            return new XOffsetYellow();
        }


        public class YOffsetYellow implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double yOffset = yPipeline.getYOffset();

                if (yOffset == 100000) {
                    return false;
                }
                if (Math.abs(yOffset) < 10) {
                    tickerY++;
                    if (tickerY >= checker) {
                        return false;
                    }
                    return true;
                }
                if (yOffset > 10) {
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, power),
                            0
                    ));

                    drive.updatePoseEstimate();
                    return true;
                }
                if (yOffset < -10) {
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, -power),
                            0
                    ));

                    drive.updatePoseEstimate();
                    return true;
                }

                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0),
                        0
                ));
                drive.updatePoseEstimate();

                tickerY = 0;

                return false;
            }
        }
        public Action YOffsetYellow() {
            return new YOffsetYellow();
        }
        public class YOffsetRed implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double yOffset = rPipeline.getYOffset();

                if (yOffset == 100000) {
                    return false;
                }
                if (Math.abs(yOffset) < 10) {
                    tickerY++;
                    if (tickerY >= checker) {
                        return false;
                    }
                    return true;
                }
                if (yOffset > 10) {
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, power),
                            0
                    ));

                    drive.updatePoseEstimate();
                    return true;
                }
                if (yOffset < -10) {
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, -power),
                            0
                    ));

                    drive.updatePoseEstimate();
                    return true;
                }

                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0),
                        0
                ));
                drive.updatePoseEstimate();

                tickerY = 0;

                return false;
            }
        }
        public Action YOffsetRed() {
            return new YOffsetRed();
        }

        public class YOffsetBlue implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                double yOffset = bPipeline.getYOffset();

                if (yOffset == 100000) {
                    return false;
                }
                if (Math.abs(yOffset) < 10) {
                    tickerY++;
                    if (tickerY >= checker) {
                        return false;
                    }
                    return true;
                }
                if (yOffset > 10) {
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, power),
                            0
                    ));

                    drive.updatePoseEstimate();
                    return true;
                }
                if (yOffset < -10) {
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, -power),
                            0
                    ));

                    drive.updatePoseEstimate();
                    return true;
                }

                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0),
                        0
                ));
                drive.updatePoseEstimate();

                tickerY = 0;

                return false;
            }
        }
        public Action YOffsetBlue() {
            return new YOffsetBlue();
        }



    }


    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();


    }

}
