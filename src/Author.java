import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public class Author {
    private Set<String> authors;

    public Author() {
        authors = new HashSet<>();
        String filePath = "Authors.csv";
        File file = new File(filePath);
        try {
            Scanner scan = new Scanner(new FileReader(file));
            while (scan.hasNextLine()) {
                String authorName = scan.nextLine().trim();
                authors.add(authorName);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addAuthor(String authorName) {
        authors.add(authorName);
        String filePath = "Authors.csv";
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.append(authorName);
            writer.append("\n");
            writer.flush();
            writer.close();
            System.out.println("Author '" + authorName + "' has been added.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listAllAuthors() {
        for (String author : authors) {
            System.out.println(author);
        }
    }

    public void searchAuthor(String searchName) {
        boolean found = false;
        for (String author : authors) {
            if (author.equalsIgnoreCase(searchName)) {
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Author '" + searchName + "' is found.");
        } else {
            System.out.println("Author '" + searchName + "' is not found.");
        }
    }
}

