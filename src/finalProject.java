import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
Author Name: Aaditya Khanal
Co-Author: Priyanka Pandit
*/
public class finalProject {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        QuoteManager quoteManager = new QuoteManager();

        User user = new User();
        Author authorManager = new Author();
        User currentUser = new User();
        String boldText = "\033[1m";
        String resetText = "\033[0m";
        String spacer = boldText+ "----------------------------------------------------"+resetText;

        boolean showSignInPrompt = true;
        boolean isAdmin = false;
        boolean isLoggedIn = false;


        int currentPage = 1;
        int quotesPerPage = 5;

        //Admin controls

        Admin admin = new Admin();
        admin.loadUsersFromFile();



        admin.setUserName("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@nku.edu");




        while (true) {
                                if (showSignInPrompt) {
                                    System.out.println(boldText+"Sign in as:"+resetText);
                                    System.out.println("1) User");
                                    System.out.println("2) Admin");
                                    int signInOption = userInput.nextInt();
                                    userInput.nextLine();
                                    if (signInOption == 1) {
                                        isAdmin = false;
                                    }
                                    else if (signInOption == 2) {
                                        System.out.println("Enter admin's Username");
                                        String adminName = userInput.next();
                                        System.out.println("Enter admin's password");
                                        String adminPassword = userInput.next();
                                        isAdmin = admin.isAdmin(adminName, adminPassword);
                                        if (isAdmin) {
                                            admin.setAdminLoggedIn(true);
                                            isLoggedIn = true;
                                        } else {
                                            System.out.println(spacer);
                                            System.out.println("Invalid admin credentials.");
                                            break;
                                        }
                                    }
                                    showSignInPrompt = false;
                                } else {
                                    displayMenu(isAdmin);

                                    int userOption = userInput.nextInt();
                                    userInput.nextLine();

                                    if (userOption >= 1 && userOption <= 6  && !isLoggedIn) {
                                        System.out.println(spacer);
                                        System.out.println("Please log in to access this feature.");
                                        userOption = 10; // Redirecting to the login userOption
                                    }

                                    switch (userOption) {
                                        case 1:
                                            System.out.println(spacer);
                                            quoteManager.listAllQuotes(currentPage, quotesPerPage);
                                            System.out.println(spacer);
                                            System.out.println("What do you want to do?");
                                            System.out.println("M. Show More Quotes");
                                            System.out.println("E. Edit Quote");
                                            System.out.println("D. Delete Quote");
                                            System.out.println("A. Add Quote");
                                            System.out.println("B. Back to previous menu.");

                                            String quoteAction = userInput.next().toUpperCase();
                                            switch (quoteAction) {
                                                case "M":
                                                    System.out.println(spacer);
                                                    if (quoteManager.calculateTotalPages(quotesPerPage) > currentPage) {
                                                        currentPage++;
                                                        quoteManager.listAllQuotes(currentPage, quotesPerPage);
                                                    } else {
                                                        System.out.println("You have reached the end of the page.");
                                                    }
                                                    break;
                                                case "E":
                                                    System.out.println(spacer);
                                                    System.out.println("Enter the quote number you want to edit:");
                                                    int editQuoteNumber = userInput.nextInt();
                                                    if (editQuoteNumber > 0 && editQuoteNumber <= quoteManager.getQuotes().size()) {
                                                        System.out.println("Enter the new author name:");
                                                        userInput.nextLine();
                                                        String newAuthor = userInput.nextLine();
                                                        System.out.println("Enter the new quote text:");
                                                        String newText = userInput.nextLine();
                                                        quoteManager.editQuote(editQuoteNumber, newAuthor, newText);
                                                        System.out.println("Quote updated successfully.");
                                                    } else {
                                                        System.out.println("Invalid quote number.");
                                                    }
                                                    break;
                                                case "D":
                                                    System.out.println(spacer);
                                                    System.out.println("Enter the quote number you want to delete:");
                                                    int deleteQuoteNumber = userInput.nextInt();
                                                    if (deleteQuoteNumber > 0 && deleteQuoteNumber <= quoteManager.getQuotes().size()) {
                                                        quoteManager.deleteQuote(deleteQuoteNumber - 1);
                                                        System.out.println("Quote deleted successfully.");
                                                    } else {
                                                        System.out.println("Invalid quote number.");
                                                    }
                                                    break;
                                                case "A":
                                                    System.out.println(spacer);
                                                    if (User.Logged) {
                                                        userInput.nextLine();
                                                        System.out.println("Enter the author's name:");
                                                        String author = userInput.nextLine();
                                                        System.out.println("Enter the quote text:");
                                                        String text = userInput.nextLine();
                                                        quoteManager.createNewQuote(user, author, text);
                                                        System.out.println("Quote added successfully.");
                                                    } else {
                                                        System.out.println("You need to be logged in to add a quote.");
                                                    }
                                                    break;
                                                case "B":
                                                    userOption = 7;
                                                    break;
                                                default:
                                                    System.out.println(spacer);
                                                    System.out.println(boldText+"Invalid option, please try again."+resetText);
                                                    System.out.println(spacer);
                                                    break;
                                            }
                                            break;
                                        case 2:
                                            System.out.println(spacer);
                                            authorManager.listAllAuthors();
                                            break;
                                        case 3:
                                            System.out.println(spacer);
                                            if(isAdmin){
                                                admin.listAllUsers();
                                            }else{
                                                if (User.Logged) {
                                                    try {
                                                        quoteManager.addNewQuote(user);
                                                        System.out.println("Quote added successfully.");
                                                    } catch (IOException e) {
                                                        System.out.println("An error occurred while adding the quote. Please try again.");
                                                    }
                                                } else {
                                                    System.out.println("You need to be logged in to add a quote.");
                                                }
                                            }

                                            break;
                                        case 4:
                                            System.out.println(spacer);
                                            if (isAdmin) {
                                                System.out.println("Enter the username of the user whose password you want to change:");
                                                String targetUsername = userInput.next();
                                                System.out.println("Enter the new password:");
                                                String newPassword = userInput.next();

                                                admin.changeUserPassword(targetUsername, newPassword);
                                            }else{System.out.println("Enter the author's name:");
                                                String authorName = userInput.nextLine();
                                                authorManager.addAuthor(authorName);
                                            }
                                            break;
                                        case 5:
                                            if(isAdmin){
                                                System.out.println(spacer);
                                                admin.displayUsersWithIds();
                                                System.out.println(spacer);
                                                System.out.println("Enter the user ID of the user you want to delete:");
                                                int targetUserId = userInput.nextInt();
                                                User targetUser = admin.findUserById(targetUserId);
                                                if (targetUser != null) {
                                                    admin.deleteUser(targetUser, quoteManager);
                                                } else {
                                                    System.out.println("User not found.");
                                                }
                                            }else{
                                                System.out.println(spacer);
                                                System.out.println("Enter the author's name to search:");
                                                String searchName = userInput.nextLine();
                                                authorManager.searchAuthor(searchName);
                                            }
                                            break;
                                        case 6:
                                            System.out.println(spacer);
                                            if(isAdmin){
                                                showSignInPrompt = true;
                                                User.Logged = false;


                                            }
                                            else{
                                            quoteManager.displayRandomQuote();
                                            System.out.println(spacer);
                                            System.out.println("What do you want to do?");
                                            System.out.println("M. Show another random Quote.");
                                            System.out.println("E. Edit Quote");
                                            System.out.println("D. Delete Quote");
                                            System.out.println("A. Add Quote");
                                            System.out.println("B. Back to previous menu.");

                                            String quoteAction2 = userInput.next().toUpperCase();
                                            switch (quoteAction2) {
                                                case "M":
                                                    System.out.println(spacer);
                                                        quoteManager.displayRandomQuote();
                                                    break;
                                                case "E":
                                                    System.out.println(spacer);
                                                    System.out.println("Enter the quote number you want to edit:");
                                                    int editQuoteNumber = userInput.nextInt();
                                                    if (editQuoteNumber > 0 && editQuoteNumber <= quoteManager.getQuotes().size()) {
                                                        System.out.println("Enter the new author name:");
                                                        userInput.nextLine();
                                                        String newAuthor = userInput.nextLine();
                                                        System.out.println("Enter the new quote text:");
                                                        String newText = userInput.nextLine();
                                                        quoteManager.editQuote(editQuoteNumber, newAuthor, newText);
                                                        System.out.println("Quote updated successfully.");
                                                    } else {
                                                        System.out.println("Invalid quote number.");
                                                    }
                                                    break;
                                                case "D":
                                                    System.out.println(spacer);
                                                    System.out.println("Enter the quote number you want to delete:");
                                                    int deleteQuoteNumber = userInput.nextInt();
                                                    if (deleteQuoteNumber > 0 && deleteQuoteNumber <= quoteManager.getQuotes().size()) {
                                                        quoteManager.deleteQuote(deleteQuoteNumber - 1);
                                                        System.out.println("Quote deleted successfully.");
                                                    } else {
                                                        System.out.println("Invalid quote number.");
                                                    }
                                                    break;
                                                case "A":
                                                    System.out.println(spacer);
                                                    if (User.Logged) {
                                                        userInput.nextLine();
                                                        System.out.println("Enter the author's name:");
                                                        String author = userInput.nextLine();
                                                        System.out.println("Enter the quote text:");
                                                        String text = userInput.nextLine();
                                                        quoteManager.createNewQuote(user, author, text);
                                                        System.out.println("Quote added successfully.");
                                                    } else {
                                                        System.out.println("You need to be logged in to add a quote.");
                                                    }
                                                    break;
                                                case "B":
                                                    userOption = 7;
                                                    break;
                                                default:
                                                    System.out.println(spacer);
                                                    System.out.println(boldText+"Invalid option, please try again."+resetText);
                                                    System.out.println(spacer);
                                                    break;
                                            }
                                            }
                                            break;
                                        case 7:
                                            if(isAdmin){
                                                System.out.println(spacer);
                                                System.out.println(boldText + "Thank you for using the Quote Repository." + resetText);
                                                System.out.println(spacer);
                                                System.exit(0);
                                            }else {
                                                System.out.println(spacer);
                                                System.out.println("Going back to sign-in options...");
                                                showSignInPrompt = true;
                                                User.Logged = false;
                                            }
                                            break;
                                        case 8:
                                            System.out.println(spacer);
                                            System.out.println(boldText + "Thank you for using the Quote Repository." + resetText);
                                            System.out.println(spacer);
                                            System.exit(0);
                                            break;
                                        case 9:
                                            System.out.println(spacer);
                                            currentUser.CreateAccount(userInput,admin.getUsers());
                                            break;
                                        case 10:
                                            System.out.println(spacer);
                                            isLoggedIn = user.signIn(admin);
                                            if (!isLoggedIn) {
                                                System.out.println("Do you want to create an account? (yes/no)");
                                                String response = userInput.nextLine();
                                                if (response.equalsIgnoreCase("yes")) {
                                                    user.CreateAccount(userInput,admin.getUsers());
                                                    System.out.println("Account created. You may log in now.");
                                                }
                                            }
                                            break;
                                        default:
                                            System.out.println(spacer);
                                            System.out.println(boldText + "Invalid option. Please try again." + resetText);
                                            break;
                                    }
                                }
                            }
                        }
                        public static void displayMenu(boolean isAdmin) {
                            String boldText = "\033[1m";
                            String resetText = "\033[0m";
                            System.out.println(boldText + "----------------------------------------------------" + resetText);
                            System.out.println(boldText + "Welcome " + (isAdmin ? "Admin" : "User") + ", what do you want to do?" + resetText);
                            if (!isAdmin) {
                                System.out.println("1. List all the quotes");
                                System.out.println("2. List all the Authors");
                                System.out.println("3. Add a Quote");
                                System.out.println("4. Add an author");
                                System.out.println("5. Search for an author");
                                System.out.println("6. Get a random quote");
                                System.out.println("7. Go back to sign-in options");
                                System.out.printf("8. %s Quit %s\n", boldText, resetText);
                            } else {
                                System.out.println("1. List all the quotes");
                                System.out.println("2. List all the Authors");
                                System.out.println("3. List all the Users");
                                System.out.println("4. Change password for the user.");
                                System.out.println("5. Delete an User.");
                                System.out.println("6. Go back to sign-in options");
                                System.out.printf("7. %s Quit %s\n", boldText, resetText);
                            }
                        }
}
