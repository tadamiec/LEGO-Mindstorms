import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

	public static String level = "Start";

	public static boolean door = false;
	public static boolean doorBumped = false;

	public static List<SymbolTravelData> symbolTravelDataList = new ArrayList<SymbolTravelData>();

	public static DifferentialPilot pilot = new DifferentialPilot(18, 95,
			Motor.B, Motor.C);

	@SuppressWarnings("deprecation")
	public static Map<Integer, String> levelList = new HashMap<Integer, String>();

	// new HashMap<String, Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor

		int Dark = 27;
		int Light = 58;

		levelList.put(0, "Start");
		levelList.put(1, "Bridge");
		levelList.put(2, "Labyrinth");
		levelList.put(3, "Symbol");
		levelList.put(4, "Shoot");
		levelList.put(5, "Boss");

		// UltrasonicSensor us = new UltrasonicSensor(SPort4);
		//
		// while(!Button.ESCAPE.isDown())
		// System.out.println(us.getDistance());

		// Behavior CF = new CollisionFront(SPort4, SPort1, SPort2); //
		// (UltraS,TSRight,TSLeft)
		// Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
		// Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
		// Behavior FTW = new FollowTheWall(SPort4, SPort3, d1, d2, d3,
		// Dark,Light); // (UltraS,LightS,..)
		// Behavior FL = new FollowTheLine2(SPort3, Dark, Light);
		// Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		// Behavior OD = new OpenADoor();
		// Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
		// Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
		// Behavior CD = new ColorDetect(SPort3, SPort4, Dark, Light, SPort1,
		// SPort2); // use bluetooth to open the door
		Behavior SR = new SymbolsReader2(SPort3, Dark, Light);

		Behavior[] BArray = { SR };

		Arbitrator Rennen = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Rennen.start();

	}
}
