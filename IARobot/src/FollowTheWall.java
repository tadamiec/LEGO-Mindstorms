import java.io.File;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.AngleSensor;
import lejos.robotics.subsumption.Behavior;

public class FollowTheWall implements Behavior {
	private boolean suppressed = false;
	private boolean labSolved = false;
	private UltrasonicSensor us;
	private LightSensor ls;

	public FollowTheWall(SensorPort US, SensorPort LS, int Dark, int Light) {
		us = new UltrasonicSensor(US);
		ls = new LightSensor(LS);
		ls.setHigh(Light);
		ls.setLow(Dark);

	}

	public boolean takeControl() {
		return !labSolved;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;

		LCD.clear();
		LCD.drawString("Ich führe ein Wand", 0, 0);

		while (/* ls.getLightValue() < 1200 && */!Button.ESCAPE.isDown()
				&& !suppressed) {
			Main.pilot.forward();


			if(us.getDistance() > 40){

				Main.pilot.travel(100);
				Main.pilot.rotate(-90);
				while (us.getDistance() > 40) {
					Main.pilot.forward();
				}
				Main.pilot.travel(50);
			}

			
			Thread.yield();
		}
		File pw = new File("power_up_8bit.wav");
		Sound.playSample(pw, 0);

		suppress();
		LCD.clear();
		LCD.drawString("Ich mach nix", 0, 0);
		Main.pilot.stop();
	}

}
