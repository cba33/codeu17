import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by basescu on 06.06.17.
 */
public class BinaryTreeTest {
    private final BinaryTree bt;

    public BinaryTreeTest() {
        this.bt = new BinaryTree(16,
                new BinaryTree(9,
                        new BinaryTree(3,
                                new BinaryTree(1, null, null),
                                new BinaryTree(5, null, null)
                        ),
                        new BinaryTree(14, null, null)
                ),
                new BinaryTree(18,
                        null,
                        new BinaryTree(19, null, null)
                )
        );
    }

    @org.junit.jupiter.api.Test
    void findAncestors() {
        try {
            String ancestors = bt.printAncestors(14);
            assertEquals(ancestors, "9 16");
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            String ancestors = bt.printAncestors(18);
            assertEquals(ancestors, "16");
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            String ancestors = bt.printAncestors(16);
            assertEquals(ancestors, "");
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            String ancestors = bt.printAncestors(2);
            fail("InexistentKeyException not thrown, but key does not exist.");
        } catch(InexistentKeyException e) {
        }
    }

    @org.junit.jupiter.api.Test
    void lowestCommonAncestor() {
        try {
            BinaryTree lca = bt.lowestCommonAncestor(14, 18);
            assertEquals(lca.getInfo(), 16);
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            BinaryTree lca = bt.lowestCommonAncestor(16, 18);
            assertEquals(lca.getInfo(), 16);
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            BinaryTree lca = bt.lowestCommonAncestor(5, 9);
            assertEquals(lca.getInfo(), 9);
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            BinaryTree lca = bt.lowestCommonAncestor(1, 14);
            assertEquals(lca.getInfo(), 9);
        } catch(InexistentKeyException e) {
            fail("InexistentKeyException thrown, but key exists");
        }

        try {
            BinaryTree lca = bt.lowestCommonAncestor(2, 16);
            fail("InexistentKeyException not thrown, but key does not exist.");
        } catch(InexistentKeyException e) {
        }

        try {
            BinaryTree lca = bt.lowestCommonAncestor(19, 4);
            fail("InexistentKeyException not thrown, but key does not exist.");
        } catch(InexistentKeyException e) {
        }

        try {
            BinaryTree lca = bt.lowestCommonAncestor(4, 17);
            fail("InexistentKeyException not thrown, but key does not exist.");
        } catch(InexistentKeyException e) {
        }
    }

}