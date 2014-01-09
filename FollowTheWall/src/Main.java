import lejos.nxt.*;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.util.Delay;

public class Main {

	/**
	 * @param args
	 */
	public static void turn(TouchSensor TSright, TouchSensor TSleft,
			UltrasonicSensor us) {
		if (TSright.isPressed() && !TSleft.isPressed()) {
			Motor.B.rotateTo(20);
			Motor.A.rotate(-360);
			Motor.B.rotateTo(0);
		}
		if (TSleft.isPressed() && !TSright.isPressed()) {
			Motor.B.rotateTo(0);
			Motor.A.rotate(-360);
			Motor.B.rotateTo(20);
			Motor.A.rotate(360);
		}

		if (TSright.isPressed() && TSleft.isPressed()) {
			Motor.B.rotateTo(0);

			Motor.A.rotate(-360);

			Motor.C.rotateTo(-90);
			while (us.getDistance() < 30) {
				Motor.A.backward();
			}
			Motor.C.rotateTo(90);
			Motor.B.rotateTo(-20);
			Motor.A.rotate(720);
		}
	}

	public static void main(String[] args) {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		TouchSensor TSright = new TouchSensor(SensorPort.S1);
		TouchSensor TSleft = new TouchSensor(SensorPort.S2);
		Motor.C.rotateTo(90);
		// float alpha = 3/5;
		int d1 = 13;
		int d2 = 20;
		Motor.A.setSpeed(360);

		while(!Button.ESCAPE.isDown()){
			Motor.B.rotateTo(0);

			//straight
			if( us.getDistance() < d2 && us.getDistance() > d1){ 
				turn(TSright,TSleft,us);
				System.out.println("Gerade aus !!!");

				Motor.A.forward();
			//right
			} else if (us.getDistance() > d2) {
				turn(TSright, TSleft, us);

				System.out.println((us.getDistance() - d2));
				Motor.B.rotateTo(4 * (us.getDistance() - d2));
				Motor.A.forward();
			//left
			} else if (us.getDistance() < d1) {
				turn(TSright, TSleft, us);
				System.out.println((us.getDistance() - d1));

				Motor.B.rotateTo(4 * (us.getDistance() - d1));
				Motor.A.forward();
			}
			// else if (us.getDistance() > d2){ //if (us.getDistance() > 20 &&
			// us.getDistance() <=40){
			// turn(TSright,TSleft,us);
			// Motor.B.rotateTo(20);
			// Motor.A.rotate(360);
			// }
			// else {
			// Motor.B.rotateTo(0);
			// Motor.A.forward();
			// }

		}
	}
}
