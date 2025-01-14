package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn;

import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinBehaviours.TeleOpStart;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.ENDING;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.SLIDE_KP;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.armPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hSlidesIn;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.hSlidesOut;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.rotatorPassivePos;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.shaqPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.slowing;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vClawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vClawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vSlidesMaxi;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vSlidesMini;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.vSlidesScaling;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinNewConstants.wristPassive;
import static java.lang.Math.E;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.CalvinBehaviours;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.NewCalvinState;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.CalvinReborn.WithHisHelpers.PID;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Config
public class CalvinRevived {
    //Initializing
    public DcMotorImplEx vSlidesLeft, vSlidesRight;
    public DcMotorEx rightBackCalvin, rightFrontCalvin, leftBackCalvin, leftFrontCalvin;

    public ServoImplEx shaq, horizontalSlidesLeft, horizontalSlidesRight,
            wrist, arm, hClaw, clawRotator, vClaw;

    //These controllers allow us to group our servos drive motors and slide motors
    public MotorSlidesController slidesController = new MotorSlidesController();

    public ServosController servoController = new ServosController();

    public DriveTrainController driveController = new DriveTrainController();

    //I assume this telemetry is for dashboard??
    Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();

    //pinpointt
    public PinpointDrive drive;

    private VoltageSensor vs;
    public NewCalvinState macroState = null;

    //The macro code i lowkey still don't understand
    public boolean MACROING = false;
    public ElapsedTime macroTimer = new ElapsedTime();
    public int macroTimeout = ENDING;
    public int slidesTrigger = ENDING;
    public int slidesTriggerThreshold = 10;
    public boolean done = false;

    //The normal constuctor you're used to. 252 does it differently, so I hope this still works
    public CalvinRevived(HardwareMap hardwareMap) {
        //Initializing Vertical Slides
        vSlidesLeft = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesLeft");
        vSlidesRight = hardwareMap.get(DcMotorImplEx.class,"verticalSlidesRight");

        vSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vSlidesRight.setMode(DcMotorImplEx.RunMode.STOP_AND_RESET_ENCODER);
        vSlidesRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        vSlidesLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        vSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slidesController.start();

        //Initializing all the motors. Do not change this unless we change the wiring
        rightBackCalvin = hardwareMap.get(DcMotorEx.class,"rightBack");
        leftBackCalvin = hardwareMap.get(DcMotorEx.class,"leftBack");
        rightFrontCalvin = hardwareMap.get(DcMotorEx.class,"rightFront");
        leftFrontCalvin = hardwareMap.get(DcMotorEx.class,"leftFront");

        rightFrontCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBackCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFrontCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBackCalvin.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFrontCalvin.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackCalvin.setDirection(DcMotorSimple.Direction.REVERSE);

        //Initializing all the servos
        //Arm Related Servos
        //Horizontal Slides
        horizontalSlidesLeft = hardwareMap.get(ServoImplEx.class,"horizontalSlidesLeft");
        horizontalSlidesRight = hardwareMap.get(ServoImplEx.class,"horizontalSlidesRight");
        horizontalSlidesLeft.setDirection(Servo.Direction.FORWARD);
        horizontalSlidesRight.setDirection(Servo.Direction.REVERSE);
        //Arm
        arm = hardwareMap.get(ServoImplEx.class,"arm");
        wrist = hardwareMap.get(ServoImplEx.class,"wrist");
        hClaw = hardwareMap.get(ServoImplEx.class, "hClaw");


        //Claw Related Servos
        vClaw = hardwareMap.get(ServoImplEx.class,"vClaw");
        shaq = hardwareMap.get(ServoImplEx.class,"shaq");
        clawRotator = hardwareMap.get(ServoImplEx.class,"clawRotator");
        //Range Booster. I assume 500 is the "zero" and 2500 is the "one" so change as you need

        clawRotator.setPwmRange(new PwmControl.PwmRange(500,2500));
        shaq.setPwmRange(new PwmControl.PwmRange(500,2500));
        wrist.setPwmRange(new PwmControl.PwmRange(500,2500));
        arm.setPwmRange(new PwmControl.PwmRange(500,2500));

        //Voltage Sensor
        vs = hardwareMap.voltageSensor.get("Control Hub");

        //Initialize the pinpoint
        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));
    }

    //Yeah, I don't really get it rn
    public Action finishAction() {
        return new FinishAction(this);
    }
    public class FinishAction implements Action {
        CalvinRevived bot = null;

        public FinishAction(CalvinRevived h) {
            bot = h;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            bot.done = true;
            return false;
        }
    }
    public class TickingAction implements Action {
        CalvinRevived bot = null;

        public TickingAction(CalvinRevived h) {
            bot = h;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            bot.tick();
            if (bot.done) {
                bot.runMacro(TeleOpStart); //UNCOMMENT THIS
                bot.tick();
                return false;
            }
            return true;
        }
    }
    public class MacroAction implements Action {
        CalvinRevived bot = null;
        NewCalvinState macro = null;
        public MacroAction(CalvinRevived h, NewCalvinState s) {
            bot = h;
            macro = s;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            bot.runMacro(macro);
            return false;
        }
    }
    public class MacroActionTimeout implements Action {
        CalvinRevived bot = null;
        NewCalvinState macro = null;
        ElapsedTime et = null;
        int timeout;
        boolean TIMER_RUNNING = false;

        public MacroActionTimeout(CalvinRevived h, NewCalvinState s, int millis) {
            bot = h;
            macro = s;
            et = new ElapsedTime();
            timeout = millis;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!TIMER_RUNNING) {
                et.reset();
                TIMER_RUNNING = true;
            }
            if (et.milliseconds() < timeout) {
                return true;
            } else {
                bot.runMacro(macro);
                return false;
            }
        }
    }
    public class WaitAction implements Action {
        ElapsedTime et = null;
        int timeout;
        boolean TIMER_RUNNING = false;

        public WaitAction(int millis) {
            et = new ElapsedTime();
            timeout = millis;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!TIMER_RUNNING) {
                et.reset();
                TIMER_RUNNING = true;
            }
            return et.milliseconds() < timeout;
        }
    }
    public Action actionTick() {
        return new CalvinRevived.TickingAction(this);
    }
    public Action actionMacro(NewCalvinState macro) {
        Logger.getLogger("FUCK").log(new LogRecord(Level.INFO, "action macro :)"));
        return new SequentialAction(new CalvinRevived.MacroAction(this, macro));
    }
    public Action actionMacroTimeout(NewCalvinState macro, int millis) {
        return new SequentialAction(new CalvinRevived.MacroActionTimeout(this, macro, millis));
    }
    public Action actionWait(int millis) {
        return new SequentialAction(new CalvinRevived.WaitAction(millis));
    }


    //Servo controller
    public class ServosController {

        public double armPos = armPassive;

        public double wristPos = wristPassive;
        public double hSlidesPos = hSlidesIn;

        public double hClawPos = hClawClosed;
        public double vClawPos = vClawClosed;
        public double rotatorPos = rotatorPassivePos;
        public double shaqPos = shaqPassive;

        public void setVClaw(boolean open) {
            vClawPos = open ? vClawOpen : vClawClosed;
        }
        public void setHClaw(boolean open) {
            hClawPos = open ? hClawOpen : hClawClosed;
        }
        //public void setArmPos(boolean inside) {elbowPos = inside ? elbowInside : elbowFullOutside;}
        public void setHSlidesPos(boolean inside) {hSlidesPos = inside ? hSlidesIn : hSlidesOut;}

        public void servosTick() {

            telemetry.addData("h claw pos", hClawPos);
            telemetry.addData("wrist pos", wristPos);
            telemetry.addData("arm pos", armPos);
            telemetry.addData("H Slides Position", hSlidesPos);
            telemetry.addData("v claw Pos", vClawPos);
            telemetry.addData("rotatorPos", rotatorPos);
            telemetry.addData("shaqPos", shaqPos);


            hClaw.setPosition(hClawPos);

            wrist.setPosition(wristPos);

            arm.setPosition(armPos);

            horizontalSlidesLeft.setPosition(hSlidesPos);
            horizontalSlidesRight.setPosition(hSlidesPos);


            vClaw.setPosition(vClawPos);
            clawRotator.setPosition(rotatorPos);
            shaq.setPosition(shaqPos);
        }


    }


    //Drive Controller
    public class DriveTrainController {

        public void driveSafely(double lx, double ly, double rx, double rt) {

            double rTrigger = (slowing) ? 1 - rt : 1;
            double joystickX = -lx;
            double joystickY = ly;
            double joystickR = -rx;


            rightFrontCalvin.setPower(rTrigger * (joystickY - joystickX - joystickR));
            leftFrontCalvin.setPower((joystickY + joystickX + joystickR));
            rightBackCalvin.setPower(rTrigger * (joystickY + joystickX - joystickR));
            leftBackCalvin.setPower(rTrigger * joystickY - joystickX + joystickR);

        }
        public void motorDrive(double motorFrontLeftPower, double motorBackLeftPower, double motorFrontRightPower, double motorBackRightPower){
            // drive the motors at custom powers for each
            // used for every other drive class

            leftBackCalvin.setPower(motorBackLeftPower);
            leftFrontCalvin.setPower(motorFrontLeftPower);
            rightBackCalvin.setPower(motorBackRightPower);
            rightFrontCalvin.setPower(motorFrontRightPower);
        }
        public void motorStop(){
            // stop all the motors
            // used at the end of all movement functions
            leftBackCalvin.setPower(0);
            leftFrontCalvin.setPower(0);
            rightBackCalvin.setPower(0);
            rightFrontCalvin.setPower(0);
        }
        public void motorDriveXY(double xvec, double yvec, double spinvec){
            // this class drives the robot in the direction of vectors from a joystick and a spin value
            // used for teleop mode driving wheels with joysticks


            double y = pow(yvec,1); // Remember, this is reversed!
            double x = pow(xvec * 1.1,1); // Counteract imperfect strafing
            double rx = pow(spinvec,1);


            //denominator is the largest motor power (absolute value) or 1
            //this ensures all the powers maintain the same ratio, but only when
            //at least one is out of the range [-1, 1]
            double denominator = Math.max(abs(y) + abs(x) + abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorDrive(frontLeftPower,backLeftPower, frontRightPower, backRightPower);

        }

    }

    //We shall see. This class for controlling the slides works the same as your normal run to position
    //while still running without an encoder
    //This means we can use button presses for specific heights
    //I don't know why it works, and their comments they said they don't know either LOL
    public class MotorSlidesController {
        public double slideTar = 0;
        public boolean runToBottom = false;
        public boolean SLIDE_TARGETING = false;
        public double basePos = 0;
        public double pos = 0;
        public double errorThreshold = 20;
        public double derivativeThreshold = 1;

        public double power = 0;

        // public double slideTar = 0;
        public PID slidePID;
        public int disabled = 2;

        // public double maxHeight = 1000;
        // public double minHeight = 0;

        // public double differenceScalar = 0.0001;
        // public double scaler = 50;
        Telemetry tele = FtcDashboard.getInstance().getTelemetry();

        public void setTele(Telemetry t) {
            tele = t;
        }

        public void start() {
            basePos = vSlidesLeft.getCurrentPosition();

            slidePID = new PID(SLIDE_KP, 0, 0, false);
            tele = FtcDashboard.getInstance().getTelemetry();
        }

        public void slidesTickButtonVersion() {
            if (disabled == 0) {
                vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                vSlidesLeft.setPower(0);
                vSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                vSlidesRight.setPower(0);
                return;
            } else if (disabled == 1) {
                vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                vSlidesLeft.setPower(0.2);
                vSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                vSlidesRight.setPower(0.2);
                return;
            } else if (vSlidesLeft.getZeroPowerBehavior() != DcMotor.ZeroPowerBehavior.BRAKE) {
                vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                vSlidesRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            if (!SLIDE_TARGETING)
                slidePID.setConsts(SLIDE_KP, 0, 0);
            slidePID.setTarget(slideTar);
            pos = -(vSlidesLeft.getCurrentPosition() - basePos);

            tele.addData("pos", pos);
            tele.addData("targeting", SLIDE_TARGETING);
            tele.addData("slidetar", slideTar);
            tele.addData("slidep", SLIDE_KP);

            if (pos < vSlidesMini - 100 && power < 0) {
                SLIDE_TARGETING = true;
                slideTar = vSlidesMini - 100;
            }
            if (pos > vSlidesMaxi && power > 0) {
                SLIDE_TARGETING = true;
                slideTar = vSlidesMaxi;
            }
            if (SLIDE_TARGETING) {
                power = -slidePID.tick(pos);
                tele.addData("pidpower", power);
            }

            tele.addData("drivingPower", !runToBottom ? minMaxScaler(pos, power) : 0.4);
            tele.update();

            if (!runToBottom) {
                vSlidesLeft.setPower(minMaxScaler(pos, power));
                vSlidesRight.setPower(minMaxScaler(pos, power));
            } else {
                vSlidesLeft.setPower(0.3);
                vSlidesRight.setPower(0.3);
            }
        }

        public void slidesTickJoyStickVersion(double x, double power) {
            if (vSlidesLeft.getCurrentPosition() > vSlidesMaxi) {
                //If its above the max, only allow negative power
                vSlidesLeft.setPower(Math.min(minMaxScaler(x, power), 0));
                vSlidesRight.setPower(Math.min(minMaxScaler(x, power), 0));
            } else if (vSlidesRight.getCurrentPosition() < vSlidesMini) {
                //If its below the min, only allow positive power
                vSlidesLeft.setPower(Math.max(minMaxScaler(x, power), 0));
                vSlidesRight.setPower(Math.max(minMaxScaler(x, power), 0));
            } else {
                vSlidesLeft.setPower(minMaxScaler(x, power));
                vSlidesRight.setPower(minMaxScaler(x, power));
            }

        }

        // REWRITE EVENTUALLY AND CLEAN UP PLEASE
        private double minMaxScaler(double x, double power) {
            double p = power * (power > 0
                    ? ((1.3 * 1 / (1 + pow(E, -vSlidesScaling * (x - 100 + vSlidesMini)))) - 0.1)
                    : ((1.3 * 1 / (1 + pow(E, vSlidesScaling * (x + 100 - vSlidesMaxi)))) - 0.1));
            // uuuuuh
            return p;
        }

        public void driveSlides(double p) {
            // if (p == 0) setTarget(pos); // untested
            tele.addData("ipower", p);
            tele.addData("cpower", power);
            tele.update();
            SLIDE_TARGETING = false;
            power = -p;
        }

        public void setTargeting(boolean targeting) {
            SLIDE_TARGETING = targeting;
        }

        public void setTarget(double tar) {
            slideTar = tar;
            SLIDE_TARGETING = true;
        }

        public void resetSlideBasePos() {
            vSlidesLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            vSlidesLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            basePos = vSlidesLeft.getCurrentPosition();
        }

        public boolean isBusy() {

            return slidePID.getDerivative() < derivativeThreshold && abs(pos - slideTar) < errorThreshold;
            // could get proportion (^) from pid but dont want to
        }

    }
    //Macros
    public void runMacro(NewCalvinState m) {
        if (macroTimer.milliseconds() < macroTimeout)
            macroTimeout = ENDING; // cancel ongoing macro
        macroState = m;
        MACROING = true;
    }
    public void cancelMacros() {
        MACROING = false;
        macroTimeout = ENDING;
        slidesTrigger = ENDING;
        // slidesController.setTargeting(false);
    }
    public void tickMacros() {
        if (macroTimer.milliseconds() > macroTimeout) {
            macroTimeout = ENDING;
            MACROING = true;
        }
        if (abs(slidesTrigger-slidesController.pos) < slidesTriggerThreshold) {
            slidesTrigger = ENDING;
            MACROING = true;
        }
        if (MACROING) {
            NewCalvinState m = macroState;

            if (m.armPosition != null)
                servoController.armPos = m.armPosition;
            if (m.extendoPosition != null)
                servoController.hSlidesPos = m.extendoPosition;
            if (m.wristPosition != null)
                servoController.wristPos = m.wristPosition;
            if (m.HClawPosition!= null)
                servoController.hClawPos = m.HClawPosition;
            if (m.VClawPosition!= null)
                servoController.vClawPos = m.VClawPosition;
            if (m.shaqPosition != null)
                servoController.shaqPos = m.shaqPosition;
            if (m.rotatorPosition != null)
                servoController.rotatorPos = m.rotatorPosition;
            if (m.verticalSlidesPosition != null)
                slidesController.setTarget(m.verticalSlidesPosition);
            MACROING = false;
        }
    }

    //updates the bot
    public void tickTele(double x, double power) {
        //drive.updatePoseEstimate(); // update localizer
        tickMacros(); // check macros
        slidesController.slidesTickJoyStickVersion(x, power); // update slides
        servoController.servosTick(); // update servos
        telemetry.addData("voltage", vs.getVoltage());
        telemetry.update();
    }
    //updates the bot
    public void tick(){
        tickMacros(); // check macros
        slidesController.slidesTickButtonVersion(); // update slides
        servoController.servosTick(); // update servos
        telemetry.addData("voltage", vs.getVoltage());
        telemetry.update();
    }
}
