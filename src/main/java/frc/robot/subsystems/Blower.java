// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BlowerConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Blower extends SubsystemBase implements Loggable {

  private Spark m_leftMotor =new Spark(BlowerConstants.kLeftMotorPort);
  private Spark m_rightMotor =new Spark(BlowerConstants.kRightMotorPort);

  /** Creates a new Blower. */
  public Blower() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void blow(){
    m_leftMotor.set(1);
    m_rightMotor.set(1);
  }

  public void stop(){
    m_leftMotor.set(0);
    m_rightMotor.set(0);
  }
}
