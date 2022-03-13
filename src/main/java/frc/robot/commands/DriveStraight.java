/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveStraight extends CommandBase {
  private final Drive m_drive;
  private final double m_speed;
  private final double m_distance;
  private double m_heading = 0.0;
  private double m_targetPosition;
  
  /**
   * Creates a new TankDrive.
   */
  public DriveStraight(double speed, double meters, Drive drive) {
    m_drive = drive;
    m_speed = speed;
    m_distance = meters;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_heading = m_drive.getHeading();
    m_targetPosition = m_drive.getAveragePosition()+m_distance*m_drive.getUnitsPerMeter();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      if(m_drive.getHeading()>m_heading){
        m_drive.tank(m_speed*.6, m_speed);
      } else if(m_drive.getHeading()<m_heading){
        m_drive.tank(m_speed, m_speed*.8);
      } else {
        m_drive.tank(m_speed, m_speed);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.tank(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_drive.getAveragePosition()>m_targetPosition) {
      m_drive.tank(0, 0);
      return true;
    }
    return false;
  }
}
