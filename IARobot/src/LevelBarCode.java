import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;


public class LevelBarCode implements Behavior{
	private boolean suppressed = false;
	private LightSensor ls;
	
	public LevelBarCode(SensorPort SP,int Dark,int Light){
		ls = new LightSensor(SP);
		ls.setHigh(Light);
		ls.setLow(Dark);
	}
	
	public boolean takeControl() {
		return true;
	}

	public void action() {
		suppressed = false;
		boolean wasWhite = false;
		boolean wasBlack = true;
		
		boolean EndBarcode = false;
		long Time = System.currentTimeMillis();
		
		while(!EndBarcode && !suppressed){
			Main.pilot.forward();
			if((wasWhite && ls.getLightValue() < 1000)){
				wasWhite = false;
				wasBlack = true;
				Time = System.currentTimeMillis();

			}
			else if(wasBlack && ls.getLightValue() > 1000){
				wasWhite = true;
				wasBlack = false;
				Time = System.currentTimeMillis();
			}
			else if((System.currentTimeMillis() - Time) > 3000){
				EndBarcode = true;
			}
				
			Thread.yield();
		}
		suppress();
		Main.level++;		
	}

	@Override
	public void suppress() {
		
	}
	
}
