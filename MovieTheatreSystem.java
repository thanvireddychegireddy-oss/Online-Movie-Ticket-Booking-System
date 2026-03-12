import java.util.*;

/**
 * ================================================
 *   MOVIE THEATRE SYSTEM — DSA in Java
 *   Topics: Searching, Sorting, Linked List,
 *           Stack, Queue, Hashing, BST
 * ================================================
 */

// ─────────────────────────────────────────────
// MOVIE CLASS — Core data object
// ─────────────────────────────────────────────
class Movie {
    int id;
    String title;
    String genre;
    double rating;
    int seats;
    double price;

    public Movie(int id, String title, String genre, double rating, int seats, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.seats = seats;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID:%-3d | %-25s | %-10s | Rating:%.1f | Seats:%-3d | Rs.%.0f",
                id, title, genre, rating, seats, price);
    }
}

// ─────────────────────────────────────────────
// SECTION 1: SEARCHING
// ─────────────────────────────────────────────
class Searching {

    // Linear Search — O(n)
    // Checks each movie one by one
    public static Movie linearSearch(Movie[] movies, String title) {
        System.out.println("\n[Linear Search] Looking for: " + title);
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].title.equalsIgnoreCase(title)) {
                System.out.println("  Found at index " + i);
                return movies[i];
            }
        }
        System.out.println("  Not found.");
        return null;
    }

    // Binary Search — O(log n)
    // Array must be sorted by title first
    public static Movie binarySearch(Movie[] sorted, String title) {
        System.out.println("\n[Binary Search] Looking for: " + title);
        int low = 0, high = sorted.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = sorted[mid].title.compareToIgnoreCase(title);
            System.out.println("  Checking: " + sorted[mid].title);
            if (cmp == 0) { System.out.println("  Found!"); return sorted[mid]; }
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        System.out.println("  Not found.");
        return null;
    }
}

// ─────────────────────────────────────────────
// SECTION 2: SORTING
// ─────────────────────────────────────────────
class Sorting {

    // Bubble Sort — O(n²) — Sort by price
    public static void bubbleSort(Movie[] arr) {
        System.out.println("\n[Bubble Sort] Sorting by price...");
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].price > arr[j + 1].price) {
                    Movie temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (Movie m : arr) System.out.println("  " + m.title + " -> Rs." + m.price);
    }

    // Insertion Sort — O(n²) — Sort by rating (high to low)
    public static void insertionSort(Movie[] arr) {
        System.out.println("\n[Insertion Sort] Sorting by rating (high to low)...");
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Movie key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].rating < key.rating) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        for (Movie m : arr) System.out.println("  " + m.title + " -> " + m.rating);
    }

    // Merge Sort — O(n log n) — Sort by title alphabetically
    public static void mergeSort(Movie[] arr, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid + 1, r);
            merge(arr, l, mid, r);
        }
    }

    private static void merge(Movie[] arr, int l, int mid, int r) {
        Movie[] left = Arrays.copyOfRange(arr, l, mid + 1);
        Movie[] right = Arrays.copyOfRange(arr, mid + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i].title.compareToIgnoreCase(right[j].title) <= 0)
                arr[k++] = left[i++];
            else
                arr[k++] = right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }
}

// ─────────────────────────────────────────────
// SECTION 3: LINKED LIST — Booking History
// ─────────────────────────────────────────────
class BookingNode {
    int bookingId;
    String customer;
    String movie;
    int seats;
    BookingNode next;

    public BookingNode(int id, String customer, String movie, int seats) {
        this.bookingId = id;
        this.customer = customer;
        this.movie = movie;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Booking#" + bookingId + " | " + customer + " | " + movie + " | " + seats + " seats";
    }
}

class BookingList {
    private BookingNode head;

    // Add new booking at end — O(n)
    public void addBooking(BookingNode node) {
        if (head == null) { head = node; return; }
        BookingNode curr = head;
        while (curr.next != null) curr = curr.next;
        curr.next = node;
        System.out.println("  Booking added: " + node);
    }

    // Cancel booking by ID — O(n)
    public void cancelBooking(int id) {
        if (head == null) return;
        if (head.bookingId == id) { System.out.println("  Cancelled: " + head); head = head.next; return; }
        BookingNode curr = head;
        while (curr.next != null) {
            if (curr.next.bookingId == id) {
                System.out.println("  Cancelled: " + curr.next);
                curr.next = curr.next.next;
                return;
            }
            curr = curr.next;
        }
        System.out.println("  Booking #" + id + " not found.");
    }

    // Display all bookings
    public void display() {
        System.out.println("\n[Booking History]:");
        BookingNode curr = head;
        if (curr == null) { System.out.println("  No bookings."); return; }
        while (curr != null) {
            System.out.println("  -> " + curr);
            curr = curr.next;
        }
    }
}

// ─────────────────────────────────────────────
// SECTION 4: STACK — Recently Viewed Movies
// ─────────────────────────────────────────────
class MovieStack {
    private Stack<String> stack = new Stack<>();

    // Push a movie onto the viewed history — O(1)
    public void view(String movieTitle) {
        stack.push(movieTitle);
        System.out.println("  Viewed: " + movieTitle);
    }

    // Pop last viewed movie — O(1)
    public void undoView() {
        if (!stack.isEmpty())
            System.out.println("  Removed from history: " + stack.pop());
        else
            System.out.println("  History is empty.");
    }

    // Show all viewed movies
    public void display() {
        System.out.println("\n[Recently Viewed Stack]: " + stack);
    }
}

// ─────────────────────────────────────────────
// SECTION 5: QUEUE — Ticket Booking Queue
// ─────────────────────────────────────────────
class BookingQueue {
    private Queue<String> queue = new LinkedList<>();

    // Customer joins queue — O(1)
    public void joinQueue(String customer) {
        queue.add(customer);
        System.out.println("  Joined queue: " + customer);
    }

    // Serve next customer — O(1)
    public void serveNext() {
        if (!queue.isEmpty())
            System.out.println("  Serving: " + queue.poll());
        else
            System.out.println("  Queue is empty.");
    }

    // Display current queue
    public void display() {
        System.out.println("\n[Booking Queue]: " + queue);
    }
}

// ─────────────────────────────────────────────
// SECTION 6: HASH MAP — Fast Movie Lookup
// ─────────────────────────────────────────────
class MovieHashMap {
    // Java's HashMap — O(1) average for get/put
    private HashMap<Integer, Movie> map = new HashMap<>();

    // Insert movie by ID
    public void insert(Movie m) {
        map.put(m.id, m);
    }

    // Search movie by ID — O(1)
    public Movie search(int id) {
        return map.getOrDefault(id, null);
    }

    // Display all movies in hash map
    public void display() {
        System.out.println("\n[HashMap - Movie Lookup Table]:");
        for (Map.Entry<Integer, Movie> entry : map.entrySet())
            System.out.println("  Key:" + entry.getKey() + " -> " + entry.getValue());
    }
}

// ─────────────────────────────────────────────
// SECTION 7: BST — Movie Catalogue by ID
// ─────────────────────────────────────────────
class BSTNode {
    Movie movie;
    BSTNode left, right;
    BSTNode(Movie m) { this.movie = m; }
}

class MovieBST {
    private BSTNode root;

    // Insert movie — O(log n) average
    public void insert(Movie m) {
        root = insertRec(root, m);
    }

    private BSTNode insertRec(BSTNode node, Movie m) {
        if (node == null) return new BSTNode(m);
        if (m.id < node.movie.id) node.left = insertRec(node.left, m);
        else if (m.id > node.movie.id) node.right = insertRec(node.right, m);
        return node;
    }

    // Search by ID — O(log n) average
    public Movie search(int id) {
        BSTNode result = searchRec(root, id);
        return result == null ? null : result.movie;
    }

    private BSTNode searchRec(BSTNode node, int id) {
        if (node == null || node.movie.id == id) return node;
        if (id < node.movie.id) return searchRec(node.left, id);
        return searchRec(node.right, id);
    }

    // In-order traversal — prints movies sorted by ID — O(n)
    public void inOrder() {
        System.out.println("\n[BST In-Order - Sorted by ID]:");
        inOrderRec(root);
    }

    private void inOrderRec(BSTNode node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.println("  " + node.movie);
        inOrderRec(node.right);
    }
}

// ─────────────────────────────────────────────
// MAIN CLASS
// ─────────────────────────────────────────────
public class MovieTheatreSystem {

    public static void main(String[] args) {

        // Sample movies
        Movie[] movies = {
            new Movie(101, "Avengers: Endgame",  "Action",   9.0, 50, 350),
            new Movie(102, "The Dark Knight",    "Thriller", 9.2, 30, 400),
            new Movie(103, "Inception",          "Sci-Fi",   8.8, 45, 300),
            new Movie(104, "Interstellar",       "Sci-Fi",   8.6, 20, 320),
            new Movie(105, "RRR",                "Action",   7.9, 55, 280),
            new Movie(106, "KGF Chapter 2",      "Action",   8.2, 35, 300),
        };

        // ══════════════════════════════════════
        // 1. SEARCHING
        // ══════════════════════════════════════
        System.out.println("\n========== SEARCHING ==========");

        // Linear search on original array
        Searching.linearSearch(movies, "Inception");

        // Sort array first, then binary search
        Movie[] sorted = Arrays.copyOf(movies, movies.length);
        Arrays.sort(sorted, (a, b) -> a.title.compareToIgnoreCase(b.title));
        Searching.binarySearch(sorted, "RRR");

        // ══════════════════════════════════════
        // 2. SORTING
        // ══════════════════════════════════════
        System.out.println("\n========== SORTING ==========");

        Movie[] copy1 = Arrays.copyOf(movies, movies.length);
        Sorting.bubbleSort(copy1);

        Movie[] copy2 = Arrays.copyOf(movies, movies.length);
        Sorting.insertionSort(copy2);

        Movie[] copy3 = Arrays.copyOf(movies, movies.length);
        System.out.println("\n[Merge Sort] Sorting alphabetically by title...");
        Sorting.mergeSort(copy3, 0, copy3.length - 1);
        for (Movie m : copy3) System.out.println("  " + m.title);

        // ══════════════════════════════════════
        // 3. LINKED LIST
        // ══════════════════════════════════════
        System.out.println("\n========== LINKED LIST (Bookings) ==========");

        BookingList history = new BookingList();
        history.addBooking(new BookingNode(1, "Thanvi",  "Inception",    2));
        history.addBooking(new BookingNode(2, "Rahul",   "RRR",          3));
        history.addBooking(new BookingNode(3, "Priya",   "KGF Chapter 2",1));
        history.display();

        System.out.println("\n  Cancelling Booking #2:");
        history.cancelBooking(2);
        history.display();

        // ══════════════════════════════════════
        // 4. STACK
        // ══════════════════════════════════════
        System.out.println("\n========== STACK (Recently Viewed) ==========");

        MovieStack viewHistory = new MovieStack();
        viewHistory.view("Avengers: Endgame");
        viewHistory.view("Inception");
        viewHistory.view("Interstellar");
        viewHistory.display();

        System.out.println("\n  Undo last view:");
        viewHistory.undoView();
        viewHistory.display();

        // ══════════════════════════════════════
        // 5. QUEUE
        // ══════════════════════════════════════
        System.out.println("\n========== QUEUE (Ticket Booking) ==========");

        BookingQueue bq = new BookingQueue();
        bq.joinQueue("Thanvi");
        bq.joinQueue("Rahul");
        bq.joinQueue("Priya");
        bq.display();

        System.out.println("\n  Serving customers:");
        bq.serveNext();
        bq.serveNext();
        bq.display();

        // ══════════════════════════════════════
        // 6. HASH MAP
        // ══════════════════════════════════════
        System.out.println("\n========== HASH MAP (Fast Lookup) ==========");

        MovieHashMap hashMap = new MovieHashMap();
        for (Movie m : movies) hashMap.insert(m);
        hashMap.display();

        System.out.println("\n  Search Movie ID 104: " + hashMap.search(104));
        System.out.println("  Search Movie ID 999: " + hashMap.search(999));

        // ══════════════════════════════════════
        // 7. BST
        // ══════════════════════════════════════
        System.out.println("\n========== BST (Movie Catalogue) ==========");

        MovieBST bst = new MovieBST();
        for (Movie m : movies) bst.insert(m);
        bst.inOrder();

        System.out.println("\n  Search ID 105: " + bst.search(105));
        System.out.println("  Search ID 999: " + bst.search(999));

        // ══════════════════════════════════════
        // DONE
        // ══════════════════════════════════════
        System.out.println("\n========================================");
        System.out.println("  All DSA modules completed!");
        System.out.println("========================================");
    }
}