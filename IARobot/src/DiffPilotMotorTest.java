import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class DiffPilotMotorTest implements Behavior {
	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;

		while (!suppressed) {
			Main.pilot.setTravelSpeed(30); // cm per second
			Main.pilot.travel(50); // cm
			Main.pilot.rotate(-90); // degree clockwise
			Main.pilot.steer(100); // turns with left wheel stationary
			Delay.msDelay(1000);
			Main.pilot.stop();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
