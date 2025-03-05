package com.example.project;
import java.util.ArrayList;
import java.util.Scanner;

public class Game{
    private static String compareRatings(Player p1, Player p2, int occurence, String type){
        int p1Rating = 0, p2Rating = 0;
        if(type.equals("allCards")){
            p1Rating = p1.highestOccurence(occurence);
            p2Rating = p2.highestOccurence(occurence);
        }
        else{
            p1Rating = p1.highestOccurenceHand(occurence);
            p2Rating = p2.highestOccurenceHand(occurence);
        }
        if(p1Rating > p2Rating){
            return "Player 1 wins!";
        }
        else if(p1Rating < p2Rating){
            return "Player 2 wins!";
        }
        return "Tie!";
    }
    
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards){
        int p1HandValue = Utility.getHandRanking(p1Hand); 
        int p2HandValue = Utility.getHandRanking(p2Hand);
        if(p1HandValue > p2HandValue){ //If player 1's one hand is higher than player 2's, player 1 wins
            return "Player 1 wins!";
        }
        else if(p2HandValue > p1HandValue){ //If player 2's one hand is higher than player 1's, player 2 wins
            return "Player 2 wins!";
        }
        else{ //Runs if player 2 and player 1 have same type of hand
            String result = ""; 
            if(p1HandValue == 8 || p1HandValue == 5){ //If the players' hands are full houses or three of a kind, method checks which three cards are higher
                result = compareRatings(p1, p2, 3, "allCards");
                if(!result.equals("Tie!")){
                    return result;
                } //If tie, method continues to the if statement
            } 
            if(p1HandValue == 8 || p1HandValue == 4 || p1HandValue == 3){ //If the players' hands are full houses, three of a kind, or two pairs, method checks which two cards are higher
                result = compareRatings(p1, p2, 2, "allCards");
                if(!result.equals("Tie!")){
                return result;
                } //If tie, method continues to the if statement
            } 
            result = compareRatings(p1, p2, 1, "handOnly"); //Checks players' cards to see which has the higher card in their hand to determine winner 
            return result; //If no one has a higher card, game ends in tie
        }
    }


    /*Draws the ranks for each drawn card
    If the card's rank is 10, one less space is used compared to the other ranks
     */
    private static String drawRank(String rank){
        if(rank.equals("10")){
            return  "| " + 10 + "  |";
        }
        else{
            return "|  " + rank + "  |";
        }
    }

    /*Draws the suits for each drawn card
    Type 0 draws the bottom suit of the card on the left side
    Type 1 draws the top suit of the card on the right side
    */
    private static String drawSuit(String suit, int type){
        if(type == 0){
            return "|" + suit + "    |";
        } 
        else{
            return "|    " + suit + "|";
        }
    }

    //Accepts each players' player object and draws the players two cards side by side
    private static void drawCard(Player player){
        String firstSuit = player.getHand().get(0).getSuit();
        String firstRank = player.getHand().get(0).getRank();
        String secondSuit = player.getHand().get(1).getSuit();
        String secondRank = player.getHand().get(1).getRank();
        System.out.println("              _____      _____");
        System.out.println("             " + drawSuit(firstSuit, 1) + "    " + drawSuit(secondSuit, 1));
        System.out.println("             " + drawRank(firstRank) + "    " + drawRank(secondRank));
        System.out.println("             " + drawSuit(firstSuit, 0) + "    " + drawSuit(secondSuit, 0));
        System.out.println("              ‾‾‾‾‾      ‾‾‾‾‾");
    }

    //Accepts the community cards arraylist and draws a table with the three cards laid out
    private static void drawTable(ArrayList<Card> communityCards){
        String firstSuit = communityCards.get(0).getSuit();
        String firstRank = communityCards.get(0).getRank();
        String secondSuit = communityCards.get(1).getSuit();
        String secondRank = communityCards.get(1).getRank();
        String thirdSuit = communityCards.get(2).getSuit();
        String thirdRank = communityCards.get(2).getRank();
        System.out.println("    --------------------------------------");
        System.out.println("    |           Community Cards          |");
        System.out.println("    |     _____      _____      _____    |");
        System.out.println("    |    " + drawSuit(firstSuit, 1) + "    " + drawSuit(secondSuit, 1) + "    " + drawSuit(thirdSuit, 1) + "   |");
        System.out.println("    |    " + drawRank(firstRank) + "    " + drawRank(secondRank) + "    " + drawRank(thirdRank) + "   |");
        System.out.println("    |    " + drawSuit(firstSuit, 0) + "    " + drawSuit(secondSuit, 0) + "    " + drawSuit(thirdSuit, 0) + "   |");
        System.out.println("    |     ‾‾‾‾‾      ‾‾‾‾‾      ‾‾‾‾‾    |");
        System.out.println("    --------------------------------------");
    }

    public static void play(){ //simulate card playing
        //Opening message and instructions
        System.out.println(" ---------------------------------------------");
        System.out.println("|       Welcome to the Poker Simulation       |");
        System.out.println("|       Press 1 to Start and 0 to Exit        |");
        System.out.println(" ---------------------------------------------");
        Scanner scan = new Scanner(System.in);
        //Boolean to see if simulation should continue running
        boolean running = true;
        //Variables for end summary
        int totalRuns = 0;
        int player1Wins = 0;
        int player2Wins = 0;
        int ties = 0;
        while (running) {
            System.out.print("Enter here: ");
            String input = scan.nextLine(); 

            if (input.equals("1")) {
                totalRuns ++;
                // Creates a deck and shuffles it
                Deck cards = new Deck();
                cards.initializeDeck();
                cards.shuffleDeck();

                // First three cards from deck become the community cards
                ArrayList<Card> communityCards = new ArrayList<>();
                communityCards.add(cards.drawCard());
                communityCards.add(cards.drawCard());
                communityCards.add(cards.drawCard());

                // Creates two players and draws two cards for each of them
                Player p1 = new Player();
                p1.addCard(cards.drawCard());
                p1.addCard(cards.drawCard());
                Player p2 = new Player();
                p2.addCard(cards.drawCard());
                p2.addCard(cards.drawCard());

                // Finds players' hand types and determines the winner
                String p1Result = p1.playHand(communityCards);
                String p2Result = p2.playHand(communityCards);
                String winner = Game.determineWinner(p1, p2, p1Result, p2Result, communityCards);
                if(winner.equals("Player 1 wins!")){
                    player1Wins++;
                }
                else if(winner.equals("Player 2 wins!")){
                    player2Wins++;
                }
                else{
                    ties++;
                }

                //Prints out game results on who won and what hand type each player has
                System.out.println("                  Player 1");
                drawCard(p1);
                drawTable(communityCards);
                System.out.println("                  Player 2");
                drawCard(p2);
                System.out.println("Player 1 Has " + p1Result + ", and Player 2 Has " + p2Result);
                System.out.println("Winner: " + winner);
                input = "";  
            }
            if (input.equals("0")) {
                running = false;  // Exits the loop if the user types 0
                //Ending summary gives information on how many wins each player had, how many ties there were, and how many runs were executed
                System.out.println(" ---------------------------------------------");
                System.out.println("|           Poker Simulation Summary          |"); 
                System.out.println("|                 Total Runs: " + totalRuns + "               |"); 
                System.out.println("| Player 1 Wins: " + player1Wins + ", Player 2 Wins: " + player2Wins + ", Ties: " + ties + " |");
                System.out.println("|               Have a Good Day!              |");
                System.out.println(" ---------------------------------------------");
            }
        }
    }  

    public static void main(String[] args) {
        //Calls play function to run the game in terminal
        play();
    }
}