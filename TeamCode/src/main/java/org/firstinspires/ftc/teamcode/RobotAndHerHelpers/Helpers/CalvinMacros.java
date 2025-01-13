package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.clawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.clawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.elbowFullOutside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.elbowInside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.horizontalSlidesIn;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.horizontalSlidesOut;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeEject;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeIntake;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeMax;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeMedium;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeOff;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeSlow;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorIntakePickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorSpecimenDeposit;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorSpecimenPickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqPassivePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqRetrievePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqScorePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqSpecimenDeposit;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqSpecimenPickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.ZERO;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.verticalSlidesHighScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.verticalSlidesLowScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.verticalSlidesSpecimenDeposit;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.verticalSlidesSpecimenStart;

import com.acmerobotics.dashboard.config.Config;

@Config
public class CalvinMacros {
    //order is intakeSpeed, intakeMultiplier, extendoPosition,
    // elbowPos, clawPosition,shaqPosition, rotatorPosition,
    // verticalSlidesPosition
    // public static CalvinState  = new CalvinState();

    //NULL
    public static CalvinState NULL = new CalvinState(null, null, null, null,
            null, null, null, null);


    //Start Positions

    public static CalvinState BucketAutoStartPosition = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            clawClosed, shaqPassivePosition, rotatorPassive, ZERO);
    public static CalvinState SpecimenAutoStartPosition = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            clawClosed, shaqSpecimenPickup, rotatorSpecimenPickup, ZERO);
    public static CalvinState TeleopStartPosition = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            clawClosed, shaqPassivePosition, rotatorPassive, null);

    //Active Intake
    public static CalvinState IntakeFullPower = new CalvinState(intakeIntake, intakeMax, null, null,
            null, null, null, null);
    public static CalvinState IntakePartialPower = new CalvinState(intakeIntake, intakeMedium, null, null,
            null, null, null, null);
    public static CalvinState IntakeLowPower = new CalvinState(intakeIntake, intakeSlow, null, null,
            null, null, null, null);

    public static CalvinState IntakeNoPower = new CalvinState(intakeOff, null, null, null,
            null, null, null, null);
    public static CalvinState EjectFullPower = new CalvinState(intakeEject, intakeMax, null, null,
            null, null, null, null);
    public static CalvinState EjectPartialPower = new CalvinState(intakeEject, intakeMedium, null, null,
            null, null, null, null);
    public static CalvinState EjectLowPower = new CalvinState(intakeEject, intakeSlow, null, null,
            null, null, null, null);

    //Extendo in its entirety (i.e elbow plus horizontalSlides
    public static CalvinState FullExtension = new CalvinState(null, null, horizontalSlidesOut, elbowFullOutside,
            null, null, null, null);
    public static CalvinState ElbowExtension = new CalvinState(null, null, null, elbowFullOutside,
            null, null, null, null);
    public static CalvinState HSlidesExtension = new CalvinState(null, null, horizontalSlidesOut, null,
            null, null, null, null);

    public static CalvinState FullRetraction = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            null, null, null, null);
    public static CalvinState ElbowRetraction = new CalvinState(null, null, null, elbowInside,
            null, null, null, null);
    public static CalvinState HSlidesRetraction = new CalvinState(null, null, horizontalSlidesIn, null,
            null, null, null, null);

    //Claw
    public static CalvinState ClawOpen = new CalvinState(null, null, null, null,
            clawOpen, null, null, null);
    public static CalvinState ClawClosed = new CalvinState(null, null, null, null,
            clawClosed, null, null, null);


    //Claw Mechanism in its entirety (i.e rotator + shaq) we shall name them "Arm
    //if there's an issue, make clawOpen null
    public static CalvinState ArmPassive = new CalvinState(null, null, null, null,
            clawOpen, shaqPassivePosition, rotatorPassive, null);
    public static CalvinState ArmIntakeGrab = new CalvinState(null, null, null, null,
            clawOpen, shaqRetrievePosition, rotatorIntakePickup, null);

    public static CalvinState ArmBucketScore = new CalvinState(null, null, null, null,
            null, shaqScorePosition, rotatorScore, null);
    public static CalvinState ArmSpecimenPickup = new CalvinState(null, null, null, null,
            null, shaqSpecimenPickup, rotatorSpecimenPickup, null);
    public static CalvinState ArmSpecimenDeposit = new CalvinState(null, null, null, null,
            null, shaqSpecimenDeposit, rotatorSpecimenDeposit, null);


    //Vertical Slides

    public static CalvinState ReturnToZero = new CalvinState(null, null, null, null,
            null, null, null, ZERO);
    public static CalvinState LowBucket = new CalvinState(null, null, null, null,
            null, null, null, verticalSlidesLowScore );

    public static CalvinState HighBucket = new CalvinState(null, null, null, null,
            null, null, null, verticalSlidesHighScore);

    public static CalvinState VSlidesSpecimenStart = new CalvinState(null, null, null, null,
            null, null, null, verticalSlidesSpecimenStart);

    public static CalvinState VSlidesSpecimenDeposit = new CalvinState(null, null, null, null,
            null, null, null, verticalSlidesSpecimenDeposit);

    //Specimen Macro Step





}