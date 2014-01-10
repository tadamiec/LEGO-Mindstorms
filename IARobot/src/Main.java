import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor
<<<<<<< HEAD
		int d1 = 15;
		int d2 = 18;
		int d3 = 22;
=======
		int d1 = 13;
		int d2 = 18;
>>>>>>> 62fa6012f7b54923bbb0de0610d0df2d92522656
		int Dark = 21;
		int Light = 70;

		Behavior CF = new CollisionFront(SPort4, SPort1, SPort2); // (UltraS,TSRight,TSLeft)
		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
<<<<<<< HEAD
		Behavior FTW = new FollowTheWall(SPort4,SPort3, d1, d2, d3,Dark,Light); // (UltraS,LightS,..)
		Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); // (LightS,..)
		// Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)

		Behavior [] BArray = { FTW, SL,StL, CR, CL, CF };
=======
		Behavior FTW = new FollowTheWall(SPort4, d1, d2); // (UltraS,..)
		Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); // (LightS)
		Behavior FL = new FollowTheLine(SPort3, Dark, Light);
		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
		// Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)

		Behavior[] BArray = { /*FTW, SL, CR, CL, CF,*/ FB, FL };
>>>>>>> 62fa6012f7b54923bbb0de0610d0df2d92522656

		Arbitrator Labyrinthe = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Labyrinthe.start();

	}

}
