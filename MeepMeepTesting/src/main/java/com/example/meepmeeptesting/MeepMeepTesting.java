package com.example.meepmeeptesting;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        double xInitial = -44;
        double yIntitial = -64;



        RoadRunnerBotEntity basketCalvin = new DefaultBotBuilder(meepMeep)
                .setDimensions(12, 15)
                .setStartPose(new Pose2d(xInitial, yIntitial, PI/2))
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(xInitial, yIntitial, PI/2))
                        .splineToLinearHeading(new Pose2d(xInitial - 12, yIntitial + 12, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial - 4, yIntitial + 30, Math.toRadians(90)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(xInitial - 12, yIntitial + 12, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial - 14, yIntitial + 30, Math.toRadians(90)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(xInitial - 12, yIntitial + 12, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial - 20, yIntitial + 30, Math.toRadians(120)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(xInitial - 12, yIntitial + 12, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial + 23, yIntitial + 64, Math.toRadians(540)), Math.toRadians(45))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(basketCalvin)
                .start();
    }
}
