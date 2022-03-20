/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public class Intake extends SubsystemBase implements Loggable {
  /**
   * Creates a new Intake.
   */
   public Intake() {

  }

  /**
   * This moves the intake mechanism up, towards the robot
   */
  @Config
  public void moveUp () {
  
  }
  
  /**
   * This moves the intake mechanism down, towards the floor
   */
  @Config
  public void moveDown () {

  }

  /**
   * This moves the motors so the ball goes into the robot at full speed
   */
  public void rotateIn() {

  }

  /**
   * This moves the motors so the ball goes into the robot at full speed
   */
  @Config
  public void rotate(int speed) {

  }

  /**
   * This moves the motors so the ball goes out of the robot at full speed
   */
  public void rotateOut() {

  }

  /**
   * This stops the motors from moving
   */
  public void rotateStop () {

  }
}
