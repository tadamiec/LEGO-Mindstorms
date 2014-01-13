import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class DiffPilotMotorTest implements Behavior {
	private boolean suppressed = false;
	private DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A,
			Motor.B, true);

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;

		while (!suppressed) {
			pilot.setTravelSpeed(30); // cm per second
			pilot.travel(50); // cm
			pilot.rotate(-90); // degree clockwise
			pilot.steer(100); // turns with left wheel stationary
			Delay.msDelay(1000);
			pilot.stop();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
