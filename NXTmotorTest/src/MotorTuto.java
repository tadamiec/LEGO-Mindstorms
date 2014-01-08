

 import lejos.nxt.Button;
 import lejos.nxt.LCD;
 import lejos.nxt.Motor;
 
 /**
  * Stop after 4 revolutions based on time, then based on tacho count
  * @author Roger
  *
  */
 public class MotorTuto {
 
      public static void main(String[] args) 
      {
           LCD.drawString("First test", 0, 0);

           
           while(!Button.ESCAPE.isDown()){
        	   	if(Button.RIGHT.isDown()){
        		    Motor.A.setSpeed(720);
        	   		Motor.A.forward();
        	   		Motor.B.setSpeed(360);
        	   		Motor.B.forward();
           		}
           		if(Button.LEFT.isDown()){
           			Motor.B.setSpeed(720);
           			Motor.B.forward();
           			Motor.A.setSpeed(360);
           			Motor.A.forward();
           		}
           		if(Button.ENTER.isDown()){
           			Motor.B.setSpeed(720);
        		    Motor.A.setSpeed(720);             
           			Motor.A.forward();
           			Motor.B.forward();
           		}
           		
           }
           
           LCD.clear();
          /* Delay.msDelay(2000);
           LCD.drawInt(Motor.A.getTachoCount(),0,0);
           Motor.A.stop();
           LCD.drawInt(Motor.A.getTachoCount(),0,1);
           Motor.A.backward();
           while (Motor.A.getTachoCount()>0); 
           LCD.drawInt(Motor.A.getTachoCount(),0,2);
           Motor.A.stop();
           LCD.drawInt(Motor.A.getTachoCount(),0,3);*/
          
     }
 
 }
 
 

