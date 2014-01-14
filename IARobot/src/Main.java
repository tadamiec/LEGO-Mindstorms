import java.util.ArrayList;
import java.util.List;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class Main {
	public static int level = 0;

	public static boolean door = false;
	public static boolean doorBumped = false;
	
	public static List<SymbolTravelData> symbolTravelDataList = new ArrayList<SymbolTravelData>();

	public static DifferentialPilot pilot = new DifferentialPilot(18, 95 , Motor.B, Motor.C);
<<<<<<< HEAD

=======
=======

	public static DifferentialPilot pilot = new DifferentialPilot(18, 95 , Motor.B, Motor.C);

>>>>>>> 76b483a70ba482011d1e8e6de7a02861024d3751
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor

<<<<<<< HEAD

=======
		int d1 = 7;
		int d2 = 15;
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
		int Dark = 21;
		int Light = 70;
//
//		Behavior CF = new CollisionFront( SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
<<<<<<< HEAD
//		Behavior FTW = new FollowTheWall(SPort4, SPort3, Dark,Light); // (UltraS,LightS,..)
		// Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); //
		// (LightS,..)
		Behavior FL = new FollowTheLine2(SPort3, Dark, Light);
		// Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
		 Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		// Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)
		Behavior TT = new TurnTable(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior Test = new DiffPilotMotorTest();
		Behavior SR = new SymbolsReader(SPort3, Dark, Light);
=======
//		Behavior FTW = new FollowTheWall(SPort4,SPort3, d1, d2, 30,Dark,Light); // (UltraS,LightS,..)
//		Behavior SL = new SearchLabyrinthe(SPort3, Dark, Light); // (LightS,..)
//		Behavior FL = new FollowTheLine(SPort3, Dark, Light);
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git
		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
//      Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
//		Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)
//		Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
//		Behavior Test = new DiffPilotMotorTest();
<<<<<<< HEAD
		Behavior OD = new OpenADoor(); // use bluetooth to open the door
=======
<<<<<<< HEAD
		
//		Behavior [] BArray = {FTW,CL,CR,CF};
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git

		Behavior[] BArray = {/*FB*/ SR};
		

<<<<<<< HEAD
=======
=======
		Behavior OD = new OpenADoor(); // use bluetooth to open the door
>>>>>>> 76b483a70ba482011d1e8e6de7a02861024d3751

//		Behavior [] BArray = {FTW,CL,CR,CF};

<<<<<<< HEAD
=======
		Behavior [] BArray = {OD,CF};
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git

		// UltrasonicSensor us = new UltrasonicSensor(SPort4);
		//
		// while(!Button.ESCAPE.isDown())
		// System.out.println(us.getDistance());

<<<<<<< HEAD
=======

>>>>>>> 76b483a70ba482011d1e8e6de7a02861024d3751
>>>>>>> branch 'master' of https://github.com/tadamiec/LEGO-Mindstorms.git

		Arbitrator Labyrinthe = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Labyrinthe.start();

	}

}
