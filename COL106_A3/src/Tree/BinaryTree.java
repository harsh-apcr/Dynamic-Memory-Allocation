package Tree;
import DoublyLinkedList.*;

public class BinaryTree<T> {
    protected BinaryTreeNode<T> root;

    public BinaryTree() {
        this.root = new BinaryTreeNode<>(null);
    }
    // root is a sentinel node
    // actual content starts from right subtree of root

    public DList<T> preOrder() {
        return preOrderTraversal(this.root.getRight(),new DList<>());
    }


    private DList<T> preOrderTraversal(BinaryTreeNode<T> root,DList<T> nodes) {
        if (root == null) return null;
        else {
            nodes.insertAtLast(root.getValue());
            preOrderTraversal(root.getLeft(),nodes);
            preOrderTraversal(root.getRight(),nodes);
            return nodes;
        }
    }

    public DList<T> postOrder() {
        return postOrderTraversal(this.root.getRight(),new DList<>());
    }

    private DList<T> postOrderTraversal(BinaryTreeNode<T> root,DList<T> nodes) {
        if (root == null) return null;
        else {
            preOrderTraversal(root.getLeft(),nodes);
            preOrderTraversal(root.getRight(),nodes);
            nodes.insertAtLast(root.getValue());
            return nodes;
        }
    }

    public DList<T> inOrder() { return inOrderTraversal(this.root.getRight(),new DList<>()); }

    private DList<T> inOrderTraversal(BinaryTreeNode<T> root,DList<T> nodes) {
        if (root == null) return nodes;
        else {
            inOrderTraversal(root.getLeft(),nodes);
            nodes.insertAtLast(root.getValue());
            inOrderTraversal(root.getRight(),nodes);
            return nodes;
        }
    }

}
