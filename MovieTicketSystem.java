import java.util.*;

class Movie {
    String name;
    int seats;
    Movie next;

    Movie(String name, int seats) {
        this.name = name;
        this.seats = seats;
        next = null;
    }
}

class MovieList {

    Movie head;

    void addMovie(String name, int seats) {
        Movie newMovie = new Movie(name, seats);

        if (head == null) {
            head = newMovie;
            return;
        }

        Movie temp = head;
        while (temp.next != null)
            temp = temp.next;

        temp.next = newMovie;
    }

    void displayMovies() {
        Movie temp = head;

        while (temp != null) {
            System.out.println(temp.name + " | Seats: " + temp.seats);
            temp = temp.next;
        }
    }

    Movie linearSearch(String name) {
        Movie temp = head;

        while (temp != null) {
            if (temp.name.equalsIgnoreCase(name))
                return temp;

            temp = temp.next;
        }

        return null;
    }

    void bubbleSort() {
        if (head == null)
            return;

        for (Movie i = head; i.next != null; i = i.next) {
            for (Movie j = head; j.next != null; j = j.next) {

                if (j.name.compareToIgnoreCase(j.next.name) > 0) {

                    String tempName = j.name;
                    int tempSeats = j.seats;

                    j.name = j.next.name;
                    j.seats = j.next.seats;

                    j.next.name = tempName;
                    j.next.seats = tempSeats;
                }
            }
        }
    }

    ArrayList<String> getMovieArray() {

        ArrayList<String> arr = new ArrayList<>();
        Movie temp = head;

        while (temp != null) {
            arr.add(temp.name);
            temp = temp.next;
        }

        return arr;
    }
}

public class MovieTicketSystem {

    static HashMap<String, String> users = new HashMap<>();

    static Stack<String> bookingHistory = new Stack<>();

    static Queue<String> waitingList = new LinkedList<>();

    static PriorityQueue<String> vipQueue = new PriorityQueue<>();

    static Scanner sc = new Scanner(System.in);

    static MovieList movieList = new MovieList();

    static int binarySearch(ArrayList<String> arr, String key) {

        int low = 0;
        int high = arr.size() - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            int res = arr.get(mid).compareToIgnoreCase(key);

            if (res == 0)
                return mid;

            else if (res < 0)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return -1;
    }

    static void registerUser() {

        System.out.print("Enter username: ");
        String user = sc.next();

        System.out.print("Enter password: ");
        String pass = sc.next();

        users.put(user, pass);

        System.out.println("User Registered!");
    }

    static void loginUser() {

        System.out.print("Enter username: ");
        String user = sc.next();

        System.out.print("Enter password: ");
        String pass = sc.next();

        if (users.containsKey(user) && users.get(user).equals(pass))
            System.out.println("Login Successful");

        else
            System.out.println("Invalid Login");
    }

    static void searchMovie() {

        System.out.print("Enter movie name: ");
        String name = sc.next();

        Movie m = movieList.linearSearch(name);

        if (m != null)
            System.out.println("Movie Found: " + m.name + " Seats: " + m.seats);
        else
            System.out.println("Movie Not Found");
    }

    static void binarySearchMovie() {

        movieList.bubbleSort();

        ArrayList<String> arr = movieList.getMovieArray();

        System.out.print("Enter movie name: ");
        String name = sc.next();

        int index = binarySearch(arr, name);

        if (index != -1)
            System.out.println("Movie Found at index " + index);
        else
            System.out.println("Movie Not Found");
    }

    static void bookTicket() {

        System.out.print("Enter movie name: ");
        String name = sc.next();

        Movie m = movieList.linearSearch(name);

        if (m != null && m.seats > 0) {

            m.seats--;

            bookingHistory.push(name);

            System.out.println("Ticket Booked Successfully");

        } else {

            System.out.println("No seats available, added to waiting list");

            System.out.print("Enter your name: ");
            String user = sc.next();

            waitingList.add(user);
        }
    }

    static void showHistory() {

        System.out.println("Booking History (Stack)");

        while (!bookingHistory.isEmpty())
            System.out.println(bookingHistory.pop());
    }

    static void addVIP() {

        System.out.print("Enter VIP name: ");
        String name = sc.next();

        vipQueue.add(name);

        System.out.println("VIP Added");
    }

    static void showVIP() {

        System.out.println("VIP Priority Queue");

        while (!vipQueue.isEmpty())
            System.out.println(vipQueue.poll());
    }

    public static void main(String[] args) {

        movieList.addMovie("Avatar", 5);
        movieList.addMovie("Avengers", 4);
        movieList.addMovie("Inception", 3);
        movieList.addMovie("Titanic", 2);

        int choice;

        do {

            System.out.println("\n---- Online Movie Ticket Booking ----");
            System.out.println("1 Register");
            System.out.println("2 Login");
            System.out.println("3 Display Movies");
            System.out.println("4 Search Movie (Linear Search)");
            System.out.println("5 Search Movie (Binary Search)");
            System.out.println("6 Sort Movies");
            System.out.println("7 Book Ticket");
            System.out.println("8 Booking History (Stack)");
            System.out.println("9 Add VIP User");
            System.out.println("10 Show VIP Users");
            System.out.println("11 Exit");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    registerUser();
                    break;

                case 2:
                    loginUser();
                    break;

                case 3:
                    movieList.displayMovies();
                    break;

                case 4:
                    searchMovie();
                    break;

                case 5:
                    binarySearchMovie();
                    break;

                case 6:
                    movieList.bubbleSort();
                    System.out.println("Movies Sorted");
                    break;

                case 7:
                    bookTicket();
                    break;

                case 8:
                    showHistory();
                    break;

                case 9:
                    addVIP();
                    break;

                case 10:
                    showVIP();
                    break;
            }

        } while (choice != 11);
    }
}