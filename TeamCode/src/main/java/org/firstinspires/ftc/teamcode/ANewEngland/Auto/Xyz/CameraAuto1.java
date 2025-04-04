package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Xyz;

import static org.firstinspires.ftc.teamcode.AStates.Auto.Bucket_AutoTest3.FOREVER;
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
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.BlueObjectPipeline;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.SampleDetectionFinal;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Config
@Autonomous (name = "CameraAuto1", group = "NEAuto")
@Disabled
public class

CameraAuto1 extends LinearOpMode {

    private OpenCvWebcam webcam;
    //private SampleDetectionFinal.RedObjectPipeline redObjectPipeline;
    private BlueObjectPipeline blueObjectPipeline;
    //private SampleSplitPlusColor.YellowObjectPipeline yellowObjectPipeline;

    public static int num = 20;
    public static double increment = 0.0005;
    public static double power = 0.3;

    public class HSlides {

        Calvin calvin;
        double yOffset;


        public HSlides(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class HSlidesMovement implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                yOffset = blueObjectPipeline.getYOffset();
                telemetryPacket.put("offset",yOffset);
                double currentPos = calvin.hSlidesLeft.getPosition();
                if (blueObjectPipeline.getIsFound()) {
                    if (yOffset > num && currentPos > hSlidesOutside && currentPos < hSlidesInside) {
                        calvin.hSlidesLeft.setPosition(currentPos + increment);
                        calvin.hSlidesRight.setPosition(currentPos + increment);
                        return true;
                    } else if (yOffset < -num && currentPos > hSlidesOutside && currentPos < hSlidesInside) {
                        calvin.hSlidesLeft.setPosition(currentPos - increment);
                        calvin.hSlidesRight.setPosition(currentPos - increment);
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;

            }
        }

        public Action hSlidesMovement() {
            return new HSlidesMovement();
        }
    }

    public class IntakeWrist {

        Calvin calvin;
        double angle;

        public IntakeWrist(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class IntakeWristMovement implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                angle = blueObjectPipeline.getDetectedAngle();
                double currentAngle = calvin.intakeWrist.getPosition();
                if (blueObjectPipeline.getIsFound()) {
                    calvin.intakeWrist.setPosition(angle/180);
                    return false;
                } else {
                    return false;
                }
            }

        }

        public Action intakeWristMovement() {
            return new IntakeWristMovement();
        }

    }


    public class XOffsetMovement {

        Calvin calvin;
        double xOffset;
        PinpointDrive drive;

        public XOffsetMovement(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
            drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));

        }

        public class XOffsetMove implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                xOffset = blueObjectPipeline.getXOffset();
                if (blueObjectPipeline.getIsFound()) {
                    if (xOffset > num) {
                        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -power), 0));
                        return true;
                    } else if (xOffset < -num) {
                        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, power), 0));
                        return true;
                    } else {
                        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                        return false;
                    }
                }
                return false;

            }
        }

        public Action xOffsetMove() {
            return new XOffsetMove();
        }


    }


    @Override
    public void runOpMode() throws InterruptedException {


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "Webcam 1"),
                cameraMonitorViewId
        );
        //redObjectPipeline = new SampleDetectionFinal.RedObjectPipeline();
        blueObjectPipeline = new BlueObjectPipeline(webcam);
        //yellowObjectPipeline = new SampleDetectionFinal.YellowObjectPipeline();

        webcam.setPipeline(blueObjectPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error:", errorCode);
            }
        });

        Calvin calvin = new Calvin(hardwareMap);
        HSlides hSlides = new HSlides(hardwareMap);
        IntakeWrist intakeWrist = new IntakeWrist(hardwareMap);
        XOffsetMovement xOffsetMovement = new XOffsetMovement(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Camera Streaming...");
            telemetry.addData("angle", blueObjectPipeline.getDetectedAngle());
            telemetry.addData("xOffset", blueObjectPipeline.getXOffset());
            telemetry.addData("yOffset", blueObjectPipeline.getYOffset());
            telemetry.addData("isFound", blueObjectPipeline.getIsFound());
            telemetry.addData("is Split", blueObjectPipeline.getSplitQuestion());
            telemetry.addData("average color", blueObjectPipeline.getRgb());
            telemetry.update();
            FtcDashboard.getInstance().startCameraStream(webcam,10);

            Actions.runBlocking(
                    new ParallelAction(
                            hSlides.hSlidesMovement(),
                            xOffsetMovement.xOffsetMove()
                            //intakeWrist.intakeWristMovement(),
                            //new SleepAction(FOREVER)
                    )

            );

        }

        webcam.stopStreaming();

    }
}
