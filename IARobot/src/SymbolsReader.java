import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

//import lejos.nxt.Sound;

public class SymbolsReader implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;
	private boolean tryLeft = false;
	private boolean alreadyForwards = false;
	private boolean minus = false;
	private boolean leftChecked = false;
	private boolean rightChecked = false;

	long startTime, currentTime;
	// static File pw = new File("power_up_8bit.wav");

	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B,
			true);

	SymbolTravelData symbolTravelData = new SymbolTravelData();

	static File pw = new File("power_up_8bit.wav");

	public SymbolsReader(SensorPort LS, int Dark, int Light) {
		ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1400 && ls.getLightValue() < 2000);
	}

	@Override
	public void action() {
		suppressed = false;
		int angle = 7;
		int limitAngle = angle;
		// int correction = 30;

		while (!suppressed) {
			leftChecked = false;
			rightChecked = false;

			while (ls.getLightValue() > 1400 && ls.getLightValue() < 2000) {
				// startTime = System.currentTimeMillis();
				Main.pilot.forward();
				// System.out.println(ls.getLightValue());
				alreadyForwards = false;
				// fastIncrease50 = false;
				// fastIncrease140 = false;
			}

			while (ls.getLightValue() > 900 && ls.getLightValue() < 1400) {
				Main.pilot.forward();
			}

			while (ls.getLightValue() > 650 && ls.getLightValue() < 900) {
				// Move a bit forward from the line
				// if (!alreadyForwards && Math.abs(limitAngle) > 20) {
				// Main.pilot.forward();
				// Delay.msDelay(300);
				// alreadyForwards = true;
				// }

				if (tryLeft) {
					// Main.pilot.rotate(limitAngle);
					if (Math.abs(limitAngle) < 20) {
						Main.pilot.rotate(limitAngle);
						tryLeft = false;
						// Return to previous position
						Main.pilot.rotate(-limitAngle);	
					} else if (Math.abs(limitAngle) > 20) {
						Main.pilot.rotate(limitAngle);
						leftChecked = true;
					} else if (Math.abs(limitAngle) > 180) {

						System.out.println(limitAngle);
						Main.pilot.rotate(-limitAngle);
						limitAngle = angle;
						tryLeft = false;
					} else {
						Sound.playSample(pw, 25);
					}
					minus = false;
				} else {
					// Main.pilot.rotate(-limitAngle);
					if (Math.abs(limitAngle) < 20) {
						Main.pilot.rotate(-limitAngle);
						tryLeft = true;
						// Return to previous position
						Main.pilot.rotate(limitAngle);
					} else if (Math.abs(limitAngle) > 20) {
						Main.pilot.rotate(limitAngle);
						rightChecked = true;
					} else if (Math.abs(limitAngle) > 180) {

						System.out.println(limitAngle);
						Main.pilot.rotate(limitAngle);

						limitAngle = angle;
						tryLeft = true;
					} else {
						Sound.playSample(pw, 25);
					}

					minus = true;
				}

				// Increment angle steps
				/*
				 * if (limitAngle > 30 && !fastIncrease50) { limitAngle = 50;
				 * fastIncrease50 = true; } else if (limitAngle < -30 &&
				 * !fastIncrease50) { limitAngle = -50 - correction;
				 * fastIncrease50 = true; }
				 * 
				 * if (limitAngle > 90 && fastIncrease50 && !fastIncrease140) {
				 * limitAngle = 135; fastIncrease140 = true; } else if
				 * (limitAngle < -90 && fastIncrease50 && !fastIncrease140) {
				 * limitAngle = -135 - correction; fastIncrease140 = true; }
				 */

				limitAngle += angle;
			}

<<<<<<< HEAD
			// if (!minus && (limitAngle > (3 * angle))) {
			// currentTime = System.currentTimeMillis();
			// symbolTravelData.setAngle(limitAngle);
			// symbolTravelData.setTime(currentTime - startTime);
			// Main.symbolTravelDataList.add(symbolTravelData);
			// System.out.println(limitAngle);
			// System.out.println(currentTime - startTime);
			// } else if (minus && (limitAngle > (3 * angle))) {
			// currentTime = System.currentTimeMillis();
			// symbolTravelData.setAngle(limitAngle);
			// symbolTravelData.setTime(currentTime - startTime);
			// Main.symbolTravelDataList.add(symbolTravelData);
			// System.out.println(-limitAngle);
			// System.out.println(currentTime - startTime);
			// }
=======
			if (!minus && (limitAngle > (3 * angle))) {
				currentTime = System.currentTimeMillis();
				symbolTravelData.setAngle(limitAngle);
				symbolTravelData.setTime(currentTime - startTime);
//				Main.symbolTravelDataList.add(symbolTravelData);
				System.out.println(limitAngle);
				System.out.println(currentTime - startTime);
			} else if (minus && (limitAngle > (3 * angle))) {
				currentTime = System.currentTimeMillis();
				symbolTravelData.setAngle(limitAngle);
				symbolTravelData.setTime(currentTime - startTime);
//				Main.symbolTravelDataList.add(symbolTravelData);
				System.out.println(-limitAngle);
				System.out.println(currentTime - startTime);
			}
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git

			limitAngle = angle;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
