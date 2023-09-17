package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//making this an autonomous code, not a TeleOP
@Autonomous(name = "ColorSensorAutonomous", group = "Robot")
public class ColorSensorAutonomous extends LinearOpMode {

    //initializing color to an object of color
    ColorSensor color = null;

    //initializing motors
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backRight = null;
    private DcMotor backLeft = null;
    public CRServo servo;
    public DcMotor lineSlide = null;

    //runtime gives us values of how long things run
    private ElapsedTime runtime = new ElapsedTime();

    //constants for driving motors
    //for the counts per motor rev, i found 560 in the documentation for our specific motors we use
    //    (Rev Robotics HD Hex Motor UltraPlanetary Motor (Gear ratio 20:1))
    static final double COUNTS_PER_MOTOR_REV = 560;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 2.95276;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    static final double COUNTS_PER_MOTOR = 1120;
    static final double WHEEL_DIAMETER = 1.5;
    static final double COUNTS_INCH = (COUNTS_PER_MOTOR) /
            (WHEEL_DIAMETER * Math.PI);
    static final double SLIDE_SPEED = 0.5;

    //runOpMode is a previously defined method, so this is overriding it
    @Override
    //the part that actually runs stuff
    public void runOpMode() {

        PinchServo pinchServo = new PinchServo(servo);
        lineSlide = hardwareMap.get(DcMotor.class, "lineSlide");
        servo = hardwareMap.get(CRServo.class, "armServo");
        //mapping the color sensor
        color = hardwareMap.get(ColorSensor.class, "color");

        //mapping the motors (i don't know the actual names so that can be changed later)
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        //setting directions so its easier to turn motors
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        //setting the mode to drive the robot
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lineSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lineSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //saying that it's ready to run
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        //waiting for the run button to be hit
        waitForStart();

        pinchServo.pinchObject();
        encoderSlide(SLIDE_SPEED, 20);

        //the exact inches are off but the overall idea here is to move forward to the cone and then
        //   strafe to the right because i was planning on putting the color sensor in the front
        //   left corner so it doesn't come close to causing any issues with the lift mechanism

        encoderDrive(DRIVE_SPEED, 15.5, 15.5, 15.5, 15.5);
        encoderDrive(TURN_SPEED, 8.75, -8.75, -8.75, 8.75);


        //shows the color values that color sensor can see
        //so in theory, this would show the color values that the sensor sees at the time it was read
        //   this can be used to see what should be in those if statements below cuz idk where 20 came from
        int rc = color.red();
        int gc = color.green();
        int bc = color.blue();

        telemetry.addData("red", rc);
        telemetry.addData("green", bc);
        telemetry.addData("blue", bc);
        telemetry.update();

        encoderDrive(TURN_SPEED, -8.75, 8.75, 8.75, -8.75);
        encoderDrive(DRIVE_SPEED, -15.5, -15.5, -15.5, -15.5);
        encoderDrive(TURN_SPEED, -12.75, 12.75, 12.75, -12.75);
        encoderDrive(DRIVE_SPEED, 11.5, 11.5, 11.5, 11.5);

        encoderSlide(SLIDE_SPEED, -2);
        pinchServo.releaseObject();

        encoderDrive(DRIVE_SPEED, -11.5, -11.5, -11.5, -11.5);
        encoderDrive(TURN_SPEED, 12.75, -12.75, -12.75, 12.75);



        //figuring out what color is in front of it and setting the correct boolean to true
        //i am completely unsure what unit it is measured in so idk what 20 means??
        if ((rc > gc) && (rc > bc)) {
            encoderDrive(TURN_SPEED, -24, 24, 24, -24);
            encoderDrive(DRIVE_SPEED, 26, 26, 26, 26);
            telemetry.addLine("red");
        }
        if ((gc > rc) && (gc > bc)) {
            encoderDrive(DRIVE_SPEED, 26, 26, 26, 26);
            telemetry.addLine("green");
        }
        if ((bc > rc) && (bc > gc)) {
            encoderDrive(TURN_SPEED, 24, -24, -24, 24);
            encoderDrive(DRIVE_SPEED, 26, 26, 26, 26);
            telemetry.addLine("blue");
        }

        //from here, do a specific parking based on what color was determined to be in front (above code)
        //for right now, im just printing out in the app what path it chose to see if it got the right one


    }

    //this is trying to code the program for moving using encoders
    //remember, this all only works in theory and has not been tested
    public void encoderDrive(double speed, double fleftInches, double bleftInches,
                             double frightInches, double brightInches) {
        //initializing variables
        int newFRTarget;
        int newFLTarget;
        int newBRTarget;
        int newBLTarget;

        //making sure this should actually be running
        if (opModeIsActive()) {
            //setting the target position
            newFRTarget = frontRight.getCurrentPosition() + (int) (frightInches * COUNTS_PER_INCH);
            newFLTarget = frontLeft.getCurrentPosition() + (int) (fleftInches * COUNTS_PER_INCH);
            newBRTarget = backRight.getCurrentPosition() + (int) (brightInches * COUNTS_PER_INCH);
            newBLTarget = backLeft.getCurrentPosition() + (int) (bleftInches * COUNTS_PER_INCH);

            frontRight.setTargetPosition(newFRTarget);
            frontLeft.setTargetPosition(newFLTarget);
            backRight.setTargetPosition(newBRTarget);
            backLeft.setTargetPosition(newBLTarget);

            //making the motor mode run to position
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //setting the power of the motors to get it to move
            frontRight.setPower(Math.abs(speed));
            frontLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));
            backLeft.setPower(Math.abs(speed));

            //making sure it does stop until it reaches
            while (opModeIsActive() && (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy())) {
                telemetry.addLine("Moving theoretically");
                telemetry.update();
            }

            //stopping all movement
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);

            //changing the motor mode *again*
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        }

    }

    public void encoderDrive(double angle) {
        //intializing variables
        int newFRTarget;
        int newFLTarget;
        int newBRTarget;
        int newBLTarget;

        //declaring speed to turn speed
        double speed = TURN_SPEED;

        //making sure this should actually be running
        if (opModeIsActive()) {
            //determining the amount the wheels need to move
            double frightInches = (Math.sin(angle) * 17.5);
            double fleftInches = -(Math.sin(angle) * 17.5);
            double brightInches = (Math.sin(angle) * 17.5);
            double bleftInches = -(Math.sin(angle) * 17.5);

            //setting target position
            newFRTarget = frontRight.getCurrentPosition() + (int) (frightInches * COUNTS_PER_INCH);
            newFLTarget = frontLeft.getCurrentPosition() + (int) (fleftInches * COUNTS_PER_INCH);
            newBRTarget = backRight.getCurrentPosition() + (int) (brightInches * COUNTS_PER_INCH);
            newBLTarget = backLeft.getCurrentPosition() + (int) (bleftInches * COUNTS_PER_INCH);

            frontRight.setTargetPosition(newFRTarget);
            frontLeft.setTargetPosition(newFLTarget);
            backRight.setTargetPosition(newBRTarget);
            backLeft.setTargetPosition(newBLTarget);

            //making the motor mode run to position
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //setting the power of the motors to get it to move
            frontRight.setPower(Math.abs(speed));
            frontLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));
            backLeft.setPower(Math.abs(speed));

            //making sure it does stop until it reaches
            while (opModeIsActive() && (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy())) {
                telemetry.addLine("Moving theoretically");
                telemetry.update();
            }

            //stopping all movement
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);

            //changing the motor mode *again*
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        }



    }

    public void encoderSlide(double speed, double inches) {
        int newSlideTarget;

        if(opModeIsActive()){
            newSlideTarget = lineSlide.getCurrentPosition() + (int) (inches * COUNTS_INCH);

            lineSlide.setTargetPosition(newSlideTarget);

            lineSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            lineSlide.setPower(Math.abs(speed));

            while (opModeIsActive() && (lineSlide.isBusy())) {
                telemetry.addLine("Moving theoretically");
                telemetry.update();
            }

            lineSlide.setPower(0);

            lineSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


}