# Vision2018RPi
2018 Vision code compiled to run on Raspberry Pi

How to compile and deploy to Rasperberry Pi:
1. Update code and prepare to compile.
2. In Eclipse (works on Laptop 9), File -> Export -> Java : Runnable JAR File -> 
   Launch Config = "Vision2018RPi", Dest = "visiontest.jar", "Extract required libraries into generated JAR".
   Note:  If the config for Vision2018RPi is not on the drop list, then compile Vision2018RPi as a "Java Application",
   similar to how we compile robot code.  It will error out, but it will add Vision2018RPi to the Launch Config list.
3. Log into the Pi	 (user = "pi", password = "raspberry") and change the file system to read-write mode ("rw" command).
4. Use FileZilla SFTP client to copy visiontest.jar from the PC to the RPi in /home/pi/Vision/  .
   If needed, get the RPi's IP address using the "ifconfig" command on the Pi (goes in the Host box in FileZilla).
   Port = 22 (to use SFTP protocol).
5. Set Pi file system back to read-only ("ro" command).
6. On the Pi, go to the Vision directory ("cd Vision") and type "./runInteractive" to run the vision pipeline in interactive mode.
   Otherwise, use "./runCameraVision" or reboot the Pi to run in batch mode.
7. While the code is running, you can view the vision stream and change camera settings by launching Chrome or Firefox (I think)
   and go to the RPi address (from ifconfig) port 1181.  For example, "http://169.254.49.232:1181".
8. You can use the camera video feed in Grip by selecting "Add source" -> "IP Camera" -> "http://169.254.49.232:1181/stream.mjpg"
   (using the right IP address from ifconfig).
   
Here is a list of properties on a MS LifeCam that can be controlled via camera.getProperty():
Property = raw_brightness, Value = 81, min = 30, max = 255
Property = brightness, Value = 23, min = 0, max = 100
Property = raw_contrast, Value = 9, min = 0, max = 10
Property = contrast, Value = 90, min = 0, max = 100
Property = raw_saturation, Value = 132, min = 0, max = 200
Property = saturation, Value = 66, min = 0, max = 100
Property = white_balance_temperature_auto, Value = 0, min = 0, max = 1 (0 = manual, 1 = auto)
Property = power_line_frequency, Value = 2, min = 0, max = 2   (0 = disabled, 1 = 50 Hz, 2 = 60 Hz)
Property = white_balance_temperature, Value = 2800, min = 2800, max = 10000
Property = raw_sharpness, Value = 50, min = 0, max = 50
Property = sharpness, Value = 100, min = 0, max = 100
Property = backlight_compensation, Value = 0, min = 0, max = 10
Property = exposure_auto, Value = 1, min = 0, max = 3 (1 = manual mode, 3 = aperture priority mode)
Property = raw_exposure_absolute, Value = 20, min = 5, max = 20000
Property = exposure_absolute, Value = 23, min = 0, max = 100
Property = pan_absolute, Value = 0, min = -201600, max = 201600
Property = tilt_absolute, Value = 0, min = -201600, max = 201600
Property = zoom_absolute, Value = 0, min = 0, max = 10

