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
		Main.pilot.rotate(45);

		while(Main.pilot.isMoving() && !suppressed )
			Thread.yield();
		suppress();

		LCD.clear();
	}
}

