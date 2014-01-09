import lejos.nxt.*;
import lejos.robotics.subsumption.*;


public class CollisionRight implements Behavior{
	private boolean suppressed = false;

	private TouchSensor TSleft;
	private TouchSensor TSright;

	public CollisionRight(SensorPort SP1, SensorPort SP2){
		TSleft = new TouchSensor(SP1);
		TSright = new TouchSensor(SP2);
	}

	public boolean takeControl() {
		return !TSleft.isPressed() || TSright.isPressed();
	}
	
	public void suppress() {
		suppressed = true;
	}
	
	public void action() {
		Motor.B.rotateTo(0);
		Motor.A.rotate(-360);
		Motor.B.rotateTo(30);
		Motor.A.rotate(360);
		while( !suppressed )
			Thread.yield();

	}
}

