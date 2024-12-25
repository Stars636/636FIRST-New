package org.firstinspires.ftc.teamcode.Zhang.TrueZhang;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
@Autonomous
public class Preseason extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence ts = drive.trajectorySequenceBuilder(new Pose2d(0,0,0))
                // ##########  WRITE YOUR CODE BELOW THIS LINE  ##########
                .forward(10)
                .back(10)



                // ##########  WRITE YOUR CODE ABOVE THIS LINE  ##########
                .build();

        waitForStart();

        drive.followTrajectorySequence(ts);

    }

}
