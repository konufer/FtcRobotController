package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="AllMotorTest", group="Robot")

public class AllMotorTest extends LinearOpMode {

    public DcMotor motor1  = null;
    public DcMotor motor2  = null;
    public DcMotor motor3  = null;
    public DcMotor motor4  = null;

    public void runOpMode() {

        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        motor4 = hardwareMap.get(DcMotor.class, "motor4");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                motor1.setPower(1);
                motor2.setPower(1);
                motor3.setPower(1);
                motor4.setPower(1);
            } else if (gamepad1.b) {
                motor1.setPower(0);
                motor2.setPower(0);
                motor3.setPower(0);
                motor4.setPower(0);
            } else if (gamepad1.x) {
                motor1.setPower(-1);
                motor2.setPower(-1);
                motor3.setPower(-1);
                motor4.setPower(-1);
            }
        }
    }
}

