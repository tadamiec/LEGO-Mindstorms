import lejos.nxt.*;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.util.Delay;

public class Main {
	
	
	/**
	 * @param args
	 */
	public static void turn(TouchSensor TSright ,TouchSensor TSleft,UltrasonicSensor us){	
		if(TSright.isPressed() && !TSleft.isPressed() ){
			Motor.B.rotateTo(20);
			Motor.A.rotate(-360);
			Motor.B.rotateTo(0);
				}
		if(TSleft.isPressed() && !TSright.isPressed()){
			Motor.B.rotateTo(-20);
			Motor.A.rotate(-360);
			Motor.B.rotateTo(20);
			Motor.A.rotate(360);
				}
		
		if(TSright.isPressed() && TSleft.isPressed()){
			Motor.B.rotateTo(0);

			Motor.A.rotate(-360);
			
			Motor.C.rotateTo(-90);
			while(us.getDistance() < 30){
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
		TouchSensor TSleft  = new TouchSensor(SensorPort.S2);
		Motor.C.rotateTo(90);

		while(!Button.ESCAPE.isDown()){
			
			
			if( us.getDistance() < 30 ){ 
				turn(TSright,TSleft,us);
				
				Motor.B.rotateTo(0);
				Motor.A.forward();
			}
			else{ //if (us.getDistance() > 20 && us.getDistance() <=40){ 
				turn(TSright,TSleft,us);
				Motor.B.rotateTo(20);
				Motor.A.rotate(360);
			}
//			else { 
//				Motor.B.rotateTo(0);
//				Motor.A.forward();
//			}
		
	
	}
}
		

}

