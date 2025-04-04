package org.firstinspires.ftc.teamcode.ANewEngland.Auto.RayRay;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ANewEngland.Auto.RayRay.Old.CameraReactionYSuperFinal;


@Config
@Autonomous
@Disabled
public class TestFile extends LinearOpMode {

    CameraReactionFinal.OffsetFinal offsetFinal;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d pose = new Pose2d(0,0,0);
        offsetFinal = new CameraReactionFinal.OffsetFinal(hardwareMap, pose);

        waitForStart();

        while (opModeIsActive()) {


            Actions.runBlocking(
                    new ParallelAction(
                            offsetFinal.XOffsetBlue(),
                            offsetFinal.YOffsetBlue()
                    )
            );
        }






    }


}
//new Action() {
//                        @Override
//                        public boolean run(@NonNull TelemetryPacket packet) {
//                            offsetX.drive.setDrivePowers(new PoseVelocity2d(
//                                    new Vector2d(0, 0.3),
//                                    0
//                            ));
//                            sleep(2000);
//                            return false;
//                        }
//                    }


// if (yOffset > 10) {
//                    targetPos = Math.min(maxPosition, currentPos + step);
//                } else if (yOffset < -10) {
//                    targetPos = Math.max(minPosition, currentPos - step);
//                }
