package org.firstinspires.ftc.teamcode.CalvinTeleOp.Deprecated;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.QualsCalvin.OGCalvin;
@Disabled
@Config
@TeleOp
public class CalvinPositionTester extends LinearOpMode {
    DcMotorImplEx SlidesLeft, SlidesRight;


    
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        OGCalvin calvin = new OGCalvin(hardwareMap, telemetry);
        waitForStart();
        SlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        SlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        SlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        SlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //calvin.checkHardwareInitialization(telemetry);
        //calvin.initialPositions();

        //Initial!!
        //we will create macros in the future, to remove room for error


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            calvin.initialPositions();
            

        }

    }

}