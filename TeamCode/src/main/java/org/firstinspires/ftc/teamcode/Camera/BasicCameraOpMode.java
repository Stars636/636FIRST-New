package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name="Basic Camera OpMode", group="Linear Opmode")
public class BasicCameraOpMode extends LinearOpMode {
    private OpenCvWebcam webcam;

    @Override
    public void runOpMode() {
        // Get camera ID from hardware map
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );

        // Initialize webcam
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "Webcam 1"),
                cameraMonitorViewId
        );

        // Attach an OpenCV pipeline (Custom pipeline will process frames)
        webcam.setPipeline(new SamplePipeline());

        // Open camera asynchronously
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

        telemetry.addLine("Camera Initialized. Press Play!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Camera Streaming...");
            telemetry.update();
            sleep(100);
        }

        // Stop camera streaming when OpMode ends
        webcam.stopStreaming();
    }

    // Sample OpenCV pipeline for basic image processing
    static class SamplePipeline extends org.openftc.easyopencv.OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            // Convert the image to grayscale (example processing step)
            Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2GRAY);
            return input; // Return processed frame
        }
    }
}
