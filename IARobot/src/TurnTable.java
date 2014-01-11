import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


public class TurnTable implements Behavior {
	private TurnControl turntableControl;
	private boolean suppressed = false;
	private TouchSensor TSleft;
	private TouchSensor TSright;
	
	public TurnTable(SensorPort SP1,SensorPort SP2){
		turntableControl = new TurnControl();
		TSright = new TouchSensor(SP1);
		TSleft = new TouchSensor(SP2);
	}
	
	@Override
	public boolean takeControl() {
		return turntableControl.connectionToTurntableSuccessful();
	}

	@Override
	public void action() {
	
		turntableControl.turnClockwise(180);
		Motor.B.rotateTo(0);
		Motor.A.forward();
		if(TSleft.isPressed() || TSright.isPressed()){
			Motor.A.rotate(-90);
			turntableControl.turnClockwise(180);
			Motor.A.rotate(-540);
		}		
		
		Motor.A.stop();
		
		while(Motor.A.isMoving() || !suppressed){
			Thread.yield();
		}
		turntableControl.disconnectFromTurntable();
		suppress();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
	
	
}
