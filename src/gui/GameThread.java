package gui;

import game.Game;

/**
 * Created by Vladimir Danilov on 20/02/2018 : 23:30.
 */
public class GameThread implements Runnable {
	
	private static final boolean DEBUG = false;
	
	private int delay;
	public boolean play;
	private ConwayJFrame frame;
	
	public GameThread(ConwayJFrame frame, int delay) {
		this.frame = frame;
		if(delay < 100){
			this.delay = 100;
		}else{
			this.delay = delay;
		}
	}
	
	@Override
	public void run() {
		if(DEBUG)
			System.out.println("Game thread started");
		this.play = true;
		frame.readFieldToMemory();
		while(this.play){
			if(DEBUG){
				System.out.println("Generation: " + Game.getGen());
				Game.printField();
				Game.printNCount();
			}
			try {
				Thread.sleep(delay);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			Game.generation();
			frame.readMemoryToField();
			if(Game.isExtinct()){
				frame.gameEnded();
				break;
			}
		}
		if(DEBUG)
			System.out.println("Game thread ended");
	}
	
	public void stopGame(){
		this.play = false;
	}
}
