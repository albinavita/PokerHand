package org.example;

import org.example.hand.PokerHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App 
{
    public static void main( String[] args ) {


        ArrayList<PokerHand> hands = new ArrayList<>();
        hands.add(new PokerHand(""));
        hands.add(new PokerHand("7C 8C KH "));
        hands.add(new PokerHand("7C 8C KH TC 3D"));
        hands.add(new PokerHand("7C 8C AH TC 3D"));
        hands.add(new PokerHand("JS 8C JC JH JD"));
        hands.add(new PokerHand("KS JC JC JH JD"));
        hands.add(new PokerHand("QC 8C JC TC 9C"));
        hands.add(new PokerHand("2C 2S TH TC TD"));
        hands.add(new PokerHand("7C 8C JC TC 7D"));
        hands.add(new PokerHand("7C QC 7H QH KD"));
        hands.add(new PokerHand("7C 8C JC TC 9D"));
        hands.add(new PokerHand("7C 8C 7H TC 7D"));

        Collections.sort(hands);

        for(PokerHand hand : hands) {
            System.out.println(hand);
        }
    }
    
}
