import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class User {
    private static int userCounter = 0; // To generate unique IDs for users
    private Admin admin;

    public User(Admin admin) {
        this.admin = admin;
    }

    private int userId;
    private int id;

    private static int nextId = 1;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    // Add a getter for the userId
    public int getUserId() {
        return userId;
    }
    public User() {
        this.userId = userCounter++;
    }
    private String email;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    public String userName;
    public static User user;

    public String Email;
    public String Password;
    public String getEmail() {
        return email;
    }
    protected String password;
    public String getPassword() {
        return password;
    }

    public static boolean Logged=false;
    public String getUserName() {
        return this.userName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmail(String email) {
        this.Email = email;
    }

    public static ArrayList<User> users = new ArrayList<>();

    public void CreateAccount(Scanner userInput, List<User> users) {
        String filepath = "Users.csv";
        File file = new File(filepath);

        System.out.println("Hello new user, please enter your name");
        this.userName = userInput.next();
        System.out.println("Enter your email: ");
        this.Email = userInput.next();
        System.out.println("Enter a password: ");
        this.Password = userInput.next();

        User newUser = new User(userName, Password, Email);
        users.add(newUser);
        this.id = nextId++;

        try {
            FileWriter writer = new FileWriter(filepath, true);

            writer.append(id +","+ userName + "," + Password + "," + Email);
            writer.append("\n");
            writer.flush();
            writer.close();

            System.out.println("Account created successfully.");
        } catch (IOException var7) {
            var7.printStackTrace();
        }
    }



    public static void Logout(){
        User.Logged=false;
    }

    public static boolean inDatabase(String userName, String password) {
        String filePath = "Users.csv";
        File file = new File(filePath);
        try {
            Scanner scan = new Scanner(new FileReader(file));
            while (scan.hasNextLine()) {
                String row = scan.nextLine();
                String[] data = row.split(",");
                if (userName.equals(data[0].trim()) && password.equals(data[1].trim())) {
                    return true;
                }
            }
            scan.close();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        }

        return false;
    }
    public boolean isAdmin(String userName, String password) {
        String adminUsername = "admin";
        String adminPassword = "admin";

        return userName.equals(adminUsername) && password.equals(adminPassword);
    }

    public boolean signIn(Admin admin) {
        admin = new Admin();
        admin.setUserName("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@nku.edu");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your Username");
        String userName = input.next();
        System.out.println("Enter password");
        String password = input.next();

        if (userName.equals(admin.getUserName()) && password.equals(admin.getPassword())) {
            if (admin.isAdminLoggedIn()) {
                System.out.println("Admin has already logged in.");
                return false;
            } else {
                admin.setAdminLoggedIn(true);
            }
        }

        if (inDatabase(userName, password) || (userName.equals(admin.getUserName()) && password.equals(admin.getPassword()))) {
            System.out.println("Correct, Logged in successfully . . . ");
            User.Logged = true;
            this.userName = userName;
            if (userName.equals(admin.getUserName()) && password.equals(admin.getPassword())) {
                user = admin;
            }
            return true;
        } else {
            System.out.println("----------------------------------------------------");
            return false;
        }
    }

}












