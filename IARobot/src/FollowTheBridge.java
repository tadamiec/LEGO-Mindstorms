import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

import java.io.*;

public class FollowTheBridge implements Behavior {
	private boolean suppressed = false;
	private boolean gapFound = false;
	private LightSensor ls;
	private int step = 10;

	// private boolean adjustmentNeeded = true;

	public FollowTheBridge(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (/* ls.getLightValue() > 950 && ls.getLightValue() < 1060 */true);
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("Mode : FollowTheBridge", 0, 0);
		suppressed = false;
		File pw = new File("power_up_8bit.wav");

		while (!suppressed) {

			// while (ls.getLightValue() > 600 && !gapFound)
			// Main.pilot.forward();
			//
			// if (!gapFound) {
			// Sound.playSample(pw, 25);
			// Main.pilot.rotate(-45);
			// gapFound = true;
			// }

			// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// START

			int i = 0;
			while (ls.getLightValue() > 1000 && i < 5) {
				Main.pilot.rotate(step);
				i++;
			}
			if (ls.getLightValue() > 1000) {
				while (ls.getLightValue() > 1000) {
					Main.pilot.forward();
				}
				Main.pilot.travel(-30);
				Main.pilot.rotate(-(i + 1) * step);
			} else {
				Main.pilot.travel(-30);
				Main.pilot.rotate(-(i + 1) * step);
			}
			Main.pilot.travel(150);

			int l1 = ls.getLightValue();
			Delay.msDelay(100);
			int l2 = ls.getLightValue();
			Delay.msDelay(100);
			int l3 = ls.getLightValue();
			Delay.msDelay(100);
			int l4 = ls.getLightValue();
			Delay.msDelay(100);
			int l5 = ls.getLightValue();
			Delay.msDelay(100);
			int l6 = ls.getLightValue();
			Delay.msDelay(100);
			int l7 = ls.getLightValue();
			Delay.msDelay(100);
			int l8 = ls.getLightValue();
			Delay.msDelay(100);
			int l9 = ls.getLightValue();
			Delay.msDelay(100);
			int l10 = ls.getLightValue();
			Delay.msDelay(100);
			int l11 = ls.getLightValue();

			int[] l = { l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11 };

			for (int j = 0; j < 10; j++) {
				if (Math.abs(l[j] - l[j + 1]) > 100) {
					Main.colorChanged = true;
				}

			}


			Thread.yield();

			// LOOP
			// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		}

		suppress();

		LCD.drawString("OUT OF FB", 0, 0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
