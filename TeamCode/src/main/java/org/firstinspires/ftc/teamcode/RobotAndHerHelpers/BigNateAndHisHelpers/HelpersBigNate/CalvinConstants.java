package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate;

import com.acmerobotics.dashboard.config.Config;

@Config
public class CalvinConstants {

    //Current Constants

    //Intake
     public static double intakeIntake = 1;
    public static double intakeEject = -1;


     public static double intakeMax = 1;

    public static double intakeMedium = 0.7;

     public static double intakeSlow= 0.3;

    public static double intakeOff = 0;

     public static double wheelMultiplier = 1;
      public static double topMultiplier = 0.7;



    //Elbow

     public static double elbowInside = 0.07;

      public static double elbowOutside = 0.75;
    public static double elbowFullOutside = 0.85;

    //Horizontal Slides
    public static double horizontalSlidesIn = 0.99;
      public static double horizontalSlidesOut = 0.68;

    //Claw

     public static double clawOpen = 0.17;
    public static double clawClosed = 0.30;

    // Claw Rotator and Shaq must work together in a way for specimens.

    //Claw Rotator
       public static double rotatorScore = 1;
      public static double rotatorSpecimenPickup = 1;

     public static double rotatorSpecimenDeposit = 1;
    public static double rotatorIntakePickup = 0.06;

    public static double rotatorPassive = 0.43;

    //Shaq

    public static double shaqPassivePosition = 0.65;
     public static double shaqRetrievePosition = 0.9;

    public static double shaqScorePosition = 0.4;

      public static double shaqSpecimenPickup = 0.01;

     public static double shaqSpecimenDeposit = 0.01;



    //Vertical Slides
    public static int verticalSlidesHighScore = 3000; //kindly note that gunner will use joystick
     public static int verticalSlidesLowScore = 1500;

      public static int verticalSlidesSpecimenStart = 1700;

    public static int verticalSlidesSpecimenDeposit = 300;
    public static double vSlidesScaler = 0.01; // erm.

    public static double vSlidesMax = 3000;
    public static double vSlidesMin = 0;
    public static double SLIDES_KP = 0.008;

     public static int ZERO = 0;


     //Specimen Macros
    public static enum SpecimenSteps {
        IDLE, START, MID, DEPOSIT
     }

     //Driving
    public static boolean slowingAllowed = true;

    //...

    //Miscellaneous
    public static int END = 2000000000;




}
