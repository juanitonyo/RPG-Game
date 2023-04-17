import java.util.Random;;

public class thePlayer extends player{

    public thePlayer() {
        super.hp = 30;
    }
    
    public void deathBlow(player target) {
        Random rand = new Random();
        double num = rand.nextDouble();
        boolean hit = (num < 0.5) ? true : false;

        if(hit) {
            target.setHp(0);
            target.setAlive(false);;

            System.out.println(this.getName() + " instantly kills " + target.getName() + "! Reducing " + this.getName() + "'s health to half.");

            this.setHp(this.getHp() / 2);
        }
        else {
            System.out.println("Attack missed!");
        }
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
}
