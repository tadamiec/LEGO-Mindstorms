import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import java.io.*;

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
		LCD.clear();
		suppressed = false;
		LCD.drawString("Ich suche das Labyrinth",0,0);
		//Real Action
		Motor.C.rotateTo(0);
		Motor.A.forward();
		
		
		
		while(ls.getLightValue()<1100 && !suppressed )
			Thread.yield();

		//Clean up
		suppress();
		LCD.clear();
//		File pw = new File("power_up_8bit.wav");
//		Sound.playSample(pw,0);
		labyrinthFound = true;
		LCD.drawString("Labyrinth gefunden!", 0, 0);
		Motor.B.rotateTo(0);
		Motor.A.stop();
		
	
	}

	public void suppress() {
		suppressed = true;
		
	}

}
