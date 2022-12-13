package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group = "Robot")
public class Autonomous extends LinearOpMode {

    public ColorSensor color;

    public CRServo servo;
    public DcMotor lineSlide = null;

    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor backLeft = null;

    static final double DRIVE_SPEED = 0.7;
    static final double TURN_SPEED = 0.6;
    static final double SLIDE_SPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {



        // Map motors to varibles
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight"); // port 1
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft"); //  port 2
        backRight  = hardwareMap.get(DcMotor.class, "backRight"); // port 3

        lineSlide = hardwareMap.get(DcMotor.class, "lineSlide");
        servo = hardwareMap.get(CRServo.class, "armServo");
        color = hardwareMap.get(ColorSensor.class, "color");

        //Creating Class instances
        ColorSensing colorSensor = new ColorSensing(color);
        NewMoveRobot drive = new NewMoveRobot(frontLeft, frontRight, backLeft, backRight, 560, 2.95276); //REV HD Hex motor
        PinchServo arm = new PinchServo(servo);

        double[] positionHeights = {3.5, 13.5, 23.5, 33.5};
        LinearSlide linearSlide = new LinearSlide(lineSlide, positionHeights, 1120, 1.5); //AndyMark NeveRest 40:1


        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();

        //Begin doing stuff
        arm.pinchObject();
        linearSlide.moveToPosition(SLIDE_SPEED, 0);



        drive.straight(DRIVE_SPEED, -15.5);
        drive.sideways(DRIVE_SPEED, 8.75);

        colorSensor.getColors();

        drive.sideways(DRIVE_SPEED, -8.75);

        drive.straight(DRIVE_SPEED, -15.5);
        drive.sideways(DRIVE_SPEED, -12.75);
        drive.straight(DRIVE_SPEED, 11.5);


        linearSlide.moveToPosition(SLIDE_SPEED, 1);
        arm.releaseObject();

        drive.straight(DRIVE_SPEED, -11.5);
        drive.sideways(DRIVE_SPEED,12.75);

        String colorDetected = colorSensor.colorPath();
        telemetry.addData("Color Detected", colorDetected);
        if (colorDetected.equals("red")) {

            drive.sideways(DRIVE_SPEED, -24);
            drive.straight(DRIVE_SPEED, 26);
        }
        if (colorDetected.equals("green")) {
            drive.straight(DRIVE_SPEED, 26);
        }
        if (colorDetected.equals("blue")) {
            drive.sideways(DRIVE_SPEED, -24);
            drive.straight(DRIVE_SPEED, 26);
        }

    }

}
