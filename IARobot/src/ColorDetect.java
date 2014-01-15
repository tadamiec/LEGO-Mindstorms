
import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class ColorDetect implements Behavior {
	private boolean suppressed = false;
	private TouchSensor rts;
	private TouchSensor lts;

	int collision_happened = 0;

	private LightSensor ls;
	private UltrasonicSensor us;
	
	public ColorDetect( SensorPort SP3, SensorPort SP4,int Dark, int Light,SensorPort SP1, SensorPort SP2) {
		rts = new TouchSensor(SP1);
		lts = new TouchSensor(SP2);
		ls = new LightSensor(SP3);
		us = new UltrasonicSensor(SP4);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	public boolean takeControl() {
		
		return (ls.getLightValue()>950);
	}

	public void suppress() {
		suppressed = true;

	}

	public void action() {
		LCD.clear();
		LCD.drawString("Mode : ColorDetect", 0, 0);
		suppressed = false;
		int tmp = 0;

		Main.pilot.travel(100);
		while (!(lts.isPressed() || rts.isPressed()) && !suppressed){
			//CENTERING

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

			//END ANGLE CORRECTION

			//FORWARD TO ELEVATOR

			while(ls.getLightValue()>600){
				Main.pilot.travel(200);
			}

			//WAIT FOR ELEVATOR
			LCD.clear();
			LCD.drawString("Ich warte für den aufzug", 0, 0);
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




			//GO INSIDE ELEVATOR
			LCD.clear();
			LCD.drawString("Go go go!", 0, 0);
			Main.pilot.forward();

			Thread.yield();
		}

	}
