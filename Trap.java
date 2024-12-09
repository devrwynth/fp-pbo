abstract class Trap {
  protected int damage;

  public int getDamage() {
      return damage;
  }

  public abstract void activate(Character character);
}

class TrapBomb extends Trap {
  public TrapBomb() {
      this.damage = 20; // Bomb trap deals 20 damage
  }

  @Override
  public void activate(Character character) {
      character.reduceHP(damage);
  }
}

class TrapMouse extends Trap {
  public TrapMouse() {
      this.damage = 10; // Mouse trap deals 10 damage
  }

  @Override
  public void activate(Character character) {
      character.reduceHP(damage);
  }
}

class TrapArrow extends Trap {
  public TrapArrow() {
      this.damage = 15; // Arrow trap deals 15 damage
  }

  @Override
  public void activate(Character character) {
      character.reduceHP(damage);
  }
}
