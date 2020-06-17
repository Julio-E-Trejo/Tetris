package Tetris;

import java.awt.Color;
import java.io.*;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TetrisGame extends JFrame{
	
	private int width, height;
	
	private AudioInputStream stream;
    
	private AudioFormat format;
    
	private DataLine.Info info;
    
	private Clip clip;
    
	/**Initializes the necessary variables to play a music file.
	 * @param play the music file to be played; must be a ".wav" file*/
    private void music(File play){
	    try {
		    stream = AudioSystem.getAudioInputStream(play);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		}
		catch (Exception e) {
			System.out.println("*** This file location does not have anything or "
					+ "does not exist. Please, try something else. ***");
			System.exit(0);
		}
    }
    
    /**Plays the given music file on a continuous loop.*/
    private void loop() {
    	clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
	@SuppressWarnings("static-access")
	public TetrisGame(int prefferedDelay, int rows, int columns){
		TetrisMethods methods = new TetrisMethods();
		TetrisControls controls = new TetrisControls(prefferedDelay, rows, columns);
		TetrisGraphics gui = new TetrisGraphics(controls, methods);
		gui.setBackground(Color.BLACK);
		this.addKeyListener(controls);
		this.add(gui);
		width = columns * controls.space + 100;
		height = rows * controls.space + 100;
		setBounds(0, 0, width + 200, height);
		setTitle("Tetris.wav");
		setVisible(true);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		TetrisGame game = new TetrisGame(70, 40, 20);
		game.music(new File("TetrisAcapella.wav"));
		game.loop();
		while(true)
		{
			game.repaint();
			
		}
	}
}
