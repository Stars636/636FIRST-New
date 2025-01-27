package org.firstinspires.ftc.teamcode.ATests.SideQuests;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;
@Config
public class MotorController {
    //PIDFController pidf = new PIDFController(kP, kI, kD, kF);
    public static double kP = 0.001;
    public static double kI = 0.0001;
    public static double kD = 0;
    //public static double kF;
    public double lastError = 0;
    public double integralSum = 0;
    public double derivative = 0;

    public boolean isTargeting = false;
    public ElapsedTime timer = new ElapsedTime();


    public double getPower(double reference, double state) {
        timer.reset();
        double error = reference - state;
        derivative = error - lastError/ timer.seconds();
        integralSum += (error * timer.seconds());
        double power = (kP * error) + (kI * integralSum) + (kD * derivative);
        lastError = error;
        timer.reset();
        return power;
    }
}
