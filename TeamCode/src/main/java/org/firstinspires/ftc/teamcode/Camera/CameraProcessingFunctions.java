package org.firstinspires.ftc.teamcode.Camera;

import static org.opencv.imgproc.Imgproc.drawContours;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CameraProcessingFunctions {

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
    //Todo: convexity defects stuff
    //  https://docs.opencv.org/3.4/d5/d45/tutorial_py_contours_more_functions.html
    //  https://docs.opencv.org/4.x/d3/dc0/group__imgproc__shape.html#gada4437098113fd8683c932e0567f47ba
    // https://stackoverflow.com/questions/31354150/opencv-convexity-defects-drawing

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

    public static double[] notFound = {1000, 11, 11E2, 1158};
    public static double[] notFoundSplitVer = {1000, 11, 11E2, 1158, 1200, 12000, 1839, 1990};
    //basically i don't want the function to crash if an object isn't found
    //so rn we will check for this number

    //int frameWidth = input.width();
//        int frameHeight = input.height();
//
//        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
    public static double smallestContour = 1000;
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
    public static double largestPossibleArea = 10000; //from testing
    //if there are two together they will be more than this
    //i will only run the hull if the area is suspiciously large
    public static final double depthThreshold = 10.0; // for significant defects
    public static final int smallestValidContour = 4; // minimum number of points for a valid contour

    public double[] estimateRedSampleOrientation(Mat input) {

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
        double xDistance = 0.0;
        double yDistance = 0.0;
        double area = 0.0;

        // Camera center
        int frameWidth = input.width();
        int frameHeight = input.height();
        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
        double minDistance = 1000; //when we find a new contour center distance, min distance will be changed to
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
        xDistance = (closestRect.center.x - cameraCenter.x);
        yDistance = (closestRect.center.y - cameraCenter.y);
        //Todo: this is why memory was leaking; release mats when you're donw

        redMask.release();
        hierarchy.release();
        lowRedRange.release();
        highRedRange.release();
        hsv.release();



        return new double[]{xDistance, yDistance, area, angle};

    }

    //Todo: maybe there's a simpler way, but for now im making two basically identical
    // functions to return the distance for future use
    // THIS IS IN PIXELS
    // in the case camera is always the same distance up,
    // let's make a function together that returns a physical distance
    // the robot should extend/move to
    // which will be MUCH better for road runner
    // and maybe a double check function too :)

/*
    public double[] estimateBlueSampleOrientation(Mat input) {

        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //helpful person detected
        //suggests filtering noise before changed to hsv
        Imgproc.medianBlur(input,input,3);



        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);
        //Translation to HSV. Light work no reaction
        //Todo: look into adaptive threshold to help

        Mat blueRange = new Mat();


        // Red has two ranges in HSV (lower and upper) for some reason ¯\_(ツ)_/¯ look
        // https://solarianprogrammer.com/2015/05/08/detect-red-circles-image-using-opencv/
        //Source is in python but nicely translatable
        Core.inRange(hsv, lowerBlue, upperBlue, blueRange); // Lower red range


        Imgproc.GaussianBlur(lowerB, redMask, new Size(5, 5), 0);
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
        double xDistance = 0.0;
        double yDistance = 0.0;
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
        xDistance = (closestRect.center.x - cameraCenter.x);
        yDistance = (closestRect.center.y - cameraCenter.y);


        return new double[]{xDistance, yDistance, area, angle};

    }

 */
    //Todo: this is for returning the mask so we can see what it looks like
    public Mat redMask(Mat input) {

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


        return redMask;

    }

    //Todo: test program using convex hull defects to detect merged objects

    public static MatOfInt4 computeConvexityDefects(MatOfPoint contour) {
        MatOfInt hullIndices = new MatOfInt();
        Imgproc.convexHull(contour, hullIndices, false);
        //creates the hull that surrounds the full object; the shape is convex

        MatOfInt4 convexDefects = new MatOfInt4();
        Imgproc.convexityDefects(contour, hullIndices, convexDefects);
        //now its getting the defects,
        // i.e. the places where the contour significantly deviates from the hull
        //hard press convexitydefects -> matofint4 is (start_index, end_index, farthest_pt_index, fixpt_depth)

        return convexDefects;
    }

    //function for splitting or not
    public static boolean shouldSplit(MatOfPoint contour, MatOfInt4 convexDefects) {
        List<Integer> defectsList = convexDefects.toList();
        //matofint4 is a matrix kind thing of (start_index, end_index, farthest_pt_index, fixpt_depth)
        //so we are making it into a list so it can be iterated through
        int significantDefects = 0;

        for (int i = 0; i < defectsList.size(); i += 4) {
            //we know depth is at the 3,7,11,(counting starts at zero) position throughout the defectslist
            int depth = defectsList.get(i + 3);
            //hard press convexityDefects to get this formula, hard to explain
            double actualDepth = depth / 256.0;


            if (actualDepth > depthThreshold) {
                //is the depth is large enough, we count it as a defect
                significantDefects++;

            }
        }

        //is there are 2 or more, then return true
        //there are situations where there is one defect, but
        //
        return significantDefects >= 2;
    }

    //
    public static List<MatOfPoint> splitContour(MatOfPoint contour, MatOfInt4 convexDefects) {
        List<Point> contourPoints = contour.toList();
        List<Integer> defectsList = convexDefects.toList();


        int maxDepth1 = 0, maxDepth2 = 0;
        int farIdx1 = -1, farIdx2 = -1;
        //initializing

        for (int i = 0; i < defectsList.size(); i += 4) {
            int depth = defectsList.get(i + 3);
            //once again, the defects have four variables, so we are getting the fourth one
            if (depth > maxDepth1) {
                maxDepth2 = maxDepth1;
                farIdx2 = farIdx1;
                maxDepth1 = depth;
                farIdx1 = defectsList.get(i + 2);
                //we are iterating through to find the two deepest defects to split the larger image into
                //thankfully we are working with rectangles LOL
            } else if (depth > maxDepth2) {
                maxDepth2 = depth;
                farIdx2 = defectsList.get(i + 2);
            }
        }

        // Check if valid split points were found
        //if not return the original
        if (farIdx1 == -1 || farIdx2 == -1) {
            return Arrays.asList(contour);
        }

        // basically, the contours go in a circle so we have to handle the wraparound
        int splitStart = Math.min(farIdx1, farIdx2);
        int splitEnd = Math.max(farIdx1, farIdx2);
        //the two parts we split the contour into
        //i think we are hoping that eeven with the missing points, it will be close enough to draw a rectangle
        //however this code won't work well for parallel samples and not too well for perpendicular samples
        //but works for everything inbetween
        List<Point> part1 = new ArrayList<>(contourPoints.subList(splitStart, splitEnd));
        List<Point> part2 = new ArrayList<>(contourPoints.subList(splitEnd, contourPoints.size()));
        part2.addAll(contourPoints.subList(0, splitStart));

        // closes the wrap around
        part1.add(part1.get(0));
        part2.add(part2.get(0));

        // make sure the contours are proper
        // not sure why its five magic number detected
        if (part1.size() >= smallestValidContour && part2.size() >= smallestValidContour) {
            return Arrays.asList(
                    new MatOfPoint(part1.toArray(new Point[0])),
                    new MatOfPoint(part2.toArray(new Point[0]))
            );
        } else {
            return Arrays.asList(contour);
        }


    }

    public double[] splitRedSample(Mat input) {

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
        //like maybe if the high red range works better you can weigh the other one less

        Imgproc.GaussianBlur(redMask, redMask, new Size(5, 5), 0);
        //gaussian blur reduces error with false positives according to some internet guy

        //Mat edges = new Mat();
        //Imgproc.Canny(redMask, edges, 50, 150);
        //hard press and look at canny
        //basically it finds edges in grayscale images
        //since we made a mask we don't need this though

        List<MatOfPoint> contours = new ArrayList<>();
        List<MatOfPoint> hullList = new ArrayList<>();
        //creates the list of all contours and hulls found

        Mat hierarchy = new Mat();
        //i couldn't really tell you what this does, it's optional in findContours

        Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double angle = 0.0;
        double xDistance = 0.0;
        double yDistance = 0.0;
        double area = 0.0;

        // Camera center
        int frameWidth = input.width();
        int frameHeight = input.height();
        Point cameraCenter = new Point(frameWidth / 2.0, frameHeight / 2.0);
        double minDistance = 1000; //when we find a new contour center distance, min distance will be changed to
        //the center distance until we find the closest
        RotatedRect closestRect = null; //null until we find our rect
        // largest double is 1.7976931348623157E308
        // pretty big number LOL
        MatOfInt hull = new MatOfInt();
        //https://docs.opencv.org/4.x/d7/d1d/tutorial_hull.html
        int isSplit = 0;

        double[] averageRGB = new double[3];
        //for testing what color its seeing


        for (MatOfPoint contour : contours) {
            if (Imgproc.contourArea(contour) > smallestContour) {

                MatOfInt4 convexDefects = computeConvexityDefects(contour);


                if (Imgproc.contourArea(contour) > largestPossibleArea) {
                    if (shouldSplit(contour, convexDefects)) {

                        isSplit = 1;

                        //only make the hull if its worth splitting
                        List<MatOfPoint> splitContours = splitContour(contour, convexDefects);

                        //Todo: look here
                        // https://docs.opencv.org/4.x/d7/d1d/tutorial_hull.html
                        //Todo: this part is for drawing, so if it doesn't work it's fine
                        Imgproc.convexHull(contour, hull);
                        //creates the hull that surrounds the full object; the shape is convex

                        Point[] contourArray = contour.toArray();
                        Point[] hullPoints = new Point[hull.rows()];
                        List<Integer> hullContourIdxList = hull.toList();
                        for (int i = 0; i < hullContourIdxList.size(); i++) {
                            hullPoints[i] = contourArray[hullContourIdxList.get(i)];
                        }
                        hullList.add(new MatOfPoint(hullPoints));


                        for (MatOfPoint splitContour : splitContours) {
                            RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(splitContour.toArray()));
                            Point objectCenter = rect.center;


                            double distance = Math.sqrt(Math.pow(objectCenter.x - cameraCenter.x, 2) +
                                    Math.pow(objectCenter.y - cameraCenter.y, 2));
                            //pythagorean theorem detected


                            if (distance < minDistance) {
                                minDistance = distance;
                                closestRect = rect;
                                area = Imgproc.contourArea(splitContour);
                            }
                        }
                    } else {
                        isSplit = 0;
                        RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                        Point objectCenter = rect.center;


                        double distance = Math.sqrt(Math.pow(objectCenter.x - cameraCenter.x, 2) +
                                Math.pow(objectCenter.y - cameraCenter.y, 2));
                        //pythagorean theorem detected

                        // another mask for the object
                        Mat mask = Mat.zeros(input.size(), CvType.CV_8UC1);
                        Imgproc.drawContours(mask, Arrays.asList(contour), -1, new Scalar(255), -1);

                        // find the average color
                        Scalar meanColor = Core.mean(input, mask);

                        mask.release(); //release mask to save data
                        // convert the native bgr to rgb
                        averageRGB = new double[] {
                                meanColor.val[2], // red
                                meanColor.val[1], // green
                                meanColor.val[0]  // blue
                        };


                        if (distance < minDistance) {
                            minDistance = distance;
                            closestRect = rect;
                            area = Imgproc.contourArea(contour);
                            //making our variables match the ones of the closest contour
                        }
                    }
                }
            }
        }
        if (closestRect == null) {
            return notFoundSplitVer; // No object was found
            //the notFound solulu might be trash ngl
            //if code crashes delete this first
        }


        angle = closestRect.angle;
        if (closestRect.size.width < closestRect.size.height) {
            angle += 90;
        }
        //correcting for rotation
        if (angle > 178 || angle < 2) {
            //angle is never great than 180 or less than zero, and they are the same thing, so i'm flattening

            angle = 180;
        }

        Point[] boxPoints = new Point[4];
        //the points/corners of the rotatedrect

        closestRect.points(boxPoints);


        Scalar color = new Scalar(0, 255, 0);
        // the rectangle and hull are drawn in green
        for (int i = 0; i < 4; i++) {
            Imgproc.line(input, boxPoints[i], boxPoints[(i + 1) % 4], new Scalar(0, 255, 0), 2);
            //draws the rectangle from the points made by the rotated rect
            //old
            // Imgproc.drawContours(input, hullList, (int)i, color);
        }
        for (MatOfPoint hullPoints : hullList) {

            Imgproc.drawContours(input, Arrays.asList(hullPoints), -1, color, 2);
            //Todo: if it doesn't work use this
            //Imgproc.drawContours(input, hullList, (int)i, color);
            //draws the rectangle from the points made by the rotated rect
        }
        xDistance = (closestRect.center.x - cameraCenter.x);
        yDistance = (closestRect.center.y - cameraCenter.y);

        redMask.release();
        hierarchy.release();
        lowRedRange.release();
        highRedRange.release();
        hsv.release();

        //release them to stay


        return new double[] {
                xDistance, yDistance, area, angle, isSplit,
                averageRGB[0], averageRGB[1], averageRGB[2] // Append average RGB values
        };

    }



}


 /*if (farIdx1 != -1 && farIdx2 != -1) {
            int splitStart = Math.min(farIdx1, farIdx2);
            int splitEnd = Math.max(farIdx1, farIdx2);

            List<Point> part1 = contourPoints.subList(0, splitStart);
            List<Point> part2 = contourPoints.subList(splitEnd, contourPoints.size());
            part2.addAll(contourPoints.subList(0, splitStart));


            return Arrays.asList(
                    new MatOfPoint(part1.toArray(new Point[0])),
                    new MatOfPoint(part2.toArray(new Point[0]))
            );
        }


        return Arrays.asList(contour);*/