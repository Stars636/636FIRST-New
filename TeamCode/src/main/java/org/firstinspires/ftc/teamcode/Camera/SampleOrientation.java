package org.firstinspires.ftc.teamcode.Camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class SampleOrientation {

    //Todo:
    //  All sources used:
    //      SkystoneDeterminationExample / StoneOrientationExample
    //      Artemis video / opencvArtemis / https://www.youtube.com/watch?v=0_w7UTN9LnE
    //      FtcDashboard_Camera / ColorMasking
    //      https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
    //      random google searches :)
    //      hard press opencv stuff
    //      random guy on reddit / https://www.reddit.com/r/FTC/comments/1fdz49d/comment/lmpfoln/
    //      BasicCameraOpMode
    //Todo: look at TeleOpSampleDetection
    //

    public static Scalar LOW_RED_RANGE_LOW = new Scalar(0, 100, 100);
    public static Scalar LOW_RED_RANGE_HIGH = new Scalar(10, 255, 255);
    public static Scalar HIGH_RED_RANGE_LOW = new Scalar(160, 100, 100);
    public static Scalar HIGH_RED_RANGE_HIGH = new Scalar(180, 255, 255);

    public static Scalar lowerYellow = new Scalar(20, 100, 100);
    public static Scalar upperYellow = new Scalar(30, 255, 255);

    //apparently red is special in need two ranges

    public static Scalar lowerBlue = new Scalar(100, 100, 100);
    public static Scalar upperBlue = new Scalar(140, 255, 255);

    //TODO: once red is working, we can make the yellow and blue versions


    public static double highRedWeight = 1.0;
    public static double lowRedWeight = 1.0;

    public static double notFound = Double.NaN;
    //basically i don't want the function to crash if an object isn't found
    //so rn we will check for this number

//int frameWidth = input.width();
//        int frameHeight = input.height();
//
//        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
    public static double smallestContour = 1;
    //basically the nested for loops are o(n^2)
    //i.e. take more time to calculate
    // so when we look for the largest contour we can save time by getting rid for the smallest ones with
    //an if statement its be good
    //idk what it should be though
    public static double largestContour = 2E09;
    //basically if two reds are touching itll be deeply confused i think
    //so we can filter it out of the for loop along with smallestContour
    //but
    //we'll need to see :)


    public double estimateRedSampleOrientation(Mat input) {

        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //helpful person detected
        //suggests filtering noise before changed to hsv
        Imgproc.medianBlur(input,input,3);



        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);
        //Translation to HSV. Light work no reaction
        //Todo: look into adaptive threshold to help

        Mat lowRedRange = new Mat();
        Mat highRedRange = new Mat();


        // Red has two ranges in HSV (lower and upper) for some reason ¯\_(ツ)_/¯ look
        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //Source is in python but nicely translatable
        Core.inRange(hsv, LOW_RED_RANGE_LOW, LOW_RED_RANGE_HIGH, lowRedRange); // Lower red range
        Core.inRange(hsv, HIGH_RED_RANGE_LOW, HIGH_RED_RANGE_HIGH, highRedRange); // Upper red range

        //Combine them :)
        Mat redMask = new Mat();
        Core.addWeighted(lowRedRange, lowRedWeight, highRedRange, highRedWeight, 0.0, redMask);
        //hard press addWeighted to look at it but its hard to know why it works other then it combines them and
        //you can choose how much each mask is weighted
        //like maybe if the highredrange works better you can weigh the other one less

        Imgproc.GaussianBlur(redMask, redMask, new Size(5, 5), 0);
        //gaussian blur reduces error with false positives according to some internet guy

        //Mat edges = new Mat();
        //Imgproc.Canny(redMask, edges, 50, 150);
        //hard press and look at canny
        //basically it finds edges in grayscale images
        //since we made a mask we don't need this though

        List<MatOfPoint> contours = new ArrayList<>();
        //creates the list of all contours found

        Mat hierarchy = new Mat();
        //i couldn't really tell you what this does, it's optional in findContours

        Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double angle = 0.0;

        // Camera center
        int frameWidth = input.width();
        int frameHeight = input.height();
        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
        double minDistance = 1000; //when we find a new contour center distance, mindistance will be changed to
        //the center distance until we find the closest
        RotatedRect closestRect = null; //null until we find our rect
        // largest double is 1.7976931348623157E308
        // pretty big number LOL

        for (MatOfPoint contour : contours) {
            //you get this
            if (Imgproc.contourArea(contour) > smallestContour) {

                RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                Point objectCenter = rect.center;

                //the points/corners of the rotatedrect
                //the 4 is saying the size

                double distance = Math.sqrt(Math.pow(objectCenter.x - cameraCenter.x, 2) +
                        Math.pow(objectCenter.y - cameraCenter.y, 2));
                //pythagorean theorem detected

                if (distance < minDistance) { // Keep track of the closest object
                    minDistance = distance;
                    closestRect = rect; //

                }


            }

        }
        if (closestRect == null) {
            return notFound; // No object was found
            //the notFound solulu might be trash ngl
            //if code crashes delete this first
        }


        angle = closestRect.angle;
        if (closestRect.size.width < closestRect.size.height) {
            angle += 90;
        }

        Point[] boxPoints = new Point[4];
        //the points/corners of the rotatedrect

        closestRect.points(boxPoints);

        for (int i = 0; i < 4; i++) {
            Imgproc.line(input, boxPoints[i], boxPoints[(i + 1) % 4], new Scalar(0, 255, 0), 2);
            //draws the rectangle from the points made by the rotated rect
        }


        return angle;

    }

    //Todo: maybe there's a simpler way, but for now im making two basically identical
    // functions to return the distance for future use
    // THIS IS IN PIXELS
    // in the case camera is always the same distance up,
    // let's make a function together that returns a physical distance
    // the robot should extend/move to
    // which will be MUCH better for road runner
    // and maybe a double check function too :)
    public double estimateRedXDistance(Mat input) {

        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //helpful person detected
        //suggests filtering noise before changed to hsv
        Imgproc.medianBlur(input,input,3);



        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);
        //Translation to HSV. Light work no reaction

        Mat lowRedRange = new Mat();
        Mat highRedRange = new Mat();


        // Red has two ranges in HSV (lower and upper) for some reason ¯\_(ツ)_/¯ look
        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //Source is in python but nicely translatable
        Core.inRange(hsv, LOW_RED_RANGE_LOW, LOW_RED_RANGE_HIGH, lowRedRange); // Lower red range
        Core.inRange(hsv, HIGH_RED_RANGE_LOW, HIGH_RED_RANGE_HIGH, highRedRange); // Upper red range

        //Combine them :)
        Mat redMask = new Mat();
        Core.addWeighted(lowRedRange, lowRedWeight, highRedRange, highRedWeight, 0.0, redMask);
        //hard press addWeighted to look at it but its hard to know why it works other then it combines them and
        //you can choose how much each mask is weighted
        //like maybe if the highredrange works better you can weigh the other one less

        Imgproc.GaussianBlur(redMask, redMask, new Size(5, 5), 0);
        //gaussian blur reduces error with false positives according to some internet guy

        //Mat edges = new Mat();

        //Imgproc.Canny(redMask, edges, 50, 150);
        //hard press and look at canny
        //basically it finds edges in grayscale images
        //since we made a mask we don't need this though

        List<MatOfPoint> contours = new ArrayList<>();
        //creates the list of all contours found

        Mat hierarchy = new Mat();

        Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double xDistance = 0.0;

        // Camera center
        int frameWidth = input.width();
        int frameHeight = input.height();
        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
        double minDistance = 1000; //when we find a new contour center distance, mindistance will be changed to
        //the center distance until we find the closest
        RotatedRect closestRect = null; //null until we find our rect
        // largest double is 1.7976931348623157E308
        // pretty big number LOL

        for (MatOfPoint contour : contours) {
            //you get this
            if (Imgproc.contourArea(contour) > smallestContour) {

                RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                Point objectCenter = rect.center;

                //the points/corners of the rotatedrect
                //the 4 is saying the size

                double distance = Math.sqrt(Math.pow(objectCenter.x - cameraCenter.x, 2) +
                        Math.pow(objectCenter.y - cameraCenter.y, 2));
                //pythagorean theorem detected

                if (distance < minDistance) { // Keep track of the closest object
                    minDistance = distance;
                    closestRect = rect; //

                }


            }


        }
        if (closestRect == null) {
            return notFound; // No object was found
            //the notFound solulu might be trash ngl
            //if code crashes delete this first
        }


        xDistance = (closestRect.center.x - cameraCenter.x);
        //TODO: This is in pixels.


        return xDistance;

    }

    public double estimateRedYDistance(Mat input) {

        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //helpful person detected
        //suggests filtering noise before changed to hsv
        Imgproc.medianBlur(input,input,3);


        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);
        //Translation to HSV. Light work no reaction

        Mat lowRedRange = new Mat();
        Mat highRedRange = new Mat();


        // Red has two ranges in HSV (lower and upper) for some reason ¯\_(ツ)_/¯ look
        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //Source is in python but nicely translatable
        Core.inRange(hsv, LOW_RED_RANGE_LOW, LOW_RED_RANGE_HIGH, lowRedRange); // Lower red range
        Core.inRange(hsv, HIGH_RED_RANGE_LOW, HIGH_RED_RANGE_HIGH, highRedRange); // Upper red range

        //Combine them :)
        Mat redMask = new Mat();
        Core.addWeighted(lowRedRange, lowRedWeight, highRedRange, highRedWeight, 0.0, redMask);
        //hard press addWeighted to look at it but its hard to know why it works other then it combines them and
        //you can choose how much each mask is weighted
        //like maybe if the highredrange works better you can weigh the other one less

        Imgproc.GaussianBlur(redMask, redMask, new Size(5, 5), 0);
        //gaussian blur reduces error with false positives according to some internet guy

        //Mat edges = new Mat();

        //Imgproc.Canny(redMask, edges, 50, 150);
        //hard press and look at canny
        //basically it finds edges in grayscale images
        //since we made a mask we don't need this though

        List<MatOfPoint> contours = new ArrayList<>();
        //creates the list of all contours found

        Mat hierarchy = new Mat();

        Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double yDistance = 0.0;

        // Camera center
        int frameWidth = input.width();
        int frameHeight = input.height();
        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
        double minDistance = 1000; //when we find a new contour center distance, mindistance will be changed to
        //the center distance until we find the closest
        RotatedRect closestRect = null; //null until we find our rect
        // largest double is 1.7976931348623157E308
        // pretty big number LOL

        for (MatOfPoint contour : contours) {
            //you get this
            if (Imgproc.contourArea(contour) > smallestContour) {

                RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                Point objectCenter = rect.center;

                //the points/corners of the rotatedrect
                //the 4 is saying the size

                double distance = Math.sqrt(Math.pow(objectCenter.x - cameraCenter.x, 2) +
                        Math.pow(objectCenter.y - cameraCenter.y, 2));
                //pythagorean theorem detected

                if (distance < minDistance) { // Keep track of the closest object
                    minDistance = distance;
                    closestRect = rect; //

                }


            }

        }
        if (closestRect == null) {
            return notFound; // No object was found
            //the notFound solulu might be trash ngl
            //if code crashes delete this first
        }


        yDistance = (closestRect.center.x - cameraCenter.x);
        //TODO: This is in pixels.


        return yDistance;

    }

    public double estimateRedArea(Mat input) {

        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //helpful person detected
        //suggests filtering noise before changed to hsv
        Imgproc.medianBlur(input,input,3);



        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);
        //Translation to HSV. Light work no reaction

        Mat lowRedRange = new Mat();
        Mat highRedRange = new Mat();


        // Red has two ranges in HSV (lower and upper) for some reason ¯\_(ツ)_/¯ look
        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //Source is in python but nicely translatable
        Core.inRange(hsv, LOW_RED_RANGE_LOW, LOW_RED_RANGE_HIGH, lowRedRange); // Lower red range
        Core.inRange(hsv, HIGH_RED_RANGE_LOW, HIGH_RED_RANGE_HIGH, highRedRange); // Upper red range

        //Combine them :)
        Mat redMask = new Mat();
        Core.addWeighted(lowRedRange, lowRedWeight, highRedRange, highRedWeight, 0.0, redMask);
        //hard press addWeighted to look at it but its hard to know why it works other then it combines them and
        //you can choose how much each mask is weighted
        //like maybe if the highredrange works better you can weigh the other one less

        Imgproc.GaussianBlur(redMask, redMask, new Size(5, 5), 0);
        //gaussian blur reduces error with false positives according to some internet guy

        //Mat edges = new Mat();

        //Imgproc.Canny(redMask, edges, 50, 150);
        //hard press and look at canny
        //basically it finds edges in grayscale images
        //since we made a mask we don't need this though

        List<MatOfPoint> contours = new ArrayList<>();
        //creates the list of all contours found

        Mat hierarchy = new Mat();

        Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double area = 0.0;

        // Camera center
        int frameWidth = input.width();
        int frameHeight = input.height();
        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
        double minDistance = 1000; //when we find a new contour center distance, mindistance will be changed to
        //the center distance until we find the closest
        RotatedRect closestRect = null; //null until we find our rect
        // largest double is 1.7976931348623157E308
        // pretty big number LOL

        for (MatOfPoint contour : contours) {
            //you get this
            if (Imgproc.contourArea(contour) > smallestContour) {

                RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                Point objectCenter = rect.center;

                //the points/corners of the rotatedrect
                //the 4 is saying the size

                double distance = Math.sqrt(Math.pow(objectCenter.x - cameraCenter.x, 2) +
                        Math.pow(objectCenter.y - cameraCenter.y, 2));
                //pythagorean theorem detected

                if (distance < minDistance) { // Keep track of the closest object
                    minDistance = distance;
                    closestRect = rect; //
                    area = Imgproc.contourArea(contour);

                }


            }


        }
        if (closestRect == null) {
            return notFound; // No object was found
            //the notFound solulu might be trash ngl
            //if code crashes delete this first
        }

        return area;


    }


} 