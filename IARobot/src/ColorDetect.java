
import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class ColorDetect implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;
	private UltrasonicSensor us;
	private TouchSensor rts;
	private TouchSensor lts;
	int collision_happened = 0;
	
	public ColorDetect( SensorPort SP3, SensorPort SP4,int Dark, int Light,SensorPort SP1, SensorPort SP2) {
		lts = new TouchSensor(SP2);
		rts = new TouchSensor(SP3);
		us = new UltrasonicSensor(SP4);
		this.ls = new LightSensor(SP3);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	public boolean takeControl() {
		
		return (true);
	}

	public void suppress() {
		suppressed = true;

	}

	public void action() {
		suppressed = false;
		int tmp = 0;
		LCD.clear();
		LCD.drawString("am aufzug", 0, 0);
		while (!suppressed){
			while (us.getDistance()>100 & tmp == 0)
			{
			Main.pilot.rotate(15);
			}
			
			if (tmp == 0){
				Main.pilot.rotate(-55);
			}
			tmp = 1;
			
//			if (ls.getLightValue() > 600) {
//				Main.pilot.forward();
//			}
//			else 
//				Main.pilot.stop();
			while (!(lts.isPressed() || rts.isPressed()) && collision_happened == 0)
			{
			while(ls.getLightValue()>600){
				Main.pilot.travel(200);
			}
			while (ls.getLightValue() < 800){
				Main.pilot.stop();
			}
			}
			while (lts.isPressed() || rts.isPressed())
			{
				Main.pilot.travel(-50);
				Main.pilot.stop();
				collision_happened = 1;
			}
			
			Thread.yield();
		}
		
		
		



//		while (Main.pilot.isMoving() && !suppressed)
//			Thread.yield();
//
//		// Clean up
//		suppress();
////		Motor.B.rotateTo(0);
////		Motor.A.stop();
		LCD.clear();

	}
}