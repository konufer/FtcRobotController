
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group = "Robot")
public class Autonomous extends LinearOpMode {

    public Servo servo;

    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor backLeft = null;

    public DcMotor baseMotor = null;
    public DcMotor gearMotor = null;

    static final double DRIVE_SPEED = 0.4;
    static final double TURN_SPEED = 0.6;
    static final double SLIDE_SPEED = 1.0;


    public void moveArm(int baseTarget, int gearTarget) {
        boolean baseTargetReached = false;
        boolean gearTargetReached = false;

        baseMotor.setTargetPosition(baseMotor.getCurrentPosition() + 10);
        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseMotor.setPower(1.0);

        gearMotor.setTargetPosition(gearMotor.getCurrentPosition() + 10);
        gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        gearMotor.setPower(1.0);

        while (true) {
        /*  telemetry.addData("Base/Gear Targets: ",  "%7d, %7d", baseTarget, gearTarget);
            telemetry.addData("Currently at: ",  "%7d, %7d", baseMotor.getCurrentPosition(), gearMotor.getCurrentPosition());
            telemetry.update();*/

            if (baseMotor.getCurrentPosition() != baseTarget && !baseTargetReached) {
                baseMotor.setTargetPosition(baseTarget);
                baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                while (baseMotor.isBusy()) {
                }
                baseTargetReached = true;
                baseMotor.setTargetPosition(baseMotor.getCurrentPosition() + 10);
                baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                baseMotor.setPower(1.0);
            }

            if (gearMotor.getCurrentPosition() != gearTarget && !gearTargetReached) {
                gearMotor.setTargetPosition(gearTarget);
                gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                while (gearMotor.isBusy()) {
                }
                gearTargetReached = true;
                gearMotor.setTargetPosition(gearMotor.getCurrentPosition() + 10);
                gearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                gearMotor.setPower(1.0);
            }

            if (baseTargetReached && gearTargetReached){
                break;
            }
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {



        // Map motors to varibles
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft"); // port 0
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight"); // port 1
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft"); //  port 2
        backRight  = hardwareMap.get(DcMotor.class, "backRight"); // port 3

        baseMotor = hardwareMap.get(DcMotor.class, "baseMotor"); // expansion hub port 0
        gearMotor = hardwareMap.get(DcMotor.class, "gearMotor"); // expansion hub port 1

        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Creating Class instances
        NewMoveRobot drive = new NewMoveRobot(frontLeft, frontRight, backLeft, backRight, 560, 2.95276); //REV HD Hex motor
        //PinchServo arm = new PinchServo(servo);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();




double total = 44;
        double delta = 16;
        double offset = 1.5;
        drive.straight(DRIVE_SPEED, delta);
        drive.sideways(DRIVE_SPEED, -offset);


        drive.sideways(DRIVE_SPEED, offset);
        drive.straight(DRIVE_SPEED, total-delta);




        drive.sideways(DRIVE_SPEED, 11.0);

        drive.straight(DRIVE_SPEED, 6.5);

        moveArm(400,400);

        drive.straight(DRIVE_SPEED, -6.5);

        drive.sideways(DRIVE_SPEED, -11.0);

    }

}


