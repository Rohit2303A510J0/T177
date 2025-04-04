import java.util.*;

public class ClassroomMonitoring {
    private static final char ACTIVE = 'A';
    private static final char INACTIVE = 'I';

    private int rows, cols;
    private char[][] grid;
    private boolean[][] visited;

    public ClassroomMonitoring(char[][] inputGrid) {
        this.grid = inputGrid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.visited = new boolean[rows][cols];
    }

    // Using Depth First Search to detect inactivity clusters
    private int dfs(int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols || visited[r][c] || grid[r][c] == ACTIVE)
            return 0;

        visited[r][c] = true;
        int size = 1;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            size += dfs(r + dr[i], c + dc[i]);
        }

        return size;
    }

    // Displaying the classroom layout
    public void printClassroom() {
        System.out.println("\nClassroom Grid:");
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    // Analyze and flag the classroom
    public void analyzeClassroom() {
        int inactiveClusters = 0;
        int maxInactiveSize = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == INACTIVE && !visited[r][c]) {
                    int size = dfs(r, c);
                    inactiveClusters++;
                    maxInactiveSize = Math.max(maxInactiveSize, size);
                }
            }
        }

        printClassroom();
        System.out.println("\nTotal Inactive Clusters: " + inactiveClusters);
        System.out.println("Largest Inactive Cluster Size: " + maxInactiveSize);

        // Flagging logic
        if (maxInactiveSize >= (rows * cols * 0.25)) {
            System.out.println("Result: Class Flagged for Cancellation due to large inactivity cluster.");
        } else if (inactiveClusters >= 3) {
            System.out.println("Result: Class Needs Monitoring due to scattered inactive groups.");
        } else {
            System.out.println("Result: Class is Active and Healthy.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int r = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int c = sc.nextInt();
        sc.nextLine(); 
        char[][] grid = new char[r][c];

        System.out.println("Enter classroom grid (A for Active, I for Inactive):");
        for (int i = 0; i < r; i++) {
            while (true) {
                System.out.print("Row " + (i + 1) + ": ");
                String line = sc.nextLine().trim().replaceAll(" ", "").toUpperCase();

                if (line.length() != c || !line.matches("[AI]+")) {
                    System.out.println("Invalid input. Please enter exactly " + c + " characters of A or I.");
                    continue;
                }

                grid[i] = line.toCharArray();
                break;
            }
        }

        ClassroomMonitoring system = new ClassroomMonitoring(grid);
        system.analyzeClassroom();
        sc.close();
    }
}