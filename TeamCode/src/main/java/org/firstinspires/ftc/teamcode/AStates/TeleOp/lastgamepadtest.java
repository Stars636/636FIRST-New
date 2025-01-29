package org.firstinspires.ftc.teamcode.AStates.TeleOp;

import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

@TeleOp
public class lastgamepadtest extends LinearOpMode {


    Gamepad lastGamepad1 = new Gamepad(), lastGamepad2 = new Gamepad();
    boolean changedX = false;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap);
        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {


            if(gamepad2.x && !lastGamepad2.x) {
                if(calvin.intakeClaw.getPosition() == intakeClawClosed) {
                    calvin.intakeClaw.setPosition(intakeClawOpen);
                } else if (calvin.intakeClaw.getPosition() == intakeClawOpen){
                    calvin.intakeClaw.setPosition(intakeClawClosed);
                }
            }

            lastGamepad1.copy(gamepad1);
            lastGamepad2.copy(gamepad2);
        }

    }



}