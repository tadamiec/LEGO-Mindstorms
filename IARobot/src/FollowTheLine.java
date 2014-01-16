import java.io.File;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

//import lejos.nxt.Sound;

public class FollowTheLine implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;
	private boolean fromLeft = true;
	private boolean alreadyForwards = false;
	private boolean minus = false;
	private boolean EndLine = false;
	
	DifferentialPilot pilot = new DifferentialPilot(30, 40, Motor.A, Motor.B,
			true);

	static File pw = new File("power_up_8bit.wav");

	public FollowTheLine(SensorPort LS, int Dark, int Light) {
		ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
//		return (ls.getLightValue() > 1100 && ls.getLightValue() < 1500);
		return !EndLine;
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("Mode : FollowTheBridge", 0, 0);
		suppressed = false;
		int angle = 0;
		int limitAngle = angle;
		long currentTime;
		boolean bothSide = false;
		
		while (!EndLine && !suppressed) {
			Main.pilot.forward();
			alreadyForwards = false;
			fromLeft = true;
			currentTime = System.currentTimeMillis();
			while (ls.getLightValue() > 1000 && ls.getLightValue() < 1200) {
				if (fromLeft) {
					Main.pilot.rotateRight();
//					findStrightLine(limitAngle);
					if (System.currentTimeMillis() - currentTime > 2000){
						fromLeft = false;
						Main.pilot.rotate(Main.pilot.getRotateSpeed()*(System.currentTimeMillis() - currentTime));

					}
//					minus = false;
				} else {
					Main.pilot.rotateLeft();
					
//					findStrightLine(-limitAngle+10);
					if (System.currentTimeMillis() - currentTime > 2000){
						bothSide = true;
						Main.pilot.rotate(-Main.pilot.getRotateSpeed()*(System.currentTimeMillis() - currentTime));
					}
//					minus = true;
				}
				limitAngle += angle;
				
				if(bothSide)
					EndLine = true;

				// Move a bit forward from the line
				if (!alreadyForwards) {
					Main.pilot.travel(20);
					// Sound.playSample(pw, 25);
//					Delay.msDelay(200);
					alreadyForwards = true;
				}
			}
//
//			if (!minus && (limitAngle > (3 * angle))) {
//				// Main.angleList.add(limitAngle);
//				System.out.println(limitAngle);
//			} else if (minus && (limitAngle > (3 * angle))) {
//				// Main.angleList.add(-limitAngle);
//				System.out.println(-limitAngle);
//			}
			limitAngle = angle;
			Thread.yield();
		}
		suppress();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	private void findStrightLine(int limitAngle) {
		Main.pilot.rotate(limitAngle);

		if (!(ls.getLightValue() > 1400)) {
			Main.pilot.rotate(-limitAngle);
		}
	}

}
