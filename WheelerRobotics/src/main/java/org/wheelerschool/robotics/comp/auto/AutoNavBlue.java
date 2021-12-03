package org.wheelerschool.robotics.comp.auto;

import static com.sun.tools.doclint.Entity.and;
import static com.sun.tools.doclint.Entity.ge;
import static com.sun.tools.doclint.Entity.pi;
import static com.sun.tools.doclint.Entity.tau;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.comp.Todo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.wheelerschool.robotics.comp.chassis.Meccanum;

@Autonomous
public class AutoNavBlue extends LinearOpMode {
    // for non next to caurousel
    Meccanum meccanum = new Meccanum();

    @Override
    public void runOpMode() throws InterruptedException {
        meccanum.init(hardwareMap);
        waitForStart();
        executeAutomaticSequence1();

    }
    private void executeAutomaticSequence1(){

        // auto for near carousel
        // gotta replace 0 with tested vals
            meccanum.closeServoFull();

            meccanum.motorDriveForwardEncoded(meccanum.NORMAL_SPEED, 100);

            meccanum.motorSpinRightEncoded(meccanum.NORMAL_SPEED, 0);

            meccanum.moveArmTime(meccanum.ARM_MAX_SPEED, 100);

            meccanum.openServoHalf();

            meccanum.motorSpinLeftEncoded(meccanum.NORMAL_SPEED, 90); // first spin + 90

            meccanum.motorDriveForwardEncoded(meccanum.NORMAL_SPEED, 100);


    }



}
