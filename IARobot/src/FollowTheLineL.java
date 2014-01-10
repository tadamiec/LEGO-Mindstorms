import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLineL implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	public FollowTheLineL(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1100);
	}

	@Override
	public void action() {
		suppressed = false;
		Motor.A.setSpeed(100);
		Motor.A.forward();

		while ((ls.getLightValue() > 1100) && !suppressed) {
			if (Motor.A.getTachoCount() > 0) {
				turnLine(-10);
			} else {
				turnLine(10);
			}
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	public static void turnLine(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
	}
}