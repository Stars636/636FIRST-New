package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@Autonomous
public class JoesAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

        ElapsedTime runtime = new ElapsedTime();
        CyDogsChassis.Direction parkingSpot = CyDogsChassis.Direction.LEFT;
        CyDogsChassis.Direction drivePath = CyDogsChassis.Direction.CENTER;

        CyDogsSparky mySparky = new CyDogsSparky(this);
        mySparky.initializeSpikeCam();
        mySparky.initializeDevices();
        mySparky.initializePositions();

     //   parkingSpot = mySparky.askParkingSpot();
     //   drivePath = mySparky.askDrivePath();

        telemetry.addData("SpikeValue", mySparky.spikeCam.spikeLocation);
        telemetry.addData("ParkingSpot", parkingSpot.toString());
        telemetry.addData("DrivePath", drivePath.toString());
        telemetry.update();

        // Wait for the start button to be pressed on the driver station
        waitForStart();
        //mySparky.Strafe( 200, 0.5, 500);
        //mySparky.MoveStraight(100,.5,500);

        if(opModeIsActive()){
        //    mySparky.MoveStraight(-635,.5,500);
        //    mySparky.Rotate(-90,.5,500);
        //    mySparky.dropPurplePixel();
        //    mySparky.returnLiftForDriving();
        //    mySparky.MoveStraight(200,.5,500);
            mySparky.scoreFromDrivingPositionAndReturn(CyDogsSparky.ArmLow);

        }
        sleep(5000);
    }
    }

