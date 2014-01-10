import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class SearchLabyrinthe implements Behavior{
	private LightSensor ls;
	private boolean suppressed = false;
	private boolean labyrinthFound = false;

	public SearchLabyrinthe(SensorPort LS,int Dark,int Light){
		ls = new LightSensor(LS);
		ls.setHigh(Light);
		ls.setLow(Dark);
	}

	public boolean takeControl() {
		return !labyrinthFound;
	}

	public void action() {
		suppressed = false;
		
		//Real Action
		Motor.C.rotateTo(0);
		Motor.A.forward();
		while( (ls.getLightValue()<900 || ls.getLightValue()>600) && !suppressed )
			Thread.yield();

		//Clean up
		suppress();
	
	}

	public void suppress() {
		suppressed = true;
		labyrinthFound = true;
		LCD.drawString("Labyrinth gefunden!", 0, 0);
		Motor.B.rotateTo(0);
		Motor.A.stop();
	}

}
