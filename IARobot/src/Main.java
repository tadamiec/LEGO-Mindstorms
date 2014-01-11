import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class Main {
	public static int level = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor

		int d1 = 15;
		int d2 = 18;
		int d3 = 22;
		int Dark = 21;
		int Light = 70;
//
//		Behavior CF = new CollisionFront(SPort4, SPort1, SPort2); // (UltraS,TSRight,TSLeft)
//		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior FTW = new FollowTheWall(SPort4,SPort3, d1, d2, d3,Dark,Light); // (UltraS,LightS,..)
//		Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); // (LightS,..)
//		Behavior FL = new FollowTheLine(SPort3, Dark, Light);
		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
		// Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
//		Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)
//		Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
		
<<<<<<< HEAD
//		Behavior [] BArray = { FTW, StL, CR, CL, CF };
		
//		Behavior [] BArray = {TT};

		Behavior[] BArray = { FB};
=======
		
//		Behavior [] BArray = {TT};

		Behavior[] BArray = { /*FTW, SL, CR, CL, CF,*/ FL/*, FB*/};
>>>>>>> cf835723f2a58dbce07d74070462ea33e6174d3b

		Arbitrator Labyrinthe = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Labyrinthe.start();

	}

}
