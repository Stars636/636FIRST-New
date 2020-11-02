package org.firstinspires.ftc.teamcode.fishlo.v1.fishlo.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.SubSystem;

public class Drive extends SubSystem {
    private DcMotor frontLeft, backLeft, frontRight, backRight;

    LinearOpMode opmode;

    int cpr = 28;
    int gearRatio = 19;
    double diameter = 3.780;
    double cpi = (cpr * gearRatio)/(Math.PI * diameter);
    double bias = 0.8;
    double strafeBias = 0.9;

    double conversion = cpi * bias;
    boolean exit = false;
    
    public Drive(Robot robot) {

        super(robot);
    }

    @Override
    public void init() {
        frontLeft = robot.hardwareMap.dcMotor.get("frontLeft");
        frontRight = robot.hardwareMap.dcMotor.get("frontRight");
        backLeft = robot.hardwareMap.dcMotor.get("backLeft");
        backRight = robot.hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        drive(0, 0);
    }

    boolean reverse = false;

    @Override
    public void handle() {
        double speed = robot.gamepad1.right_trigger - robot.gamepad1.left_trigger;
        double direction = -robot.gamepad1.left_stick_x;

        if(robot.gamepad1.a) {
            reverse = false;
        } else if(robot.gamepad1.y) {
            reverse = true;
        }

        DriveDir driveDir = getDir(direction);

        if(Math.abs(speed) < 5 && driveDir != DriveDir.STRAIGHT) {
            drive(-direction, direction);
        } else {
            if (reverse) {
                drive(driveDir.getLeft(reverse) * speed, driveDir.getRight(reverse) * speed);
            } else {
                drive(driveDir.getRight(reverse) * speed, driveDir.getLeft(reverse) * speed);
            }
        }

        robot.telemetry.addData("Drive - Dat - speed", speed);
        robot.telemetry.addData("Drive - Dat - direction", driveDir.name());
        robot.telemetry.addData("Drive - Set - frontLeft", frontLeft.getPower());
        robot.telemetry.addData("Drive - Set - backLeft", backLeft.getPower());
        robot.telemetry.addData("Drive - Set - frontRight", frontRight.getPower());
        robot.telemetry.addData("Drive - Set - backRight", backRight.getPower());
        robot.telemetry.addData("Drive - Enc - Left", frontLeft.getCurrentPosition());
        robot.telemetry.addData("Drive - Enc - Right", frontRight.getCurrentPosition());
    }

    private DriveDir getDir(double dir) {
        if(dir <= -0.7) {
            return DriveDir.HARD_LEFT;
        } else if(dir <= -0.4) {
            return DriveDir.MEDIUM_LEFT;
        } else if(dir <= -0.1) {
            return DriveDir.EASY_LEFT;
        } else if(dir >= 0.7) {
            return DriveDir.HARD_RIGHT;
        } else if(dir >= 0.4) {
            return DriveDir.MEDIUM_RIGHT;
        } else if(dir >= 0.1) {
            return DriveDir.EASY_RIGHT;
        } else {
            return DriveDir.STRAIGHT;
        }
    }

    public enum DriveDir {
        HARD_LEFT(-1, 1),
        MEDIUM_LEFT(0, 1),
        EASY_LEFT(0.5, 1),
        STRAIGHT(1, 1),
        EASY_RIGHT(1, 0.5),
        MEDIUM_RIGHT(1, 0),
        HARD_RIGHT(1, -1);

        private double left, right;

        DriveDir(double left, double right) {
            this.left = left;
            this.right = right;
        }

        public double getLeft(boolean reverse) {
            return reverse ? -left : left;
        }

        public double getRight(boolean reverse) {
            return reverse ? -right : right;
        }
    }

    private void left(double power) {
        try {
            frontLeft.setPower(power);
            backLeft.setPower(power);
        } catch(Exception ex) {}
    }

    private void right(double power) {
        try {
            frontRight.setPower(power);
            backRight.setPower(power);
        } catch(Exception ex) {}
    }

    public void drive(double left, double right) {
        left(left);
        right(right);
    }

    public void moveToPosition(double inches, double speed) {
        int move = (int)(Math.round(inches*conversion));

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + move);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + move);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + move);
        backRight.setTargetPosition(backRight.getCurrentPosition() + move);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(speed, speed);

        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            if (exit) {
                stop();
                return;
            }
        }
        stop();
        return;
    }

    public void strafeToPosition(double inches, double speed) {
        int move = (int)(Math.round(inches * cpi * strafeBias));

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + move);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - move);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - move);
        backRight.setTargetPosition(backRight.getCurrentPosition() + move);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(speed, speed);

        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {}
        stop();
        return;
    }

    public void stop() {
        drive(0, 0);
    }


    int base = 0;
    public void resetEncoder() {
        base = backRight.getCurrentPosition();
    }

    public int getEncoder() {
        return backRight.getCurrentPosition() - base;
    }




}
