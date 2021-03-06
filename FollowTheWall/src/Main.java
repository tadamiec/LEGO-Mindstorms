import lejos.nxt.*;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.util.Delay;
import java.util.*;


public class Main {
	
	
	/**
	 * @param args
	 */
	
	public static void back(UltrasonicSensor us){
		Motor.A.stop();
		Motor.B.rotateTo(0);

		Motor.A.rotate(-90);
		
		if(us.getDistance() < 30){
		
			Motor.C.rotateTo(-90);
			while(us.getDistance() < 30){
//			if( us.getDistance() < d2 && us.getDistance() > d1){ 
//				System.out.println("Gerade aus !!!");
//				Motor.B.rotateTo(0);
//			}
//			else if (us.getDistance() > d2) {								
//				Motor.B.rotateTo(Math.max(4*(d2- us.getDistance()),-30 ));
//			}
//			else if (us.getDistance() < d1){
//
//				Motor.B.rotateTo(Math.min(4*(d1 - us.getDistance()),30) );
//			}
				Motor.A.backward();
			}

			Motor.A.rotate(-540);
			Motor.C.rotateTo(90);
			Motor.B.rotateTo(-25);
			Motor.A.rotate(540);
		}else {
			Motor.A.rotate(-540);

		}
	}
	
	public static void collision(TouchSensor TSright ,TouchSensor TSleft,UltrasonicSensor us){	
//		int d1 = 13;
//		int d2 = 20;
//		
		Delay timer = new Delay();
		
		
		if(TSright.isPressed() && !TSleft.isPressed() ){
			timer.msDelay(250);
			if(TSleft.isPressed())
				back(us);
			else{
			Motor.A.stop();

			Motor.B.rotateTo(0);
			Motor.A.rotate(-360);
			Motor.B.rotateTo(-30);
			Motor.A.rotate(360);
			}
		}
		if(TSleft.isPressed() && !TSright.isPressed()){
			timer.msDelay(250);
			if(TSright.isPressed())
				back(us);
			else{
			Motor.A.stop();
			Motor.B.rotateTo(0);
			Motor.A.rotate(-360);
			Motor.B.rotateTo(30);
			Motor.A.rotate(360);
				}
		}
		
		if(TSright.isPressed() && TSleft.isPressed())
			back(us);
		
		
		
		
	}	
	
	public static void main(String[] args) {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		TouchSensor TSright = new TouchSensor(SensorPort.S1);
		TouchSensor TSleft  = new TouchSensor(SensorPort.S2);
		Motor.C.rotateTo(90);
//		float alpha = 3/5;
		int d1 = 14;
		int d2 = 18;
		Motor.A.setSpeed(360);
		while(!Button.ESCAPE.isDown()){
			
			

//			if( us.getDistance() < d2 && us.getDistance() > d1){ 
//				collision(TSright,TSleft,us);
//				System.out.println("Gerade aus !!!");
//				Motor.B.rotateTo(0);
//			}
			if (us.getDistance() > d2) {
				collision(TSright,TSleft,us);
				
//				System.out.println((us.getDistance()-d2));
				
				Motor.B.rotateTo(10);
				
//				Motor.B.rotateTo(Math.min(2*(us.getDistance()-d2),30));
				

			}
			else if (us.getDistance() < d1){
				collision(TSright,TSleft,us);
//				System.out.println((us.getDistance()-d1));
				Motor.B.rotateTo(-10);

//				Motor.B.rotateTo(Math.max(2*(us.getDistance()-d1),-30) );
			}
//			else if (us.getDistance() > d2){ //if (us.getDistance() > 20 && us.getDistance() <=40){ 
//				turn(TSright,TSleft,us);
//				Motor.B.rotateTo(20);
//				Motor.A.rotate(360);
//			}
//			else { 
//				Motor.B.rotateTo(0);
//				Motor.A.forward();
//			}
			Motor.B.rotateTo(0);
			Motor.A.forward();
	
	}
}
		

}

