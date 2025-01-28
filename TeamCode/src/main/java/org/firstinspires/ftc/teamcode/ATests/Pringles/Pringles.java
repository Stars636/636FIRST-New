package org.firstinspires.ftc.teamcode.ATests.Pringles;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Deque;
import java.util.LinkedList;

@TeleOp
public class Pringles extends LinearOpMode {

    Gamepad lastGamepad1 = new Gamepad(), lastGamepad2 = new Gamepad();
    Deque<Gamepad> gamepad1History = new LinkedList<>(), gamepad2History = new LinkedList<>();

    private ElapsedTime transferr = new ElapsedTime();
    public static double transferr1 = 3;
    public static double transferr2 = 3;
    public static double transferr3 = 3;

    public static double check1 = 0;
    public static double check2 = 0;
    public static double check3 = 0;
    DcMotorImplEx motor;
    ServoImplEx claw, servo;








    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();
        servo = hardwareMap.get(ServoImplEx.class, "servo");
        claw = hardwareMap.get(ServoImplEx.class, "claw");
        motor = hardwareMap.get(DcMotorImplEx.class, "motor");


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
            telemetry.addData("Check 1", check1);
            telemetry.addData("Check 2", check2);
            telemetry.addData("Check 3", check3);
            telemetry.addData("timer", transferr);
            telemetry.update();

            // keep last gamepad in because its useful for simple button presses
            lastGamepad1.copy(gamepad1);
            lastGamepad2.copy(gamepad2);

        }

    }
    //essentially this is to make sure that the enum problem is not from the bot class
    //and is from misunderstanding or bad code
    enum FraudStep {
        READY, FIRST, SECOND, THIRD, FINAL
    }
    FraudStep fraudStep = FraudStep.READY;

    public void fraud(boolean b, boolean lb){
        switch (fraudStep) {
            case READY:
                if(b && lb) {
                    check1 += 100;
                    fraudStep = FraudStep.FIRST;
                }
            case FIRST:
                transferr.reset();
                motor.setPower(1);
            case SECOND:
            case THIRD:
            case FINAL:

        }

    }


}