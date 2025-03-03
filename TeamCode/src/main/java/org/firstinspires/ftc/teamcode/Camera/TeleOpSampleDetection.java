package org.firstinspires.ftc.teamcode.Camera;


import static org.firstinspires.ftc.teamcode.Camera.SampleOrientation.notFound;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Camera.BorrowedSampleCode.SkystoneDeterminationExample;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name="FtcDashboard Camera", group="Linear")
public class TeleOpSampleDetection extends LinearOpMode {
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

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        rPipeline = new RedObjectPipeline();
        webcam.setPipeline(rPipeline);

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
                FtcDashboard.getInstance().startCameraStream(webcam, 30);
                //idk choose one

            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        waitForStart();

        while(opModeIsActive()){

            //FtcDashboard.getInstance().startCameraStream(webcam, 30);
            telemetry.addData("angle", rPipeline.getDetectedAngle());
            telemetry.addData("xOffset", rPipeline.getXOffset());
            telemetry.addData("yOffset", rPipeline.getYOffset());
            telemetry.addData("area", rPipeline.getArea());
            if (rPipeline.getDetectedAngle() == notFound) {
                telemetry.addData("Object Not Found",notFound);
                telemetry.update();
            } else {
                telemetry.addData("Object Found", "yay");
                telemetry.update();
            }

            telemetry.update(); 
            //let cpu rest or something
            sleep(100);
        }
    }
    public static class RedObjectPipeline extends OpenCvPipeline {
        private final SampleOrientation detector = new SampleOrientation();
        private volatile double detectedAngle = 0; // Stores the detected angle
        private volatile double xOffset = 0;
        private volatile double yOffset = 0;
        private volatile double area = 0;
        //other example code has volatile here
        //volatile seems to make remove errors?
        // https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for

        @Override
        public Mat processFrame(Mat input) {
            detectedAngle = detector.estimateRedSampleOrientation(input);
            xOffset = detector.estimateRedXDistance(input);
            yOffset = detector.estimateRedYDistance(input);
            area = detector.estimateRedArea(input);
            return input; // Return the drawings
        }

        public double getDetectedAngle() {
            return detectedAngle;
        }
        public double getXOffset() {
            return xOffset;
        }
        public double getYOffset() {
            return yOffset;
        }
        public double getArea(){
            return area;
        }
    }
}
