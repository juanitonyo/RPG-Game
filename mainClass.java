import java.util.Scanner;

public class mainClass{

    public int chooseTarget(player [] bandit, int enemyAlive) {
        //Choosing target
        System.out.println("Choose target: ");
        for(int x = 0; x < enemyAlive; x++) {
            System.out.println(bandit[x].getName() + "'s HP: " + bandit[x].getHp());
        }
        
        System.out.print("\nTarget: ");
        Scanner myChoice = new Scanner(System.in);
        int choice = myChoice.nextInt();

        return choice;
    }

    public void gamePlay() {
        player thisPlayer = new player();
        player [] bandit = new player [10];

        //Input player's name
        System.out.print("Enter player's name: ");
        
        Scanner scanName = new Scanner(System.in);
        String myName =  scanName.nextLine();

        thisPlayer.setName(myName);

        //Run this while player is still alve
        while(thisPlayer.isAlive()) {
            int enemyAlive = 0;

            for(int stage = 0; stage < 5; stage++) {
                //Generate opponents
                for(int x = 0; x < stage + 1; x++) {
                    bandit[x] = new player();
                    bandit[x].setName("Bandit" + (x + 1));
                    enemyAlive = x;
                }

                while(enemyAlive > 0 && thisPlayer.isAlive()) {
                    System.out.println("\n+===============+\n");
                    
                    //Display enemy/s health points
                    for(int x = 0; x < enemyAlive; x++) {
                        if(bandit[x].getHp() < 0) {
                            enemyAlive--;
                        }
                        System.out.println(bandit[x].getName() + "'s HP: " + bandit[x].getHp());
                    }
                    
                    //Player Move then Opponent move
                    System.out.print("\n+===============+\nPlayer's HP: " + thisPlayer.getHp() + "\nMove Set: \n 1. Slash\n+===============+\nPick move: ");
                    Scanner move = new Scanner(System.in);
                    int thisMove = move.nextInt();

                    switch(thisMove) {
                        case 1: 
                            int target = chooseTarget(bandit, enemyAlive) - 1;
                            System.out.println(thisPlayer.getName() + " slashed " + bandit[target].getName() + "!\n");
                            thisPlayer.attack(bandit[target]);
                            
                            
                            /*
                             * DITO NA ME MAGDEDECIDE KUNG PANALO OR TALO
                             * NEXT AGENDA
                             *  1. Gumawa ng separate user-defined functions para 'di magulo dito
                             *  2. MATULOG!
                             */
                            
                            
                            break;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }

                    for(int x = 0; x < enemyAlive; x++) {
                        if(bandit[x].getHp() < 0) {
                            continue;
                        }
                        
                        //Attacking system
                        System.out.println(bandit[x].getName() + " attacked " + thisPlayer.getName() + "!\n");
                        bandit[x].attack(thisPlayer);

                        if(thisPlayer.getHp() <= 0) {
                            System.out.println(bandit[x].getName() + " defeated " + thisPlayer.getName() + "!\n\n+== GAME OVER ==+\n\n");
                            thisPlayer.setAlive(false);
                        } 
                    }
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