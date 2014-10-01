package at.fht.robotFactory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

/**
 * The main class of or controller the robotfactory,
 * which represents also the Watchdog for all threads
 * @author Christoph Hackenberger
 * @version 1.0
 */
public class Factory implements Runnable {
	
	private static Watchable[] threads;
	
	//This array of threads is just exists because of unit testing reasons
	private static Thread[] ts;
	
	private static Office office;
	private static Storageguy storage;
	
	private static Logger logger;
	
	private static String lagerDir;
	private static String logDir;
	private static int supAmount;
	private static int assAmount;
	private static int runtime;
	
	/**
	 * The Main method of the robotfactory
	 * @param args program options
	 */
	public static void main(String[] args) {
		parseArgs(args);
		setupLog4J();
		logger = LogManager.getLogger(Factory.class.getName());
		initFactory();
		startThreads();
		new Thread(new Factory()).start();
	}
	
	/**
	 * just a main method for unit testing
	 * @param arg program options
	 */
	public static void testMain(String arg) {
		String[] args = arg.split(" ");
		parseArgs(args);
		setupLog4J();
		logger = LogManager.getLogger(Factory.class.getName());
		initFactory();
	}
	
	/**
	 * just a main method for unit testing
	 * @param arg program options
	 */
	public static void testMainWithThreads(String arg) {
		String[] args = arg.split(" ");
		parseArgs(args);
		setupLog4J();
		logger = LogManager.getLogger(Factory.class.getName());
		initFactory();
		startThreads();
	}
	
	/**
	 * creates the Office and the Storageguy of the robotfactory
	 */
	private static void initFactory() {
		logger.info("Init Office and Storageguy");
		office = new Office();
		try {
			storage = new Storageguy(lagerDir);
		} catch (IOException ex) {
			logger.error("The given storage directory is invalid: " + ex.getMessage());
			logger.info("Stopping program");
			System.exit(1);
		}
	}
	
	/**
	 * basic setup for Log4J
	 * this method is a little bit tricky because it sets a system property to the logfile dir
	 * but Log4j has at this time already created a file ${sys:kfhuCTPdas} as defined in the xml
	 * so we have to delete this
	 */
	private static void setupLog4J() {
		System.setProperty("kfhuCTPdas", logDir);
		File dir = new File("${sys:kfhuCTPdas}");
		dir.delete();
	}
	
	/**
	 * Parses the options from the command line
	 * @param args the options from the command line
	 */
	@SuppressWarnings("static-access")
	private static void parseArgs(String[] args) {
		Options options = new Options();
		
		Option lager = OptionBuilder.withArgName("lager_dir").hasArg().
				withDescription("the storage directory").withLongOpt("lager").create('l');
		
		Option log = OptionBuilder.withArgName("log_file").hasArg().
				withDescription("the location and the name of the log file").withLongOpt("log").create('o');
		
		Option lieferanten = OptionBuilder.withArgName("amount").hasArg().
				withDescription("the amount of suppliers which should be created").withLongOpt("lieferanten").create('s');
		
		Option monteure = OptionBuilder.withArgName("amount").hasArg().
				withDescription("the amount of assemblers which should be created").withLongOpt("monteure").create('a');
		
		Option laufzeit = OptionBuilder.withArgName("time").hasArg().
				withDescription("the time in ms which the program should run").withLongOpt("laufzeit").create('t');
		
		options.addOption(lager);
		options.addOption(log);
		options.addOption(lieferanten);
		options.addOption(monteure);
		options.addOption(laufzeit);
		
		CommandLineParser cmd = new BasicParser();
		try {
			CommandLine line = cmd.parse(options, args, true);
			if(!line.hasOption('l'))
				throw new ParseException("Option -" + lager.getOpt() + " is required!");
			else if(!line.hasOption('o'))
				throw new ParseException("Option -" + log.getOpt() + " is required!");
			else if(!line.hasOption('s'))
				throw new ParseException("Option -" + lieferanten.getOpt() + " is required!");
			else if(!line.hasOption('a'))
				throw new ParseException("Option -" + monteure.getOpt() + " is required!");
			else if(!line.hasOption('t'))
				throw new ParseException("Option -" + laufzeit.getOpt() + " is required!");
			
			lagerDir = line.getOptionValue('l');
			logDir = line.getOptionValue('o');
			try {
				supAmount = Integer.parseInt(line.getOptionValue('s'));
			}catch (NumberFormatException ex) {
				throw new ParseException("argument for option -s --lieferanten must be an integer");
			}
			try {
				assAmount = Integer.parseInt(line.getOptionValue('a'));
			}catch (NumberFormatException ex) {
				throw new ParseException("argument for option -a --monteure must be an integer");
			}
			try {
				runtime = Integer.parseInt(line.getOptionValue('t'));
			}catch (NumberFormatException ex) {
				throw new ParseException("argument for option -t --laufzeit must be an integer");
			}
			
		}catch (ParseException ex) {
			System.out.println(" Error: " + ex.getMessage());
			HelpFormatter format = new HelpFormatter();
			format.printHelp("robotfactory", options);
			System.exit(1);
		}
	}
	
	/**
	 * starts the Supplier and Assembler Threads
	 */
	private static void startThreads() {
		threads = new Watchable[supAmount+assAmount];
		ts = new Thread[supAmount+assAmount];
		logger.info("Starting " + supAmount + " Supplier Threads");
		for(int i = 0; i < supAmount; i++) {
			Supplier sup = new Supplier();
			threads[i] = sup;
			Thread t = new Thread(sup);
			t.start();
			ts[i] = t;
		}
		logger.info("Starting " + assAmount + " Assembler Threads");
		for(int i = supAmount; i < supAmount+assAmount; i++) {
			Assembler ass = new Assembler();
			threads[i] = ass;
			Thread t = new Thread(ass);
			t.start();
			ts[i] = t;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {	
		try {
			Thread.sleep(runtime);
			logger.info("Runtime expired stopping all threads");
		} catch (InterruptedException e) {
			logger.error("Watchdog interrupted stopping all threads NOW!");
		}
		for(Watchable w : threads)
			while(!w.shutdown());
		
	}
	
	/**
	 * Returns the Office of the robotfactory
	 * @return the Office of the robotfactory
	 */
	public static Office getOffice() {
		return office;
	}
	
	/**
	 * Returns the Storageguy of the robotfactory
	 * @return the Storageguy of the robotfactory
	 */
	public static Storageguy getStorage() {
		return storage;
	}
	
	/**
	 * Returns the started Threads from Factory
	 * This method just exists because of unit testing reasons
	 * @return array of Threads
	 */
	public static Thread[] getThreads() {
		return ts;
	}
}
