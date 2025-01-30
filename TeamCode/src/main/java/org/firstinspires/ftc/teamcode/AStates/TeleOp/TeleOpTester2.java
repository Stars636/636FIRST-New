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
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.highSpecimen;
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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;

@Config
@Disabled
@TeleOp
public class TeleOpTester2 extends LinearOpMode {


    //slides r
Calvin calvin;

    boolean changedY = false;

    @Override
    public void runOpMode() throws InterruptedException {

        calvin = new Calvin(hardwareMap);

        waitForStart();
        telemetry.addLine("Best Wishes!");
        telemetry.update();



        while (opModeIsActive()) {

            if(gamepad2.y && !changedY) {
                if(calvin.depositClaw.getPosition() == depositClawClosed) {
                    calvin.depositClaw.setPosition(depositClawOpen);
                    changedY = true;
                } else if (calvin.intakeClaw.getPosition() == depositClawOpen){
                    calvin.depositClaw.setPosition(depositClawClosed);
                    changedY = true;
                } else {
                    calvin.depositClaw.setPosition(depositClawClosed);
                    changedY = true;
                }
            } else if (!gamepad2.y) {
                changedY = false;
            }


            telemetry.addData("gamepad a", gamepad2.a);

            telemetry.addData("lastgamepad a", changedY);
            telemetry.addData("arm pos", calvin.intakeArm.getPosition());

            telemetry.update();

        }

    }
}