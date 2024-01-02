package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ServoClawTest", group="Robot")

public class ServoClawTest extends LinearOpMode {

    public Servo leftServo;
    public Servo rightServo;

    public void runOpMode() {

        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                leftServo.setPosition(0.95);
                rightServo.setPosition(0.95);
            } else if (gamepad1.b) {
                leftServo.setPosition(0.75);
                rightServo.setPosition(0.75);
            }
        }
    }
}
