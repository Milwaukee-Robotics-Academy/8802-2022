package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Autonomous;
import frc.robot.commands.SplitArcadeDrive;
import frc.robot.subsystems.Blower;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;


public class RobotContainer {
  private final Drive m_drive = new Drive();
  private final Intake m_intake = new Intake();
  private final Blower m_blower = new Blower();
  private final Climber m_climber = new Climber();


 // private final PowerDistributionPanel pdp = new PowerDistributionPanel();
  private final XboxController driverController = new XboxController(0);
//  private final XboxController operatorController = new XboxController(1);
  private final CommandBase m_autonomousCommand = new Autonomous(m_drive).withTimeout(5);
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();
public RobotContainer() {

  Shuffleboard.getTab("Intake").add("intake In", new InstantCommand(m_intake::rotateIn, m_intake));
  Shuffleboard.getTab("Intake").add("intake Out", new InstantCommand(m_intake::rotateOut, m_intake));
  Shuffleboard.getTab("Intake").add("intake Up", new InstantCommand(m_intake::moveUp, m_intake));
  Shuffleboard.getTab("Intake").add("intake Down", new InstantCommand(m_intake::moveDown, m_intake));
  Shuffleboard.getTab("Intake").add("intake Stop", new InstantCommand(m_intake::rotateStop, m_intake));


  configureButtonBindings();
    m_drive.setDefaultCommand(new SplitArcadeDrive(driverController::getLeftTriggerAxis,
         driverController::getRightTriggerAxis, driverController::getLeftX, m_drive));
         // Add commands to the autonomous command chooser
    m_autoChooser.setDefaultOption("Simple Auto",m_autonomousCommand);
    m_autoChooser.addOption("Drive Back", new InstantCommand(()-> m_drive.drive(-.6,0,0)).withTimeout(5));

// Put the chooser on the dashboard
  SmartDashboard.putData(m_autoChooser);
    //m_drive.setDefaultCommand(new TankDrive(() -> driverController.getLeftY(), () -> driverController.getRightY(), m_drive));
}

/**
 * This tells what buttons are being used for which commands
 */
public void configureButtonBindings() {

  // final JoystickButton operatorA = new JoystickButton(operatorController, XboxController.Button.kA.value);
  // final JoystickButton operatorB = new JoystickButton(operatorController, XboxController.Button.kB.value);
  // final JoystickButton operatorX = new JoystickButton(operatorController, XboxController.Button.kX.value);
  final JoystickButton driverA = new JoystickButton(driverController, XboxController.Button.kA.value);
  final JoystickButton driverB = new JoystickButton(driverController, XboxController.Button.kB.value);
  // final JoystickButton operatorRightBumper = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);
  // final JoystickButton oepratorLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
  // final JoystickButton operatorStartButton = new JoystickButton(operatorController, XboxController.Button.kStart.value);
  // final JoystickButton operatorLeftStick = new JoystickButton(operatorController, XboxController.Button.kLeftStick.value);
  // final JoystickButton operatorRightStick = new JoystickButton(operatorController, XboxController.Button.kRightStick.value);
 driverA.whenPressed(new InstantCommand(m_blower::blow, m_blower));
 driverB.whenPressed(new InstantCommand(m_blower::stop, m_blower));
  // operatorX.whenPressed(new InstantCommand(m_intake::rotateStop, m_intake));
  // operatorX.whenReleased(new InstantCommand(m_intake::rotateStop, m_intake));

  // operatorRightBumper.whenPressed(new InstantCommand(m_intake::moveUp, m_intake));
  // oepratorLeftBumper.whenPressed(new InstantCommand(m_intake::moveDown, m_intake));

  // operatorStartButton.whenPressed(new InstantCommand(m_storage::turnIn));
  // operatorStartButton.whenReleased(new InstantCommand(m_storage::storageStop));

  // operatorLeftStick.whenPressed(new InstantCommand(m_shooter::stopShooter));
  // operatorRightStick.whenPressed(new InstantCommand(m_shooter::runShooter));
  final JoystickButton driverStart = new JoystickButton(driverController, XboxController.Button.kStart.value);
  final JoystickButton driverBack = new JoystickButton(driverController, XboxController.Button.kBack.value);
  driverStart.whenPressed(new InstantCommand(m_climber::extend, m_climber))
    .whenReleased(new InstantCommand(m_climber::stop, m_climber));
  driverBack.whenPressed(new InstantCommand(m_climber::unextend, m_climber))
    .whenReleased(new InstantCommand(m_climber::stop, m_climber));
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
    return m_autoChooser.getSelected();
  }

}