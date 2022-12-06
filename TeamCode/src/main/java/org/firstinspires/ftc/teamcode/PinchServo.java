package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PinchServo {

    public CRServo pinchServo;

    ElapsedTime runtime = new ElapsedTime();

    public PinchServo(CRServo pinchServo){
        this.pinchServo = pinchServo;
    }

    public void stop(){
        //this stops the servo, it should be 0 but the servo is weird
        pinchServo.setPower(-0.05);
    }

    public void pinchObject(){
    pinchServo.setPower(1.0);
    }

    public void releaseObject(){
        runtime.reset();
        while (runtime.seconds() < 0.5) {
            pinchServo.setPower(-1.0);
        }
        stop();
    }

}
