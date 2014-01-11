import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLine implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	static List<Integer> lastDirectionLine = new ArrayList<Integer>();
	static List<Integer> lastDirectionTarpoulin = new ArrayList<Integer>();

	public FollowTheLine(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		lastDirectionLine.add(0);
		lastDirectionTarpoulin.add(1);
		Motor.A.setSpeed(200);

		while (!suppressed) {
			// Line
			if (ls.getLightValue() > 1100) {
				LastTarpoulin = lastDirectionTarpoulin.get(lastDirectionTarpoulin.size() - 1);
				if (LastTarpoulin > 0) {
					turnLine(-20);
				} else {
					turnLine(10);
				}
				// Tarpoulin
			} else if (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				LastLine = lastDirectionLine.get(lastDirectionLine.size() - 1);
				if (LastLine > 0) {
					turnTarpaulin(-10);
				} else {
					turnTarpaulin(10);
				}
			}
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	static int LastLine, LastTarpoulin;

	public static void turnLine(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		lastDirectionLine.add(Motor.B.getTachoCount());
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		lastDirectionTarpoulin.add(Motor.B.getTachoCount());
	}
	
	public static void lineNotfound(){
		
	}
}