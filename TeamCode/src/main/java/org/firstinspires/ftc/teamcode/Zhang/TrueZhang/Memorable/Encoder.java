package org.firstinspires.ftc.teamcode.Zhang.TrueZhang.Memorable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class Encoder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Servo jamal = hardwareMap.get(Servo.class,"Jamal");

        DcMotor charlie = hardwareMap.get(DcMotor.class,"firstChildCharles");

        charlie.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        charlie.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        waitForStart();

        while (opModeIsActive()) {
            double worlds = 540; // constant we used to make sure the values or revolutions and angles make sense

            double position = charlie.getCurrentPosition();
            double revolutions = position/worlds; //takahiro is revolutions
            double angle = revolutions * 360;
            double angleNormalized = angle % 360;

            double diameter = 3.5; // In cm
            double circumference = Math.PI * diameter;
            double distance = circumference * revolutions;



            telemetry.addData("Encoder Position", position);
            telemetry.addData("Encoder Angle", angle);
            telemetry.addData("Encoder Normal Angle", angleNormalized);
            telemetry.addData("Encoder Revolution", revolutions);
            telemetry.addData("Distance Traveled", distance);
            /*

             */
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


            telemetry.update();
        }


    }
}

