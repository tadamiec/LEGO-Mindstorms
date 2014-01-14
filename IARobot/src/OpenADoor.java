import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.util.Delay;

import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class OpenADoor implements Behavior {
	public boolean suppressed = false;

	public void OpenADoor() {

	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		Main.door = true;

		if (Main.doorBumped) {
//			System.out.println("Press Enter to connect to Gate!");
			// Button.ENTER.waitForPress();

			System.out.println("Connecting...");
			RemoteDevice btrd = Bluetooth.getKnownDevice("Gate1");
			BTConnection conn = Bluetooth.connect(btrd);

			if (conn == null) {
				System.out.println("Connection failed!");
			} else {
				System.out.println("Connected!");
				System.out.println("Sending 0");
				DataOutputStream os = conn.openDataOutputStream();
				Delay.msDelay(1000);
				try {
					os.writeInt(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Sent!");

				Delay.msDelay(1000);
				try {
					os.writeInt(99);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn.close();
				System.out.println("Connection closed.");
				Motor.A.forward();
			}
			// NXTConnection Connex = Bluetooth.waitForConnection();
			// DataInputStream dis = Connex.openDataInputStream();
			// DataOutputStream dos = Connex.openDataOutputStream();
		} else {
			Motor.B.setSpeed(90);
			Motor.B.forward();
			Motor.C.setSpeed(90);
			Motor.C.forward();
			Thread.yield();
		}
		while (!suppressed) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
