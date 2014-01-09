import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class CollisionFront implements Behavior{
	private UltrasonicSensor us;
	private TouchSensor TSright;
	private TouchSensor TSleft;
	private boolean suppressed = false;

	public boolean takeControl(){
		return (TSright.isPressed() && TSleft.isPressed());
   	}

	public void suppress(){
	suppressed = true;
	}

	public void action() {
	suppressed = false;
	//Real Action
	Motor.A.stop();
	Motor.B.rotateTo(0);
	Motor.A.rotate(-90);
	if(us.getDistance() < 30)
	{
		Motor.C.rotateTo(-90);
		while(us.getDistance() < 30)
			Motor.A.backward();
		Motor.A.rotate(-540);
		Motor.B.rotateTo(-25);
		Motor.C.rotateTo(90);
		Motor.A.rotate(540);
	}
	else 
		Motor.A.rotate(-540);

	while( Motor.A.isMoving() && !suppressed )
		Thread.yield();

	//Clean up
	Motor.B.rotateTo(0);
	Motor.A.stop();
   	}
}