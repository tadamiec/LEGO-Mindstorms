import java.util.Map;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

//import lejos.nxt.Sound;

public class SymbolsReader implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;
	private boolean fromLeft = false;
	private boolean alreadyForwards = false;
	private boolean minus = false;
	private boolean fastIncrease50 = false;
	private boolean fastIncrease140 = false;

	long startTime, currentTime;

	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B,
			true);

	SymbolTravelData symbolTravelData = new SymbolTravelData();

	// static File pw = new File("power_up_8bit.wav");

	public SymbolsReader(SensorPort LS, int Dark, int Light) {
		ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1000 && ls.getLightValue() < 1500);
	}

	@Override
	public void action() {
		suppressed = false;
		int angle = 7;
		int limitAngle = angle;
		int correction = 30;

		while (!suppressed) {
			startTime = System.currentTimeMillis();
			Main.pilot.forward();
			alreadyForwards = false;
			// fastIncrease50 = false;
			// fastIncrease140 = false;
			while (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				// Move a bit forward from the line
				if (!alreadyForwards && Math.abs(limitAngle) > 30) {
					Main.pilot.forward();
					// Sound.playSample(pw, 25);
					Delay.msDelay(300);
					alreadyForwards = true;
				}

				if (fromLeft) {
					findStrightLine(limitAngle);
					fromLeft = false;
					minus = false;
				} else {
					findStrightLine(-limitAngle);
					fromLeft = true;
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

			limitAngle = angle;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	private void findStrightLine(int limitAngle) {
		Main.pilot.rotate(limitAngle);

		if (!(ls.getLightValue() > 1000)) {
			Main.pilot.rotate(-limitAngle);
		}
	}

}
