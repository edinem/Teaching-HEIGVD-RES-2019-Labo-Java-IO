package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {

    String[] tmp;

    String macSeparator9AndBelow = "\r";
    String macSepartor10AndAboveAndLinux = "\n";
    String windowSeparator = macSeparator9AndBelow + macSepartor10AndAboveAndLinux; //\r\n

    //in case of Windows OS. We start with this one because it's the more complete "\r\n";
    if(lines.contains(windowSeparator)){
        tmp = split(windowSeparator,lines);
    }

    //in case of mac 9 and below OS
    else if(lines.contains(macSeparator9AndBelow)){
        tmp = split(macSeparator9AndBelow,lines);
    }

    //in case of mac 10 and above OS
    else if(lines.contains(macSepartor10AndAboveAndLinux)){
        tmp = split(macSepartor10AndAboveAndLinux,lines);
    }

    //otherwise
    else{
        tmp = new String[] {"",lines};
    }

    return tmp;
  }

  public static String[] split(String separator, String lines){
    String[] tmp = lines.split(separator,2);
    tmp[0] += separator;
    return tmp;
  }

}
