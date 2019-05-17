/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real.life.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Create/Rename the multiple files with a pattern in a source directory at the
 * desired destination path.
 *
 *
 * @author Akshay
 * @version 1.0
 * @since 1.0 18-MAY-2019
 */
public class FileRename {

    public static void main(String[] args) {
        String pattern = "sample_new";
        String replaceWith = "sample";
        String source = "D:\\DirRenameExample\\temp";
        String destination = "D:\\DirRenameExample\\temp1\\";

        /**
         * If true then move the renamed to destination else create copy.
         */
        boolean isRename = true;
        try {
            FileRename.rename(pattern, replaceWith, source, destination, isRename);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     *
     * @param pattern Pattern to match
     * @param replaceWith Replace file pattern with
     * @param source Source Path
     * @param destination Destination Path. If not exists, will create new and
     * if not provided then use the source as the destination.
     * @param isRename if true then rename and move the files else create the
     * copy and move.
     * @throws Exception
     */
    private static void rename(String pattern, String replaceWith, String source, String destination, boolean isRename) throws Exception {
        File sourceDir = new File(source);
        if (!sourceDir.exists()) {
            throw new Exception("source Directory:(" + source + ") not exists");
        }

        if (!sourceDir.isDirectory()) {
            throw new Exception("(" + source + ") is not a directory.");
        }

        File destinationDir = null;
        if (destination != null && !destination.trim().isEmpty()) {
            destinationDir = new File(destination);
            if (!destinationDir.exists()) {
                destinationDir.mkdir();
            }
        } else {
            destination = source;
        }

        for (File file : sourceDir.listFiles()) {
            if (file.getName().startsWith(pattern)) {
                String fileName = file.getName();
//                System.out.println("fileName= " + fileName);
                fileName = fileName.replaceFirst(pattern, replaceWith);
                File dest = new File(destination + fileName);
                if (isRename) {
                    boolean isRenameDone = file.renameTo(dest);
                    System.out.println("isRenameDone = " + isRenameDone + "=== fileName = " + dest.getCanonicalPath());
                } else {
                    if (!dest.exists()) {
                        dest.createNewFile();
                    }
                    Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Created Created dest = " + dest.toPath());
                }
            }
        }
    }
}
