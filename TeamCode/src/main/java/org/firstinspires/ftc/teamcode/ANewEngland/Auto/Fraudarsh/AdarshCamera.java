<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/ANewEngland/Auto/AdarshCamera.java
package org.firstinspires.ftc.teamcode.ANewEngland.Auto;
import static org.firstinspires.ftc.teamcode.AStates.Auto.Bucket_AutoTest3.FOREVER;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
=======
package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Fraudarsh;


import com.acmerobotics.dashboard.config.Config;
>>>>>>> a474a226cb8c327f5a6c0446aa49b9ac1dac9180:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/ANewEngland/Auto/Fraudarsh/AdarshCamera.java
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.SampleDetectionFinal;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
@Autonomous (name = "TheBestAuto", group = "NEAuto")
public class AdarshCamera extends LinearOpMode {
    private OpenCvWebcam detectionApparatus;
    private SampleDetectionFinal.BlueObjectPipeline blueDetect;
    public class extendo {
        Calvin calvin;
        public double yCoord;
        public extendo(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }
        public class extendoExtender implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                yCoord = blueDetect.getYOffset();
                double currentPosition = calvin.hSlidesRight.getPosition();
                if (blueDetect.getIsFound()) {
                    if (yCoord<-20 && hSlidesOutside<currentPosition&&hSlidesInside>currentPosition) {
                        calvin.hSlidesRight.setPosition(currentPosition - 0.02);
                        calvin.hSlidesLeft.setPosition(currentPosition - 0.02);
                        return true;
                    } else if (yCoord<20 && hSlidesOutside<currentPosition&&hSlidesInside>currentPosition) {
                        calvin.hSlidesRight.setPosition(currentPosition + 0.02);
                        calvin.hSlidesLeft.setPosition(currentPosition + 0.02);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        public Action extendoExtender2() { return new extendoExtender(); }
    }
    public class wrist {
        Calvin calvin;
        public double orientAngle;
        public wrist(HardwareMap hardwareMap) {
            calvin = new Calvin(hardwareMap);
        }
        public class TurningOfTheWrist implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                orientAngle = blueDetect.getDetectedAngle();
            }
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {



    }

}


