
package org.firstinspires.ftc.teamcode.ANewEngland.Auto.Fraudarsh;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesInside;
import static org.firstinspires.ftc.teamcode.AStates.Bot.Calvin.hSlidesOutside;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.ANewEngland.Camera.SampleDetectionFinal;
import org.firstinspires.ftc.teamcode.AStates.Bot.Calvin;
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
                double currentAngle = calvin.intakeWrist.getPosition();
                if (blueDetect.getIsFound()) {
                    calvin.intakeWrist.setPosition(orientAngle/180);
                    return false;
                } else {
                    return false;
                }
            }
        }
        public Action TurningOfTheWrist2() {
            return new TurningOfTheWrist();
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {



    }

}


