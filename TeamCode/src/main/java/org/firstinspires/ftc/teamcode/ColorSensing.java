package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorSensing {
    public static ColorSensor color;

    public int red;
    public int green;
    public int blue;

    public ColorSensing(ColorSensor color){
        this.color = color;
    }

    public int[] getColors() {
        red = color.red();
        green = color.green();
        blue = color.blue();

       int[] colorValues = {red, green, blue};
       return colorValues;
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
