package org.firstinspires.ftc.teamcode.BackUps_TrashCan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class shaq extends LinearOpMode {

    ServoImplEx shaq;
    @Override
    public void runOpMode() throws InterruptedException {
        shaq = hardwareMap.get(ServoImplEx.class,"shaq");
        shaq.setPwmRange(new PwmControl.PwmRange(500,2500));

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.a) {
                shaq.setPosition(0);
            } else if(gamepad1.b) {
                shaq.setPosition(1);
            }
        }
    }
}
