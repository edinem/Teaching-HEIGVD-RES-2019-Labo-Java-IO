package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {

      if(rootDirectory != null){
          vistor.visit(rootDirectory);

          File[] listFiles = rootDirectory.listFiles();
          ArrayList<File> listDirectory = new ArrayList<>();

          if(listFiles != null) {
              Arrays.sort(listFiles);
              for (File currentFile : listFiles) {
                  if(currentFile.isFile()){
                      vistor.visit(currentFile);
                  }else if(currentFile.isDirectory()){
                      listDirectory.add(currentFile);
                  }
              }

              for(File currentDir : listDirectory){
                  explore(currentDir,vistor);
              }
          }
      }else{
          return;
      }

   // throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
