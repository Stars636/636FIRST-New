package org.firstinspires.ftc.teamcode.AStates.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
@Config
@TeleOp
public class PIDController extends LinearOpMode {



    boolean changedX = false;
    Calvin calvin;
    public static double target = 2000;
    public  static double Kp = 0.05, Ki = 0.000008, Kd = 0.00007;
    public static double integralSum = 0;
    public  static double lastError = 0;

    public static boolean up = true;


    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap);
        waitForStart();


        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {
            //https://www.ctrlaltftc.com/the-pid-controller
            //Tune the slides ki, kp, and kd values here, the autos should import them
            //this needs to be always running. So when you implement this, make it a parallel action
            //i think

            double encoderPosition = calvin.vSlidesLeft.getCurrentPosition();

            double error = target - encoderPosition;

            double derivative = (error - lastError) / timer.seconds();

            integralSum = integralSum + (error * timer.seconds());

            double power = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

            calvin.vSlidesLeft.setPower(power);
            calvin.vSlidesRight.setPower(power);

            timer.reset();

            telemetry.addData("position", calvin.vSlidesLeft.getCurrentPosition());
            telemetry.addData("power", calvin.vSlidesLeft.getPower());
            telemetry.addData("timer", timer);
            telemetry.addData("error", error);
            telemetry.addData("lasterror", lastError);
            telemetry.addData("integral sum", integralSum);
            telemetry.update();



        }

    }




}