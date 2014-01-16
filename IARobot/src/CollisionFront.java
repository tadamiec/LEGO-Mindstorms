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

<<<<<<< HEAD
//		if (Main.level == 42){
//			Main.pilot.travel(-10);
//			Delay.msDelay(10000);
//			Main.pilot.travel(200);
//		}
=======
<<<<<<< HEAD
		if (Main.level == "Labyrinth"){
			Main.pilot.travel(-10);
			Delay.msDelay(10000);
=======
		if (Main.level == 42){
			Main.pilot.travel(-30);
			Delay.msDelay(10000); //Problem?
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
			Main.pilot.travel(200);
		}
>>>>>>> bf5cc9529c48e32a00b959278c0b04e1c8f979c7

//		else {

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
		

		while (Main.pilot.isMoving() && !suppressed)
			Thread.yield();

		// Clean up
		suppress();
		LCD.clear();
	}
}
