package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

//making it a TeleOp
@TeleOp(name = "ColorSensing", group = "Robot")
public class ColorSensorMechanics extends LinearOpMode {

    //initializing the color sensor as color
    ColorSensor color;

    //overriding runOpMode
    @Override
    //running the OpMode
    public void runOpMode() throws InterruptedException {

        //mapping color sensor
        color = hardwareMap.get(ColorSensor.class, "color");

        //saying op mode is ready to start
        telemetry.addData("Status: ", "Ready to run");
        telemetry.update();
        //waiting for start
        waitForStart();

        //while loop to run while the op mode is active
        while (opModeIsActive()) {
            //assigning the values seen by color sensor
            int r = color.red();
            int g = color.green();
            int b = color.blue();

            //showing values of the colors
            telemetry.addData("Red: ", r);
            telemetry.addData("Green: ", g);
            telemetry.addData("Blue: ", b);

            //showing what color is detected
            if ((r > g) && (r > b)) {
                telemetry.addData("Color seen: ", "Red");
            }
            if ((g > r) && (g > b)) {
                telemetry.addData("Color seen: ", "Green");
            }
            if ((b > r) && (b > g)) {
                telemetry.addData("Color seen: ", "Blue");
            }

            //updating the data to the driver station
            telemetry.update();

        }


    }
}
