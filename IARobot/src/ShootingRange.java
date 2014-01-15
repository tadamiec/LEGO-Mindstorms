import lejos.robotics.subsumption.Behavior;


public class ShootingRange implements Behavior , ShootingRangeListener {
	private boolean suppressed = false;
	private ShootingRangeControl Shooter = new ShootingRangeControl(this);
	private boolean successed = false;
	private int angle = 90;
		
	@Override
	public boolean takeControl() {
		return !successed;
	}

	@Override
	public void action() {
		suppressed = false;
		
		Shooter.connect();
		
		
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
		successed = true;
	}

	@Override
	public void shootFail() {
		if(angle >= 180)
			angle-=10;
		else if(angle <= 90)
			angle+=10;
	}

	@Override
	public void shootInvalidAngle() {
		
	}

	@Override
	public void error(String message) {
		// TODO Auto-generated method stub
		
	}
	

}
