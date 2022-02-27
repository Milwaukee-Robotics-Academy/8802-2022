// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BlowerConstants;
import io.github.oblarg.oblog.Loggable;

public class Blower extends SubsystemBase implements Loggable {
  private CANSparkMax m_leftMotor =new CANSparkMax(BlowerConstants.kLeftMotorFrontPort, MotorType.kBrushless);
  private CANSparkMax m_rightMotor = new CANSparkMax(BlowerConstants.kRightMotorFrontPort, MotorType.kBrushless);
  private CANSparkMax m_leftRearMotor = new CANSparkMax(BlowerConstants.kLeftMotorRearPort, MotorType.kBrushless);
  private CANSparkMax m_rightRearMotor = new CANSparkMax(BlowerConstants.kRightMotorRearPort, MotorType.kBrushless);

  /** Creates a new Blower. */
  public Blower() {
    m_leftMotor.restoreFactoryDefaults();
    m_leftRearMotor.restoreFactoryDefaults();
    m_rightMotor.restoreFactoryDefaults();
    m_rightRearMotor.restoreFactoryDefaults();
    m_leftRearMotor.follow(m_leftRearMotor,true);
    m_rightRearMotor.follow(m_rightRearMotor,true);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void blow(){
    m_leftMotor.set(1);
    m_rightMotor.set(1);
  }
  public void setRampRate(boolean ramp){
    if (ramp) {
      if(m_leftMotor.setOpenLoopRampRate(0.25) != REVLibError.kOk) {
        SmartDashboard.putString("Ramp Rate", "Error");
      }
    } else {
      if(m_leftMotor.setOpenLoopRampRate(0) != REVLibError.kOk) {
        SmartDashboard.putString("Ramp Rate", "Error");
      }
    }
  }
}
