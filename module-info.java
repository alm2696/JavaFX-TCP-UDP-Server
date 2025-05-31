/**
 * This module specifies the dependencies required for JavaFX and the packages  
 * that need to be accessible to JavaFX's internal modules. The `mod03` package
 * is opened to the JavaFX graphics module for reflective access.
 * 
 */
module edu.commonwealthu.alm2696.CMSC230 {
	
	// JavaFX modules required for GUI
	requires javafx.graphics;
	requires javafx.controls;
	
	// Opens the mod03.inClass package for reflection access to javafx.graphics
	opens mod03 to javafx.graphics;
	
}
