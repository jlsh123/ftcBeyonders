//this is an example code for teleOp (note: it needs a hwMap to work) 
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name = "teleOp", group = "final")
public class teleOp extends LinearOpMode {

    double y_coor;
    double x_coor;
    double rotate;
    double LFSpeed;
    double LBSpeed;
    double RFSpeed;
    double RBSpeed;
    hwMap robot = new hwMap();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        telemetry.addData("calibrating", "initialized");
        telemetry.update();

        waitForStart();// runs after pressing start

        while (opModeIsActive()) {
            x_coor = gamepad1.right_stick_x;
            y_coor = -gamepad1.right_stick_y;
            rotate = gamepad1.left_stick_x;
            if (gamepad1.right_bumper){
                //shifting in a lower speed
                LFSpeed = (-y_coor - x_coor - rotate) * 0.3;
                LBSpeed = (-y_coor + x_coor - rotate) * 0.3;
                RFSpeed = (y_coor - x_coor - rotate) * 0.3;
                RBSpeed = (y_coor + x_coor - rotate) * 0.3;
            }else {
                //shifting in normal speed
                LFSpeed = Range.clip((-y_coor - x_coor - rotate),-1.0,1.0);
                LBSpeed = Range.clip((-y_coor + x_coor - rotate),-1.0,1.0);
                RFSpeed = Range.clip((y_coor - x_coor - rotate),-1.0,1.0);
                RBSpeed = Range.clip((y_coor + x_coor - rotate),-1.0,1.0);
            }
            //set motor power
            robot.LeftFMotor.setPower(LFSpeed);
            robot.LeftBMotor.setPower(LBSpeed);
            robot.RightFMotor.setPower(RFSpeed);
            robot.RightBMotor.setPower(RBSpeed);


            //show data
            telemetry.addData("power (LF)",robot.LeftFMotor.getPower());
            telemetry.addData("power (RF)",robot.RightFMotor.getPower());
            telemetry.addData("power (RB)",robot.RightBMotor.getPower());
            telemetry.addData("power (LB)",robot.LeftBMotor.getPower());

            telemetry.addData("pos (LF)",robot.LeftFMotor.getCurrentPosition());
            telemetry.addData("pos (RF)",robot.RightFMotor.getCurrentPosition());
            telemetry.addData("pos (RB)",robot.RightBMotor.getCurrentPosition());
            telemetry.addData("pos (LB)",robot.LeftBMotor.getCurrentPosition());

            telemetry.addData("Robot Status","updated");
            
            telemetry.update();//must need in order to update what is shown on the phone
        }
    }
}
