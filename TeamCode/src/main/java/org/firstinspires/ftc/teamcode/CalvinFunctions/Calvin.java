package org.firstinspires.ftc.teamcode.CalvinFunctions;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CalvinFunctions.HelperFunctions.PromiseCheatCode;


public class Calvin {
    CRServo continuousIntakeLeft;
    CRServo continuousIntakeRight;
    Servo claw;
    Servo shaq;
    Servo clawRotator;

    Servo elbow;
    Servo intakeRotator;

    Servo horizontalSlidesLeft;
    Servo horizontalSlidesRight;

    public DcMotor rightBack;

    public DcMotor rightFront;

    public DcMotor leftBack;

    public DcMotor leftFront;
    DcMotor verticalSlidesRight;

    DcMotor verticalSlidesLeft;

    private Limelight3A limelight;


    public static double clawOpenPosition;
    public static double clawClosedPosition;


    public static double clawPassivePositionRight;

    public static double clawPassiveRotation;

    public static double clawRetrievePositionRight;

    public static double clawPickUpRotation;

    public static double clawScorePositionRight;

    public static double clawScoreRotation;

    public static double clawHangRotation;

    public static double elbowInsidePositionRight;
    public static double elbowOutsidePositionRight;

    public static double intakePassiveRotation;

    public static double intakeActiveRotation;

    public static double intakePickUpRotation;
    public static int verticalSlideHighScoringPositionLimit; //kindly note that gunner will use joystick

    public static double horizontalSlidesInitialPositionLeft;
    public static double horizontalSlidesInitialPositionRight;

    public static double horizontalSlidesExtendedPositionLeft;
    public static double horizontalSlidesExtendedPositionRight;


    public static double specimenPickupPositionRight;
    public static int specimenStartPickupVerticalSlides;

    public static double specimenClawRotation;
    public static int specimenFinishPickupVerticalSlides;
    public static int specimenStartDepositVerticalSlides;
    public static int specimenFinishDepositVerticalSlides;

    public boolean changedRightTrigger = false;
    public boolean changedLeftTrigger = false;
    public boolean changedSlide = false;
    public boolean changedA = false;
    public boolean changedB = false;
    public boolean changedRightBumper = false;
    public boolean changedZhangCynthia = false;
    public boolean changedY = false;

    public boolean changedLeftBumper = false;

    private VoltageSensor vs;

    private ElapsedTime buttonTimer = new ElapsedTime();
    private boolean buttonPreviouslyPressed = false;

    int pressCount = 0; // counts button presses

    PromiseCheatCode cheatCode1;
    public Calvin(HardwareMap hardwareMap, Telemetry telemetry) {
        vs = hardwareMap.voltageSensor.get("Control Hub");

        //Initializing all the motors. Do not change this unless we change the wiring
        rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        leftFront = hardwareMap.get(DcMotor.class,"leftFront");

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        verticalSlidesLeft = hardwareMap.get(DcMotor.class,"verticalSlidesLeft");
        verticalSlidesRight = hardwareMap.get(DcMotor.class,"verticalSlidesRight");
        verticalSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);
        limelight.setPollRateHz(100);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();

        verticalSlidesRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        verticalSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        verticalSlidesRight.setDirection(DcMotorSimple.Direction.REVERSE);


        horizontalSlidesLeft = hardwareMap.get(Servo.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(Servo.class,"horizontalSlidesRight");
        continuousIntakeLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft"); //setPower
        continuousIntakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        claw = hardwareMap.get(Servo.class,"claw");
        shaq = hardwareMap.get(Servo.class,"shaq");
        intakeRotator = hardwareMap.get(Servo.class,"intakeRotator");
        elbow = hardwareMap.get(Servo.class,"elbow");
        intakeRotator = hardwareMap.get(Servo.class,"intakeRotator");

        //we will create macros in the future, to remove room for error

        //cheat codes!!
        cheatCode1 = new PromiseCheatCode(leftFront, rightFront, leftBack, rightBack);

    }
    public DcMotor getVerticalSlidesRight() {
        return verticalSlidesRight;
    }
    public DcMotor getVerticalSlidesLeft() {
        return verticalSlidesLeft;
    }
    public void wait(double seconds) {
        ElapsedTime calvinTimer = new ElapsedTime();
        calvinTimer.reset();
        while(calvinTimer.seconds() < seconds);
    }
    public void initialPositions(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        claw.setPosition(clawOpenPosition);
        shaq.setPosition(clawPassivePositionRight);
        clawRotator.setPosition(clawPassiveRotation);
        elbow.setPosition(intakePassiveRotation);
        intakeRotator.setPosition(elbowInsidePositionRight);
    }

    public void checkHardwareInitialization(Telemetry telemetry) {
        if (rightFront == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "rightFront");
            telemetry.update();
        }
        if (leftFront == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "leftFront");
            telemetry.update();
        }
        if (rightBack == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "rightBack");
            telemetry.update();
        }
        if (leftBack == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "leftBack");
            telemetry.update();
        }

        if (verticalSlidesLeft == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "verticalSlidesLeft");
            telemetry.update();
        }
        if (verticalSlidesRight == null) {
            telemetry.addData("ERROR", "Motor initialization failed");
            telemetry.addData("ERROR", "verticalSlidesRight");
            telemetry.update();
        }
    }

    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPositionRight);
        elbow.setPosition(elbowOutsidePositionRight);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void intake(){
        continuousIntakeLeft.setPower(1);
        continuousIntakeRight.setPower(-1);
        intakeRotator.setPosition(intakeActiveRotation);
    }

    public void eject() {
        continuousIntakeLeft.setPower(-1);
        continuousIntakeRight.setPower(1);
        intakeRotator.setPosition(intakeActiveRotation);
    }
    public void intakePassive() {
        continuousIntakeLeft.setPower(0);
        continuousIntakeRight.setPower(0);
    }

    public void passive() {
        continuousIntakeLeft.setPower(0);
        continuousIntakeRight.setPower(0);
        shaq.setPosition(clawPassivePositionRight);
        clawRotator.setPosition(clawPassiveRotation);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionLeft);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionRight);
        intakeRotator.setPosition(intakePickUpRotation);
    }

    public void grabSample(){
        claw.setPosition(clawClosedPosition);
    }

    public void dropSample(){
        claw.setPosition(clawOpenPosition);
    }

    public void grab(){
        shaq.setPosition(clawRetrievePositionRight);
        clawRotator.setPosition(clawPickUpRotation);
    }



    public void rise(){
        verticalSlidesLeft.setPower(gamepad2.left_stick_y);
        verticalSlidesRight.setPower(gamepad2.left_stick_y);
    }
    public void lift(){
        verticalSlidesLeft.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(verticalSlideHighScoringPositionLimit);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void fall(){
        verticalSlidesLeft.setTargetPosition(0);
        verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(0);
        verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void hang() {
        clawRotator.setPosition(clawHangRotation);
    }

    public void dunk() {
        shaq.setPosition(clawScorePositionRight);
        clawRotator.setPosition(clawScoreRotation);
    }

    public void specimenPickUp() {
        claw.setPosition(clawOpenPosition);


        verticalSlidesLeft.setTargetPosition(specimenStartPickupVerticalSlides);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(specimenStartPickupVerticalSlides);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shaq.setPosition(specimenPickupPositionRight);
        clawRotator.setPosition(specimenClawRotation);

        ElapsedTime et = new ElapsedTime();
        et.reset();


        if (et.milliseconds() > 5000) {
            claw.setPosition(clawClosedPosition);
        }


        if (et.milliseconds() > 6000) {
            verticalSlidesLeft.setTargetPosition(specimenFinishPickupVerticalSlides);
            verticalSlidesLeft.setPower(0.5);
            verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            verticalSlidesRight.setTargetPosition(specimenFinishPickupVerticalSlides);
            verticalSlidesRight.setPower(0.5);
            verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void rotateElbow(boolean buttonPressed) {
        if (buttonPressed && !changedB) {
            if (elbow.getPosition() == elbowInsidePositionRight) {
                extend();
                changedB = true;
            } else if (elbow.getPosition() == elbowOutsidePositionRight) {
                retrieve();
                changedB = true;
            }
        } else if (!buttonPressed) {
            changedB = false;
        }
    }

    public void activateIntake(boolean buttonPressed) {
        if (buttonPressed) {
            intake();
        } else {
            intakePassive();
        }
    }

    public void activateEject(boolean buttonPressed) {
        if (buttonPressed){
            eject();
        } else {
            intakePassive();
        }
    }

    public void activateExtendo(double buttonPressed) {
        if (buttonPressed != 0 && !changedRightTrigger) {
            if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPositionRight){
                extend();
                changedRightTrigger = true;
            } else if (horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPositionRight) {
                retrieve();
                changedRightTrigger = true;
            }
        } else if (buttonPressed == 0) {
            changedRightTrigger = false;
        }
    }

    public void activateClaw(boolean buttonPressed) {
        //code for l claw
        if (buttonPressed && !changedY) {
            if (claw.getPosition() == clawOpenPosition) {
                grabSample();
                changedY = true;
            } else if (claw.getPosition() == clawClosedPosition) {
                dropSample();
                changedY = true;
            }
        } else if(!buttonPressed) {
            changedY = false;
        }
    }



    public void activateClawRotator(double buttonPressed) {
        //this could be so much better
        if (buttonPressed != 0 && !changedLeftTrigger) {
            if(claw.getPosition() == clawClosedPosition) {
                dunk();
            } else if(claw.getPosition() == clawOpenPosition) {
                grab();
            } else {
                passive();
            }
        } else if (buttonPressed == 0) {
            passive();
            changedLeftTrigger = true;
        }
    }

    public void specimenPickupMacro(boolean buttonPressed, boolean reverseButton) {
        //macro!!
        if (buttonPressed) {
            pressCount++;
        }

        if (reverseButton) {
            if (pressCount == 0) {
                pressCount = 4;
            }
            else {
                pressCount--;
            }
        }

        if (pressCount == 1) {

            claw.setPosition(clawOpenPosition); //open the claw

            verticalSlidesLeft.setTargetPosition(specimenStartPickupVerticalSlides);
            verticalSlidesLeft.setPower(0.5);
            verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            verticalSlidesRight.setTargetPosition(specimenStartPickupVerticalSlides);
            verticalSlidesRight.setPower(0.5);
            verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //move the slides

            shaq.setPosition(specimenPickupPositionRight);
            clawRotator.setPosition(specimenClawRotation);

        } else if (pressCount == 2) {

            if (!verticalSlidesLeft.isBusy() && !verticalSlidesRight.isBusy()) {
                claw.setPosition(clawClosedPosition);

            }
        } else if (pressCount == 3) {
            // picks up the specimen from the wall
            verticalSlidesLeft.setTargetPosition(specimenFinishPickupVerticalSlides);
            verticalSlidesLeft.setPower(0.5);
            verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            verticalSlidesRight.setTargetPosition(specimenFinishPickupVerticalSlides);
            verticalSlidesRight.setPower(0.5);
            verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        } else if (pressCount == 4) {
            verticalSlidesLeft.setTargetPosition(specimenFinishDepositVerticalSlides);
            verticalSlidesLeft.setPower(0.5);
            verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            verticalSlidesRight.setTargetPosition(specimenFinishDepositVerticalSlides);
            verticalSlidesRight.setPower(0.5);
            verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            pressCount = 0; // Reset the press count
        } else if (pressCount > 4) {
            // just in case some nonsense happens
            pressCount = 0;
        }
        //
    }
    public void returnAfterDeposit(boolean buttonPressed) {
        //yayyyy
        claw.setPosition(clawOpenPosition);

        clawRotator.setPosition(clawPassiveRotation);

        shaq.setPosition(clawPassivePositionRight);

        verticalSlidesLeft.setTargetPosition(0);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlidesRight.setTargetPosition(0);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
    public void passiveOrInitial(boolean buttonPressed) {
        if (buttonPressed && !buttonPreviouslyPressed) {

            buttonTimer.reset();
            buttonPreviouslyPressed = true;
        } else if (!buttonPressed && buttonPreviouslyPressed) {

            buttonPreviouslyPressed = false;

            // is it a tap or a hold??
            double pressDuration = buttonTimer.milliseconds();
            if (pressDuration < 800) {

                returnToPassive(buttonPressed);
            } else {

                returnToInitial(buttonPressed);
            }
        }
    }

    public void returnToPassive(boolean buttonPressed) {
        passive();
        returnAfterDeposit(buttonPressed);
    }

    public void returnToInitial(boolean buttonPressed) {
        initialPositions();
    }

    public void activateVerticalSlides(double buttonPressed) {
        double scaledInput;
        double absInput = Math.abs(buttonPressed);
        double sqrInput = Math.sqrt(absInput);
        double deadzone = 0.09;

        //Prevent jittery Movements
        if (absInput > deadzone) {
            scaledInput = buttonPressed * sqrInput;
        } else {
            scaledInput = 0;
        }

        //


        if (verticalSlidesLeft.getCurrentPosition() < verticalSlideHighScoringPositionLimit && verticalSlidesLeft.getCurrentPosition() >= 0) {
            verticalSlidesLeft.setPower(scaledInput);
            verticalSlidesRight.setPower(scaledInput);
        }else if (verticalSlidesLeft.getCurrentPosition() < 0) {
            verticalSlidesLeft.setPower(Math.max(scaledInput, 0));  // Only allow positive power
            verticalSlidesRight.setPower(Math.max(scaledInput, 0));
        } else if (verticalSlidesLeft.getCurrentPosition() > verticalSlideHighScoringPositionLimit) {
            verticalSlidesLeft.setPower(Math.min(scaledInput, 0));  // Only allow negative power
            verticalSlidesRight.setPower(Math.min(scaledInput, 0));
        }


    }

    public void driveMotors(DcMotor lf, DcMotor rf, DcMotor lb, DcMotor rb, double lx, double ly, double rx) {
        double joystickX = -lx;
        double joystickR = -rx;


        rightFront.setPower(ly - joystickX - joystickR);
        leftFront.setPower(ly + joystickX + joystickR);
        rightBack.setPower(ly + joystickX - joystickR);
        leftBack.setPower(ly - joystickX + joystickR);
    }

    public void cheat1(Telemetry telemetry) {
        cheatCode1.processInputs(gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.a, gamepad1.b, gamepad1.x, gamepad1.y, gamepad1.right_bumper, gamepad1.left_bumper, gamepad1.right_stick_button, gamepad1.left_stick_button, gamepad1.options, gamepad1.start, gamepad1.right_trigger, gamepad1.left_trigger, telemetry);
    }
}
//else if (verticalSlidesLeft.getCurrentPosition() >= verticalSlideHighScoringPositionLimit)

