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
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeSpecimenLiftPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeSpecimenLiftRot;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeSpecimenPickupPos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeSpecimenPickupRot;
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
@TeleOp (group = "States", name = "RUN, TESTER")
public class CalvinTele extends LinearOpMode {
    //The robot.
    //"Isn't she lovely" - Stevie Wonder
    Calvin calvin;

    //Debouncers
    boolean changedX = false;
    boolean changedY = false;
    boolean changedA = false;
    boolean changedDpadUp = false;
    boolean changedRB = false;
    boolean changedLB = false;
    boolean changedLeft = false;
    boolean changedRight = false;

    boolean driverLB = false;

    //Transfer and the timers
    public ElapsedTime transferTime = new ElapsedTime();
    public static double transferPart1 = 0.3;
    public static double transferPart2 = 0.7;
    public static double transferPart3 = 0.1;
    public static double transferPart4 = 0.1;
    public static double transferPart5 = 0.7;

    //Pickup timers
    public ElapsedTime pickUpTime = new ElapsedTime();
    public static double pickUp1 = 0.1;//lower this over time LOL
    public static double pickUp2 = 0.1;

    //Specimen Timers
    public ElapsedTime specimenTime = new ElapsedTime();
    public static double specimenPart0 = 2;
    public static double specimenPart1 = 2;
    public static double specimenPart2 = 2;
    public static double specimenPart3 = 2;
    public static double specimenPart4 = 2;
    public static double specimenPart5 = 2;
    public static double specimenPart6 = 2;
    public static double specimenPart7 = 2;
    public static double specimenPart8 = 2;

    public static boolean isMajorMacroing = false;

    //Hopeful fix
    double intakeClawPos = 0.4;
    @Override
    public void runOpMode() throws InterruptedException {
        //Initializing the bot
        calvin = new Calvin(hardwareMap);
        waitForStart();

        //Initial Positions
        calvin.initialBucket();

        telemetry.addLine("Best Wishes!");
        telemetry.update();
        //Idk
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //Just checking
            intakeClawPos = calvin.intakeClaw.getPosition();

            //Todo: Transfer Macro
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
                            if (intakeClawMacro == IntakeClawMacro.CLOSED) {

                                calvin.intakeWrist.setPosition(intakeWristFlat);
                                calvin.intakeElbow.setPosition(intakeClawTransferRot);
                                calvin.intakeArm.setPosition(intakeClawTransferPos);
                                transferTime.reset();
                                transferStep = TransferSteps.TWICE;
                            } else if (intakeClawMacro == IntakeClawMacro.OPENED){
                                calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                                calvin.intakeElbow.setPosition(intakeClawTransferRot);
                                calvin.intakeArm.setPosition(intakeClawTransferPos);
                                transferTime.reset();
                                transferStep = TransferSteps.TWICE;
                            }
                        }
                    break;
                case TWICE:
                    if (transferTime.seconds() >= transferPart2){


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
                        calvin.depositClaw.setPosition(depositClawClosed);
                        depositClawMacro = DepositClawMacro.CLOSED;
                        transferTime.reset();
                        transferStep = TransferSteps.LETGO;
                    }
                    break;
                case LETGO:
                    if (transferTime.seconds() >= transferPart4) {

                        calvin.intakeClaw.setPosition(intakeClawOpen);
                        intakeClawMacro = IntakeClawMacro.OPENED;
                        transferTime.reset();
                        transferStep = TransferSteps.RETURN;
                    }

                    break;
                case RETURN:
                    if (transferTime.seconds() >= transferPart5) {
                        calvin.intakeWrist.setPosition(intakeWristFlat);
                        calvin.intakeElbow.setPosition(intakeClawPassiveRot);
                        calvin.intakeArm.setPosition(intakeClawPassivePos);


                        calvin.depositWrist.setPosition(depositClawPassiveRot);
                        calvin.depositArm.setPosition(depositClawPassivePos);
                        armMacro = ArmMacro.PASSIVE;
                        transferTime.reset();
                        isMajorMacroing = false;
                        transferStep = TransferSteps.READY;
                    }
                    break;
            }
            //Todo: Pickup Macro
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
            //Todo: HSlides Movement
            double changedBy = calvin.hSlidesLeft.getPosition();
            if (calvin.hSlidesLeft.getPosition() < hSlidesInside && calvin.hSlidesLeft.getPosition() >= hSlidesOutside) {
                if (!isMajorMacroing) {
                    if (gamepad2.left_trigger != 0) {
                        changedBy += (increment * gamepad2.left_trigger);
                        calvin.hSlidesLeft.setPosition(changedBy);
                        calvin.hSlidesRight.setPosition(changedBy);
                    }
                }
            }

            if (calvin.hSlidesLeft.getPosition() <= hSlidesInside && calvin.hSlidesLeft.getPosition() > hSlidesOutside) {
                if (gamepad2.right_trigger != 0) {
                    if (!isMajorMacroing) {
                        changedBy -= (increment * gamepad2.right_trigger);
                        calvin.hSlidesLeft.setPosition(changedBy);
                        calvin.hSlidesRight.setPosition(changedBy);
                    }
                    if (!isMajorMacroing) {
                        calvin.hover();
                    }
                    //Todo: software limits
                    if (!isMajorMacroing) {
                        calvin.depositWrist.setPosition(depositClawScoreRot);
                        calvin.depositArm.setPosition(depositClawScorePos);
                        armMacro = ArmMacro.PASSIVE;

                    }
                }
            }
            //Todo: HSlides Edge Cases
            if (calvin.hSlidesLeft.getPosition() > hSlidesInside) {
                calvin.hSlidesLeft.setPosition(hSlidesInside);
                calvin.hSlidesRight.setPosition(hSlidesInside);
            }
            if (calvin.hSlidesLeft.getPosition() < hSlidesOutside) {
                calvin.hSlidesLeft.setPosition(hSlidesOutside);
                calvin.hSlidesRight.setPosition(hSlidesOutside);
            }
            //Todo: software limits
            if (armMacro != ArmMacro.PASSIVE) {
                calvin.hSlidesLeft.setPosition(hSlidesInside);
                calvin.hSlidesRight.setPosition(hSlidesInside);
            }


            //Todo: wrist code
            // somewhat overcomplicated
            if(!isMajorMacroing) {
                if (gamepad2.dpad_left && !changedLeft) {
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
                if (gamepad2.dpad_right && !changedRight) {
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
            }

            //Todo: Intake Claw Macro
            switch (intakeClawMacro){
                case OPENED:
                    if (isMajorMacroing) {
                        if (gamepad2.x && !changedX) {
                            calvin.intakeClaw.setPosition(intakeClawClosed);
                            changedX = true;
                            intakeClawMacro = IntakeClawMacro.CLOSED;
                        } else if (!gamepad2.x) {
                            changedX = false;
                        }
                    }
                    break;
                case CLOSED:
                    if (isMajorMacroing) {
                        if (gamepad2.x && !changedX) {
                            calvin.intakeClaw.setPosition(intakeClawOpen);
                            changedX = true;
                            intakeClawMacro = IntakeClawMacro.OPENED;
                        } else if (!gamepad2.x) {
                            changedX = false;
                        }
                    }
                    break;
            }
            //Todo: Deposit Claw Macro
            switch (depositClawMacro){
                case OPENED:
                    if (!isMajorMacroing) {
                        if (gamepad2.y && !changedY) {
                            calvin.depositClaw.setPosition(depositClawClosed);
                            changedY = true;
                            depositClawMacro = DepositClawMacro.CLOSED;
                        } else if (!gamepad2.y) {
                            changedY = false;
                        }
                    }
                    break;
                case CLOSED:
                    if (isMajorMacroing) {
                        if (gamepad2.y && !changedY) {
                            calvin.depositClaw.setPosition(depositClawOpen);
                            changedY = true;
                            depositClawMacro = DepositClawMacro.OPENED;
                        } else if (!gamepad2.y) {
                            changedY = false;
                        }
                    }
                    break;
            }

            //Todo: Arm Macro
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
                        if (gamepad2.dpad_up && !changedDpadUp) {
                            calvin.depositWrist.setPosition(depositClawSpeciRotStart);
                            calvin.depositArm.setPosition(depositClawSpeciPosStart);
                            changedDpadUp = true;
                            armMacro = ArmMacro.SPECIMENSTART;
                        } else if (!gamepad2.dpad_up) {
                            changedDpadUp = false;
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
                        if (gamepad2.dpad_up && !changedDpadUp) {
                            calvin.depositWrist.setPosition(depositClawSpeciRotStart);
                            calvin.depositArm.setPosition(depositClawSpeciPosStart);
                            changedDpadUp = true;
                            armMacro = ArmMacro.SPECIMENSTART;
                        } else if (!gamepad2.dpad_up) {
                            changedDpadUp = false;
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
                        if (gamepad2.dpad_up && !changedDpadUp) {
                            calvin.depositWrist.setPosition(depositClawPassiveRot);
                            calvin.depositArm.setPosition(depositClawPassivePos);
                            changedDpadUp = true;
                            armMacro = ArmMacro.PASSIVE;
                        } else if (!gamepad2.dpad_up) {
                            changedDpadUp = false;
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
                        if (gamepad2.dpad_up && !changedDpadUp) {
                            calvin.depositWrist.setPosition(depositClawPassiveRot);
                            calvin.depositArm.setPosition(depositClawPassivePos);
                            changedDpadUp = true;
                            armMacro = ArmMacro.PASSIVE;
                        } else if (!gamepad2.dpad_up) {
                            changedDpadUp = false;
                        }
                    }
                    break;
                default:
                    armMacro = ArmMacro.PASSIVE;
                    break;

            }
            //Todo: gunner is out of buttons, I'm placing Adarsh specimen transfer idea in the drivers for now
            //Todo: the wrist can't actually rotate that far, as far as i know, but lets try
            switch (specMacro) {
                case READY:
                    if (gamepad1.left_bumper && !driverLB) {
                        isMajorMacroing = true;
                        calvin.intakeClaw.setPosition(intakeClawOpen);
                        intakeClawMacro = IntakeClawMacro.OPENED;
                        calvin.intakeWrist.setPosition(intakeWristFlat);
                        calvin.intakeElbow.setPosition(intakeClawPassiveRot);
                        calvin.intakeArm.setPosition(intakeClawPassivePos);
                        calvin.hSlidesLeft.setPosition(hSlidesInside);
                        calvin.hSlidesRight.setPosition(hSlidesInside);

                        calvin.depositClaw.setPosition(depositClawOpen);
                        depositClawMacro = DepositClawMacro.OPENED;
                        calvin.depositWrist.setPosition(depositClawTransferRot);
                        calvin.depositArm.setPosition(depositClawTransferPos);
                        armMacro = ArmMacro.PASSIVE;

                        driverLB = true;
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.HIT;
                    } else if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    break;
                case HIT:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart0) {
                        calvin.intakeElbow.setPosition(intakeSpecimenPickupRot);
                        calvin.intakeArm.setPosition(intakeSpecimenPickupPos);
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.GRAB;
                    }
                    break;
                case GRAB:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart1) {
                        calvin.intakeClaw.setPosition(intakeClawClosed);
                        intakeClawMacro = IntakeClawMacro.CLOSED;
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.LIFT;
                    }
                    break;
                case LIFT:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart2) {
                        calvin.intakeElbow.setPosition(intakeSpecimenLiftRot);
                        calvin.intakeArm.setPosition(intakeSpecimenLiftPos);
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.MOVE;
                    }
                    break;
                case MOVE:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart3) {
                        calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                        calvin.intakeElbow.setPosition(intakeClawTransferRot);
                        calvin.intakeArm.setPosition(intakeClawTransferPos);
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.TWICE;
                    }
                    break;
                case TWICE:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart4) {
                        calvin.depositClaw.setPosition(depositClawOpen);
                        depositClawMacro = DepositClawMacro.OPENED;
                        calvin.depositWrist.setPosition(depositClawTransferRot);
                        calvin.depositArm.setPosition(depositClawTransferPos);
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.TAKEIT;
                    }
                    break;
                case TAKEIT:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart5) {
                        calvin.depositClaw.setPosition(depositClawClosed);
                        depositClawMacro = DepositClawMacro.CLOSED;
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.LETGO;
                    }
                    break;
                case LETGO:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart6) {
                        calvin.intakeClaw.setPosition(intakeClawOpen);
                        intakeClawMacro = IntakeClawMacro.OPENED;
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.SCORE;
                    }
                    break;
                case SCORE:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart7) {
                        calvin.depositArm.setPosition(depositClawSpeciPosFinish);
                        calvin.depositWrist.setPosition(depositClawScoreRot);
                        specimenTime.reset();
                        specMacro = SpecimenTransferMacro.RETURN;
                    }
                    break;
                case RETURN:
                    if(!gamepad1.left_bumper) {
                        driverLB = false;
                    }
                    if(specimenTime.seconds() > specimenPart8) {
                        calvin.depositWrist.setPosition(depositClawSpeciRotFinish);
                        armMacro = ArmMacro.SPECIMENFINISH;
                        calvin.intakeWrist.setPosition(intakeWristFlat);
                        calvin.intakeElbow.setPosition(intakeClawPassiveRot);
                        calvin.intakeArm.setPosition(intakeClawPassivePos);
                        specimenTime.reset();
                        isMajorMacroing = false;
                        specMacro = SpecimenTransferMacro.READY;
                    }
                    break;

            }
            //READY, HIT, GRAB, LIFT, MOVE, TWICE, TAKEIT, LETGO, SCORE, RETURN


            //Todo: Specimen Score Button
            if(gamepad2.b) {
                calvin.depositWrist.setPosition(depositClawSpeciRotFinish);
                calvin.depositArm.setPosition(depositClawSpeciPosFinish);
                armMacro = ArmMacro.SPECIMENFINISH;
            }
            //Todo: Passive setter
            if (gamepad2.dpad_down) {
                if (!isMajorMacroing){
                    calvin.depositArm.setPosition(depositClawPassivePos);
                    calvin.depositWrist.setPosition(depositClawPassiveRot);
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                    calvin.intakeElbow.setPosition(intakeClawPassiveRot);
                    calvin.intakeArm.setPosition(intakeClawPassivePos);
                    armMacro = ArmMacro.PASSIVE;
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
            //thank you, zohra and david
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.right_trigger != 0) {
                double power = (1 - gamepad1.right_trigger);
                calvin.leftFront.setPower(Math.max(power, 0.45) * frontLeftPower);
                calvin.leftBack.setPower(Math.max(power, 0.45) * backLeftPower);
                calvin.rightFront.setPower(Math.max(power, 0.45)  * frontRightPower);
                calvin.rightBack.setPower(Math.max(power, 0.45) * backRightPower);
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
            telemetry.addData("SpecimenTranferMacro", specMacro);
            telemetry.addData("SpecimenMacro Timer", specimenTime.seconds());
            telemetry.addData("PickupMacro", pickUpStep);
            telemetry.addData("PickupMacro Timer", pickUpTime.seconds());
            telemetry.addData("TransferMacro", transferStep);
            telemetry.addData("TransferMacro Timer", transferTime.seconds());
            telemetry.addData("Arm Macro", armMacro);
            telemetry.addData("Intake Claw", intakeClawMacro);
            telemetry.addData("Deposit Claw", depositClawMacro);
            telemetry.update();

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

    public enum SpecimenTransferMacro {
        READY, HIT, GRAB, LIFT, MOVE, TWICE, TAKEIT, LETGO, SCORE, RETURN
    }
    //READY: Macro is ready
    //HIT: The intake claw moves to land on the walled specimen, the deposit claw moves to passive
    //GRAB: The intake claw closed on the specimen
    //LIFT: The intake claw lifts off the wall
    //MOVE: The intake claw moves to transfer position and rotates 180
    //TWICE: THe deposit claw moves to transfer position
    //TAKEIT: THe deposit claw closes
    //LETGO: The intake claw opens
    //SCORE: Deposit arm moves to scoring position
    //RETURN: EVverything else moves back to passive
    public SpecimenTransferMacro specMacro = SpecimenTransferMacro.READY;

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