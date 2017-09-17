# psg-code


## Synopsis

A port of the PSG Code to run on Raspberry Pi 3, because ,No Elon Musk, we need more autonomous killer robots!!!! (see project references below)

## Description

This code is intended for the NERF Sentry Gun, ported to run on the Raspberry Pi 3 and leveraging the RPi 3 camera.
I've changed the code somewhat and updated the device setup GUI to create and change the configuration settings.
I've tried to preserve sounds in the minimal implementation because killer robots should be able to speak. :stuck_out_tongue_winking_eye:     
This code is a standalone invocation of the Processing based code
I've also modified the configuration settings to use JSON. I leveraged the (GSON library) so as not to be dependent on the somewhat limited JSON implementation in Processing

## Installation

### Hardware
1.	Raspberry Pi 3 
1.	Raspberry Pi Camera ( I tested with v2)
1.	Arduino (I used Sparkfun's RedBoard, an Uno clone)
1.	Controller (At a guess you will use some kind of wireless (Bluetooth?) controller. A killer robot attached by a tether looks kind of sad :disappointed:)
  
### Prerequisites
1. Install [Ubuntu MATE](https://ubuntu-mate.org/raspberry-pi/ "https://ubuntu-mate.org/raspberry-pi/") or use the original Pi 3 Raspbian image
1. Install these extra packages

   ````sh
	sudo apt install libjinput-java libjinput-java-doc libjutils-java alsa-utils bluez bluez-tools \
   		gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good gstreamer1.0-libav \
   		gstreamer1.0-libav-dbg libjogl2-java libjogl2-jni
   ````

1. Using `raspi-config` enable the camera. Add the `bcm2835_v4l2` to `/etc/modules`
   ````sh
	echo bcm2835_v4l2 | sudo tee -a /etc/modules
   ````
1. Install Oracle java, using the Oracle java PPA
	````sh
	sudo add-apt-repository ppa:webupd8team/java
	sudo apt-get update
	#Next line is important!!!
	sudo echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
	sudo apt-get install oracle-java8-installer -y
	````
   **Note:** 
   >Order of installs is important java then maven
	

1.	Install ``maven`` from [here](https://maven.apache.org/ "https://maven.apache.org/"). Use the instructions found [here](https://thelinuxfaq.com/488-how-to-install-maven-latest-version-on-ubuntu "https://thelinuxfaq.com/488-how-to-install-maven-latest-version-on-ubuntu"),
substituting the current maven version (like `3.5.0`) for `3.3.9`. Just in case the website is not available or (TL;DR), here is the Readers Digest version
	````sh
	cd ~/Downloads
	sudo wget http://apache.mirrors.pair.com/maven/maven-3/3.5.0/binaries/apache-maven-3.5.0-bin.tar.gz
	sudo tar -zxvf apache-maven-3.5.0-bin.tar.gz
	sudo mv apache-maven-3.5.0 /usr/local/
	sudo ln -s /usr/local/apache-maven-3.5.0 /usr/local/apache-maven
	echo export M2_HOME=/usr/local/apache-maven | sudo tee /etc/profile.d/maven.sh
	echo export M3_HOME=\${M2_HOME} | sudo tee -a /etc/profile.d/maven.sh
	echo export PATH=\${M3_HOME}/bin:\${PATH} | sudo tee -a /etc/profile.d/maven.sh
	source ~/.profile && source /etc/profile && mvn -v
	````
     The output will look something like :
	````sh
	Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T15:39:06-04:00)
	Maven home: /usr/local/maven
	Java version: 1.8.0_144, vendor: Oracle Corporation
	Java home: /usr/lib/jvm/java-8-oracle/jre
	Default locale: en_US, platform encoding: UTF-8
	OS name: "linux", version: "4.9.47-v7+", arch: "arm", family: "unix"
	````

1.	Install Arduino IDE from [Arduino site](https://www.arduino.cc/en/Main/Software "https://www.arduino.cc/en/Main/Software")
	````sh
	cd ~/Downloads
	#Replace "X.X.X" with the actual Arduino software version like "1.8.4"
	tar -xvf arduino-X.X.X-linuxarm.tar.xz
	cd arduino-X.X.X
	./install.sh
	````

### Get the Code

1.	Checkout and build the code
	````sh
	[ ! -d ~/projects ] && mkdir ~/projects
	cd ~/projects
	git clone http://nrapopords.myds.me:30000/Makerbar/psg-code.git 
	cd psg-code/src/main/resources/scripts
	./installMissing.sh
	cd ~/projects/psg-code
	mvn clean install
	````
	
## Configuration
The following configuration must be done prior to running the code for the first time ...

### Arduino
1.	Plug-in the Arduino board into the USB port. Make sure that Arduino is powered by an external power supply.
1.	Run the Arduino IDE
1.	Connect to the serial port that Arduino is listening on
1.	Open the sketch `~/projects/psg-code/src/main/resources/arduino/PSG_Arduino_Code.ino` 
1.	Verify that the `boardType` is set to the correct value for your setup:
	````c++
	enum BoardTypes {
		ARDUINO_BARE, SHIELD_V4, SHIELD_V6, SHIELD_V7, STANDALONE_V3, STANDALONE_V5, STANDALONE_V7, STANDALONE_V8
	};
	
	BoardTypes boardType = ARDUINO_BARE;
	````
	At the bottom of the sketch are the pin assignments that depend on the `boardType` value :
	````c++
	void assignPins() {

		switch (boardType) {
		case ARDUINO_BARE:
			// pin attachments:
			panServoPin = 8;                        // Arduino pin for pan servo
			tiltServoPin = 9;                       // Arduino pin for tilt servo
			triggerServoPin = 10;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			firingIndicatorLEDPin = 12;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 11;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 3;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			electricTriggerPin = 7;                 // Arduino pin for output to trigger MOSFET
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		case SHIELD_V4:
		case SHIELD_V6:
			// pin attachments:
			panServoPin = 9;                        // Arduino pin for pan servo
			tiltServoPin = 8;                       // Arduino pin for tilt servo
			triggerServoPin = 7;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			electricTriggerPin = 6;                 // Arduino pin for output to trigger MOSFET
			firingIndicatorLEDPin = 11;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 12;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 10;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		case SHIELD_V7:
			// pin attachments:
			panServoPin = 8;                        // Arduino pin for pan servo
			tiltServoPin = 9;                       // Arduino pin for tilt servo
			triggerServoPin = 10;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			electricTriggerPin = 7;                 // Arduino pin for output to trigger MOSFET
			firingIndicatorLEDPin = 12;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 6;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 11;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		case STANDALONE_V3:
			// pin attachments:
			panServoPin = 8;                        // Arduino pin for pan servo
			tiltServoPin = 9;                       // Arduino pin for tilt servo
			triggerServoPin = 10;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			electricTriggerPin = 7;                 // Arduino pin for output to trigger MOSFET
			firingIndicatorLEDPin = 12;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 14;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 11;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		case STANDALONE_V5:
			// pin attachments:
			panServoPin = 8;                        // Arduino pin for pan servo
			tiltServoPin = 9;                       // Arduino pin for tilt servo
			triggerServoPin = 10;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			electricTriggerPin = 7;                 // Arduino pin for output to trigger MOSFET
			firingIndicatorLEDPin = 12;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 14;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 11;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		case STANDALONE_V7:
			// pin attachments:
			panServoPin = 8;                        // Arduino pin for pan servo
			tiltServoPin = 9;                       // Arduino pin for tilt servo
			triggerServoPin = 10;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			electricTriggerPin = 7;                 // Arduino pin for output to trigger MOSFET
			firingIndicatorLEDPin = 12;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 14;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 11;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		case STANDALONE_V8:
			// pin attachments:
			panServoPin = 8;                        // Arduino pin for pan servo
			tiltServoPin = 9;                       // Arduino pin for tilt servo
			triggerServoPin = 10;                    // Arduino pin for trigger servo, or output to trigger MOSFET
			electricTriggerPin = 7;                 // Arduino pin for output to trigger MOSFET
			firingIndicatorLEDPin = 12;              // Arduino pin for firing indicator LED
			USBIndicatorLEDPin = 14;                 // Arduino pin for USB indicator LED
			modeIndicatorLEDPin = 13;                // Arduino pin for Mode indicator LED
			reloadSwitchPin = 11;                    // Arduino pin for input from RELOAD switch
			disablePlatePin = 2;                    // Arduino pin for input from disable plate
			invertInputs = true;                   // TRUE turns on internal pull-ups, use if closed switch connects arduino pin to ground
			break;
		}
	}
	```` 
1.  Choose the correct `enum` value for the `boardType` and deploy it to the connected board

### Controller
1. Connect your game controller to the RPi PORT port and run the `InputDeviceSetupTool` to create the device configuration `.cfg` file. 
Select your controller from the device list and map the buttons and the sliders on your controller to actions.
   ````sh
   cd ~/psg-code/src/main/resources/scripts
   # TODO add the command to launch the InputDeviceSetupTool
   ```` 
   **Note:**
   >This will have the side effect of creating the `data` directory for the project, creating the default configuration file,
   >and initializing the `deviceConfig` property to the device config file name  

### Default Settings
1.  Run the following command to create the default configuration file. (this is only needed if the Controller step was skipped) 
	````sh
	cd ~/psg-code/src/main/resources/scripts
	# TODO: add the command to launch the Settings command to initialize settings
	````   
   **Note:** 
   >Read the instructions on the [PSG Site](http://psg.rudolphlabs.com/make-your-own/using-the-software "http://psg.rudolphlabs.com/make-your-own/using-the-software") 
   >on the various features of the software. They are still valid just the configuration settings are now in JSON so hopefully a little easier to read. I've
   >preserved the original (well mostly original :smiley: ) PDE files with the project in the `src/main/resource/pde` folder for reference. 	

## How to use
1.  Run the following command to launch. 
	````sh
	cd ~/psg-code/src/main/resources/scripts
	# TODO: add the command to launch the main program
	````   


### Detail Notes
1. This project is intended to run on RPi 3 leveraging the `armv7l` os build, meaning 32-bit ARM and tested under ARM port of Ubuntu MATE for Raspberry Pi 3.
1. 
1. TODO add additional notes here 


## Notes on this Project
This project was created in middle of 2017 and all the references to maven and Java versions reflect that fact and will
probably get old very quickly

## Author and Legal information

### Author

[Nick Rapoport](mailto:nrapopor@hotmail.com?subject=PSG%20Code "mailto:nrapopor@hotmail.com?subject=PSG%20Code")

### Copyright

Copyright&copy;  2017 Nick Rapoport -- All rights reserved (free
for use under the restrictions of GPLv2) 

### Warranty

Warranty on a killer robot brain? Nope, none at all!!! It may, or may not, shoot ... Maybe ... 

### License

[GPLv2](https://www.gnu.org/licenses/gpl-2.0.html "https://www.gnu.org/licenses/gpl-2.0.html")

## Based On

#### Projects
- [Ubuntu MATE](https://ubuntu-mate.org/raspberry-pi/ "https://ubuntu-mate.org/raspberry-pi/")
- [Processing](https://processing.org/ "https://processing.org/")
- [Project Sentry Gun](http://psg.rudolphlabs.com/make-your-own/setup-the-psg-code "http://psg.rudolphlabs.com/make-your-own/setup-the-psg-code")
- [Minim](http://code.compartmental.net/2007/03/27/minim-an-audio-library-for-processing/ "http://code.compartmental.net/2007/03/27/minim-an-audio-library-for-processing/")
- [G4P](http://www.lagers.org.uk/g4p/index.html "http://www.lagers.org.uk/g4p/index.html")
- [GLVideo](https://github.com/gohai/processing-glvideo "https://github.com/gohai/processing-glvideo")
- [BLOB Detection](http://www.v3ga.net/processing/BlobDetection/ "http://www.v3ga.net/processing/BlobDetection/")
- [GSON](https://github.com/google/gson "https://github.com/google/gson")

#### Date
2017-08-01

