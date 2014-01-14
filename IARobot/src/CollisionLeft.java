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

<<<<<<< HEAD
		Main.pilot.rotate(-45);

	
=======
		//		Motor.B.rotateTo(0);
//		Motor.A.rotate(-360);
//		Motor.B.rotateTo(15);
//		Motor.A.rotate(360);
>>>>>>> 2fbb4f186f4ed65ee8afb3e41c674c147e487945
		while(Main.pilot.isMoving() && !suppressed )
			Thread.yield();
		suppress();

		LCD.clear();

	}
}
