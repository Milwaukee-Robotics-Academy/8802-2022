/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;
import io.github.oblarg.oblog.Loggable;

public class Climber extends SubsystemBase implements Loggable {
  /**
   * Creates a new Climber.
   */
  private final WPI_TalonFX m_leftMotor = new WPI_TalonFX(ClimberConstants.kClimberMotorLeft);
  private final WPI_TalonFX m_rightMotor = new WPI_TalonFX(ClimberConstants.kClimberMotorRight);

  private static final double DRIVE_SPEED = 0.5;

  public Climber() {
    m_leftMotor.configFactoryDefault();
    m_rightMotor.configFactoryDefault();
    m_rightMotor.setInverted(TalonFXInvertType.CounterClockwise);
    m_leftMotor.setInverted(TalonFXInvertType.Clockwise);
    m_leftMotor.setNeutralMode(NeutralMode.Brake);
    m_rightMotor.setNeutralMode(NeutralMode.Brake);    
  }
  /**
   * This elongates the elevator to hook onto the bar
   */
  public void extend() {
    m_leftMotor.set(DRIVE_SPEED);
    m_rightMotor.set(DRIVE_SPEED);
  }
  
  public void stop()
  {
    m_leftMotor.set(0);
    m_rightMotor.set(0);
  }
  /**
   * This shrinks the elevator to lift the robot
   */
  public void unextend() {
    m_leftMotor.set(-DRIVE_SPEED);
    m_rightMotor.set(-DRIVE_SPEED);
  }
}
