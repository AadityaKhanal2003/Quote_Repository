import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private List<User> users;
    public Admin(List<User> users) {
        this.users = users;
    }

    public Admin() {
        users = new ArrayList<>();
    }
    private boolean adminLoggedIn = false;

    public boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    public void setAdminLoggedIn(boolean adminLoggedIn) {
        this.adminLoggedIn = adminLoggedIn;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }


    public void listAllUsers() {
        String filePath = "Users.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            System.out.println("List of all users:");
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                System.out.println("Username: " + userData[0] + ", Email: " + userData[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public void deleteUser(User targetUser, QuoteManager quoteManager) {
        if (adminLoggedIn) {
            users.remove(targetUser);
            quoteManager.deleteQuotesByUserId(targetUser.getUserId());
            System.out.println("User and their quotes have been deleted.");
        } else {
            System.out.println("Admin is not logged in.");
        }
    }

    // finding the user
    public User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
    public void displayUsersWithIds() {
        System.out.println("List of users with their IDs:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId() + ", Username: " + user.getUserName());
        }
    }
    public static List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        String filePath = "Users.csv";
        File file = new File(filePath);

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userInfo = line.split(",");

                User user = new User(userInfo[0], userInfo[1], userInfo[2]);
                users.add(user);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }



    // Method to change another user's password
    public void changeUserPassword(String targetUsername, String newPassword) {
        if (adminLoggedIn) {
            String filepath = "Users.csv";
            String tempFilepath = "tempUsers.csv";
            File file = new File(filepath);
            File tempFile = new File(tempFilepath);

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                boolean userFound = false;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (userData[0].equalsIgnoreCase(targetUsername)) {
                        userFound = true;
                        userData[1] = newPassword;
                    }
                    writer.write(String.join(",", userData));
                    writer.newLine();
                }
                reader.close();
                writer.close();

                if (userFound) {
                    // Replace the original file with the updated file
                    if (file.delete() && tempFile.renameTo(file)) {
                        System.out.println("Password changed successfully for user: " + targetUsername);
                    } else {
                        System.out.println("An error occurred while updating the password.");
                    }
                } else {
                    System.out.println("User not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading or writing the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Admin is not logged in.");
        }
    }

}