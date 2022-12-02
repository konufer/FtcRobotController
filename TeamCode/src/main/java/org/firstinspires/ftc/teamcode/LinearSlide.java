package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LinearSlide {

    public DcMotor lineSlide;

    //In inches
    public double[] positionHeights;

    public int currentPosition;
    public int lineSlideTarget;

    final double COUNTS_PER_MOTOR_REV;
    final double WHEEL_DIAMETER_INCHES;
    final double COUNTS_PER_INCH;



    public LinearSlide(DcMotor lineSlide, double[] positionHeights, double COUNTS_PER_MOTOR_REV, double WHEEL_DIAMETER_INCHES){
        this.lineSlide = lineSlide;

        currentPosition = 0;

        this.positionHeights = positionHeights;
        this.COUNTS_PER_MOTOR_REV = COUNTS_PER_MOTOR_REV;
        this.WHEEL_DIAMETER_INCHES = WHEEL_DIAMETER_INCHES;
        COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);


    }

    public double distanceToMove(int desiredPosition){
        double distanceDiff = positionHeights[desiredPosition] - positionHeights[currentPosition];
        return distanceDiff;
    }

    public void moveToPosition(int desiredPosition){
        double distance = distanceToMove(desiredPosition);

        int moveCounts = (int)(distance * COUNTS_PER_INCH);
        lineSlideTarget = lineSlide.getCurrentPosition() + moveCounts;
        lineSlide.setTargetPosition(lineSlideTarget);
        lineSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(lineSlide.isBusy()){
            lineSlide.setPower(1.0);
        }

        lineSlide.setPower(0.0);
    }


}

