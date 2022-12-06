package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColourSensor {
    public static ColorSensor color;

    public int red;
    public int green;
    public int blue;

    public ColourSensor(ColorSensor color){
        this.color = color;
    }

    public void getColors() {
        red = color.red();
        green = color.green();
        blue = color.blue();

        telemetry.addData("Red", red);
        telemetry.addData("Green", green);
        telemetry.addData("Blue", blue);
        telemetry.update();
    }

    public String colorPath() {
        if ((red > green) && (red > blue)) {
            return "red";
        }
        if ((green > red) && (green > blue)) {
            return "green";
        }
        if ((blue > red) && (blue > green)) {
            return "blue";
        }
        return null;
    }

}
