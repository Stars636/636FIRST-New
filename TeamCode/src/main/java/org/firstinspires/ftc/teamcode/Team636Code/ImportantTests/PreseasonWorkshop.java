package org.firstinspires.ftc.teamcode.Team636Code.ImportantTests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
@Autonomous
public class PreseasonWorkshop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence ts = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                // ##########  WRITE YOUR CODE BELOW THIS LINE  ##########




                // ##########  WRITE YOUR CODE ABOVE THIS LINE  ##########
                .build();

        waitForStart();

        drive.followTrajectorySequence(ts);

    }

}
