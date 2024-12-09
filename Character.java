public class Character {
  private int x, y;
  private int hp;
  private int stamina;
  private Inventory inventory;

  public Character(int x, int y) {
      this.x = x;
      this.y = y;
      this.hp = 50;
      this.stamina = 50;
      this.inventory = new Inventory();
  }

  public int getX() {
      return x;
  }

  public int getY() {
      return y;
  }

  public int getHP() {
      return hp;
  }

  public int getStamina() {
      return stamina;
  }

  public void move(int dx, int dy) {
      this.x += dx;
      this.y += dy;
      this.stamina = Math.max(0, this.stamina - 1);
  }

  public void reduceHP(int amount) {
      this.hp = Math.max(0, this.hp - amount);
  }

  public void restoreHP(int amount) {
      this.hp = Math.min(50, this.hp + amount);  // Max HP is 50
  }

  public void restoreStamina(int amount) {
      this.stamina = Math.min(50, this.stamina + amount);  // Max Stamina is 50
  }

  public Inventory getInventory() {
      return inventory;
  }

  public boolean isAlive() {
      return hp > 0;
  }

  public boolean isExhausted() {
      return stamina < 1;
  }
}