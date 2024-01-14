/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Mecanum Wheel OpMode that uses a gyro
 * All controls will move the robot relitive to your position (when you started the program)
 * Left Stick translates the robot
 * Right Stick rotates the robot
 */

@TeleOp(name="POV Movement", group="Robot")

public class POVMovement extends LinearOpMode {

    static final double DRIVE_DAMPENER = 0.75;
    static final double TURN_DAMPENER = 0.8;
    static final double BASE_DAMPENER = 0.2;
    static final double GEAR_DAMPENER = 0.2;

    public DcMotor frontRight  = null;
    public DcMotor frontLeft  = null;
    public DcMotor backLeft  = null;
    public DcMotor backRight  = null;
    public DcMotor baseMotor  = null;
    public DcMotor gearMotor  = null;

/*    public int initialBasePosition = -9000;
    public int initialGearPosition = -9000;*/

    public static BNO055IMU imu;


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

            if (gamepad1.a) {
                unlockMotor(baseMotor);
                unlockMotor(gearMotor);
                return "drive";
            }
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

    public void driveRobot(){

        while (true){
            //The Mecanum Wheel math
            double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI/4;

            double rightX = TURN_DAMPENER * gamepad1.right_stick_x;

            double v1 = r * Math.cos(robotAngle) + rightX;
            double v2 = r * Math.sin(robotAngle) - rightX;
            double v3 = r * Math.sin(robotAngle) + rightX;
            double v4 = r * Math.cos(robotAngle) - rightX;

            //Square dampening
            double v1Final = v1 * v1;
            if (v1 < 0) {
                v1Final = -1 * v1Final;
            }

            double v2Final = v2 * v2;
            if (v2 < 0) {
                v2Final = -1 * v2Final;
            }

            double v3Final = v3 * v3;
            if (v3 < 0) {
                v3Final = -1 * v3Final;
            }

            double v4Final = v4 * v4;
            if (v4 < 0) {
                v4Final = -1 * v4Final;
            }

            frontLeft.setPower(DRIVE_DAMPENER * v1Final);
            frontRight.setPower(DRIVE_DAMPENER * v2Final);
            backLeft.setPower(DRIVE_DAMPENER * v3Final);
            backRight.setPower(DRIVE_DAMPENER * v4Final);

            if (gamepad1.a){
                break;
            }
        }
    }

    public void unlockMotor(DcMotor motor){
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void runOpMode() {
        // Map motors to varibles
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight"); // port 1
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft"); //  port 2
        backRight  = hardwareMap.get(DcMotor.class, "backRight"); // port 3

        baseMotor = hardwareMap.get(DcMotor.class, "baseMotor"); // expansion hub port 0
        gearMotor = hardwareMap.get(DcMotor.class, "gearMotor"); // expansion hub port 1

        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Motors on the left need to be reversed
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        // backRight.setDirection(DcMotor.Direction.REVERSE);

        //This initializes the gyro sensor within the expansion hub
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        GyroClass gyro = new GyroClass(imu);
        gyro.resetHeading();

        MoveRobot move = new MoveRobot(frontLeft, frontRight, backLeft, backRight, gyro);
        ElapsedTime runtime = new ElapsedTime();

   /**     // Ensure the robot is stationary.  Reset the encoders and set the motors to BRAKE mode
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setMode(DcMotor.DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);   **/

        //For testing purposes
        while (opModeInInit()) {
//            telemetry.addData(">", "Robot Heading = %4.0f", gyro.getRawHeading());
//            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            String unlockedMotor = bothMotorsLocked(); // method returns when one of the motors is unlocked

//            if (driveMode){
//                driveRobot();
//                continue;
//            }

            if (unlockedMotor.equals("drive")){
                driveRobot();
                continue;
            }
            oneMotorUnlocked(unlockedMotor);


//            bothMotorsLocked();
//
//            while (!gamepad1.b){
//                driveRobot();
//            }
        }

    }
}


