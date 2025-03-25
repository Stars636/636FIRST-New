package org.firstinspires.ftc.teamcode.ANewEngland.Bot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class CalvinNE {
    public DcMotorEx leftFront, rightFront, leftBack, rightBack;
    public DcMotorImplEx vSlidesLeft, vSlidesRight;





    public ServoImplEx intakeClaw, intakeWrist, intakeElbow, intakeArm;

    public ServoImplEx depositClaw, depositArm, depositWrist;

    public ServoImplEx hSlidesLeft, hSlidesRight;

    //tentative hang code
    public DcMotorImplEx pullUpRight, pullUpLeft;
    public ServoImplEx hookRight, hookLeft;

    public static int lowBucket; //todo: test

    public static int highSpecimen; //todo: test

    public static int lowSpecimen; //todo: test(low priority)

    public static int highBucket; //todo: test


    public static double depositClawOpen;  //todo: test
    public static double depositClawClosed;  //todo: test
    public static double depositClawPassivePos;    //todo: test

    public static double depositClawPassiveRot; //todo: test

    public static double depositClawTransferPos;  //todo: test

    public static double depositClawTransferRot;   //todo: test

    public static double depositClawScorePos;  //todo: test

    public static double depositClawScoreRot;  //todo: test
    public static double depositClawSpeciPosStart;  //todo: test

    public static double depositClawSpeciRotStart;  //todo: test

    public static double depositClawSpeciPosFinish;  //todo: test
    public static double depositClawSpeciRotFinish ;  //todo: test

    public static double increment;  //todo: test
    public static double floatPosition;  //todo: test


    public static double hSlidesInside;  //todo: test
    public static double hSlidesOutside;  //todo: test
    public static double intakeClawOpen;  //todo: test
    public static double intakeClawClosed;  //todo: test
    public static double intakeClawTransferPos;  //todo: test
    public static double intakeClawTransferRot;  //todo: test
    public static double intakeClawPassivePos;  //todo: test
    public static double intakeClawPassiveRot;  //todo: test
    public static double intakeClawHoverPos;  //todo: test
    public static double intakeClawHoverRot;  //todo: test
    public static double intakeClawGrabPos;  //todo: test
    public static double intakeClawGrabRot;  //todo: test
    public static double intakeWristFlat;  //todo: test
    public static double intakeWristTiltRight;  //todo: test
    public static double intakeWristNormalLeft;  //todo: test
    public static double intakeWristNormalRight; //as in perpendicular  //todo: test
    //Todo: refactor this to a better name
    public static double intakeWristTiltLeft;  //todo: test


    public ElapsedTime transferTime = new ElapsedTime();
    public static double transferPart1; //todo: test
    public static double transferPart2; //todo: test
    public static double transferPart3; //todo: test
    public static double transferPart4; //todo: test

    public ElapsedTime pickUpTime = new ElapsedTime();

    public static double pickUp1;//lower this over time LOL //todo: test
    public static double pickUp2; //todo: test
    public ElapsedTime specimenTime = new ElapsedTime();
    public static double specimenPart1; //todo: test

    public static boolean isMacroing = false;


    public static boolean isTargeting = false; //Todo: make the vertical slides able to go to a specific position


    public CalvinNE(HardwareMap hardwareMap) {
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");//
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");//
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");//
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");//

        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        vSlidesLeft = hardwareMap.get(DcMotorImplEx.class, "vSlidesLeft");//
        vSlidesRight = hardwareMap.get(DcMotorImplEx.class, "vSlidesRight");//

        vSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vSlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        vSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        vSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        vSlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);





        //Todo: uncomment these when they've been plugged in, null pointer exception issue


        //

        intakeClaw = hardwareMap.get(ServoImplEx.class, "intakeClaw");//
        intakeWrist = hardwareMap.get(ServoImplEx.class, "intakeWrist");//
        intakeElbow = hardwareMap.get(ServoImplEx.class, "intakeElbow");//
        intakeArm = hardwareMap.get(ServoImplEx.class, "intakeArm");//

        depositClaw = hardwareMap.get(ServoImplEx.class, "depositClaw");//
        depositArm = hardwareMap.get(ServoImplEx.class, "depositArm");//
        depositWrist = hardwareMap.get(ServoImplEx.class, "depositWrist");//


        hSlidesLeft = hardwareMap.get(ServoImplEx.class, "hSlidesLeft");//
        hSlidesRight = hardwareMap.get(ServoImplEx.class, "hSlidesRight");//
        hSlidesLeft.setDirection(Servo.Direction.FORWARD);
        hSlidesRight.setDirection(Servo.Direction.REVERSE);


        depositArm.setPwmRange(new PwmControl.PwmRange(500, 2500));
        depositWrist.setPwmRange(new PwmControl.PwmRange(500, 2500));
        intakeArm.setPwmRange(new PwmControl.PwmRange(500, 2500));
        intakeElbow.setPwmRange(new PwmControl.PwmRange(500, 2500));
        intakeWrist.setPwmRange(new PwmControl.PwmRange(500, 2500));

    }

    public void initialTele() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
        intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
        depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);

    }

    public void initialSpecimen() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
        intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
        depositClaw.setPosition(depositClawClosed);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
    }

    public void initialBucket() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
        intakeClaw.setPosition(intakeClawOpen);
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
        depositClaw.setPosition(depositClawClosed);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
    }


    //FIXME
    // -Add all the basic functions


    public void depositClawOpen() {
        depositClaw.setPosition(depositClawOpen);
    }

    public void depositClawClosed() {
        depositClaw.setPosition(depositClawClosed);
    }

    public void hSlidesIn() {
        hSlidesLeft.setPosition(hSlidesInside);
        hSlidesRight.setPosition(hSlidesInside);
    }

    public void hSlidesOut() {
        hSlidesLeft.setPosition(hSlidesOutside);
        hSlidesRight.setPosition(hSlidesOutside);
    }

    public void intakePassive() {

        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawPassiveRot);
        intakeArm.setPosition(intakeClawPassivePos);
    }

    public void depositPassive() {
        //depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawPassiveRot);
        depositArm.setPosition(depositClawPassivePos);
    }

    public void depositScore() {
        depositWrist.setPosition(depositClawScoreRot);
        depositArm.setPosition(depositClawScorePos);
    }

    public void depositSpecimenStart() {
        depositWrist.setPosition(depositClawSpeciPosStart);
        depositArm.setPosition(depositClawSpeciRotStart);
    }


    //Todo: Create a function for scoring specimens, whether using the timer or not
    // -Currently questionable
    // -Function for the pickup
    // -Function for score (maybe with timer) (if you use a statemachine move it to tele)
    // -I'll leave this to you. :)

    public enum SpecimenPickupSteps {
        READY, FINAL
    }

    public SpecimenPickupSteps specimenStep = SpecimenPickupSteps.READY;

    public void scoreSpecimen(boolean buttonPressed, boolean lastButtonPressed) { //so on so forth
        switch (specimenStep) {
            case READY:
                if (buttonPressed && !lastButtonPressed) {
                    isMacroing = true;
                    depositClaw.setPosition(depositClawClosed);
                    specimenTime.reset();
                    specimenStep = SpecimenPickupSteps.FINAL;
                }
                break;
            case FINAL:
                if (specimenTime.seconds() >= specimenPart1) {
                    depositWrist.setPosition(depositClawSpeciRotFinish);
                    depositArm.setPosition(depositClawSpeciPosFinish);
                    isMacroing = false;
                    specimenStep = SpecimenPickupSteps.READY;
                }

                break;
        }
    }

    //Todo: Create a function for automating the transfer, whether using the timer or not
    // -Functions for everything needs for intake
    //      -Will we need to have modes to switch between for all the controls?
    // -Functions for the transfer
    // -Functions for the scoring
    public void hover() {
        intakeElbow.setPosition(intakeClawHoverRot);
        intakeArm.setPosition(intakeClawHoverPos);
    }

    public void grab() {
        intakeElbow.setPosition(intakeClawGrabRot);
        intakeArm.setPosition(intakeClawGrabPos);
    }

    //Todo: find out if set all this positions simultaneuously actually works
    // -otherwise, use timers

    //Todo: timer version of the pickup from chamber
    public enum PickUpSteps {
        READY, MOVE, GRAB
    }

    public PickUpSteps pickUpStep = PickUpSteps.READY;

    public void pickUp(boolean buttonPressed, boolean lastButtonPressed) {
        switch (pickUpStep) {
            case READY:
                if (buttonPressed && !lastButtonPressed) {
                    isMacroing = true;
                    pickUpTime.reset();
                    pickUpStep = PickUpSteps.MOVE;
                }
                break;
            case MOVE:
                grab();
                if (pickUpTime.seconds() >= pickUp1) {
                    pickUpTime.reset();
                    pickUpStep = PickUpSteps.GRAB;
                }
                break;
            case GRAB:
                if (intakeClaw.getPosition() == intakeClawClosed) {
                    intakeClaw.setPosition(intakeClawOpen);
                } else if (intakeClaw.getPosition() == intakeClawOpen) {
                    intakeClaw.setPosition(intakeClawClosed);
                }
                if (pickUpTime.seconds() >= pickUp2) {
                    hover();
                    isMacroing = false;
                    pickUpStep = PickUpSteps.READY;
                }
                break;

        }
    }

    //Todo: this is the timer version (i.e only one button press after closing a claw on sample
    //i.e just use a state machine you guys know what that is
    //Todo: move this to your teleop of choice, it only works in the while loop
    // i think??
    public enum TransferSteps {
        READY, MOVE, GRAB, LETGO, RETURN;
    }

    // READY: Waits for the button press to start the transfer sequence
// MOVE: Moves components to the initial transfer position
// GRAB: Closes the deposit claw to secure the object
// LETGO: Opens the intake claw to release the object
// RETURN: Resets all components to their default positions
    public TransferSteps transferStep = TransferSteps.READY;


    public void transferStartClosed() {
        intakeWrist.setPosition(intakeWristFlat);
        intakeElbow.setPosition(intakeClawTransferRot);
        intakeArm.setPosition(intakeClawTransferPos);
        depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawTransferRot);
        depositArm.setPosition(depositClawTransferPos);
    }

    public void transferStartOpen() {
        intakeWrist.setPosition(intakeWristNormalLeft);
        intakeElbow.setPosition(intakeClawTransferRot);
        intakeArm.setPosition(intakeClawTransferPos);
        depositClaw.setPosition(depositClawOpen);
        depositWrist.setPosition(depositClawTransferRot);
        depositArm.setPosition(depositClawTransferPos);
    }

    //Todo: in macros, the timer should be reset AT THE END OF THE STEP TO SET UP FOR THE NEXT
    // otherwise, THE TIMER WILL BE RESET EVERY CYCLE AND NOT WORK
    // I THINK
    public void transferEnd(boolean buttonPressed, boolean lastButtonPressed) {
        switch (transferStep) {
            case READY:
                if (buttonPressed && !lastButtonPressed) {
                    depositPassive();
                    isMacroing = true;
                    transferTime.reset();
                    transferStep = TransferSteps.MOVE;
                }
                break;
            case MOVE:
                if (transferTime.seconds() >= transferPart1) {
                    if (intakeClaw.getPosition() == intakeClawClosed) {
                        transferStartClosed();
                        transferTime.reset();
                        transferStep = TransferSteps.GRAB;
                    }
                    if (intakeClaw.getPosition() == intakeClawOpen) {
                        transferStartOpen();
                        transferTime.reset();
                        transferStep = TransferSteps.GRAB;
                    }
                }
                break;
            case GRAB:
                if (transferTime.seconds() >= transferPart2) {
                    depositClaw.setPosition(depositClawClosed);
                    transferTime.reset();
                    transferStep = TransferSteps.LETGO;
                }
                break;
            case LETGO:
                if (transferTime.seconds() >= transferPart3) {
                    intakeClaw.setPosition(intakeClawOpen);
                    transferTime.reset();
                    transferStep = TransferSteps.RETURN;
                }

                break;
            case RETURN:
                if (transferTime.seconds() >= transferPart4) {
                    intakeWrist.setPosition(intakeWristFlat);
                    intakeElbow.setPosition(intakeClawPassiveRot);
                    intakeArm.setPosition(intakeClawPassivePos);

                    depositWrist.setPosition(depositClawPassiveRot);
                    depositArm.setPosition(depositClawPassivePos);
                    isMacroing = false;
                    transferStep = TransferSteps.READY;
                }
                break;
        }
    }
    //We can always make the timer faster, so please don't worry

    //Todo: Auto functions
/*

}
public class Claw {
    Calvin calvin = new Calvin(new HardwareMap());

    
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            calvin.intakeClaw.setPosition(intakeClawClosed);
            return false;
        }
    }

    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            calvin.intakeClaw.setPosition(intakeClawOpen);
            return false;
        }
    }

    public Action openClaw() {
        return new OpenClaw();
    }
}
*/
}
