package Options;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

public class ChangeJRXMLteacherName {


    public static void main(String[] args) {
        // Folder path containing the JRXML files
        String folderPath = "H:\\ProjectWith\\Reports";

        // Text to find and replace
        String oldText = "محمود عبدالعليم";
        String newText = "علاء فاروق";

        // Process each .jrxml file in the folder
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jrxml"))
                    .forEach(path -> {
                        try {
                            replaceTextInJRXML(path, oldText, newText);
                            System.out.println("Processed file: " + path);
                        } catch (IOException e) {
                            System.err.println("Failed to process file: " + path + " - " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error reading folder: " + e.getMessage());
        }
    }

    private static void replaceTextInJRXML(Path filePath, String oldText, String newText) throws IOException {
        // Read the content of the JRXML file
        String content = new String(Files.readAllBytes(filePath));

        // Replace the specified text
        String updatedContent = content.replace(oldText, newText);

        // Save the updated content back to the file
        Files.write(filePath, updatedContent.getBytes());
    }
}
