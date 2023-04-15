import java.util.Arrays;
import java.util.Scanner;

public class mainClass{
    public static Scanner s = new Scanner(System.in);
    public static mainClass objSelect;
    public player[] bandit;
    public thePlayer thisPlayer;
    public theBoss thisBoss; 

    //Show Player and Opponent Status
    public void playerOpponentStatus (thePlayer thisPlayer, player[] bandit, int enemyAlive) {
        System.out.println("\n+===============+\n");

        //Display enemy/s health points
        for(int x = 0; x < enemyAlive; x++) {
            System.out.println((x + 1) + ". " +  bandit[x].getName() + "'s HP: " + bandit[x].getHp());
        }

        System.out.println("\n+===============+\n\n" + thisPlayer.getName() + "'s HP: " + thisPlayer.getHp() + "\n\n+===============+");
    }

    //Shows player move set and at the same chose them
    public void playerMoveSet (thePlayer thisPlayer, player[] bandit, int enemyAlive) {
        mainClass objSelect = new mainClass();

        System.out.print("\nPick move: \n 1. Slash\n 2. Death Blow\n 3. FireBall\n 4. Use HP Potion\n\nMove: ");
        int choice = s.nextInt();
        s.nextLine();

        int target = objSelect.chooseTarget(bandit, enemyAlive);

        switch(choice) {
            case 1:
                thisPlayer.slash(bandit[target - 1]);
                break;
            case 2:
                thisPlayer.deathBlow(bandit[target - 1]);
                break;
            case 3:
                thisPlayer.fireBall(bandit[target - 1]);
                break;
            case 4:
                thisPlayer.usePotion();
                break;
            default:
                System.out.println("Invalid choice. Try Again.");
                objSelect.playerMoveSet(thisPlayer, bandit, enemyAlive);
                break;
        }

        if(bandit[target - 1].getHp() <= 0) {
            bandit[target - 1].setAlive(false);
        }

    }

    //Choosing target. Returns a number if the chosen target is alive, repeat if not.
    public int chooseTarget (player[] bandit, int enemyAlive) {
        boolean isValid = true;
        int choice = 0;

        //Choosing target
        System.out.println("\n+===============+\n\nChoose target: \n");
        for(int x = 0; x < enemyAlive; x++) {
            System.out.println((x + 1) + ". " + bandit[x].getName() + "'s HP: " + bandit[x].getHp());
        }
        
        while(isValid == false) {
            //Input Target
            System.out.print("\nTarget: ");
            choice = s.nextInt();
            s.nextLine();        

            if(choice > enemyAlive || choice < 0) {
                System.out.println("Target unidentified. Choose another.");
                objSelect.chooseTarget(bandit, enemyAlive);
            }
            
            else if(bandit[choice - 1].isAlive() == false) {
                System.out.println("Target is dead. Choose another.");
                objSelect.chooseTarget(bandit, enemyAlive);
            }

            else {
                isValid = true;
            }
        }
        
        return choice;
        
    }

    //Opponent attacks the player and the move will be used is random as long as the opponent is still alive. The opponent will attack accordingly to their names.
    public void opponentsAttack (thePlayer thisPlayer, player [] bandit, int enemyAlive) {
        for(int x = 0; x < enemyAlive; x++) {
            if(bandit[x].isAlive()) {
                bandit[x].slash(thisPlayer);
            }
        }
    }

    //Checking the status of the character
    public void checkStatus(thePlayer thisPlayer, player [] bandit, int enemyAlive) {
       
        if(thisPlayer.isBurned()) {
            thisPlayer.setHp(thisPlayer.getHp() - 1);
            thisPlayer.setCounter(thisPlayer.getCounter() - 1);

            if(thisPlayer.getCounter() == 0) {
                thisPlayer.setBurned(false);
            }
        }

        for(int x = 0; x < enemyAlive; x++) {
            if(bandit[x].isBurned()) {
                bandit[x].setHp(bandit[x].getHp() - 1);
                bandit[x].setCounter(bandit[x].getCounter() - 1);

                if(bandit[x].getCounter() == 0) {
                    bandit[x].setBurned(false);
                }
            }
        }
    }

    public void gamePlay() {
        // call self object to access other functions
        thisPlayer = new thePlayer();
        bandit = new player[5];

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
                for(int x = 0; x < level; x++) {
                    bandit[x] = new player();
                    bandit[x].setName("Bandit " + (x + 1));
                }

                System.out.println("\n+== Level " + level + " Stage  " + stage + " ==+\n");

                while(isNotComplete && thisPlayer.isAlive()) {
                    
                    objSelect.playerOpponentStatus(thisPlayer, bandit, enemyAlive);
                    
                    objSelect.playerMoveSet(thisPlayer, bandit, enemyAlive);

                    objSelect.opponentsAttack(thisPlayer, bandit, enemyAlive);

                    objSelect.checkStatus(thisPlayer, bandit, enemyAlive);

                    //Check if the player is still alive
                    if(thisPlayer.getHp() <= 0) {
                        System.out.println(thisPlayer.getName() + " is defeated!\n\n+== GAME OVER ==+\n\n");
                        thisPlayer.setAlive(false);
                        break;
                    } 

                    //Check all opponents if alive
                    for(int x = 0; x < level; x++) {
                        if(bandit[x].isAlive()) {
                            break;
                        }
                        if(x + 1 == level) {
                            isNotComplete = !isNotComplete;
                        }
                    }

                    if(!isNotComplete) {
                        System.out.println("\n+== CONGRATULATIONS! YOU CLEARED LEVEL " + level + " STAGE " + stage + "! ==+\n");
                        Arrays.fill(bandit, null);
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