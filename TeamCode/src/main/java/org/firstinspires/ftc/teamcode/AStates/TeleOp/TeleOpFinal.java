package org.firstinspires.ftc.teamcode.AStates.TeleOp;

import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassivePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassiveRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawScorePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawScoreRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciPosFinish;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciPosStart;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciRotFinish;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawSpeciRotStart;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawTransferPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawTransferRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.highBucket;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.increment;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawPassivePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawPassiveRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawTransferPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawTransferRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristFlat;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristNormalLeft;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristNormalRight;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltLeft;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltRight;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

@Config
@TeleOp (group = "States", name = "RUN THIS PLEASE")
public class TeleOpFinal extends LinearOpMode {


//Tele


    public static boolean isSpecimen = false;
    boolean changedX = false;
    boolean changedY = false;

    boolean changedA = false;
    boolean changedB = false;

    boolean changedRST = false;
    boolean changedRB = false;
    boolean changedLB = false;

    boolean changedUp = false;
    boolean changedLeft = false;
    boolean changedRight = false;
    public ElapsedTime transferTime = new ElapsedTime();
    public static double transferPart1 = 0.3;
    public static double transferPart2 = 0.7;
    public static double transferPart3 = 0.1;
    public static double hooking = 0;

    public static double transferPart4 = 0.1;
    public static double transferPart5 = 0.7;



    //Todo: slides are likely slow af. increase this verticalIncrement
    //slides r
Calvin calvin;
    public ElapsedTime pickUpTime = new ElapsedTime();

    public static double pickUp1 = 0.1;//lower this over time LOL
    public static double pickUp2 = 0.1;
    public ElapsedTime specimenTime = new ElapsedTime();
    public static double specimenPart0 = 1;
    public static double specimenPart1 = 2;

    public static boolean isMajorMacroing = false;

    //macroing

    double intakeClawPos = 0.4;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime timer = new ElapsedTime();
        //todo:maybe call differently?
        calvin = new Calvin(hardwareMap);
        //
        //Todo: LAST GAMEPAD is NOT working
        //The code is so messy rn im really sorry but i'm a little off





        waitForStart();

        calvin.initialBucket();


        telemetry.addLine("Best Wishes!");
        telemetry.update();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            intakeClawPos = calvin.intakeClaw.getPosition();
            //TODO: If you're here
            //if something doesn't work start here
            //intake claw
            //TODO: change button

            //calvin.transferEnd(gamepad2.right_bumper, lastGamepad2.right_bumper);

            switch(transferStep) {
                case READY:
                    if (gamepad2.right_bumper && !changedRB) {
                        calvin.depositWrist.setPosition(depositClawPassiveRot);
                        calvin.depositArm.setPosition(depositClawPassivePos);
                        armMacro = ArmMacro.PASSIVE;
                        calvin.depositClaw.setPosition(depositClawOpen);
                        depositClawMacro = DepositClawMacro.OPENED;
                        calvin.hSlidesLeft.setPosition(hSlidesInside);
                        calvin.hSlidesRight.setPosition(hSlidesInside);
                        intakeClawPos = calvin.intakeClaw.getPosition();
                        isMajorMacroing = true;
                        transferTime.reset();
                        changedRB = true;
                        transferStep = TransferSteps.MOVE;
                    } else if (!gamepad2.right_bumper) {
                        changedRB = false;
                    }
                    break;
                case MOVE:
                        if (!gamepad2.right_bumper) {
                            changedRB = false;
                        }

                        calvin.hSlidesLeft.setPosition(hSlidesInside);
                        calvin.hSlidesRight.setPosition(hSlidesInside);
                        if (transferTime.seconds() > transferPart1) {
                            if (intakeClawPos == intakeClawClosed) {
                                telemetry.addData("what is happening", calvin.intakeClaw.getPosition());
                                telemetry.update();
                                calvin.intakeWrist.setPosition(intakeWristFlat);
                                calvin.intakeElbow.setPosition(intakeClawTransferRot);
                                calvin.intakeArm.setPosition(intakeClawTransferPos);
                                transferTime.reset();
                                transferStep = TransferSteps.TWICE;
                            } else {
                                telemetry.addData("i know what's happening", calvin.intakeClaw.getPosition());
                                telemetry.update();
                                //calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                                //todo: needs fix, robot is not recognized intakeClawClosed
                                calvin.intakeWrist.setPosition(intakeWristFlat);
                                calvin.intakeElbow.setPosition(intakeClawTransferRot);
                                calvin.intakeArm.setPosition(intakeClawTransferPos);
                                transferTime.reset();
                                transferStep = TransferSteps.TWICE;
                            }
                        }
                    break;
                case TWICE:
                    if (transferTime.seconds() >= transferPart2){
                        telemetry.addLine("yes1");

                       calvin.depositClaw.setPosition(depositClawOpen);
                        depositClawMacro = DepositClawMacro.OPENED;
                       calvin.depositWrist.setPosition(depositClawTransferRot);
                       calvin.depositArm.setPosition(depositClawTransferPos);

                       transferTime.reset();
                       transferStep = TransferSteps.GRAB;
                            //if not working remove else if

                    }
                    break;
                case GRAB:
                    if (transferTime.seconds() >= transferPart3) {
                        telemetry.addLine("yes2");
                        calvin.depositClaw.setPosition(depositClawClosed);
                        depositClawMacro = DepositClawMacro.CLOSED;
                        transferTime.reset();
                        transferStep = TransferSteps.LETGO;
                    }
                    break;
                case LETGO:
                    if (transferTime.seconds() >= transferPart4) {
                        telemetry.addLine("yes3");
                        calvin.intakeClaw.setPosition(intakeClawOpen);
                        intakeClawMacro = IntakeClawMacro.CLOSED;
                        transferTime.reset();
                        transferStep = TransferSteps.RETURN;
                    }

                    break;
                case RETURN:
                    if (transferTime.seconds() >= transferPart5) {
                        telemetry.addLine("yes4");
                        calvin.intakeWrist.setPosition(intakeWristFlat);
                        calvin.intakeElbow.setPosition(intakeClawPassiveRot);
                        calvin.intakeArm.setPosition(intakeClawPassivePos);


                        calvin.depositWrist.setPosition(depositClawPassiveRot);
                        calvin.depositArm.setPosition(depositClawPassivePos);
                        armMacro = ArmMacro.PASSIVE;
                        transferTime.reset();
                        armMacro = ArmMacro.PASSIVE;
                        isMajorMacroing = false;
                        transferStep = TransferSteps.READY;
                    }
                    break;
            }
            //erm
            //todo: fix button issue

            if(gamepad2.x && !changedX) {
                if(calvin.intakeClaw.getPosition() == intakeClawClosed) {
                    calvin.intakeClaw.setPosition(intakeClawOpen);
                    changedX = true;
                } else if (calvin.intakeClaw.getPosition() == intakeClawOpen){
                    calvin.intakeClaw.setPosition(intakeClawClosed);
                    changedX = true;
                } else {
                    calvin.intakeClaw.setPosition(intakeClawClosed);
                    changedX = true;
                }
            } else if (!gamepad2.x) {
                changedX = false;
            }
            //Todo: check that transfer even works
            // -these are both state machines and require the most testing and scrutiny
            //Todo: make backups?
            //calvin.pickUp(gamepad2.left_bumper, lastGamepad2.left_bumper);
            switch(pickUpStep) {
                case READY:
                    if (gamepad2.left_bumper && !changedLB) {
                        isMajorMacroing = true;
                        pickUpTime.reset();
                        changedLB = true;
                        pickUpStep = PickUpSteps.MOVE;
                    } else if (!gamepad2.left_bumper) {
                        changedLB = false;
                    }
                    break;
                case MOVE:
                    calvin.grab();
                    if (!gamepad2.left_bumper) {
                        changedLB = false;
                    }
                    if (pickUpTime.seconds() >= pickUp1) {
                        pickUpTime.reset();
                        pickUpStep = PickUpSteps.DECIDE;
                    }
                    break;
                case DECIDE:
                    if (calvin.intakeClaw.getPosition() == intakeClawClosed) {
                        calvin.intakeClaw.setPosition(intakeClawOpen);
                        intakeClawMacro = IntakeClawMacro.OPENED;
                        pickUpStep = PickUpSteps.GRAB;
                    } else if (calvin.intakeClaw.getPosition() == intakeClawOpen) {
                        calvin.intakeClaw.setPosition(intakeClawClosed);
                        intakeClawMacro = IntakeClawMacro.CLOSED;
                        pickUpStep = PickUpSteps.GRAB;
                    }
                    break;
                case GRAB:
                    if (pickUpTime.seconds() >= pickUp2) {
                        calvin.hover();
                        isMajorMacroing = false;
                        pickUpStep = PickUpSteps.READY;
                    }
                    break;

            }

            //
            //Natural horizontal slides
            //Todo: change horizontal slides so that driver can increment it
            //Todo: whenever extendo comes out the arm should be forward facing

            //TODO: decide how hover works
            double changedBy = calvin.hSlidesLeft.getPosition();
            if (calvin.hSlidesLeft.getPosition() < hSlidesInside && calvin.hSlidesLeft.getPosition() >= hSlidesOutside) {
                if (gamepad2.left_trigger != 0) {
                    changedBy += (increment * gamepad2.left_trigger);
                    calvin.hSlidesLeft.setPosition(changedBy);
                    calvin.hSlidesRight.setPosition(changedBy);
                }
            }

            if (calvin.hSlidesLeft.getPosition() <= hSlidesInside && calvin.hSlidesLeft.getPosition() > hSlidesOutside) {
                if (gamepad2.right_trigger != 0) {
                    changedBy -= (increment * gamepad2.right_trigger);
                    calvin.hSlidesLeft.setPosition(changedBy);
                    calvin.hSlidesRight.setPosition(changedBy);
                    if (!isMajorMacroing) {
                        calvin.hover();
                    }
                }
            }
            //Todo: handle edge cases
            //DONE
            if (calvin.hSlidesLeft.getPosition() > hSlidesInside) {
                calvin.hSlidesLeft.setPosition(hSlidesInside);
                calvin.hSlidesRight.setPosition(hSlidesInside);
            }
            if (calvin.hSlidesLeft.getPosition() < hSlidesOutside) {
                calvin.hSlidesLeft.setPosition(hSlidesOutside);
                calvin.hSlidesRight.setPosition(hSlidesOutside);
            }
            //Todo: wrist code somewhat overcomplicated
            if(gamepad2.dpad_left && !changedLeft) {
                if (calvin.intakeWrist.getPosition() == intakeWristFlat) {
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                    changedLeft = true;
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltLeft) {
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                    changedLeft = true;
                } else if (calvin.intakeWrist.getPosition() == intakeWristNormalRight) {
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                    changedLeft = true;
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltRight) {
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                    changedLeft = true;
                }
            } else if (!gamepad2.dpad_left) {
                changedLeft = false;
            }
            if(gamepad2.dpad_right && !changedRight) {
                if (calvin.intakeWrist.getPosition() == intakeWristFlat) {
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                    changedRight = true;
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltLeft) {
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                    changedRight = true;
                } else if (calvin.intakeWrist.getPosition() == intakeWristNormalLeft) {
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                    changedRight = true;
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltRight) {
                    calvin.intakeWrist.setPosition(intakeWristNormalRight);
                    changedRight = true;
                }
            } else if (!gamepad2.dpad_right) {
                changedRight = false;
            }
            //Todo: deposit

            switch (intakeClawMacro){
                case OPENED:
                    if(gamepad2.x && !changedX) {
                        calvin.intakeClaw.setPosition(intakeClawClosed);
                        changedX = true;
                        intakeClawMacro = IntakeClawMacro.CLOSED;
                    } else if (!gamepad2.x) {
                        changedX = false;
                    }
                    break;
                case CLOSED:
                    if(gamepad2.x && !changedX) {
                        calvin.intakeClaw.setPosition(intakeClawOpen);
                        changedX = true;
                        intakeClawMacro = IntakeClawMacro.OPENED;
                    } else if (!gamepad2.x) {
                        changedX = false;
                    }
                    break;
            }
            switch (depositClawMacro){
                case OPENED:
                    if(gamepad2.y && !changedY) {
                        calvin.depositClaw.setPosition(depositClawClosed);
                        changedY = true;
                        depositClawMacro = DepositClawMacro.CLOSED;
                    } else if (!gamepad2.y) {
                        changedY = false;
                    }
                    break;
                case CLOSED:
                    if(gamepad2.y && !changedY) {
                        calvin.depositClaw.setPosition(depositClawOpen);
                        changedY = true;
                        depositClawMacro = DepositClawMacro.OPENED;
                    } else if (!gamepad2.y) {
                        changedY = false;
                    }
                    break;
            }




                switch(armMacro) {
                    case PASSIVE:
                        if (!isMajorMacroing) {
                            if (gamepad2.a && !changedA) {
                                calvin.depositWrist.setPosition(depositClawScoreRot);
                                calvin.depositArm.setPosition(depositClawScorePos);
                                changedA = true;
                                armMacro = ArmMacro.SCORE;
                            } else if (!gamepad2.a) {
                                changedA = false;
                            }
                            if (gamepad2.b && !changedB) {
                                calvin.depositWrist.setPosition(depositClawSpeciRotStart);
                                calvin.depositArm.setPosition(depositClawSpeciPosStart);
                                changedB = true;
                                armMacro = ArmMacro.SPECIMENSTART;
                            } else if (!gamepad2.b) {
                                changedB = false;
                            }
                        }
                        break;

                    case SCORE:
                        if (!isMajorMacroing) {
                            if (gamepad2.a && !changedA) {
                                calvin.depositWrist.setPosition(depositClawPassiveRot);
                                calvin.depositArm.setPosition(depositClawPassivePos);
                                changedA = true;
                                armMacro = ArmMacro.PASSIVE;
                            } else if (!gamepad2.a) {
                                changedA = false;
                            }
                            if (gamepad2.b && !changedB) {
                                calvin.depositWrist.setPosition(depositClawSpeciRotStart);
                                calvin.depositArm.setPosition(depositClawSpeciPosStart);
                                changedB = true;
                                armMacro = ArmMacro.SPECIMENSTART;
                            } else if (!gamepad2.b) {
                                changedB = false;
                            }
                        }
                        break;

                    case SPECIMENSTART:
                        if (!isMajorMacroing) {
                            if (gamepad2.a && !changedA) {
                                calvin.depositWrist.setPosition(depositClawScoreRot);
                                calvin.depositArm.setPosition(depositClawScorePos);
                                changedA = true;
                                armMacro = ArmMacro.SCORE;
                            } else if (!gamepad2.a) {
                                changedA = false;
                            }
                            if (gamepad2.b && !changedB) {
                                calvin.depositWrist.setPosition(depositClawPassiveRot);
                                calvin.depositArm.setPosition(depositClawPassivePos);
                                changedB = true;
                                armMacro = ArmMacro.PASSIVE;
                            } else if (!gamepad2.b) {
                                changedB = false;
                            }
                        }
                        break;
                    case SPECIMENFINISH:
                        if (!isMajorMacroing) {
                            if (gamepad2.a && !changedA) {
                                calvin.depositWrist.setPosition(depositClawPassiveRot);
                                calvin.depositArm.setPosition(depositClawPassivePos);
                                changedA = true;
                                armMacro = ArmMacro.PASSIVE;
                            } else if (!gamepad2.a) {
                                changedA = false;
                            }
                            if (gamepad2.b && !changedB) {
                                calvin.depositWrist.setPosition(depositClawPassiveRot);
                                calvin.depositArm.setPosition(depositClawPassivePos);
                                changedB = true;
                                armMacro = ArmMacro.PASSIVE;
                            } else if (!gamepad2.b) {
                                changedB = false;
                            }
                        }
                        break;
                    default:
                        armMacro = ArmMacro.PASSIVE;
                        break;

                }

            //Todo: make a better macro for this specimen stuff somehow
            // - also find a better button haha
            //specimen scoring
            //Todo: check if this double press fix actually works
            //Only if you want


            if (gamepad1.x )

            switch (specimenStep) {
                case READY:
                    if (gamepad2.dpad_up && !changedUp) {
                        isMajorMacroing = true;
                        calvin.depositClaw.setPosition(depositClawClosed);
                        depositClawMacro = DepositClawMacro.CLOSED;
                        specimenTime.reset();
                        //slideTimer.reset();
                        changedUp = true;
                        specimenStep = SpecimenSteps.CHILL;
                    } else if (!gamepad2.dpad_up){
                        changedUp = false;
                    }
                    break;
                case CHILL:
                    if (!gamepad2.dpad_up){
                        changedUp = false;
                    }
                    //idk if we wanna do something here
                    if (specimenTime.seconds() >= specimenPart0) {
                        specimenTime.reset();
                        specimenStep = SpecimenSteps.FINAL;
                    }
                    break;
                case FINAL:
                    if (!gamepad2.dpad_up){
                        changedUp = false;
                    }
                    if (specimenTime.seconds() >= specimenPart1) {
                        calvin.depositWrist.setPosition(depositClawSpeciRotFinish);
                        calvin.depositArm.setPosition(depositClawSpeciPosFinish);
                        isMajorMacroing = false;
                        specimenTime.reset();
                        armMacro = ArmMacro.SPECIMENFINISH;
                        specimenStep = SpecimenSteps.READY;
                    }
                    break;
            }

            if (gamepad2.dpad_down) {
                if (!isMajorMacroing){
                    calvin.depositArm.setPosition(depositClawPassivePos);
                    calvin.depositWrist.setPosition(depositClawPassiveRot);
                }
            }

            //Todo: Vertical Slide Improvements

            if (calvin.vSlidesRight.getCurrentPosition() < highBucket && calvin.vSlidesRight.getCurrentPosition() >= 0) {
                calvin.vSlidesLeft.setPower(-gamepad2.left_stick_y);
                calvin.vSlidesRight.setPower(-gamepad2.left_stick_y);
            } else if (calvin.vSlidesRight.getCurrentPosition() > 0) {
                calvin.vSlidesLeft.setPower(Math.min(-gamepad2.left_stick_y, 0));  // Only allow positive power
                calvin.vSlidesRight.setPower(Math.min(-gamepad2.left_stick_y, 0));
            } else if (calvin.vSlidesRight.getCurrentPosition() < highBucket) {
                calvin.vSlidesLeft.setPower(Math.max(-gamepad2.left_stick_y, 0));  // Only allow negative power
                calvin.vSlidesRight.setPower(Math.max(-gamepad2.left_stick_y, 0));
            }
            //Todo: DRIVER CONTROLS
            // - I.E. DRIVETRAIN and HANG

            //TODO: Driving
            //thanks, david
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.right_trigger != 0) {
                calvin.leftFront.setPower((1 - gamepad1.right_trigger) * frontLeftPower);
                calvin.leftBack.setPower((1 - gamepad1.right_trigger) * backLeftPower);
                calvin.rightFront.setPower((1 - gamepad1.right_trigger) * frontRightPower);
                calvin.rightBack.setPower((1 - gamepad1.right_trigger)* backRightPower);
            } else {
                calvin.leftFront.setPower(frontLeftPower);
                calvin.leftBack.setPower(backLeftPower);
                calvin.rightFront.setPower(frontRightPower);
                calvin.rightBack.setPower(backRightPower);
            }



            //TODO: Hang
            // ....
            // ....
            // Conrad kindly mention that x and y should move the servos and
            // a and b should move the motors? i think
            telemetry.addData("isMacroing", isMajorMacroing);
            telemetry.addData("SpecimenMacro", specimenStep);
            telemetry.addData("SpecimenMacro Timer", specimenTime.seconds());
            telemetry.addData("PickupMacro", pickUpStep);
            telemetry.addData("PickupMacro Timer", pickUpTime.seconds());
            telemetry.addData("TransferMacro", transferStep);
            telemetry.addData("TransferMacro Timer", transferTime.seconds());
            telemetry.addData("Arm Macro", armMacro);
            telemetry.addData("Intake Claw", intakeClawMacro);
            telemetry.addData("Deposit Claw", depositClawMacro);
            telemetry.update();


            // keep last gamepad in because its useful for simple button presses


        }



    }
    //Todo: MAJOR MACROS
    enum TransferSteps {
        READY, MOVE, TWICE, GRAB, LETGO, RETURN
    }
    // READY: Waits for the button press to start the transfer sequence
// MOVE: Moves intake claw to transfer position
    //TWICE: Moves deposit claw to transfer position
// GRAB: Closes the deposit claw to secure the object
// LET GO: Opens the intake claw to release the object
// RETURN: Resets all components to their default positions
    TransferSteps transferStep = TransferSteps.READY;

    public enum PickUpSteps {
        READY, MOVE, DECIDE, GRAB
    }
    public PickUpSteps pickUpStep = PickUpSteps.READY;

    public enum SpecimenSteps {
        READY, CHILL, FINAL
    }
    public SpecimenSteps specimenStep = SpecimenSteps.READY;




    //Todo: MINOR MACROS

    public enum ArmMacro {
        PASSIVE, SCORE, SPECIMENSTART, SPECIMENFINISH
    }
    public ArmMacro armMacro = ArmMacro.PASSIVE;

    enum IntakeClawMacro {
        OPENED, CLOSED
    }

    IntakeClawMacro intakeClawMacro = IntakeClawMacro.OPENED;
    enum DepositClawMacro {
        OPENED, CLOSED
    }
    DepositClawMacro depositClawMacro = DepositClawMacro.CLOSED;


}