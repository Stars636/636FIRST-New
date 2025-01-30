package org.firstinspires.ftc.teamcode.ZTrashCan;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class Claw extends LinearOpMode {

    Servo Jamal;

    @Override
    public void runOpMode() throws InterruptedException {

        //Jamal = hardwareMap.get(Servo.class,"Ethan");
        Jamal = hardwareMap.get(Servo.class,"Servo1");

        double position = 0.0;



        waitForStart();
        boolean changeda = false;
        boolean changedb = false;

        while (opModeIsActive()) {



            if (gamepad1.a && !changeda) {
                position = 0.0;
                changeda = true;


            } else if (!gamepad1.a) {
                changeda = false;
            }
            if (gamepad1.b && !changedb) {
                position = 0.28;
                changedb = true;
            } else if(!gamepad1.b) {
                changedb = false;
            }
            Jamal.setPosition(position);

            telemetry.addData("Servo Position", Jamal.getPosition());
            telemetry.addData("Changed? A", changeda);
            telemetry.addData("Changed? B", changedb);
            telemetry.update();

            idle();
            /*


            if(gamepad1.a) {
                int desiredPosition = 540; // The position (in ticks) that you want the motor to move to
                charlie.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
                charlie.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                charlie.setPower(0.5);
            }
            if(gamepad1.b) {
                int desiredPosition = 0; // The position (in ticks) that you want the motor to move to
                charlie.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
                charlie.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                charlie.setPower(0.5);
            }
              */


        }


    }
}
