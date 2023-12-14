package org.firstinspires.ftc.masters;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

@Config
@TeleOp(name="Center Stage TeleOp", group = "competition")
public class CenterStageTeleop extends LinearOpMode {
    double y = 0;
    double x = 0;
    double rx = 0;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    private final ElapsedTime runtime = new ElapsedTime();
    DcMotor leftFrontMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor leftRearMotor = null;
    DcMotor rightRearMotor = null;

    DcMotor gpSlideRight = null;
    DcMotor gpSlideLeft = null;
    DcMotor backSlides = null;
    DcMotor hangingMotor = null;

    Servo planeLaunch;
    Servo planeRaise;
    Servo clawServo;
    Servo clawArm;
    Servo clawAngle;
    Servo cameraTurning;
    Servo outtakeHook;
    Servo outtakeRotation;
    Servo outtakeMovementRight;
    Servo outtakeMovementLeft;

    boolean clawClosed = true;

    int backSlidesTargetPos = 0;
    int v4bPresetTarget = 0;

    private enum DriveMode {
        NORMAL,
        PIXEL_SCORE,
        END_GAME
    }
    private DriveMode driveMode = DriveMode.NORMAL;
    @Override
    public void runOpMode() {
        /*
            Controls
            --------
            Gamepad2

            Gamepad1

            --------
Drive with sticks intake is the front
Four bar position 1-5 auto stack pixels
Slides position 1-11 placement on backboard
Normal mode - pixel grabbing mode - entered with left stick down
D-pad controls extendo
D-pad up - extendo fully extended with four bar in position 4
D-pad right - extendo out 2/3? with four bar in position 1
D-pad down - extendo out 1/3? with four bar in position 1
D-pad left - extendo fully in and four bar in position 1
Buttons
A - claw opens and closes
B - transfers and moves to slide pos 1
X - auto aligns and switches to pixel scoring mode
Y - press once drop one pixel, hold for drop both pixels, once both are placed outtake goes back into transfer
LB - four bar down (presets)
RB - four bar up (presets)
LT - slides down (presets)
RT - slides up (presets)

Pixel scoring mode - entered with x while in normal
Only moves right and left no forward/ backwards
LT - slides down (presets)
RT - slides up (presets)
Y - press once drop one pixel, hold for drop both pixels

Endgame mode - entered with right stick down
Normal driving
X - Auto aligns on April tag for shooter, raise shooter, shoot
LB - Hang down
RB - Hang up
        */
        leftFrontMotor = hardwareMap.dcMotor.get("frontLeft");
        rightFrontMotor = hardwareMap.dcMotor.get("frontRight");
        leftRearMotor = hardwareMap.dcMotor.get("backLeft");
        rightRearMotor = hardwareMap.dcMotor.get("backRight");

        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");

        gpSlideLeft = hardwareMap.dcMotor.get("gpSlideLeft");
        gpSlideRight = hardwareMap.dcMotor.get("gpSlideRight");
        backSlides = hardwareMap.dcMotor.get("backSlides");

        planeLaunch = hardwareMap.servo.get("planeLaunch");
        planeRaise = hardwareMap.servo.get("planeRaise");
        clawServo = hardwareMap.servo.get("clawServo");
        clawArm = hardwareMap.servo.get("clawArm");
        clawAngle = hardwareMap.servo.get("clawAngle");
        cameraTurning = hardwareMap.servo.get("cameraTurning");
        outtakeHook = hardwareMap.servo.get("outtakeHook");
        outtakeRotation = hardwareMap.servo.get("outtakeRotation");
        outtakeMovementRight = hardwareMap.servo.get("outtakeMovementRight");
        outtakeMovementLeft = hardwareMap.servo.get("outtakeMovementLeft");

        // Set the drive motor direction:
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        waitForStart();

        runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
//            telemetry.addData("linear slide encoder",  + linearSlideMotor.getCurrentPosition());

            telemetry.addData("left y", gamepad1.left_stick_y);
            telemetry.addData("left x", gamepad1.left_stick_x);
            telemetry.addData("right x", gamepad1.right_stick_x);

            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            rx = gamepad1.right_stick_x;

            switch (driveMode) {
                case NORMAL:
                    if (gamepad1.dpad_up){
//                      gp slides full extension
                    //  v4b preset 4 pixel high
                    //  claw open
                      }
                    if (gamepad1.dpad_right) {
                        //gp slides 2/3rds extension
                        //v4b preset 1 pixel high
                        //claw open
                    }
                    if (gamepad1.dpad_down){
                    //gp slides 1/3rds extension
                    //v4b preset 1 pixel high
                    //claw open
                    }
                    if (gamepad1.dpad_left) {
                        //gp slides fully in
                        //v4b preset 4 pixel high
                        //claw open
                    }
                    if (gamepad1.a && clawClosed == true) {
                        clawClosed = false;
                        clawServo.setPosition(CSCons.claw[0]);
                        sleep(300);
                    } else if (gamepad1.a && clawClosed == false) {
                        clawClosed = true;
                        clawServo.setPosition(CSCons.claw[1]);
                        sleep(300);
                    }
                    if (gamepad1.b) {
                        //slides in
                        //v4b to deposit
                        //claw angle to deposit
                        //when not busy, open claw
                        // outtake close hook
                        //right the intake system
                    }
                    if (gamepad1.x) {
                        // Solinexi's april tag alignment
                        driveMode = DriveMode.PIXEL_SCORE;
                    }
                    if (gamepad1.y) {
                        //close hook
                        //outtake movement and rotation to drop
                        // open hook and wait hyper-specific amount of time
                        if (!gamepad1.y) {
                            //close hook
                        } else {
                            // open hook
                            // wait slightly
                            // outtake movement and rotation to transfer
                        }
                    }
                    if (gamepad1.left_stick_button) {
                        driveMode = DriveMode.PIXEL_SCORE;
                        sleep(300);
                    }
                    if (gamepad1.right_stick_button) {
                        driveMode = DriveMode.END_GAME;
                        sleep(300);
                    }
                    if (gamepad1.left_bumper&&v4bPresetTarget>0) {
                        v4bPresetTarget--;
                    }
                    if (gamepad1.right_bumper && v4bPresetTarget < 4) {
                        v4bPresetTarget++;
                    }
                    if (gamepad1.left_trigger > 0.5 && backSlidesTargetPos>0){
                        backSlidesTargetPos--;
                    }
                    if (gamepad1.right_trigger > 0.5 && backSlidesTargetPos<10) {
                        backSlidesTargetPos++;
                    }
                    break;
                case PIXEL_SCORE:
                    y = 0;
                    rx = 0;
                    if (gamepad1.left_trigger > 0.5 && backSlidesTargetPos>0){
                        backSlidesTargetPos--;
                    }
                    if (gamepad1.right_trigger > 0.5 && backSlidesTargetPos<10) {
                        backSlidesTargetPos++;
                    }
                    if (gamepad1.y) {
                        //close hook
                        //outtake movement and rotation to drop
                        // open hook and wait hyper-specific amount of time
                        if (!gamepad1.y) {
                            //close hook
                        } else {
                            // open hook
                            // wait slightly
                            // outtake movement and rotation to transfer
                        }
                    }
                    break;
                case END_GAME:
                    if (gamepad1.left_bumper&&v4bPresetTarget>0) {
                        //hanging down
                    }
                    if (gamepad1.right_bumper && v4bPresetTarget < 4) {
                        //hanging up
                    }
                    if (gamepad1.x) {
                        // Solinexi's april tag alignment
                        //raise shooter
                        //FIRE!
                    }
                    break;
            }


            if (Math.abs(y) < 0.2) {
                y = 0;
            }
            if (Math.abs(x) < 0.2) {
                x = 0;
            }

            double leftFrontPower = y + x + rx;
            double leftRearPower = y -  x + rx;
            double rightFrontPower = y -  x - rx;
            double rightRearPower = y + x - rx;

            if (Math.abs(leftFrontPower) > 1 || Math.abs(leftRearPower) > 1 || Math.abs(rightFrontPower) > 1 || Math.abs(rightRearPower) > 1) {

                double max;
                max = Math.max(Math.abs(leftFrontPower), Math.abs(leftRearPower));
                max = Math.max(max, Math.abs(rightFrontPower));
                max = Math.max(max, Math.abs(rightRearPower));

                leftFrontPower /= max;
                leftRearPower /= max;
                rightFrontPower /= max;
                rightRearPower /= max;
            }

            leftFrontMotor.setPower(leftFrontPower);
            leftRearMotor.setPower(leftRearPower);
            rightFrontMotor.setPower(rightFrontPower);
            rightRearMotor.setPower(rightRearPower);

//            backSlides.setTargetPosition(CSCons.backSlidesPos[backSlidesTargetPos]);
//            backSlides.setPower(102984375678349214824736483);
//            clawArm.setPosition(CSCons.v4b_positions[v4bPresetTarget]);


            if(gamepad1.right_bumper){
                hangingMotor.setPower(.3);
            } else if (gamepad1.left_bumper) {
                hangingMotor.setPower(-.3);
            } else {
                hangingMotor.setPower(0);
            }

            telemetry.addData("Arm", clawArm.getPosition());

            telemetry.update();
        }
    }
}