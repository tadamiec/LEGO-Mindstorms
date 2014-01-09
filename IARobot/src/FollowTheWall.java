import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class FollowTheWall implements Behavior {
	private boolean suppressed = false;

	public boolean takeControl() {
	      return true;
	   }

	   public void suppress() {
	      suppressed = true;
	   }
	  
	   public void action() {
		  suppressed = false;
		  
			if (us.getDistance() > d2) {
				collision(TSright,TSleft,us);

				
				Motor.B.rotateTo(Math.min(2*(us.getDistance()-d2),30));
				

			}
			else if (us.getDistance() < d1){
				collision(TSright,TSleft,us);

				Motor.B.rotateTo(Math.max(2*(us.getDistance()-d1),-30) );
			}
	
			Motor.B.rotateTo(0);
			Motor.A.forward();
		  
		  while( !suppressed )
			  Thread.yield();

	  }

}
