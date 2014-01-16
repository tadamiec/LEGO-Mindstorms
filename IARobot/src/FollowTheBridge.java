import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import java.io.*;

public class FollowTheBridge implements Behavior {
	private boolean suppressed = false;
	private boolean gapFound = false;
	private LightSensor ls;
	private int step = 10;
//	private boolean adjustmentNeeded = true;

	public FollowTheBridge(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (/*ls.getLightValue() > 950 && ls.getLightValue() < 1060*/true);
	}

	@Override
	public void action() {
		suppressed = false;
		File pw = new File("power_up_8bit.wav");

		Main.pilot.travel(100);

		while (ls.getLightValue() < 1200 && !suppressed) {

//			while (ls.getLightValue() > 600 && !gapFound)
//				Main.pilot.forward();
//
//			if (!gapFound) {
//				Sound.playSample(pw, 25);
//				Main.pilot.rotate(-45);
//				gapFound = true;
//			}

			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//START
			
			int i = 0;
			while (ls.getLightValue() > 600 && i < 5) {
				Main.pilot.rotate(step);
				i++;
//				adjustmentNeeded = true;
			}
			if (ls.getLightValue() > 600) {
//				adjustmentNeeded = false;
				while (ls.getLightValue() >600){
					Main.pilot.forward();
				}
				Main.pilot.travel(-10);
				Main.pilot.rotate(-(i+1)*step);
			}
			gapFound = true;
			Motor.A.forward();
			if (ls.getLightValue() < 600) 
				Motor.B.rotateTo(15);
			else
				Motor.B.rotateTo(-10);
//			if (adjustmentNeeded) {
//				Main.pilot.rotate(-4 * step);
//				adjustmentNeeded = false;
//			}
			Main.pilot.travel(200);

			Thread.yield();
			
			//LOOP
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		}

		suppress();

		LCD.drawString("OUT", 0, 0);
		LCD.drawInt(ls.getLightValue(), 1, 1);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}