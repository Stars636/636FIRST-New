package org.firstinspires.ftc.teamcode.ATests;

import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawClosed;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.intakeClawOpen;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

@TeleOp
public class TeleOpNewerr extends LinearOpMode {



    boolean changedX = false;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap);
        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {


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


        }

    }



}