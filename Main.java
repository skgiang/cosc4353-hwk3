import org.apache.commons.cli.*;

public class Main implements Comparable {
    private static String type;
    private static boolean result;

    public static void main(String[] args){
        Main main = new Main();
        Options options = new Options();

        Option typeOption = Option.builder("t")
                .longOpt("type")
                .hasArg()
                .numberOfArgs(1)
                .required(true)
                .build();
        options.addOption(typeOption);

        Option keyOption = Option.builder("k")
                .longOpt("key")
                .hasArg()
                .numberOfArgs(1)
                .required(true)
                .build();
        options.addOption(keyOption);

        Option listOption = Option.builder("l")
                .longOpt("list")
                .hasArgs()
                .valueSeparator(' ')
                .required(true)
                .build();
        options.addOption(listOption);

        CommandLineParser commandLineParser = new DefaultParser();
        //HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine commandLine;

        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            //helpFormatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String type = commandLine.getOptionValue("type");
        String[] stringElements = commandLine.getOptionValues("list");
        if(type.equals("i")){ //expect integer key and list
            int intKey = Integer.parseInt(commandLine.getOptionValue("key"));
            Integer[] intElements = new Integer[stringElements.length];
            for(int i = 0; i < stringElements.length; i++)
                intElements[i] = Integer.parseInt(stringElements[i]);
            result = main.binSearch(intElements, intKey);
        }
        else if(type.equals("s")){ //expect String key and list
            String stringKey = commandLine.getOptionValue("key");
            result = main.binSearch(stringElements, stringKey);
        }
        else
        {
            System.out.println("Illegal type. Program Terminated.");
            System.exit(1);
        }

        if(result) //KEY FOUND, print 1
            System.out.println("1");
        else
            System.out.println("0");
    }

    public int compareTo(Object o) {
        return 0;
    }

    protected boolean binSearch(Comparable[] aList, Comparable key){
        int firstPos = 0;
        int lastPos = aList.length - 1;
        int midPos;

        while(firstPos <= lastPos)
        {
            //positioning the middle index
            midPos = (lastPos - firstPos) / 2 + firstPos;

            //non-recursive searching
            if(aList[midPos].compareTo(key) > 0) { //focus on LOWER half
                lastPos = midPos - 1;
            }
            else if(aList[midPos].compareTo(key) < 0){ //focus on HIGHER half
                firstPos = midPos + 1;
            }
            else { //aList[midPos].compareTo(key) = 0 //MATCH FOUND
                return true;
            }
        }
        return false; //NO MATCH FOUND //key does not exist in the array
    }
}
