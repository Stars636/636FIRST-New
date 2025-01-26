package org.firstinspires.ftc.teamcode.ATests.SideQuests;
import com.arcrobotics.ftclib.controller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Motor extends LinearOpMode {
    //PIDFController pidf = new PIDFController(kP, kI, kD, kF);
    double kP;
    double kI;
    double kD;
    double kF;
    PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kP, kI, kD, kF);
    @Override
    public void runOpMode(){

    }
}
