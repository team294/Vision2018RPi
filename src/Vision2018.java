
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team294.vision.GripPipeline;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable; 
import edu.wpi.first.networktables.NetworkTableEntry; 

public class Vision2018 {

	public static final int IMG_WIDTH = 640;
	public static final int IMG_HEIGHT = 480;
	
	public static GripPipeline pipeline = new GripPipeline();
	public static double centerX, centerY;

	public static void main(String[] args) {
    	Rect r, rBiggest = new Rect();		// Rectangles for image filtering
    	int maxWidth;			// Size of biggest rectangle
		
		System.out.println("test");
		
		// Creates a NetworkTable for Raspberry Pi information 		
		NetworkTableEntry xCoord; 
		NetworkTableEntry yCoord; 	
		
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		inst.startClientTeam(294);
		NetworkTable pi = inst.getTable("Pi"); 
		
		xCoord = pi.getEntry("X"); 
		yCoord = pi.getEntry("Y"); 
		
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setVideoMode(VideoMode.PixelFormat.kYUYV, IMG_WIDTH, IMG_HEIGHT, 30);
	    camera.setExposureManual(23);
	    camera.setWhiteBalanceManual(2800);
	    camera.setBrightness(23);
	    
	    CvSink m_cvSink = new CvSink("Test CvSink");
	    m_cvSink.setSource(camera);
	    
	    Mat m_image = new Mat();
	    
	    // Run pipeline 10 times
//	    for (int i = 0; i<10; i++) {	    	

	    while (true) {
	    	long frameTime = m_cvSink.grabFrame(m_image, 10.0);
		    if (frameTime == 0) {
			    // There was an error, report it
			    String error = m_cvSink.getError();
			    System.out.println("Error: " + error);
		    } else {
		    	// No errors, process the image
		    	System.out.println("Success!");

		    	// Run the GRIP pipeline
		    	pipeline.process(m_image);
		    	
		    	// Find the output ConvexHull with the biggest width.  That will
		    	// probably be the closest box, which is probably the one we want.
		    	maxWidth = -1;
	    		for (MatOfPoint mp : pipeline.convexHullsOutput()) {
	    			r = Imgproc.boundingRect(mp);
	    			if (r.width > maxWidth) {
	    				rBiggest = r;
	    				maxWidth = r.width;
	    			}		
	    		}

		    	// Get the center X and Y coords of the biggest contour found
		    	if (maxWidth > -1) {
	                centerX = rBiggest.x + (rBiggest.width / 2);
	                centerY = rBiggest.y + (rBiggest.height / 2);

			    	xCoord.setDouble(centerX); 
			    	yCoord.setDouble(centerY);
	                
	    		    System.out.println("Center of biggest countour:  X = " + centerX + ", Y = " + centerY);
		        } else {
		        	System.out.println("No countours found.");
			    	xCoord.setDouble(-1); 
			    	yCoord.setDouble(-1);
		        }

		    }
		    
	    }

	}

}
