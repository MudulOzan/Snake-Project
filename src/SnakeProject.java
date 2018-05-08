import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SnakeProject extends JFrame implements KeyListener {
	//region vars
	private int score = 0;
	private int SPEED = 200;
	private DrawingPanel dp;
	private JLabel lblScore;
	private int DIRECTION = 0;
	private int parts = 6;
	private final Timer timer;
	private ArrayList<Point> coords = new ArrayList<Point>(); 
	private int apple = 1;
	private static SnakeProject w;
    private BufferedImage image;
	private final int minY = 2;
	private final int maxY = 40;
	private final int minX = 4;
	private final int maxX = 60;
	private int aX = -100;
	private int aY = -100;
	private Point applePoint;
	//endregion
	SnakeProject() {		
		createSnake();
		
		addKeyListener(this);

		JLabel exitIcon = new JLabel(); 
		exitIcon.setIcon(new ImageIcon("C:\\Users\\Widmore\\hub\\Snake-Project\\bin\\download.png"));
		exitIcon.setBounds(150, 150, 16, 16);
		
		JPanel contentPanel = new JPanel();
		this.add(contentPanel);
		
		lblScore = new JLabel("Score");
		lblScore.setBounds(150, 150, 50, 50);
		lblScore.setForeground(Color.WHITE);
		contentPanel.add(lblScore);
		
		//region JMenuBar
		JMenuBar menubar = new JMenuBar();
		
		
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);
		
		JMenuItem start = new JMenuItem("Start");
		start.setToolTipText("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				score = 0;
				parts = 6;
				DIRECTION = 0;
				createApplePos();
				newGame();
				timer.start();
			}
		});
		menu.add(start);
		
		JMenuItem level = new JMenuItem("Level");
		level.setToolTipText("Level");
		
		menu.add(level);
		
		
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
        timer = new Timer(SPEED,taskPerformer);
        timer.setRepeats(true);
        timer.start();
        //endregion
        
        //region frameComponents
        dp = new DrawingPanel();
		this.add(dp);
		
		this.setUndecorated(true);
		this.setBackground(Color.BLACK);
		this.setResizable(false);
		this.setTitle("Snake Game");
		this.setSize(750,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//endregion
	}
	public static void main(String [] args) {
		w = new SnakeProject();
	}
	
	public void createSnake() {
		for (int i = 0; i < parts; i++) {
			coords.add(new Point((300 - i * 10), 200));
		}
	}
	
	public void newGame() {
		for (int i = 0; i < parts; i++) {
			coords.set(i, (new Point((300 - i * 10), 200)));
		}
	}
	//region appleTime
	
	public void createApplePos() {
		Random r = new Random(); 
		aX = (r.nextInt(maxX-minX)+minX) * 10;
		aY = (r.nextInt(maxY-minY)+minY) * 10;
	}
	public Point applePos() {
		applePoint = new Point(aX, aY);
		return applePoint;
	}
	//endregion
	
	public void move() {
		final int frame = 10;
		switch(DIRECTION) {
    	case 0:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x + frame, coords.get(0).y));
    		isRight();
    		bite();
    		break;
    	case 1:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x - frame, coords.get(0).y));
    		isLeft();
    		bite();
    		break;
    	case 2:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x, coords.get(0).y - frame));
    		isUp();
    		bite();
    		break;
    	case 3:
    		head(coords.get(0), frame);
			coords.set(0, new Point(coords.get(0).x, coords.get(0).y + frame));
    		isDown();
    		bite();
    		break;
    	}
		eat(coords.get(0));
	}
	
	//region eat
	public void eat(Point p) {
		if(p.x == applePoint.x && p.y == applePoint.y) {
			createApplePos();
			score += 10;
		}
	}
	//endregion
	
	//region where is my snake?
	public void isLeft() {
		if (coords.get(0).x == 30) coords.set(0, new Point(630, coords.get(0).y));

	}
	public void isRight() {		
		if (coords.get(0).x == 640) coords.set(0, new Point(40, coords.get(0).y));
	}
	public void isUp() {
		if (coords.get(0).y <= 10) coords.set(0, new Point(coords.get(0).x, 410));
	}
	public void isDown() {
		if (coords.get(0).y >= 420) coords.set(0, new Point(coords.get(0).x, 20));
	}
	//endregion 
	
	//region follow my lead
	public void head(Point p, int frame) {
		for(int i = coords.size()-1; i > 1 ; i--) {
			coords.set(i, new Point(coords.get(i-1).x, coords.get(i-1).y));
		}
		coords.set(1, p);
    	dp.repaint();
	}
	//endregion
	
	//region me snake bite meself
	public void bite() {
		
		for (int i = 1; i < coords.size(); i++) {
			if (coords.get(0).x == coords.get(i).x && coords.get(0).y == coords.get(i).y) { 
				timer.stop(); 
				JOptionPane.showMessageDialog(w, new JLabel("Game Over. our score: " + score,JLabel.CENTER), "Game Over", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	//endregion

	
	class DrawingPanel extends JPanel  {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawRect(40, 20, 600, 400);
			g.setColor(Color.WHITE);
			g.drawString("Score: " + score, 650, 40);
			for(int i = 0; i < 6; i++) {
				g.setColor(new Color(0, 190, 0));
				g.fillOval(coords.get(i).x, coords.get(i).y, 10, 10);
				setBackground(Color.BLACK);
			}
			if (apple == 1) {
				createApplePos();
				apple = 0;
			}
			URL resource = getClass().getResource("apple.png");
	        try {
	            image = ImageIO.read(resource);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			g.setColor(new Color(255, 0, 0));
			g.drawImage(image, applePos().x, applePos().y, this);
		}
	}

	//region keyEvents
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT && DIRECTION != 1 && DIRECTION != 0) { DIRECTION = 0; }
		else if (keyCode == KeyEvent.VK_LEFT && DIRECTION != 0 && DIRECTION != 1) { DIRECTION = 1; }
		else if (keyCode == KeyEvent.VK_UP && DIRECTION != 3 && DIRECTION != 2) { DIRECTION = 2; }
		else if (keyCode == KeyEvent.VK_DOWN && DIRECTION != 2 && DIRECTION != 3) { DIRECTION = 3; }
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	//endregion
}