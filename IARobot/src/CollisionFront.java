import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.util.Delay;

public class CollisionFront implements Behavior {
	private TouchSensor TSright;
	private TouchSensor TSleft;
	private boolean suppressed = false;

	public CollisionFront( SensorPort SP1, SensorPort SP2) {
		TSleft = new TouchSensor(SP2);
		TSright = new TouchSensor(SP1);
	}

	public boolean takeControl() {
		return (TSright.isPressed() && TSleft.isPressed());
	}

	public void suppress() {
		suppressed = true;

	}

	public void action() {
		suppressed = false;
		LCD.clear();
		LCD.drawString("Mode : CollisionFront", 0, 0);

		if (Main.level == 42){
			Main.pilot.travel(-30);
			Delay.msDelay(10000); //Problem?
			Main.pilot.travel(200);
		}

		else {

			if (Main.door) {
				Main.pilot.stop();
				Main.doorBumped = true;
				Main.pilot.travel(-10);
			}

			else {
				Main.pilot.stop();
				Main.pilot.travel(-10);
				Main.pilot.rotate(90);
			}
		}

		while (Main.pilot.isMoving() && !suppressed)
			Thread.yield();

		// Clean up
		suppress();
		LCD.clear();
	}
}
