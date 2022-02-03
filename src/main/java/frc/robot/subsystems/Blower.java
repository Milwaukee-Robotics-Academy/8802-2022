/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BlowerConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public class Blower extends SubsystemBase implements Loggable {
  /**
   * Creates a new Blower.
   */
  private final CANSparkMax m_MainMotor = new CANSparkMax(BlowerConstants.kMain, MotorType.kBrushless);
  private final CANSparkMax m_FollowerMotor = new CANSparkMax(BlowerConstants.kFollower, MotorType.kBrushless);
  private final SparkMaxPIDController m_PidController;
  private final RelativeEncoder m_encoder;


   public Blower() {
    m_MainMotor.restoreFactoryDefaults();
    m_FollowerMotor.restoreFactoryDefaults();
    m_PidController = m_MainMotor.getPIDController();
    m_encoder = m_MainMotor.getEncoder();

    m_MainMotor.setInverted(false);
    m_FollowerMotor.setInverted(true);
    m_FollowerMotor.follow(m_MainMotor);

    m_PidController.setP(BlowerConstants.kP);
    m_PidController.setI(BlowerConstants.kP);
    m_PidController.setD(BlowerConstants.kP);
    m_PidController.setIZone(BlowerConstants.kIz);
    m_PidController.setFF(BlowerConstants.kFF);
    m_PidController.setOutputRange(BlowerConstants.kMinOutput,BlowerConstants.kMaxOutput);



  }

  /**
   * Blow this blower!
   */
  @Config
  public void blow(){
    m_PidController.setReference(BlowerConstants.kMaxRPM, CANSparkMax.ControlType.kVelocity);
  }

  /**
   * Stop the blower
   */
  @Config
  public void stop(){
    m_PidController.setReference(0, CANSparkMax.ControlType.kVelocity);
  }


  public void periodic(){
    // SmartDashboard.putData(intakeInput);
    // SmartDashboard.putNumber("intakeInputValue", intakeInput.getValue());
    // SmartDashboard.putNumber("intakeInputVoltage", intakeInput.getVoltage());
    // SmartDashboard.putBoolean("isBallAtIntake", isBallAtIntake());
    // SmartDashboard.putNumber("CurrentCount", m_outputCount++ );
  }
}
