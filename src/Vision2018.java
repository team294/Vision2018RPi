
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team294.vision.GripPipeline;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable; 
import edu.wpi.first.networktables.NetworkTableEntry; 


public class Vision2018 {

	public static final int IMG_WIDTH = 640;
	public static final int IMG_HEIGHT = 480;
	public static final int IMG_FPS = 30;
	
	public static GripPipeline pipeline = new GripPipeline();
	public static double centerX, centerY;
	
	
	public static void main(String[] args) {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
		UsbCamera driveCamera = CameraServer.getInstance().startAutomaticCapture(1);
		
		cameraSetUp(camera);
		cameraSetUp(driveCamera);    
		
		imageProcess(camera);
	    }
	
	
	public static void cameraSetUp(UsbCamera camera) {
		
	    camera.setVideoMode(VideoMode.PixelFormat.kYUYV, IMG_WIDTH, IMG_HEIGHT, IMG_FPS);
	    camera.setExposureAuto();  // Start in auto exposure mode so that we can set brightness
	    camera.setBrightness(25);  // Setting brightness only works correctly in auto exposure mode (?)
	    camera.getProperty("contrast").set(80);
	    camera.getProperty("saturation").set(60);
	    camera.setExposureManual(24);
	    camera.setWhiteBalanceManual(2800);
	    
	    // List all properties from the camera
	    // With the right property, we can set contrast, etc.
	    for (VideoProperty vp : camera.enumerateProperties()) {
	    	if (vp.isString())
	    		System.out.println("Property = " + vp.getName() + ", string = " + vp.getString());
	    	else
	    		System.out.println("Property = " + vp.getName() + ", Value = " + vp.get() + ", min = " + vp.getMin() + ", max = " + vp.getMax());
	    }
	    
	    /* Here is a dump of the properties from a MS LifeCam:
	     * 
	     * Property = raw_brightness, Value = 81, min = 30, max = 255
	     * Property = brightness, Value = 23, min = 0, max = 100
	     * Property = raw_contrast, Value = 9, min = 0, max = 10
	     * Property = contrast, Value = 90, min = 0, max = 100
	     * Property = raw_saturation, Value = 132, min = 0, max = 200
	     * Property = saturation, Value = 66, min = 0, max = 100
	     * Property = white_balance_temperature_auto, Value = 0, min = 0, max = 1 (0 = manual, 1 = auto)
	     * Property = power_line_frequency, Value = 2, min = 0, max = 2  (0 = disabled, 1 = 50 Hz, 2 = 60 Hz)
	     * Property = white_balance_temperature, Value = 2800, min = 2800, max = 10000
	     * Property = raw_sharpness, Value = 50, min = 0, max = 50
	     * Property = sharpness, Value = 100, min = 0, max = 100
	     * Property = backlight_compensation, Value = 0, min = 0, max = 10
	     * Property = exposure_auto, Value = 1, min = 0, max = 3 (1 = manual mode, 3 = aperture priority mode)
	     * Property = raw_exposure_absolute, Value = 20, min = 5, max = 20000
	     * Property = exposure_absolute, Value = 23, min = 0, max = 100
	     * Property = pan_absolute, Value = 0, min = -201600, max = 201600
	     * Property = tilt_absolute, Value = 0, min = -201600, max = 201600
	     * Property = zoom_absolute, Value = 0, min = 0, max = 10
	     */
	    	
	}
	public static void imageProcess(UsbCamera camera) {
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