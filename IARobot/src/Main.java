import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class Main {
	public static int level = 0;

	public static boolean door = false;
	public static boolean doorBumped = false;
<<<<<<< HEAD
	public static DifferentialPilot pilot = new DifferentialPilot(38, 125 , Motor.B, Motor.C);
=======
	public static DifferentialPilot pilot = new DifferentialPilot(32, 120,
			Motor.B, Motor.C);

>>>>>>> d96543fedccc3fa21c735e66f5f0be64fa2690f7
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor

<<<<<<< HEAD
		int d1 = 7;
		int d2 = 15;
		int d3 = 50;
		int Dark = 21;
		int Light = 70;
//
		Behavior CF = new CollisionFront( SPort1, SPort2); // (TSRight,TSLeft)
		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior FTW = new FollowTheWall(SPort4,SPort3, d1, d2, d3,Dark,Light); // (UltraS,LightS,..)
//		Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); // (LightS,..)
//		Behavior FL = new FollowTheLine(SPort3, Dark, Light);
//		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
//      Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)
//		Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
//		Behavior Test = new DiffPilotMotorTest();
		
		Behavior [] BArray = {FTW,CL,CR,CF};

//		Behavior[] BArray = { FB};
		


=======
		int d1 = 6;
		int d2 = 22;
		int d3 = 22;
		int Dark = 20;
		int Light = 60;

		Behavior CF = new CollisionFront(SPort4, SPort1, SPort2); // (UltraS,TSRight,TSLeft)
		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior FTW = new FollowTheWall(SPort4, SPort3, d1, d2, d3, Dark,
				Light); // (UltraS,LightS,..)
		// Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); //
		// (LightS,..)
		Behavior FL = new FollowTheLine(SPort3, Dark, Light);
		// Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
		 Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		// Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)
		Behavior TT = new TurnTable(SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior Test = new DiffPilotMotorTest();
		Behavior OD = new OpenADoor();
		Behavior[] BArray = {/*LSC*/ OD ,CF };

		// Behavior[] BArray = { FB};

		// UltrasonicSensor us = new UltrasonicSensor(SPort4);
		//
		// while(!Button.ESCAPE.isDown())
		// System.out.println(us.getDistance());
>>>>>>> d96543fedccc3fa21c735e66f5f0be64fa2690f7

		Arbitrator Labyrinthe = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Labyrinthe.start();

	}

}
