import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class SnakeProject extends JFrame implements KeyListener {
	private DrawingPanel dp;
	private int DIRECTION = 0;
	private final int parts = 6;

	private ArrayList<Point> coords = new ArrayList<Point>(); 

	SnakeProject() {		
		for (int i = 0; i < parts; i++) {
			coords.add(new Point((300 - i * 10), 200));
		}
		addKeyListener(this);

		
		

		//region JMenuBar
		JMenuBar menubar = new JMenuBar();
		
		
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);
		
		
		JMenu exit = new JMenu("Exit");
		JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
        });
        menubar.add(exit);
        exit.add(eMenuItem);
        
        
        
        setJMenuBar(menubar);
        //endregion
        
        
        //region listeners
		ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	move();
            }
        };
        Timer timer = new Timer(150,taskPerformer);
        timer.setRepeats(true);
        //timer.start();
        //endregion
        
        //region frameComponents
		dp = new DrawingPanel();
		this.add(dp);
		
		this.setBackground(Color.BLACK);
		this.setResizable(false);
		this.setTitle("Snake Game");
		this.setSize(700,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//endregion
	}
	public static void main(String [] args) {
		SnakeProject w = new SnakeProject();
	}
	
	public void move() {
		final int frame = 10;

		switch(DIRECTION) {
    	case 0:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x + frame, coords.get(0).y));
    		break;
    	case 1:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x - frame, coords.get(0).y));
    		break;
    	case 2:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x, coords.get(0).y - frame));
    		break;
    	case 3:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x, coords.get(0).y + frame));
    		break;
    	}
	}
	
	public void head(Point p, int frame) {
		for(int i = 1; i < coords.size(); i++) {
			coords.set(i, new Point(coords.get(i-1).x, coords.get(i-1).y));
		}
		coords.set(1, p);
    	dp.repaint();
	}
	class DrawingPanel extends JPanel  {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawRect(40, 25, 600, 400);
			for(int i = 0; i < 6; i++) {
				g.setColor(new Color(0, 255, 55));
				g.fillOval(coords.get(i).x, coords.get(i).y, 10, 10);
			}
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) { DIRECTION = 0; move(); dp.repaint(); }
		else if (keyCode == KeyEvent.VK_LEFT) { DIRECTION = 1; move(); dp.repaint(); }
		else if (keyCode == KeyEvent.VK_UP) { DIRECTION = 2; move(); dp.repaint(); }
		else if (keyCode == KeyEvent.VK_DOWN) { DIRECTION = 3; move(); dp.repaint(); }
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}