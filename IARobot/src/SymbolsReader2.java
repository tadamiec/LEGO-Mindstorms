import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class SymbolsReader2 implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;
	private boolean tryLeft;
	private int[] rightAngleList = { 10, 100, 145 };
	private int[] leftAngleList = { 10, 70, 125 };
	private static int rangeAngle = 5;
	private static int changeAngle = 5;

	static File pw = new File("power_up_8bit.wav");

	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B,
			true);

	public SymbolsReader2(SensorPort LS, int Dark, int Light) {
		ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1700);
	}

	@Override
	public void action() {
		suppressed = false;
		boolean bool;
		while (!suppressed) {

			Main.pilot.forward();
			Sound.playSample(pw, 75);
			tryLeft = true;
			bool = false;
			if (ls.getLightValue() < 1700) {
				Main.pilot.travel(20);
				if (tryLeft) {
					Main.pilot.rotate(leftAngleList[0]);
					if (ls.getLightValue() > 1700)
						bool = true;

					// Return to previous position
					Main.pilot.rotate(-leftAngleList[0] - changeAngle
							* rangeAngle);
					tryLeft = false;

				} else {
					Main.pilot.rotate(-rightAngleList[0]);
					if (ls.getLightValue() > 1700)
						bool = true;

					// Return to previous position
					Main.pilot.rotate(rightAngleList[0] + changeAngle
							* rangeAngle);
					tryLeft = true;
				}
				if(!bool){
				for (int listIterator = 1; listIterator < 3; listIterator++) {
					if (tryLeft) {
						Main.pilot.rotate(leftAngleList[listIterator]);
						if (checkAngle(-changeAngle))
							break;

						// Return to previous position
						Main.pilot.rotate(-leftAngleList[listIterator]
								- changeAngle * rangeAngle);
						tryLeft = false;

					} else {
						listIterator--;
						Main.pilot.rotate(-rightAngleList[listIterator]);
						if (checkAngle(changeAngle))
							break;

						// Return to previous position
						Main.pilot.rotate(rightAngleList[listIterator]
								+ changeAngle * rangeAngle);
						tryLeft = true;
					}
				}
			}
			}
			Thread.yield();
		}

	}

	private boolean checkAngle(int angle) {
		for (int i = 0; i < rangeAngle; i++) {
			Main.pilot.rotate(angle);
			if (ls.getLightValue() > 1700)
				return true;
		}

		return false;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
