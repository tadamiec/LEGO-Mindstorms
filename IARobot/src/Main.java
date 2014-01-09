import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SensorPort SPort1 = SensorPort.S1;
		SensorPort SPort2 = SensorPort.S2;
		SensorPort SPort3 = SensorPort.S3;
		SensorPort SPort4 = SensorPort.S4;
		int d1 = 13;
		int d2 = 18; 
		
		
		Behavior CF = new CollisionFront();
		Behavior CL = new CollisionLeft(SPort1, SPort2);
		Behavior CR = new CollisionRight(SPort1, SPort2);
		Behavior FTW = new FollowTheWall(SPort4, d1, d2);
		
		Behavior [] BArray = {CF,CL,CR,FTW};
		
		Arbitrator Labyrinthe = new Arbitrator(BArray);
		
		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		
		Labyrinthe.start();
		
	}

}
