package gui;

import javax.swing.*;
import game.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vladimir Danilov on 20/02/2018 : 22:09.
 */
public class ConwayJFrame extends JFrame {
	
	private static final int WINDOW_WIDTH = 612;
	private static final int WINDOW_HEIGHT = 700;
	
	public static boolean gameInProgress = false;
	
	private Cell[][] cells;
	private JPanel field;
	
	private JButton startButton;
	private JButton stopButton;
	private JButton clearButton;
	private JButton demoButton;
	
	private GameThread gt;
	private Thread gameThread;
	
	public ConwayJFrame() {
		setTitle("Conway's game of life");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		
		gt = new GameThread(this, 300);
		
		setupField();
		
		setupButtons();
		
		setVisible(true);
	}
	
	private void setupField(){
		field = new JPanel();
		field.setLayout(new GridLayout(Game.FIELD_SIZE, Game.FIELD_SIZE));
		//field.setSize(new Dimension(Cell.SIZE * Game.FIELD_SIZE, Cell.SIZE * Game.FIELD_SIZE));
		field.setSize(610, 610);
		field.setLocation(-2, 1);
		
		cells = new Cell[Game.FIELD_SIZE][Game.FIELD_SIZE];
		for(int i = 0; i < Game.FIELD_SIZE; i++){
			for(int j = 0; j < Game.FIELD_SIZE; j++){
				cells[i][j] = new Cell();
				field.add(cells[i][j]);
			}
		}
		this.add(field);
	}
	
	private void setupButtons(){
		int buttonW = 150;
		int buttonH = 40;
		int buttons = 3;
		int buttonsGap = 1;
		
		startButton = new JButton("Start");
		startButton.setSize(buttonW, buttonH);
		startButton.setLocation((WINDOW_WIDTH - (buttonW + buttonsGap) * buttons) / 2, WINDOW_HEIGHT - 80);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setVisible(false);
				stopButton.setVisible(true);
				clearButton.setEnabled(false);
				demoButton.setEnabled(false);
				startGame();
			}
		});
		this.add(startButton);
		
		stopButton = new JButton("Stop");
		stopButton.setSize(buttonW, buttonH);
		stopButton.setLocation((WINDOW_WIDTH - (buttonW + buttonsGap) * buttons) / 2, WINDOW_HEIGHT - 80);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setVisible(true);
				stopButton.setVisible(false);
				clearButton.setEnabled(true);
				demoButton.setEnabled(true);
				gt.stopGame();
				gameInProgress = false;
			}
		});
		stopButton.setVisible(false);
		this.add(stopButton);
		
		clearButton = new JButton("Clear");
		clearButton.setSize(buttonW, buttonH);
		clearButton.setLocation((WINDOW_WIDTH - (buttonW + buttonsGap) * buttons) / 2 + (buttonsGap + buttonW),
				WINDOW_HEIGHT - 80);
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < Game.FIELD_SIZE; i++){
					for(int j = 0; j < Game.FIELD_SIZE; j++){
						cells[i][j].setAlive(false);
					}
				}
				readFieldToMemory();
			}
		});
		this.add(clearButton);
		
		demoButton = new JButton("Demo");
		demoButton.setSize(buttonW, buttonH);
		demoButton.setLocation((WINDOW_WIDTH - (buttonW + buttonsGap) * buttons) / 2 + (buttonsGap + buttonW) * 2,
				WINDOW_HEIGHT - 80);
		demoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.loadDemoField();
				readMemoryToField();
			}
		});
		this.add(demoButton);
	}
	
	public synchronized void readFieldToMemory(){
		for(int i = 0; i < Game.FIELD_SIZE; i++){
			for(int j = 0; j < Game.FIELD_SIZE; j++){
				Game.setCell(i, j, cells[i][j].isAlive());
			}
		}
	}
	
	public synchronized void readMemoryToField(){
		for(int i = 0; i < Game.FIELD_SIZE; i++){
			for(int j = 0; j < Game.FIELD_SIZE; j++){
				cells[i][j].setAlive(Game.isCellAlive(i, j));
			}
		}
	}
	
	private void startGame(){
		gameThread = new Thread(gt, "GameThread");
		gameThread.start();
		gameInProgress = true;
	}
	
	public synchronized void gameEnded(){
		stopButton.setVisible(false);
		startButton.setVisible(true);
		clearButton.setEnabled(true);
		demoButton.setEnabled(true);
		gameInProgress = false;
	}
	
}
