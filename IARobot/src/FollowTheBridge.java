import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

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

		while (!(ls.getLightValue() > 600 && ls.getLightValue() < 900)
				&& !suppressed) {
			// Nothing
			if (ls.getLightValue() < 550) {
				Motor.A.forward();
				Motor.B.rotateTo(10);
				// Wood
			} else {
				Motor.A.forward();
				Motor.B.rotateTo(-10);
			}
			Thread.yield();
		}
		suppress();
		Motor.A.stop();
		Motor.B.rotateTo(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}