import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class FollowTheWall implements Behavior {
	private boolean suppressed = false;
	private UltrasonicSensor us;
	private LightSensor ls;

	public FollowTheWall(SensorPort US, SensorPort LS, int Dark, int Light) {
		us = new UltrasonicSensor(US);
		ls = new LightSensor(LS);
		ls.setHigh(Light);
		ls.setLow(Dark);

	}

	public boolean takeControl() {
		return Main.level == 6 || Main.level == 3;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;

		LCD.clear();
		LCD.drawString("Labyrinth", 0, 0);

		while (!suppressed) {
			Main.pilot.forward();

			if (us.getDistance() > 100) {
				Main.pilot.travel(100);
				Main.pilot.rotate(-90);
				while (us.getDistance() > 100) {
					Main.pilot.forward();
				}
				Main.pilot.travel(50);
			}
			if(ls.getLightValue() > 1700){
				Main.pilot.travel(100);
//				if(Main.level == 3)
//					Main.pilot.rotate(45);
				Main.level = 0;
			}
			Thread.yield();
		}

		suppress();
		LCD.clear();
	}

}
