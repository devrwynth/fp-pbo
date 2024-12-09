public class Labirin {
  private final int[][] maze;

  // 5 level labirin yang berbeda
  private static final int[][][] mazeTypes = new int[5][10][10];

  static {
      // Level 1
      mazeTypes[0] = new int[][] {
          {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 1, 3, 0, 0, 0, 1},  // Trap di (1,5)
          {1, 0, 1, 0, 1, 0, 1, 1, 0, 1},
          {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
          {1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
          {1, 0, 0, 0, 0, 0, 1, 6, 0, 1},  // Chest di (5,7)
          {1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
          {1, 0, 1, 1, 1, 1, 0, 0, 0, 1},
          {1, 1, 1, 1, 1, 1, 1, 1, 2, 1}
      };

      // Level 2
      mazeTypes[1] = new int[][] {
          {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 1, 0, 0, 3, 0, 1},  // TrapBomb di (1,7)
          {1, 0, 1, 0, 1, 1, 1, 0, 0, 1},
          {1, 0, 1, 0, 0, 1, 1, 0, 0, 1},
          {1, 4, 0, 0, 1, 0, 0, 1, 0, 1},  // TrapMouse di (4,1)
          {1, 1, 1, 0, 1, 0, 1, 0, 6, 1},  // Chest di (5,8)
          {1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
          {1, 1, 1, 1, 1, 5, 1, 0, 0, 1},  // TrapArrow di (7,5)
          {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
          {1, 1, 1, 1, 1, 1, 1, 1, 2, 1}
      };

      // Level 3-5: Sesuaikan level lain dengan pola yang diinginkan
      mazeTypes[2] = mazeTypes[0];
      mazeTypes[3] = mazeTypes[1];
      mazeTypes[4] = mazeTypes[0];
  }

  public Labirin(int level) {
      this.maze = mazeTypes[level - 1];
  }

  public int[][] getMaze() {
      return maze;
  }

  public boolean isWall(int x, int y) {
      return maze[x][y] == 1;
  }

  public boolean isFinish(int x, int y) {
      return maze[x][y] == 2;
  }

  public int getCellType(int x, int y) {
      return maze[x][y];
  }
}