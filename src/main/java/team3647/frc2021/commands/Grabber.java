/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team3647.frc2021.commands;

import java.util.function.DoubleSupplier;


import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2021.subsystems.HatchGrabber;
import team3647.lib.util.Units;
import team3647.lib.wpi.Timer;

public class Grabber extends CommandBase {
  /**
   * Creates a new Grabber.
   */
  private final HatchGrabber m_hatchGrabber;
  private final DoubleSupplier m_currentVelocity;
  private final Timer timer;
  private final Boolean m_extend;
  private final Boolean m_retract;


  public Grabber(HatchGrabber hatchGrabber, Boolean extend, Boolean retract, DoubleSupplier currentVelocity) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_hatchGrabber=hatchGrabber;
    timer= new Timer();
    addRequirements(m_hatchGrabber);
    m_extend = extend;
    m_retract = retract;
    m_currentVelocity=currentVelocity;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_extend && m_currentVelocity.getAsDouble()*Units.meters_to_feet(1)<=4.0){
        
        m_hatchGrabber.extend();
        m_hatchGrabber.grabHatch();

    } else if (m_retract){

        m_hatchGrabber.retract();
        m_hatchGrabber.releaseHatch();

    } else if (m_hatchGrabber.isExtended()){

      m_hatchGrabber.runConstant();

    } else {

      m_hatchGrabber.stop();

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
