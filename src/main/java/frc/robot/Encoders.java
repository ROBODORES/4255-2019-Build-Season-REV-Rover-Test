/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

/**
 * Add your docs here.
 */
public class Encoders {
    public CANEncoder leftCanEncoder;
    public CANEncoder rightCanEncoder;
    private double leftZero, rightZero; 
    private double distPerFoot;
    private double topSpeed;

    public Encoders(CANEncoder leftCanEncoder, CANEncoder rightCanEncoder, double distPerFoot) {
        this.leftCanEncoder = leftCanEncoder;
        this.rightCanEncoder = rightCanEncoder;
        this.distPerFoot = distPerFoot;
        leftZero = 0.0;
        rightZero = 0.0;
        topSpeed = 0.0;
    }

    public double feet(double input) {
        return input/distPerFoot;
    }

    public void reset() {
        leftZero = leftCanEncoder.getPosition();
        rightZero = (-rightCanEncoder.getPosition());
    }

    public double getLeftPosition() {
        return feet(leftCanEncoder.getPosition()-leftZero);
    }

    public double getRightPosition() {
        return feet((-rightCanEncoder.getPosition())-rightZero);
    }

    public double getAveragePosition() {
        return (getLeftPosition()+getRightPosition())/2.0;
    }

    public double getLeftVelocity() {
        return feet(leftCanEncoder.getVelocity())/60.0;
    }

    public double getRightVelocity() {
        return feet(-rightCanEncoder.getVelocity())/60.0;
    }

    public double getAverageVelocity() {
        return (getLeftVelocity()+getRightVelocity())/2.0;
    }

    public void speedTest() {
        double speed = Math.abs(getAverageVelocity());
        if (speed > topSpeed) {
            topSpeed = speed;
        }
        System.out.print("Current Speed: "+speed*0.681879+"MPH");
        System.out.println(" , Top Speed: "+topSpeed*0.681879+"MPH");
    }
}
