package org.firstinspires.ftc.teamcode.CalvinTeleOp.Limelight3A;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.clawOpenPosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.specimenClawRotation;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.specimenPickupPosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin.specimenStartPickupVerticalSlides;

import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;

@TeleOp
public class LimelightTest0_1 extends LinearOpMode {




    public static boolean buttonPreviouslyPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        //calvin.initializeLimelight(telemetry);
        calvin.checkHardwareInitialization(telemetry);
        calvin.initialPositions();

        calvin.verticalSlidesLeft.setTargetPosition(specimenStartPickupVerticalSlides);
        calvin.verticalSlidesLeft.setPower(0.5);
        calvin.verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        calvin.verticalSlidesRight.setTargetPosition(specimenStartPickupVerticalSlides);
        calvin.verticalSlidesRight.setPower(0.5);
        calvin.verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        boolean aPress = false; //press A to rotate the claw to the back
        boolean xPress = false; //press X to return but to passive
        // where it would pick on the specimen
        boolean rbPress = false; //press rb to turn on the specimen tracking procedure


        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            //Okay so let's break what we want to do into steps.
            //First, we want the limelight to detect the specimen
            //Second, we want the limelight to return data on the distance from the specimen
            //Third, we want to add an offset based on our robot(i.e where is the claw)
            //Fourth, we want to get the robots current pose, and make it parallel to the specimen
            //Fifth, we want the bot to correct for error and movement, i.e. strafe to follow the specimen
            //Sixth, we want the bot to pickup the specimen
            //Seventh, quickly.
            LLStatus status = calvin.limelight.getStatus();
            telemetry.addData("Name", "%s",
                    status.getName());
            telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                    status.getTemp(), status.getCpu(),(int)status.getFps());
            telemetry.addData("Pipeline", "Index: %d, Type: %s",
                    status.getPipelineIndex(), status.getPipelineType());

            calvin.driveMotors(calvin.leftFront, calvin.rightFront, calvin.leftBack, calvin.rightBack, gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

            if(gamepad1.a && !aPress) {
                calvin.shaq.setPosition(specimenPickupPosition);
                calvin.clawRotator.setPosition(specimenClawRotation);
                calvin.claw.setPosition(clawOpenPosition);
                aPress = true;
            } else if(!gamepad1.a) {
                aPress = false;
            }
            if(gamepad1.x && !xPress) {
                calvin.passive();
                xPress = true;
            } else if(!gamepad1.x) {
                xPress = false;
            }

            calvin.activateClaw(gamepad1.b);

            if(gamepad1.right_bumper && !rbPress) {
                calvin.passive();
                rbPress = true;
            } else if(!gamepad1.right_bumper) {
                rbPress = false;
            }

            procedure(gamepad1.right_bumper, calvin);


        }

    }

    public void procedure(boolean buttonPressed, Calvin calvin) {
        ElapsedTime buttonTimer = new ElapsedTime();

        if (buttonPressed && !buttonPreviouslyPressed) {

            buttonTimer.reset();
            buttonPreviouslyPressed = true;
        } else if (!buttonPressed && buttonPreviouslyPressed) {

            buttonPreviouslyPressed = false;

            // is it a tap or a hold??
            double pressDuration = buttonTimer.milliseconds();
            if (pressDuration < 800) {

                calvin.findSpecimen(telemetry);
            } else {

                calvin.limelight.stop();
            }
        }
    }


}