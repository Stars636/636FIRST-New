package com.example.meepmeeptesting;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        double xInitial = -45+45; //i have Initial variables because in real life i think the robot will start from zero
        double yInitial = -64+64;// so use Initial variables haha

        double xStart = 0;
        double yStart = -64;

        double xBegin = 0;
        double yBegin = -64;
        double offset = 15;

        //double fraudOffset = 20;
        //double xStartfraud = 0;
        //double yStartfraud = 64;

        Pose2d scorePose = new Pose2d(xInitial - 17, yInitial + 4, Math.toRadians(45));

        RoadRunnerBotEntity basketBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .build();
        RoadRunnerBotEntity cyntakaBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .build();
        RoadRunnerBotEntity turnBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .build();

        RoadRunnerBotEntity specimenBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        RoadRunnerBotEntity specimenAndBasket = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d startPose = new Pose2d(0, 0, 0);
        Pose2d scorePosee = new Pose2d(xInitial + 12, yInitial + 12, Math.toRadians(-45));
        Pose2d scorePoseee = new Pose2d(xInitial + 8.5, yInitial + 15, Math.toRadians(-45)); //(9, 15)


        turnBot.runAction(basketBot.getDrive().actionBuilder(new Pose2d(xInitial, xInitial, 0))
                .splineToLinearHeading(scorePosee, Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(xInitial + 10, yInitial + 4, Math.toRadians(0)), Math.toRadians(0))

                .splineToLinearHeading(scorePosee, Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(xInitial + 10, yInitial + 10, Math.toRadians(0)), Math.toRadians(0))

                .splineToLinearHeading(scorePosee, Math.toRadians(0))

                .splineToLinearHeading(new Pose2d(xInitial + 9, yInitial + 20, Math.toRadians(30)), Math.toRadians(0))

                .splineToLinearHeading(scorePosee, Math.toRadians(0))
                //.splineToLinearHeading(new Pose2d(xInitial + 8, yInitial + 23, Math.toRadians(0)), Math.toRadians(0))
                .build());

        basketBot.runAction(basketBot.getDrive().actionBuilder(new Pose2d(xInitial, yInitial, PI/2))
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xInitial - 4, yInitial + 20, Math.toRadians(90)), Math.toRadians(45))
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xInitial - 14, yInitial + 20, Math.toRadians(90)), Math.toRadians(45))
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xInitial - 20, yInitial + 20, Math.toRadians(110)), Math.toRadians(45))
                .splineToLinearHeading(scorePose, Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xInitial + 23, yInitial + 64, Math.toRadians(0)), Math.toRadians(50))
                .splineToLinearHeading(scorePose, Math.toRadians(50))
                .splineToLinearHeading(new Pose2d(xInitial + 23, yInitial + 56, Math.toRadians(0)), Math.toRadians(50))
                .splineToLinearHeading(scorePose, Math.toRadians(50))
                .build());

        double fraudOffset = 12.5;
        cyntakaBot.runAction(cyntakaBot.getDrive().actionBuilder(new Pose2d(8.5, 15, Math.toRadians(-45)))
                .splineToLinearHeading(scorePoseee, Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 10, Math.toRadians(0)), Math.toRadians(0))
                .splineToLinearHeading(scorePoseee, Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(xInitial + fraudOffset, yInitial + 16.85, Math.toRadians(0)), Math.toRadians(0))
                .splineToLinearHeading(scorePoseee, Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(xInitial + 15.5, yInitial + 17, Math.toRadians(28)), Math.toRadians(0))
                .splineToLinearHeading(scorePoseee, Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(xInitial + 64, yInitial, Math.toRadians(-90)), Math.toRadians(130))//turning towards submersibl
                .splineToLinearHeading(new Pose2d(xInitial + 64, yInitial - 23, Math.toRadians(90)), Math.toRadians(90))
                .build());

        specimenAndBasket.runAction(specimenAndBasket.getDrive().actionBuilder(new Pose2d(xBegin, yBegin, 3*PI/2))
                .splineToLinearHeading(new Pose2d(xBegin, yBegin + 25, Math.toRadians(270)), Math.toRadians(270)) //moves to score specimen
                //score specimen
                .splineToLinearHeading(new Pose2d(xBegin - 48, yBegin + 16, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xBegin + offset, yBegin + 10, Math.toRadians(0)), Math.toRadians(0))


                .build());

        specimenBot.runAction(basketBot.getDrive().actionBuilder(new Pose2d(xStart, yStart, 3*PI/2))
                .splineToLinearHeading(new Pose2d(xStart, yStart + 25, Math.toRadians(270)), Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(xStart + 32, yStart + 26, Math.toRadians(270)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 40, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 48, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 10, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 58, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 50, Math.toRadians(90)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart + 64, yStart + 10, Math.toRadians(90)), Math.toRadians(90))
                //CYNTHIA I FOUND SOMETHING COOL
                .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(90))
                //.splineToLinearHeading(new Pose2d(xStart, yStart + 15, Math.toRadians(270)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(90))
                //.splineToLinearHeading(new Pose2d(xStart, yStart + 15, Math.toRadians(270)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(90))
                //.splineToLinearHeading(new Pose2d(xStart, yStart + 15, Math.toRadians(270)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(xStart, yStart + 34, Math.toRadians(270)), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(xStart + 38, yStart + 2, Math.toRadians(90)), Math.toRadians(90))

                .build());






        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(basketBot)
                //.addEntity(specimenBot)
                //.addEntity(specimenAndBasket)
                //.addEntity(turnBot)
                //.addEntity(myBot)
                .addEntity(cyntakaBot)
                .start();
    }
}
//OLD CODE

 /*MeepMeep meepMeep = new MeepMeep(600);
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
                .start();*/



//252 auto code just to look at

// RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                .setDimensions(12, 15)
//                .setStartPose(new Pose2d(-63, 6, PI))
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(60, 60, Math.toRadians(600), Math.toRadians(380), 15)
//                .build();
//        DriveShim drive = myBot.getDrive();
//
//
//        //to deposit first specimen
//        TrajectoryActionBuilder a1 = drive.actionBuilder(new Pose2d(0, 0, 0)).setTangent(PI)
//                .splineTo(new Vector2d(-26.6, -5), PI);
//
//        //first sweep
//
//        TrajectoryActionBuilder a2 = a1.endTrajectory().fresh().setTangent(0)
//                // first sweep
//                .splineTo(new Vector2d(-23,24),PI * 3 / 4)
//                .splineToSplineHeading(new Pose2d(-15, 25, 7*PI/16)
//                        , 0, null, null)
//                // second sweep
//                .splineToLinearHeading(new Pose2d(-28, 32, PI*6/8),
//                        PI * 3 / 4)
//                .splineToSplineHeading(new Pose2d(-15, 31, 6*PI/16),
//                        0, null, null)
//                // third sweep
//                .splineToLinearHeading(new Pose2d(-40, 36, PI/2),
//                        PI * 3 / 4)
//                .splineToSplineHeading(new Pose2d(-15, 39, PI/2),
//                        0, null, null)
//                .splineToSplineHeading(new Pose2d(-13, 33, PI), 0, null, null)
//                .splineToConstantHeading(new Vector2d(-1.5, 33), 0, null, null)
//
//                ;
//
//
//
//
//
//
//        TrajectoryActionBuilder a5 = a2.endTrajectory().fresh().setTangent(PI)
//                .splineToSplineHeading(new Pose2d(-20, -1, 0 - 0.0001), PI)
//                .splineToSplineHeading(new Pose2d(-29.5, -1, 0 - 0.0004), PI);
//
//        // wall specimen 2
//        TrajectoryActionBuilder a6 = a5.endTrajectory().fresh().setTangent(0)
//                .splineToSplineHeading(new Pose2d(-15, 4, PI), PI/2)
//                .splineToConstantHeading(new Vector2d(-10, 32.5), 0)
//                .splineToSplineHeading(new Pose2d(-1.5, 33, PI), 0, null, null);
//        TrajectoryActionBuilder a7 = a6.endTrajectory().fresh().setTangent(PI)
//                .splineToSplineHeading(new Pose2d(-15, -9, 0 - 0.0002), PI)
//                .splineToSplineHeading(new Pose2d(-29.5, -9, 0 - 0.0004), PI);
//
//        // wall specimen 3
//        TrajectoryActionBuilder a8 = a7.endTrajectory().fresh().setTangent(0)
//                .splineToSplineHeading(new Pose2d(-15, 4, PI), PI/2)
//                .splineToConstantHeading(new Vector2d(-10, 32.5), 0)
//                .splineToSplineHeading(new Pose2d(-1.5, 33, PI), 0, null, null);
//        TrajectoryActionBuilder a9 = a8.endTrajectory().fresh().setTangent(PI)
//                .splineToSplineHeading(new Pose2d(-15, -12, 0 - 0.0003), PI)
//                .splineToSplineHeading(new Pose2d(-29.5, -12, 0 - 0.0004), PI);
//
//        //wall specimen 4
//        TrajectoryActionBuilder a10 = a9.endTrajectory().fresh().setTangent(0)
//                .splineToSplineHeading(new Pose2d(-15, 4, PI), PI/2)
//                .splineToConstantHeading(new Vector2d(-10, 32.5), 0)
//                .splineToSplineHeading(new Pose2d(-1.5, 33, PI), 0, null, null);
//        TrajectoryActionBuilder a11 = a10.endTrajectory().fresh().setTangent(PI)
//                .splineToSplineHeading(new Pose2d(-15, -14, 0 - 0.0003), PI)
//                .splineToSplineHeading(new Pose2d(-29.5, -14, 0 - 0.0004), PI);
//
//        // park
//        TrajectoryActionBuilder a12 = a11.endTrajectory().fresh().setTangent(0)
//                .splineToConstantHeading(new Vector2d(0,33),0);
//
//
//
//        Action t1 = a1.build();
//        Action t2 = a2.build();
//
//
//        Action t5 = a5.build();
//        Action t6 = a6.build();
//        Action t7 = a7.build();
//        Action t8 = a8.build();
//        Action t9 = a9.build();
//        Action t10 = a10.build();
//        Action t11 = a11.build();
//        Action t12 = a12.build();
//
//        myBot.runAction(new SequentialAction(
//                // hob.actionMacro(SPECIMEN_BEFORE_DEPOSIT),
//                // specimen sweep pos 1 - X: -23, Y: 29, R: 5pi/4
//                t1,t2,t5,t6,t7,t8,t9,t10,t11, t12));