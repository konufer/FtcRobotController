package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PinchServo {

    public Servo pinchServo;

    ElapsedTime runtime = new ElapsedTime();

    public PinchServo(Servo pinchServo){
        this.pinchServo = pinchServo;
    }

    public void pinchObject(){
    pinchServo.setPosition(1.0);
    }

    public void releaseObject(){
        pinchServo.setPosition(0.0);
    }

}
