/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;

public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  private static final int leftDeviceID = 0; 
  private static final int rightDeviceID = 1;
  private CANSparkMax m_leftMotor;
  private CANSparkMax m_rightMotor;
  private CANEncoder m_leftCanEncoder;
  private CANEncoder m_rightCanEncoder;

  private Solenoid m_hatchGrabber;

  private CameraServer camserv;
  private UsbCamera cam0, cam1;
  
  private Encoders m_encoders;

  @Override
  public void robotInit() {
  /**
   * SPARK MAX controllers are intialized over CAN by constructing a CANSparkMax object
   * 
   * The CAN ID, which can be configured using the SPARK MAX Client, is passed as the
   * first parameter
   * 
   * The motor type is passed as the second parameter. Motor type can either be:
   *  com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless
   *  com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed
   * 
   * The example below initializes four brushless motors with CAN IDs 1 and 2. Change
   * these parameters to match your setup
   */
    m_leftMotor = new CANSparkMax(leftDeviceID, MotorType.kBrushless);
    m_rightMotor = new CANSparkMax(rightDeviceID, MotorType.kBrushless);

    m_leftCanEncoder = new CANEncoder(m_leftMotor);
    m_rightCanEncoder = new CANEncoder(m_rightMotor);

    m_encoders = new Encoders(m_leftCanEncoder, m_rightCanEncoder, 7.4);

    m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);

    m_hatchGrabber = new Solenoid(0);

    //m_lidarPort = new I2C(edu.wpi.first.wpilibj.I2C.Port.kOnboard, 0x62);
    //m_lidar = new Lidar(m_lidarPort);

    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);

    //CameraServer.getInstance().startAutomaticCapture();
    camserv = CameraServer.getInstance();
    cam0 = camserv.startAutomaticCapture(0);
    cam1 = camserv.startAutomaticCapture(1);
  }

  @Override
  public void teleopPeriodic() {
    //m_myRobot.tankDrive(-m_leftStick.getY()*0.8, -m_rightStick.getY()*0.8);
    m_myRobot.arcadeDrive(-m_rightStick.getY()*0.8, m_rightStick.getRawAxis(3)*0.6);
    if (m_rightStick.getRawAxis(2) > 0.0) {
      m_hatchGrabber.set(true);
    } else {
      m_hatchGrabber.set(false);
    }
    //m_encoders.speedTest();
    //System.out.println(m_lidarPort.;
    //System.out.println(m_lidar.update());
  }
}
