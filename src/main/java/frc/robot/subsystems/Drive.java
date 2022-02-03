/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class Drive extends SubsystemBase implements Loggable {
  private final Victor m_LeftMotor = new Victor(DriveConstants.kLeftMotorFrontPort);
  private final Victor m_LeftFollowerMotor = new Victor(DriveConstants.kLeftMotorRearPort);
  private final Victor m_RightMotor = new Victor(DriveConstants.kRightMotorFrontPort);
  private final Victor m_RightFollowerMotor = new Victor(DriveConstants.kRightMotorRearPort);
  private final MotorControllerGroup m_LeftMotors = new MotorControllerGroup(m_LeftMotor, m_LeftFollowerMotor);
  private final MotorControllerGroup m_RightMotors = new MotorControllerGroup(m_RightMotor, m_RightFollowerMotor);
  private final Encoder m_LeftEncoder = new Encoder(DriveConstants.kLeftEncoder1,DriveConstants.kLeftEncoder2);
  private final Encoder m_RightEncoder = new Encoder(DriveConstants.kRightEncoder1,DriveConstants.kRightEncoder2);
  
  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

  public static final double kMaxSpeed = 3.0; // meters per second
  public static final double kMaxAngularSpeed = 2 * Math.PI; // one rotation per second

  private static final double kTrackWidth = 0.5588; // meters
  private static final double kWheelRadius = 0.1524; // meters
  private static final int kEncoderResolution = 5; //CIMcoder has 5 CPR this may need to be adjusted * 10.71 for gearbox

  private final DifferentialDriveKinematics m_kinematics =
  new DifferentialDriveKinematics(kTrackWidth);

private final DifferentialDriveOdometry m_odometry;

  @Log.Gyro
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  @Log.DifferentialDrive
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_LeftMotors, m_RightMotors);

 /**
   * Creates a new drive.
   */
  public Drive() {
    m_LeftMotors.setInverted(true);
    m_gyro.reset();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
  }

   /**
   * Drives the robot with the given -1 to 1 driver gamepad inputs converting 
   * to linear velocity and angular velocity.
   *
   * @param rightThrottle percent to determine Linear velocity in m/s.
   * @param leftThrottle subtracted from right throttle
   * @param rot percent to determine Angular velocity in rad/s.
   */
  public void drive(double rightThrottle, double leftThrottle, double rotation) {
    var wheelSpeeds = m_kinematics.toWheelSpeeds(
      new ChassisSpeeds((m_speedLimiter.calculate(rightThrottle - leftThrottle)*kMaxSpeed), 0.0, m_rotLimiter.calculate(-rotation))
      );
      this.setSpeeds(wheelSpeeds);
    }
    public double deadband(double value){
      //Upper Deadband//
      if (value >= +0.2)
        return value; 

      //Lower Deadband//
      if (value <= -0.2)
        return value;

      //Outside Deadband//
      return 0;
    }

      /**
   * Sets the desired wheel speeds.
   *
   * @param speeds The desired wheel speeds.
   */
  public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
    // final double leftFeedforward = m_feedforward.calculate(speeds.leftMetersPerSecond);
    // final double rightFeedforward = m_feedforward.calculate(speeds.rightMetersPerSecond);

    // final double leftOutput =
    //     m_leftPIDController.calculate(m_leftEncoder.getRate(), speeds.leftMetersPerSecond);
    // final double rightOutput =
    //     m_rightPIDController.calculate(m_rightEncoder.getRate(), speeds.rightMetersPerSecond);
    // m_leftGroup.setVoltage(leftOutput + leftFeedforward);
    // m_rightGroup.setVoltage(rightOutput + rightFeedforward);
  }


  public void driveTank(double d, double e){
    m_robotDrive.tankDrive(d, e);
  }

  public void vision() {

  }

  @Config
  public void tank(double left, double right){
    m_LeftMotor.set(left);
    m_RightMotor.set(right);
  }
  
  public void stopDrive(){
    this.tank(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
@Log(name = "Left Encoder")
  public int getLeftEncoderPosition(){
    return m_LeftEncoder.get();
  }

  @Log(name = "Right Encoder")
  public int getRightEncoderPosition(){
    return m_RightEncoder.get();
  }
}