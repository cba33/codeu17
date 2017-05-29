import java.util.InputMismatchException;

/**
 * Created by basescu on 29.05.17.
 */
public class BinaryTree {
    private Integer info;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(int info, BinaryTree left, BinaryTree right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    public Integer getInfo() {
        return info;
    }

    public String printAncestors(Integer key) {
        return this.printAncestors(key, "");
    }

    private String printAncestors(Integer key, String ancestors) {
        if(this.info == key)
            return ancestors;
        if (this.left == null && this.right == null)
            return "";
        StringBuilder sb = new StringBuilder(ancestors.length() + 10);
        sb.append(this.info);
        sb.append(" ");
        sb.append(ancestors);
        String left = "";
        String right = "";

        if(this.left != null) {
           left = this.left.printAncestors(key, sb.toString());
        }
        if(this.right != null) {
            right = this.right.printAncestors(key, sb.toString());
        }

        if(left != "")
            return left;
        return right;
    }

    private BinaryTree lowestCommonAncestor(BinaryTree tree1, BinaryTree tree2) {
        if(tree1 == null || tree2 == null)
            return null;

        BinaryTree leftRes = null;
        BinaryTree rightRes = null;

        if (this.left != null)
            leftRes = this.left.lowestCommonAncestor(tree1, tree2);
        if (this.right != null)
            rightRes = this.right.lowestCommonAncestor(tree1, tree2);
        if(this.left == null)
            return rightRes;
        if(this.right == null)
            return leftRes;

        if(this.left.contains(tree1) && this.right.contains(tree2))
            return this;
        if(this.left.contains(tree2) && this.right.contains(tree1))
            return this;

        if(leftRes != null)
            return leftRes;
        return rightRes;
    }

    private boolean contains(BinaryTree contained) {
        if(this.info == contained.info) {
           if (this.isIdentical(contained))
               return true;
        }

        boolean containedLeft = false;
        boolean containedRight = false;

        if(this.left != null)
            containedLeft = this.left.contains(contained);

        if(this.right != null)
            containedRight = this.right.contains(contained);

        return (containedLeft || containedRight);
    }

    private boolean isIdentical(BinaryTree tree) {
        if(this.info != tree.info)
            return false;
        if(this.left == null && tree.left != null)
            return false;
        if(this.left != null && tree.left == null)
            return false;
        if(this.right == null && tree.right != null)
            return false;
        if(this.right != null && tree.right == null)
            return false;

        boolean leftMatch = true;
        boolean rightMatch = true;
        if(this.left != null) {
            leftMatch = this.left.isIdentical(tree.left);
        }
        if(this.right != null) {
            rightMatch = this.right.isIdentical(tree.right);
        }
        return (leftMatch && rightMatch);
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree(16,
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

        // Question 1
        System.out.println(bt.printAncestors(5));

        // Question 2
        System.out.println(bt.lowestCommonAncestor(new BinaryTree(5, null, null), new BinaryTree(14, null, null)).getInfo());
    }
}
