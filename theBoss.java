import java.util.Random;

public class theBoss extends player{

    public theBoss() {
        super.hp = 30;
        super.accuracy = 10;
        super.evade = 8;
        super.attk = 11;
        super.defense = 9;
        super.potion = 4;
    }

    public void fireBall(player target) {
        int min = 1;

        int successHit = (int)Math.floor(Math.random() * (this.getAccuracy() - min + 1) + min);
        int failedHit = (int)Math.floor(Math.random() * (target.getEvade() - min + 1) + min);

        boolean hit = successHit > failedHit;

        if(hit) {
            double damage = (this.getAttk() - target.getDefense()) * (1 + (Math.random() * 0.1 - 0.05));
            target.setHp(target.getHp() - (int)damage);

            System.out.println(this.getName() + " burned " + target.getName() + " by " + (int)damage);

            Random rand = new Random();
            double num = rand.nextDouble();
            boolean deb = (num < 0.5) ? true : false;

            if(deb) {
                target.isBurned = true;
                target.setCounter(3);
            }
        
        }
        else {
            System.out.println("Attack missed!");
        }
    }

    public void usePotion() {
        if(this.getPotion() > 0) {
            System.out.println(this.getName() + " uses potion. Adding 5 hp.");
            this.setHp(this.getHp() + 5);
            this.setPotion(this.getPotion() - 1);
        }
        else {
            System.out.println("No potion left.");
        }
    }
    
}
