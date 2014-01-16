import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.util.Delay;

public class ColorDetect implements Behavior {
	private boolean suppressed = false;
	private TouchSensor rts;
	private TouchSensor lts;

	int collision_happened = 0;

	private LightSensor ls;
	private UltrasonicSensor us;

	public ColorDetect(SensorPort SP3, SensorPort SP4, int Dark, int Light,
			SensorPort SP1, SensorPort SP2) {
		rts = new TouchSensor(SP1);
		lts = new TouchSensor(SP2);
		ls = new LightSensor(SP3);
		us = new UltrasonicSensor(SP4);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	public boolean takeControl() {
<<<<<<< HEAD

		return (ls.getLightValue() > 950);
=======
		
		return (Main.colorChanged || ls.getLightValue() >= 1650); //kritik
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
	}

	public void suppress() {
		suppressed = true;

	}

	public void action() {
		LCD.clear();
		LCD.drawString("Mode : ColorDetect", 0, 0);
		suppressed = false;
		int tmp = 0;

<<<<<<< HEAD
		Main.pilot.travel(100); // Problem?
		while (!suppressed) {

			// CENTERING
			while (ls.getLightValue() < 1000) { // Problem?
				Main.pilot.rotate(30);
				Main.pilot.rotate(-30);
			}
			Main.pilot.travel(100);
			// END CENTERING
=======
		Main.pilot.travel(50); // Problem?
		while (!suppressed){
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git

			// ANGLE CORRECTION
			while (us.getDistance() > 100 & tmp == 0) {
				Main.pilot.rotate(15);
			}

			if (tmp == 0) {
				Main.pilot.rotate(-55);
			}
			tmp = 1;
			// END ANGLE CORRECTION

<<<<<<< HEAD
			// FORWARD TO ELEVATOR
			while (ls.getLightValue() > 600) {
=======
			//FORWARD TO ELEVATOR
			while(ls.getLightValue()>1000){
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
				Main.pilot.forward();
			}

			// WAIT FOR ELEVATOR
			LCD.clear();
<<<<<<< HEAD
			LCD.drawString("Ich warte für den aufzug", 0, 0);
			while (ls.getLightValue() < 800) {
=======
			LCD.drawString("Ich warte fur den aufzug", 0, 0);
			while (ls.getLightValue() < 1450){
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
				Main.pilot.stop();
			}

<<<<<<< HEAD
			Thread.yield();
		}

=======
			//GO INSIDE ELEVATOR
			LCD.clear();
			LCD.drawString("Go go go!", 0, 0);
			Main.pilot.travel(120);
			Delay.msDelay(13000);
			Main.pilot.travel(300);
			Thread.yield();
			}
			
		}
		
		
		



//		while (Main.pilot.isMoving() && !suppressed)
//			Thread.yield();
//
//		// Clean up
//		suppress();
////		Motor.B.rotateTo(0);
////		Motor.A.stop(
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
	}

	// while (Main.pilot.isMoving() && !suppressed)
	// Thread.yield();
	//
	// // Clean up
	// suppress();
	// // Motor.B.rotateTo(0);
	// // Motor.A.stop();

	// GO INSIDE ELEVATOR
	/*
	 * LCD.clear(); LCD.drawString("Go go go!", 0, 0); Main.pilot.forward();
	 * 
	 * Thread.yield();
	 */

}
