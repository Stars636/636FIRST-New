/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.ANewEngland.Camera.OldNonFunctional;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.CameraProcessingFunctions;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Config
@TeleOp
public class SampleDetectionUltimate extends LinearOpMode
{
    OpenCvWebcam webcam;
    RedObjectPipeline rPipeline;
    YellowObjectPipeline yPipeline;
    BlueObjectPipeline bPipeline;

    public static int pipeline = 0;
    @Override
    public void runOpMode()
    {
        /*
         * Instantiate an OpenCvCamera object for the camera we'll be using.
         * In this sample, we're using a webcam. Note that you will need to
         * make sure you have added the webcam to your configuration file and
         * adjusted the name here to match what you named it in said config file.
         *
         * We pass it the view that we wish to use for camera monitor (on
         * the RC phone). If no camera monitor is desired, use the alternate
         * single-parameter constructor instead (commented out below)
         */
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
        webcam.setPipeline(rPipeline);

        /*
         * Open the connection to the camera device. New in v1.4.0 is the ability
         * to open the camera asynchronously, and this is now the recommended way
         * to do it. The benefits of opening async include faster init time, and
         * better behavior when pressing stop during init (i.e. less of a chance
         * of tripping the stuck watchdog)
         *
         * If you really want to open synchronously, the old method is still available.
         */
        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
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

        telemetry.addLine("Waiting for start");
        telemetry.update();

        /*
         * Wait for the user to press start on the Driver Station
         */
        waitForStart();

        while (opModeIsActive())
        {
            /*
             * Send some stats to the telemetry
             */
            double angle = 0;
            double xOffset = 0;
            double yOffset = 0;
            double area = 0;
            boolean found = false;
            boolean split = false;
            Scalar color = new Scalar(0,0,0);


            telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
            if (pipeline == 0){
                webcam.setPipeline(rPipeline);
                angle =  rPipeline.getDetectedAngle();
                xOffset = rPipeline.getXOffset();
                yOffset = rPipeline.getYOffset();
                area = rPipeline.getArea();
                found = rPipeline.getIsFound();
                split = rPipeline.getSplitQuestion();
                color = rPipeline.getRgb();
            } else if (pipeline == 1) {
                webcam.setPipeline(yPipeline);
                angle =  yPipeline.getDetectedAngle();
                xOffset = yPipeline.getXOffset();
                yOffset = yPipeline.getYOffset();
                area = yPipeline.getArea();
                found = yPipeline.getIsFound();
                split = yPipeline.getSplitQuestion();
                color = yPipeline.getRgb();
            } else if (pipeline == 2) {
                webcam.setPipeline(bPipeline);
                angle =  bPipeline.getDetectedAngle();
                xOffset = bPipeline.getXOffset();
                yOffset = bPipeline.getYOffset();
                area = bPipeline.getArea();
                found = bPipeline.getIsFound();
                split = bPipeline.getSplitQuestion();
                color = bPipeline.getRgb();
            }



            //FtcDashboard.getInstance().sendImage(rPipeline.getOutput());
            telemetry.addData("angle", angle);
            telemetry.addData("xOffset", xOffset);
            telemetry.addData("yOffset", yOffset);
            telemetry.addData("area", area);
            telemetry.addData("isFound", found);
            telemetry.addData("is Split", split);
            telemetry.addData("average color", color);
            telemetry.update();

            FtcDashboard.getInstance().startCameraStream(webcam,10);
            telemetry.update();

            /*
             * NOTE: stopping the stream from the camera early (before the end of the OpMode
             * when it will be automatically stopped for you) *IS* supported. The "if" statement
             * below will stop streaming from the camera when the "A" button on gamepad 1 is pressed.
             */


            /*
             * For the purposes of this sample, throttle ourselves to 10Hz loop to avoid burning
             * excess CPU cycles for no reason. (By default, telemetry is only sent to the DS at 4Hz
             * anyway). Of course in a real OpMode you will likely not want to do this.
             */
            sleep(100);
        }
    }

    /*
     * An example image processing pipeline to be run upon receipt of each frame from the camera.
     * Note that the processFrame() method is called serially from the frame worker thread -
     * that is, a new camera frame will not come in while you're still processing a previous one.
     * In other words, the processFrame() method will never be called multiple times simultaneously.
     *
     * However, the rendering of your processed image to the viewport is done in parallel to the
     * frame worker thread. That is, the amount of time it takes to render the image to the
     * viewport does NOT impact the amount of frames per second that your pipeline can process.
     *
     * IMPORTANT NOTE: this pipeline is NOT invoked on your OpMode thread. It is invoked on the
     * frame worker thread. This should not be a problem in the vast majority of cases. However,
     * if you're doing something weird where you do need it synchronized with your OpMode thread,
     * then you will need to account for that accordingly.
     */
    public static class RedObjectPipeline extends OpenCvPipeline
    {
        boolean viewportPaused;

        private final CameraProcessingFunctions detector = new CameraProcessingFunctions();
        private volatile double detectedAngle = 0; // Stores the detected angle
        private volatile double xOffset = 0;
        private volatile double yOffset = 0;
        private volatile double area = 0;
        private volatile boolean splitQuestion = false;
        private volatile Scalar rgb = new Scalar(0,0,0);
        private volatile double[] dataRed = new double[8];

        private volatile boolean isFoundQu = false;

        private final OpenCvWebcam webcam;

        public RedObjectPipeline(OpenCvWebcam webcam) {
            this.webcam = webcam;
        }




        //other example code has volatile here
        //volatile seems to make remove errors?
        // https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for

        @Override
        public Mat processFrame(Mat input) {
            dataRed = detector.splitRedSample(input);
            isFoundQu = detector.isFoundQ;
            xOffset = dataRed[0];
            yOffset = dataRed[1];
            area = dataRed[2];
            detectedAngle = dataRed[3];
            if (dataRed[4] == 0)  {
                splitQuestion = false;
            } else if (dataRed[4] == 1) {
                splitQuestion = true;
            }
            rgb = new Scalar(dataRed[5],dataRed[6], dataRed[7]);

            //Todo: Bitmap conversion is like the same as in the og ftcdahsboard color mask
            //this is not our own and we should look into why


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
        public boolean getSplitQuestion(){ return splitQuestion; }
        public Scalar getRgb(){ return rgb; }
        public boolean getIsFound(){ return isFoundQu; }

        @Override
        public void onViewportTapped()
        {

            viewportPaused = !viewportPaused;

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }

    public static class YellowObjectPipeline extends OpenCvPipeline

    {
        boolean viewportPaused;

        private final CameraProcessingFunctions detector = new CameraProcessingFunctions();
        private volatile double detectedAngle = 0; // Stores the detected angle
        private volatile double xOffset = 0;
        private volatile double yOffset = 0;
        private volatile double area = 0;
        private volatile boolean splitQuestion = false;
        private volatile Scalar rgb = new Scalar(0,0,0);
        private volatile double[] dataRed = new double[8];

        private volatile boolean isFoundQu = false;

        private final OpenCvWebcam webcam;

        public YellowObjectPipeline(OpenCvWebcam webcam) {
            this.webcam = webcam;
        }



        //other example code has volatile here
        //volatile seems to make remove errors?
        // https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for

        @Override
        public Mat processFrame(Mat input) {
            dataRed = detector.splitYellowSample(input);
            isFoundQu = detector.isFoundQ;
            xOffset = dataRed[0];
            yOffset = dataRed[1];
            area = dataRed[2];
            detectedAngle = dataRed[3];
            if (dataRed[4] == 0)  {
                splitQuestion = false;
            } else if (dataRed[4] == 1) {
                splitQuestion = true;
            }
            rgb = new Scalar(dataRed[5],dataRed[6], dataRed[7]);

            //Todo: Bitmap conversion is like the same as in the og ftcdahsboard color mask
            //this is not our own and we should look into why


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
        public boolean getSplitQuestion(){ return splitQuestion; }
        public Scalar getRgb(){ return rgb; }
        public boolean getIsFound(){ return isFoundQu; }

        @Override
        public void onViewportTapped()
        {

            viewportPaused = !viewportPaused;

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }

    public static class BlueObjectPipeline extends OpenCvPipeline
    {
        boolean viewportPaused;

        private final CameraProcessingFunctions detector = new CameraProcessingFunctions();
        private volatile double detectedAngle = 0; // Stores the detected angle
        private volatile double xOffset = 0;
        private volatile double yOffset = 0;
        private volatile double area = 0;
        private volatile boolean splitQuestion = false;
        private volatile Scalar rgb = new Scalar(0,0,0);
        private volatile double[] dataRed = new double[8];

        private volatile boolean isFoundQu = false;

        private final OpenCvWebcam webcam;

        public BlueObjectPipeline(OpenCvWebcam webcam) {
            this.webcam = webcam;
        }



        //other example code has volatile here
        //volatile seems to make remove errors?
        // https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for

        @Override
        public Mat processFrame(Mat input) {
            dataRed = detector.splitBlueSample(input);
            isFoundQu = detector.isFoundQ;
            xOffset = dataRed[0];
            yOffset = dataRed[1];
            area = dataRed[2];
            detectedAngle = dataRed[3];
            if (dataRed[4] == 0)  {
                splitQuestion = false;
            } else if (dataRed[4] == 1) {
                splitQuestion = true;
            }
            rgb = new Scalar(dataRed[5],dataRed[6], dataRed[7]);

            //Todo: Bitmap conversion is like the same as in the og ftcdahsboard color mask
            //this is not our own and we should look into why


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
        public boolean getSplitQuestion(){ return splitQuestion; }
        public Scalar getRgb(){ return rgb; }
        public boolean getIsFound(){ return isFoundQu; }

        @Override
        public void onViewportTapped()
        {

            viewportPaused = !viewportPaused;

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }

}