import lejos.nxt.*;
import lejos.robotics.subsumption.*;

public class ColorDetect implements Behavior {
<<<<<<< HEAD
        private boolean suppressed = false;
        private TouchSensor rts;
        private TouchSensor lts;

        int collision_happened = 0;

        private LightSensor ls;
        private UltrasonicSensor us;
        
        public ColorDetect( SensorPort SP3, SensorPort SP4,int Dark, int Light,SensorPort SP1, SensorPort SP2) {
                rts = new TouchSensor(SP1);
                lts = new TouchSensor(SP2);
                ls = new LightSensor(SP3);
                us = new UltrasonicSensor(SP4);
                ls.setLow(Dark);
                ls.setHigh(Light);
        }

        public boolean takeControl() {
                
                return (ls.getLightValue()>950);
        }

        public void suppress() {
                suppressed = true;

        }

        public void action() {
                LCD.clear();
                LCD.drawString("Mode : ColorDetect", 0, 0);
                suppressed = false;
                int tmp = 0;

                Main.pilot.travel(100); // Problem?
                while (!suppressed){

                        //CENTERING
                        while(ls.getLightValue()<1000){ // Problem?
                                Main.pilot.rotate(30);
                                Main.pilot.rotate(-30);
                        }
                        Main.pilot.travel(100);
                        //END CENTERING

                        //ANGLE CORRECTION
                        while (us.getDistance()>100 & tmp == 0)
                        {
                        Main.pilot.rotate(15);
                        }
                        
                        if (tmp == 0){
                                Main.pilot.rotate(-55);
                        }
                        tmp = 1;
                        //END ANGLE CORRECTION

                        //FORWARD TO ELEVATOR
                        while(ls.getLightValue()>600){
                                Main.pilot.forward();
                        }

                        //WAIT FOR ELEVATOR
                        LCD.clear();
                        LCD.drawString("Ich warte für den aufzug", 0, 0);
                        while (ls.getLightValue() < 800){
                                Main.pilot.stop();
                        }

                        //GO INSIDE ELEVATOR
                        LCD.clear();
                        LCD.drawString("Go go go!", 0, 0);
                        Main.pilot.forward();

                        Thread.yield();
                        }
                        
                }
                
                
                



//                while (Main.pilot.isMoving() && !suppressed)
//                        Thread.yield();
//
//                // Clean up
//                suppress();
////                Motor.B.rotateTo(0);
////                Motor.A.stop(
        }
=======
	private boolean suppressed = false;
	private TouchSensor rts;
	private TouchSensor lts;

	int collision_happened = 0;
//	int lf1 = 0;
//	int lf2 = 0;
	int lf = 0;
	int tmp = 0;

	private LightSensor ls;
	private UltrasonicSensor us;
	
	public ColorDetect( SensorPort SP3, SensorPort SP4,int Dark, int Light,SensorPort SP1, SensorPort SP2) {
		rts = new TouchSensor(SP1);
		lts = new TouchSensor(SP2);
		ls = new LightSensor(SP3);
		us = new UltrasonicSensor(SP4);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	public boolean takeControl() {
		
		return (Main.colorChanged || ls.getLightValue() >= 1600); //kritik
	}

	public void suppress() {
		suppressed = true;

	}

	public void action() {
		LCD.clear();
		LCD.drawString("Mode : ColorDetect", 0, 0);
		suppressed = false;
		int tmp = 0;

//		Main.pilot.travel(50); // Problem?
		while (!suppressed){

			//ANGLE CORRECTION
			while (us.getDistance()>60 & tmp == 0)
			{
			Main.pilot.setRotateSpeed(100);
			Main.pilot.rotate(5);
			}
			if (tmp == 0){
				if (us.getDistance()>40)
				{
					Main.pilot.rotate(-47);
				}
				if (us.getDistance()<=40 & us.getDistance()>35)
				{
					Main.pilot.rotate(-51);
				}
				if (us.getDistance()<=35 & us.getDistance()>25)
				{
					Main.pilot.rotate(-55);
				}
				if (us.getDistance()<25)
				{
					Main.pilot.rotate(-40);
				}
				
			//	Main.pilot.rotate(-55);
			}
			tmp = 1;
			//END ANGLE CORRECTION
			
//			while (ls.getLightValue() >= 1600)
//			{
//				Main.pilot.stop();
//			}
//			Delay.msDelay(500);   important!!!!!!!
			//FORWARD TO ELEVATOR
			
			while(ls.getLightValue()>1100){
				Main.pilot.forward();
			}

			//WAIT FOR ELEVATOR
			LCD.clear();
			LCD.drawString("Ich warte fur den aufzug", 0, 0);
			while (ls.getLightValue() < 1450){
				Main.pilot.stop();
			}

			//GO INSIDE ELEVATOR
			LCD.clear();
			LCD.drawString("Go go go!", 0, 0);
			Main.pilot.setTravelSpeed(80);
			//Main.pilot.travel(120);
			// Test!
			while (us.getDistance()>60){
				Main.pilot.travel(30);
				LCD.clear();
				LCD.drawString("Aufzug2!", 0, 0);
		
				}
			
			Main.pilot.travel(30);
			while (us.getDistance()<=60 )
			{
				LCD.clear();
				LCD.drawString("Aufzug1!", 0, 0);
			while (lf < 100)
			{
			if (us.getDistance()<18)
			{
				
				Motor.C.setSpeed(100);
				Motor.C.rotate(50);
				Motor.B.stop();
				Motor.B.setSpeed(100);
				Motor.B.rotate(50);
				Motor.C.stop();
				lf += 60;
//				Main.pilot.travel(8);
//				lf1 += 10;
//				lf2 += 10;
			
				
			
				
				
			}
			if (us.getDistance()>18)
			{
				
				Motor.B.setSpeed(100);
				Motor.B.rotate(50);
				Motor.C.stop();
				Motor.C.setSpeed(100);
				Motor.C.rotate(50);
				Motor.B.stop();
				lf += 60;
//				Main.pilot.travel(8);
//				lf1 += 10;
//				lf2 += 10;
			
			
				
			}
//			if  (lf1+lf2>140)
//			{
//				Delay.msDelay(3000);
//				Main.pilot.travel(200);
//				Thread.yield();
//			}
			Thread.yield();
			}
			
		
			}
		
			
//			if  (lf1+lf2>140)
//			{
				Delay.msDelay(4000);
				Main.pilot.travel(200);			
//			}
			
			
			
		
			}
	}
	
	}
		
		
		



//		while (Main.pilot.isMoving() && !suppressed)
//			Thread.yield();
//
//		// Clean up
//		suppress();
////		Motor.B.rotateTo(0);
////		Motor.A.stop(
	
>>>>>>> 2b01a53e717fdb7f746d9142079fb7ff82cf5864
