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
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorSpecimenPickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqPassivePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqSpecimenPosition;

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

    public static CalvinState BasketAutoStartPosition = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            clawClosed, shaqPassivePosition, rotatorPassive, null);
    public static CalvinState SpecimenAutoStartPosition = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            clawClosed, shaqSpecimenPosition, rotatorSpecimenPickup, null);
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
    public static CalvinState HslidesExtension = new CalvinState(null, null, horizontalSlidesOut, null,
            null, null, null, null);

    public static CalvinState FullRetraction = new CalvinState(null, null, horizontalSlidesIn, elbowInside,
            null, null, null, null);
    public static CalvinState ElbowRetraction = new CalvinState(null, null, null, elbowInside,
            null, null, null, null);
    public static CalvinState HslidesRetraction = new CalvinState(null, null, horizontalSlidesIn, null,
            null, null, null, null);

    //Claw
    public static CalvinState ClawOpen = new CalvinState(null, null, null, null,
            null, null, null, null);


    //Claw Mechanism in its entirety (i.e rotator + shaq)
    public static CalvinState ClawPassive = new CalvinState(null, null, null, null,
            clawOpen, shaqPassivePosition, rotatorPassive, null);
    public static CalvinState ClawGrab = new CalvinState(null, null, null, null,
            null, shaqPassivePosition, rotatorPassive, null);




}
