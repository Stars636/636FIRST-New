package org.firstinspires.ftc.teamcode.ATests.Pringles;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PringleBot {
    public DcMotorImplEx motor;
    public ServoImplEx claw, servo;
    private ElapsedTime transferrr = new ElapsedTime();
    public static double transferrr1 = 3;
    public static double transferrr2 = 3;
    public static double transferrr3 = 3;

    public static double check10 = 0;
    public static double check20 = 0;
    public static double check30 = 0;
    public PringleBot(HardwareMap hardwareMap) {
        servo = hardwareMap.get(ServoImplEx.class, "servo");
        claw = hardwareMap.get(ServoImplEx.class, "claw");
        motor = hardwareMap.get(DcMotorImplEx.class, "motor");
    }


}
