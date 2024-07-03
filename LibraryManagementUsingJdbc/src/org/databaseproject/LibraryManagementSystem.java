package org.databaseproject;

import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    // Create
    public static void addBook(String title, String author, int publicationYear, String genre) {
        String query = "INSERT INTO books (title, author, publication_year, genre) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, publicationYear);
            preparedStatement.setString(4, genre);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read all books
    public static void listAllBooks() {
        String query = "SELECT * FROM books";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Books in the library:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int publicationYear = resultSet.getInt("publication_year");
                String genre = resultSet.getString("genre");

                System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author +
                                   ", Year: " + publicationYear + ", Genre: " + genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update
    public static void updateBook(int id, String title, String author, int publicationYear, String genre) {
        String query = "UPDATE books SET title = ?, author = ?, publication_year = ?, genre = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, publicationYear);
            preparedStatement.setString(4, genre);
            preparedStatement.setInt(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book with ID " + id + " was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public static void deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book with ID " + id + " was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search by genre and/or publication year
    public static void searchBooks(String genre, int publicationYear) {
        String query = "SELECT * FROM books WHERE genre = ? AND publication_year = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, genre);
            preparedStatement.setInt(2, publicationYear);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Books found:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("publication_year");
                String bookGenre = resultSet.getString("genre");

                System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author +
                                   ", Year: " + year + ", Genre: " + bookGenre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add Book");
            System.out.println("2. List All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Books by Genre and Year");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int publicationYear = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    addBook(title, author, publicationYear, genre);
                    break;

                case 2:
                    listAllBooks();
                    break;

                case 3:
                    System.out.print("Enter book ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter new author: ");
                    String newAuthor = scanner.nextLine();
                    System.out.print("Enter new publication year: ");
                    int newYear = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new genre: ");
                    String newGenre = scanner.nextLine();
                    updateBook(updateId, newTitle, newAuthor, newYear, newGenre);
                    break;

                case 4:
                    System.out.print("Enter book ID to delete: ");
                    int deleteId = scanner.nextInt();
                    deleteBook(deleteId);
                    break;

                case 5:
                    System.out.print("Enter genre to search (leave empty for any genre): ");
                    String searchGenre = scanner.nextLine();
                    System.out.print("Enter publication year to search (0 for any year): ");
                    int searchYear = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    searchBooks(searchGenre, searchYear);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

