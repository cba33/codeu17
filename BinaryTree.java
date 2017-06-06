import java.util.LinkedList;

/**
 * Created by basescu on 29.05.17.
 */


class InexistentKeyException extends Exception {
    @Override
    public String getMessage() {
        return "Key does not exist in tree";
    }
}

public class BinaryTree {
    private final int info;
    private final BinaryTree left;
    private final BinaryTree right;

    public BinaryTree(int info, BinaryTree left, BinaryTree right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

    public int getInfo() {
        return info;
    }

    public String printAncestors(int key) throws InexistentKeyException{
        LinkedList<BinaryTree> ancestors =  this.findAncestors(key);
        if (ancestors.size() == 0) {
            return "";
        }

        /* initialize String Builder with probably enough space - 6 chars per key (including space separator) */
        StringBuilder sb = new StringBuilder(6 * ancestors.size());

        for (BinaryTree crtNode : ancestors) {
            sb.append(crtNode.getInfo());
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    private LinkedList<BinaryTree> findAncestors(int key) throws InexistentKeyException {
        LinkedList<BinaryTree> ancestors = new LinkedList<BinaryTree>();

        if (this.info == key) {
            return ancestors;
        }

        LinkedList leftAncestors = null;
        LinkedList rightAncestors = null;

        /* look for key left and right, continue tree exploration if key not found */
        if (this.left != null) {
            try {
                leftAncestors = this.left.findAncestors(key);
                leftAncestors.add(this);
                return leftAncestors;
            } catch (InexistentKeyException e) {

            }
        }
        if (this.right != null) {
            try {
                rightAncestors = this.right.findAncestors(key);
                rightAncestors.add(this);
                return rightAncestors;
            } catch (InexistentKeyException e) {
            }
        }

        throw new InexistentKeyException();
    }

    public BinaryTree lowestCommonAncestor(int key1, int key2) throws InexistentKeyException {

        /* find ancestors of key 1, take each of them and see if the tree rooted there contains key2 */
        LinkedList<BinaryTree> ancestorsKey1 = findAncestors(key1);

        if(ancestorsKey1.size() == 0) {
            if (this.containsKey(key2)) {
                return this;
            }
            throw new InexistentKeyException();
        }

        for (int i = 0 ; i < ancestorsKey1.size() ; i++) {

            BinaryTree intermedNode = ancestorsKey1.get(i);
            int prevAncestor;
            if (i > 0) {
                prevAncestor = ancestorsKey1.get(i-1).getInfo();
            }
            else {
                prevAncestor = key1;
            }

            if (intermedNode.getInfo() == key2) {
                return intermedNode;
            }


            if (intermedNode.left != null) {
                if (intermedNode.left.getInfo() == prevAncestor) {
                    if (intermedNode.right != null) {
                        if (intermedNode.right.containsKey(key2)) {
                            return intermedNode;
                        }
                    }
                }
            }

            if (intermedNode.right != null) {
                if (intermedNode.right.getInfo() == prevAncestor) {
                    if (intermedNode.left != null) {
                        if (intermedNode.left.containsKey(key2)) {
                            return intermedNode;
                        }
                    }
                }
            }
        }

        throw new InexistentKeyException();
    }

    private boolean containsKey(int key) {
        if (this.getInfo() == key) {
            return true;
        }

        boolean inLeft = false;
        boolean inRight = false;
        if (this.left != null) {
            inLeft = this.left.containsKey(key);
        }
        if (this.right != null) {
            inRight = this.right.containsKey(key);
        }

        return (inLeft || inRight);
    }
}
