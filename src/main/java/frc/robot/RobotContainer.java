package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AdvanceStorageToShooter;
import frc.robot.commands.Autonomous;
import frc.robot.commands.RunStorageWithPhotoeyes;
import frc.robot.commands.ShooterShoot;
import frc.robot.commands.SplitArcadeDrive;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Vision;

public class RobotContainer {
  private final Drive m_drive = new Drive();
  private final Intake m_intake = new Intake();
  private final Storage m_storage = new Storage();
  private final Shooter m_shooter = new Shooter();
  private final Vision m_vision = new Vision();


 // private final PowerDistributionPanel pdp = new PowerDistributionPanel();
  private final XboxController driverController = new XboxController(0);
//  private final XboxController operatorController = new XboxController(1);
  private final CommandBase m_autonomousCommand = new Autonomous(m_drive).withTimeout(5);

public RobotContainer() {

  Shuffleboard.getTab("Shooter").add("AdvanceStorageToShooter", new AdvanceStorageToShooter(m_storage));
  Shuffleboard.getTab("Intake").add("intake In", new InstantCommand(m_intake::rotateIn, m_intake));
  Shuffleboard.getTab("Intake").add("intake Out", new InstantCommand(m_intake::rotateOut, m_intake));
  Shuffleboard.getTab("Intake").add("intake Up", new InstantCommand(m_intake::moveUp, m_intake));
  Shuffleboard.getTab("Intake").add("intake Down", new InstantCommand(m_intake::moveDown, m_intake));
  Shuffleboard.getTab("Intake").add("intake Stop", new InstantCommand(m_intake::rotateStop, m_intake));
  Shuffleboard.getTab("Shooter").add("Stop Shooter", new InstantCommand(m_shooter::stopShooter,m_shooter));
  Shuffleboard.getTab("Shooter").add("SHOOT!!!", new ShooterShoot(m_shooter,m_storage));
  Shuffleboard.getTab("Storage").add("Turn In Storage", new InstantCommand(m_storage::turnIn, m_storage));
  Shuffleboard.getTab("Storage").add("Turn Out Storage", new InstantCommand(m_storage::turnOut, m_storage));
  Shuffleboard.getTab("Storage").add("Stop Storage", new InstantCommand(m_storage::storageStop, m_storage));  

  
  configureButtonBindings();
    m_drive.setDefaultCommand(new SplitArcadeDrive(() -> driverController.getLeftTriggerAxis(),
         () -> driverController.getRightTriggerAxis(), () -> driverController.getLeftX(), m_drive));
    //m_drive.setDefaultCommand(new TankDrive(() -> driverController.getLeftY(), () -> driverController.getRightY(), m_drive));
    m_storage.setDefaultCommand(new RunStorageWithPhotoeyes(m_storage));
}

/**
 * This tells what buttons are being used for which commands
 */
public void configureButtonBindings() {

  // final JoystickButton operatorA = new JoystickButton(operatorController, XboxController.Button.kA.value);
  // final JoystickButton operatorB = new JoystickButton(operatorController, XboxController.Button.kB.value);
  // final JoystickButton operatorX = new JoystickButton(operatorController, XboxController.Button.kX.value);
  final JoystickButton driverA = new JoystickButton(driverController, XboxController.Button.kA.value);
  // final JoystickButton operatorRightBumper = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);
  // final JoystickButton oepratorLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
  // final JoystickButton operatorStartButton = new JoystickButton(operatorController, XboxController.Button.kStart.value);
  // final JoystickButton operatorLeftStick = new JoystickButton(operatorController, XboxController.Button.kLeftStick.value);
  // final JoystickButton operatorRightStick = new JoystickButton(operatorController, XboxController.Button.kRightStick.value);
  // operatorA.whenPressed(new InstantCommand(m_intake::rotateIn, m_intake));
  // operatorB.whenPressed(new InstantCommand(m_intake::rotateOut, m_intake));
  // operatorX.whenPressed(new InstantCommand(m_intake::rotateStop, m_intake));
  // operatorX.whenReleased(new InstantCommand(m_intake::rotateStop, m_intake));

  // operatorRightBumper.whenPressed(new InstantCommand(m_intake::moveUp, m_intake));
  // oepratorLeftBumper.whenPressed(new InstantCommand(m_intake::moveDown, m_intake));

  // operatorStartButton.whenPressed(new InstantCommand(m_storage::turnIn));
  // operatorStartButton.whenReleased(new InstantCommand(m_storage::storageStop));

  // operatorLeftStick.whenPressed(new InstantCommand(m_shooter::stopShooter));
  // operatorRightStick.whenPressed(new InstantCommand(m_shooter::runShooter));

  driverA.whenHeld(new ShooterShoot(m_shooter,m_storage),true);

}

public void shuffleBoard(){
//SmartDashboard.putBoolean("intakeInput", m_storage.isBallAtIntake());


}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autonomousCommand;
  }

}