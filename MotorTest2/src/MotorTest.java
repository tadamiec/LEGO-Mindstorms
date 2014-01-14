import lejos.nxt.*;


public class MotorTest {
	public static void main(String[] args) {
		
		System.out.println("Prototy 2 : Motor Test");
		
		Motor.A.setSpeed(720);
		
		while(!Button.ESCAPE.isDown()){
			if(Button.ENTER.isDown()){
				Motor.A.forward();
			}
			if(Button.RIGHT.isDown()){
				Motor.B.rotate(10);
			}
			if(Button.LEFT.isDown()){
				Motor.B.rotate(-10);
			}
		}
		
	}

}
