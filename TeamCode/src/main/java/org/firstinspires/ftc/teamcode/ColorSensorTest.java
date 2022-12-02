package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//making this an autonomous code, not a TeleOP
@Autonomous(name = "ColorSensorTest", group = "Robot")
public class ColorSensorTest extends LinearOpMode {

    //prolly initializing color and ColorSensor is the type
    ColorSensor color;



    //im going to declare this now cuz might have to use it eventually (which actually is used!!)
    private ElapsedTime runtime = new ElapsedTime();



    //idk what this is - runOpMode is a predefined method in the LinearOpMode Class. This overrides the exsisting method
    @Override
    //the part that actually runs stuff i think
    public void runOpMode() {

        //mapping the color sensor and declaring color to it
        color = hardwareMap.get(ColorSensor.class, "color");



        //saying that it's ready to run
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        //waiting for the run button to be hit
        waitForStart();

        //resetting runtime
        runtime.reset();

        while (opModeIsActive()){

            //trying to move with encoders :/

        //the exact inches are off but the overall idea here is to move forward to the cone
        //   and then strafe to the right because i was planning on putting the color sensor in the
        //   front left corner so it doesn't come close to causing any issues with the picker upper

        //       (also, there is a calculation to figure out how many inches to move based on the angle you want to go
        //           you take the sin of the angle, so sin(90) = 1. and then you multiply by i think the width of the robot
        //           so for this, if you wanted to turn 90 degrees, you would be moving roughly 17 inches
        //           but i'm bypassing the annoying process of turning and then turning back by just strafing into position)


        /* NOT USING THIS (just a hardcoded part in case we cant get the encoders to work,
               but this still doesn't have the right time values)
        //move forward (w/o encoder)
        while (runtime.seconds() < 1.5){
            frontRight.setPower(0.7);
            frontLeft.setPower(0.7);
            backRight.setPower(0.7);
            backLeft.setPower(0.7);
        }
        //reset runtime again
        runtime.reset();
        //move right
        while (runtime.seconds() < 0.4){
            frontRight.setPower(-0.6);
            frontLeft.setPower(0.6);
            backRight.setPower(0.6);
            backLeft.setPower(-0.6);
        }
         */


        //shows the color values that color sensor can see
        //so in theory, this would show the color values that the sensor sees at the time it was read
        //   this can be used to see what should be in those if statments below cuz idk where 20 came from
        telemetry.addData("red", color.red());
        telemetry.addData("green", color.blue());
        telemetry.addData("blue", color.green());
        telemetry.update();

        //booleans to choose a path
        boolean r = false;
        boolean b = false;
        boolean g = false;

        //figuring out what color is in front of it and setting the correct boolean to true
        //i am completly unsure what unit it is measured in so idk what 20 means??
        if (color.red() > 20) {
            r = true;
        } else if (color.blue() > 20){
            b = true;
        } else if (color.green() > 20){
            g = true;
        }

        //from here, do a specific parking based on what color was determined to be in front (above code)
        //for right now, im just printing out in the app what path it chose to see if it got the right one
        if (r){
            telemetry.addLine("The color detected was red.");
        } else if (g) {
            telemetry.addLine("The color detected was green.");
        } else if (b) {
            telemetry.addLine("The color detected was blue.");
        }

    }


    }



    }
