package org.firstinspires.ftc.teamcode.ANewEngland.Camera.OldNonFunctional;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ANewEngland.Camera.CameraProcessingFunctions;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
@Disabled
@TeleOp(group = "Camera")
public class SampleMask extends LinearOpMode {
    //Todo:
    //  All sources used:
    //      SkystoneDeterminationExample / StoneOrientationExample
    //      Artemis video / opencvArtemis / https://www.youtube.com/watch?v=0_w7UTN9LnE
    //      FtcDashboard_Camera / ColorMasking
    //      https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
    //      random google searches :)
    //      hard press opencv stuff
    //      random person on reddit / omg thank u sm / https://www.reddit.com/r/FTC/comments/1fdz49d/comment/lmpfoln/
    //Todo: look at SampleOrientation
    //
    OpenCvCamera webcam;
    RedObjectPipeline rPipeline;

    static FtcDashboard dashboard;
    static Handler backgroundHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        rPipeline = new RedObjectPipeline();
        webcam.setPipeline(rPipeline);


        // Todo:
        //  this stuff is all for sending the ftcdashboard stuff to a different thread
        // i don't know why we need this and other teams don't, i looked at exmaple code and they didn't need this
        // and i feel like ethan and daniel would mention if they needed this too
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        HandlerThread handlerThread = new HandlerThread("FrameStreamThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());


        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        //Todo: see SkystoneDeterminationExample thats their comment

        webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);

                //idk choose one

            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
                telemetry.addData("Error",errorCode);
                //emo
            }
        });


        waitForStart();

        while(opModeIsActive()){



            telemetry.update();
            //let cpu rest or something
            sleep(100);
        }
    }
    public static class RedObjectPipeline extends OpenCvPipeline {
        private final CameraProcessingFunctions detector = new CameraProcessingFunctions();

        //other example code has volatile here
        //volatile seems to make remove errors?
        // https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for

        @Override
        public Mat processFrame(Mat input) {
            input = detector.redMask(input);


            //Todo: Bitmap conversion is like the same as in the og ftcdahsboard color mask
            //this is not our own and we should look into why
            Mat processedFrame = input.clone();
            // Convert to Bitmap and send to FTC Dashboard on a background thread
            final Bitmap bitmap = Bitmap.createBitmap(processedFrame.cols(), processedFrame.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(processedFrame, bitmap);

            backgroundHandler.post(() -> {
                if (dashboard != null) {
                    // Use the FTC Dashboard's image API (example)
                    dashboard.sendImage(bitmap);
                    bitmap.recycle(); // Avoid memory leaks
                }
            });
            //release for memory
            processedFrame.release();

            return input; // Return the drawings
        }


    }
}
