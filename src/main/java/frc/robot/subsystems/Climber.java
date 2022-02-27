/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

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

  public Climber() {

}
/**
 * This elongates the elevator to hook onto the bar
 */
public void extend() {
  }
  
  /**
   * This shrinks the elevator to lift the robot
   */
  public void unextend() {
   }
}
