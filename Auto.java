//this is an example code for auto mode (note: it needs a hwMap to work) 
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;



@Autonomous(name = "backup_autoL1", group = "final")
public class Auto extends LinearOpMode {
    //define variables here
    hwMap robot = new hwMap();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        waitForStart();
        //auto code here
        robot.EncoderForward_CM(10,1);
    }
}
