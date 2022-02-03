/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class Vision extends SubsystemBase implements Loggable {
  // @Log.CameraStream
  private final PhotonCamera visionCamera = new PhotonCamera("photonvision");
  // @Log.CameraStream
  private final PhotonCamera driverCamera = new PhotonCamera("driverView");
  /**
   * Creates a new Vision.
   */
  public Vision() {

    driverCamera.setDriverMode(true);
  }

  public double getDistance(){
    return 1.0;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
