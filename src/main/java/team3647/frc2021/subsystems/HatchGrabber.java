/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team3647.frc2021.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team3647.frc2021.robot.Constants;
import team3647.frc2021.robot.RobotContainer;
import team3647.lib.drivers.VictorSPXFactory;
import team3647.lib.drivers.VictorSPXFactory.Configuration;
import team3647.lib.wpi.Solenoid;

public class HatchGrabber implements PeriodicSubsystem {
  /**
   * Creates a new HatchGrabber.
   */

  private VictorSPX hatchSucker;
  private Solenoid hatchSolenoid;
  private double current = 0;
  private boolean extended;

  public HatchGrabber(Configuration hatchSuckerConfig, int hatchSolenoidPin) {
    hatchSucker = VictorSPXFactory.createVictor(hatchSuckerConfig);
    hatchSolenoid = new Solenoid(hatchSolenoidPin);

  }

  public void extend(){
    hatchSolenoid.set(false);
    extended = true;
  }

  public void retract(){
    hatchSolenoid.set(true);
    extended = false;
  }

  private void updateCurrent() {
		current = RobotContainer.pDistributionPanel.getCurrent(Constants.cHatchGrabber.hatchGrabberPDPpin);
	}

  private double limitCurrent(double motorConst, double currentConst) {
		updateCurrent();
		System.out.println("Hatch Motor current: " + current);
		return current > currentConst ? (currentConst / current) * motorConst : motorConst;

	}

  public void grabHatch() {
		double output = limitCurrent(1, 15);
		hatchSucker.set(ControlMode.PercentOutput, output);
	}

	public void releaseHatch() {
		hatchSucker.set(ControlMode.PercentOutput, -limitCurrent(1, 15));
  }

  public void setOpenLoop(double demand) {
		hatchSucker.set(ControlMode.PercentOutput, demand);
	}

  public void runConstant() {
		setOpenLoop(.2);
  }
  
  public void stop(){
    setOpenLoop(0);
  }

  public boolean isExtended() {
		return extended;
	}

  
  @Override
  public String getName() {
    return "Hatch Grabber";
  }
}
