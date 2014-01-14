import lejos.nxt.*;
import lejos.robotics.subsumption.*;


public class CollisionRight implements Behavior{
	private boolean suppressed = false;

	private TouchSensor TSleft;
	private TouchSensor TSright;
	
	public CollisionRight(SensorPort SP1, SensorPort SP2){
		TSleft = new TouchSensor(SP2);
		TSright = new TouchSensor(SP1);
	}

	public boolean takeControl() {
		return !TSleft.isPressed() && TSright.isPressed();
	}
	
	public void suppress() {
		suppressed = true;
		
	}
	
	public void action() {
		LCD.clear();
		LCD.drawString("Es gibt ein object an meinem Recht", 0, 0);
<<<<<<< HEAD
		Main.pilot.rotate(45);

		while(Main.pilot.isMoving() && !suppressed )
=======

>>>>>>> 2fbb4f186f4ed65ee8afb3e41c674c147e487945
			Thread.yield();
		suppress();

		LCD.clear();
	}
}

