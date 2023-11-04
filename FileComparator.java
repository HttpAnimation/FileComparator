import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileComparator {
    public static void main(String[] args) {
        // Load folder paths from Folders.ini
        Properties properties = new Properties();
        try {
            FileInputStream fileInput = new FileInputStream("Folders.ini");
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.err.println("Error reading Folders.ini: " + e.getMessage());
            return;
        }

        String folderPath1 = properties.getProperty("Folder1");
        String folderPath2 = properties.getProperty("Folder2");

        // Check if folder paths are provided
        if (folderPath1 == null || folderPath2 == null) {
            System.err.println("Folder paths not specified in Folders.ini");
            return;
        }

        // Lists to store file information
        List<FileInfo> fileList1 = new ArrayList<>();
        List<FileInfo> fileList2 = new ArrayList<>();

        // Populate file lists
        populateFileList(new File(folderPath1), fileList1);
        populateFileList(new File(folderPath2), fileList2);

        // Compare the two lists and display matching files
        for (FileInfo file1 : fileList1) {
            for (FileInfo file2 : fileList2) {
                if (file1.getName().equals(file2.getName())) {
                    System.out.println("Matching file found:");
                    System.out.println("Name: " + file1.getName());
                    System.out.println("Size: " + file1.getSize() + " bytes");
                    System.out.println("Path in Folder 1: " + file1.getPath());
                    System.out.println("Path in Folder 2: " + file2.getPath());
                    System.out.println();
                }
            }
        }
    }

    private static void populateFileList(File directory, List<FileInfo> fileList) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(new FileInfo(file));
                    } else if (file.isDirectory()) {
                        populateFileList(file, fileList);
                    }
                }
            }
        }
    }

    static class FileInfo {
        private String name;
        private long size;
        private String path;

        FileInfo(File file) {
            this.name = file.getName();
            this.size = file.length();
            this.path = file.getAbsolutePath();
        }

        String getName() {
            return name;
        }

        long getSize() {
            return size;
        }

        String getPath() {
            return path;
        }
    }
}
