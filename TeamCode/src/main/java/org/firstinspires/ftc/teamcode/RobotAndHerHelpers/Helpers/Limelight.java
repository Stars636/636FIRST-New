package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;
@Disabled
public class Limelight {
    //just trying things out
    public DcMotorEx leftFront, leftBack, rightFront, rightBack;

    public Limelight3A limelight;


    public static double tolerance = 2;

    public static double power = 0.3;

    public static double idealSize = 30;

    public static double sizeTolerance = 5;

    //Okay so let's break what we want to do into steps.
    //First, we want the limelight to detect the specimen, make sure its in the right spot and calibrated properly
    //Second, we want the limelight to return data on the distance from the specimen
    //Third, we want to add an offset based on our robot(i.e where is the claw)
    //Fourth, we want to get the robots current pose, and make it parallel to the specimen
    //Fifth, we want the bot to correct for error and movement, i.e. strafe to follow the specimen
    //Sixth, we want the bot to pickup the specimen
    //Seventh, quickly.

    /* https://docs.limelightvision.io/docs/docs-limelight/getting-started/FTC/pipelines */
    //First, we want the limelight to detect the specimen,
    public Limelight(DcMotorEx leftFront, DcMotorEx rightFront, DcMotorEx leftBack, DcMotorEx rightBack, Limelight3A limelight) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;
        this.limelight = limelight;


    }

    // make sure its in the right spot and calibrated
    public void initializeLimelight(Telemetry telemetry) {
        limelight.pipelineSwitch(0);

        telemetry.setMsTransmissionInterval(11);

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
        //limelight.start();
        //do we want the limelight always on, or do we want the limelight to turn on we want declare it to)

    }
    //Second, we want the limelight to return data on the distance from the specimen
    public void getBotPose(Telemetry telemetry, IMU imu) {
        LLStatus status = limelight.getStatus();
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = limelight.getLatestResult();
        if (result != null) {
            // Access general information
            Pose3D botpose = result.getBotpose();
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa();
            telemetry.addData("LL Latency", captureLatency + targetingLatency);
            telemetry.addData("Parse Latency", parseLatency);
            telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));

            if (result.isValid()) {
                telemetry.addData("tx", tx);
                telemetry.addData("txnc", result.getTxNC());
                telemetry.addData("ty", ty);
                telemetry.addData("tync", result.getTyNC());

                telemetry.addData("Botpose", botpose.toString());

                // Access barcode results
                List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
                for (LLResultTypes.BarcodeResult br : barcodeResults) {
                    telemetry.addData("Barcode", "Data: %s", br.getData());
                }

                // Access classifier results
                List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
                for (LLResultTypes.ClassifierResult cr : classifierResults) {
                    telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
                }

                // Access detector results
                List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
                for (LLResultTypes.DetectorResult dr : detectorResults) {
                    telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                }

                // Access fiducial results
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                }

                // Access color results
                List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                for (LLResultTypes.ColorResult cr : colorResults) {
                    telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
                }
            }
        } else {
            telemetry.addData("Limelight", "No data available");
        }

        telemetry.update();
    }

//Suppose the limelight sees the specimen.
    //First, we either want the limelight to swing in a circle
    //or, adjust the heading to be parallel, and move to the side to align the robot
    public void align(LLResult result) {
        //double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        //YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        LLStatus status = limelight.getStatus();

        if (result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            // Pose3D botpose = result.getBotpose();
            if (!isAligned(tx)) {
                if (Math.abs(tx) > tolerance && tx > 0) {
                    leftFront.setPower(power);
                    rightFront.setPower(-power);
                    rightBack.setPower(-power);
                    leftBack.setPower(power);
                } else if (Math.abs(tx) > tolerance && tx < 0) {
                    leftFront.setPower(-power);
                    rightFront.setPower(power);
                    rightBack.setPower(power);
                    leftBack.setPower(-power);
                }

            }
        }

    }

    public boolean isAligned(double tx) {
        if (Math.abs(tx) <= tolerance) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isCorrectDistance(double ta) {
        if(Math.abs(ta - idealSize) <= tolerance) {
            return true;
        } else {
            return false;
        }
    }
    public void approachCarefully(LLResult result) {
        if (result.isValid()) {
            double tx = result.getTx();
            double ta = result.getTa();
            if(isAligned(tx) && !isCorrectDistance(ta)) {
                rightFront.setPower(-power);
                leftFront.setPower(-power);
                rightBack.setPower(-power);
                leftBack.setPower(-power);
            }
        }

    }



}
