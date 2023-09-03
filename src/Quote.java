import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Quote {
    private int userId;

    public Quote(String author, String text, int userId) {
        this.author = author;
        this.text = text;
        this.userId = userId;
    }

    // Add a getter for the userId
    public int getUserId() {
        return userId;
    }

    public String author;
    public String text;
    public String editor;
    public LocalDate addDate;
    private Admin admin;

    public Quote(Admin admin) {
        this.admin = admin;
    }


    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public String getEditor() {
        return editor;
    }

    // Constructor to create a new Quote object
    public Quote(String author, String text, LocalDate addDate, String editor) {
        this.author = author;
        this.text = text;
        this.addDate = addDate;
        this.editor = editor;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setEditor(String editor) {
        this.editor = editor;
    }
    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Quote() {
    }


    // Static method to create a new quote and return it
    public static Quote createNewQuote(User person) throws IOException {
        String author;
        String text;
        LocalDate addDate;
        String editor = person.userName;

        Scanner input = new Scanner(System.in);

        System.out.println("What is the name of the person who said the quote?");
        author = input.nextLine();
        System.out.println("What is the Quote?");
        text = input.nextLine();
        LocalDate current = LocalDate.now();
        addDate = LocalDate.now();

        Quote newQuote = new Quote(author, text, addDate, editor);

        try {
            FileWriter write = new FileWriter("quotes.csv", true);
            write.append(author + ";" + text + ";" + current + ";" + editor);
            write.append("\n");

            write.flush();
            write.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: The quotes file could not be found or accessed. Please try again.");
        } catch (IOException e) {
            System.out.println("Error: There was a problem while writing to the quotes file. Please try again.");
        }

        return newQuote;
    }
    public void showQuote() {
        System.out.println(" " + text);
        System.out.println("-  " + author);
        System.out.println("   Added by " + editor);
        System.out.println("   Date Added: " + addDate);
    }

}



