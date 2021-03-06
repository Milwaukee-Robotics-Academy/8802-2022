/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Drive extends SubsystemBase implements Loggable {
  private final WPI_TalonFX m_leftMotor = new WPI_TalonFX(DriveConstants.kLeftMotorFrontPort);
  private final WPI_TalonFX m_leftFollowerMotor = new WPI_TalonFX(DriveConstants.kLeftMotorRearPort);
  private final WPI_TalonFX m_rightMotor = new WPI_TalonFX(DriveConstants.kRightMotorFrontPort);
  private final WPI_TalonFX m_rightFollowerMotor = new WPI_TalonFX(DriveConstants.kRightMotorRearPort);
  SendableChooser<Boolean> m_preventTilt = new SendableChooser<>();
  // private final SlewRateLimiter m_accLimiter = new SlewRateLimiter(.9);
  // private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(2);
 
  
  @Log.Gyro
  private final AHRS m_gyro = new AHRS(SerialPort.Port.kUSB1);

  @Log.DifferentialDrive
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

 /**
   * Creates a new drive.
   */
  public Drive() {

    m_rightMotor.configFactoryDefault();
    m_rightFollowerMotor.configFactoryDefault();
    m_leftMotor.configFactoryDefault();
    m_leftFollowerMotor.configFactoryDefault();
    m_rightMotor.configOpenloopRamp(.75);
    m_rightMotor.setNeutralMode(NeutralMode.Brake);
    m_rightFollowerMotor.configOpenloopRamp(.75);
    m_leftFollowerMotor.configOpenloopRamp(.75);
    m_leftMotor.configOpenloopRamp(.75);
 
    m_rightFollowerMotor.setNeutralMode(NeutralMode.Brake);
    m_leftFollowerMotor.setNeutralMode(NeutralMode.Brake);
    m_leftMotor.setNeutralMode(NeutralMode.Brake);
    
    
    m_leftFollowerMotor.follow(m_leftMotor);
    m_rightFollowerMotor.follow(m_rightMotor);

    m_rightMotor.setInverted(TalonFXInvertType.Clockwise);
    m_leftMotor.setInverted(TalonFXInvertType.CounterClockwise);

    m_rightFollowerMotor.setInverted(InvertType.FollowMaster);
    m_leftFollowerMotor.setInverted(InvertType.FollowMaster);
    
    
  }

  public void drive(double rightThrottle, double leftThrottle, double rotation) {
      m_robotDrive.arcadeDrive(-(rightThrottle - leftThrottle), rotation*.75);
    }
    public double deadband(double value){
      //Upper Deadband//
      if (value >= +0.2)
        return value; 

      //Lower Deadband//
      if (value <= -0.2)
        return value;

      //Outside Deadband//
      return 0;
    }

  public void vision() {

  }

public void resetSensors() {
  m_gyro.reset();
}
  public void tank(double left, double right){
    m_leftMotor.set(left);
    m_rightMotor.set(right);
  }
  
  public void stopDrive(){
    this.tank(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
@Log(name = "Left Encoder")
  public double getLeftEncoderPosition(){
    return m_leftMotor.getSelectedSensorPosition();
  }

  @Log(name = "Right Encoder")
  public double getRightEncoderPosition(){
    return m_rightMotor.getSelectedSensorPosition();
  }

  public double getAveragePosition() {
    return (m_rightMotor.getSelectedSensorPosition()+m_leftMotor.getSelectedSensorPosition())/2;
  }

  public double getUnitsPerMeter() {
    return (2048*10.71)/(.1524*Math.PI);
  }

/**
 * Returns the total accumulated yaw angle (Z Axis, in degrees) reported by the sensor. 
 * getAngle() 
 * @return
 */
  public double getHeading(){
    return m_gyro.getAngle();
  }

  /**
   * allow for the Neutral mode to be set to coast when disabled
   * @param brake
   */
  public void setBrake(boolean brake){
  if (brake){
    m_rightFollowerMotor.setNeutralMode(NeutralMode.Brake);
    m_leftFollowerMotor.setNeutralMode(NeutralMode.Brake);  
    m_rightMotor.setNeutralMode(NeutralMode.Brake);
    m_leftMotor.setNeutralMode(NeutralMode.Brake);
  } else {
    m_rightFollowerMotor.setNeutralMode(NeutralMode.Coast);
    m_leftFollowerMotor.setNeutralMode(NeutralMode.Coast);  
    m_rightMotor.setNeutralMode(NeutralMode.Coast);
    m_leftMotor.setNeutralMode(NeutralMode.Coast);
  }
}

}