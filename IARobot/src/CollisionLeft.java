import lejos.nxt.*;
import lejos.robotics.subsumption.Behavior;


public class CollisionLeft implements Behavior {
	private boolean suppressed = false;

	private TouchSensor TSleft;
	private TouchSensor TSright;

	public CollisionLeft(SensorPort SP1, SensorPort SP2){
		TSleft = new TouchSensor(SP2);
		TSright = new TouchSensor(SP1);
	}

	public boolean takeControl() {
		return TSleft.isPressed() && !TSright.isPressed();
	}
	
	public void suppress() {
		suppressed = true;
		
	}
	
	public void action() {
		LCD.clear();
		LCD.drawString("Es gibt ein object an meinem Links", 0, 0);

		Main.pilot.rotate(-45);
	
<<<<<<< HEAD
=======

>>>>>>> 76b483a70ba482011d1e8e6de7a02861024d3751
		while(Main.pilot.isMoving() && !suppressed )
			Thread.yield();
		suppress();

		LCD.clear();

	}
}
