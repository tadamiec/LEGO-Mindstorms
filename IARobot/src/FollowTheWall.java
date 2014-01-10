import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class FollowTheWall implements Behavior {
	private boolean suppressed = false;
	private boolean labSolved = false;
	private UltrasonicSensor us;
	private LightSensor ls;
	private int d1;
	private int d2;
	private int d3;


	public FollowTheWall(SensorPort US,SensorPort LS, int i, int j,int k,int Dark,int Light){
		us = new UltrasonicSensor(US);
		ls = new LightSensor(LS);
		ls.setHigh(Light);
		ls.setLow(Dark);
		d1 = i;
		d2 = j;
		d3 = k;
	}

	public boolean takeControl() {
		return !labSolved;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;

		LCD.clear();
		LCD.drawString("Ich führe ein Wand", 0, 0);

		Motor.C.rotateTo(90);

		Motor.A.forward();

		while(ls.getLightValue() < 1100 && !Button.ESCAPE.isDown() && !suppressed ){
			
			if( (us.getDistance() < d2 && us.getDistance() > d1) 
					|| (us.getDistance() < d3 && Motor.B.getTachoCount() >= 30))
				Motor.B.rotateTo(0);
			else if (us.getDistance() > d3)
				Motor.B.rotateTo(Math.min(2*(us.getDistance()-d3),30));
			else if (us.getDistance() > d2 && us.getDistance() < d3)
				Motor.B.rotateTo(Math.min(2*(us.getDistance()-d2),10));
			else if (us.getDistance() < d1)
				Motor.B.rotateTo(Math.max(2*(us.getDistance()-d1),-10) );
			
			
			
			
			Thread.yield();
		}
		
		if(ls.getLightValue() > 1100)
			labSolved = true;
		suppress();	
		LCD.clear();
		LCD.drawString("Ich mach nix", 0, 0);
		Motor.A.stop();
	}

}

