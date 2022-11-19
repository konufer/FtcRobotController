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

import org.firstinspires.ftc.teamcode.GyroClass;
import org.firstinspires.ftc.teamcode.MoveRobot;

/**
 * Mecanum Wheel OpMode that uses a gyro
 * All controls will move the robot relitive to your position (when you started the program)
 * Left Stick translates the robot
 * Right Stick rotates the robot
 */

@TeleOp(name="Non-POV Movement", group="Robot")

public class NonPOVMovement extends LinearOpMode {

    static final double DAMPENER = 0.35;
    static final double TURN_DAMPENER = 0.8;

    public DcMotor frontRight  = null;
    public DcMotor frontLeft  = null;
    public DcMotor backLeft  = null;
    public DcMotor backRight  = null;

    public static BNO055IMU imu;

    @Override
    public void runOpMode() {
        // Map motors to varibles
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight"); // port 1
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft"); //  port 2
        backRight  = hardwareMap.get(DcMotor.class, "backRight"); // port 3

        //Motors on the left need to be reversed
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        //This initializes the gyro sensor within the expansion hub
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        GyroClass gyro = new GyroClass(imu);
        gyro.resetHeading();

        MoveRobot move = new MoveRobot(frontLeft, frontRight, backLeft, backRight, gyro);

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
            telemetry.addData(">", "Robot Heading = %4.0f", gyro.getRawHeading());
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {


            if (gamepad1.x){
                double robotHeading = gyro.getRawHeading();
                move.turnToHeading(TURN_DAMPENER, robotHeading - Math.PI/2);
                move.holdHeading(TURN_DAMPENER, robotHeading - Math.PI/2,0.5);
                continue;
            }



            if (gamepad1.a){
                double robotHeading = gyro.getRawHeading();
                move.turnToHeading(TURN_DAMPENER, robotHeading + Math.PI/2);
                move.holdHeading(TURN_DAMPENER, robotHeading + Math.PI/2,0.5);
                continue;
            }




            //The Mecanum Wheel math
            double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;

            double currentAngle = gyro.getRawHeading();
            double inputAngle = robotAngle - currentAngle;

            double rightX = TURN_DAMPENER * gamepad1.right_stick_x;

            double v1 = r * Math.cos(inputAngle) + rightX;
            double v2 = r * Math.sin(inputAngle) - rightX;
            double v3 = r * Math.sin(inputAngle) + rightX;
            double v4 = r * Math.cos(inputAngle) - rightX;

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

            frontLeft.setPower(DAMPENER * v1Final);
            frontRight.setPower(DAMPENER * v2Final);
            backLeft.setPower(DAMPENER * v3Final);
            backRight.setPower(DAMPENER * v4Final);
        }
    }
}


