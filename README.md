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
   
   

