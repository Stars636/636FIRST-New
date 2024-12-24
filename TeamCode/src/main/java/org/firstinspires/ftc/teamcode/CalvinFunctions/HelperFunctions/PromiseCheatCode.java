package org.firstinspires.ftc.teamcode.CalvinFunctions.HelperFunctions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

import java.util.List;

public class PromiseCheatCode {
    //please write one for fun!!
    public DcMotor leftFront, leftBack, rightFront, rightBack;
    private static final String[] cheatCode = {"up", "up", "down", "down", "left", "right", "left", "right", "B", "A", "start"};
    private List<String> inputTracker = new ArrayList<>();
    private static int maxInput = cheatCode.length;


    public PromiseCheatCode(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;
    }
    public void processInputs(boolean left, boolean right, boolean up, boolean down, boolean a, boolean b, boolean x, boolean y, boolean rb, boolean lb, boolean r3, boolean  l3, boolean option, boolean start,  double rt, double lt, Telemetry telemetry) {
        if (left) registerInput("left", telemetry);
        if (right) registerInput("right", telemetry);
        if (up) registerInput("up", telemetry);
        if (down) registerInput("down", telemetry);
        if (a) registerInput("A", telemetry);
        if (b) registerInput("B", telemetry);
        if (x) registerInput("X", telemetry);
        if (y) registerInput("Y", telemetry);
        if (rb) registerInput("RB", telemetry);
        if (lb) registerInput("LB", telemetry);
        if (rt != 0) registerInput("RT", telemetry);
        if (lt != 0) registerInput("LT", telemetry);
        if (r3) registerInput("R3", telemetry);
        if (l3) registerInput("L3", telemetry);
        if (option) registerInput("option", telemetry);
        if (start) registerInput("start", telemetry);
    }

    private boolean activateCheatCode() {
        if (inputTracker.size() != cheatCode.length) {
            return false;
        }

        for (int i = 0; i < cheatCode.length; i++) {
            if (!inputTracker.get(i).equals(cheatCode[i])) {
                return false;
            }
        }
        return true;
    }

    public void letItRip(double power, double time) {
        ElapsedTime cheatTime = new ElapsedTime();

        if (cheatTime.seconds() < 5) {
            leftFront.setPower(power);
            leftBack.setPower(power);
            rightFront.setPower(-power);
            rightBack.setPower(-power);
        }

    }

    public void activateCheat(Telemetry telemetry){
       telemetry.addLine("Beyblade Beyblade let it rip");

       letItRip(1, 5);
    }

    private void registerInput(String button, Telemetry telemetry) {
        inputTracker.add(button);

        if (activateCheatCode()) {
            activateCheat(telemetry);
        }

        if (inputTracker.size() > maxInput) {
            inputTracker.remove(0); // Remove the oldest input
        }

    }

}
