package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="ColorSensorParking", group = "Robot")
public class AutonomousColorParking extends LinearOpMode {

    public ColorSensor color;

    public CRServo servo;
    public DcMotor lineSlide = null;

    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor backLeft = null;

    ColorSensing colorSensor = new ColorSensing(color);

    EncodedDriving drive = new EncodedDriving(frontLeft, frontRight, backLeft, backRight, lineSlide);
    PinchServo arm = new PinchServo(servo);

    static final double     DRIVE_SPEED              = 0.7;
    static final double     TURN_SPEED              = 0.6;
    static final double     SLIDE_SPEED              = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        mapHardware();

        drive.encodeMode();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        arm.pinchObject();
        drive.encoderSlide(SLIDE_SPEED, 20);

        drive.encoderDrive(DRIVE_SPEED, 15.5, 15.5, 15.5, 15.5);
        drive.encoderDrive(DRIVE_SPEED, 8.75, -8.75, -8.75, 8.75);

        detectColors();

        drive.encoderDrive(TURN_SPEED, -8.75, 8.75, 8.75, -8.75);
        drive.encoderDrive(DRIVE_SPEED, -15.5, -15.5, -15.5, -15.5);
        drive.encoderDrive(TURN_SPEED, -12.75, 12.75, 12.75, -12.75);
        drive.encoderDrive(DRIVE_SPEED, 11.5, 11.5, 11.5, 11.5);

        drive.encoderSlide(SLIDE_SPEED, -2);
        arm.releaseObject();

        drive.encoderDrive(DRIVE_SPEED, -11.5, -11.5, -11.5, -11.5);
        drive.encoderDrive(TURN_SPEED, 12.75, -12.75, -12.75, 12.75);

        String colorDetected = colorSensor.colorPath();
        telemetry.addData("Color Detected", colorDetected);
        if (colorDetected.equals("red")) {
            drive.encoderDrive(TURN_SPEED, -24, 24,24, -24);
            drive.encoderDrive(DRIVE_SPEED, 26, 26, 26, 26);
        }
        if (colorDetected.equals("green")) {
            drive.encoderDrive(DRIVE_SPEED, 26, 26, 26, 26);
        }
        if (colorDetected.equals("blue")) {
            drive.encoderDrive(TURN_SPEED, -24, 24,24, -24);
            drive.encoderDrive(DRIVE_SPEED, 26, 26, 26, 26);
        }

    }

    public void mapHardware() {
        color = hardwareMap.get(ColorSensor.class, "color");

        servo = hardwareMap.get(CRServo.class, "armServo");
        lineSlide = hardwareMap.get(DcMotor.class, "lineSlide");

        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
    }

    public void detectColors() {
        int[] colorValues = colorSensor.getColors();

        telemetry.addData("Red:", colorValues[0]);
        telemetry.addData("Green:", colorValues[1]);
        telemetry.addData("Blue:", colorValues[2]);
    }
}
