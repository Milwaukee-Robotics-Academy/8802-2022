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
import io.github.oblarg.oblog.annotations.Log;
import static frc.robot.Constants.ShooterConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Shooter extends SubsystemBase implements Loggable {
  /**
   * Creates a new Shooter.
   */ 

public Shooter (){
}
  /**
   * This raises the shooter
   */
  @Config  (name = "Shooter Up")
  public void up() {
  }

  /**
   * This lowers the shooter
   */
  @Config  (name = "Shooter Down")
  public void down() {
  }

  /**
   * This will have the motors run full speed
   **/
  @Config (name = "Run Shooter")
  public void runShooter() {
  }

  /**
   * This will have the motors run at a specific speed
   * @param speed
   */
  public void runShooter(Double speed){
  }

  public void stopShooter(){
  }

  @Config
public void runShooter(double front, double back) {
}
}
