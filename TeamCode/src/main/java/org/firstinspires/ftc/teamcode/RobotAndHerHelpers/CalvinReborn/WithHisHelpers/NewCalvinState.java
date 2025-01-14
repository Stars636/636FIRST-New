package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers;

public class NewCalvinState {
    //everyone asks
    //what is calvin
    //but nobody asks
    //how is calvin
    //however
    //our case is simply different.
    public Double extendoPosition, armPosition, wristPosition, HClawPosition, VClawPosition, shaqPosition, rotatorPosition;
    //Horizontal Claw and Vertical Claw.
    public Integer verticalSlidesPosition;



    public NewCalvinState(Double armPosition,
                          Double wristPosition,
                          Double HClawPosition,
                          Double extendoPosition,
                          Double VClawPosition,
                          Double shaqPosition,
                          Double rotatorPosition,
                          Integer verticalSlidesPosition) {
        this.armPosition = armPosition;
        this.wristPosition = wristPosition;
        this.HClawPosition = HClawPosition;
        this.extendoPosition = extendoPosition;
        this.VClawPosition = VClawPosition;
        this.shaqPosition = shaqPosition;
        this.rotatorPosition = rotatorPosition;
        this.verticalSlidesPosition = verticalSlidesPosition;
    }


}
