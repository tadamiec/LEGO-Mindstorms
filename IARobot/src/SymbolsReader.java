import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class SymbolsReader implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;

	static File pw = new File("power_up_8bit.wav");

	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B,
			true);

	public SymbolsReader(SensorPort LS, int Dark, int Light) {
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
		float incrementedAngle = 0;
		float returnAngle = 0;
		double storeAngle = 0;
		int rangeAngleLeft = 10, rangeAngleRight = 10;
		int rangeAngle = 20;
		boolean leftChecked = false;
		boolean rightChecked = false;
		boolean firstIncrement = false;

		while (!suppressed) {
			while (ls.getLightValue() > 1700) {
				Main.pilot.setTravelSpeed(100);
				Main.pilot.forward();
				if ((rangeAngle > 20)) {
					// System.out.println(storeAngle);
					Main.symbolAngleList.add(storeAngle);
				}
				rangeAngle = 20;
				rangeAngleLeft = 10;
				rangeAngleRight = 10;
				incrementedAngle = 0;
				returnAngle = 0;
				storeAngle = 0;
				leftChecked = false;
				rightChecked = false;
				firstIncrement = false;
			}

			while (ls.getLightValue() < 1700) {
				Main.pilot.setRotateSpeed(30);
				if (!leftChecked) {
					Main.pilot.rotateLeft();
					incrementedAngle -= Main.pilot.getAngleIncrement();

					// Get angle to store
					storeAngle = incrementedAngle;

					if (Math.abs(incrementedAngle) > rangeAngleLeft) {
						Main.pilot.setRotateSpeed(50);

						// Go Back
						returnAngle = 0;
						while (Math.abs(returnAngle) < Math
								.abs(incrementedAngle)) {
							Main.pilot.rotateRight();
							returnAngle += Main.pilot.getAngleIncrement();
						}

						incrementedAngle = 0;
						leftChecked = true;
					}
				} else if (!rightChecked && (ls.getLightValue() < 1700)) {
					Main.pilot.rotateRight();
					incrementedAngle += Main.pilot.getAngleIncrement();

					// Get angle to store
					storeAngle = incrementedAngle;

					if (Math.abs(incrementedAngle) > rangeAngleRight) {
						Main.pilot.setRotateSpeed(50);

						// Go Back
						returnAngle = 0;
						while (Math.abs(returnAngle) < Math
								.abs(incrementedAngle)) {
							Main.pilot.rotateLeft();
							returnAngle += Main.pilot.getAngleIncrement();
						}

						incrementedAngle = 0;
						rightChecked = true;
					}
				} else if (leftChecked && rightChecked && (rangeAngle == 55)) {
					// no line
					Main.pilot.stop();
					getSymbol();
				} else if (ls.getLightValue() < 1700) {
					Delay.msDelay(100);
					Main.pilot.travel(25);
					if (!firstIncrement) {
						rangeAngle += 35;
						firstIncrement = true;
					}

					rangeAngleLeft = rangeAngle - 5;
					rangeAngleRight = rangeAngle + 35;
					leftChecked = false;
					rightChecked = false;
				}
			}

			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	public void getSymbol() {
		if (Main.symbolAngleList.size() < 2) {
			System.out.println("L");
		} else if (Main.symbolAngleList.size() > 2) {
			System.out.println("M");
		} else if ((Main.symbolAngleList.get(0) < -70)
				&& (Main.symbolAngleList.get(1) < -65)) {
			System.out.println("M");
		} else if ((Main.symbolAngleList.get(0) < -70)
				&& (Main.symbolAngleList.get(1) > -40)) {
			System.out.println("Z");
		} else if ((Main.symbolAngleList.get(0) > -65)
				&& (Main.symbolAngleList.get(1) > -75)) {
			System.out.println("U");
		}
	}
}
