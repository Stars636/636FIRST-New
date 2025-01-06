package org.firstinspires.ftc.teamcode.RobotAndHerHelpers;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.HelperFunctions.PromiseCheatCode;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.Detector;


public class Calvin {
    public CRServo IntakeLeft;
    public CRServo IntakeRight;
    public ServoImplEx claw;
    public ServoImplEx shaq;
    public ServoImplEx clawRotator;

    public ServoImplEx elbowLeft;
    public ServoImplEx elbowRight;

    public ServoImplEx horizontalSlidesLeft;
    public ServoImplEx horizontalSlidesRight;

    public DcMotorEx rightBack;

    public DcMotorEx rightFront;

    public DcMotorEx leftBack;

    public DcMotorEx leftFront;
    public DcMotorImplEx verticalSlidesRight;

    public DcMotorImplEx verticalSlidesLeft;

    public Limelight3A limelight;

    public static double tolerance = 2;

    public static double power = 0.3;

    public static double idealSize = 30;

    public static double sizeTolerance = 5;


    public static double clawOpenPosition;
    public static double clawClosedPosition;


    public static double clawPassivePosition;

    public static double clawPassiveRotation;

    public static double clawRetrievePosition;

    public static double clawPickUpRotation;

    public static double clawScorePosition;

    public static double clawScoreRotation;

    public static double clawHangRotation;

    public static double elbowInsidePosition;
    public static double elbowOutsidePosition;






    public static int verticalSlideHighScoringPositionLimit; //kindly note that gunner will use joystick

    //public static int verticalSlideLowScoringPositionLimit;
    public static double horizontalSlidesInitialPosition;

    public static double horizontalSlidesExtendedPosition;


    public static double specimenPickupPosition;
    public static int specimenStartPickupVerticalSlides;

    public static double specimenClawRotation;
    public static int specimenFinishPickupVerticalSlides;

    public static int specimenStartDepositVerticalSlides;
    public static int specimenFinishDepositVerticalSlides;

    public boolean changedRightTrigger = false;
    public boolean changedLeftTrigger = false;


    public boolean changedB = false;


    public boolean changedRightBumper = false;
    public boolean changedZhang = false;

    public boolean changedUchida = false;
    public boolean changedY = false;

    public boolean changedLeftBumper = false;

    private VoltageSensor vs;

    private ElapsedTime buttonTimer = new ElapsedTime();
   // private boolean buttonPreviouslyPressed = false;

    private final double proportion = 1.0;

    private final double integral = 0.1;

    private final double  derivative = 0.05;
    //public PIDCoefficients pid = new PIDCoefficients(proportion, integral, derivative);

    int pressCount = 0; // counts button presses

    Detector detector;

    PromiseCheatCode cheatCode1;
    public Calvin(HardwareMap hardwareMap, Telemetry telemetry) {
        vs = hardwareMap.voltageSensor.get("Control Hub");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.setPollRateHz(100);

        limelight.pipelineSwitch(0);

        Detector detector = new Detector(leftFront, rightFront, leftBack, rightBack, limelight);


        //Initializing all the motors. Do not change this unless we change the wiring
        rightBack = hardwareMap.get(DcMotorEx.class,"rightBack");
        leftBack = hardwareMap.get(DcMotorEx.class,"leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class,"leftFront");

        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDCoefficients pid = new PIDCoefficients(proportion, integral, derivative);
        verticalSlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        verticalSlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");
        verticalSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        verticalSlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlidesRight.setMode(DcMotorImplEx.RunMode.RUN_USING_ENCODER);

        //if things don't work, consider removing this
        verticalSlidesLeft.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);
        verticalSlidesRight.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);
        //

        verticalSlidesLeft.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.BRAKE);
        verticalSlidesRight.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.BRAKE);
        verticalSlidesRight.setDirection(DcMotorSimple.Direction.REVERSE);


        horizontalSlidesLeft = hardwareMap.get(ServoImplEx.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(ServoImplEx.class,"horizontalSlidesRight");
        //one of these must be reversed
        horizontalSlidesLeft.setDirection(Servo.Direction.FORWARD);
        horizontalSlidesRight.setDirection(Servo.Direction.REVERSE);
        IntakeLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft"); //setPower
        IntakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        claw = hardwareMap.get(ServoImplEx.class,"claw");
        shaq = hardwareMap.get(ServoImplEx.class,"shaq");
        clawRotator = hardwareMap.get(ServoImplEx.class,"clawRotator");
        elbowLeft = hardwareMap.get(ServoImplEx.class,"elbowLeft");
        elbowRight = hardwareMap.get(ServoImplEx.class,"elbowRight");
        elbowRight.setDirection(Servo.Direction.REVERSE);

        //we will create macros in the future, to remove room for error

        //cheat codes!!
        cheatCode1 = new PromiseCheatCode(leftFront, rightFront, leftBack, rightBack);

    }
    //public DcMotor getVerticalSlidesRight() {
      //  return verticalSlidesRight;
    //}
    //public DcMotor getVerticalSlidesLeft() {
     //   return verticalSlidesLeft;
    //}
    public void wait(double seconds) {
        ElapsedTime calvinTimer = new ElapsedTime();
        calvinTimer.reset();
        while(calvinTimer.seconds() < seconds);
    }
    public void initialPositions(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        claw.setPosition(clawOpenPosition);
        shaq.setPosition(clawPassivePosition);
        clawRotator.setPosition(clawPassiveRotation);
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);
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
        if (limelight == null || !limelight.isConnected()) {
            telemetry.addData("ERROR", "Camera initialization failed");
            telemetry.addData("ERROR", "Limelight3A");
            telemetry.update();
        }
        if (elbowLeft == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "Elbow");
            telemetry.update();
        }
        if (horizontalSlidesRight == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "horizontalSlidesRight");
            telemetry.update();
        }
        if (horizontalSlidesLeft == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "horizontalSlidesLeft");
            telemetry.update();
        }
        if (elbowRight == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "intakeRotator");
            telemetry.update();
        }
        if (IntakeRight == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "continuousIntakeRight");
            telemetry.update();
        }
        if (IntakeLeft == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "continuousIntakeLeft");
            telemetry.update();
        }
        if (shaq == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "shaq");
            telemetry.update();
        }
        if (claw == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "claw");
            telemetry.update();
        }
        if (clawRotator == null) {
            telemetry.addData("ERROR", "Servo initialization failed");
            telemetry.addData("ERROR", "clawRotator");
            telemetry.update();
        }

    }

    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPosition);
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }

    public void intake(){
        IntakeLeft.setPower(1);
        IntakeRight.setPower(-1);
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }

    public void eject() {
        IntakeLeft.setPower(-1);
        IntakeRight.setPower(1);
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }
    public void intakePassive() {
        IntakeLeft.setPower(0);
        IntakeRight.setPower(0);
    }

    public void passive() {
        IntakeLeft.setPower(0);
        IntakeRight.setPower(0);
        shaq.setPosition(clawPassivePosition);
        clawRotator.setPosition(clawPassiveRotation);
        moveVerticalSlidesTo(0);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);
    }

    public void grabSample(){
        claw.setPosition(clawClosedPosition);
    }

    public void dropSample(){
        claw.setPosition(clawOpenPosition);
    }

    public void grab(){
        shaq.setPosition(clawRetrievePosition);
        clawRotator.setPosition(clawPickUpRotation);
    }



    public void rise(){
        verticalSlidesLeft.setPower(gamepad2.left_stick_y);
        verticalSlidesRight.setPower(gamepad2.left_stick_y);
    }
    public void lift(){
        moveVerticalSlidesTo(verticalSlideHighScoringPositionLimit);
    }
    public void fall(){
        moveVerticalSlidesTo(0);
    }
    public void hang() {
        clawRotator.setPosition(clawHangRotation);
    }

    public void dunk() {
        shaq.setPosition(clawScorePosition);
        clawRotator.setPosition(clawScoreRotation);
    }

    private void moveVerticalSlidesTo(int targetPosition) {
        verticalSlidesLeft.setTargetPosition(targetPosition);
        verticalSlidesLeft.setPower(0.5);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        verticalSlidesRight.setTargetPosition(targetPosition);
        verticalSlidesRight.setPower(0.5);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void specimenPickUp() {
        claw.setPosition(clawOpenPosition);


        moveVerticalSlidesTo(specimenStartPickupVerticalSlides);

        shaq.setPosition(specimenPickupPosition);
        clawRotator.setPosition(specimenClawRotation);

        ElapsedTime et = new ElapsedTime();
        et.reset();


        if (et.milliseconds() > 5000) {
            claw.setPosition(clawClosedPosition);
        }

        /*
        if (et.milliseconds() > 6000) {
            moveVerticalSlidesTo(specimenFinishPickupVerticalSlides);
        }
        */

        if (et.milliseconds() > 6000) {
            moveVerticalSlidesTo(specimenStartDepositVerticalSlides);
        }
    }

    public void specimenDeposit() {
        moveVerticalSlidesTo(specimenFinishDepositVerticalSlides);

        ElapsedTime et = new ElapsedTime();
        et.reset();

        if (et.milliseconds() > 1000) {
            claw.setPosition(clawOpenPosition);
        }

    }

    public void rotateElbow(boolean buttonPressed) {
        if (buttonPressed && !changedB) {
            if (elbowLeft.getPosition() == elbowInsidePosition) {
                extend();
                changedB = true;
            } else if (elbowLeft.getPosition() == elbowOutsidePosition) {
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
            if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPosition){
                extend();
                changedRightTrigger = true;
            } else if (horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPosition) {
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

    enum SpecimenPickupState {
        IDLE, MOVE_TO_START, CLOSE_CLAW, MOVE_TO_FINISH_PICKUP, MOVE_TO_DEPOSIT
    }

    SpecimenPickupState specimenPickupState = SpecimenPickupState.IDLE;

    public void specimenPickupMacro(boolean buttonPressed, boolean reverseButton, Telemetry telemetry) {
        telemetry.addData("State", specimenPickupState);
        telemetry.update();
        switch (specimenPickupState) {
            case IDLE:
                if (buttonPressed && !changedLeftBumper) {
                    changedLeftBumper = true;
                    specimenPickupState = SpecimenPickupState.MOVE_TO_START;
                } else if(!buttonPressed){
                    changedLeftBumper = false;
                }
                if (reverseButton && !changedRightBumper) {
                    moveVerticalSlidesTo(specimenFinishPickupVerticalSlides);

                    changedRightBumper = true;
                    specimenPickupState = SpecimenPickupState.MOVE_TO_DEPOSIT;

                } else if(!reverseButton) {
                    changedRightBumper = false;
                }
                break;
            case MOVE_TO_START:
                if (buttonPressed && !changedLeftBumper) {
                    claw.setPosition(clawOpenPosition); //open the claw

                    moveVerticalSlidesTo(specimenStartPickupVerticalSlides);
                    //move the slides

                    shaq.setPosition(specimenPickupPosition);
                    clawRotator.setPosition(specimenClawRotation);

                    changedLeftBumper = true;
                    specimenPickupState = SpecimenPickupState.CLOSE_CLAW;
                } else if(!buttonPressed){
                    changedLeftBumper = false;
                }
                if (reverseButton && !changedRightBumper) {
                    passive();
                    changedRightBumper = true;
                    specimenPickupState = SpecimenPickupState.IDLE;
                } else if(!reverseButton) {
                    changedRightBumper = false;
                }

                break;
            case CLOSE_CLAW:
                if (buttonPressed && !changedLeftBumper) {
                    if (!verticalSlidesLeft.isBusy() && !verticalSlidesRight.isBusy()) {
                        claw.setPosition(clawClosedPosition);
                    }
                    changedLeftBumper = true;
                    specimenPickupState = SpecimenPickupState.MOVE_TO_FINISH_PICKUP;
                } else if(!buttonPressed){
                    changedLeftBumper = false;
                }
                if (reverseButton && !changedRightBumper) {
                    claw.setPosition(clawOpenPosition); //open the claw

                    moveVerticalSlidesTo(specimenStartPickupVerticalSlides);
                    //move the slides

                    shaq.setPosition(specimenPickupPosition);
                    clawRotator.setPosition(specimenClawRotation);

                    changedRightBumper = true;
                    specimenPickupState = SpecimenPickupState.MOVE_TO_START;

                } else if(!reverseButton) {
                    changedRightBumper = false;
                }

                break;
            case MOVE_TO_FINISH_PICKUP:
                if (buttonPressed && !changedLeftBumper) {

                    moveVerticalSlidesTo(specimenFinishPickupVerticalSlides);

                    changedLeftBumper = true;
                    specimenPickupState = SpecimenPickupState.MOVE_TO_DEPOSIT;
                } else if(!buttonPressed){
                    changedLeftBumper = false;
                }
                if (reverseButton && !changedRightBumper) {

                        claw.setPosition(clawClosedPosition);

                    changedRightBumper = true;
                    specimenPickupState = SpecimenPickupState.CLOSE_CLAW;

                } else if(!reverseButton) {
                    changedRightBumper = false;
                }

                break;
            case MOVE_TO_DEPOSIT:
                if (buttonPressed && !changedLeftBumper) {
                   moveVerticalSlidesTo(specimenFinishDepositVerticalSlides);

                    changedLeftBumper = true;
                    specimenPickupState = SpecimenPickupState.IDLE;
                } else if(!buttonPressed){
                    changedLeftBumper = false;
                }
                if (reverseButton && !changedRightBumper) {
                    moveVerticalSlidesTo(specimenFinishPickupVerticalSlides);

                    changedRightBumper = true;
                    specimenPickupState = SpecimenPickupState.MOVE_TO_FINISH_PICKUP;

                } else if(!reverseButton) {
                    changedRightBumper = false;
                }

                break;
        }


    }

   /* public void specimenPickupMacro(boolean buttonPressed, boolean reverseButton) {
        //macro!!
        if (buttonPressed && !changedLeftBumper) {
            pressCount++;
            changedLeftBumper = true;
        } else if (!buttonPressed) {
            changedLeftBumper = false;
        }

        if (reverseButton && !changedRightBumper) {
            if (pressCount == 0) {
                pressCount = 4;
                changedRightBumper = true;
            }
            else {
                pressCount--;
                changedRightBumper = true;
            }
        } else if (!reverseButton) {
            changedRightBumper = false;
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

            shaq.setPosition(specimenPickupPosition);
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
    }*/
    public void returnAfterDeposit(boolean buttonPressed) {

        //yayyyy
        claw.setPosition(clawOpenPosition);

        clawRotator.setPosition(clawPassiveRotation);

        shaq.setPosition(clawPassivePosition);

        moveVerticalSlidesTo(0);
    }
    enum PassiveOrInitialState {
        IDLE, BUTTON_PRESSED, TAP, HOLD
    }

    PassiveOrInitialState passiveOrInitialState = PassiveOrInitialState.IDLE;

    public void passiveOrInitial(boolean buttonPressed) {
        switch (passiveOrInitialState) {
            case IDLE:
                if (buttonPressed) {
                    passiveOrInitialState = PassiveOrInitialState.BUTTON_PRESSED;
                    buttonTimer.reset();
                }
                break;
            case BUTTON_PRESSED:
                if (!buttonPressed) {
                    if (buttonTimer.milliseconds() < 1000) {
                        passiveOrInitialState = PassiveOrInitialState.TAP;
                    } else {
                        passiveOrInitialState = PassiveOrInitialState.HOLD;
                    }
                }
                break;
            case TAP:
                returnToPassive(buttonPressed);
                passiveOrInitialState = PassiveOrInitialState.IDLE;
                break;
            case HOLD:
                returnToInitial(buttonPressed);
                passiveOrInitialState = PassiveOrInitialState.IDLE;
                break;
        }
    }

    /*public void passiveOrInitial(boolean buttonPressed) {
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
    }*/

    public void returnToPassive(boolean buttonPressed) {
        if (buttonPressed && !changedZhang) {
            passive();
            returnAfterDeposit(buttonPressed);
            changedZhang = true;
        } else if(!buttonPressed){
            changedZhang = false;
        }

    }

    public void returnToInitial(boolean buttonPressed) {
        if (buttonPressed && !changedUchida) {
            initialPositions();
            changedUchida = true;
        } else if(!buttonPressed){
            changedUchida = false;
        }

    }

    public void activateVerticalSlides(double buttonPressed) {
        double scaledInput;
        double absInput = Math.abs(buttonPressed);
        double sqrInput = Math.sqrt(absInput);
        double deadzone = 0.09;

        //Prevent jittery Movements
        if (absInput > deadzone) {
            scaledInput = Math.min(1,buttonPressed + sqrInput);
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


        rf.setPower(ly - joystickX - joystickR);
        lf.setPower(ly + joystickX + joystickR);
        rb.setPower(ly + joystickX - joystickR);
        lb.setPower(ly - joystickX + joystickR);
    }

    public void detection(Telemetry telemetry){
        detector.findSpecimen(telemetry);
    }

    public void cheat1(Telemetry telemetry) {
        cheatCode1.processInputs(gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.a, gamepad1.b, gamepad1.x, gamepad1.y, gamepad1.right_bumper, gamepad1.left_bumper, gamepad1.right_stick_button, gamepad1.left_stick_button, gamepad1.options, gamepad1.start, gamepad1.right_trigger, gamepad1.left_trigger, telemetry);
    }
}


