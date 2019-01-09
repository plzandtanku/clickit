import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.temporal.*;
import java.io.*;
public class ClickIt{
	public static int count = 0;
	public static int endCount = 10;
	public static int prevX = 0;
	public static int prevY = 0;
	public static int max_width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	public static int max_height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public static LocalTime startTime;
	public static LocalTime endTime;
	public static String savefile = "clickit_highscores.txt";
	public static long current_highscore = 0;
	
	public static void startMenu(){
		JFrame frame = new JFrame("Click It!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel(new BorderLayout());
		JLabel title = new JLabel("Number of buttons to click: "+ endCount);
		Button button = new Button("Start Game");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchGame();
				frame.dispose();
			}
		});
		p.add(button);
		p.add(title, BorderLayout.NORTH);
		frame.setContentPane(p);
		frame.setSize(200,200);
		frame.setLocation(500,500);
		frame.setVisible(true);
	}
	public static void launchGame(){
		count = 0;
		// initialize the starting button
		Random rand = new Random();
		JFrame frame = new JFrame("Click It!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel(new BorderLayout());
		Button button = new Button("Click here!");
		button.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){
				count++;
				moveButton(frame, rand);
			//	frame.dispose();
			}
			public void mouseReleased(MouseEvent e){}
			
		});
		p.add(button);
		frame.setContentPane(p);
		frame.setSize(100,100);
		frame.setVisible(true);
		startTime = LocalTime.now();
		moveButton(frame, rand);
		System.out.println("done");
	}
	public static void endGame(){
		endTime = LocalTime.now();
		long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
		boolean updated = updateHighScore(seconds);
		String result = String.format("It took you approximately %d seconds to click all the buttons.", seconds);
		result += updated ? "\nYou've achieved a new high score!"  : "";
		result += String.format("\n The current high score is %d", current_highscore);
		JFrame frame = new JFrame("Game Over");
		JPanel p = new JPanel(new GridLayout(0,1));
		JPanel p2 = new JPanel(new GridLayout(0,2));
		JTextPane jp = new JTextPane();
		jp.setEditable(false);
		jp.setText(result);
		
		Button button = new Button("Quit");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
			}
		});
		
		Button button2 = new Button("Play Again");
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchGame();
				frame.dispose();
			}
		});
		p.add(jp);
		p2.add(button2);
		p2.add(button);
		p.add(p2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(p, BorderLayout.CENTER);
		frame.pack();
		frame.setLocation(500,500);
		frame.setVisible(true);
	}
	public static boolean updateHighScore(long score){
		try{
			if (current_highscore < score){
				return false;
			}
			BufferedWriter bf = new BufferedWriter(new FileWriter(new File(savefile)));
			bf.write(Long.toString(score));
			bf.flush();
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public static void moveButton(JFrame frame, Random rand){
		if (count >= endCount){
			endGame();
			frame.dispose();
			return;
		}

		int x = rand.nextInt(max_width-100);
		int y = rand.nextInt(max_height-100);
		while (prevX == x && prevY == y) x = rand.nextInt(max_height-100);
		prevX = x; prevY = y;
	//	frame.setSize(100,100);
		frame.setLocation(x,y);
		//frame.setVisible(true);
	}
	public static void main(String[] args){
		try {
			File file = new File(savefile);
			if (file.isFile()){
				Scanner sc = new Scanner(file);
				current_highscore = sc.nextLong();
			}
		}
		catch (InputMismatchException e){
			System.err.println("Malformed clickit_highscores file. I suggest deleting it");
		}		
		catch (Exception e){
			e.printStackTrace();
		}
		startMenu();
	}
	
}
