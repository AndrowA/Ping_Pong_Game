
/**
 * A class that generates the floor 
 * Includes methods to change coordinate types and create a new screen
 * Code inspired from code taken from the assignment handout given by Prof. Frank Ferrie and the tutorial session given by Katrina Poulin
 */

package ppPackage;

import java.awt.Color;
import static ppPackage.ppSimParams.*;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class ppTable {
	
	GraphicsProgram GProgram;  	// Graphics program instance 
	
	/**
	 * A constructor that sets up the table including the ground
	 */
	
	public ppTable(GraphicsProgram GProgram) { 
		
		this.GProgram = GProgram;	// Graphics program instance variable
		
		// Create the ground plane
		drawGroundLine();
	
	}
	
	/**
     * Method to convert from world to screen coordinates.
     * @param P a point object in world coordinates
     * @return p the corresponding point object in screen coordinates
     */
    
    public GPoint W2S (GPoint P) {
    	return new GPoint((P.getX()-Xmin)*Xs,ymax-(P.getY()-Ymin)*Ys);
    }
	
    /**
     * Method to convert from screen to world coordinates.
     * @param P a point object in screen coordinates
     * @return p the corresponding point object in world coordinates
     */
    
    public GPoint S2W(GPoint P) {
    	return new GPoint(P.getX()/Xs + Xmin, ((ymax - P.getY())/Ys + Ymin));
    }
    
    /**
     * Method to erase all objects on the display (except the buttons and draws a new ground plane)
     */
    
    public void newScreen() {
    	GProgram.removeAll();
    	drawGroundLine();
    }
    
    /**
     * Method to draw the ground line of the ping pong table
     */
    
    public void drawGroundLine(){
    	GRect gPlane = new GRect(0,HEIGHT,WIDTH+OFFSET,3);	// A thick line HEIGHT pixels down from the top
    	gPlane.setColor(Color.BLACK);
    	gPlane.setFilled(true);
    	GProgram.add(gPlane);
    }
}
