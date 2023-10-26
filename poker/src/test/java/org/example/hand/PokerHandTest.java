package org.example.hand;

import org.example.PokerCombination;
import org.example.exception.PokerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PokerHandTest {

    private PokerHand hand1;
    private PokerHand hand2;
    private PokerHand hand3;
    private PokerHand hand4;
    private PokerHand hand5;
    private PokerHand hand6;
    private PokerHand hand7;
    private PokerHand hand8;
    private PokerHand hand9;
    private PokerHand hand10;
    private List<PokerHand> hands;
    private List<PokerHand> expectedHands;
    private List<PokerHand> actualHands;

    @BeforeEach
    void setUp() {
        //каре
        hand1 = new PokerHand("KS 8C KC KH KD");
        //старшая карта
        hand2 = new PokerHand("KS 2H 5C JD TD");
        //пара
        hand3 = new PokerHand("7C 8C JC TC 7D");
        //две пары
        hand4 = new PokerHand("7C QC 7H QH KD");
        //тройка
        hand5 = new PokerHand("7C 8C 7H TC 7D");
        //стрит
        hand6 = new PokerHand("7C 8C JC TC 9D");
        //флеш
        hand7 = new PokerHand("2C 3C AC 4C 5C");
        //фулл хаус
        hand8 = new PokerHand("2C 2S TH TC TD");
        //стрит флеш
        hand9 = new PokerHand("QC KC JC TC 9C");
        //флеш рояль
        hand10 = new PokerHand("JC TC AC QC KC");

        hands = new ArrayList<>();

        //фулл хаус
        hands.add(new PokerHand("QC 8C JC TC 9C"));
        hands.add(new PokerHand("QC KC JC TC 9C"));
        //каре
        hands.add(new PokerHand("JS 8C JC JH JD"));
        hands.add(new PokerHand("KS 8C KC KH KD"));
        //две пары
        hands.add(new PokerHand("7C QC 7H QH JD"));
        hands.add(new PokerHand("7D QC 7H QH KS"));
        //старшая карта
        hands.add(new PokerHand("7C 8C KH TC 3D"));
        hands.add(new PokerHand("7C 8C AH TC 3D"));

        expectedHands = new ArrayList<>();
        expectedHands.add(hand10);
        expectedHands.add(hand9);
        expectedHands.add(hand8);
        expectedHands.add(hand7);
        expectedHands.add(hand6);
        expectedHands.add(hand5);
        expectedHands.add(hand4);
        expectedHands.add(hand3);
        expectedHands.add(hand2);
        expectedHands.add(hand1);

        actualHands = new ArrayList<>();
        actualHands.add(hand1);
        actualHands.add(hand5);
        actualHands.add(hand8);
        actualHands.add(hand4);
        actualHands.add(hand2);
        actualHands.add(hand6);
        actualHands.add(hand7);
        actualHands.add(hand3);
        actualHands.add(hand9);
        actualHands.add(hand10);
    }

    @Test
    void result_ROYAL_FLUSH() throws PokerException {
        int result = hand10.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(9, result);
    }

    @Test
    void result_STRAIGHT_FLUSH() throws PokerException {
       int result = hand9.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(8, result);
    }

    @Test
    void result_FLUSH() throws PokerException {
       int result = hand7.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(5, result);
    }

    @Test
    void result_FOUR_OF_KIND() throws PokerException {
        int result = hand1.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(7, result);
    }

    @Test
    void result_FULL_HOUSE() throws PokerException {
        int result = hand8.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(6, result);
    }

    @Test
    void result_TWO_PAIRS() throws PokerException {
        int result = hand4.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(2, result);
    }

    @Test
    void result_THREE_OF_KIND() throws PokerException {
        int result = hand5.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(3, result);
    }

    @Test
    void result_PAIR() throws PokerException {
        int result = hand3.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(1, result);
    }

    @Test
    void result_STRAIGHT() throws PokerException {
        int result = hand6.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(4, result);
    }

    @Test
    void result_HIGH_CARD() throws PokerException {
        int result = hand2.result();
        System.out.println(PokerCombination.values()[result]);
        Assertions.assertEquals(0, result);
    }

    @Test
    void compareTo_All() {
        Collections.sort(expectedHands);
        expectedHands.forEach(System.out::println);
        Collections.sort(actualHands);
        System.out.println();
        actualHands.forEach(System.out::println);
        Assertions.assertEquals(expectedHands, actualHands);
    }

    @Test
    void compareTo() {
        Assertions.assertEquals(-1, hands.get(0).compareTo(hands.get(5)));
        Assertions.assertEquals(1, hands.get(0).compareTo(hands.get(1)));
        Assertions.assertEquals(1, hands.get(2).compareTo(hands.get(3)));
        Assertions.assertEquals(1, hands.get(4).compareTo(hands.get(5)));
    }
}