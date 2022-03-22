package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Blow;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.SplitArcadeDrive;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Blower;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;

public class RobotContainer {
  private final Drive m_drive = new Drive();
  private final Blower m_blower = new Blower();
  private final Climber m_climber = new Climber();
  private final XboxController driverController = new XboxController(0);
  private final XboxController operatorController = new XboxController(1);
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  public RobotContainer() {

    shuffleBoard();
    configureButtonBindings();
    m_drive.setDefaultCommand(new SplitArcadeDrive(driverController::getLeftTriggerAxis,
        driverController::getRightTriggerAxis, driverController::getLeftX, m_drive));
   
        // Add commands to the autonomous command chooser
    m_autoChooser.setDefaultOption("Blow", new InstantCommand(() -> m_blower.blow(), m_blower).withTimeout(7)
        .andThen(new InstantCommand(() -> m_blower.stop(), m_blower).withTimeout(1)));

    m_autoChooser.addOption("reverse", new DriveStraight(.6, 1.5, m_drive).withTimeout(5));
    m_autoChooser.addOption("Blow", new Blow(m_blower).withTimeout(5));
    m_autoChooser.addOption("Blow.Reverse",
        new Blow(m_blower).withTimeout(5).andThen(
          new DriveStraight(.6, 1.7, m_drive).withTimeout(5)
            ));

    m_autoChooser.addOption("Blow.Turn.Blow.Reverse",
            new Blow(m_blower).withTimeout(1.4).andThen(
            new Turn(.6, m_drive).withTimeout(.33).andThen(
              new Blow(m_blower).withTimeout(5).andThen(
              new DriveStraight(.6, 1.5, m_drive).withTimeout(5)
              ))));

  }

  /**
   * This tells what buttons are being used for which commands
   */
  public void configureButtonBindings() {
   
   /**
    * Operator Controls
    */
    final JoystickButton operatorStartButton = new JoystickButton(operatorController, XboxController.Button.kStart.value);
    final JoystickButton operatorBackButton = new JoystickButton(operatorController, XboxController.Button.kBack.value);
    final JoystickButton operatorLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
    final JoystickButton operatorRightBumper = new JoystickButton(operatorController, XboxController.Button.kRightBumper.value);
    operatorRightBumper.whenPressed(new InstantCommand(m_blower::blow, m_blower));
    operatorLeftBumper.whenPressed(new InstantCommand(m_blower::stop, m_blower));
    operatorStartButton.whenPressed(new InstantCommand(m_climber::extend, m_climber)).whenReleased(new InstantCommand(m_climber::stop, m_climber));
    operatorBackButton.whenPressed(new InstantCommand(m_climber::unextend, m_climber)).whenReleased(new InstantCommand(m_climber::stop, m_climber));
  }

  public void shuffleBoard() {
    // Put the chooser on the dashboard
    SmartDashboard.putData("Auto", m_autoChooser);

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    m_drive.setBrake(true);
    return m_autoChooser.getSelected();
  }

  public void disabledInit() {
    m_drive.setBrake(false);
  }

  public void disabledPeriodic() {
    m_drive.setBrake(false);
  }

  public void robotInit() {
    m_drive.setBrake(true);
  }

  public void robotPeriodic() {
  }

  public void teleopInit() {
    m_drive.setBrake(true);
  }
}