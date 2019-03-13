package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {
  private int numberOfLine = 1;
  private boolean backslashRDetected = false;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    for(int i = off; i < off + len; ++i){
      this.write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i){
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    char macSeparator9AndBelow = '\r';
    char macSepartor10AndAboveAndLinux = '\n';


    if(numberOfLine == 1) { //Si on est à la première ligne, on insére le numéro
      super.write(String.valueOf(numberOfLine++) + '\t');
      super.write(c);
    }else if(macSepartor10AndAboveAndLinux == (char)c){ //forcement une fin de ligne avec \n
      backslashRDetected = false;
      super.write(c);
      super.write(String.valueOf(numberOfLine++) + '\t');
    }else if(macSeparator9AndBelow == (char)c){ //si c'est un \r on fixe la variable à true et on ecrit le caractère.
      backslashRDetected = true;
      super.write(c);
    }else if(backslashRDetected){ //si le \r a été trouvé avant, cela veut dire que c'était un retour à la ligne. Donc on mets le numéro avant.
      backslashRDetected = false;
      super.write(String.valueOf(numberOfLine++) + '\t');
      super.write(c);
    }else{ //autrement on écrit juste le caractère.
      super.write(c);
    }
  }

}
