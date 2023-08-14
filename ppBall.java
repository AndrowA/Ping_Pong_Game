
/**
 * A class that implements our main simulation loop to move the ball and make it bounce on the ground and the paddles
 * Extends he Thread Class, therefore its run method executes concurrently with all other methods 
 * Code inspired from code taken from the assignment handout given by Prof. Frank Ferrie
 */

package ppPackage;

import java.awt.Color;
import java.awt.event.ActionEvent;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import static ppPackage.ppSimParams.*;

public class ppBall extends Thread{

	// Instance variables
	private double Xinit; 				// Initial position of ball - X
	private double Yinit; 				// Initial position of ball - Y
	private double Vo; 					// Initial velocity (Magnitude)
	private double theta; 				// Initial direction
	private double loss; 				// Energy loss on collision
	private Color color; 				// Color of ball
	private GraphicsProgram GProgram; 	// Instance of ppSim class (this)
	GOval myBall; 						// Graphics object representing ball
	ppTable myTable;					// Instance of myTable 
	ppPaddle RPaddle;					// Instance of right ppPaddle
	ppPaddle LPaddle;					// Instance of left ppPaddle 
	double X, Xo, Y, Yo;				// Position of the ball
	double Vx, Vy; 						// Velocity of the ball
	boolean running; 					// Boolean that can be used to terminate the simulation


	/**
	 * The constructor for the ppBall class copies parameters to instance variables, creates an
	 * instance of a GOval to represent the ping-pong ball, and adds it to the display.
	 *
	 * @param Xinit - starting position of the ball X (meters)
	 * @param Yinit - starting position of the ball Y (meters)
	 * @param Vo - initial velocity (meters/second)
	 * @param theta - initial angle to the horizontal (degrees)
	 * @param loss - loss on collision ([0,1])
	 * @param color - ball color (Color)
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 * @param myTable - a reference to the table object created in the main program
	 */

	public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, 
			Color color, GraphicsProgram GProgram, ppTable myTable) {

		// Copy constructor parameters to instance variables

		this.Xinit=Xinit; 
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.color=color;
		this.GProgram=GProgram;
		this.myTable = myTable;
		this.myBall = new GOval(Xinit*Xs,Yinit*Ys,2*bSize*Xs,2*bSize*Ys);	//Setting the initial parameters of the Ball	
		myBall.setColor(color);		// Set the Color of the Ball
		myBall.setFilled(true);		// Set the Ball to be filled 
		GProgram.add(myBall);		// Add the instance of the Ball
	}

	/**
	 * In a thread, the run method is NOT started automatically (like in Assignment 1).
	 * Instead, a start message must be sent to each instance of the ppBall class, e.g.,
	 * ppBall myBall = new ppBall (--parameters--);
	 * myBall.start();
	 * The body of the run method is essentially the simulator code you wrote for A1.
	 */

	/**
	 * The run method contains our main while loop, that is executed to move the ball and allow it to bounce
	 */

	public void run() {
		
		// Initialize simulation parameters

		Xo = Xinit;							// Set initial X position
		Yo = Yinit;							// Set initial Y position
		double time = 0;							// Time starts at 0 and counts up
		double Vt = bMass*g / (4*Pi*bSize*bSize*k); // Terminal velocity
		double Vox=Vo*Math.cos(theta*Pi/180);		// X component of velocity
		double Voy=Vo*Math.sin(theta*Pi/180);		// Y component of velocity
		running = true;						// Total energy is not 0 

		// Important - X and Y are ***relative*** to the initial starting position Xo,Yo.
		// So the absolute position is Xabs = X+Xo and Yabs = Y+Yo.

		// Also - print out a header line for the displayed values during test cases
		if (TEST) {
			System.out.printf("\t\t\t Ball Position and Velocity\n");
		}


		// Energy values

		double KEx = 0.5*bMass*Vox*Vox;		// X Component of Kinetic Energy
		double KEy = 0.5*bMass*Voy*Voy;		// Y Component of Kinetic Energy
		double PE = bMass*g*Yo;				// Potential Energy
		double ETHR = 0.001;				// Minimum Energy required for the ball to keep moving

		// Simulation loop.  Calculate position and velocity, print, increment
		// time.  Do this until ball hits the ground.

		// Main simulation loop
		while (running) {
			X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));				// Update relative position
			Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
			Vx = Vox*Math.exp(-g*time/Vt);						// Update velocity
			Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;


			// Print Values for Test cases

			if(TEST) {
				System.out.printf("t: %.2f\t\t X: %.2f\t Y: %.2f\t Vx: %.2f\t Vy: %.2f\n",
						time,X+Xo,Y+Yo,Vx,Vy);
			}


			GProgram.pause(TICK*timeSlider.getValue()*TSCALE);										// Pause program for SLEEP mS

			// Check to see if we hit the ground yet.  When the ball hits the ground, the height of the center
			// is the radius of the ball. 


			// Stop the simulation if the ball goes out of bounds 

			if (Y+Yo > Ymax){
				
				// Give the point to the Human if the Agent returns a ball out of bounds
				if (Vx >= 0) {
					scoreH += 1;
					humanScore.setText(String.valueOf(scoreH));
				}
				
				// Give the point to the Agent if the Human returns a ball out of bounds
				else {
					scoreA += 1;
					agentScore.setText(String.valueOf(scoreA));
				}
				
				// Stop the simulation
				break;
			}

			// Collision with the floor

			if ((Yo + Y <= bSize) & (Vy <0)){

				// Components of the energy accounting for the energy loss

				KEx = 0.5*bMass*Vx*Vx*(1-loss);
				KEy = 0.5*bMass*Vy*Vy*(1-loss);
				PE=0;
				Vox = Math.sqrt(2*KEx/bMass);
				Voy = Math.sqrt(2*KEy/bMass);

				if (Vx<0) Vox= -Vox;		// Vox depends on Vx

				// re-initialize the time and motion parameters

				time = 0;		// Time is reset to 0 at every collision
				Xo+=X;			// Need to accumulate distance between collision
				Yo = bSize;		// The absolute position of the ball on the ground
				X=0;			// (X,Y) is the instantaneous position along an arc,
				Y=0;			// Absolute position is (Xo+X,Yo+Y).

				if ((KEx + KEy + PE <= ETHR)) running = false; // Stopping Condition

			}

			// Collision with the right paddle

			if ((Vx > 0) && (Xo+X) >= (RPaddle.getP().getX()- ppPaddleW/2 - bSize)) {


				//Possible collision
				if (RPaddle.contact(X+Xo, Y+Yo)) {

					// Components of the energy accounting for the energy loss

					KEx = 0.5*bMass*Vx*Vx*(1-loss);
					KEy = 0.5*bMass*Vy*Vy*(1-loss);
					PE=bMass*g*Y;

					Vox = Math.sqrt(2*KEx/bMass);
					Voy = Math.sqrt(2*KEy/bMass);

					Vox=Vox*ppPaddleXgain; 							// Scale X component of velocity

					// Limit the maximum velocity of the ball
					if (Vox > voxMAX) {
						Vox = voxMAX;
					}

					Voy=Voy*ppPaddleYgain*RPaddle.getSgnVy(); 	// Scale Y + same direction as paddle 	

					//Voy=Voy*ppPaddleYgain*RPaddle.getV().getY();

					// re-initialize the time and motion parameters
					time = 0;										// Time is reset to 0 at every collision
					Xo= RPaddle.getP().getX()- ppPaddleW/2 -bSize;	// The absolute position of the ball on the right paddle
					Yo+= Y;		// Need to accumulate distance between collision
					X=0;		// (X,Y) is the instantaneous position along an arc,
					Y=0;		// Absolute position is (Xo+X,Yo+Y).
					Vox = -Vox;	// Change the direction of the horizontal velocity


				}
				else {
					running = false;
					Xo= RPaddle.getP().getX()- ppPaddleW/2 -bSize; // To make sure the ball doesn't go past the paddle
					X=0; 
					// increment agent's points if the human doesn't return the ball
					scoreA += 1;
					agentScore.setText(String.valueOf(scoreA));
				}


			}

			// Collision with the left paddle

			if ((Vx < 0) && (Xo+X) <= (LPaddle.getP().getX() + ppPaddleW/2 + bSize)) {


				//Possible collision
				if (LPaddle.contact(X+Xo, Y+Yo)) {

					// Components of the energy accounting for the energy loss

					KEx = 0.5*bMass*Vx*Vx*(1-loss);
					KEy = 0.5*bMass*Vy*Vy*(1-loss);
					PE=bMass*g*Y;

					Vox = Math.sqrt(2*KEx/bMass);
					Voy = Math.sqrt(2*KEy/bMass);

					Vox=Vox*LPaddleXgain; 						// Scale X component of velocity
					
					// Limit the maximum velocity of the ball
					if (Vox > voxMAX) {
						Vox = voxMAX;
					}
					
					Voy=Voy*LPaddleYgain*LPaddle.getSgnVy(); 	// Scale Y + same direction as paddle 	
					
					
					//Voy=Voy*ppPaddleYgain*RPaddle.getV().getY();

					// re-initialize the time and motion parameters
					time = 0;										// Time is reset to 0 at every collision
					Xo= LPaddle.getP().getX()+ ppPaddleW/2 + bSize;	// The absolute position of the ball on the right paddle
					Yo+= Y;		// Need to accumulate distance between collision
					X=0;		// (X,Y) is the instantaneous position along an arc,
					Y=0;		// Absolute position is (Xo+X,Yo+Y).

				}
				else {
					running = false;
					Xo= LPaddle.getP().getX()+ ppPaddleW/2 +bSize; // To make sure the ball doesn't go past the paddle
					X=0; 
					
					// increment human's points if the agent doesn't return the ball
					scoreH +=1;
					humanScore.setText(String.valueOf(scoreH));
				}

			}



			// Update the position of the ball.  Plot a tick mark at current location.

			GPoint p = myTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize));		// Get current position in screen coordinates
			double ScrX = p.getX();
			double ScrY = p.getY();
			myBall.setLocation(ScrX,ScrY);

			// Trace the trajectory of the ball if the toggle button is on 
			if (traceButton.isSelected()) {
				trace(ScrX,ScrY);
			}
			
			time += TICK;

		}
	}

	
	/**
	 * A simple method to plot a dot at the current location in screen coordinates
	 * @param scrX
	 * @param scrY
	 */

	private void trace(double ScrX, double ScrY) {
		GOval pt = new GOval(ScrX,ScrY,PD,PD);
		pt.setColor(Color.BLACK);
		pt.setFilled(true);
		GProgram.add(pt);	
	}

	/**
	 * A setter to assign the Rpaddle instance we have created to the paddle instance of our ball
	 * @param myPaddle
	 */

	public void setRightPaddle(ppPaddle myPaddle) {
		this.RPaddle = myPaddle;
	}

	/**
	 * A setter to assign the Lpaddle instance we have created to the paddle instance of our ball
	 * @param myPaddle
	 */

	public void setLeftPaddle(ppPaddle myPaddle) {
		this.LPaddle = myPaddle;
	}

	/**
	 *  Getter for the ball velocity
	 *  @return v - the velocity of the ball, containing both the x and y components
	 */
	
	public GPoint getV() {
		return new GPoint(Vx,Vy);
	}
	
	/**
	 *  Getter for the ball position
	 *  @return p - the position of the ball, containing both the x and y components
	 */
	
	public GPoint getP() {
		return new GPoint(X+Xo, Y+Yo);
	}

	/**
	 *  Method to terminate the simulation 
	 */
	
	void kill() {
		running = false;
	}
	
}

