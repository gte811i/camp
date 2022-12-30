/**
 * 
 */
package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * Utility class containing utilities for MintApp.
 * 
 * @author scarleton3
 */
public class CmdLogger implements Thread.UncaughtExceptionHandler{
	
	/** A final static logger instance. */
	public static Logger logger=LoggerFactory.getLogger(CmdLogger.class);
    /**
     * Configures the log4j. This code
     * checks to see if there are any appenders defined for log4j which is the
     * definitive way to tell if log4j is already initialized 
     */
	public CmdLogger(){
//		try {
//			 Load a logback config file programmatically
//			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//			JoranConfigurator configurator = new JoranConfigurator();
//			lc.reset();
//			configurator.setContext(lc);
//			configurator.doConfigure("./logback.xml"); // file must be in same location as running
//			StatusPrinter.printInCaseOfErrorsOrWarnings(lc); // Internal status data is printed in case of warning or errors
//		} catch (JoranException je) {
//			 StatusPrinter will handle this
//		} catch (Exception ex) {
//			logger.error("{}", ex);
//		}
	}

	public static void configure() {
		Thread.setDefaultUncaughtExceptionHandler(new CmdLogger());
    }

	/* (non-Javadoc)
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logger.error("Uncaught exception in thread: " + t.getName(), e);
	}
}
