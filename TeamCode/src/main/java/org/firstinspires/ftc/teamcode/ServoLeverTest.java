package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ServoTest", group="Robot")

public class ServoLeverTest extends LinearOpMode {

    public Servo leverServo;

    public void runOpMode() {

        leverServo = hardwareMap.get(Servo.class, "armServo");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                leverServo.setPosition(1.0);
            } else if (gamepad1.b) {
                leverServo.setPosition(0.0);
            }
        }
    }
}
