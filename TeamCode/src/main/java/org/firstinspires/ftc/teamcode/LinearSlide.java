package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class LinearSlide {

    public static DcMotor lineSlide;

    //In inches
    public double[] positionHeights;

    public int currentPosition;
    public int lineSlideTarget;

    final double COUNTS_PER_MOTOR_REV;
    final double DISTANCE_MOVED_PER_REVOLUTION;
    final double COUNTS_PER_INCH;



    public LinearSlide(DcMotor lineSlide, double[] positionHeights, double COUNTS_PER_MOTOR_REV, double DISTANCE_MOVED_PER_REVOLUTION){
        this.lineSlide = lineSlide;

        currentPosition = 0;

        this.positionHeights = positionHeights;
        this.COUNTS_PER_MOTOR_REV = 537.7 ;   // eg: GoBILDA 312 RPM Yellow Jacket
        this.DISTANCE_MOVED_PER_REVOLUTION = 42;
        COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV) / (DISTANCE_MOVED_PER_REVOLUTION * 3.1415);


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
    }


}

