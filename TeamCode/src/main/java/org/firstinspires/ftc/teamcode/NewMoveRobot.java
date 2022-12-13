/* Copyright (c) 2022 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class NewMoveRobot {

    //Declare OpMode members
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backRight;
    public DcMotor backLeft;
    public GyroClass gyro;

    final double COUNTS_PER_MOTOR_REV;
    final double WHEEL_DIAMETER_INCHES;
    final double COUNTS_PER_INCH;

    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double DRIVE_GEAR_REDUCTION  = 1.0 ;  // No External Gearing.

    // Requiring more accuracy (a smaller number) will often make the turn take longer to get into the final position.
    // Increase these numbers if the heading does not corrects strongly enough
    // Decrease these numbers if the heading does not settle on the correct value
    static final double  P_TURN_GAIN = 0.02;
    static final double  P_DRIVE_GAIN = 0.03;



    public NewMoveRobot(DcMotor frontRight, DcMotor frontLeft, DcMotor backRight, DcMotor backLeft, double COUNTS_PER_MOTOR_REV, double WHEEL_DIAMETER_INCHES){
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;

        //this.gyro = gyro;

        this.COUNTS_PER_MOTOR_REV = COUNTS_PER_MOTOR_REV;
        this.WHEEL_DIAMETER_INCHES = WHEEL_DIAMETER_INCHES;
        COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        encodeMode();

    }
    public void encodeMode() {
        //setting directions so its easier to turn motors
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        //setting the mode to drive the robot
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void straight(double speed, double distance){
        encoderDrive(speed, distance, distance, distance, distance);
    }

    public void sideways(double speed, double distance){
        encoderDrive(speed, -distance, distance, distance, -distance);
    }


    public void encoderDrive(double speed, double FR_Inches, double FL_Inches,
                             double BR_Inches, double BL_Inches) {
        //initializing variables
        int newFRTarget;
        int newFLTarget;
        int newBRTarget;
        int newBLTarget;

        //making sure this should actually be running

        //setting the target position
        newFRTarget = frontRight.getCurrentPosition() + (int) (FR_Inches * COUNTS_PER_INCH);
        newFLTarget = frontLeft.getCurrentPosition() + (int) (FL_Inches * COUNTS_PER_INCH);
        newBRTarget = backRight.getCurrentPosition() + (int) (BR_Inches * COUNTS_PER_INCH);
        newBLTarget = backLeft.getCurrentPosition() + (int) (BL_Inches * COUNTS_PER_INCH);

        frontRight.setTargetPosition(newFRTarget);
        frontLeft.setTargetPosition(newFLTarget);
        backRight.setTargetPosition(newBRTarget);
        backLeft.setTargetPosition(newBLTarget);

        //making the motor mode run to position
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //setting the power of the motors to get it to move
        frontRight.setPower(Math.abs(speed));
        frontLeft.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(speed));
        backLeft.setPower(Math.abs(speed));

        //making sure it does stop until it reaches
        while ((frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy())) {

        }

        //stopping all movement
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        //changing the motor mode *again*
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void encoderTurn(double speed, double angle) {
        //intializing variables
        int newFRTarget;
        int newFLTarget;
        int newBRTarget;
        int newBLTarget;

        //determining the amount the wheels need to move
        double frightInches = (Math.sin(angle) * 17.5);
        double fleftInches = -(Math.sin(angle) * 17.5);
        double brightInches = (Math.sin(angle) * 17.5);
        double bleftInches = -(Math.sin(angle) * 17.5);

        //setting target position
        newFRTarget = frontRight.getCurrentPosition() + (int) (frightInches * COUNTS_PER_INCH);
        newFLTarget = frontLeft.getCurrentPosition() + (int) (fleftInches * COUNTS_PER_INCH);
        newBRTarget = backRight.getCurrentPosition() + (int) (brightInches * COUNTS_PER_INCH);
        newBLTarget = backLeft.getCurrentPosition() + (int) (bleftInches * COUNTS_PER_INCH);

        frontRight.setTargetPosition(newFRTarget);
        frontLeft.setTargetPosition(newFLTarget);
        backRight.setTargetPosition(newBRTarget);
        backLeft.setTargetPosition(newBLTarget);

        //making the motor mode run to position
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //setting the power of the motors to get it to move
        frontRight.setPower(Math.abs(speed));
        frontLeft.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(speed));
        backLeft.setPower(Math.abs(speed));

        //making sure it does stop until it reaches
        while ((frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy())) {

        }

        //stopping all movement
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        //changing the motor mode *again*
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
