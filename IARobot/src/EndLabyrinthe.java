import lejos.robotics.subsumption.Behavior;


public class EndLabyrinthe implements Behavior {
	public boolean suppressed = true;
	
	
	@Override
	public boolean takeControl() {
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		
		while(!suppressed)
			Thread.yield();
		
		suppress();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	



}
