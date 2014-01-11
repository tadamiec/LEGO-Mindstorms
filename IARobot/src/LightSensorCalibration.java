import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;


public class LightSensorCalibration implements Behavior{
	private boolean suppress = false;
	private boolean calibration = false;
	private LightSensor ls;
	

	
	public LightSensorCalibration(SensorPort SP){
		ls = new LightSensor(SP);
	}
	
	public boolean takeControl() {
		return Button.RIGHT.isDown() || calibration;
	}

	public void action() {
		calibration = true;
		LCD.drawString("Dark value", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		LCD.drawInt(ls.getLightValue(),0,0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		LCD.drawString("Light value", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		LCD.drawInt(ls.getLightValue(),0,0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		suppress();
		calibration = false;

	}

	public void suppress() {
		suppress = true;
	}
}