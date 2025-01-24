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

    public static w

}
