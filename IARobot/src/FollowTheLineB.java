import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLineB implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	public FollowTheLineB(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 600 && ls.getLightValue() < 900);
	}

	@Override
	public void action() {
		suppressed = false;
		Motor.A.setSpeed(100);
		Motor.A.forward();

		while ((ls.getLightValue() > 600 && ls.getLightValue() < 900)
				&& !suppressed) {

			if (Motor.A.getTachoCount() > 0) {
				turnTarpaulin(-10);
			} else {
				turnTarpaulin(10);
			}
			Thread.yield();
		}
		// File pw = new File("power_up_8bit.wav");
		// Sound.playSample(pw,50);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	static int LastLine, LastTarpoulin;

	public static void turnLine(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
	}
}