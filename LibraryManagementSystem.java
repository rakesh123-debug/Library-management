import java.util.*;

// Book class
class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    public void returnBook() {
        isAvailable = true;
    }

    public void displayBookInfo() {
        System.out.println("Title: " + title + ", Author: " + author + ", ISBN: " + ISBN + ", Available: " + isAvailable);
    }
}

// User class
class User {
    private String name;
    private String userId;
    private List<Book> borrowedBooks;

    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.borrow();
            System.out.println(name + " borrowed " + book.getTitle());
        } else {
            System.out.println("Sorry, this book is currently unavailable.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.returnBook();
            System.out.println(name + " returned " + book.getTitle());
        } else {
            System.out.println("You haven't borrowed this book.");
        }
    }

    public void displayUserInfo() {
        System.out.println("User: " + name + ", ID: " + userId);
        System.out.println("Borrowed Books:");
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            for (Book book : borrowedBooks) {
                System.out.println(" - " + book.getTitle());
            }
        }
    }
}

// Library class
class Library {
    private Map<String, Book> books;
    private Map<String, User> users;

    public Library() {
        books = new HashMap<>();
        users = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getISBN(), book);
    }

    public void removeBook(String ISBN) {
        if (books.containsKey(ISBN)) {
            books.remove(ISBN);
            System.out.println("Book with ISBN " + ISBN + " removed.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void removeUser(String userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            System.out.println("User with ID " + userId + " removed.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books.values()) {
                book.displayBookInfo();
            }
        }
    }

    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            for (User user : users.values()) {
                user.displayUserInfo();
            }
        }
    }

    public Book getBookByISBN(String ISBN) {
        return books.get(ISBN);
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }
}

// Main class with user interface
public class LibraryManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add User");
            System.out.println("4. Remove User");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. List Books");
            System.out.println("8. List Users");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    addUser();
                    break;
                case 4:
                    removeUser();
                    break;
                case 5:
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    library.listBooks();
                    break;
                case 8:
                    library.listUsers();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String ISBN = scanner.nextLine();
        Book book = new Book(title, author, ISBN);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        System.out.print("Enter book ISBN to remove: ");
        String ISBN = scanner.nextLine();
        library.removeBook(ISBN);
    }

    private static void addUser() {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        User user = new User(name, userId);
        library.addUser(user);
        System.out.println("User added successfully.");
    }

    private static void removeUser() {
        System.out.print("Enter user ID to remove: ");
        String userId = scanner.nextLine();
        library.removeUser(userId);
    }

    private static void borrowBook() {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        User user = library.getUserById(userId);
        if (user != null) {
            System.out.print("Enter book ISBN to borrow: ");
            String ISBN = scanner.nextLine();
            Book book = library.getBookByISBN(ISBN);
            if (book != null) {
                user.borrowBook(book);
            } else {
                System.out.println("Book not found.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void returnBook() {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        User user = library.getUserById(userId);
        if (user != null) {
            System.out.print("Enter book ISBN to return: ");
            String ISBN = scanner.nextLine();
            Book book = library.getBookByISBN(ISBN);
            if (book != null) {
                user.returnBook(book);
            } else {
                System.out.println("Book not found.");
            }
        } else {
            System.out.println("User not found.");
        }
    }
}
