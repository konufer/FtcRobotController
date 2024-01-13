package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="ManualArmTest", group="Robot")

public class ManualArmTest extends LinearOpMode {

    static final double BASE_DAMPENER = 0.8;
    static final double GEAR_DAMPENER = 0.6;
    public DcMotor baseMotor  = null;
    public DcMotor gearMotor  = null;

    public String bothMotorsLocked(){
        int baseTarget = baseMotor.getCurrentPosition() + 10;
        baseMotor.setTargetPosition(baseTarget);
        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseMotor.setPower(1.0);

        int gearTarget = gearMotor.getCurrentPosition() + 10;
        gearMotor.setTargetPosition(gearTarget);
        gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        gearMotor.setPower(1.0);

        while (true) {
/*            telemetry.addData("Base/Gear Targets: ",  "%7d, %7d", baseTarget, gearTarget);
            telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
            telemetry.update();*/

            if (gamepad1.left_stick_y != 0){
                unlockMotor(baseMotor);
                return "base";
            }

            if (gamepad1.right_stick_y != 0){
                unlockMotor(gearMotor);
                return "gear";
            }

//            if (gamepad1.a) {
//                return "drive";
//            }
        }
    }

    public void oneMotorUnlocked(String unlockedMotor){
        if (unlockedMotor == "base"){
            while (true) {
/*                telemetry.addData("Gear Motor Target: ",  "%7d", gearMotor);
                telemetry.addData("Currently at: ",  "%7d", gearMotor.getCurrentPosition());
                telemetry.update();*/

                baseMotor.setPower(gamepad1.left_stick_y * BASE_DAMPENER);

                if (baseMotor.getPower() == 0){
                    break;
                }
            }
        }

        if (unlockedMotor == "gear"){
            while (true) {
/*                telemetry.addData("Base Motor Target: ",  " %7d", baseMotor);
                telemetry.addData("Currently at",  " at %7d", baseMotor.getCurrentPosition());
                telemetry.update();*/

                gearMotor.setPower(-gamepad1.right_stick_y * GEAR_DAMPENER);

                if (gearMotor.getPower() == 0){
                    break;
                }
            }
        }
    }

//    public void driveRobot(){
//
//    }
    public void unlockMotor(DcMotor motor){
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void runOpMode() {

        baseMotor = hardwareMap.get(DcMotor.class, "baseMotor");
        gearMotor = hardwareMap.get(DcMotor.class, "gearMotor");

        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            String unlockedMotor = bothMotorsLocked(); // method returns when one of the motors is unlocked

//            if (unlockedMotor.equals("drive")){
//                driveRobot();
//            }

            oneMotorUnlocked(unlockedMotor);
        }
    }
}
