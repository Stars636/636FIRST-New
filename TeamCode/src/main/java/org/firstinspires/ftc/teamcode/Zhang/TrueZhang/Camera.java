
package org.firstinspires.ftc.teamcode.Zhang.TrueZhang;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.util.concurrent.atomic.AtomicReference;

@Autonomous(name="Camera", group="Linear")
public class Camera extends LinearOpMode {
    public static class CameraStreamProcessor implements VisionProcessor, CameraStreamSource {
        private final AtomicReference<Bitmap> lastFrame = new AtomicReference<>(Bitmap.createBitmap(1,1, Bitmap.Config.RGB_565));
        //private volatile Bitmap lastFrame =  Bitmap.createBitmap(1,1, Bitmap.Config.RGB_565);
        @Override
        public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
            continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
        }

        @Override
        public void init(int width, int height, CameraCalibration cameraCalibration) {
            lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        }

        @Override
        public Object processFrame(Mat frame, long captureTimeNanos) {
            Bitmap b = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.RGB_565);
            Utils.matToBitmap(frame, b);
            lastFrame.set(b);
            return null;
        }

        @Override
        public void onDrawFrame(Canvas canvas, int i, int i1, float v, float v1, Object o) {

        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagProcessor myAprilTagProcessor;
        //myAprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        myAprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        TfodProcessor myTfodProcessor;

        myTfodProcessor = new TfodProcessor.Builder()
                .setMaxNumRecognitions(10)
                .setUseObjectTracker(true)
                .setTrackerMaxOverlap((float) 0.2)
                .setTrackerMinSize(16)
                .build();

        //myVisionPortal.setProcessorEnabled(myAprilTagProcessor, true);

        VisionPortal myVisionPortal;

        myVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(myAprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                //.enableCameraMonitoring(true)
                //.enableLiveView(true)
                .setLiveViewContainerId(0)
                .setAutoStopLiveView(true)
                .build();

        //myVisionPortal.setProcessorEnabled(myAprilTagProcessor, true);
        final FtcDashboard_Camera.CameraStreamProcessor processor = new FtcDashboard_Camera.CameraStreamProcessor();

        new VisionPortal.Builder().
                addProcessor(processor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        waitForStart();

        FtcDashboard.getInstance().startCameraStream(processor, 0);

        while(opModeIsActive()){
            sleep(100);
        }

    }
}
