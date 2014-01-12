import javax.bluetooth.RemoteDevice;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.robotics.subsumption.Behavior;


public class InitBluetooth implements Behavior{
	private boolean suppressed = false;
	private boolean BluetoothInitialize = false;
	@Override
	public boolean takeControl() {
		return !BluetoothInitialize;
	}

	@Override
	public void action() {
		suppressed = false;
		LCD.clear();
		LCD.drawString("Bluetooth",0, 0);
		LCD.drawString("initialization...", 0, 1);
		
		Bluetooth.btEnable();
		
		Bluetooth.setVisibility((byte) 0);
		
		LCD.drawString("Successfull", 0, 2);
		
	
		
		BluetoothInitialize = true;
		suppress();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
