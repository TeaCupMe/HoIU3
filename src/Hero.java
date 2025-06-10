public class Hero extends GameObject {
    private String name;
    private int stamina;
    Army army;

    Hero(int x, int y, String name) {
        super(x, y, GameObjectType.GAME_OBJECT_TYPE_HERO);
        this.name = name;
        this.stamina = 0;
    }

    Hero(Army army) {
        super(army.x, army.y, GameObjectType.GAME_OBJECT_TYPE_HERO);
        this.name = "Hero";
        this.stamina = 100;
        this.army = army;
    }



    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }


//    boolean checkIfCanMove(int requiredStamina, int distance) {
//        if (requiredStamina>)
//    }
    @Override
    public String description() {
        return "Hero " + name + ", Stamina: " + stamina;
    }

}
