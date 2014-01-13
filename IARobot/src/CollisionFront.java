import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class CollisionFront implements Behavior {
	private UltrasonicSensor us;
	private TouchSensor TSright;
	private TouchSensor TSleft;
	private boolean suppressed = false;

	public CollisionFront(SensorPort US, SensorPort SP1, SensorPort SP2) {
		us = new UltrasonicSensor(US);
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
		LCD.drawString("Es gibt ein object gerade aus !", 0, 0);

		if (Main.door) {
//			Motor.A.stop();
			Main.pilot.stop();
			Main.doorBumped = true;
//			Motor.A.rotate(-180);
			Main.pilot.travel(-10);
		}

		else {
			// Real Action
//			Motor.A.stop();
//			Motor.B.rotateTo(0);
//			Motor.A.rotate(-90);
			Main.pilot.stop();
			
			Main.pilot.travel(-10);
			
			Main.pilot.rotate(90);
			
//			if (us.getDistance() < 30) {
////				Motor.C.rotateTo(-90);
//				while (us.getDistance() < 30)
//					Motor.A.backward();
//				Motor.A.rotate(-540);
//				Motor.B.rotateTo(-25);
//				Motor.C.rotateTo(90);
//				Motor.A.rotate(540);
//			} else
//				Motor.A.rotate(-540);
		}

		while (Motor.A.isMoving() && !suppressed)
			Thread.yield();

		// Clean up
		suppress();
//		Motor.B.rotateTo(0);
//		Motor.A.stop();
		LCD.clear();

	}
}