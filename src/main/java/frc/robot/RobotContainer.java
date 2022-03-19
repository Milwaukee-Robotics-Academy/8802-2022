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
import frc.robot.commands.DriveStraight;
import frc.robot.commands.SplitArcadeDrive;
import frc.robot.commands.TurnToAngle;
import frc.robot.subsystems.Blower;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;


public class RobotContainer {
  private final Drive m_drive = new Drive();
  private final Blower m_blower = new Blower();
  private final Climber m_climber = new Climber();
  private final XboxController driverController = new XboxController(0);
  private final XboxController operatorController = new XboxController(1);
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();
  SendableChooser<Command> m_DriveChooser = new SendableChooser<>();

public RobotContainer() {

  shuffleBoard();
  configureButtonBindings();
    m_drive.setDefaultCommand(new SplitArcadeDrive(driverController::getLeftTriggerAxis,
         driverController::getRightTriggerAxis, driverController::getLeftX, m_drive));
         // Add commands to the autonomous command chooser



    //m_drive.setDefaultCommand(new TankDrive(() -> driverController.getLeftY(), () -> driverController.getRightY(), m_drive));
}

/**
 * This tells what buttons are being used for which commands
 */
public void configureButtonBindings() {

  final JoystickButton driverA = new JoystickButton(driverController, XboxController.Button.kA.value);
  final JoystickButton driverB = new JoystickButton(driverController, XboxController.Button.kB.value);
  final JoystickButton operatorA = new JoystickButton(operatorController, XboxController.Button.kA.value);
  final JoystickButton operatorB = new JoystickButton(operatorController, XboxController.Button.kB.value);
  // final JoystickButton operatorX = new JoystickButton(operatorController, XboxController.Button.kX.value);
  // final JoystickButton operatorRightBumper = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);
  // final JoystickButton oepratorLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
  // final JoystickButton operatorStartButton = new JoystickButton(operatorController, XboxController.Button.kStart.value);
  // final JoystickButton operatorLeftStick = new JoystickButton(operatorController, XboxController.Button.kLeftStick.value);
  // final JoystickButton operatorRightStick = new JoystickButton(operatorController, XboxController.Button.kRightStick.value);
  
  operatorA.whenPressed(new InstantCommand(m_blower::blow, m_blower));
  OperatorB.whenPressed(new InstantCommand(m_blower::stop, m_blower));
  // operatorX.whenPressed(new InstantCommand(m_intake::rotateStop, m_intake));
  // operatorX.whenReleased(new InstantCommand(m_intake::rotateStop, m_intake));
  // operatorRightBumper.whenPressed(new InstantCommand(m_intake::moveUp, m_intake));
  // oepratorLeftBumper.whenPressed(new InstantCommand(m_intake::moveDown, m_intake));
  // operatorStartButton.whenPressed(new InstantCommand(m_storage::turnIn));
  // operatorStartButton.whenReleased(new InstantCommand(m_storage::storageStop));
  // operatorLeftStick.whenPressed(new InstantCommand(m_shooter::stopShooter));
  // operatorRightStick.whenPressed(new InstantCommand(m_shooter::runShooter));
  final JoystickButton operatorLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
  final JoystickButton operatorRightBumper = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
  operatorLeftBumper.whenPressed(new InstantCommand(m_climber::extend, m_climber))
    .whenReleased(new InstantCommand(m_climber::stop, m_climber));
  operatorRightBumper.whenPressed(new InstantCommand(m_climber::unextend, m_climber))
    .whenReleased(new InstantCommand(m_climber::stop, m_climber));
 }

public void shuffleBoard(){
// Put the chooser on the dashboard
SmartDashboard.putData(m_autoChooser);
m_autoChooser.setDefaultOption("reverse",new InstantCommand(()-> m_drive.drive(0,0,0)).withTimeout(5));
m_autoChooser.addOption("Blow.Reverse", new InstantCommand(()->m_blower.blow(), m_blower).withTimeout(1.4).andThen(
  new DriveStraight(-.6,1.5,m_drive).withTimeout(5)));
m_autoChooser.addOption("Blow.Turn.Blow.Reverse", new InstantCommand(()->m_blower.blow(), m_blower).withTimeout(1.4).andThen(
  new TurnToAngle(25,m_drive).withTimeout(3).andThen(
    new DriveStraight(-.6,1.5,m_drive).withTimeout(5))));
  // Shuffleboard.getTab("Intake").add("intake In", new InstantCommand(m_intake::rotateIn, m_intake));
  // Shuffleboard.getTab("Intake").add("intake Out", new InstantCommand(m_intake::rotateOut, m_intake));
  // Shuffleboard.getTab("Intake").add("intake Up", new InstantCommand(m_intake::moveUp, m_intake));
  // Shuffleboard.getTab("Intake").add("intake Down", new InstantCommand(m_intake::moveDown, m_intake));
  // Shuffleboard.getTab("Intake").add("intake Stop", new InstantCommand(m_intake::rotateStop, m_intake));
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