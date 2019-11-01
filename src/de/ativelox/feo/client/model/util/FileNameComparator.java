package de.ativelox.feo.client.model.util;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class FileNameComparator implements Comparator<Path> {

    @Override
    public int compare(Path o1, Path o2) {
        String fileName1 = o1.getFileName().toString().split("\\.")[0];
        String fileName2 = o2.getFileName().toString().split("\\.")[0];

        String[] numbers1 = fileName1.split("_");
        String[] numbers2 = fileName2.split("_");

        int file1First = Integer.parseInt(numbers1[1]);
        int file2First = Integer.parseInt(numbers2[1]);

        if (file1First > file2First) {
            return 1;

        } else if (file1First < file2First) {
            return -1;

        } else {
            if (numbers1.length <= 2) {
                return -1;
            } else if (numbers2.length <= 2) {
                return 1;
            }

            int file1Second = Integer.parseInt(numbers1[2]);
            int file2Second = Integer.parseInt(numbers2[2]);

            if (file1Second > file2Second) {
                return 1;
            } else if (file1Second < file2Second) {
                return -1;
            }
        }
        return 0;
    }

}
