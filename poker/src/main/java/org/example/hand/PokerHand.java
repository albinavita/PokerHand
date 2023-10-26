package org.example.hand;

import org.example.PokerCombination;
import org.example.exception.PokerException;

import java.util.*;

public class PokerHand implements Comparable<PokerHand>{

        private String gameCards;
        private int number;

        private static final int ORDER_RANK = 4;
        private static final int FOUR_REPEATS = 4;
        private static final int TWO_REPEATS = 2;
        private static final int TEN = 10;
        private static final int COUNT_CARDS = 10;

        public PokerHand(String gameCards) {
            this.gameCards = gameCards;
        }

        /**
         * Четный индекс и ноль - это номинал
         * с нечетными индексами - масть
         * @return возвращаю список строк:
         *  нулевой индекс - это строка с номиналом;
         *  первый индекс - это строка с мастью
         */
        private List<String> parsing() throws PokerException {
            List<String> rankSuit = new ArrayList<>();
            StringBuilder suitBuilder = new StringBuilder();
            StringBuilder rankBuilder = new StringBuilder();
            String str = gameCards.replaceAll(" ", "");
            if ((str.length() < COUNT_CARDS) || (str.length() > COUNT_CARDS)){
                throw new PokerException("Неверный набор или количество карт");
            }
            for (int i = 0; i < str.length(); i++) {
                if ( (i == 0) || (i % 2 == 0)) {
                    rankBuilder.append(str.charAt(i));
                } else {
                    suitBuilder.append(str.charAt(i));
                }
            }
            rankSuit.add(rankBuilder.toString());
            rankSuit.add(suitBuilder.toString());

            return rankSuit;
        }

        /**считаю количество мастей (для выборки флешей)
         * или количество одинакового номинала
         * @param str - или строка номинала
         *            или строка масти
         * @return карту, где ключ - это символ входной строки,
         * а значение это количество повторяющихся символов
         */
        private Map<Character, Integer> numberOfRepet(String str) {
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < str.length(); i++) {
                Integer n = map.get(str.charAt(i));
                if (n == null) {
                    map.put(str.charAt(i), 1);
                } else {
                    map.put(str.charAt(i), ++n);
                }
            }
            return map;
        }

        /**возвращаю порядковый номер силы набора карт
         * чем больше номер, тем сильней рука
         * условие по колличеству мастей:
         * если одна масть, то это какой-то из флешей
         * @return целочисленное значение
         * */
        public int result() throws PokerException {
            List<String> rankSuit = parsing();
            Map<Character, Integer> mapRank = numberOfRepet(rankSuit.get(0));
            Map<Character, Integer> mapSuit = numberOfRepet(rankSuit.get(1));

            List<Integer> sortRanks = sortRank();
            boolean orderRank = isOrderRank(sortRanks);

            if(mapSuit.values().size() == 1) {
                number = getFlesh(orderRank, sortRanks);
            } else {
                number = countRepetRank(orderRank, mapRank);
            }
            return number;
        }

        /**сортировка по номиналу (для проверки идут ли карты по порядку)
         * @return отсортированный список
         */
        private List<Integer> sortRank() throws PokerException {
            List<Integer> ranks = convertToNumber();
            Collections.sort(ranks);
            return ranks;
        }

        /**идут ли карты одна за другой
         * @param sortedRanks отсортированный список
         * @return true or false
         * */
        private boolean isOrderRank(List<Integer>sortedRanks) {
            int count = 0;
            for (int i = 0; i < sortedRanks.size() - 1; i++) {
                if (sortedRanks.get(i + 1).equals(sortedRanks.get(i) + 1)) {
                    count++;
                }
            }
            return (count == ORDER_RANK);
        }

        /**
         * Считаю, что все карты одной масти и выбираю какой из флеш
         * @param orderRank
         * @param sortedRanks
         * @return силу руки в цифровом эквиваленте
         */
        private int getFlesh(boolean orderRank, List<Integer> sortedRanks){
            int result = 0;
            if (isRoyalFrush(orderRank, sortedRanks)){
                result = PokerCombination.ROYAL_FLUSH.ordinal();
            } else
            if(isStraightFrush(orderRank, sortedRanks)){
                result = PokerCombination.STRAIGHT_FLUSH.ordinal();

            } else {
                result = PokerCombination.FLUSH.ordinal();
            }
            return result;
        }

        private boolean isRoyalFrush(boolean orderRank, List<Integer> sortedRanks) {
            return ((orderRank) && (sortedRanks.get(0).equals(TEN)));
        }

        private boolean isStraightFrush(boolean orderRank, List<Integer> sortedRanks) {
            return ((orderRank) && (sortedRanks.get(0) < TEN));
        }

        /**
         * У всех карт разная масть и набор руки зависит от числа записей в карте ranks
         * @param orderRank - номинал карт по порядку
         * @param ranks карта номиналов и колличество идентичных номиналов
         * @return силу руки (чем больше цифра, тем сильней рука)
         */
        private int countRepetRank(boolean orderRank, Map<Character, Integer> ranks) {
            int result = 0;
            if (!ranks.isEmpty()) {
                switch (ranks.size()) {
                    case 2:
                        if (ranks.containsValue(FOUR_REPEATS)) {
                            result = PokerCombination.FOUR_OF_KIND.ordinal();
                        } else {
                            result = PokerCombination.FULL_HOUSE.ordinal();
                        }
                        break;
                    case 3:
                        if (ranks.containsValue(TWO_REPEATS)) {
                            result = PokerCombination.TWO_PAIRS.ordinal();
                        } else {
                            result = PokerCombination.THREE_OF_KIND.ordinal();
                        }
                        break;
                    case 4:
                        result = PokerCombination.PAIR.ordinal();
                        break;
                    case 5:
                        if (orderRank) {
                            result = PokerCombination.STRAIGHT.ordinal();
                        } else {
                            result = PokerCombination.HIGH_CARD.ordinal();
                        }
                        break;
                }
            }
            return  result;
        }

        /**
         * собираю список номинала руки в целочисленном представлении
         * @return список номиналов в целочисленном представлении
         */
        private List<Integer> convertToNumber() throws PokerException {
            String[] world = parsing().get(0).split("");
            List<Integer> ranks = new ArrayList<>();
            for (int i = 0; i < world.length; i++) {
                ranks.add(convertStringToNumber(world[i]));
            }
            return ranks;
        }

        /**
         * меняю буквенные значения на цифру для более удобного
         * подсчета силы руки
         * @param rank строка состоит из одной буквы
         * @return целочисленное представление цифры
         */
        private Integer convertStringToNumber(String rank) {
            int number = 0;
            switch (rank) {
                case "T": number = 10; break;
                case "J": number = 11; break;
                case "Q": number = 12; break;
                case "K": number = 13; break;
                case "A": number = 14; break;
                default: number = Integer.parseInt(rank); break;
            }
            return number;
        }

        /**
         * @return список уникальных номиналов карт отсортированных по убыванию
         */
        private List<Integer> maxCart() throws PokerException {
            Map<Character, Integer> map = numberOfRepet(parsing().get(0));
            List<Integer>letter = new ArrayList<>();
            Map<Character, Integer> sortedMap = new LinkedHashMap<>();

            map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

            for(Map.Entry<Character, Integer> entry : sortedMap.entrySet()) {
                int number = convertStringToNumber(String.valueOf(entry.getKey()));
                letter.add(Integer.valueOf(number));
            }

           return letter;
        }

    /**
     * сравниваю два объекта, если они одной комбинации
     * @param otherHand - другой объект
     * @return если рука не идентичная, то возвращаю более
     */
    private int maxKare(PokerHand otherHand) throws PokerException {
            int result = 0;
            List<Integer> hand1 = otherHand.maxCart();
            List<Integer> hand2 = this.maxCart();
            for (int i = 0; i < hand2.size(); i++) {
                result = hand1.get(i) - hand2.get(i);
                if (result < 0) {
                    result = -1;
                }
                if (result > 0) {
                    result = 1;
                }
                if (result != 0) {
                    return result;
                }
            }
          return result;
        }

        @Override
        public String toString() {
            return "PokerHand{" +
                    "gameCards='" + gameCards + '\'' +
                    " : combination='" + PokerCombination.values()[number] +'\'' +
                    '}';
        }

        @Override
        public int compareTo(PokerHand otherHand) {

           try {
                int hand1 = this.result();
                int hand2 = otherHand.result();

                if (hand1 < hand2) {
                    return 1;
                }
                if (hand1 == hand2) {
                    return maxKare(otherHand);
                }
        } catch (PokerException e) {
        System.err.println(e.getMessage());
    }
            return -1;
        }
    }


