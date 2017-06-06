import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by basescu on 06.06.17.
 */


class Dictionary {
    private static HashSet words;
    private static HashSet prefixes;

    static {
        words = new HashSet();
        prefixes = new HashSet();
        words.add("CAR");
        words.add("CARD");
        words.add("CAT");
        words.add("CART");
        prefixes.add("C");
        prefixes.add("CA");
        prefixes.add("CAR");
        prefixes.add("CARD");
        prefixes.add("CART");
        prefixes.add("CAT");
    }

    static boolean isWord(String str) {
        return words.contains(str);
    }

    static boolean isPrefix(String str) {
        return prefixes.contains(str);
    }

}

class GraphTest {

    char[][] input = {{'A','A','R'}, {'T','C','D'}};
    Graph g = new Graph(2, 3, input);

    @org.junit.jupiter.api.Test
    void createWords() {
        HashSet wordsFound = g.createWords();
        System.out.print(wordsFound);
        if (wordsFound.size() != 3)
            fail("Incorrect number of words found");
        if (!wordsFound.contains("CAR"))
            fail("Incorrect words found");
        if (!wordsFound.contains("CARD"))
            fail("Incorrect words found");
        if (!wordsFound.contains("CAT"))
            fail("Incorrect words found");
    }
}