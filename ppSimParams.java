
/**
 * A class that contains all the parameters used in the simulation including:
 * The parameters defined in screen coordinates,
 * The Ping pong table parameters,  
 * The parameters defined in simulation coordinates, 
 * The paddles' Parameters, 
 * The parameters used by the ppSim class,  
 * The booleans to test our code, and  
 * The parameters for our interface
 * Inspired from code taken from the assignment handout given by Prof. Frank Ferrie
 */

package ppPackage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import acm.graphics.GPoint;

public class ppSimParams {
	
	// 1. Parameters defined in screen coordinates (pixels, acm coordinates)

	 public static final int WIDTH = 1280; 			// n.b. screen coordinates
	 public static final int HEIGHT = 600;
	 public static final int OFFSET = 200;
	
	 // 2. Ping-pong table parameters
	 public static final double ppTableXlen = 2.74; 	// Length
	 public static final double ppTableHgt = 1.52; 		// Ceiling
	 public static final double XwallL = 0.05; 			// Position of l wall
	 public static final double XwallR = 2.69; 			// Position of r wall

	// 3. Parameters defined in simulation coordinates

	 public static final double g = 9.8; 			// MKS
	 public static final double k = 0.1316; 		// Vt constant
	 public static final double Pi = 3.1416;
	 public static final double bSize = 0.02; 		// pp ball radius
	 public static final double bMass = 0.0027; 	// pp ball mass
	 public static final double TICK = 0.01; 		// Clock tick duration (sec)
	 public static final double ETHR = 0.001; 		// Minimum ball energy
	 public static final double Xmin = 0.0; 					// Minimum value of X (pp table)
	 public static final double Xmax = ppTableXlen; 			// Maximum value of X
	 public static final double Ymin = 0.0; 					// Minimum value of Y
	 public static final double Ymax = ppTableHgt; 				// Maximum value of Y
	 public static final int xmin = 0; 							// Minimum value of x
	 public static final int xmax = WIDTH; 						// Maximum value of x
	 public static final int ymin = 0; 							// Minimum value of y
	 public static final int ymax = HEIGHT; 					// Maximum value of y
	 public static final double Xs = (xmax-xmin)/(Xmax-Xmin); 	// Scale factor X
	 public static final double Ys = (ymax-ymin)/(Ymax-Ymin); 	// Scale factor Y
	 public static final double Xinit = XwallL; 				// Initial ball location (X)
	 public static final double Yinit = Ymax/2; 				// Initial ball location (Y)
	 public static final double PD = 1; 						// Trace point diameter
	 public static final double TSCALE = 2000; 					// Scaling parameter for pause()
	 public static final double voxMAX = 8; 					// Maximum velocity in the simulation

	// 4. Paddle Parameters

	 static final double ppPaddleH = 8*2.54/100; 				// Paddle height
	 static final double ppPaddleW = 0.5*2.54/100; 				// Paddle width
	 static final double ppPaddleXinit = XwallR-ppPaddleW/2; 	// Initial RPaddle X
	 static final double ppPaddleYinit = Yinit; 				// Initial RPaddle Y
	 static final double ppPaddleXgain = 2.00; 					// Vx gain on Rpaddle hit
	 static final double ppPaddleYgain = 1.25; 					// Vy gain on Rpaddle hit
	 static final double LPaddleXinit = XwallL - ppPaddleW/2;	// Initial LPaddle X
	 static final double LPaddleYinit = Yinit;					// Initial RPaddle Y
	 static final double LPaddleXgain = 2.00; 					// Vx gain on Lpaddle hit
	 static final double LPaddleYgain = 1.25; 					// Vy gain on Lpaddle hit


	// 5. Parameters used by the ppSim class

	 static final double YinitMAX = 0.75*Ymax; 		// Max inital height at 75% of range
	 static final double YinitMIN = 0.25*Ymax; 		// Min inital height at 25% of range
	 static final double EMIN = 0.1; 				// Minimum loss coefficient
	 static final double EMAX = 0.15;			 	// Maximum loss coefficient
	 static final double VoMIN = 5.0; 				// Minimum velocity
	 static final double VoMAX = 5.0; 				// Maximum velocity
	 static final double ThetaMIN = 0.0; 			// Minimum launch angle
	 static final double ThetaMAX = 20.0; 			// Maximum launch angle
	 static final long RSEED = 8976232; 			// Random number gen. seed value
	 
	// 6. Miscellaneous

	 public static final boolean DEBUG = false; 	// Debug msg. and single step if true
	 public static final boolean MESG = true; 		// Enable status messages on console
	 public static final boolean TEST = false; 		// To enable the testing of the code
	 public static final int STARTDELAY = 1000; 	// Delay between setup and start
	 
	
	// 7. Interface
	public static JToggleButton traceButton;					// Create the Toggle Button to show or hide the trace of the ball
	public static JLabel agent = new JLabel("Agent");			// Create the Agent label in the scoreboard 
	public static JLabel agentScore = new JLabel("0");			// Create the Agent's score label in the scoreboard 
	public static JLabel human = new JLabel("Human");			// Create the Human label in the scoreboard 
	public static JLabel humanScore = new JLabel("0");			// Create the Human's score label for the scoreboard 
	public static int scoreA = 0;								// Variable to track the score of the agent
	public static int scoreH = 0;								// Variable to track the score of the human
	public static JSlider timeSlider = new JSlider(1,2,1); 		// Slider to slow down the play 
	public static JSlider lagSlider = new JSlider(0,30,0);		// Slider to reduce the reaction time of the agent
	
	
}


