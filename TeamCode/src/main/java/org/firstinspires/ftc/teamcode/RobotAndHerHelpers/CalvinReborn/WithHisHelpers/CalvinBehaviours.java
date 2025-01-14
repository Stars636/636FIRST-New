package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.RETURN;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.armInside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.armOutside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.armPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hSlidesIn;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hSlidesOut;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.rotatorDunk;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.rotatorPassivePos;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.rotatorSpecimenCapture;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.rotatorSpecimenScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.rotatorTransfer;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.shaqPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.shaqScore;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.shaqSpeciDeposit;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.shaqSpeciPickup;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.shaqTransfer;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.verticalSlidesHigh;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.verticalSlidesLow;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.verticalSlidesSpeciDeposit;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.verticalSlidesSpeciStart;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.wristPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.wristTransfer;

import com.acmerobotics.dashboard.config.Config;

//holds every calvin macro
//motors don't run to position, so this is daniel meant by only having one method
//that changes servo locations
//ours are kindy funky because we don't do stuff that complicated
//but macros will be rlly useful for specimen scoring
@Config
public class CalvinBehaviours {


    //NULL
    public static NewCalvinState NULL = new NewCalvinState(null, null, null, null,
            null, null, null, null);


    //Start Positions

    public static NewCalvinState BucketStart = new NewCalvinState(armPassive, wristPassive, hClawOpen, hSlidesIn,
            vClawClosed, shaqPassive, rotatorPassivePos, RETURN);
    public static NewCalvinState SpecimenStart = new NewCalvinState(armPassive, wristPassive, hClawOpen, hSlidesIn,
            vClawClosed, shaqPassive, rotatorPassivePos, RETURN);
    public static NewCalvinState TeleOpStart =  new NewCalvinState(armPassive, wristPassive, hClawOpen, hSlidesIn,
            vClawClosed, shaqPassive, rotatorPassivePos, RETURN);

    //Horizontal Behavior

    //Extendo in its entirety (i.e elbow plus horizontalSlides
    public static NewCalvinState FullExtension = new NewCalvinState(armOutside, null, null,
            hSlidesOut, null, null, null, null);
    public static NewCalvinState ArmExtension = new NewCalvinState(armOutside, null, null, null,
            null, null, null, null);
    public static NewCalvinState HSlidesExtension =   new NewCalvinState(null, null, null, hSlidesOut,
            null, null, null, null);


    public static NewCalvinState FullRetraction = new NewCalvinState(armInside, wristPassive, null,
            hSlidesOut, null, null, null, null);
    public static NewCalvinState ArmRetraction = new NewCalvinState(null, null, null, armInside,
            null, null, null, null);
    public static NewCalvinState HSlidesRetraction = new NewCalvinState(null, null, null, hSlidesIn,
            null, null, null, null);

    //Horizontal Claw
    public static NewCalvinState HClawOpen = new NewCalvinState(null, null, hClawOpen, null,
            null, null, null, null);
    public static NewCalvinState HClawClosed = new NewCalvinState(null, null, hClawClosed, null,
            null, null, null, null);

    //Vertical Claw

    public static NewCalvinState VClawOpen =  new NewCalvinState(null, null, null, null,
            vClawOpen, null, null, null);
    public static NewCalvinState VClawClosed = new NewCalvinState(null, null, null, null,
            vClawClosed, null, null, null);

    //Claw Mechanism in its entirety (i.e rotator + shaq) we shall name them "Arm
    //if there's an issue, make clawOpen null
    public static NewCalvinState ShaqPassive = new NewCalvinState(null, null, null, null,
            vClawOpen, shaqPassive, rotatorPassivePos, null);
    public static NewCalvinState ShaqTransfer = new NewCalvinState(null, null, null, null,
            vClawOpen, shaqTransfer, rotatorTransfer, null);

    public static NewCalvinState HClawTransfer = new NewCalvinState(armInside, wristTransfer, null, hSlidesIn,
            null, null, null, null);

    public static NewCalvinState ShaqBucketScore = new NewCalvinState(null, null, null, null,
            null, shaqScore, rotatorDunk, null);
    public static NewCalvinState SpecimenPickup = new NewCalvinState(null, null, null, null,
            null, shaqSpeciPickup, rotatorSpecimenCapture, null);
    public static NewCalvinState SpecimenDeposit = new NewCalvinState(null, null, null, null,
            null, shaqSpeciDeposit, rotatorSpecimenScore, null);


    //Vertical Slides

    public static NewCalvinState Requiem = new NewCalvinState(null, null, null, null,
            null, null, null, RETURN);
    public static NewCalvinState LowBucket = new NewCalvinState(null, null, null, null,
            null, null, null, verticalSlidesLow);

    public static NewCalvinState HighBucket = new NewCalvinState(null, null, null, null,
            null, null, null, verticalSlidesHigh);

    public static NewCalvinState VSlidesSpecimenStart = new NewCalvinState(null, null, null, null,
            null, null, null, verticalSlidesSpeciStart);

    public static NewCalvinState VSlidesSpecimenDeposit = new NewCalvinState(null, null, null, null,
            null, null, null, verticalSlidesSpeciDeposit);

    //Specimen Macro Step





}
