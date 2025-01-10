package org.firstinspires.ftc.teamcode.RobotAndHerHelpers;

//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.HelperFunctions.PromiseCheatCode;

@Config
public class Calvin {
    public CRServo intakeLeft;
    public CRServo intakeRight;
    public CRServo intakeUp;
    
    public ServoImplEx claw;
    public ServoImplEx shaq;
    public ServoImplEx clawRotator;

    public ServoImplEx elbowLeft;
    public ServoImplEx elbowRight;

    public ServoImplEx horizontalSlidesLeft;
    public ServoImplEx horizontalSlidesRight;

    public DcMotorEx rightBackCalvin;

    public DcMotorEx rightFrontCalvin;

    public DcMotorEx leftBackCalvin;

    public DcMotorEx leftFrontCalvin;
   // public DcMotorImplEx verticalSlidesRight;

    //public DcMotorImplEx verticalSlidesLeft;

    //public Limelight3A limelight;

    //public static double tolerance = 2;

    //public static double power = 0.3;

    //public static double idealSize = 30;

    //public static double sizeTolerance = 5;

    public boolean switchMode = false;

    public static double intakeUpSpeed = 0.7;


    public static double clawOpenPosition = 0.17;
    public static double clawClosedPosition = 0.30;


    public static double clawPassivePosition = 0.65;

    public static double clawPassiveRotation = 0.43;

    public static double clawRetrievePosition = 0.9;

    public static double clawPickUpRotation = 0.06;

    public static double clawScorePosition = 0.4;

    public static double clawScoreRotation = 1;

    public static double clawHangRotation = 1;

    public static double elbowInsidePosition = 0.07;
    public static double elbowOutsidePosition = 0.75;






    public static int verticalSlideHighScoringPositionLimit = 3150; //kindly note that gunner will use joystick

    //public static int verticalSlideLowScoringPositionLimit = 1500;
    public static double horizontalSlidesInitialPosition = 0.99;

    public static double horizontalSlidesExtendedPosition = 0.68;


    //public static double specimenPickupPosition = 0;

    public static double specimenClawPosition = 0.01;

    public static double specimenDepositClawRotation = 1;

    //public static int specimenStartPickupVerticalSlides = 0;



   // public static int specimenFinishPickupVerticalSlides = 1000;

    public static int specimenStartDepositVerticalSlides = 1000;
    public static int specimenFinishDepositVerticalSlides = 0;

    public boolean changedRightTrigger = false;
    public boolean changedLeftTrigger = false;


    public boolean changedB = false;

    public boolean changedExtend = false;

    public boolean changedZhang = false;

    public boolean changedUchida = false;


    public boolean changedY = false;


    private ElapsedTime buttonTimer = new ElapsedTime();

    public PromiseCheatCode cheatCode1 = new PromiseCheatCode(leftFrontCalvin, rightFrontCalvin, leftBackCalvin, rightBackCalvin, claw, shaq, clawRotator);





    public Calvin(HardwareMap hardwareMap, Telemetry telemetry) {
        //Initializing all the motors. Do not change this unless we change the wiring
        rightBackCalvin = hardwareMap.get(DcMotorEx.class,"rightBack");
        leftBackCalvin = hardwareMap.get(DcMotorEx.class,"leftBack");
        rightFrontCalvin = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftFrontCalvin = hardwareMap.get(DcMotorEx.class,"leftFront");

        rightFrontCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBackCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFrontCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBackCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFrontCalvin.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackCalvin.setDirection(DcMotorSimple.Direction.REVERSE);




        horizontalSlidesLeft = hardwareMap.get(ServoImplEx.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(ServoImplEx.class,"horizontalSlidesRight");

        horizontalSlidesLeft.setDirection(Servo.Direction.FORWARD);
        horizontalSlidesRight.setDirection(Servo.Direction.REVERSE);
        intakeLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft"); //setPower
        intakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        intakeUp = hardwareMap.get(CRServo.class, "continuousIntakeUp"); //setPower

        claw = hardwareMap.get(ServoImplEx.class,"claw");
        shaq = hardwareMap.get(ServoImplEx.class,"shaq");

        clawRotator = hardwareMap.get(ServoImplEx.class,"clawRotator");
        clawRotator.setPwmRange(new PwmControl.PwmRange(500,2500));
        shaq.setPwmRange(new PwmControl.PwmRange(500,2500));
        elbowLeft = hardwareMap.get(ServoImplEx.class,"elbowLeft");
        elbowRight = hardwareMap.get(ServoImplEx.class,"elbowRight");
        elbowRight.setDirection(Servo.Direction.REVERSE);

        //PromiseCheatCode cheatCode1 = new PromiseCheatCode(leftFrontCalvin, rightFrontCalvin, leftBackCalvin, rightBackCalvin, claw, shaq, clawRotator);
    }

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
    public void initialPositions2(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        claw.setPosition(clawOpenPosition);
        //shaq.setPosition(clawPassivePosition);
        clawRotator.setPosition(clawPassiveRotation);
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);

    }


    public void extend(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPosition);
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }
    public void elbowIn(){
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);
    }
    public void elbowOut(){
        elbowLeft.setPosition(elbowOutsidePosition);
        elbowRight.setPosition(elbowOutsidePosition);
    }
    public void extendoIn(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
    }
    public void extendoOut(){
        horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesExtendedPosition);

    }

    public void intake(){
        intakeLeft.setPower(-1);
        intakeRight.setPower(1);
        intakeUp.setPower(intakeUpSpeed);

    }

    public void eject() {
        intakeLeft.setPower(1);
        intakeRight.setPower(-1);
        intakeUp.setPower(-intakeUpSpeed);

    }
    public void intakePassive() {
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
        intakeUp.setPower(0);
    }

    public void passive() {
        shaq.setPosition(clawPassivePosition);
        clawRotator.setPosition(clawPassiveRotation);
    }

    public void retrieve(){
        horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        elbowLeft.setPosition(elbowInsidePosition);
        elbowRight.setPosition(elbowInsidePosition);
    }

    public void returnAfterDeposit() {

        //yayyyy
        claw.setPosition(clawOpenPosition);

        clawRotator.setPosition(clawPassiveRotation);

        shaq.setPosition(clawPassivePosition);

        //moveVerticalSlidesTo(0);
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
    public void hang() {
        clawRotator.setPosition(clawHangRotation);
    }

    public void dunk() {
        shaq.setPosition(clawScorePosition);
        clawRotator.setPosition(clawScoreRotation);
    }
    public void specimenScore(){
        clawRotator.setPosition(specimenDepositClawRotation);
        shaq.setPosition(specimenClawPosition);
    }



  /*  public void moveVerticalSlidesTo(int targetPosition) {
        verticalSlidesLeft.setTargetPosition(targetPosition);
        verticalSlidesLeft.setPower(1);
        verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        verticalSlidesRight.setTargetPosition(targetPosition);
        verticalSlidesRight.setPower(1);
        verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }*/

    /*public void specimenPickUp() {
        claw.setPosition(clawOpenPosition);


        moveVerticalSlidesTo(specimenStartPickupVerticalSlides);

        shaq.setPosition(specimenPickupPosition);
        clawRotator.setPosition(specimenDepositClawRotation);

        ElapsedTime et = new ElapsedTime();
        et.reset();


        if (et.milliseconds() > 5000) {
            claw.setPosition(clawClosedPosition);
        }

        /*
        if (et.milliseconds() > 6000) {
            moveVerticalSlidesTo(specimenFinishPickupVerticalSlides);
        }


        if (et.milliseconds() > 6000) {
            moveVerticalSlidesTo(specimenStartDepositVerticalSlides);
        }
    }*/

    /*public void specimenDeposit() {

        moveVerticalSlidesTo(specimenFinishDepositVerticalSlides);

        ElapsedTime et = new ElapsedTime();
        et.reset();

        if (et.milliseconds() > 1000) {
            claw.setPosition(clawOpenPosition);
        }

    }*/

    public enum switchScoringMode {
        BASKET, SPECIMEN
    }
    public switchScoringMode scoringMode = switchScoringMode.BASKET;

    enum ScoringButton {
        IDLE, BUTTON_PRESSED, TAP, HOLD
    }
    ScoringButton scoringSwitchButton = ScoringButton.IDLE;
    ElapsedTime scoringTimer = new ElapsedTime();
    /*public void activateSwitchScoring(boolean buttonPressed) {
        switch (scoringSwitchButton) {
            case IDLE:
                if (buttonPressed) {
                    scoringSwitchButton = ScoringButton.BUTTON_PRESSED;
                    scoringTimer.reset();
                }
                break;
            case BUTTON_PRESSED:
                if (!buttonPressed) {
                    if (scoringTimer.milliseconds() < 800) {
                        scoringSwitchButton = ScoringButton.TAP;
                    } else {
                        scoringSwitchButton = ScoringButton.HOLD;
                    }
                }
                break;
            case TAP:
                //...
                scoringSwitchButton = ScoringButton.IDLE;
                break;
            case HOLD:
                switch (scoringMode) {
                    case BASKET:
                        scoringMode = switchScoringMode.SPECIMEN;
                    case SPECIMEN:
                        scoringMode = switchScoringMode.BASKET;
                }
                scoringSwitchButton = ScoringButton.IDLE;
                break;
        }
    }

    public void activateSwitchScoring(double buttonPressed) {
        switch (scoringSwitchButton) {
            case IDLE:
                if (buttonPressed != 0) {
                    scoringSwitchButton = ScoringButton.BUTTON_PRESSED;
                    scoringTimer.reset();
                }
                break;
            case BUTTON_PRESSED:
                if (buttonPressed == 0) {
                    if (scoringTimer.milliseconds() < 800) {
                        scoringSwitchButton = ScoringButton.TAP;
                    } else {
                        scoringSwitchButton = ScoringButton.HOLD;
                    }
                }
                break;
            case TAP:
                //...
                scoringSwitchButton = ScoringButton.IDLE;
                break;
            case HOLD:
                switch (scoringMode) {
                    case BASKET:
                        scoringMode = switchScoringMode.SPECIMEN;
                    case SPECIMEN:
                        scoringMode = switchScoringMode.BASKET;
                }
                scoringSwitchButton = ScoringButton.IDLE;
                break;
        }
    }*/

    /*public void activateSwitchScoring(double buttonPressed) {
        if (buttonPressed != 0 && !switchMode) {
            switch (scoringMode) {
                case SPECIMEN:
                    scoringMode = switchScoringMode.BASKET;
                    switchMode = true;
                case BASKET:
                    scoringMode = switchScoringMode.SPECIMEN;
                    switchMode = true;

            }
        } else if (buttonPressed == 0) {
            switchMode = false;
        }
    }*/

   /* public void activateSwitchScoring(boolean buttonPressed) {
        if (buttonPressed && !switchMode) {
            switch (scoringMode) {
                case SPECIMEN:
                    scoringMode = switchScoringMode.BASKET;
                    switchMode = true;
                case BASKET:
                    scoringMode = switchScoringMode.SPECIMEN;
                    switchMode = true;

            }
        } else if (!buttonPressed ) {
            switchMode = false;
        }
    }*/

    public void activateRotateElbow(boolean buttonPressed) {
        if (buttonPressed && !changedB) {
            if (elbowLeft.getPosition() == elbowInsidePosition) {
                elbowOut();
                changedB = true;
            } else if (elbowLeft.getPosition() == elbowOutsidePosition) {
                elbowIn();
                changedB = true;
            } else {
                elbowIn();
                changedB = true;
            }
        } else if (!buttonPressed) {
            changedB = false;
        }


    }
    public void activateRotateElbow(double buttonPressed) {
        if (buttonPressed != 0 && !changedB) {
            if (elbowLeft.getPosition() == elbowInsidePosition) {
                elbowOut();
                changedB = true;
            } else if (elbowLeft.getPosition() == elbowOutsidePosition) {
                elbowIn();
                changedB = true;
            } else {
                elbowIn();
                changedB = true;
            }
        } else if (buttonPressed == 0) {
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
    public void activateIntake(double buttonPressed) {
        if (buttonPressed != 0) {
            intake();
        } else {
            intakePassive();
        }

    }

    public void activateEject(boolean buttonPressed) {
        if (buttonPressed) {
          eject();
        } else {
            intakePassive();


        }
    }
    public void activateEject(double buttonPressed) {
        if (buttonPressed != 0) {
            eject();
        } else {
            intakePassive();


        }
    }

    public void activateExtendo(double buttonPressed) {
        if (buttonPressed != 0 && !changedRightTrigger) {
            if (horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPosition){
                extendoIn();
                changedRightTrigger = true;
            } else if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPosition) {
                extendoOut();
                changedRightTrigger = true;
            } else {
                extendoIn();
            }
        } else if (buttonPressed == 0) {
            changedRightTrigger = false;
        }
    }

    public void activateExtendo(boolean buttonPressed) {
        if (!buttonPressed  && !changedRightTrigger) {
            if (horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPosition){
                extendoIn();
                changedRightTrigger = true;
            } else if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPosition) {
                extendoOut();
                changedRightTrigger = true;
            } else {
                extendoIn();
            }
        } else if (!buttonPressed ) {
            changedRightTrigger = false;
        }
    }

    public void activateFullExtension(double buttonPressed) {
        if (buttonPressed != 0 && !changedExtend) {
            if(horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPosition) {
                retrieve();
                changedExtend = true;
            } else if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPosition) {
                extend();
                changedExtend = true;
            } else {
                retrieve();
                changedExtend = true;
            }
        } else if (buttonPressed == 0) {
            changedExtend = false;
        }
    }

    public void activateFullExtension(boolean buttonPressed) {
        if (!buttonPressed && !changedExtend) {
            if(horizontalSlidesRight.getPosition() == horizontalSlidesExtendedPosition) {
                retrieve();
                changedExtend = true;
            } else if (horizontalSlidesRight.getPosition() == horizontalSlidesInitialPosition) {
                extend();
                changedExtend = true;
            } else {
                retrieve();
                changedExtend = true;
            }
        } else if (!buttonPressed) {
            changedExtend = false;
        }
    }


    public void activateClaw(boolean buttonPressed) {

        //code for l claw
        if (buttonPressed && !changedY) {
            //claw.setPwmEnable();
            if (claw.getPosition() == clawOpenPosition) {
                claw.setPosition(clawClosedPosition);
                changedY = true;
            } else if (claw.getPosition() == clawClosedPosition) {
                claw.setPosition(clawOpenPosition);
                changedY = true;
            } else {
                claw.setPosition(clawOpenPosition);
            }
        } else if(!buttonPressed) {
            changedY = false;
            //claw.setPwmDisable();
        }
    }
    public void activateClaw(double buttonPressed) {

        //code for l claw
        if (buttonPressed != 0 && !changedY) {
            //claw.setPwmEnable();
            if (claw.getPosition() == clawOpenPosition) {
                claw.setPosition(clawClosedPosition);
                changedY = true;
            } else if (claw.getPosition() == clawClosedPosition) {
                claw.setPosition(clawOpenPosition);
                changedY = true;
            } else {
                claw.setPosition(clawOpenPosition);
            }
        } else if(buttonPressed == 0) {
            changedY = false;
            //claw.setPwmDisable();
        }
    }



    public void activateScore(boolean buttonPressed) {
        //this could be so much better

                if (buttonPressed && !changedLeftTrigger) {
                    dunk();
                    changedLeftTrigger = true;
                } else if (!buttonPressed) {
                    changedLeftTrigger = false;
                }



    }


    public void activateScore(double buttonPressed) {
        //this could be so much better

                if (buttonPressed != 0 && !changedLeftTrigger) {
                    dunk();
                    changedLeftTrigger = true;
                } else if (buttonPressed == 0) {
                    changedLeftTrigger = false;
                }



    }
    public boolean changedTrigger = false;
    public void activateSpecimen(boolean buttonPressed) {
        if (buttonPressed && !changedLeftTrigger) {
            specimenScore();
            changedTrigger = true;
        } else if (!buttonPressed) {
            changedTrigger = false;
        }
    }
    public void activateSpecimen(double buttonPressed) {
        if (buttonPressed != 0 && !changedLeftTrigger) {
            specimenScore();
            changedTrigger = true;
        } else if (buttonPressed == 0) {
            changedTrigger = false;
        }


    }

    public void activateCollectIntake(boolean buttonPressed) {
        if (buttonPressed && !changedLeftTrigger) {
            grab();
            changedLeftTrigger = true;
        } else if (!buttonPressed) {
            changedLeftTrigger = false;
        }
    }

    public void activateCollectIntake(double buttonPressed) {
        if (buttonPressed != 0 && !changedLeftTrigger) {
            grab();
            changedLeftTrigger = true;
        } else if (buttonPressed == 0) {
            changedLeftTrigger = false;
        }
    }



   public enum PassiveOrInitialState {
        IDLE, BUTTON_PRESSED, TAP, HOLD
    }

    public PassiveOrInitialState passiveOrInitialState = PassiveOrInitialState.IDLE;

    public boolean passiveQuestion = false;

    public void activatePassiveOrInitial(boolean buttonPressed) {
        if (buttonPressed && !passiveQuestion) {
            passive();
            passiveQuestion = true;
        } else if (!buttonPressed) {
            passiveQuestion = false;
        }
    }

    public void activatePassiveOrInitial(double buttonPressed) {
        switch (passiveOrInitialState) {
            case IDLE:
                if (buttonPressed != 0) {
                    passiveOrInitialState = PassiveOrInitialState.BUTTON_PRESSED;
                    buttonTimer.reset();
                }
                break;
            case BUTTON_PRESSED:
                if (buttonPressed == 0) {
                    if (buttonTimer.milliseconds() < 1200) {
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

    public void returnToPassive(boolean buttonPressed) {
        if (buttonPressed && !changedZhang) {
            passive();
            returnAfterDeposit();
            changedZhang = true;
        } else if(!buttonPressed){
            changedZhang = false;
        }

    }
    public void returnToPassive(double buttonPressed) {
        if (buttonPressed != 0 && !changedZhang) {
            passive();
            returnAfterDeposit();
            changedZhang = true;
        } else if(buttonPressed == 0){
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
    public void returnToInitial(double buttonPressed) {
        if (buttonPressed != 0 && !changedUchida) {
            initialPositions();
            changedUchida = true;
        } else if(buttonPressed == 0){
            changedUchida = false;
        }

    }
    /*public void activateVerticalSlides(double buttonPressed) {
        if (buttonPressed != 0) {
            if (verticalSlidesLeft.getCurrentPosition() < verticalSlideHighScoringPositionLimit && verticalSlidesLeft.getCurrentPosition() >= 0) {
                verticalSlidesLeft.setPower(buttonPressed);
                verticalSlidesRight.setPower(buttonPressed);
            } else if (verticalSlidesLeft.getCurrentPosition() < 0) {
                verticalSlidesLeft.setPower(Math.max(buttonPressed, 0));  // Only allow positive power
                verticalSlidesRight.setPower(Math.max(buttonPressed, 0));
            } else if (verticalSlidesLeft.getCurrentPosition() > verticalSlideHighScoringPositionLimit) {
                verticalSlidesLeft.setPower(Math.min(buttonPressed, 0));  // Only allow negative power
                verticalSlidesRight.setPower(Math.min(buttonPressed, 0));
            }



    }*/
    public void driveMotors(DcMotor lf, DcMotor rf, DcMotor lb, DcMotor rb, double lx, double ly, double rx) {
        double joystickX = -lx;
        double joystickR = -rx;


        rf.setPower(ly - joystickX - joystickR);
        lf.setPower(ly + joystickX + joystickR);
        rb.setPower(ly + joystickX - joystickR);
        lb.setPower(ly - joystickX + joystickR);
    }

    public void cheat1(boolean dL, boolean dR, boolean dU, boolean dD, boolean a, boolean b, boolean x, boolean y, boolean rB, boolean lB, boolean r3, boolean l3, boolean o, boolean s, double rT, double lT, Telemetry telemetry) {
        cheatCode1.processInputs(dL, dR, dU, dD, a, b, x, y, rB, lB, r3, l3, o, s, rT, lT, telemetry);
        telemetry.addData("inputs",cheatCode1.inputTracker);
        telemetry.update();
    }


}


