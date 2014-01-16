import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class ShootingRange implements Behavior , ShootingRangeListener {
	private boolean suppressed = false;
	private ShootingRangeControl Shooter = new ShootingRangeControl(this);
	private boolean successed = false;
	private int angle = 45;
	private UltrasonicSensor us;
	
	public ShootingRange(SensorPort SP){
		us = new UltrasonicSensor(SP);
	}
	
	@Override
	public boolean takeControl() {
		return !successed;
	}

	@Override
	public void action() {
		suppressed = false;
		
		Shooter.connect();
		
		Main.pilot.rotate(90);
		System.out.print(us.getDistance());
		
		while(!suppressed && !successed){
			Shooter.shoot(angle);
			Thread.yield();
		}
		suppress();
		Shooter.disconnect();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public void shootSuccess() {
		System.out.println("Shoot Successfull !");
		successed = true;
	}

	@Override
	public void shootFail() {
		System.out.println("Shoot Failed :(");
		if(angle >= 180)
			angle-=10;
		else if(angle <= 90)
			angle+=10;
	}

	@Override
	public void shootInvalidAngle() {
		System.out.println("InvalidAngle");
		angle = 90;
	}

	@Override
	public void error(String message) {
		System.err.println(message);
	}
	

}
