public class Game {
    private Labirin labirin;
    private Character character;
    private int level;
    private int healthPotions;  // Jumlah Health Potion
    private int staminaPotions; // Jumlah Stamina Potion

    public Game(int level) {
        this.level = level;
        this.character = new Character(1, 1); // Karakter mulai dari (1, 1)
        this.labirin = new Labirin(level);
        this.healthPotions = 0;
        this.staminaPotions = 0;
    }

    public Labirin getLabirin() {
        return labirin;
    }

    public Character getCharacter() {
        return character;
    }

    public int getLevel() {
        return level;
    }

    public int getHealthPotions() {
        return healthPotions;
    }

    public int getStaminaPotions() {
        return staminaPotions;
    }

    public void useHealthPotion() {
        if (healthPotions > 0) {
            character.restoreHP(20); // Pulihkan 20 HP
            healthPotions--;
        }
    }

    public void useStaminaPotion() {
        if (staminaPotions > 0) {
            character.restoreStamina(20); // Pulihkan 20 stamina
            staminaPotions--;
        }
    }

    public boolean moveCharacter(int dx, int dy) {
        int x = character.getX() + dx;
        int y = character.getY() + dy;

        if (x >= 0 && x < 10 && y >= 0 && y < 10 && !labirin.isWall(x, y)) {
            character.move(dx, dy);
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return labirin.isFinish(character.getX(), character.getY());
    }
}
