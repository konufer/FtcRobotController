///* Copyright (c) 2017 FIRST. All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without modification,
// * are permitted (subject to the limitations in the disclaimer below) provided that
// * the following conditions are met:
// *
// * Redistributions of source code must retain the above copyright notice, this list
// * of conditions and the following disclaimer.
// *
// * Redistributions in binary form must reproduce the above copyright notice, this
// * list of conditions and the following disclaimer in the documentation and/or
// * other materials provided with the distribution.
// *
// * Neither the name of FIRST nor the names of its contributors may be used to endorse or
// * promote products derived from this software without specific prior written permission.
// *
// * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
// * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
// * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
// * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//@Autonomous(name="Robot: Auto Park Right", group="Robot")
//
//public class RobotAutoParkRight extends LinearOpMode {
//
//    // Map motors to varibles
//    public DcMotor frontRight  = null; // port 1
//    public DcMotor frontLeft  = null;
//    public DcMotor backLeft  = null;
//    public DcMotor backRight  = null;
//
//    private ElapsedTime     runtime = new ElapsedTime();
//
//    double time = 2.85;
//
//    static final double     FRONT_LEFT_SPEED = 0.3;  //Positive
//    static final double     FRONT_RIGHT_SPEED = -0.3; // Negative
//    static final double     BACK_LEFT_SPEED = -0.3; // Negative
//    static final double     BACK_RIGHT_SPEED = 0.3; // Positive
//
//    @Override
//    public void runOpMode() {
//
//        // Map motors to varibles
//        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
//        frontRight  = hardwareMap.get(DcMotor.class, "frontRight"); // port 1
//        backLeft  = hardwareMap.get(DcMotor.class, "backLeft"); //  port 2
//        backRight  = hardwareMap.get(DcMotor.class, "backRight"); // port 4
//
//        frontLeft.setDirection(DcMotor.Direction.REVERSE);
//        backLeft.setDirection(DcMotor.Direction.REVERSE);
//
//        frontRight.setDirection(DcMotor.Direction.FORWARD);
//        backRight.setDirection(DcMotor.Direction.FORWARD);
//
//        // Send telemetry message to signify robot waiting
//        telemetry.addData(">", "Robot Ready.  Press Play.");    //
//        telemetry.update();
//
//        waitForStart();
//
//        //Run Robot
//        frontLeft.setPower(FRONT_LEFT_SPEED);
//        frontRight.setPower(FRONT_RIGHT_SPEED);
//        backLeft.setPower(BACK_LEFT_SPEED);
//        backRight.setPower(BACK_RIGHT_SPEED);
//
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//
//        //Stop Robot
//        frontLeft.setPower(0);
//        frontRight.setPower(0);
//        backLeft.setPower(0);
//        backRight.setPower(0);
//
//        telemetry.addData("Path", "Complete");
//        telemetry.update();
//        sleep(1000);
//    }
//}