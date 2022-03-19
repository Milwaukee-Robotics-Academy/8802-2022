// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
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

  // private CANSparkMax m_leftMotor =new CANSparkMax(BlowerConstants.kLeftMotorFrontPort, MotorType.kBrushless);
  // private CANSparkMax m_leftRearMotor =new CANSparkMax(BlowerConstants.kLeftMotorFrontPort, MotorType.kBrushless);
  private WPI_VictorSPX m_rightMotor = new WPI_VictorSPX(BlowerConstants.kRightMotorPort);


  /** Creates a new Blower. */
  public Blower() {
    // m_leftMotor.restoreFactoryDefaults();
    // m_leftRearMotor.restoreFactoryDefaults();
    // m_leftRearMotor.follow(m_leftMotor,true);
    // m_leftMotor.setInverted(false);
    // m_leftMotor.clearFaults();
    // m_leftRearMotor.clearFaults();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void blow(){
    // m_leftMotor.set(.2);
    m_rightMotor.set(1);
  }

  public void stop(){
    // m_leftMotor.set(0);
    m_rightMotor.set(0);
  }
}
