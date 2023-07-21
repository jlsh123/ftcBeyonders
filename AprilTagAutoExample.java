package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous(name = "AprilTagAutoExample", group = "final")

public class AprilTagAutoExample extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    hwMap robot = new hwMap();

    static final double FEET_PER_METER = 3.28084;

    double fx = 578.272;//useless variable for us
    double fy = 578.272;//useless variable for us
    double cx = 402.145;//useless variable for us
    double cy = 221.506;//useless variable for us
    
    int location = 0;//aprilTagInfo (the goal of the detection)
    
    double tagsize = 0.166;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        waitForStart();
        robot.SlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.elapsedTime.reset();
        boolean tagNotFound = true;
        telemetry.addLine("nothing detected :(");
        telemetry.update();
        while (tagNotFound &&robot.elapsedTime.seconds()<5) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 1 || tag.id == 2 || tag.id == 3) {
                    tagOfInterest = tag;
                    location = tag.id;
                    tagNotFound = false;
                    break;
                }
            }
            sleep(20);
        }
        //show results
        if (location != 0){
            telemetry.addData("Target found :) , location", location);
        }else{
            telemetry.addLine("nothing detected after 5 seconds :(");
        }
        telemetry.update();
        //auto code here (use the location variable here)



    }

}
