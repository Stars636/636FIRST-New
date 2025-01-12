package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinMacros.IntakeFullPower;
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

            //so pressing start b doesn't make b do something
            if (gamepad2.start || gamepad1.start) return;

            if (gamepad1.a && !lastGamepad1.a) {
                calvin.runMacro(IntakeFullPower);
            }





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