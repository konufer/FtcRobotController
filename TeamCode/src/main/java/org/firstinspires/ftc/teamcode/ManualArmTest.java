package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="ManualArmTest", group="Robot")

public class ManualArmTest extends LinearOpMode {

    static final double DAMPENER = .2;
    public DcMotor baseMotor  = null;
    public DcMotor gearMotor  = null;

    public void lockPosition(DcMotor motor){
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (motor.isBusy()) {
            motor.setPower(0.5);
        }


    }

    public void unlockPosition(DcMotor motor){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void runOpMode() {

        baseMotor = hardwareMap.get(DcMotor.class, "baseMotor");
        gearMotor = hardwareMap.get(DcMotor.class, "gearMotor");

        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
           baseMotor.setPower(gamepad1.left_stick_y * 0.6);
           gearMotor.setPower(-gamepad1.right_stick_y * 0.3);

           if (gamepad1.a){
               lockPosition(baseMotor);
           }
            if (gamepad1.x){
                unlockPosition(baseMotor);
            }
        }
    }
}
