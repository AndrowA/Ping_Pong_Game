
/**
 * A class that implements a run method to generate the table, the floor, and random parameters for the ball
 * Tracks the position of the mouse to control the right paddle,
 * Creates an instance of a ball and the paddles, and start the simulation
 * This class has no explicit constructor
 * Code inspired from code taken from the assignment handout given by Prof. Frank Ferrie and the tutorial session given by Katrina Poulin
 */

package ppPackage;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class ppSim extends GraphicsProgram{

	ppTable myTable;		// Instance of myTable
	ppPaddle RPaddle;		// Instance of RPaddle
	ppPaddleAgent LPaddle;	// Instance of LPaddle
	ppBall myBall;			// Instance of myBall
	RandomGenerator rgen;	// Instance of rgen
	
	// Main entry point of our program
	public static void main(String[] args) {
		new ppSim().start(args);
	}


	/**
	 * This init method is used to generate the display (including the floor, buttons, sliders and labels), and random parameters for the ball,
	 * Tracks the position of the mouse, and the activation of buttons
	 * Creates an instance of a ball and the paddles, and start the simulation
	 */	

	public void init() {

		this.resize(ppSimParams.WIDTH+OFFSET,ppSimParams.HEIGHT+OFFSET);	// initialize window size

		// Create the buttons:

		// New serve button
		JButton newServeButton = new JButton("New Serve");
		// Quit Button
		JButton quitButton = new JButton("Quit");		
		// Trace Button
		traceButton = new JToggleButton("Trace");
		// Clear Button
		JButton clearButton = new JButton("Clear");			
		
		//Sliders' labels and buttons to reset them:
		JLabel timeI = new JLabel("+t");
		JLabel timeD = new JLabel("-t");
		JButton rtime = new JButton("rtime");
		JLabel lagD = new JLabel("-lag");
		JLabel lagI = new JLabel("+lag");
		JButton rlag = new JButton("rlag");
		
		// Add the scoreboard
		add(agent, NORTH);
		add(agentScore, NORTH);
		add(human, NORTH);
		add(humanScore, NORTH);
		
		// Add the buttons:
		add(newServeButton, SOUTH);
		add(clearButton, SOUTH);	
		add(quitButton, SOUTH);
		add(traceButton, SOUTH);
		
		// Add sliders and buttons to reset them
		add(timeI, SOUTH);
		add(timeSlider, SOUTH);
		add(timeD, SOUTH);
		add(rtime, SOUTH);
		add(lagD, SOUTH);
		add(lagSlider, SOUTH);
		add(lagI, SOUTH);
		add(rlag, SOUTH);
		
		// Adding listeners
		addMouseListeners();
		addActionListeners();

		//Generate random parameters for ppBall
		rgen = RandomGenerator.getInstance();
		rgen.setSeed(RSEED);

		// Create the ppTable instance
		myTable = new ppTable(this);
		
		pause(1000);

		// Start a new Game 
		newGame();


	}
	
	/** 
	 * Method to generate random parameters and return an instance of ppBall
	 * @return a new ppBall instance
	 */
	
	ppBall newBall() {

		// Generate random parameters for the simulation
		Color iColor = Color.RED;							// Ball's color
		double iYinit = rgen.nextDouble(YinitMIN,YinitMAX);	// Initial Y position
		double iLoss = rgen.nextDouble(EMIN,EMAX);			// Energy loss
		double iVel = rgen.nextDouble(VoMIN,VoMAX);			// Launch velocity
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);	// Launch angle

		// Create a ppBall instance
		return new ppBall(Xinit,iYinit,iVel,iTheta,iLoss,iColor,this, myTable);
	}
	
	/**
	 * Method to stop the current game and start a new one
	 */
	
	public void newGame() {
		 if (myBall != null) myBall.kill(); // stop current game in play
		 myTable.newScreen();	// Make a new display
		 myBall = newBall();	// Make a new ball instance
		 
		 // Create the paddle instances
		 RPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, Color.GREEN, myTable, this);
		 LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.BLUE, myTable, this);
		 
		 // Assign the myball object to the myball instance of the paddle
		 LPaddle.attachBall(myBall);
		 
		 // Assign the RPaddle and LPaddle objects to the myball paddles instance variables
		 myBall.setRightPaddle(RPaddle);
		 myBall.setLeftPaddle(LPaddle);
		 
		 pause(STARTDELAY);
		 
		 // Start the threads 
		 myBall.start();
		 LPaddle.start();
		 RPaddle.start();
		}


	/**
	 * Mouse Handler - to moves the paddle up and down in Y
	 * @param - e, a mouse a event
	 */

	public void mouseMoved(MouseEvent e) {
		// convert mouse position to a point in screen coordinates
		if (myTable==null || RPaddle==null) return; // To avoid the null pointer exception
		GPoint Pm = myTable.S2W(new GPoint(e.getX(),e.getY()));
		double PaddleX = RPaddle.getP().getX();
		double PaddleY = Pm.getY();
		RPaddle.setP(new GPoint(PaddleX,PaddleY));
	}
	
	
	/**
	 * Method to receive input from the buttons and execute commands in return
	 * @param e, an action event
	 */
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// If the the "New serve" button is pressed, a new game will start
		if (command.equals("New Serve")) {
			newGame();
		}
		// If the the "Quit" button is pressed, the game will end
		else if (command.equals("Quit")) {
			System.exit(0);
		}
		// If the the "rtime" button is pressed, the slider to control the time will go back to its initial state
		else if (command.equals("rtime")) {
			timeSlider.setValue(0);
		}
		// If the the "rlag" button is pressed, the slider to control the agent's lag will go back to its initial state
		else if (command.equals("rlag")) {
			lagSlider.setValue(0);
		}
		// If the the "Clear" button is pressed, the score of the agent and the human will be set back to 0
		else if (command.equals("Clear")) {
			scoreA = 0;
			agentScore.setText(String.valueOf(scoreA));
			scoreH = 0;
			humanScore.setText(String.valueOf(scoreH));
		}
	}


}
