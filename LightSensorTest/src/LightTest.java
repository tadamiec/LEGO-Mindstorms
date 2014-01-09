import lejos.nxt.*;
import lejos.robotics.objectdetection.*;

public class LightTest {
	
	public static void main(String[] args) {
		LightSensor LS = new LightSensor(SensorPort.S3);
		
		Button.ENTER.waitForPressAndRelease();
		
		while(!Button.ESCAPE.isDown()){
			if(LS.getLightValue() < 25){
				Motor.A.forward();
				Motor.B.rotateTo(10);
			} else {
				Motor.A.forward();
				Motor.B.rotateTo(-10);
			}
		}
		
	}
	
}