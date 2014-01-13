import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLine2 implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;
	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B, true);

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
		suppressed = false;

		while (!suppressed) {
			if (ls.getLightValue() > 1100) {

			} else {

			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
