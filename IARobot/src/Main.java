import java.util.ArrayList;
import java.util.List;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class Main {
	public static int level = 42;

	public static boolean door = false;
	public static boolean doorBumped = false;
<<<<<<< HEAD
	public static DifferentialPilot pilot = new DifferentialPilot(18, 95 , Motor.B, Motor.C);

	
	public static List<SymbolTravelData> symbolTravelDataList = new ArrayList<SymbolTravelData>();


=======
	
//	public static List<SymbolTravelData> symbolTravelDataList = new ArrayList<SymbolTravelData>();

	public static DifferentialPilot pilot = new DifferentialPilot(18, 95 , Motor.B, Motor.C);


>>>>>>> f241dfb5945ccc82b55c39deb76751ea85acdd4a

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SensorPort SPort1 = SensorPort.S1; // TouchSensor Right
		SensorPort SPort2 = SensorPort.S2; // TouchSensor Left
		SensorPort SPort3 = SensorPort.S3; // LightSensor
		SensorPort SPort4 = SensorPort.S4; // UltraSonicSensor

<<<<<<< HEAD
	
		int Dark = 21;
		int Light = 70;

		Behavior CF = new CollisionFront( SPort1, SPort2); // (TSRight,TSLeft)
		Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
		Behavior FTW = new FollowTheWall(SPort4,SPort3,Dark,Light); // (UltraS,LightS,..)
//		Behavior FL = new FollowTheLine2(SPort3, Dark, Light);
//		Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
//		Behavior SR = new SymbolsReader(SPort3, Dark, Light);
//		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
//		Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
//		Behavior LBC = new LevelBarCode(SPort3, Dark, Light); //(LightS,..)
//		
		Behavior [] BArray = {FTW,CL,CR,CF};


		// UltrasonicSensor us = new UltrasonicSensor(SPort4);
		//
		// while(!Button.ESCAPE.isDown())
		// System.out.println(us.getDistance());


=======
		int Dark = 20;
		int Light = 60;

		Behavior CF = new CollisionFront(SPort4, SPort1, SPort2); // (UltraS,TSRight,TSLeft)
		// Behavior CL = new CollisionLeft(SPort1, SPort2); // (TSRight,TSLeft)
		// Behavior CR = new CollisionRight(SPort1, SPort2); // (TSRight,TSLeft)
		// Behavior FTW = new FollowTheWall(SPort4, SPort3, d1, d2, d3, Dark,Light); // (UltraS,LightS,..)
		// Behavior FL = new FollowTheLine2(SPort3, Dark, Light);
		// Behavior LSC = new LightSensorCalibration(SPort3); //(LightS)
		// Behavior OD = new OpenADoor();
		Behavior FB = new FollowTheBridge(SPort3, Dark, Light);
		// Behavior TT = new TurnTable(SPort1,SPort2); //(TSRight,TSLeft)
		Behavior CD = new ColorDetect(SPort3,SPort4, Dark, Light,SPort1, SPort2); // use bluetooth to open the door
		
		Behavior [] BArray = {FB,CD,CF};
>>>>>>> f241dfb5945ccc82b55c39deb76751ea85acdd4a

		Arbitrator Rennen = new Arbitrator(BArray);

		LCD.drawString("Press ENTER", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();

		Rennen.start();

	}

}
