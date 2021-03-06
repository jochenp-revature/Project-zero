package com.revature;

import org.apache.log4j.Logger;

/**
 * This is just a demo class
 * @author SophieGavrila
 *
 */
public class LoggingDriver {

	// STep 1. Make a Logger for the class
	private static Logger log = Logger.getLogger(LoggingDriver.class);
	
	
	/*
	 * Logging is a method of reporting information during the execution of a program.
	 * We typically use log files to inform devs and System Admins about what's happening within the 
	 * program at runtime
	 * 
	 * We have several "Logging Levels"
	 * 
	 * OFF: No logging at all
	 * ALL: Turns on all levels of logging
	 * TRACE: Adds more information to our DEBUG logs
	 * DEBUG: Primarily a logging level that is helpful for Developers (turn on all of the following levels)
	 * INFO: Represent important business processes, used for defining when they have completed
	 * 		and that the current application state is "as expected"
	 * 		Generally speaking, System Administrators as well as similar roles (such as SREs) will monitor INFO logs to track
	 * 		the Application Status
	 * 
	 * WARN: Suggest that the application might be continued, but you should take extra caution
	 * ERROR: Shouting that something has gone very wrong and should be investigated immediately
	 * FATAL: Very rare, but signals that something is terribly wrong, and will likely result in application failure
	 */
	
	
	// Where is our logger appending these log statements?
	public static void main(String[] args) {
		
		log.info("Application has started!");
		
		try {
			recurse();
		} catch (Error err) {
			log.error("Something has gone horribly wrong");
		}
		
		log.info("Application has successfully completed!");
		
	}
	
	
	public static void recurse() {
		
		recurse();
		
	}

}
