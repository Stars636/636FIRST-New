package org.firstinspires.ftc.teamcode.competition.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.competition.utils.gamepads.GamepadManager;
import org.firstinspires.ftc.teamcode.competition.utils.interactions.items.StandardMotor;
import org.firstinspires.ftc.teamcode.competition.utils.io.InputSpace;
import org.firstinspires.ftc.teamcode.competition.utils.io.OutputSpace;
import org.firstinspires.ftc.teamcode.competition.utils.locations.ElevatorBottomLimitSwitchLocation;
import org.firstinspires.ftc.teamcode.competition.utils.locations.ElevatorLeftLiftMotorLocation;
import org.firstinspires.ftc.teamcode.competition.utils.locations.ElevatorRightLiftMotorLocation;
import org.firstinspires.ftc.teamcode.competition.utils.locations.IntakeLiftingServoLocation;
import org.firstinspires.ftc.teamcode.competition.utils.locations.IntakeSpinningMotorLocation;
import org.firstinspires.ftc.teamcode.competition.utils.locations.TankDrivetrainLocation;

@TeleOp(name="OpenHouseTeleOpTwo", group="linear")
public class OpenHouseTeleOpTwo extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        resetStartTime();
        InputSpace inputSpace = new InputSpace(hardwareMap);
        OutputSpace outputSpace = new OutputSpace(hardwareMap);
        GamepadManager gamepadManager = new GamepadManager(gamepad1, gamepad1, gamepad1, gamepad1, gamepad1, gamepad1);
        int intakePos;
        boolean intakeIsDown = false;
        while(opModeIsActive() && gamepadManager.shouldNotCallThePolice()) {

            // driving
            int left = (int) Range.clip(gamepadManager.functionOneGamepad().left_stick_y * 75, -75, 75);
            int right = (int) Range.clip(gamepadManager.functionOneGamepad().right_stick_y * 75, -75, 75);
            inputSpace.sendInputToTank(TankDrivetrainLocation.Action.SET_SPEED, -right, -left);

            // intake lifter
            if(gamepadManager.functionOneGamepad().dpad_right) {
                intakeIsDown = false;
            }else if(gamepadManager.functionOneGamepad().dpad_left) {
                intakeIsDown = true;
            }
            if(intakeIsDown) {
                intakePos = 50;
            }else{
                intakePos = 30;
            }
            inputSpace.sendInputToIntakeLifter(IntakeLiftingServoLocation.Action.SET_POSITION, intakePos);

            // intake
            int intakeGas = (int) Range.clip(gamepadManager.functionOneGamepad().left_trigger * 100, 0, 100);
            int intakeBrake = (int) Range.clip(gamepadManager.functionOneGamepad().right_trigger * 100, 0, 100);
            int intakeSpeed = Range.clip(intakeGas - intakeBrake, -100, 100);
            inputSpace.sendInputToIntakeSpinner(IntakeSpinningMotorLocation.Action.SET_SPEED, intakeSpeed);

            // elevator
            int elevatorInput = (gamepadManager.functionOneGamepad().right_bumper ? 0 : 1) + (gamepadManager.functionOneGamepad().left_bumper ? 0 : -1);
            int inputVal = ((StandardMotor) inputSpace.getElevatorLeftLift().getInternalInteractionSurface()).getDcMotor().getCurrentPosition() > -200 ? Range.clip(elevatorInput * 25, -25, 5) : Range.clip(elevatorInput * 25, -25, 25);
            if(inputVal < 0 || outputSpace.receiveOutputFromElevatorBottomLimitSwitch(ElevatorBottomLimitSwitchLocation.Values.PRESSED) == 0) {
                inputSpace.sendInputToElevatorLeftLift(ElevatorLeftLiftMotorLocation.Action.SET_SPEED, inputVal);
                inputSpace.sendInputToElevatorRightLift(ElevatorRightLiftMotorLocation.Action.SET_SPEED, -inputVal);
            }else{
                inputSpace.sendInputToElevatorLeftLift(ElevatorLeftLiftMotorLocation.Action.SET_SPEED, 0);
                inputSpace.sendInputToElevatorRightLift(ElevatorRightLiftMotorLocation.Action.SET_SPEED, 0);
            }
        }
    }

}
