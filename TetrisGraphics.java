package Tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TetrisGraphics extends JPanel{
	
	private TetrisControls TC;
	
	private TetrisMethods TM;
	
	private Color[][] blockColor;
	
	public TetrisGraphics(TetrisControls controls, TetrisMethods methods)
	{
		TC = controls;
		TM = methods;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		blockColor = TC.getColorArray();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080);
		g.setColor(Color.CYAN);
		g.drawString("Points: " + TC.points + " | Highest Score: " + TC.greatestPoints, (TC.minX + TC.maxX) / 2 - 50, TC.minY - 2);
		g.drawLine(TC.minX, TC.minY, TC.minX, TC.maxY);
		g.drawLine(TC.minX, TC.minY, TC.maxX, TC.minY);
		g.drawLine(TC.minX, TC.maxY, TC.maxX, TC.maxY);
		g.drawLine(TC.maxX, TC.minY, TC.maxX, TC.maxY);
		for(int row = 0; row < blockColor.length; row ++)
			for(int col = 0; col < blockColor[row].length; col ++)
			{
				if(blockColor[row][col] == null)
				{
					g.setColor(new TetrisMethods().colorGen());
					g.drawRect(TC.space * col + TC.minX, TC.space * row + TC.minX, TC.space, TC.space);
				}
				else
				{
					g.setColor(blockColor[row][col]);
					g.fillRect(TC.space * col + TC.minX, TC.space * row + TC.minX, TC.space, TC.space);
					g.setColor(Color.BLACK);
					g.drawRect(TC.space * col + TC.minX, TC.space * row + TC.minX, TC.space, TC.space);
				}
			}
		TM.showNextBlock(g, TC.getNextBlock(), TC.maxX + 100, TC.minY + TC.space * 3, TC.space);
	}
}
