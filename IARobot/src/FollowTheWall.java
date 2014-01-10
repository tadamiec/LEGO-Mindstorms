import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class FollowTheWall implements Behavior {
	private boolean suppressed = false;
	private UltrasonicSensor us;
	private int d1;
	private int d2;

	public FollowTheWall(SensorPort US, int i, int j){
		us = new UltrasonicSensor(US);
		d1 = i;
		d2 = j;
	}

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
		Motor.A.stop();
	}

	public void action() {

		suppressed = false;

		Motor.C.rotateTo(90);

		Motor.A.forward();


		//Motor.B.rotateTo(0);

		while(!Button.ESCAPE.isDown() && !suppressed ){
			if( us.getDistance() < d2 && us.getDistance() > d1)
				Motor.B.rotateTo(0);
			else if (us.getDistance() > d2)
				Motor.B.rotateTo(Math.min(2*(us.getDistance()-d2),30));
			else if (us.getDistance() < d1)
				Motor.B.rotateTo(Math.max(2*(us.getDistance()-d1),-30) );
			Thread.yield();
		}
		
		suppress();
		
		

	}

}

