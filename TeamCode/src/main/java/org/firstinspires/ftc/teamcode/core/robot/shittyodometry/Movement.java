package org.firstinspires.ftc.teamcode.core.robot.shittyodometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;

import org.firstinspires.ftc.teamcode.core.robot.tools.IMU;
import org.firstinspires.ftc.teamcode.core.robot.tools.headless.AutoLift;
import org.firstinspires.ftc.teamcode.opmodes.util.MyToggleButtonReader;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class Movement {
    private final SampleMecanumDrive drive;
    private final GamepadEx moveGamepad;
    private final MyToggleButtonReader yReader;
    private final BNO055IMU imu;
    private final Encoder leftEncoder, frontEncoder;
    private final AutoLift lift;

    private double headingOffset = 0;
    private double forwardOffset = 0;
    private double sidewaysOffset = 0;

    private States state = States.MOVEFORWARD;
    private boolean firstTimeAuto = true;


    public Movement(HardwareMap hardwareMap, GamepadEx gamepad, AutoLift lift) {
        this.imu = IMU.create(hardwareMap);
        this.drive = new SampleMecanumDrive(hardwareMap);
        this.moveGamepad = gamepad;
        this.yReader = new MyToggleButtonReader(this.moveGamepad, GamepadKeys.Button.Y);
        this.leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "intake"));
        this.frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "backEncoder"));
        this.frontEncoder.setDirection(Encoder.Direction.REVERSE);
        this.lift = lift;
    }
    public SampleMecanumDrive getDrive() {
        return drive;
    }

    private double getHeading() {
        return imu.getAngularOrientation().firstAngle - Math.abs(headingOffset);
    }

    private void resetHeading() {
        headingOffset = imu.getAngularOrientation().firstAngle;
    }

    private double getForwardMovement() {
        return StandardTrackingWheelLocalizer.encoderTicksToInches(leftEncoder.getCurrentPosition() - Math.abs(forwardOffset));
    }

    private void resetForwardMovement() {
        forwardOffset = leftEncoder.getCurrentPosition();
    }

    private double getSidewaysMovement() {
        return StandardTrackingWheelLocalizer.encoderTicksToInches(frontEncoder.getCurrentPosition() - Math.abs(sidewaysOffset));
    }

    private void resetSidewaysMovement() {
        sidewaysOffset = frontEncoder.getCurrentPosition();
    }

    private void zeroMotors() {
        drive.setWeightedDrivePower(new Pose2d(
                0,
                0,
                0
        ));
    }

    private enum States {
        MOVEFORWARD,
        TURN,
        DUMP,
        TURNBACK,
        MOVEBACK
    }

    public void update() {
        yReader.readValue();
        if (!yReader.getState()) {
            firstTimeAuto = true;
            drive.setWeightedDrivePower(new Pose2d(
                    moveGamepad.getLeftY(),
                    -moveGamepad.getLeftX(),
                    moveGamepad.getRightX()
            ));
        } else {
            if (firstTimeAuto) {
                zeroMotors();
                resetForwardMovement();
                firstTimeAuto = false;
                state = States.MOVEFORWARD;
            }
            double heading = getHeading();
            double forward = getForwardMovement();

            switch (state) {
                case MOVEFORWARD:
                    if (forward < 19.5) {
                        drive.setWeightedDrivePower(new Pose2d(
                                0.4,
                                0,
                                0
                        ));
                    } else {
                        state = States.TURN;
                        resetHeading();
                    }
                    break;
                case TURN:
                    if (heading > -15) {
                        drive.setWeightedDrivePower(new Pose2d(
                                0,
                                0,
                                -0.4
                        ));
                    } else {
                        state = States.DUMP;
                        resetHeading();
                        lift.setPosition(AutoLift.Positions.DUMPTSE);
                    }
                    break;
                case DUMP:
                    if (lift.getPosition() != AutoLift.Positions.INTAKING) {
                        state = States.TURNBACK;
                    }
                    break;
                case TURNBACK:
                    if (heading < 15) {
                        drive.setWeightedDrivePower(new Pose2d(
                                0,
                                0,
                                0.4
                        ));
                    } else {
                        state = States.MOVEBACK;
                        resetForwardMovement();
                    }
                    break;
                case MOVEBACK:
                    if (forward > -19.5) {
                        drive.setWeightedDrivePower(new Pose2d(
                                -0.4,
                                0,
                                0
                        ));
                    } else {
                        zeroMotors();
                        yReader.currToggleState = false;
                    }
                    break;
            }
        }
    }
}
