package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal.Calvin;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp
public class TeleOpNew_COPYFROMHERE extends LinearOpMode {

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