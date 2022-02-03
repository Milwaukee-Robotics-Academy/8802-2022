/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
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
  private final Victor m_leftMotor = new Victor(DriveConstants.kLeftMotorFrontPort);
  private final Victor m_leftFollowerMotor = new Victor(DriveConstants.kLeftMotorRearPort);
  private final Victor m_rightMotor = new Victor(DriveConstants.kRightMotorFrontPort);
  private final Victor m_rightFollowerMotor = new Victor(DriveConstants.kRightMotorRearPort);
  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(m_leftMotor, m_leftFollowerMotor);
  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(m_rightMotor, m_rightFollowerMotor);
  private final Encoder m_leftEncoder = new Encoder(DriveConstants.kLeftEncoder1,DriveConstants.kLeftEncoder2);
  private final Encoder m_rightEncoder = new Encoder(DriveConstants.kRightEncoder1,DriveConstants.kRightEncoder2);
  
  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(DriveConstants.kSlewSpeedLimit);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(DriveConstants.kSlewRotLimit);



  private final PIDController m_leftPIDController = new PIDController(DriveConstants.kP, DriveConstants.kI, DriveConstants.kD);
  private final PIDController m_rightPIDController = new PIDController(DriveConstants.kP, DriveConstants.kI, DriveConstants.kD);


  // Gains are for example purposes only - must be determined for your own robot!
  private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 3);

  private final DifferentialDriveKinematics m_kinematics =
  new DifferentialDriveKinematics(DriveConstants.kTrackWidth);

private final DifferentialDriveOdometry m_odometry;

  @Log.Gyro
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  @Log.DifferentialDrive
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotors, m_rightMotors);

 /**
   * Creates a new drive.
   */
  public Drive() {
    m_leftMotors.setInverted(true);

    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
    // Set the distance per pulse for the drive encoders. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.
    m_leftEncoder.setDistancePerPulse(2 * Math.PI * DriveConstants.kWheelRadius / DriveConstants.kEncoderResolution);
    m_rightEncoder.setDistancePerPulse(2 * Math.PI * DriveConstants.kWheelRadius / DriveConstants.kEncoderResolution);

    //Reset the sensors
    m_gyro.reset(); 
    m_leftEncoder.reset();
    m_rightEncoder.reset();
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
      new ChassisSpeeds((m_speedLimiter.calculate(rightThrottle - leftThrottle)*DriveConstants.kMaxSpeed), 0.0, m_rotLimiter.calculate(-rotation))
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
    final double leftFeedforward = m_feedforward.calculate(speeds.leftMetersPerSecond);
    final double rightFeedforward = m_feedforward.calculate(speeds.rightMetersPerSecond);

    final double leftOutput =
        m_leftPIDController.calculate(m_leftEncoder.getRate(), speeds.leftMetersPerSecond);
    final double rightOutput =
        m_rightPIDController.calculate(m_rightEncoder.getRate(), speeds.rightMetersPerSecond);
    m_leftMotors.setVoltage(leftOutput + leftFeedforward);
    m_rightMotors.setVoltage(rightOutput + rightFeedforward);
  }


  public void driveTank(double d, double e){
    m_robotDrive.tankDrive(d, e);
  }

  public void vision() {

  }

  @Config
  public void tank(double left, double right){
    m_leftMotor.set(left);
    m_rightMotor.set(right);
  }
  
  public void stopDrive(){
    this.tank(0, 0);
  }
  /** Updates the field-relative position. */
  public void updateOdometry() {
    m_odometry.update(
        m_gyro.getRotation2d(), m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
  }
@Log(name = "Left Encoder")
  public int getLeftEncoderPosition(){
    return m_leftEncoder.get();
  }

  @Log(name = "Right Encoder")
  public int getRightEncoderPosition(){
    return m_rightEncoder.get();
  }
  
  /**
   * Resets the field-relative position to a specific location.
   *
   * @param pose The position to reset to.
   */
  public void resetOdometry(Pose2d pose) {
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  /**
   * Returns the pose of the robot.
   *
   * @return The pose of the robot.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }
}