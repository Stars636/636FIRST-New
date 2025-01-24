package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public class Calvin {
    DcMotorEx leftFront, rightFront, leftBack, rightBack;
    DcMotorImplEx vSlidesLeft, vSlidesRight;

    DcMotorImplEx hangleft, hangRight;

    ServoImplEx hClaw, hWrist, hElbow, hArm;

    ServoImplEx vClaw, vElbow, vWrist;

    ServoImplEx hSlidesLeft, hSlidesRight;

    public static int lowBucket;

    public static int highBucket;

    public static int hangStart; //note this is the verticalSlides

    public static int hangfinish; //add this to the current position of the hang motors

    public static double vClawOpen = 0.17;
    public static double vClawClosed = 0.30;


    public static double vClawPassivePos = 0.65;

    public static double vClawPassiveRot = 0.43;

    public static double vClawTransferPos = 0.9;

    public static double vClawTransferRot = 0.06;

    public static double vClawScorePos = 0.4;

    public static double vClawScoreRot = 1;

    public static double vClawSpeciPos = 0;

    public static double vClawSpeciRot = 1;

    public static double hSlidesInside = 0.99;
    public static double hSlidesOutside = 0.68;
    public static double hClawOpen;
    public static double hClawClosed;
    public static double hClawTransferPos;
    public static double hClawTransferRot;
    public static double hClawPassivePos;
    public static double hClawPassiveRot;

    //public static double hClaw



}
