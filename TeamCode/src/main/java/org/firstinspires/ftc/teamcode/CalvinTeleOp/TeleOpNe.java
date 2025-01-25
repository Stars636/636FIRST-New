package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.depositClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.depositClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.depositClawPassivePos;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.hangServoFinish;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.hangServoInitial;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeWristFlat;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeWristNormalLeft;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeWristNormalRight;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeWristTiltLeft;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin.intakeWristTiltRight;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp
public class TeleOpNe extends LinearOpMode {

    Gamepad lastGamepad1 = new Gamepad(), lastGamepad2 = new Gamepad();
    Deque<Gamepad> gamepad1History = new LinkedList<>(), gamepad2History = new LinkedList<>();

    private ElapsedTime transferTime = new ElapsedTime();
    public static double transfer1;
    public static double transfer2;
    public static double transfer3;
    private ElapsedTime specimenTime = new ElapsedTime();
    public static double specimen1;
    public static double specimen2;
    public static double specimen3;

    Calvin calvin = new Calvin(hardwareMap);


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();


        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //if something doesn't work start here
            if (gamepad2.start || gamepad1.start) return;
            //intake claw
            //TODO: change button
            if(gamepad2.x && !lastGamepad2.x) {
                if(calvin.intakeClaw.getPosition() == intakeClawClosed) {
                    calvin.intakeClawOpen();
                } else if (calvin.intakeClaw.getPosition() == intakeClawOpen){
                    calvin.intakeClawClosed();
                }
            }
            //Todo: check that transfer even works
            // -these are both state machines and require the most testing and scrutiny
            calvin.pickUp(gamepad2.left_bumper, lastGamepad2.left_bumper);
            calvin.transferEnd(gamepad2.right_bumper, lastGamepad2.right_bumper);
            //Natural horizontalslides

            if(gamepad2.left_trigger != 0 && lastGamepad2.left_trigger == 0) {
                if(calvin.hSlidesLeft.getPosition() == hSlidesInside) {
                    calvin.hSlidesOut();
                } else if (calvin.intakeClaw.getPosition() == intakeClawOpen){
                    calvin.hSlidesIn();
                }
            }
            if(gamepad2.right_trigger != 0 && lastGamepad2.right_trigger == 0) {
                calvin.hover();
            }
            //Todo: wrist code extremely overcomplicated
            // -change this to an increment, maybe using the triggers
            if(gamepad2.dpad_left && !lastGamepad2.dpad_left) {
                if (calvin.intakeWrist.getPosition() == intakeWristFlat) {
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltLeft) {
                    calvin.intakeWrist.setPosition(intakeWristNormalLeft);
                } else if (calvin.intakeWrist.getPosition() == intakeWristNormalRight) {
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltRight) {
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                }
            }
            if(gamepad2.dpad_right && !lastGamepad2.dpad_right) {
                if (calvin.intakeWrist.getPosition() == intakeWristFlat) {
                    calvin.intakeWrist.setPosition(intakeWristTiltRight);
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltLeft) {
                    calvin.intakeWrist.setPosition(intakeWristFlat);
                } else if (calvin.intakeWrist.getPosition() == intakeWristNormalLeft) {
                    calvin.intakeWrist.setPosition(intakeWristTiltLeft);
                } else if (calvin.intakeWrist.getPosition() == intakeWristTiltRight) {
                    calvin.intakeWrist.setPosition(intakeWristNormalRight);
                }
            }
            //Todo: deposit

            if(gamepad2.y && !lastGamepad2.y) {
                if (calvin.depositClaw.getPosition() == depositClawOpen) {
                    calvin.depositClawClosed();
                } else if (calvin.depositClaw.getPosition() == depositClawClosed) {
                    calvin.depositClawOpen();
                }
            }

            if(gamepad2.a && !lastGamepad2.a) {
                if (calvin.depositArm.getPosition() != depositClawPassivePos) {
                    calvin.depositPassive();
                } else {
                    calvin.depositScore();
                }
            }
            //Todo: make a better macro for this specimen stuff somehow
            // - also find a better button haha
            //specimen scoring
            calvin.scoreSpecimen(gamepad2.b, lastGamepad2.b);

            if (gamepad2.right_stick_button && !lastGamepad2.right_stick_button) {
                if (calvin.depositArm.getPosition() == depositClawPassivePos) {
                    calvin.depositPassive();
                } else if (calvin.hangServo.getPosition() == hangServoFinish) {
                    calvin.depositSpecimenStart(); //Ideally you won't need to...
                }
            }

            //Todo: DRIVER CONTROLS
            // - I.E. DRIVETRAIN and HANG














            gamepad1History.add(gamepad1);
            gamepad2History.add(gamepad2);
            // delete everything in gamepad histories with a 500 cycle delay
            if (gamepad1History.size() > 500) {
                gamepad1History.removeLast();
                gamepad2History.removeLast();
            }
            telemetry.addData("Gamepad 1",  gamepad1History.getFirst());
            telemetry.addData("Gamepad 2",  gamepad2History.getFirst());
            telemetry.update();

            // keep last gamepad in because its useful for simple button presses
            lastGamepad1.copy(gamepad1);
            lastGamepad2.copy(gamepad2);

        }

    }



}