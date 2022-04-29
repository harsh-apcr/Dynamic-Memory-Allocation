package Tree;

public class BinaryTreeNode<T> {
    private BinaryTreeNode<T> parent,left,right;
    private boolean isBlack;    // for RBTree implementation
    private T value;

    BinaryTreeNode(T value) {
        this.value = value;
        parent = null;
        left = null;
        right = null;
        isBlack = true;
    }

    BinaryTreeNode(T value,boolean blackColor) {
        this.value = value;
        parent = null;
        left = null;
        right = null;
        isBlack = blackColor;
    }

    BinaryTreeNode<T> getParent() {
        return parent;
    }

    BinaryTreeNode<T> getLeft() {
        return left;
    }

    BinaryTreeNode<T> getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }

    void setValue(T value) { this.value = value; }

    void setParent(BinaryTreeNode<T> parent) {
        this.parent = parent;
    }

    void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    boolean isBlack() { return this.isBlack; }

    /**
     * Sets color of node black if setBlack is true
     * @param setBlack indicator variable for whether to set color as black or not
     */
    void setColorBlack(boolean setBlack) { this.isBlack = setBlack;}


}
