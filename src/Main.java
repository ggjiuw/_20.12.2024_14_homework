import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("What do you want to use? (view, edit, copy, new, quit)");
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print(">> ");
            String userInput = scanner.next();

            if (userInput.equals("view")) {
                String fileName = getPath("Enter file name to view\n>> ", scanner);
                if (fileName != null)
                    view(fileName);

            } else if (userInput.equals("edit")) {

                System.out.println("Enter some text\ntype exit to exit.");

                Queue<String> textQueue = new LinkedList<>();

                String userText = scanner.next();
                while (!userText.equals("exit"))  {
                    textQueue.add(userText);

                    userText = scanner.next();
                }

                String fileName = getPath("Enter file name to edit\n>> ", scanner);
                if (fileName != null)
                    edit(fileName, textQueue);

            } else if (userInput.equals("copy")) {
                String copyFrom = getPath("Copy from\n>> ", scanner);
                String copyTo = getPath("Copy to\n>> ", scanner);

                if (!(copyFrom == null || copyTo == null)) {
                    copyFile(copyFrom, copyTo);
                }
            } else if (userInput.equals("new")) {
                String newFileName = getPath("Enter file name to create\n>> ", scanner);

                if (newFileName != null)
                    createNewFile(newFileName);
            } else if (userInput.equals("quit")) {
                break;
            } else {
                System.out.println("Unknown command: '" + userInput + "'");
            }

        }


    }

    public static void view(String fileName) {
        Path filePath = Paths.get(fileName);

        try {
            for (String line : Files.readAllLines(filePath)) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void edit(String fileName, Queue<String> queue) {
        Path filePath = Paths.get(fileName);

        try {
            String item = queue.poll();

            while (item != null) {
                Files.write(filePath, (item + "\n").getBytes(), StandardOpenOption.APPEND);

                item = queue.poll();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void copyFile(String copyFrom, String copyTo) {
        Path copyFromPath = Paths.get(copyFrom);
        Path copyToPath = Paths.get(copyTo);

        try {

            Files.createFile(copyToPath);
            for (String line : Files.readAllLines(copyFromPath)) {
                Files.write(copyToPath, (line + "\n").getBytes(), StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void createNewFile(String fileName) {
        Path newFilePath = Paths.get(fileName);
        try {
            Files.createFile(newFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getPath(String text, Scanner scanner) {
        System.out.print(text);

        String fileName = scanner.next();
        if (fileName == null) {
            System.out.println("File name cannot be null!");
            System.out.println("Canceling...");
            return null;
        }
        return fileName;
    }
}
