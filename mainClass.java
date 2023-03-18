import java.util.Scanner;

public class mainClass{

    //Show Player and Opponent Status
    public void playerOpponentStatus (thePlayer thisPlayer, player [] bandit, int enemyAlive) {
        System.out.println("\n+===============+\n");

        //Display enemy/s health points
        for(int x = 0; x < enemyAlive; x++) {
            if(bandit[x].getHp() < 0) {
                enemyAlive--;
            }
            System.out.println(bandit[x].getName() + "'s HP: " + bandit[x].getHp());
        }

        System.out.println("\n+===============+\n" + thisPlayer.getName() + "'s HP: " + thisPlayer.getHp() + "\n+===============+");
    }

    //Choosing target. Returns a number if the chosen target is alive, repeat if not.
    public int chooseTarget (player [] bandit, int enemyAlive) {
        //Choosing target
        System.out.println("Choose target: ");
        for(int x = 0; x < enemyAlive; x++) {
            System.out.println(bandit[x].getName() + "'s HP: " + bandit[x].getHp());
        }
        
        System.out.print("\nTarget: ");
        Scanner myChoice = new Scanner(System.in);
        int choice = myChoice.nextInt();

        if(bandit[choice - 1].isAlive == false) {
            System.out.println("Target is dead. Choose another.");
            chooseTarget(bandit, enemyAlive);
        }

        return choice;
    }

    //Shows player move set and at the same chose them
    public void playerMoveSet (thePlayer thisPlayer, player [] bandit, int enemyAlive) {
        System.out.print("Pick move: \n 1. Slash\n 2. Death Blow\n 3. FireBall\n 4. Use HP Potion\n\nMove: ");
        Scanner myChoice = new Scanner(System.in);
        int choice = myChoice.nextInt();

        int target = chooseTarget(bandit, enemyAlive);

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
                playerMoveSet(thisPlayer, bandit, enemyAlive);
                break;
        }
    }

    //Opponent attacks the player and the move will be used is random as long as the opponent is still alive. The opponent will attack accordingly to their names.
    public void opponentsAttack (thePlayer thisPlayer, player [] bandit, int enemyAlive) {
        for(int x = 0; x < enemyAlive; x++) {
            if(bandit[x].isAlive) {
                bandit[x].slash(thisPlayer);
            }
        }
    }

    //Checking the status of the character
    public void checkStatus(player thisCharacter) {
        if(thisCharacter.isBurned()) {
            thisCharacter.setHp(thisCharacter.getHp() - 1);
            thisCharacter.setCounter(thisCharacter.getCounter() - 1);
        }

        if(thisCharacter.getCounter() == 0){
            thisCharacter.isBurned = false;
        }
    }

    public void gamePlay() {
        thePlayer thisPlayer = new thePlayer();
        player [] bandit = new player [10];

        //Input player's name
        System.out.print("Enter player's name: ");
        
        Scanner scanName = new Scanner(System.in);
        String myName =  scanName.nextLine();

        int level = 1;

        thisPlayer.setName(myName);

        //Run this while player is still alve
        while(thisPlayer.isAlive) {

            for(int stage = 0; stage < 5; stage++) {
                int enemyAlive = stage + 1;

                //Generate opponents
                for(int x = 0; x < enemyAlive; x++) {
                    bandit[x] = new player();
                    bandit[x].setName("Bandit" + (x + 1));
                }

                System.out.println("\n+== Level " + level + " Stage  " + (int)(stage + 1) + " ==+\n");

                while(enemyAlive > 0 && thisPlayer.isAlive()) {
                    
                    playerOpponentStatus(thisPlayer, bandit, enemyAlive);
                    
                    playerMoveSet(thisPlayer, bandit, enemyAlive);

                    opponentsAttack(thisPlayer, bandit, enemyAlive);

                    checkStatus(thisPlayer);

                    for(int x = 0; x < stage; x++) {
                        if(bandit[x].isAlive) {
                            checkStatus(bandit[x]);
                        }
                    }

                    //Check if the player is still alive
                    if(thisPlayer.getHp() <= 0) {
                        System.out.println(thisPlayer.getName() + " is defeated!\n\n+== GAME OVER ==+\n\n");
                        thisPlayer.setAlive(false);
                        break;
                    } 

                    for(int x = 0; x < stage; x++) {
                        if(bandit[x].isAlive() == false) {
                            --enemyAlive;
                        }
                    }

                    if(enemyAlive == 0) {
                        System.out.println("\n+== CONGRATULATIONS! YOU CLEARED LEVEL " + level + " STAGE " + (int)(stage + 1) + "! ==+\n");
                    }
                }

                if(thisPlayer.isAlive()) {
                    level++;
                    stage = 0;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        boolean isPlayable = true;
        mainClass objCSelect = new mainClass();

        while(isPlayable){

            System.out.print("+== MAIN MENU ==+\n 1. Play\n 2. Exit\n+===============+\n\nSelect Option: ");
            Scanner myChoice = new Scanner(System.in);
            int choice = myChoice.nextInt();

            switch(choice){
                case 1: 
                    System.out.println("\n+===============+\n");
                    objCSelect.gamePlay();
                    break;
                case 2:
                    isPlayable = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }

        }

        System.exit(0);
    }

    
}