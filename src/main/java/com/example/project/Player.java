package com.example.project;
import java.util.ArrayList;

public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    //Sets player with an empty hand and no community cards
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    //Returns hand and community cards
    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    //Adds a card to hand 
    public void addCard(Card c){
        hand.add(c);
    }

    /*allCards will add cards from hand and community cards from parameter
    Gets array lists containing rank frequency and suit frequency 
    Turns array lists into strings and uses array lists to count how many pairs there are
    Checks strings and pair count to see which type of hand a player has*/
    public String playHand(ArrayList<Card> communityCards){ 
        allCards = new ArrayList<>();
        allCards.add(hand.get(0));
        allCards.add(hand.get(1));
        for(Card card : communityCards){
            allCards.add(card);
        }
        String suitString = "", rankingString = "", rankingString2 = "";
        int pairCount = 0;
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        ArrayList<Integer> suitFreqency = findSuitFrequency();
        for(int i = 0; i < 4; i ++){
            suitString += suitFreqency.get(i);
        }
        for(int i = 0; i < 13; i ++){
            rankingString += rankFrequency.get(i);
            if(rankFrequency.get(i) == 2){
                pairCount ++;
            }
        }
        rankingString2 = rankingString.substring(4) + rankingString.substring(0, 4); 
        if(rankingString.substring(8).equals("11111") && suitString.indexOf("5") > -1){
            return "Royal Flush";
        }
        else if((rankingString.indexOf("11111") > -1 || rankingString2.indexOf("11111") > -1) && suitString.indexOf("5") > -1){
            return "Straight Flush";
        }
        else if(rankingString.indexOf("4") > -1){
            return "Four of a Kind";
        }
        else if(rankingString.indexOf("3") > -1 && rankingString.indexOf("2") > -1){
            return "Full House";
        }
        else if(suitString.indexOf("5") > -1){
            return "Flush";
        }
        else if(rankingString.indexOf("11111") > -1 || rankingString2.indexOf("11111") > -1){
            return "Straight";
        }
        else if(rankingString.indexOf("3") > -1){
            return "Three of a Kind";
        }
        else if(pairCount == 2){
            return "Two Pair";
        }
        else if(pairCount == 1){
            return "A Pair";
        }
        else if(hand.toString().indexOf("A") > -1){
            return "High Card";
        }
        else{
            return "Nothing";
        }
    }

    //Input a number and get the highest rank that has said number of cards (All cards)
    public int highestOccurence(int cardAmount){
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        for(int i = 12; i >= 0; i --){
            if(rankFrequency.get(i) == cardAmount){
                return i;
            }
        }
        return -1;
    }

    //Input a number and get the highest rank that has said number of cards (Hand cards only)
    public int highestOccurenceHand(int cardAmount){
        ArrayList<Integer> rankFrequencyHand = findRankingFrequencyHand();
        for(int i = 12; i >= 0; i --){
            if(rankFrequencyHand.get(i) == cardAmount){
                return i;
            }
        }
        return -1;
    }

    //Sorts all cards from highest to lowest
    public void sortAllCards(){
        for(int i = 1; i < hand.size(); i ++){
            String currentCard = hand.get(i).getRank();
            int j = i - 1;
            while(j >= 0 && currentCard.compareTo(hand.get(j).getRank()) < 0){
                hand.set(j + 1, hand.get(j));
                j --;
            }
            hand.set(j + 1, hand.get(i));
        }
    } 

    /*Creates an empty array called holderArray with 13 0's
    Loops through each card in hand and increases the index correlating to the card's ranking by one
    Turns array into an arraylist*/
    public ArrayList<Integer> findRankingFrequencyHand(){
        int[] holderArray = new int[13];
        for(Card card : hand){
            int cardRanking = Utility.getRankValue(card.getRank()) - 2;
            holderArray[cardRanking] ++;
        }   
        ArrayList<Integer> rankingFrequencyHand = new ArrayList<>();
        for(int num : holderArray){
            rankingFrequencyHand.add(num);
        }
        return rankingFrequencyHand;
    }

    /*Creates an empty array called holderArray with 13 0's
    Loops through each card in allCards and increases the index correlating to the card's ranking by one
    Turns array into an arraylist*/
    public ArrayList<Integer> findRankingFrequency(){
        int[] holderArray = new int[13];
        for(Card card : allCards){
            int cardRanking = Utility.getRankValue(card.getRank()) - 2;
            holderArray[cardRanking] ++;
        }   
        ArrayList<Integer> rankingFrequency = new ArrayList<>();
        for(int num : holderArray){
            rankingFrequency.add(num);
        }
        return rankingFrequency;
    }

    /*Creates an empty array called holderArray with 4 0's
    Loops through each card in allCards and increases the index correlating to the card's suit by one
    Turns array into an arraylist*/
    public ArrayList<Integer> findSuitFrequency(){
        int[] holderArray = new int[4];
        for(Card card : allCards){
            int cardSuit = Utility.getSuitValue(card.getSuit());
            holderArray[cardSuit] ++;
        } 
        ArrayList<Integer> suitFrequency = new ArrayList<>();
        for(int num : holderArray){
            suitFrequency.add(num);
        }
        return suitFrequency;
    }
   
    @Override
    public String toString(){
        return hand.toString();
    }
}
