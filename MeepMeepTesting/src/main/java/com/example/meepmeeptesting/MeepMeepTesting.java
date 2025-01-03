package com.example.meepmeeptesting;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        double xInitial = -44; //i have Initial variables because in real life i think the robot will start from zero
        double yInitial = -64;// so use Initial variables haha

        double xStart = 0;
        double yStart = -64;



        //Basket Auto, pretty good, high basket auto is currently based on this
        RoadRunnerBotEntity basketCalvin = new DefaultBotBuilder(meepMeep)
                .setDimensions(12, 12)
                .setStartPose(new Pose2d(xInitial, yInitial, PI/2))
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(xInitial, yInitial, PI/2))
                        .splineToLinearHeading(new Pose2d(xInitial - 17, yInitial + 4, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial - 4, yInitial + 30, Math.toRadians(90)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(xInitial - 17, yInitial + 4, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial - 14, yInitial + 30, Math.toRadians(90)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(xInitial - 17, yInitial + 4, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial - 20, yInitial + 30, Math.toRadians(120)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(xInitial - 17, yInitial + 4, Math.toRadians(45)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xInitial + 23, yInitial + 64, Math.toRadians(540)), Math.toRadians(45))
                        .build());

        //Specimen auto, very bad, but I want to give Uchida and Zhang something to see and base their ideas off of
        //You can see how splines are not always the best
        //also how does your path translate in the real world?
        //good luck!

        RoadRunnerBotEntity speciCalvin = new DefaultBotBuilder(meepMeep)
                .setDimensions(12, 15)
                .setStartPose(new Pose2d(xStart, yStart, 3*PI/2))
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(xStart, yStart, 3*PI/2))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 25, yStart + 25, Math.toRadians(70)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 35, yStart + 40, Math.toRadians(0)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 35, yStart + 35, Math.toRadians(280)), Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(xStart + 45, yStart + 40, Math.toRadians(0)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 45, yStart + 35, Math.toRadians(280)), Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(xStart + 55, yStart + 40, Math.toRadians(0)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 55, yStart + 35, Math.toRadians(270)), Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 10, Math.toRadians(90)), Math.toRadians(300))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .build());

        //specimen auto! yay
        RoadRunnerBotEntity specimenCalvin = new DefaultBotBuilder(meepMeep)
                .setDimensions(12, 15)
                .setStartPose(new Pose2d(xStart, yStart, 3*PI/2))
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(xStart, yStart, 3*PI/2))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 32, yStart + 26, Math.toRadians(270)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                        //CYNTHIA I FOUND SOMETHING COOL
                        .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(270))
                        //.splineToLinearHeading(new Pose2d(xStart, yStart + 15, Math.toRadians(270)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(270))
                        //.splineToLinearHeading(new Pose2d(xStart, yStart + 15, Math.toRadians(270)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(270))
                        //.splineToLinearHeading(new Pose2d(xStart, yStart + 15, Math.toRadians(270)), Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(270))

                        .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(basketCalvin)
                //.addEntity(speciCalvin) //you can comment out addentity lines if you dont wanna see them simultaneuosly
                .addEntity(specimenCalvin)
                .start();
    }
}
