import lejos.nxt.*;
import lejos.robotics.objectdetection.*;

public class Test implements FeatureListener {
	public static int MAX_DETECT = 80;

	public static void main(String[] args) throws Exception {

		Test listener = new Test();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 500);
		fd.addListener(listener);

		// Button.ENTER.waitForPressAndRelease();

		
		while (!Button.ESCAPE.isDown()) {

			while(us.getDistance() < 50){
				System.out.println("search...");
				Motor.C.rotate(10);
			}
			System.out.println("Free Way !!");
				
			
		}
		//Button.waitForAnyPress();

	}

	@Override
	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int) feature.getRangeReading().getRange();
		System.out.println("Range:" + range);
		
	}
}
