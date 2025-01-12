package org.firstinspires.ftc.teamcode.RobotAndHerHelpers;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.END;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.SLIDES_KP;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.clawClosed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.despacito;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.elbowInside;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.horizontalSlidesIn;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeIntake;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeMax;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.intakeOff;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.rotatorPassive;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.shaqPassivePosition;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.slowingAllowed;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.topMultiplier;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.vSlidesScaler;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.vSlidesMax;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.vSlidesMin;
import static org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinConstants.vSlidesScaler;
import static java.lang.Math.E;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

import com.acmerobotics.dashboard.FtcDashboard;
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
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.CalvinState;
import org.firstinspires.ftc.teamcode.RobotAndHerHelpers.Helpers.PID;
public class BigNate {

    //Initializing
    public DcMotorImplEx vSlidesLeft, vSlidesRight;
    public DcMotorEx rightBackCalvin, rightFrontCalvin, leftBackCalvin, leftFrontCalvin;

    public CRServo intakeLeft, intakeUp, intakeRight;

    public ServoImplEx shaq, horizontalSlidesLeft, horizontalSlidesRight,
            elbowLeft, elbowRight, claw, clawRotator;

    //Organize later
    Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();
    public static double ASCENT_KP = 0.01;

    public MotorSlideController slidesController = new MotorSlideController();

    public ServoController servoController = new ServoController();

    public DriveController driveController = new DriveController();

    private VoltageSensor vs;
    public CalvinState macroState = null;
    public boolean MACROING = false;
    public ElapsedTime macroTimer = new ElapsedTime();
    public int macroTimeout = END;
    public int slidesTrigger = END;
    public int slidesTriggerThreshold = 10;
    public boolean done = false;

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

        vs = hardwareMap.voltageSensor.get("Control Hub");
    }
    //As a practice, intake controller here
    public class ServoController {

        public double intakeSpeed = intakeOff;
        public double intakeMulti = intakeMax;

        public double elbowPos = elbowInside;
        public double HSlidesPos = horizontalSlidesIn;
        public double clawPos = clawClosed;
        public double rotatorPos = rotatorPassive;
        public double shaqPos = shaqPassivePosition;


        public void servosTick() {
            telemetry.addData("Intake Speed", intakeSpeed);
            telemetry.addData("Intake Multiplier", intakeMulti);
            telemetry.addData("Elbow Position", elbowPos);
            telemetry.addData("H Slides Position", HSlidesPos);
            telemetry.addData("clawPos", clawPos);
            telemetry.addData("rotatorPos", rotatorPassive);
            telemetry.addData("shaqPos", shaqPassivePosition);

            intakeLeft.setPower(intakeSpeed * intakeMulti);
            intakeRight.setPower(intakeSpeed * intakeMulti);
            intakeUp.setPower(intakeSpeed * intakeMulti * topMultiplier);

            horizontalSlidesLeft.setPosition(horizontalSlidesIn);
            horizontalSlidesRight.setPosition(horizontalSlidesIn);

            elbowLeft.setPosition(elbowInside);
            elbowRight.setPosition(elbowInside);

            claw.setPosition(clawClosed);
            clawRotator.setPosition(rotatorPassive);
            shaq.setPosition(shaqPassivePosition);
        }


    }



    public class DriveController {

        public void driveSafely(double lx,double  ly, double rx, double rt) {

            double rTrigger = (slowingAllowed) ? 1 - rt : 1;
            double joystickX = -lx;
            double joystickY = ly;
            double joystickR = -rx;


            rightFrontCalvin.setPower(rTrigger * (joystickY - joystickX - joystickR));
            leftFrontCalvin.setPower((joystickY + joystickX + joystickR));
            rightBackCalvin.setPower(rTrigger * (joystickY + joystickX - joystickR));
            leftBackCalvin.setPower(rTrigger * joystickY - joystickX + joystickR);

        }

    }


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

            slidePID = new PID(SLIDES_KP, 0, 0, false);
            tele = FtcDashboard.getInstance().getTelemetry();
        }

        public void slidesTick() {
            if (disabled == 0) {
                vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                vSlidesLeft.setPower(0);
                return;
            } else if (disabled == 1) {
                vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                vSlidesLeft.setPower(0.2);
                return;
            } else if (vSlidesLeft.getZeroPowerBehavior() != DcMotor.ZeroPowerBehavior.BRAKE) {
                vSlidesLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

            if (!runToBottom)
                vSlidesLeft.setPower(minMaxScaler(pos, power));
            else
                vSlidesLeft.setPower(0.3);
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

    public void tick() {
        //drive.updatePoseEstimate(); // update localizer
        tickMacros(); // check macros
        slidesController.slidesTick(); // update slides
        servoController.servosTick(); // update servos
        driveController.driveSafely(); //update drive motors
        telemetry.addData("voltage", vs.getVoltage());
        telemetry.update();
    }



}
