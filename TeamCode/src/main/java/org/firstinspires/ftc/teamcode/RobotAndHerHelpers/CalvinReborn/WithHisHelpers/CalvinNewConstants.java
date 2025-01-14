package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers;

import com.acmerobotics.dashboard.config.Config;

@Config
public class CalvinNewConstants {

    //Current Constants

    //Intake Claw

    public static double hClawOpen;

    public static double hClawClosed;


    //Arm

     public static double armInside;
      public static double armOutside;
    public static double armPassive ;

      //Wrist

    public static double wristPassive;
    public static double wristIntake;

    public static double wristReady;

    public static double wristTransfer;

    //Horizontal Slides
    public static double hSlidesIn = 0.99;
      public static double hSlidesOut = 0.68;

    //Vertical Claw

     public static double vClawOpen = 0.17;
    public static double vClawClosed = 0.30;

    // Claw Rotator and Shaq must work together in a way for specimens.

    //Claw Rotator
       public static double rotatorDunk = 1;
      public static double rotatorSpecimenCapture= 1;

     public static double rotatorSpecimenScore = 1;
    public static double rotatorTransfer = 0.06;

    public static double rotatorPassivePos = 0.43;

    //Shaq

    public static double shaqPassive = 0.65;
     public static double shaqTransfer = 0.9;

    public static double shaqScore = 0.4;

      public static double shaqSpeciPickup = 0.01;

     public static double shaqSpeciDeposit = 0.01;

    //Vertical Slides
    public static int verticalSlidesHigh = 3000; //kindly note that gunner will use joystick
     public static int verticalSlidesLow = 1500;

      public static int verticalSlidesSpeciStart = 1700;

    public static int verticalSlidesSpeciDeposit = 300;
    public static double vSlidesScaling = 0.01; // erm.

    public static double vSlidesMaxi = 3000;
    public static double vSlidesMini = 0;
    public static double SLIDE_KP = 0.008;

     public static int RETURN = 0;


     //Specimen Macros
    public static enum SpecimenSteps {
        IDLE, START, MID, DEPOSIT
     }

     //Driving
    public static boolean slowing = true;

    //...

    //Miscellaneous
    public static int ENDING = 2000000000;




}
