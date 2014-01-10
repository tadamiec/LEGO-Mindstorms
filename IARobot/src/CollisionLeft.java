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
		Motor.B.rotateTo(0);
		Motor.A.stop();
	}
	
	public void action() {
		Motor.B.rotateTo(0);
		Motor.A.rotate(-360);
		Motor.B.rotateTo(15);
		Motor.A.rotate(360);
		while(Motor.A.isMoving() && !suppressed )
			Thread.yield();
		suppress();

	}
}
