package org.firstinspires.ftc.teamcode.CalvinTeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Calvin;

@Config
@TeleOp
public class ValueTester extends LinearOpMode {
    public static double clawOpenPositionTest;
    public static double clawClosedPositionTest;


    public static double clawPassivePositionTest;

    public static double clawPassiveRotationTest;

    public static double clawRetrievePositionTest;

    public static double clawPickUpRotationTest;

    public static double clawScorePositionTest;

    public static double clawScoreRotationTest;

    public static double clawHangRotationTest;

    public static double elbowInsidePositionTest;
    public static double elbowOutsidePositionTest;

    public static double intakePassiveRotationTest;

    public static double intakeActiveRotationTest;

    public static double intakePickUpRotationTest;
    public static int verticalSlideHighScoringPositionLimitTest; //kindly note that gunner will use joystick

    public static double horizontalSlidesInitialPositionTest;

    public static double horizontalSlidesExtendedPositionTest;


    public static double specimenPickupPositionTest;
    public static int specimenStartPickupVerticalSlidesTest;

    public static double specimenClawRotationTest;
    public static int specimenFinishPickupVerticalSlidesTest;

    public static int specimenFinishDepositVerticalSlidesTest;

    public static int tester = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime et = new ElapsedTime();

        Calvin calvin = new Calvin(hardwareMap, telemetry);

        calvin.checkHardwareInitialization(telemetry);


        waitForStart();

        telemetry.addLine("Best Wishes.");
        telemetry.update();

        while (opModeIsActive()) {

            switch (tester) {
                case 0:
                    break;
                case 1:
                    //test clawOpenPosition
                    calvin.claw.setPosition(clawOpenPositionTest);
                    break;
                case 2:
                    //test clawClosedPosition
                    calvin.claw.setPosition(clawClosedPositionTest);
                    break;
                case 3:
                    //test clawPassivePosition
                    calvin.shaq.setPosition(clawPassivePositionTest);
                    break;
                case 4:
                    //test clawPassiveRotation
                    calvin.clawRotator.setPosition(clawPassiveRotationTest);
                    break;
                case 5:
                    //test clawRetrievePosition
                    calvin.shaq.setPosition(clawRetrievePositionTest);
                    break;
                case 6:
                    //test clawPickUpRotation
                    calvin.clawRotator.setPosition(clawPickUpRotationTest);
                    break;
                case 7:
                    // Test clawScorePosition
                    calvin.shaq.setPosition(clawScorePositionTest);
                    break;
                case 8:
                    // Test clawScoreRotation
                    calvin.clawRotator.setPosition(clawScoreRotationTest);
                    break;
                case 9:
                    // Test clawHangRotation
                    calvin.clawRotator.setPosition(clawHangRotationTest);
                    break;
                case 10:
                    // Test elbowInsidePosition
                    calvin.elbowLeft.setPosition(elbowInsidePositionTest);
                    calvin.elbowRight.setPosition(elbowInsidePositionTest);
                    break;
                case 11:
                    // Test elbowOutsidePosition
                    calvin.elbowLeft.setPosition(elbowOutsidePositionTest);
                    calvin.elbowRight.setPosition(elbowOutsidePositionTest);
                    break;
                case 12:
                    // Test intakePassiveRotation
                    // calvin.elbowRight.setPosition(intakePassiveRotationTest);
                    break;
                case 13:
                    // Test intakeActiveRotation
                   // calvin.elbowRight.setPosition(intakeActiveRotationTest);
                    break;
                case 14:
                    // Test intakePickUpRotation
                   // calvin.elbowRight.setPosition(intakePickUpRotationTest);
                    break;
                case 15:
                    // Test verticalSlideHighScoringPositionLimit
                    calvin.verticalSlidesLeft.setTargetPosition(verticalSlideHighScoringPositionLimitTest);
                    calvin.verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    calvin.verticalSlidesRight.setTargetPosition(verticalSlideHighScoringPositionLimitTest);
                    calvin.verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;
                case 16:
                    // Test horizontalSlidesInitialPosition
                    calvin.horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionTest);
                    calvin.horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionTest);
                    break;
                case 17:
                    // Test horizontalSlidesExtendedPosition
                    calvin.horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPositionTest);
                    //  calvin.horizontalSlidesRight.setPosition(horizontalSlidesExtendedPositionTest);
                    break;
                case 18:
                    // Test specimenPickupPosition
                    calvin.shaq.setPosition(specimenPickupPositionTest);
                    break;
                case 19:
                    // Test specimenStartPickupVerticalSlides
                    calvin.verticalSlidesLeft.setTargetPosition(specimenStartPickupVerticalSlidesTest);
                    calvin.verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    calvin.verticalSlidesRight.setTargetPosition(specimenStartPickupVerticalSlidesTest);
                    calvin.verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;
                case 20:
                    // Test specimenClawRotation
                    calvin.clawRotator.setPosition(specimenClawRotationTest);
                    break;
                case 21:
                    // Test specimenFinishPickupVerticalSlides
                    calvin.verticalSlidesLeft.setTargetPosition(specimenFinishPickupVerticalSlidesTest);
                    calvin.verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    calvin.verticalSlidesRight.setTargetPosition(specimenFinishPickupVerticalSlidesTest);
                    calvin.verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;
                case 22:
                    // Test specimenFinishDepositVerticalSlides
                    calvin.verticalSlidesLeft.setTargetPosition(specimenFinishDepositVerticalSlidesTest);
                    calvin.verticalSlidesLeft.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    calvin.verticalSlidesRight.setTargetPosition(specimenFinishDepositVerticalSlidesTest);
                    calvin.verticalSlidesRight.setPower(0.5); // Tells the motor that the position it should go to is desiredPosition
                    calvin.verticalSlidesRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;
                case 23:
                    //test intake
                    calvin.IntakeLeft.setPower(1);
                    calvin.IntakeRight.setPower(-1);
                    break;
                case 24:
                    //test eject
                    calvin.IntakeLeft.setPower(-1);
                    calvin.IntakeRight.setPower(1);
                default:
                    telemetry.addData("Tester", "Invalid value");
                    telemetry.update();
                    tester = 0;
                    break;
            }





        }

    }

} //Test horizontal slides initial position
                  //  calvin.horizontalSlidesLeft.setPosition(horizontalSlidesInitialPositionTest);
                  //  calvin.horizontalSlidesRight.setPosition(horizontalSlidesInitialPositionTest);
//test horizontal slides extended position
                //    calvin.horizontalSlidesLeft.setPosition(horizontalSlidesExtendedPositionTest);
                  //  calvin.horizontalSlidesRight.setPosition(horizontalSlidesExtendedPositionTest);
////test continuous intake
//                    calvin.continuousIntakeLeft.setPower(1);
//                    calvin.continuousIntakeRight.setPower(-1);
////test ejection
//                    calvin.continuousIntakeLeft.setPower(-1);
//                    calvin.continuousIntakeRight.setPower(1);