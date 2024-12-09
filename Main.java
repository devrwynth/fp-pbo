public class Main {
  public static void main(String[] args) {
      // Membuat objek Game untuk memulai permainan dengan level 1
      Game game = new Game(1);
      
      // Membuat objek GUI dan mengirimkan objek Game
      GUI gui = new GUI(game);
      
      // Menampilkan jendela GUI permainan
      gui.setVisible(true);
  }
}
