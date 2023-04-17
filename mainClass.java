import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class mainClass{

    // Global objects
    public static Scanner s = new Scanner(System.in);
    public static mainClass objSelect = new mainClass();
    public player[] bandit;
    public thePlayer thisPlayer;
    public theBoss thisBoss; 
    public boolean isBossMode = false;
    public int savedVictim = 0;

    //Global Variable


    //Show Player and Opponent Status
    public void playerOpponentStatus (int enemyAlive) {
        System.out.println("\n+===============+\n");

        //Display enemy/s health points
        if(isBossMode) {
            System.out.println(thisBoss.getName() + "'s HP: " + thisBoss.getHp());
        }
        else {
            for(int x = 0; x < enemyAlive; x++) {
                System.out.println((x + 1) + ". " +  bandit[x].getName() + "'s HP: " + bandit[x].getHp());
            }
        }

        System.out.println("\n+===============+\n\nSaved Victim: " + savedVictim + "\n" + thisPlayer.getName() + "'s HP: " + thisPlayer.getHp() + "\n\n+===============+");
    }

    //Shows player move set and at the same chose them
    public void playerMoveSet (int enemyAlive) {

        System.out.print("\nPick move: \n 1. Slash\n 2. Death Blow\n 3. FireBall\n 4. Use HP Potion\n\nMove: ");
        int choice = s.nextInt();
        s.nextLine();

        if(isBossMode) {
            switch(choice) {
                case 1:
                    thisPlayer.slash(thisBoss);
                    if(thisBoss.getHp() <= 0) {
                        thisBoss.setAlive(false);
                    }
                    break;
                case 2:
                    thisPlayer.deathBlow(thisBoss);
                    if(thisBoss.getHp() <= 0) {
                        thisBoss.setAlive(false);
                    }
                    break;
                case 3:
                    thisPlayer.fireBall(thisBoss);
                    if(thisBoss.getHp() <= 0) {
                        thisBoss.setAlive(false);
                    }
                    break;
                case 4:
                    thisPlayer.usePotion();
                    if(thisBoss.getHp() <= 0) {
                        thisBoss.setAlive(false);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try Again.");
                    objSelect.playerMoveSet(enemyAlive);
                    break;
            }
        }

        else {
            if(choice == 4) {
                thisPlayer.usePotion();
            }
            else {
                int target = objSelect.chooseTarget(enemyAlive, choice);

                switch(choice) {
                    case 1:
                        thisPlayer.slash(bandit[target - 1]);
                        if(bandit[target - 1].getHp() <= 0) {
                            bandit[target - 1].setAlive(false);
                        }
                    break;
                    case 2:
                        thisPlayer.deathBlow(bandit[target - 1]);
                        if(bandit[target - 1].getHp() <= 0) {
                            bandit[target - 1].setAlive(false);
                        }
                    break;
                    case 3:
                        thisPlayer.fireBall(bandit[target - 1]);
                        if(bandit[target - 1].getHp() <= 0) {
                            bandit[target - 1].setAlive(false);
                        }
                    break;
                    default:
                        System.out.println("Invalid choice. Try Again.");
                        objSelect.playerMoveSet(enemyAlive);
                        break;
                }
            }
        }
    }

    //Choosing target. Returns a number if the chosen target is alive, repeat if not.
    public int chooseTarget (int enemyAlive, int choice1) {
        boolean isValid = false;
        int choice = 0;

        if(choice1 == 4) {
            return 0;
        }

        //Choosing target
        System.out.println("\n+===============+\n\nChoose target: \n");
        for(int x = 0; x < enemyAlive; x++) {
            System.out.println((x + 1) + ". " + bandit[x].getName() + "'s HP: " + bandit[x].getHp());
        }
        
        while(!isValid) {
            //Input Target
            System.out.print("\nTarget: ");
            choice = s.nextInt();
            s.nextLine();        

            if(choice > enemyAlive || choice < 0) {
                System.out.println("Target unidentified. Choose another.");
                objSelect.chooseTarget(enemyAlive, 0);
            }
            
            else if(!bandit[choice - 1].isAlive()) {
                System.out.println("Target is dead. Choose another.");
                objSelect.chooseTarget(enemyAlive, 0);
            }

            else {
                break;
            }
        }
        
        return choice;
        
    }

    //Opponent attacks the player and the move will be used is random as long as the opponent is still alive. The opponent will attack accordingly to their names.
    public void opponentsAttack (int enemyAlive) {
        if(isBossMode) {
            if(thisBoss.getHp() <= 15) {
                Random rand = new Random();
                double num = rand.nextDouble();
                boolean atk = (num < 0.5) ? true : false;

                if(atk) {
                    double num1 = rand.nextDouble();
                    boolean atk1 = (num1 < 0.5) ? true : false;

                    if(atk1) {
                        thisBoss.slash(thisPlayer);
                    }
                    else {
                        thisBoss.fireBall(thisPlayer);
                    }
                }
                else {
                    thisBoss.usePotion();
                }
            }
            else {
                Random rand = new Random();
                double num = rand.nextDouble();
                boolean atk = (num < 0.5) ? true : false;

                if(atk) {
                    thisBoss.slash(thisPlayer);
                }
                else {
                    thisBoss.fireBall(thisPlayer);
                }
            }
        }

        else {
            for(int x = 0; x < enemyAlive && !isBossMode; x++) {
                if(bandit[x].isAlive()) {
                    bandit[x].slash(thisPlayer);
                }
            }
        }

        if(thisPlayer.getHp() <= 0) {
            thisPlayer.setAlive(false);
        }
    }

    //Checking the status of the character
    public void checkStatus(int enemyAlive) {
       
        if(thisPlayer.isBurned()) {
            thisPlayer.setHp(thisPlayer.getHp() - 1);
            thisPlayer.setCounter(thisPlayer.getCounter() - 1);

            if(thisPlayer.getCounter() == 0) {
                thisPlayer.setBurned(false);
            }
        }

        if(isBossMode && thisBoss.isBurned()) {
            thisBoss.setHp(thisBoss.getHp() - 1);
            thisBoss.setCounter(thisBoss.getCounter() - 1);

            if(thisBoss.getCounter() == 0) {
                thisBoss.setBurned(false);
            }
        }

        else {
            for(int x = 0; x < enemyAlive && !isBossMode; x++) {
                if(bandit[x].isBurned()) {
                    bandit[x].setHp(bandit[x].getHp() - 1);
                    bandit[x].setCounter(bandit[x].getCounter() - 1);
    
                    if(bandit[x].getCounter() == 0) {
                        bandit[x].setBurned(false);
                    }
                }
            }
        }
    }

    public void gamePlay() {
        // call self object to access other functions
        thisPlayer = new thePlayer();
        bandit = new player[100];

        //Input player's name
        System.out.print("Enter player's name: ");
        String myName =  s.nextLine();

        thisPlayer.setName(myName);
        int stage = 1;

        //Run this while player is still alve
        do {
            
            for(int level = 1; level <= 5 && thisPlayer.isAlive(); level++) {
                boolean isNotComplete = true;
                int enemyAlive = level;

                //Generate opponents
                if(level == 5) {
                    isBossMode = !isBossMode;
                    thisBoss = new theBoss();
                    thisBoss.setName("Loki");
                }
                else {
                    for(int x = 0; x < level; x++) {
                        bandit[x] = new player();
                        bandit[x].setName("Bandit " + (x + 1));
                    }
                }

                System.out.println("\n+== Level " + level + " Stage  " + stage + " ==+\n");

                while(isNotComplete && thisPlayer.isAlive()) {
                    
                    objSelect.playerOpponentStatus(enemyAlive);
                    
                    objSelect.playerMoveSet(enemyAlive);

                    objSelect.opponentsAttack(enemyAlive);

                    objSelect.checkStatus(enemyAlive);

                    //Check if the player is still alive
                    if(!thisPlayer.isAlive()) {
                        System.out.println(thisPlayer.getName() + " is defeated!\n\n+== GAME OVER ==+\n\n");
                        break;
                    } 

                    //Check all opponents if alive
                    if(isBossMode && !thisBoss.isAlive()) {
                        isNotComplete = !isNotComplete;
                        isBossMode = !isBossMode;

                        System.out.println("Loki is defeated! You saved 1 victim!");
                        savedVictim++;
                    }
                    else {
                        for(int x = 0; x < enemyAlive && !isBossMode; x++) {
                            if(bandit[x].isAlive()) {
                                break;
                            }
                            if(x + 1 == enemyAlive) {
                                isNotComplete = !isNotComplete;
                            }
                        }
                    }

                    if(!isNotComplete) {
                        System.out.println("\n+== CONGRATULATIONS! YOU CLEARED LEVEL " + level + " STAGE " + stage + "! ==+\n");
                        Arrays.fill(bandit, null);

                        if(level == 5) {
                            level = 1;
                        }
                    }
                }
            }

            stage++;

        }while(thisPlayer.isAlive());

    }
    
    public static void main(String[] args) {
        
        boolean isPlayable = true;

        while(isPlayable){

            System.out.print("+== MAIN MENU ==+\n 1. Play\n 2. Exit\n+===============+\n\nSelect Option: ");
            
            int choice = s.nextInt();
            s.nextLine();

            switch(choice){
                case 1: 
                    System.out.println("\n+===============+\n");
                    objSelect.gamePlay();
                    break;
                case 2:
                    isPlayable = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            
        }
        
        s.close();
        System.exit(0);
    }
    
}