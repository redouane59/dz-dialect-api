package io.github.Redouane59.dz.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class ResourceList {

  /**
   * for all elements of java.class.path get a Collection of resources Pattern pattern = Pattern.compile(".*"); gets all resources
   *
   * @param pattern the pattern to match
   * @return the resources in the order they are found
   */
  @SneakyThrows
  public static Collection<String> getResources(
      final Pattern pattern) {

    final ArrayList<String> retval = new ArrayList<>();
    Path                    dir    = Paths.get("src/main/resources");
    List<Path>              paths  = Files.walk(dir).collect(Collectors.toList());
    for (Path path : paths) {
      if (path.toFile().isDirectory()) {
        retval.addAll(getResourcesFromDirectory(path.toFile(), pattern));
      }
    }
    return retval;
  }

  private static Collection<String> getResourcesFromDirectory(
      final File directory,
      final Pattern pattern) {
    final ArrayList<String> retval   = new ArrayList<String>();
    final File[]            fileList = directory.listFiles();
    for (final File file : fileList) {
      if (file.isDirectory()) {
        retval.addAll(getResourcesFromDirectory(file, pattern));
      } else {
        try {
          final String  fileName = file.getCanonicalPath();
          final boolean accept   = pattern.matcher(fileName).matches();
          if (accept) {
            retval.add(fileName);
          }
        } catch (final IOException e) {
          throw new Error(e);
        }
      }
    }
    return retval;
  }
}