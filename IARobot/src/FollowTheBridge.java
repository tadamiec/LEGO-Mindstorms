import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import java.io.*;

public class FollowTheBridge implements Behavior {
	private boolean suppressed = false;
	private boolean gapFound = false;
	private LightSensor ls;

	public FollowTheBridge(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 950 && ls.getLightValue() < 1060);
	}

	@Override
	public void action() {
		suppressed = false;
		Motor.A.setSpeed(180);
		Motor.B.rotateTo(0);
		Motor.A.forward();
		File pw = new File("power_up_8bit.wav");

		while (  ls.getLightValue() < 1200
				&& !suppressed ) {
			// Wood
			while (ls.getLightValue() > 600 && !gapFound) {
				Motor.A.stop();
				Motor.B.rotateTo(-10);
				Motor.A.rotate(80);
				Motor.B.rotateTo(10);
				Motor.A.rotate(60);
			}
			if (!gapFound){
				Sound.playSample(pw, 25);
				Motor.B.rotateTo(30);
			}
			gapFound = true;
			Motor.A.forward();
			if (ls.getLightValue() < 600) {
				Motor.B.rotate(5);
			} else {
				Motor.B.rotate(-5);
			}
			Thread.yield();
			LCD.drawInt(ls.getLightValue(), 0, 0);
		}
		suppress();

		Motor.A.stop();
		Motor.B.rotateTo(0);
		LCD.drawString("OUT", 1, 1);
		LCD.drawInt(ls.getLightValue(), 2, 2);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}