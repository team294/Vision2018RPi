
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team294.vision.GripPipeline;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision2018 {
		
	public static GripPipeline pipeline = new GripPipeline();
	public static double centerX;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("test");
		
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		inst.startClientTeam(294);
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setVideoMode(VideoMode.PixelFormat.kYUYV, GripPipeline.IMG_WIDTH, GripPipeline.IMG_HEIGHT, 30);
	    camera.setExposureManual(23);
	    camera.setWhiteBalanceManual(2800);
	    camera.setBrightness(53);
	    
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
		
		    	pipeline.process(m_image);
//		    	if (!pipeline.filterContoursOutput().isEmpty()) {
//		            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//	                centerX = r.x + (r.width / 2);
//	    		    System.out.println("Center of 1st countour:  X = " + centerX);
//		        } else {
//		        	System.out.println("No countours found.");
//		        }
		    }
	    }
	    

	    
	    
//	    visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
//	    	if (!pipeline.filterContoursOutput().isEmpty()) {
//	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//	            synchronized (imgLock) {
//	                centerX = r.x + (r.width / 2);
//	            }
//	        }
//	    });
//	    visionThread.start();
//	    
//	    while (true) {
//	    	double centerX2;
//		    synchronized (imgLock) {
//		        centerX2 = centerX;
//		    }
//		    System.out.println(centerX2);
//	    }
		

	}

}
