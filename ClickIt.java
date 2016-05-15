import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.temporal.*;
public class ClickIt{
	public static int count = 0;
	public static LocalTime startTime;
	public static LocalTime endTime;
	public static void startMenu(){
		JFrame frame = new JFrame("Click It!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel(new BorderLayout());
		Button button = new Button("Start Game");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startTime = LocalTime.now();
				launchGame();
				frame.dispose();
			}
		});
		p.add(button);
		frame.setContentPane(p);
		frame.setSize(200,200);
		frame.setLocation(500,500);
		frame.setVisible(true);
	}
	public static void launchGame(){
		generateButton();
		System.out.println("done");
	}
	public static void endGame(){
		endTime = LocalTime.now();
		long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
		System.out.format("It took you %d seconds to click all the buttons. Wow, that is awful", seconds);
	}
	public static void generateButton(){
		if (count >= 2){
			endGame();
			return;
		}
		JFrame frame = new JFrame("Click It!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel(new BorderLayout());
		Button button = new Button("Click here!");
		button.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				count++;
				generateButton();
				frame.dispose();
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
			
		});
		p.add(button);
		frame.setContentPane(p);
		int max_width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		int max_height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		Random rand = new Random();
		int x = rand.nextInt(max_width-100);
		int y = rand.nextInt(max_height-100);
		frame.setSize(100,100);
		frame.setLocation(x,y);
		frame.setVisible(true);
	}
	public static void main(String[] args) throws InterruptedException{
		//LocalTime test = LocalTime.now();
		//Thread.sleep(5000);
	//	LocalTime test2 = LocalTime.now();
//long minutes = ChronoUnit.SECONDS.between(test, test2);
//System.out.println(minutes);
//if (1>0){
//return;
//}
		startMenu();
	}
	
}