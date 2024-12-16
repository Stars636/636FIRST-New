package org.firstinspires.ftc.teamcode.Team636Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class Calvin_TeleOp extends LinearOpMode {
    CRServo continuousIntakeLeft;
    CRServo continuousIntakeRight;
    Servo claw;
    Servo clawLeft;
    Servo clawRight;

    Servo elbowServoLeft;
    Servo elbowServoRight;



    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        //Initializing all the motors. Do not change this unless we change the wiring
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");

        DcMotor horizontalSlidesLeft = hardwareMap.get(DcMotor.class,"horizontalSlidesLeft");
        DcMotor horizontalSlidesRight = hardwareMap.get(DcMotor.class,"horizontalSlidesRight");

        continuousIntakeLeft = hardwareMap.get(CRServo.class," continuousIntakeLeft"); //setPower
        continuousIntakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        claw = hardwareMap.get(Servo.class,"Claw");
        clawLeft = hardwareMap.get(Servo.class,"ClawLeft");
        clawRight = hardwareMap.get(Servo.class,"clawRight");
        elbowServoLeft = hardwareMap.get(Servo.class,"elbowServoLeft");
        elbowServoRight = hardwareMap.get(Servo.class,"elbowServoRight");


        waitForStart();

        while (opModeIsActive()) {


        }

    }

}