package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="ManualArmTest", group="Robot")

public class ManualArmTest extends LinearOpMode {

    static final double BASE_DAMPENER = 0.05 ;
    static final double GEAR_DAMPENER = 0.05;
    public DcMotor baseMotor  = null;
    public DcMotor gearMotor  = null;


    public DcMotor frontRight  = null;
    public DcMotor frontLeft  = null;
    public DcMotor backLeft  = null;
    public DcMotor backRight  = null;

    public String bothMotorsLocked(){
        int baseTarget = baseMotor.getCurrentPosition() + 5;
        baseMotor.setTargetPosition(baseTarget);
        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseMotor.setPower(1.0);

        int gearTarget = gearMotor.getCurrentPosition() + 5;
        gearMotor.setTargetPosition(gearTarget);
        gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        gearMotor.setPower(1.0);

        while (true) {
            telemetry.addData("Base/Gear Targets: ",  "%7d, %7d", baseTarget, gearTarget);
            telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
            telemetry.update();

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
                telemetry.addData("Base/Gear Targets: ",  "%7d, 7d", 0,0);
                telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
                telemetry.update();

                baseMotor.setPower(-gamepad1.left_stick_y * BASE_DAMPENER);

                if (baseMotor.getPower() == 0){
                    break;
                }
            }
        }

        if (unlockedMotor == "gear"){
            while (true) {
                telemetry.addData("Base/Gear Targets: ",  "%7d, 7d", 0,0);
                telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
                telemetry.update();

                gearMotor.setPower(gamepad1.right_stick_y * GEAR_DAMPENER);

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

        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight"); // port 1
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft"); //  port 2
        backRight  = hardwareMap.get(DcMotor.class, "backRight"); // port 3

        baseMotor = hardwareMap.get(DcMotor.class, "baseMotor");
        gearMotor = hardwareMap.get(DcMotor.class, "gearMotor");

        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            String unlockedMotor = bothMotorsLocked(); // method returns when one of the motors is unlocked

            oneMotorUnlocked(unlockedMotor);
        }
    }
}


//    public void bothMotorsLocked(){
//        if (initialBasePosition == -9000 && initialGearPosition == -9000){
//            initialBasePosition = baseMotor.getCurrentPosition();
//            initialGearPosition = gearMotor.getCurrentPosition();
//        }
//
//        int baseTarget = baseMotor.getCurrentPosition() + 10;
//        baseMotor.setTargetPosition(baseTarget);
//        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        baseMotor.setPower(1.0);
//
//        int gearTarget = gearMotor.getCurrentPosition() + 10;
//        gearMotor.setTargetPosition(gearTarget);
//        gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        gearMotor.setPower(1.0);
//
//
//        while (true) {
//
//            telemetry.addData("Base/Gear Targets: ",  "%7d, %7d", baseTarget, gearTarget);
//            telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
//            telemetry.update();
//
//            if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0 || gamepad1.right_stick_x != 0){
//                driveRobot();
//            }
//
//            if (gamepad1.a){
//                moveArm(initialBasePosition + 10, initialBasePosition + 120, BASE_DAMPENER, GEAR_DAMPENER);
//
//            }
//            if (gamepad1.x){
//                moveArm(initialBasePosition + 20, initialBasePosition - 100, BASE_DAMPENER, 0.6);
//            }
//            if (gamepad1.b){
//                unlockMotor(baseMotor);
//                unlockMotor(gearMotor);
//            }
//        }
//    }


//    public void moveArm(int baseTarget, int gearTarget, double baseSpeed, double gearSpeed) {
//        boolean baseTargetReached = false;
//        boolean gearTargetReached = false;
//
//        baseMotor.setTargetPosition(baseMotor.getCurrentPosition() + 10);
//        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        baseMotor.setPower(baseSpeed);
//
//        gearMotor.setTargetPosition(gearMotor.getCurrentPosition() + 10);
//        gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        gearMotor.setPower(gearSpeed);
//
//        while (true) {
//            telemetry.addData("Base/Gear Targets: ",  "%7d, %7d", baseTarget, gearTarget);
//            telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
//            telemetry.update();
//
//
//            if (baseMotor.getCurrentPosition() != baseTarget && !baseTargetReached) {
//                baseMotor.setTargetPosition(baseTarget);
//                baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//                while (baseMotor.isBusy()) {
//                }
//                baseTargetReached = true;
//                baseMotor.setTargetPosition(baseMotor.getCurrentPosition() + 10);
//                baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                baseMotor.setPower(1.0);
//            }
//
//            if (gearMotor.getCurrentPosition() != gearTarget && !gearTargetReached) {
//                gearMotor.setTargetPosition(gearTarget);
//                gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//                while (gearMotor.isBusy()) {
//                }
//                gearTargetReached = true;
//                gearMotor.setTargetPosition(gearMotor.getCurrentPosition() + 10);
//                gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                gearMotor.setPower(1.0);
//            }
//
//            if (baseTargetReached && gearTargetReached){
//                break;
//            }
//        }
//    }
