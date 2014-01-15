import java.io.File;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

//import lejos.nxt.Sound;

public class FollowTheLine2 implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;
	private boolean fromLeft = false;
	private boolean alreadyForwards = false;
	private boolean minus = false;

	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B,
			true);

	static File pw = new File("power_up_8bit.wav");

	public FollowTheLine2(SensorPort LS, int Dark, int Light) {
		ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1100 && ls.getLightValue() < 1500);
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("Mode : FollowTheBridge", 0, 0);
		suppressed = false;
		int angle = 10;
		int limitAngle = angle;

		while (!suppressed) {
			Main.pilot.forward();
			alreadyForwards = false;
			while (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				if (fromLeft) {
					findStrightLine(limitAngle);
					fromLeft = false;
					minus = false;
				} else {
					findStrightLine(-limitAngle);
					fromLeft = true;
					minus = true;
				}
				limitAngle += angle;

				// Move a bit forward from the line
				if (!alreadyForwards) {
					Main.pilot.forward();
					// Sound.playSample(pw, 25);
					Delay.msDelay(200);
					alreadyForwards = true;
				}
			}

			if (!minus && (limitAngle > (3 * angle))) {
				// Main.angleList.add(limitAngle);
				System.out.println(limitAngle);
			} else if (minus && (limitAngle > (3 * angle))) {
				// Main.angleList.add(-limitAngle);
				System.out.println(-limitAngle);
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

		if (!(ls.getLightValue() > 1100)) {
			Main.pilot.rotate(-limitAngle);
		}
	}

}
