package org.firstinspires.ftc.teamcode.AStates.TeleOp;

import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.depositClawPassivePos;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.floatPosition;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hangServoFinish;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.highBucket;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.increment;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristFlat;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristNormalLeft;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristNormalRight;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltLeft;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeWristTiltRight;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.isMacroing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp
public class TeleOpFinal extends LinearOpMode {

    Gamepad lastGamepad1 = new Gamepad(), lastGamepad2 = new Gamepad();
    Deque<Gamepad> gamepad1History = new LinkedList<>(), gamepad2History = new LinkedList<>();

//Tele

    Calvin calvin = new Calvin(hardwareMap);


    @Override
    public void runOpMode() throws InterruptedException {





        waitForStart();

        calvin.initialTele();


        telemetry.addLine("Best Wishes!");
        telemetry.update();

        if (isStopRequested()) return;

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
            //Todo: make backups?
            calvin.pickUp(gamepad2.left_bumper, lastGamepad2.left_bumper);
            calvin.transferEnd(gamepad2.right_bumper, lastGamepad2.right_bumper);
            //Natural horizontal slides

            //Todo: change horizontal slides so that driver can increment it
            //Todo: whenever extendo comes out the arm should be forward facing
            /*if(gamepad2.left_trigger != 0 && lastGamepad2.left_trigger == 0) {
                if(calvin.hSlidesLeft.getPosition() == hSlidesInside) {
                    calvin.hSlidesOut();
                } else if (calvin.intakeClaw.getPosition() == intakeClawOpen){
                    calvin.hSlidesIn();
                }
            }
            if(gamepad2.right_trigger != 0 && lastGamepad2.right_trigger == 0) {
                calvin.hover();
            }*/


            if (calvin.hSlidesLeft.getPosition() <= hSlidesInside && calvin.hSlidesLeft.getPosition() > hSlidesOutside) {
                if (gamepad2.left_trigger != 0 && lastGamepad2.left_trigger == 0) {
                    calvin.hSlidesLeft.setPosition(calvin.hSlidesLeft.getPosition() + increment*gamepad2.left_trigger);
                    calvin.hSlidesRight.setPosition(calvin.hSlidesRight.getPosition() + increment*gamepad2.left_trigger);
                } else if (gamepad2.right_trigger != 0 && lastGamepad2.right_trigger == 0) {
                    calvin.hSlidesLeft.setPosition(calvin.hSlidesLeft.getPosition() - increment*gamepad2.left_trigger);
                    calvin.hSlidesRight.setPosition(calvin.hSlidesRight.getPosition() + increment*gamepad2.left_trigger);
                    if (!isMacroing) {
                        calvin.hover();
                    }
                }

            }






            //Todo: wrist code extremely overcomplicated
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
                } else {
                    calvin.depositSpecimenStart(); //Ideally you won't need to...
                }
            }
            //Todo: Vertical Slide Improvements
            if (calvin.vSlidesRight.getCurrentPosition() < 0) {
                calvin.vSlidesLeft.setPower(Math.min(-gamepad2.left_stick_y, 0));  // Only allow positive power
                calvin.vSlidesRight.setPower(Math.min(-gamepad2.left_stick_y, 0));
            } else if (calvin.vSlidesRight.getCurrentPosition() > highBucket) {
                calvin.vSlidesLeft.setPower(Math.max(-gamepad2.left_stick_y, 0));  // Only allow negative power
                calvin.vSlidesRight.setPower(Math.max(-gamepad2.left_stick_y, 0));
            } else {
                calvin.vSlidesLeft.setPower(-gamepad2.left_stick_y); //Natural Movement
                calvin.vSlidesRight.setPower(-gamepad2.left_stick_y);
            }

            //Todo: DRIVER CONTROLS
            // - I.E. DRIVETRAIN and HANG

            //TODO: Driving

            double joystickX = -gamepad1.left_stick_x * 1.1; //apparently counteracts imperfect strafing
            double joystickY = gamepad1.left_stick_y;
            double joystickR = -gamepad1.right_stick_x;
            double brakes = 1 - gamepad1.right_trigger;

            if (gamepad1.right_trigger != 0) {
                calvin.rightFront.setPower((joystickY - joystickX - joystickR)/brakes);
                calvin.leftFront.setPower((joystickY + joystickX + joystickR)/brakes);
                calvin.rightBack.setPower((joystickY + joystickX - joystickR)/brakes);
                calvin.leftBack.setPower((joystickY - joystickX + joystickR)/brakes);
            } else {
                calvin.rightFront.setPower((joystickY - joystickX - joystickR));
                calvin.leftFront.setPower((joystickY + joystickX + joystickR));
                calvin.rightBack.setPower((joystickY + joystickX - joystickR));
                calvin.leftBack.setPower((joystickY - joystickX + joystickR));
            }

            //TODO: Hang














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