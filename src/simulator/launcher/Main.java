package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

import javax.swing.*;

public class Main {
 	//a
	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer ticks= _timeLimitDefaultValue;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _mode = null;
	private static Factory<Event> _eventsFactory = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);

			parseOutFileOption(line);

			parseTicksOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Number of ticks").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Mode of execution").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		if(_mode.equals("console")){
			_inFile = line.getOptionValue("i");
			if (_inFile == null) {
				throw new ParseException("An events file is missing");
			}
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if(_mode.equals("console")){
			_outFile = line.getOptionValue("o");
		}
	}

	private static void parseTicksOption(CommandLine line) throws ParseException {
		if(_mode.equals("console")){
			ticks = Integer.valueOf(line.getOptionValue("t"));
		}
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		if(line.getOptionValue("m") == null){
			_mode = "gui";
		}
		else{
			_mode = line.getOptionValue("m");
		}
	}

	private static void initFactories() {

		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder() );
		lsbs.add( new MostCrowdedStrategyBuilder() );

		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder() );
		dqbs.add( new MoveAllStrategyBuilder() );

		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add( new NewVehicleEventBuilder());
		ebs.add( new SetContClassEventBuilder());
		ebs.add( new SetWeatherEventBuilder());
		_eventsFactory = new BuilderBasedFactory<Event>(ebs);

	}

	private static void startBatchMode() throws IOException {
		Controller controlador = new Controller(new TrafficSimulator(),_eventsFactory);
		controlador.loadEvents(new FileInputStream(_inFile));

		if(_outFile != null){
			controlador.GUIrun(ticks,new FileOutputStream(_outFile));
		}
		else{
			controlador.GUIrun(ticks,System.out);
		}
	}
	private static void startGUIMode() throws IOException {
		Controller controlador = new Controller(new TrafficSimulator(),_eventsFactory);
		if(_inFile != null){
			controlador.loadEvents(new FileInputStream(_inFile));

		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override

			public void run() {
				MainWindow aux = new MainWindow(controlador);
				aux.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			}
		});


	}
	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if(_mode.equals("gui")){
			startGUIMode();
		}
		else{
			startBatchMode();
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
