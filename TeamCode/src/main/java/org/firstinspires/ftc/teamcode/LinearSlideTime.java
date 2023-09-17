package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class LinearSlideTime {

    public DcMotor lineSlide;
    public int currentPosition;
    ElapsedTime runtime = new ElapsedTime();

    public LinearSlideTime(DcMotor lineSlide){
        this.lineSlide = lineSlide;
        currentPosition = 0;

    }


    public void moveForTime(double speed, double time){
        runtime.reset();
        lineSlide.setPower(speed);

        while (runtime.seconds() < time){

        }

        lineSlide.setPower(0);


    }
}

