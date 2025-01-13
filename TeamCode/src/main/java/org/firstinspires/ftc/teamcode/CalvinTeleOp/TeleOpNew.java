package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.clawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.clawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.elbowFullOutside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.elbowInside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.elbowOutside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.horizontalSlidesIn;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.horizontalSlidesOut;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqScorePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqSpecimenDeposit;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqSpecimenPickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.slowingAllowed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmBucketScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmIntakeGrab;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ArmSpecimenPickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.EjectFullPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.EjectPartialPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ElbowExtension;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.ElbowRetraction;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.FullExtension;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.FullRetraction;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.HSlidesExtension;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.HSlidesRetraction;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.IntakeFullPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.IntakeNoPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.IntakePartialPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.TeleopStartPosition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNate;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp (name = "RUN THIS ONE ")
public class TeleOpNew extends LinearOpMode {

    Gamepad lastGamepad1 = new Gamepad(), lastGamepad2 = new Gamepad();
    Deque<Gamepad> gamepad1History = new LinkedList<>(), gamepad2History = new LinkedList<>();




    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        BigNate calvin = new BigNate(hardwareMap);


        waitForStart();

        calvin.runMacro(TeleopStartPosition);

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //if something doesn't work start here

            //so pressing start b doesn't make b do something etc
            if (gamepad1.start || gamepad2.start) return;

            // DRIVER CONTROLS
            //Currently driving and intake

            //if slowing is allowed, right trigger will slow down for more precise turning for specimen pickup
            //if it should be a specific amount rather than measure how hard you press right trigger, then change this
            // to like double rTrigger = (slowingAllowed) ? "slowedAmount" : 1;
            //and make an if statement for the drive controller
            double rTrigger = (slowingAllowed) ? 1 - gamepad1.right_trigger : 1;
            calvin.driveController.motorDriveXY(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            //Intake and Eject Controls
            //Currently the triggers are the slowing buttons haha
            if (gamepad1.a) {
                if (gamepad1.left_trigger != 0){
                    calvin.runMacro(IntakePartialPower);
                } else {
                    calvin.runMacro(IntakeFullPower);
                }
            } else if (gamepad1.b) {
                if (gamepad1.left_trigger != 0){
                    calvin.runMacro(EjectPartialPower);
                } else {
                    calvin.runMacro(EjectFullPower);
                }
            } else {
                calvin.runMacro(IntakeNoPower);
            }

            //GUNNER CONTROLS
            // Gunner Controls
            //If something doesn't work first thiung you should do is
            //remove clawOpen from all the shaq + clawRotator i.e arm macros and
            //replace with null
            if (gamepad2.dpad_up) {
                calvin.runMacro(ArmPassive);
            }

            if (gamepad2.b && !lastGamepad2.b) {
                calvin.servoController.setElbowPos(calvin.servoController.elbowPos == elbowFullOutside);
            }

            if (gamepad2.x && !lastGamepad2.x) {
                if (calvin.servoController.shaqPos == shaqSpecimenPickup) {
                    calvin.runMacro(ArmPassive);
                } else {
                    calvin.runMacro(ArmSpecimenPickup);
                }
            }

            if (gamepad2.right_bumper && !lastGamepad2.right_bumper) {
                if (calvin.servoController.shaqPos == shaqScorePosition) {
                    calvin.runMacro(ArmPassive);
                } else {
                    calvin.runMacro(ArmBucketScore);
                }
            }
            if (gamepad2.left_bumper && !lastGamepad2.left_bumper) {
                if (calvin.servoController.shaqPos == shaqScorePosition) {
                    calvin.runMacro(ArmPassive);
                } else {
                    calvin.runMacro(ArmIntakeGrab);
                }
            }


            if (gamepad2.left_trigger != 0 && lastGamepad2.left_trigger != 0) {
                if (calvin.servoController.HSlidesPos == horizontalSlidesIn) {
                    calvin.runMacro(FullExtension);
                } else if (calvin.servoController.HSlidesPos == horizontalSlidesOut) {
                    calvin.runMacro(FullRetraction);
                } else {
                    telemetry.addLine("??");
                }
            }

            if (gamepad2.right_trigger != 0 && lastGamepad2.right_trigger == 0) {
                calvin.servoController.setHSlidesPos(calvin.servoController.HSlidesPos == horizontalSlidesOut);
            }

            if (gamepad2.y && !lastGamepad2.y) {
                calvin.servoController.setClaw(calvin.servoController.clawPos == clawClosed);
            }

            //this is a really bad fix for the vertical slides
            //if we want to make buttons, that might be better
            calvin.tick(calvin.vSlidesLeft.getCurrentPosition(), gamepad2.left_stick_y);

            gamepad1History.add(gamepad1);
            gamepad2History.add(gamepad2);
            // delete everything in gamepad histories with a 500 cycle delay
            if (gamepad1History.size() > 500) {
                gamepad1History.removeLast();
                gamepad2History.removeLast();
            }
            telemetry.addData("Gamepad 1",  gamepad1History.getFirst());
            telemetry.addData("Gamepad 1 Last", lastGamepad1.toString());
            telemetry.addData("Gamepad 2",  gamepad2History.getFirst());
            telemetry.addData("Gamepad 1",  lastGamepad2.toString());

            telemetry.addData("V Slides: Gamepad Left Stick Y", gamepad2.left_stick_y);
            telemetry.update();

            // keep last gamepad in because its useful for simple button presses
            lastGamepad1.copy(gamepad1);
            lastGamepad2.copy(gamepad2);
        }

    }



}