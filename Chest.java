import java.util.Random;

class Chest {
  public boolean containsHealthPotion;
  public boolean containsStaminaPotion;

  public Chest() {
      // Randomize the content of the chest
      Random random = new Random();
      if (random.nextBoolean()) {
          containsHealthPotion = true;
          containsStaminaPotion = false;
      } else {
          containsHealthPotion = false;
          containsStaminaPotion = true;
      }
  }

  public void open(Inventory inventory) {
      if (containsHealthPotion) {
          inventory.addHealthPotion();
      }
      if (containsStaminaPotion) {
          inventory.addStaminaPotion();
      }
  }
}