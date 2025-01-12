package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.slowingAllowed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.EjectFullPower;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.EjectPartialPower;
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

@TeleOp
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

            //Gunner Controls






            calvin.tick();

            gamepad1History.add(gamepad1);
            gamepad2History.add(gamepad2);
            // delete everything in gamepad histories with a 500 cycle delay
            if (gamepad1History.size() > 500) {
                gamepad1History.removeLast();
                gamepad2History.removeLast();
            }
            // keep last gamepad in because its useful for simple button presses
            lastGamepad1.copy(gamepad1);
            lastGamepad2.copy(gamepad2);


        }

    }



}