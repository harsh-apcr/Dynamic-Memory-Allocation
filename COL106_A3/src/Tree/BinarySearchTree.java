package Tree;

import DoublyLinkedList.DList;

import java.util.Comparator;
import java.util.Iterator;

public class BinarySearchTree<T> extends BinaryTree<T> implements Iterable<T> {
    protected final Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }


    /**
     * inserts a node with val = `value` into the BST
     * @param value @NotNull, value to insert into BST
     */
    public void insert(T value) {
        if(this.root.getRight() ==null) {
            this.root.setRight(new BinaryTreeNode<>(value));
            this.root.getRight().setParent(root);
        }
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while (true) {
                if (comparator.compare(value, currNode.getValue()) > 0) {
                    if (currNode.getRight() != null) {
                        currNode = currNode.getRight();
                    } else {
                        currNode.setRight(new BinaryTreeNode<>(value));
                        currNode.getRight().setParent(currNode);
                        break;
                    }
                } else {
                    if (currNode.getLeft() != null) {
                        currNode = currNode.getLeft();
                    } else {
                        currNode.setLeft(new BinaryTreeNode<>(value));
                        currNode.getLeft().setParent(currNode);
                        break;
                    }
                }
            }
        }
    }

    /**
     * searches for val = `value` in the BST
     * @param value @NotNull , value to search for
     * @return the first occurrence of value (w.r.t level of tree)
     */
    public BinaryTreeNode<T> search(T value) throws TreeEmptyException,ValueNotFoundException {
        if (this.root.getRight() == null) throw new TreeEmptyException();
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while(currNode!=null) {
                if (currNode.getValue().equals(value)) return currNode;
                else if (comparator.compare(value,currNode.getValue()) > 0) {
                    currNode = currNode.getRight();
                }
                else {
                    currNode = currNode.getLeft();
                }
            }
            throw new ValueNotFoundException();
        }
    }

    /**
     * returns max element in subtree rooted at node
     * @param node @NotNull BinaryTreeNode<T>
     * @return getMax() in subtree rooted at `node`
     */
    private T getMax(BinaryTreeNode<T> node) {
        while(true) {
            if(node.getRight()!=null) {
                node = node.getRight();
            }
            else {
                return node.getValue();
            }
        }
    }

    /**
     * returns min element in subtree rooted at node
     * @param node @NotNull BinaryTreeNode<T>
     * @return getMin() in the subtree rooted at node
     */
    private T getMin(BinaryTreeNode<T> node) {
        while(true) {
            if(node.getLeft()!=null) {
                node = node.getLeft();
            }
            else {
                return node.getValue();
            }
        }
    }


    /**
     * @param value @NotNull
     * @return returns the largest value in BinarySearchTree<T> this with value <= `value`(input)
     */
    public T predecessor(T value) throws TreeEmptyException,ValueNotFoundException {
        BinaryTreeNode<T> currNode = this.search(value);
        if (currNode.getLeft()!=null) {
            return getMax(currNode.getLeft());
        }
        else {
            // find an ancestor of currNode whose right child is also an ancestor
            BinaryTreeNode<T> parNode = currNode.getParent();
            while(parNode!=root && parNode.getLeft() == currNode) {
                currNode = parNode;
                parNode = currNode.getParent();
            }
            if (parNode != root) return parNode.getValue(); // parNode.getRight() == currNode
            else return null;
        }
    }

    /**
     * @param value @NotNull
     * @return returns the largest value in BinarySearchTree<T> this with value > value(input)
     */
    public T successor(T value) throws TreeEmptyException,ValueNotFoundException {
        BinaryTreeNode<T> currNode = this.search(value);
        if (currNode.getRight()!=null) {
            return getMin(currNode.getRight());
        }
        else {
            // find an ancestor of currNode whose left child is also an ancestor
            BinaryTreeNode<T> parNode = currNode.getParent();
            while(parNode!=root && parNode.getRight() == currNode) {
                currNode = parNode;
                parNode = currNode.getParent();
            }
            if (parNode != root) return parNode.getValue(); // parNode.getLeft() == currNode
            else return null;
        }
    }

    /**
     * deletes a value `value` from the tree
     * @param value @NotNull
     */
    public void delete(T value) throws TreeEmptyException , ValueNotFoundException {
        if(this.root.getRight() ==null) throw new TreeEmptyException();
        else {
            BinaryTreeNode<T> currNode = this.root.getRight();
            while(currNode!=null) {
                if (comparator.compare(value, currNode.getValue()) > 0) {
                    currNode = currNode.getRight();
                } else if (comparator.compare(value, currNode.getValue()) < 0) {
                    currNode = currNode.getLeft();
                }
                else {
                    // delete currNode
                    // case 1 - leaf
                    // case 2 - parent of a single child
                    // case 3 - has 2 children
                    // complete the block
                    boolean isRightChild = currNode.getParent().getRight() == currNode;
                    // leaf node (prune it)
                    if (currNode.getRight() == null && currNode.getLeft() == null) {
                        if (isRightChild) currNode.getParent().setRight(null);
                        else currNode.getParent().setLeft(null);
                    }
                    else {
                        // not a leaf node
                        // parent of a single child
                        if (currNode.getRight()!=null && currNode.getLeft() == null) {
                            currNode.getRight()
                                    .setParent(currNode.getParent());
                            if(isRightChild) currNode.getParent().setRight(currNode.getRight());
                            else currNode.getParent().setLeft(currNode.getRight());
                        }
                        // (node.right == null || node.left != null) && (node.right != null || node.left != null)
                        // (A+B')(A'+B') = B'+ AA'= B'
                        // node.left != null
                        else if(currNode.getRight() == null) {
                            currNode.getLeft()
                                    .setParent(currNode.getParent());
                            if(isRightChild) currNode.getParent().setRight(currNode.getLeft());
                            else currNode.getParent().setLeft(currNode.getLeft());
                        }
                        // node.left!=null and node.right != null
                        //has two children
                        // deleting successor
                        else {
                            BinaryTreeNode<T> newNode = currNode.getRight();
                            while(true) {
                                if(newNode.getLeft()!=null) {
                                    newNode = newNode.getLeft();
                                }
                                else {
                                    // newNode.left == null
                                    if (newNode.getRight()!=null) {
                                        newNode.getRight()
                                                .setParent(newNode.getParent());
                                        if (newNode.getParent().getRight() == newNode)
                                            newNode.getParent().setRight(newNode.getRight());
                                        else newNode.getParent().setLeft(newNode.getRight());
                                    }
                                    else {
                                        if (newNode.getParent().getRight() == newNode)
                                            newNode.getParent().setRight(null);
                                        else newNode.getParent().setLeft(null);
                                    }
                                    break;
                                }
                            }
                            currNode.setValue(newNode.getValue());
                        }
                    }
                    return;
                }
            }
            // currNode==null
            throw new ValueNotFoundException();
        }
    }

    // key-invariant property
    public static <E> boolean BSTreeSanityCheck(BinarySearchTree<E> bST) {
        return bST.inOrder().isIncreasing(bST.comparator);
    }


    /**
     * if exact == true then does exact search of value in Tree else does a Best-Fit Search
     * @param value @NotNull
     * @param exact boolean
     * @return BinaryTreeNode<T> of the node found
     */
    public BinaryTreeNode<T> search(T value,boolean exact) throws TreeEmptyException, ValueNotFoundException{
        if (exact) return this.search(value);
        else {
            // find a block with the smallest value such that value >= `value` (input)
            // implements best-fit search
            BinaryTreeNode<T> currNode = this.root.getRight();
            while(currNode!=null) {
                // a.equals(b) == (comparator.compare(a,b) == 0) must hold
                if (comparator.compare(value,currNode.getValue()) > 0) {
                    currNode = currNode.getRight();
                }
                else if (comparator.compare(value, currNode.getValue()) == 0) {
                    return currNode;
                }
                else {
                    if (currNode.getLeft() != null && comparator.compare(currNode.getLeft().getValue(), value) > 0) {
                        currNode = currNode.getLeft();
                    } else {
                        // currNode.getLeft() == null || currNode.getLeft().getValue() < value
                        return currNode;
                    }
                }
            }
            return null;
        }
    }

    public Iterator<T> iterator() {
        DList<T> inorder = this.inOrder();
        return inorder.iterator();
    }






}
