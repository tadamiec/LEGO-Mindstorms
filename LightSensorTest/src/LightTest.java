import lejos.nxt.*;
import lejos.robotics.objectdetection.*;

public class LightTest {
	
	public static void main(String[] args) {
		LightSensor LS = new LightSensor(SensorPort.S1);
		
		while(!Button.ENTER.isDown())
			System.out.println("light:" + LS.getLightValue());
		
		
	}
	
}
