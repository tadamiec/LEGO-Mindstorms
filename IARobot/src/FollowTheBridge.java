import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;
import java.io.*;

public class FollowTheBridge implements Behavior {
	private boolean suppressed = false;
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
		Motor.A.setSpeed(100);
		Motor.A.forward();

		while (((ls.getLightValue() > 430 && ls.getLightValue() < 550) || (ls.getLightValue() > 950 && ls.getLightValue() < 1060))
				&& !suppressed) {
			// Wood
			while (ls.getLightValue() >950) {
				Motor.A.stop();
				Motor.B.rotateTo(-10);
				Motor.A.rotate(180);
				Motor.B.rotateTo(0);
				Motor.A.rotate(180);
			} 
			// Gap
			Motor.A.stop();
			Motor.B.rotateTo(10);
			Motor.A.forward();
			}
			Thread.yield();
		}
		suppress();
		Motor.A.stop();
		Motor.B.rotateTo(0);
		LCD.clear();
		LCD.drawString("OUT", 0, 0);
		File pw = new File("power_up_8bit.wav");
		Sound.playSample(pw,0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}