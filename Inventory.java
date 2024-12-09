class Inventory {
  private int healthPotionCount;
  private int staminaPotionCount;

  public Inventory() {
      this.healthPotionCount = 0;
      this.staminaPotionCount = 0;
  }

  public void addHealthPotion() {
      healthPotionCount++;
  }

  public void addStaminaPotion() {
      staminaPotionCount++;
  }

  public boolean useHealthPotion(Character character) {
      if (healthPotionCount > 0) {
          character.restoreHP(20);  // Heal 20 HP
          healthPotionCount--;
          return true;
      }
      return false;
  }

  public boolean useStaminaPotion(Character character) {
      if (staminaPotionCount > 0) {
          character.restoreStamina(20);  // Restore 20 stamina
          staminaPotionCount--;
          return true;
      }
      return false;
  }

  public int getHealthPotionCount() {
      return healthPotionCount;
  }

  public int getStaminaPotionCount() {
      return staminaPotionCount;
  }
}