import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;



public class LevelBarCode implements Behavior{
	private boolean suppressed = false;
	private LightSensor ls;
	private boolean EndBarcode = false;

	public LevelBarCode(SensorPort SP,int Dark,int Light){
		ls = new LightSensor(SP);
		ls.setHigh(Light);
		ls.setLow(Dark);
	}
	
	public boolean takeControl() {
		return !EndBarcode;
	}

	public void action() {
		suppressed = false;
		boolean wasWhite = false;
		boolean wasBlack = true;
		
		long Time = System.currentTimeMillis();
		while(!EndBarcode && !suppressed){
			System.out.println(ls.getLightValue());
			Main.pilot.forward();
			if((wasWhite && ls.getLightValue() < 1700)){
				wasWhite = false;
				wasBlack = true;
				Time = System.currentTimeMillis();

			}
			else if(wasBlack && ls.getLightValue() > 1700){
				wasWhite = true;
				wasBlack = false;
				Time = System.currentTimeMillis();
			}
			else if((System.currentTimeMillis() - Time) > 1000){
				EndBarcode = true;
			}
				
			Thread.yield();
		}
		suppress();
		Main.level++;
		Main.pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
