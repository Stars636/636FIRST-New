package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinFinal;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Calvin {
    DcMotorEx leftFront, rightFront, leftBack, rightBack;
    DcMotorImplEx vSlidesLeft, vSlidesRight;

    DcMotorImplEx hangleft, hangRight;

    ServoImplEx intakeClaw, intakeWrist, intakeElbow, intakeArm;

    ServoImplEx depositClaw, depositElbow, depositWrist;

    ServoImplEx hSlidesLeft, hSlidesRight;

    public static int lowBucket;

    public static int highBucket;

    public static int hangStart; //note this is the verticalSlides

    public static int hangfinish; //add this to the current position of the hang motors

    public static double depositClawOpen = 0.17;
    public static double depositClawClosed = 0.30;


    public static double depositClawPassivePos = 0.65;

    public static double depositClawPassiveRot = 0.43;

    public static double depositClawTransferPos = 0.9;

    public static double depositClawTransferRot = 0.06;

    public static double depositClawScorePos = 0.4;

    public static double depositClawScoreRot = 1;

    public static double depositClawSpeciPos = 0;

    public static double depositClawSpeciRot = 1;

    public static double hSlidesInside = 0.99;
    public static double hSlidesOutside = 0.68;
    public static double intakeClawOpen;
    public static double intakeClawClosed;
    public static double intakeClawTransferPos;
    public static double intakeClawTransferRot;
    public static double intakeClawPassivePos;
    public static double intakeClawPassiveRot;

    //public static double hClaw



}
