import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;


public class CollisionRight implements Behavior{
	private boolean suppressed = false;

	private TouchSensor TSleft;
	private TouchSensor TSright;
	private DifferentialPilot Pilot;
	
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
=======
		
		Main.pilot.rotate(20);
		
>>>>>>> d96543fedccc3fa21c735e66f5f0be64fa2690f7
//		Motor.B.rotateTo(0);
//		Motor.A.rotate(-360);
//		Motor.B.rotateTo(-15);
//		Motor.A.rotate(360);
<<<<<<< HEAD
		while(Main.pilot.isMoving() && !suppressed )
=======
		while(Motor.A.isMoving() && !suppressed )
>>>>>>> d96543fedccc3fa21c735e66f5f0be64fa2690f7
			Thread.yield();
		suppress();
//		Motor.B.rotateTo(0);
//		Motor.A.stop();
		LCD.clear();
	}
}

