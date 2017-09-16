/*
 * Configuration.h
 *
 *  Created on: Sep 1, 2017
 *      Author: ubuntu
 */

#ifndef CONFIGURATION_H_
#define CONFIGURATION_H_



typedef struct config_t {
	// Booleans but int
	int controlMode;
	int safety;
	int firingMode;
	int scanWhenIdle;
	int trackingMotion;
	int trackingColor;
	int leadTarget;
	int safeColor;
	int showRestrictedZones;
	int showDifferentPixels;
	int showTargetBox;
	int showCameraView;
	int mirrorCam;
	int soundEffects;

	// Integers
	int camWidth;
	int camHeight;
	int nbDot;
	int antSens;
	int minBlobArea;
	int tolerance;
	int effect;
	int trackColorTolerance;
	int trackColorRed;
	int trackColorGreen;
	int trackColorBlue;
	int safeColorMinSize;
	int safeColorTolerance;
	int safeColorRed;
	int safeColorGreen;
	int safeColorBlue;
	int idleTime;

	// Floats
	double propX;
	double propY;
	double xRatio;
	double yRatio;
	double xMin;
	double xMax;
	double yMin;
	double yMax;

} configuration;
configuration configuration1;

#endif /* CONFIGURATION_H_ */
