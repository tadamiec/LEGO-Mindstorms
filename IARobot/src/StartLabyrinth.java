import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;


public class StartLabyrinth implements Behavior {
	private boolean inLabyrinth = false;
	private boolean suppressed = false;
	private boolean wasBlack = false;
	private boolean wasWhite = true;
	private LightSensor ls;
	
	public StartLabyrinth(SensorPort SP, int Dark, int Light){
		ls = new LightSensor(SP);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}
	
	@Override
	public boolean takeControl() {
		return !inLabyrinth;
	}

	@Override
	public void action() {
		suppressed = false;
		
		Motor.B.rotateTo(0);
		boolean EndBarcode = false;
		long Time = System.currentTimeMillis();
		
		while(!EndBarcode && !suppressed){
			Motor.A.rotate(20);
			if((wasWhite && ls.getLightValue() < 1100)){
				wasWhite = false;
				wasBlack = true;
				Time = System.currentTimeMillis();

			}
			else if(wasBlack && ls.getLightValue() > 1100){
				wasWhite = true;
				wasBlack = false;
				Time = System.currentTimeMillis();
			}
			else if((System.currentTimeMillis() - Time) > 5000){
				EndBarcode = true;
			}
				
			Thread.yield();
		}
		inLabyrinth = true;
		suppress();
		Main.level++;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	

}
