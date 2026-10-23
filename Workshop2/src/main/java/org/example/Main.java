package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Book[] book = {
                new Book(1024, "9780000000001", "Shatter Me"),
                new Book(2157, "9780000000002", "Harry Potter"),
                new Book(3098, "9780000000003", "The Hunger Games"),
                new Book(4126, "9780000000004", "The Notebook"),
                new Book(5381, "9780000000005", "The Silent Patient"),
                new Book(6472, "9780000000006", "It"),
        new Book(7539, "9780000000007", "The Hobbit"),
        new Book(8645, "9780000000008", "Atomic Habits"),
        new Book(9713, "9780000000009", "Becoming"),
        new Book(1082, "9780000000010", "The Great Gatsby"),
        new Book(2349, "9780000000011", "Dune"),
        new Book(3567, "9780000000012", "The Maze Runner"),
        new Book(4781, "9780000000013", "Gone Girl"),
        new Book(5916, "9780000000014", "Wonder"),
        new Book(6238, "9780000000015", "The Fault in Our Stars"),
        new Book(7451, "9780000000016", "Educated"),
        new Book(8679, "9780000000017", "The Alchemist"),
        new Book(9824, "9780000000018", "Pride and Prejudice"),
        new Book(1356, "9780000000019", "The Martian"),
        new Book(2693, "9780000000020", "The Lightning Thief"),
        };


        int option = 0;
        while (option != 3) {
            System.out.println("======Welcome to the Reading Corner=====");
            System.out.println("Welcome! Please select from the following");
            System.out.println("1. Explore Books on the Shelf");
            System.out.println("2. See Books currently Borrowed");
            System.out.println("3. End your visit");
            System.out.println("Your selection: ");

            option = scanner.nextInt();

            switch (option){
                case 1:
                    System.out.println("-----READY FOR A NEW READER------");
                    for(int i = 0; i < book.length; i++){

                        if (book[i].isCheckedOut() == false){
                            System.out.println(
                                    "Books ID: " + book[i].getId() + " | ISBN: " +book[i].getIsbn() + " | Title: " + book[i].getTitle()
                            );
                        }
                    }

                    System.out.println("Enter a book ID to borrow(0 to return): ");
                    int borrowId = scanner.nextInt();
                    scanner.nextLine();

                    if (borrowId !=0){
                        boolean bookFound = false;

                        for(int i =0; i< book.length;i++){
                            if (book[i].getId() == borrowId){
                                bookFound = true;
                                if (book[i].isCheckedOut() == false){
                                    System.out.println("Who will be reading this book? ");
                                    String reader = scanner.nextLine();

                                    book[i].checkOut(reader);
                                    System.out.println("Happy reading!");
                                } else{
                                    System.out.println("This story is already with another reader.");
                                }
                                break;
                            }
                        }
                        if (bookFound == false){
                            System.out.println("We couldn't find that book ID.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("-------BOOKS OUT ON AN ADVENTURE------");
                    for(int i = 0; i < book.length; i++){
                        if(book[i].isCheckedOut() == true){
                            System.out.println(
                                    book[i].getId() + " | " + book[i].getIsbn() + " | " + book[i].getTitle() + " | Reader: " + book[i].getCheckedOutTo()
                            );
                        }
                    }
                    System.out.println("C - Check In a Book");
                    System.out.println("X - Back to Main Menu");
                    System.out.println("Your selection: ");

                    String action = scanner.next();

                    if (action.equalsIgnoreCase("C")){
                        System.out.println("Enter the book ID to return: ");
                        int returnId = scanner.nextInt();

                        boolean returned = false;

                        for (int i = 0; i < book.length;i++){
                            if (book[i].getId() == returnId && book[i].isCheckedOut() == true){
                                book[i].checkIn();
                                returned = true;

                                System.out.println("Your book is has been returned!");
                                break;
                            }
                        }
                        if (returned == false){
                            System.out.println("No borrowed book found with that ID.");
                        }
                    }
                    break;

            }
        }
    }
}