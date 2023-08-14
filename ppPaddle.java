
/**
 * Class that creates a ppPaddle instance and exports methods for interacting with the paddle instance
 * Extends the Thread Class, therefore its run method executes concurrently with all other methods 
 * Code inspired from code taken from the assignment handout given by Prof. Frank Ferrie and the tutorial session given by Katrina Poulin
 */

package ppPackage;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;
import java.awt.Color;

public class ppPaddle extends Thread {
	
	public double X;   // Horizontal Position of the center of the paddle
	public double Y;	// Vertical Position of the center of the paddle
	public double Vx;	// Horizontal velocity of the paddle
	public double Vy;	// Vertical Velocity of the paddle
	
	private ppTable myTable;			// Instance of myTable 
	public GraphicsProgram GProgram;	// Instance of ppSim class (this)
	private GRect myPaddle;				// Graphics object representing paddle
	
	/**
	 * The constructor for the ppPaddle class copies parameters to instance variables, creates an
	 * instance of GProgram and myTable 
	 *
	 * @param X - Horizontal Position of the center of the paddle
	 * @param Y - Vertical Position of the center of the paddle
	 * @param myColor - The color of the paddle
	 * @param myTable - a reference to the table object created in the main program
	 * @param GProgram - a reference to the ppSim class used to manage the display 
	 */
	
	public ppPaddle (double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		this.X = X;
		this.Y = Y;
		this.myTable = myTable;
		this.GProgram= GProgram;
		
		// World Coordinates of the top left corner of the paddle
		double upperLeftX = X - ppPaddleW/2;
		double upperLeftY = Y + ppPaddleH/2;
		
		// Top left corner of the paddle in screen coordinates
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY)); 
		double ScrX = p.getX();
		double ScrY = p.getY();
		
		// Create a graphics object to represent the paddle using screen coordinates
		this.myPaddle = new GRect(ScrX, ScrY, ppPaddleW*Xs, ppPaddleH*Ys); 
		myPaddle.setFilled(true);
		myPaddle.setColor(myColor);
		GProgram.add(this.myPaddle);
	}
	
	/**
     * Method to get the paddle's location (X,Y)
     * @return p - the paddle's location
     */
	
	public GPoint getP() {
		return new GPoint(X,Y);
	}
	
	/**
	 * Run method used to continually update the paddle velocity in response to 
	 * user induced changes in position
	 */
	
	public void run() {
		double lastX = X; 	// Previous X Position
		double lastY = Y;	// Previous Y Position
		while (true) {
			Vx=(X-lastX)/TICK;	// Update the paddle's horizontal velocity
			Vy=(Y-lastY)/TICK;	// Update the paddle's vertical velocity
			lastX=X;
			lastY=Y;
			GProgram.pause(TICK*TSCALE); // Time to mS
		}
	}
	
	/**
     * Method to set and move the paddle to (X,Y)
     */
	
	public void setP(GPoint P) {
		
		//update instance variables 
		this.X = P.getX();
		this.Y = P.getY();
		
		// World Coordinates of the upper left corner of the paddle
		double upperLeftX = X - ppPaddleW/2;
		double upperLeftY = Y + ppPaddleH/2;
		
		// Upper left corner of the paddle in screen coordinates
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY)); 
		double ScrX = p.getX();
		double ScrY = p.getY();
		
		// move the GRect instance
		myPaddle.setLocation(ScrX,ScrY);
	}
	
	/**
     * Method to get the velocity of the paddle (Vx,Vy)
     * @return v - containing the x and y velocity of the paddle 
     */
	
	public GPoint getV() {
		return new GPoint(Vx,Vy);
	}
	
	/**
     * Method to get the sign of the Y velocity of the paddle 
     * @return - sign of the Y velocity
     */
	
	public double getSgnVy() {
		
		// return 1 when Vy >= 0
		if (Vy >= 0) return 1;
		else return (-1); 	
		// return -1 when Vy < 0
		
	}
	
	/**
     * Method to determine if a surface at position (Sx,Sy) is in contact with the paddle 
     * @param Sx - x position
     * @param Sy - y position
     * @return boolean
     */
	
	public boolean contact(double Sx, double Sy) {
		
		return ((Sy >= Y- ppPaddleH/2) && (Sy <= Y + ppPaddleH/2));
	}
	
}
