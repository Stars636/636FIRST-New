package org.firstinspires.ftc.teamcode.Camera.Pipelines;

import org.firstinspires.ftc.teamcode.Camera.CameraProcessingFunctions;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class YellowObjectPipeline extends OpenCvPipeline
    {
        boolean viewportPaused;

        private final CameraProcessingFunctions detector = new CameraProcessingFunctions();
        private volatile double detectedAngle = 0;
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


            return input;
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