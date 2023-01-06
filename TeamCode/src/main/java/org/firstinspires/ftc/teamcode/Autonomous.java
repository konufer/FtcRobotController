/*
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group = "Robot")
public class Autonomous extends LinearOpMode {

    public ColorSensor color;

    public Servo servo;
    public DcMotor lineSlide = null;

    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor backLeft = null;

    static final double DRIVE_SPEED = 0.4;
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
        servo = hardwareMap.get(Servo.class, "armServo");
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

        //forward a little
        //read signal cone
        // forward some more (drive over signal cone)
        //shift a little to the right or left (depending on the start)
        //dunk on the high cone
        //shift a little the other way to recenter
        //move depending on signal cone

        // position 1 (red)
        //move left

        //position 2 (green)
        //do nothing

        //position 3 (blue)
        //move right

        */
/*double total = 44;
        double delta = 16;
        double offset = 3;
        drive.straight(DRIVE_SPEED, delta);
        drive.sideways(DRIVE_SPEED, -offset);

        colorSensor.getColors();
        String colorDetected = colorSensor.colorPath();

        drive.sideways(DRIVE_SPEED, offset);
        drive.straight(DRIVE_SPEED, total-delta);*//*




        //drive.sideways(DRIVE_SPEED, 2.0);

        linearSlide.moveToPosition(1.0, 1);
        arm.releaseObject();

        */
/*drive.sideways(DRIVE_SPEED, -2.0);


        telemetry.addData("red", color.red());
        telemetry.addData("blue", color.blue());
        telemetry.addData("green", color.green());
        telemetry.update();


        if (colorDetected.equals("red")) {
            drive.sideways(DRIVE_SPEED, -24);
        }

        // if green do nothing

        if (colorDetected.equals("blue")) {
            drive.sideways(DRIVE_SPEED, 24);
        }*//*

    }

}
*/
