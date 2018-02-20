package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vladimir Danilov on 20/02/2018 : 22:10.
 */
public class Cell extends JButton {
	
	private boolean alive;
	
	public Cell() {
		setBackground(Color.WHITE);
		alive = false;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!ConwayJFrame.gameInProgress) {
					setAlive(!alive);
				}
			}
		});
	}
	
	public void setAlive(boolean isAlive){
		this.alive = isAlive;
		setBackground(alive ? Color.BLACK : Color.WHITE);
	}
	public boolean isAlive(){
		return this.alive;
	}
	
}
