package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class EncodedDriving {

    //declaration of motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotor lineSlide;

    public EncodedDriving(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, DcMotor lineSlide){
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.lineSlide = lineSlide;
    }


    //REV HD Hex motor numbers for mechanum wheels
    static final double COUNTS_PER_MOTOR_REV = 560;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 2.95276;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    //AndyMark 40:1 motor numbers for linear slide
    static final double COUNTS_PER_MOTOR = 1120;
    static final double WHEEL_DIAMETER = 1.5;
    static final double COUNTS_INCH = (COUNTS_PER_MOTOR) /
            (WHEEL_DIAMETER * Math.PI);


    public void encodeMode() {
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
    }

    public void encoderDrive(double speed, double fleftInches, double bleftInches,
                             double frightInches, double brightInches) {
        //initializing variables
        int newFRTarget;
        int newFLTarget;
        int newBRTarget;
        int newBLTarget;

        //making sure this should actually be running

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
        while ((frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy())) {

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

    public void encoderTurn(double speed, double angle) {
        //intializing variables
        int newFRTarget;
        int newFLTarget;
        int newBRTarget;
        int newBLTarget;

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
        while ((frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy())) {

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

    public void encoderSlide(double speed, double inches) {
        int newSlideTarget;

        newSlideTarget = lineSlide.getCurrentPosition() + (int) (inches * COUNTS_INCH);

        lineSlide.setTargetPosition(newSlideTarget);

        lineSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lineSlide.setPower(Math.abs(speed));

        while (lineSlide.isBusy()) {

        }

        lineSlide.setPower(0);

        lineSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}
