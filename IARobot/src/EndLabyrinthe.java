import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;


public class EndLabyrinthe implements Behavior {
	private boolean suppressed = false;

	private LightSensor ls;
	private boolean LabyrintheSolved = false;
	
	public EndLabyrinthe(SensorPort SP,int Dark, int Light){
		ls = new LightSensor(SP);
		ls.setHigh(Light);
		ls.setLow(Dark);
	}
	
	@Override
	public boolean takeControl() {
		return ls.getLightValue() > 1100;
	}

	@Override
	public void action() {
		suppressed = false;
		
		Motor.A.stop();
		
		
		
		while(!LabyrintheSolved && !suppressed)
			Thread.yield();
		
		suppress();
		Main.level++;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	



}
