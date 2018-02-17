# Vision2018RPi
2018 Vision code compiled to run on Raspberry Pi

How to compile and deploy to Rasperberry Pi:
1. Update code and prepare to compile.
2. In Eclipse (works on Laptop 9), File -> Export -> Java : Runnable JAR File -> 
   Dest = "visiontest.jar", "Extract required libraries into generated JAR"
3. Log into the Pi (user = "pi", password = "raspberry") and change the file system to read-write mode ("rw" command).
4. Use FileZilla SFTP client to copy visiontest.jar from the PC to the RPi in /home/pi/Vision/  .
   If needed, get the RPi's IP address using the "ifconfig" command on the Pi (goes in the Host box in FileZilla).
   Port = 22 (to use SFTP protocol).
5. Set Pi file system back to read-only ("ro" command).
6. Run "~/Vision/runInteractive" to run the vision pipeline in interactive mode.  Otherwise, use "runCameraVision" or reboot the Pi
   to run in batch mode.
   
   

