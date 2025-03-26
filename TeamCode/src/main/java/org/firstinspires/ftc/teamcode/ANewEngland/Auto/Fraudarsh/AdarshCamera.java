package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Fraudarsh;
import static org.firstinspires.ftc.teamcode.AStates.Auto.Bucket_AutoTest3.FOREVER;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.config.Config;


import org.firstinspires.ftc.teamcode.ANewEngland.Camera.Pipelines.BlueObjectPipeline;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Config
@Autonomous (name = "TheBestAuto", group = "NEAuto")
public class AdarshCamera extends LinearOpMode {
    private OpenCvWebcam detectionApparatus;
    private BlueObjectPipeline blueDetect;
    public static double extendoIncrement = 0.0005;
    public static double drivePower = 0.5;
    public static int margin = 20;

    public class extendo {
        Calvin calvin;
        public double yCoord;
        public extendo(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }
        public class extendoExtender implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                yCoord = blueDetect.getYOffset();
                double currentPosition = calvin.hSlidesRight.getPosition();
                if (blueDetect.getIsFound()) {
                    if (yCoord>margin && hSlidesOutside<currentPosition&&hSlidesInside>currentPosition) {
                        calvin.hSlidesRight.setPosition(currentPosition - extendoIncrement);
                        calvin.hSlidesLeft.setPosition(currentPosition - extendoIncrement);
                        return true;
                    } else if (yCoord<margin && hSlidesOutside<currentPosition&&hSlidesInside>currentPosition) {
                        calvin.hSlidesRight.setPosition(currentPosition + extendoIncrement);
                        calvin.hSlidesLeft.setPosition(currentPosition + extendoIncrement);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        public Action extendoExtender2() {
            return new extendoExtender();
        }
    }
    public class wrist {
        Calvin calvin;
        public double orientAngle;
        public wrist(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }
        public class TurningOfTheWrist implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                orientAngle = blueDetect.getDetectedAngle();
                double currentAngle = calvin.intakeWrist.getPosition();
                if (blueDetect.getIsFound()) {
                    calvin.intakeWrist.setPosition(orientAngle/180);
                    return false;
                } else {
                    return false;
                }
            }
        }
        public Action TurningOfTheWrist2() {
            return new TurningOfTheWrist();
        }
    }
    public class strafer {
        Calvin calvin;
        double xCoord;
        PinpointDrive drive;
        public strafer(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
            drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));
        }
        public class strafering implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                xCoord = blueDetect.getXOffset();
                if (blueDetect.getIsFound()) {
                    if (xCoord > margin) {
                        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, drivePower), 0));
                        return true;
                    }
                    else if (xCoord < -margin) {
                        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, -drivePower), 0));
                    }
                    else{
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        public Action strafering() {
            return new strafering2();
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        int camStream = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        detectionApparatus = OpenCvCameraFactory.getInstance().createWebcam(
         hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "Webcam 1"),
         camStream
        );
        blueDetect = new BlueObjectPipeline(detectionApparatus);
        detectionApparatus.setPipeline(blueDetect);
        detectionApparatus.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                detectionApparatus.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {
                telemetry.addData("Webcam Fraudulence:", errorCode);
            }
        });
        extendo Extendo = new extendo(hardwareMap);
        wrist Wrist = new wrist(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addLine("Camera On");
            telemetry.addData("angle", blueDetect.getDetectedAngle());
            telemetry.addData("xOffset", blueDetect.getXOffset());
            telemetry.addData("yOffset", blueDetect.getYOffset());
            telemetry.addData("isFound", blueDetect.getIsFound());
            telemetry.addData("is Split", blueDetect.getSplitQuestion());
            telemetry.addData("average color", blueDetect.getRgb());
            telemetry.update();
            FtcDashboard.getInstance().startCameraStream(detectionApparatus, 10);
            Actions.runBlocking(
                    new SequentialAction(
                            Extendo.extendoExtender2(),
                            Wrist.TurningOfTheWrist2(),
                            new SleepAction(FOREVER)
                    )
            );
        }
    }
}