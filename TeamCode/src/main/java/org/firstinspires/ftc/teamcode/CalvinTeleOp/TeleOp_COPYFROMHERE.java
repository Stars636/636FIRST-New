package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;

@TeleOp
public class TeleOp_COPYFROMHERE extends LinearOpMode {

    public boolean changedHundred = false;
    public boolean changedFifty = false;

    public boolean changedTwenty = false;

    public int initialMotorPosition = 0;

    public boolean changedTen = false;
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.checkHardwareInitialization(telemetry);

        waitForStart();




        calvin.moveVerticalSlidesTo(initialMotorPosition);


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            calvin.testIntital(gamepad1.a);
            precisionTest(gamepad1.x,gamepad1.y,gamepad1.b, gamepad1.right_stick_button, calvin);
        }

    }

    public void precisionTest(boolean button1, boolean button2, boolean button3, boolean button4, Calvin calvin) {
        if (button1 && !changedTen) {
            initialMotorPosition += 10;
            calvin.moveVerticalSlidesTo(initialMotorPosition);
            changedTen = true;
        } else if(!button1) {
            changedTen = false;
        }
        if (button2 && !changedTwenty) {
            initialMotorPosition += 20;
            calvin.moveVerticalSlidesTo(initialMotorPosition);
            changedTwenty = true;
        } else if(!button2) {
            changedTwenty = false;
        }
        if (button1 && !changedFifty) {
            initialMotorPosition += 50;
            calvin.moveVerticalSlidesTo(initialMotorPosition);
            changedFifty = true;
        } else if(!button1) {
            changedFifty = false;
        }
        if (button1 && !changedHundred) {
            initialMotorPosition += 100;
            calvin.moveVerticalSlidesTo(initialMotorPosition);
            changedFifty = true;
        } else if(!button1) {
            changedFifty = false;
        }
    }

}