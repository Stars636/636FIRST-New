package org.firstinspires.ftc.teamcode.ANewEngland.Auto.RayRay;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.MightBeFunctional.SampleDetectionSupreme;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Config
@Autonomous
public class TestFile extends LinearOpMode {


    CameraReactionUltimate.Offset offset;


    @Override
    public void runOpMode() throws InterruptedException {

        offset = new CameraReactionUltimate.Offset(hardwareMap,new Pose2d(0,0,0));
        waitForStart();

        while (opModeIsActive()) {


            Actions.runBlocking(
                    new Action() {
                        @Override
                        public boolean run(@NonNull TelemetryPacket packet) {
                            offset.drive.setDrivePowers(new PoseVelocity2d(
                                    new Vector2d(0, 0.3),
                                    0
                            ));
                            sleep(2000);
                            return false;
                        }
                    }
            );
        }






    }


}


// if (yOffset > 10) {
//                    targetPos = Math.min(maxPosition, currentPos + step);
//                } else if (yOffset < -10) {
//                    targetPos = Math.max(minPosition, currentPos - step);
//                }
