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


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor


		int Dark = 21;
		int Light = 70;
//
//		Behavior CF = new CollisionFront( SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
//		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
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
		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
//      Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		Behavior StL = new StartLabyrinth(SPort3, Dark, Light); //(LightS,..)
//		Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
//		Behavior Test = new DiffPilotMotorTest();
		Behavior OD = new OpenADoor(); // use bluetooth to open the door

		Behavior[] BArray = {/*FB*/ SR};
		


		// UltrasonicSensor us = new UltrasonicSensor(SPort4);
		//
		// while(!Button.ESCAPE.isDown())
		// System.out.println(us.getDistance());


		Arbitrator Labyrinthe = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Labyrinthe.start();

	}

}
