package org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.END;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.SLIDES_KP;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.clawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.clawOpen;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.elbowFullOutside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.elbowInside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.horizontalSlidesIn;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.horizontalSlidesOut;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.intakeMax;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.intakeOff;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.rotatorPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.shaqPassivePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.slowingAllowed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.topMultiplier;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.vSlidesScaler;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.vSlidesMax;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinConstants.vSlidesMin;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinMacros.TeleopStartPosition;

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
import com.qualcomm.robotcore.hardware.CRServo;
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
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.CalvinState;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.BigNateAndHisHelpers.HelpersBigNate.PIDCalc;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Config
public class BigNate {

    //Initializing
    public DcMotorImplEx vSlidesLeft, vSlidesRight;
    public DcMotorEx rightBackCalvin, rightFrontCalvin, leftBackCalvin, leftFrontCalvin;

    public CRServo intakeLeft, intakeUp, intakeRight;

    public ServoImplEx shaq, horizontalSlidesLeft, horizontalSlidesRight,
            elbowLeft, elbowRight, claw, clawRotator;

    //These controllers allow us to group our servos drive motors and slide motors
    public MotorSlideController slidesController = new MotorSlideController();

    public ServoController servoController = new ServoController();

    public DriveController driveController = new DriveController();

    //I assume this telemetry is for dashboard??
    Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();


    //pinpointt
    public PinpointDrive drive;

    private VoltageSensor vs;
    public CalvinState macroState = null;

    //The macro code i lowkey still don't understand
    public boolean MACROING = false;
    public ElapsedTime macroTimer = new ElapsedTime();
    public int macroTimeout = END;
    public int slidesTrigger = END;
    public int slidesTriggerThreshold = 10;
    public boolean done = false;

    //The normal constuctor you're used to. 252 does it differently, so I hope this still works
    public BigNate(HardwareMap hardwareMap) {
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
        //Elbow
        elbowLeft = hardwareMap.get(ServoImplEx.class,"elbowLeft");
        elbowRight = hardwareMap.get(ServoImplEx.class,"elbowRight");
        elbowRight.setDirection(Servo.Direction.REVERSE);

        //Intake Servos
        intakeLeft = hardwareMap.get(CRServo.class,"continuousIntakeLeft"); //setPower
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRight = hardwareMap.get(CRServo.class,"continuousIntakeRight"); //setPower
        intakeUp = hardwareMap.get(CRServo.class, "continuousIntakeUp"); //setPower
        //Claw Related Servos
        claw = hardwareMap.get(ServoImplEx.class,"claw");
        shaq = hardwareMap.get(ServoImplEx.class,"shaq");
        clawRotator = hardwareMap.get(ServoImplEx.class,"clawRotator");
        //Range Booster. I assume 500 is the "zero" and 2500 is the "one" so change as you need
        clawRotator.setPwmRange(new PwmControl.PwmRange(500,2500));
        shaq.setPwmRange(new PwmControl.PwmRange(500,2500));

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
        BigNate bot = null;

        public FinishAction(BigNate h) {
            bot = h;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            bot.done = true;
            return false;
        }
    }
    public class TickingAction implements Action {
        BigNate bot = null;

        public TickingAction(BigNate h) {
            bot = h;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            bot.tick();
            if (bot.done) {
                bot.runMacro(TeleopStartPosition);
                bot.tick();
                return false;
            }
            return true;
        }
    }
    public class MacroAction implements Action {
        BigNate bot = null;
        CalvinState macro = null;

        public MacroAction(BigNate h, CalvinState s) {
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
        BigNate bot = null;
        CalvinState macro = null;
        ElapsedTime et = null;
        int timeout;
        boolean TIMER_RUNNING = false;

        public MacroActionTimeout(BigNate h, CalvinState s, int millis) {
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
        return new TickingAction(this);
    }
    public Action actionMacro(CalvinState macro) {
        Logger.getLogger("FUCK").log(new LogRecord(Level.INFO, "action macro :)"));
        return new SequentialAction(new MacroAction(this, macro));
    }
    public Action actionMacroTimeout(CalvinState macro, int millis) {
        return new SequentialAction(new MacroActionTimeout(this, macro, millis));
    }
    public Action actionWait(int millis) {
        return new SequentialAction(new WaitAction(millis));
    }


    //Servo controller
    public class ServoController {

        public double intakeSpeed = intakeOff;
        public double intakeMulti = intakeMax;

        public double elbowPos = elbowInside;
        public double HSlidesPos = horizontalSlidesIn;
        public double clawPos = clawClosed;
        public double rotatorPos = rotatorPassive;
        public double shaqPos = shaqPassivePosition;

        public void setClaw(boolean open) {
            clawPos = open ? clawOpen : clawClosed;
        }
        public void setElbowPos(boolean inside) {elbowPos = inside ? elbowInside : elbowFullOutside;}
        public void setHSlidesPos(boolean inside) {HSlidesPos = inside ? horizontalSlidesIn : horizontalSlidesOut;}

        public void servosTick() {

            telemetry.addData("Intake Speed", intakeSpeed);
            telemetry.addData("Intake Multiplier", intakeMulti);
            telemetry.addData("Elbow Position", elbowPos);
            telemetry.addData("H Slides Position", HSlidesPos);
            telemetry.addData("clawPos", clawPos);
            telemetry.addData("rotatorPos", rotatorPos);
            telemetry.addData("shaqPos", shaqPos);


            intakeLeft.setPower(intakeSpeed * intakeMulti);
            intakeRight.setPower(intakeSpeed * intakeMulti);
            intakeUp.setPower(intakeSpeed * intakeMulti * topMultiplier);

            horizontalSlidesLeft.setPosition(HSlidesPos);
            horizontalSlidesRight.setPosition(HSlidesPos);

            elbowLeft.setPosition(elbowPos);
            elbowRight.setPosition(elbowPos);

            claw.setPosition(clawPos);
            clawRotator.setPosition(rotatorPos);
            shaq.setPosition(shaqPos);
        }


    }


//Drive Controller
    public class DriveController {

        public void driveSafely(double lx, double ly, double rx, double rt) {

            double rTrigger = (slowingAllowed) ? 1 - rt : 1;
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
    public class MotorSlideController {
        public double slideTar = 0;
        public boolean runToBottom = false;
        public boolean SLIDE_TARGETING = false;
        public double basePos = 0;
        public double pos = 0;
        public double errorThreshold = 20;
        public double derivativeThreshold = 1;

        public double power = 0;

        // public double slideTar = 0;
        public PIDCalc slidePID;
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

            slidePID = new PIDCalc(SLIDES_KP, 0, 0, false);
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
                slidePID.setConsts(SLIDES_KP, 0, 0);
            slidePID.setTarget(slideTar);
            pos = -(vSlidesLeft.getCurrentPosition() - basePos);

            tele.addData("pos", pos);
            tele.addData("targeting", SLIDE_TARGETING);
            tele.addData("slidetar", slideTar);
            tele.addData("slidep", SLIDES_KP);

            if (pos < vSlidesMin - 100 && power < 0) {
                SLIDE_TARGETING = true;
                slideTar = vSlidesMin - 100;
            }
            if (pos > vSlidesMax && power > 0) {
                SLIDE_TARGETING = true;
                slideTar = vSlidesMax;
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
            if (vSlidesLeft.getCurrentPosition() > vSlidesMax) {
                //If its above the max, only allow negative power
                vSlidesLeft.setPower(Math.min(minMaxScaler(x, power), 0));
                vSlidesRight.setPower(Math.min(minMaxScaler(x, power), 0));
            } else if (vSlidesRight.getCurrentPosition() < vSlidesMin) {
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
                    ? ((1.3 * 1 / (1 + pow(E, -vSlidesScaler * (x - 100 + vSlidesMin)))) - 0.1)
                    : ((1.3 * 1 / (1 + pow(E, vSlidesScaler * (x + 100 - vSlidesMax)))) - 0.1));
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
    public void runMacro(CalvinState m) {
        if (macroTimer.milliseconds() < macroTimeout)
            macroTimeout = END; // cancel ongoing macro
        macroState = m;
        MACROING = true;
    }
    public void cancelMacros() {
        MACROING = false;
        macroTimeout = END;
        slidesTrigger = END;
        // slidesController.setTargeting(false);
    }
    public void tickMacros() {
        if (macroTimer.milliseconds() > macroTimeout) {
            macroTimeout = END;
            MACROING = true;
        }
        if (abs(slidesTrigger-slidesController.pos) < slidesTriggerThreshold) {
            slidesTrigger = END;
            MACROING = true;
        }
        if (MACROING) {
            CalvinState m = macroState;
            if (m.intakeSpeed != null)
                servoController.intakeSpeed = m.intakeSpeed;
            if (m.extendoPosition!= null)
                servoController.intakeMulti = m.intakeMultiplier;
            if (m.elbowPosition != null)
                servoController.elbowPos = m.elbowPosition;
            if (m.clawPosition != null)
                servoController.clawPos = m.clawPosition;
            if (m.shaqPosition != null)
                servoController.shaqPos = m.shaqPosition;
            if (m.rotatorPosition != null)
                servoController.rotatorPos = m.rotatorPosition;
            if (m.verticalSlidesPosition!= null)
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
