package com.nm.app;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class TreeTriangle extends Canvas implements Runnable,MouseListener {
	private static final long serialVersionUID = 1L;

	private final int WIDHT,HEIGHT;
	
	private BufferedImage img;
	private int imgPix[];
	
	private boolean running = false;
	
	private boolean convertion;
	private double angle;
	private double angleStep;
	
	private double tick = 0;
	private double step = 0;
	
	public TreeTriangle() {
		this.WIDHT = 800;
		this.HEIGHT = 600;
		
		this.img = new BufferedImage(WIDHT, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.imgPix = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		this.convertion = false;
		this.angle = Math.PI/2;
		this.angleStep = 0.001;
		
		JFrame frame = new JFrame();
		frame.setSize(WIDHT, HEIGHT);
		frame.setTitle("Fractal trees and triangles");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		addMouseListener(this);
		frame.add(this);
		
		frame.setVisible(true);
		
		running = true;
	}
	
	private void render() {
		BufferStrategy bs;
		bs = this.getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		for(int i=0;i<WIDHT;i++) {
			for(int j=0;j<HEIGHT;j++) {
				imgPix[i+j*WIDHT] = 0;
			}
		}
		
		if(!convertion) {
			tick++;
			if(!(step>Math.PI*2/3))
				step = angleStep*tick;
		}else {
			tick--;
			if(!(step<Math.PI/6))
				step = angleStep*tick;
		}
		
		drawTree(WIDHT/2, HEIGHT*9/10, angle,	step, HEIGHT*2/5, img.getGraphics());
		
		g.drawImage(img, 0, 0, null);
		
		g.dispose();
		bs.show();
	}
	
	private void drawTree(int startX,int startY,double angle,double step,int lenght,Graphics g) {
		if(lenght<1)
			return;
		int x = startX + (int)(Math.cos(angle)*lenght);
		int y = startY - (int)(Math.sin(angle)*lenght);
		
		g.setColor(Color.WHITE);
		g.drawLine(startX, startY, x, y);
		
		drawTree(x, y, angle-step, step, lenght/2, g);
		drawTree(x, y, angle, step, lenght/2, g);
		drawTree(x, y, angle+step, step, lenght/2, g);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		convertion = !convertion;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		TreeTriangle app = new TreeTriangle();
		Thread thread = new Thread(app);
		thread.run();
	}

	@Override
	public void run() {
		while(running) {
			render();
		}
	}

}
