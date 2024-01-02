package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="MotorTest", group="Robot")

public class MotorTest extends LinearOpMode {

    public DcMotor motor  = null;

    public void runOpMode() {

        motor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                motor.setPower(1);
            } else if (gamepad1.b) {
                motor.setPower(0);
            } else if (gamepad1.x) {
                motor.setPower(-1);
            }
        }
    }
}

