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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Mecanum Wheel OpMode that uses a gyro
 * All controls will move the robot relitive to your position (when you started the program)
 * Left Stick translates the robot
 * Right Stick rotates the robot
 */

@TeleOp(name="Auto Slide Test", group="Robot")

public class AutoSlideTest extends LinearOpMode {

    public DcMotor lineSlide;
    public CRServo servo;

    public static double SLIDE_SPEED = 1.0;

    @Override
    public void runOpMode() {
        lineSlide = hardwareMap.get(DcMotor.class, "lineSlide");
        servo = hardwareMap.get(CRServo.class, "armServo");

        PinchServo pinchServo = new PinchServo(servo);

        double[] positionHeights = {3.5, 13.5, 23.5, 33.5};
        LinearSlide linearSlide = new LinearSlide(lineSlide, positionHeights, 1120, 1.5); //AndyMark NeveRest 40:1

        waitForStart();
        while (opModeIsActive()) {

            if(gamepad1.dpad_down){
                linearSlide.moveToPosition(SLIDE_SPEED, 0);
            }

            if(gamepad1.dpad_left){
                linearSlide.moveToPosition(SLIDE_SPEED, 1);

            }
            if(gamepad1.dpad_up){
                linearSlide.moveToPosition(SLIDE_SPEED, 2);

            }
            if(gamepad1.dpad_right){
                linearSlide.moveToPosition(SLIDE_SPEED, 3);

            }

            if(gamepad1.b){
                pinchServo.pinchObject();
            }

            if(gamepad1.a){
                pinchServo.releaseObject();
            }

        }
    }
}


