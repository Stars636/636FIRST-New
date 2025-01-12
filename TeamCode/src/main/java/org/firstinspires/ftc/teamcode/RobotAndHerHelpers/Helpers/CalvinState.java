package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers;

public class CalvinState {
    //everyone asks
    //what is calvin
    //but nobody asks
    //how is calvin
    //however
    //our case is simply different.
    public Double intakeSpeed, intakeMultiplier, extendoPosition, elbowPos, clawPosition, shaqPosition, rotatorPosition;
    public Integer verticalSlidesPosition;

    public CalvinState(Double intakeSpeed,
                       Double intakeMultiplier,
                       Double extendoPosition,
                       Double elbowPos,
                       Double clawPosition,
                       Double shaqPosition,
                       Double rotatorPosition,
                       Integer verticalSlidesPosition) {
        this.intakeMultiplier = intakeMultiplier;
        this.extendoPosition = extendoPosition;
        this.elbowPos = elbowPos;
        this.clawPosition = clawPosition;
        this.shaqPosition = shaqPosition;
        this.rotatorPosition = rotatorPosition;
        this.verticalSlidesPosition = verticalSlidesPosition;
    }


}
