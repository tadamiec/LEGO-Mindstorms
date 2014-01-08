import lejos.nxt.*;
import lejos.robotics.objectdetection.*;
import lejos.util.Delay;

public class Test implements FeatureListener {
	public static int MAX_DETECT = 80;

	public static void main(String[] args) throws Exception {

		Test listener = new Test();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		TouchSensor TSright = new TouchSensor(SensorPort.S1);
		TouchSensor TSleft  = new TouchSensor(SensorPort.S2);
		RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 500);
		fd.addListener(listener);
		Delay timer = new Delay();
		// Button.ENTER.waitForPressAndRelease();

		Button.ENTER.waitForPressAndRelease();
		
		System.out.println("Prototy 2 : Evasion Test");
		boolean rightTested = false;
		Motor.A.setSpeed(360);
		Motor.B.rotateTo(0);

		while(!Button.ESCAPE.isDown()){
			while(us.getDistance() < 50 && !TSleft.isPressed() && !TSright.isPressed()){
				if(!rightTested){
					Motor.A.rotate(-720);
					timer.msDelay(1000);
					Motor.A.forward();
					Motor.B.rotateTo(30);
					rightTested = true;
					timer.msDelay(1000);

				}
				else{
					Motor.A.rotate(-720);
					timer.msDelay(1000);
					Motor.A.forward();
					Motor.B.rotateTo(-30);
					rightTested = false;
					timer.msDelay(1000);

				}
//				if(TSleft.isPressed() || TSright.isPressed())
//					break;
			}
			
//			if(ts.isPressed()){
//				Motor.B.rotateTo(30);
//				Motor.A.backward();
//				timer.msDelay(2000);
//				rightTested = true;
//			}
//			else{
//				if(rightTested){
//					Motor.B.rotateTo(-30);
//					Motor.A.forward();
//					timer.msDelay(2000);
//					rightTested = false;
//					Motor.B.rotateTo(0);
//				}
//				Motor.A.forward();
//			}
			
			while(TSright.isPressed() && !TSleft.isPressed()){
				Motor.B.rotateTo(-10);
				timer.msDelay(2000);
			}
			while(TSleft.isPressed() && !TSright.isPressed()){
				Motor.B.rotateTo(10);
				timer.msDelay(2000);
			}
			while(TSleft.isPressed() && TSright.isPressed()){
				Motor.A.backward();
				timer.msDelay(2000);
			}
			Motor.B.rotateTo(0);
			Motor.A.forward();

//			if(Button.ENTER.isDown()){
//				Motor.A.forward();
//			}
//			if(Button.RIGHT.isDown()){
//				Motor.B.rotate(2);
//			}
//			if(Button.LEFT.isDown()){
//				Motor.B.rotate(-2);
//			}
		}
		
		//Button.waitForAnyPress();

	}

	@Override
	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int) feature.getRangeReading().getRange();
		System.out.println("Range:" + range);
		
	}
}
