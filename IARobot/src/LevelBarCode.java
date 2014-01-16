import java.io.File;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;



public class LevelBarCode implements Behavior{
	private boolean suppressed = false;
	private LightSensor ls;
	private boolean EndBarcode = false;

	public LevelBarCode(SensorPort SP,int Dark,int Light){
		ls = new LightSensor(SP);
		ls.setHigh(Light);
		ls.setLow(Dark);
	}
	
	public boolean takeControl() {
		return Main.level == 0;
	}

	public void action() {
		LCD.clear();
		LCD.drawString("BarCode", 0, 0);
		suppressed = false;
		boolean wasWhite = false;
		boolean wasBlack = true;
		EndBarcode = false;
		Main.level = 0;
		long Time = System.currentTimeMillis();
		while(!EndBarcode && !suppressed){
			Main.pilot.forward();
			if((wasWhite && ls.getLightValue() < 1700)){
				wasWhite = false;
				wasBlack = true;
				Time = System.currentTimeMillis();

			}
			else if(wasBlack && ls.getLightValue() > 1700){
				wasWhite = true;
				wasBlack = false;
				Time = System.currentTimeMillis();
				Main.level++;
			}
			else if((System.currentTimeMillis() - Time) > 2000){
				if(wasWhite)
					Main.level--;
				EndBarcode = true;
			}
				
			Thread.yield();
		}
		if(Main.level == 4)
			Main.pilot.travel(100);
		Main.pilot.stop();
		System.out.println(Main.level);
		File pw = new File("power_up_8bit.wav");
		Sound.playSample(pw, 50);

		suppress();
	

	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
