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

      if(rootDirectory == null){
          return;
      }

      vistor.visit(rootDirectory);

      //On crée deux tableaux. Un avec les fichiers et l'autre avec les répertoires. La méthode de sélection
      // a été donnée par Olivier Liechti.
      File[] listFiles = rootDirectory.listFiles(File::isFile);
      File[] listDirectories = rootDirectory.listFiles(File::isDirectory);


      if(listDirectories != null ) {
          Arrays.sort(listDirectories);
          for (File currentDirectory : listDirectories) {
              explore(currentDirectory,vistor);
          }
      }
      if(listFiles != null ) {
          Arrays.sort(listFiles);
          for (File currentFile : listFiles) {
              vistor.visit(currentFile);
          }
      }
  }

}
