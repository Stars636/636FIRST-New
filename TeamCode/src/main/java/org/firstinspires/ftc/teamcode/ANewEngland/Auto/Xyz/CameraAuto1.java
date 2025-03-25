package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Xyz;

import static org.firstinspires.ftc.teamcode.AStates.Auto.Bucket_AutoTest3.FOREVER;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ANewEngland.Camera.SampleDetectionFinal;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous (name = "CameraAuto1", group = "NEAuto")
public class CameraAuto1 extends LinearOpMode {

    private OpenCvWebcam webcam;
    private SampleDetectionFinal.RedObjectPipeline redObjectPipeline;
    //private SampleDetectionFinal.BlueObjectPipeline blueObjectPipeline;
    //private SampleSplitPlusColor.YellowObjectPipeline yellowObjectPipeline;

    public class HSlides {

        Calvin calvin;
        double yOffset;


        public HSlides(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }

        public class HSlidesMovement implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                yOffset = redObjectPipeline.getYOffset();
                double currentPos = calvin.hSlidesLeft.getPosition();
                if (redObjectPipeline.getIsFound()) {
                    if (yOffset > 15 && currentPos > hSlidesOutside && currentPos < hSlidesInside) {
                        calvin.hSlidesLeft.setPosition(currentPos - 0.003);
                        calvin.hSlidesRight.setPosition(currentPos - 0.003);
                        return true;
                    } else if (yOffset < -15 && currentPos > hSlidesOutside && currentPos < hSlidesInside) {
                        calvin.hSlidesLeft.setPosition(currentPos + 0.003);
                        calvin.hSlidesRight.setPosition(currentPos + 0.003);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
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
                angle = redObjectPipeline.getDetectedAngle();
                double currentAngle = calvin.intakeWrist.getPosition();
                if (redObjectPipeline.getIsFound()) {
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


    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "Webcam 1"),
                cameraMonitorViewId
        );
        redObjectPipeline = new SampleDetectionFinal.RedObjectPipeline();
        //blueObjectPipeline = new SampleDetectionFinal.BlueObjectPipeline();
        //yellowObjectPipeline = new SampleDetectionFinal.YellowObjectPipeline();

        webcam.setPipeline(redObjectPipeline);

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

        Calvin calvin = new Calvin(hardwareMap);
        HSlides hSlides = new HSlides(hardwareMap);
        IntakeWrist intakeWrist = new IntakeWrist(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Camera Streaming...");
            telemetry.addData("angle", redObjectPipeline.getDetectedAngle());
            telemetry.addData("xOffset", redObjectPipeline.getXOffset());
            telemetry.addData("yOffset", redObjectPipeline.getYOffset());
            telemetry.addData("isFound", redObjectPipeline.getIsFound());
            telemetry.addData("is Split", redObjectPipeline.getSplitQuestion());
            telemetry.addData("average color", redObjectPipeline.getRgb());
            telemetry.update();
            FtcDashboard.getInstance().startCameraStream(webcam,10);

            Actions.runBlocking(
                    new SequentialAction(
                            hSlides.hSlidesMovement(),
                            //intakeWrist.intakeWristMovement(),
                            new SleepAction(FOREVER)
                    )

            );

        }

        webcam.stopStreaming();

    }
}
