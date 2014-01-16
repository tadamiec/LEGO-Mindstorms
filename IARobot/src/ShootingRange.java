import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class ShootingRange implements Behavior, ShootingRangeListener {
	private boolean suppressed = false;
	private ShootingRangeControl Shooter = new ShootingRangeControl(this);
	private UltrasonicSensor us;

	public ShootingRange(SensorPort SP) {
		us = new UltrasonicSensor(SP);
	}

	@Override
	public boolean takeControl() {
		return Main.level == 8;
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("Shooter", 0, 0);
		suppressed = false;
		
		if(Main.startFtl){
			LCD.drawString("I want FTL", 0, 1);
			Main.ftl = true;
			Main.startFtl = false;
			suppress();
		}

		if (!suppressed) {
			LCD.drawString("connecting...", 0, 2);

			Shooter.connect();
			LCD.drawString("connected.", 0, 3);

			Main.pilot.rotate(100);
			Main.pilot.travel(-10);
			System.out.print(us.getDistance());
		}

		while (!suppressed) {
			if (us.getDistance() < 80)
				Shooter.shoot(us.getDistance() + 15);

			Thread.yield();
		}
		
		
		suppress();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public void shootSuccess() {
		System.out.println("Shoot Successfull !");
		Shooter.disconnect();
		Main.pilot.travel(150);
		Main.startFtl = true;
		LCD.clear();
		Main.level = 0;
		Main.ftl = true;
	}

	@Override
	public void shootFail() {
		System.out.println("Shoot Failed :(");

	}

	@Override
	public void shootInvalidAngle() {
		System.out.println("InvalidAngle");
	}

	@Override
	public void error(String message) {
		System.err.println(message);
	}

}
