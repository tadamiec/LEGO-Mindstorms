import lejos.nxt.Button;
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

		while (!Button.ESCAPE.isDown() && !suppressed) {
			Main.pilot.setTravelSpeed(100); // cm per second
			Main.pilot.forward(); // cm
			Main.pilot.rotate(-90); // degree clockwise
			Main.pilot.forward(); // cm

//			Main.pilot.steer(100); // turns with left wheel stationary
			Delay.msDelay(1000);
			Main.pilot.rotate(90); // degree clockwise
			Main.pilot.forward(); // cm


			//Main.pilot.stop();
			Thread.yield();
		}
		suppress();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
