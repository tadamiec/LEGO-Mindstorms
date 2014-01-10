import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SensorPort SPort1 = SensorPort.S1; //TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; //TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; //LightSensor 
		SensorPort SPort4 = SensorPort.S4; //UltraSonicSensor
		int d1 = 13;
		int d2 = 18; 
		int Dark = 21;
		int Light = 70;
		
		Behavior CF = new CollisionFront(SPort4,SPort1,SPort2); //(UltraS,TSRight,TSLeft)
		Behavior CL = new CollisionLeft(SPort1, SPort2); //(TSRight,TSLeft)
		Behavior CR = new CollisionRight(SPort1, SPort2); //(TSRight,TSLeft)
		Behavior FTW = new FollowTheWall(SPort4, d1, d2); //(UltraS,..)
		Behavior SL = new SearchLabyrinthe(SPort3,Dark,Light); //(LightS)
		//Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		
		Behavior [] BArray = {FTW,SL,CR,CL,CF};
		
		Arbitrator Labyrinthe = new Arbitrator(BArray);
		
		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		
		Labyrinthe.start();
		
	}

}
