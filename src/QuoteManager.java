import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class QuoteManager {
    private ArrayList<Quote> quotes;

    public QuoteManager() {
        quotes = new ArrayList<Quote>();
        readQuotesFromFile();
    }

    private void readQuotesFromFile() {
        try {
            File quotesFile = new File("quotes.csv");
            Scanner scanner = new Scanner(quotesFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] quoteData = line.split(";");
                String author = quoteData[0];
                String text = quoteData[1];
                LocalDate addDate = LocalDate.parse(quoteData[2]);
                String editor = quoteData[3];

                Quote quote = new Quote(author, text, addDate, editor);
                quotes.add(quote);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: The quotes file could not be found or accessed. Please try again.");
        }
    }

    public void addNewQuote(User person) throws IOException {
        // Call the createNewQuote method from the Quote class
        Quote newQuote = Quote.createNewQuote(person);

        // Add the new quote to the quotes ArrayList
        quotes.add(newQuote);
    }
    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
    public int calculateTotalPages(int quotesPerPage) {
        return (int) Math.ceil((double) quotes.size() / quotesPerPage);
    }

    public void listAllQuotes(int currentPage, int quotesPerPage) {
        int totalPages = calculateTotalPages(quotesPerPage);
        if (currentPage <= totalPages && currentPage > 0) {
            int startIndex = (currentPage - 1) * quotesPerPage;
            int endIndex = Math.min(startIndex + quotesPerPage, quotes.size());

            System.out.println("Page " + currentPage + " of " + totalPages);
            for (int i = startIndex; i < endIndex; i++) {
                Quote quote = quotes.get(i);
                System.out.println("----------------------------------------------------");
                System.out.printf("%d.", (i + 1));
                quote.showQuote();
            }
        } else {
            System.out.println("Error: Invalid page number.");
        }
    }

    public void displayRandomQuote() {
        if (!quotes.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(quotes.size());
            quotes.get(randomIndex).showQuote(); // Corrected: Removed the unnecessary argument
        } else {
            System.out.println("No quotes available.");
        }
   }
    private void saveQuotesToFile() {
        try {
            FileWriter writer = new FileWriter("quotes.csv");
            for (Quote quote : quotes) {
                writer.append(quote.getAuthor() + ";" + quote.getText() + ";" + quote.getAddDate() + ";" + quote.getEditor() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating the quotes.csv file.");
        }
    }

    public void editQuote(int index, String newAuthor, String newText) {
        if (index >= 0 && index < quotes.size()) {
            Quote quote = quotes.get(index);
            quote.setAuthor(newAuthor);
            quote.setText(newText);
            saveQuotesToFile(); // You need to create this method to update the quotes.csv file
        }
    }

    public void deleteQuote(int index) {
        if (index >= 0 && index < quotes.size()) {
            quotes.remove(index);
            saveQuotesToFile(); // You need to create this method to update the quotes.csv file
        }
    }
    public void deleteQuotesByUserId(int userId) {
        quotes.removeIf(quote -> quote.getUserId() == userId);
    }

    public void createNewQuote(User user, String author, String text) {
        Quote newQuote = new Quote();
        newQuote.setAuthor(author);
        newQuote.setText(text);
        newQuote.setAddDate(LocalDate.now());
        newQuote.setEditor(user.getUserName());
        quotes.add(newQuote);
        saveQuotesToFile(); // You need to create this method to update the quotes.csv file
    }



    /* (Tested but had a bug)
    public void createNewQuote(User user) {
        if (User.Logged) {
            Quote newQuote = new Quote();
            try {
                newQuote.createQuote(user);
                quotes.add(newQuote);
            } catch (IOException e) {
                System.err.println("Error creating new quote");
                e.printStackTrace();
            }
        } else {
            System.out.println("You must be logged in to create a new quote.");
        }
    }
    */
}
