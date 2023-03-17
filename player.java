public class player {

    //Player Stats
    public String name = "";
    public int hp = 20;
    public int accuracy = 8;
    public int evade = 6;
    public int attk = 10;
    public int defense = 8;
    public int potion = 3;
    public boolean isAlive = true;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
    public int getEvade() {
        return evade;
    }
    public void setEvade(int evade) {
        this.evade = evade;
    }
    public int getAttk() {
        return attk;
    }
    public void setAttk(int attk) {
        this.attk = attk;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getPotion() {
        return potion;
    }
    public void setPotion(int potion) {
        this.potion = potion;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void attack(player target) {
        if(target.isAlive) {
            System.out.println(this.getName() + " attacked " + target.getName() + "!");

            int min = 1;

            int successHit = (int)Math.floor(Math.random() * (this.getAccuracy() - min + 1) + min);
            int failedHit = (int)Math.floor(Math.random() * (target.getEvade() - min + 1) + min);

            boolean hit = successHit > failedHit;

            if(hit) {
                double damage = (this.getAttk() - target.getDefense()) * (1 + (Math.random() * 0.1 - 0.05));
                target.setHp(target.getHp() - (int)damage);

                if(target.getHp() < 0) {
                    target.setAlive(false);
                }

                System.out.println(this.getName() + " damaged " + target.getName() + " by " + (int)damage);
            }
            else {
                System.out.println("Attack missed!");
            }
        }
        else {
            System.out.println("Target is unattackable.");
        }
    }
}
