package ppPackage;

import java.awt.Color;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * Class that creates a left Agent paddle that follows the Y position of the ball and returns it to play against a human
 * Extends the ppPaddle Class
 * Code inspired from code taken from the assignment handout given by Prof. Frank Ferrie and the tutorial session given by Katrina Poulin
 */
import static ppPackage.ppSimParams.*;

public class ppPaddleAgent extends ppPaddle {
	
	ppBall myBall; // Ball instance variable to get the position of the ball
	
	/**
	 * Constructor used to refers to the superclass constructor
	 * @param X - Horizontal Position of the center of the paddle
	 * @param Y - Vertical Position of the center of the paddle
	 * @param myColor - The color of the paddle
	 * @param myTable - a reference to the table object created in the main program
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 */
	
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		
		super(X, Y, myColor,myTable, GProgram); 
	}
	
	/**
	 * Method to move the agent paddle according to the position of the ball and slow it down
	 */
	
	public void run(){
		
		int ballSkip = 0;
		
		// Variable to slow the Agent down
		int AgentLag = lagSlider.getValue(); 
		
		double lastX = X;
		
		double lastY =Y;
		
		while(true) {
			
			// Since the run method of this class overwrites the run method of the superclass, we have to add its elements here:
			
			Vx=(X-lastX)/TICK;	// Update the paddle's horizontal velocity
			Vy=(Y-lastY)/TICK;	// Update the paddle's vertical velocity
			lastX=X;
			lastY=Y;
			
			// To slow the paddle using a counter and set the position of the ball accordingly
			if (ballSkip++ >= AgentLag) {
				
				// Set the paddle position to that ball's Y position
				this.setP(new GPoint(this.X, this.myBall.getP().getY()));
				
				ballSkip=0;
			}
			
			this.GProgram.pause(TICK*TSCALE);	// Pause to slow the LeftPaddle down
			
		}
	}
	
	/**
	 * Setter method to set the value of the myBall instance variable
	 * @param myball 
	 */
	
	public void attachBall(ppBall myBall){
		this.myBall = myBall;
	}
		
	
	
}
