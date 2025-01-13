package org.firstinspires.ftc.teamcode.AutoTests;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.clawClosedPosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.clawPassivePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.clawPassiveRotation;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.elbowInsidePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.horizontalSlidesInitialPosition;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "Qualifier Auto Basket 1", group = "A+ Qualifier")

public class BasketQualsAuto extends LinearOpMode {
    DcMotorImplEx SlidesLeft;
    DcMotorImplEx SlidesRight;

    public static double forwardTime = 800;

    public static double turnTime = 325;

    public static double backTime = 550;

    public static double farForwardTime = 500;

    public static double strafeTime = 50;

    public static double fraud = 150;


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();
        ElapsedTime intake = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        SlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        SlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");
        SlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlidesLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        SlidesRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        calvin.grabSample();
        //calvin.initialPositionsTwo();
        calvin.grabSample();

        calvin.horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
        calvin.horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
        calvin.claw.setPosition(clawClosedPosition);
        calvin.shaq.setPosition(clawPassivePosition);
        calvin.clawRotator.setPosition(clawPassiveRotation);
        calvin.elbowLeft.setPosition(elbowInsidePosition);
        calvin.elbowRight.setPosition(elbowInsidePosition);

        telemetry.addLine("Best Wishes.");
        telemetry.update();


        while (opModeIsActive()) {

            calvin.horizontalSlidesLeft.setPosition(horizontalSlidesInitialPosition);
            calvin.horizontalSlidesRight.setPosition(horizontalSlidesInitialPosition);
            calvin.claw.setPosition(clawClosedPosition);
            calvin.shaq.setPosition(clawPassivePosition);
            calvin.clawRotator.setPosition(clawPassiveRotation);
            calvin.elbowLeft.setPosition(elbowInsidePosition);
            calvin.elbowRight.setPosition(elbowInsidePosition);

            calvin.grabSample();
            telemetry.addData("time", et.seconds());
            telemetry.addData("claw", calvin.claw.getPosition());
            telemetry.update();

            calvin.grabSample();



            calvin.wait(0.5);

            et.reset();
            telemetry.update();
            while (et.milliseconds() < forwardTime) {
                double joystickX = 0;
                double joystickY = -0.3;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }


            calvin.wait(0.5);

            et.reset();
            telemetry.update();
            while (et.milliseconds() < turnTime) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = -0.21;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }

            sleep(1000);

            et.reset();
            telemetry.update();
            while (et.milliseconds() < backTime) {
                double joystickX = 0;
                double joystickY = 0.25;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }

            calvin.wait(0.5);

            moveVerticalSlidesTo(1500);

            calvin.wait(1.3);



            calvin.dunk();

            calvin.wait(0.5);

            calvin.dropSample();

            calvin.wait(0.5);

            calvin.passive();

            moveVerticalSlidesTo(0);

            calvin.wait(0.5);

            moveVerticalSlidesTo(0);



            et.reset();
            telemetry.update();
            while (et.milliseconds() < farForwardTime) {
                double joystickX = 0;
                double joystickY = -0.3;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);

            }


            et.reset();
            telemetry.update();

            while (et.milliseconds() < turnTime) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = 0.21;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }

            calvin.wait(0.5);
            et.reset();
            telemetry.update();

            while (et.milliseconds() < strafeTime) {
                double joystickX = -0.15;
                double joystickY = 0;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }

            //calvin.extend();
            intake.reset();
            telemetry.update();
            while (et.seconds() < 2.5) {
                calvin.intake();
            }
            et.reset();
            telemetry.update();
            while (et.milliseconds() < 100) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = 0.1;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }
            intake.reset();
            telemetry.update();
            while (et.seconds() < 2.5) {
                calvin.intake();
            }
            et.reset();
            telemetry.update();
            while (et.milliseconds() < 50) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = -0.1;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }
            intake.reset();
            telemetry.update();
            while (et.seconds() < 2.5) {
                //calvin.intake();
            }
            et.reset();
            telemetry.update();
            while (et.milliseconds() < 150) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = 0.1;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }
            intake.reset();
            telemetry.update();
            while (et.seconds() < 2.5) {
                calvin.intake();
            }
            et.reset();
            telemetry.update();
            while (et.milliseconds() < 150) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = -0.1;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }
            intake.reset();
            telemetry.update();
            while (et.seconds() < 2.5) {
                calvin.intake();
            }

            et.reset();
            telemetry.update();
            while (et.milliseconds() < farForwardTime) {
                double joystickX = 0;
                double joystickY = 0.3;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);

            }

            et.reset();
            telemetry.update();
            while (et.milliseconds() < strafeTime) {
                double joystickX = 0.15;
                double joystickY = 0;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }


            et.reset();
            telemetry.update();
            while (et.milliseconds() < turnTime) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = -0.21;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
            }

            calvin.wait(0.5);
            //calvin.grabSample();

            calvin.wait(0.5);

            calvin.grab();

            calvin.wait(0.5);

           // moveVerticalSlidesTo(1500);

            calvin.wait(1.3);



           //calvin.dunk();

            calvin.wait(0.5);

           // calvin.dropSample();

            calvin.wait(0.5);

            //calvin.passive();

            moveVerticalSlidesTo(0);


            et.reset();
            while (et.milliseconds() < 20000) {
                double joystickX = 0;
                double joystickY = 0;
                double joystickR = 0;
                calvin.rightFrontCalvin.setPower(joystickY - joystickX - joystickR);
                calvin.leftFrontCalvin.setPower(joystickY + joystickX + joystickR);
                calvin.rightBackCalvin.setPower(joystickY + joystickX - joystickR);
                calvin.leftBackCalvin.setPower(joystickY - joystickX + joystickR);
                moveVerticalSlidesTo(0);

                if(SlidesLeft.getCurrentPosition() < 0 || SlidesRight.getCurrentPosition() < 0) {
                    SlidesLeft.setPower(0);
                    SlidesRight.setPower(0);
                }
            }

            et.reset();
            telemetry.update();



            calvin.wait(30000);

        }


        }


private void moveVerticalSlidesTo(int targetPosition) {

    SlidesLeft.setTargetPosition(targetPosition);
    SlidesLeft.setPower(0.8);
    SlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    SlidesRight.setTargetPosition(targetPosition);
    SlidesRight.setPower(0.8);
    SlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
}

}
