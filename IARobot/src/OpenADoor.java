import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.subsumption.Behavior;


public class OpenADoor implements Behavior{
	public boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		
		NXTConnection Connex = Bluetooth.waitForConnection();
		DataInputStream dis = Connex.openDataInputStream();
	    DataOutputStream dos = Connex.openDataOutputStream();
	    
		while(!suppressed){
			Thread.yield();
		}
			
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
	

}
