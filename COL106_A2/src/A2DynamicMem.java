// Class: A2DynamicMem
// Implements Defragment in A2. No other changes should be needed for other functions.

import Tree.*;
import java.util.Comparator;
import DoublyLinkedList.*;


public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and RBTrees.
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and RBTrees.
    //Your BST (and RB tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two.
    public void Defragment() {
        BinarySearchTree<MemoryBlock> defrag =
                new BinarySearchTree<>(Comparator.comparingInt((MemoryBlock blk) -> blk.key));

        this.freeBlk.forEach(memBlk -> defrag.insert(new MemoryBlock(memBlk.address, memBlk.size, memBlk.address)));

        // Defrag is a BST/RB Tree ordered w.r.t address containing memBlocks from freeBlk dictionary
        DList<MemoryBlock> freeBlkList = defrag.inOrder();
        ListIterator<MemoryBlock> it = freeBlkList.iterator();
        MemoryBlock prevNode = null;
        MemoryBlock currNode;
        boolean isFirst = true;
        while(it.hasNext()) {
            currNode = it.next();
            if (isFirst) {
                prevNode = currNode;
                isFirst = false;
            }
            else {
                if (prevNode.address+ prevNode.size == currNode.address) {
                    // prevNode and currNode are contiguous
                    // delete prevNode as well as the currNode from freeBlkList and add merged block
                    MemoryBlock newMemBlk = new MemoryBlock(prevNode.address, prevNode.size+ currNode.size, prevNode.address);
                    it.removePrev();
                    it.insert(newMemBlk);
                    it.remove();

                    prevNode = newMemBlk;
                }
                else {
                    prevNode = currNode;
                }
            }
        }
        // clear the existing tree
        this.freeBlk = new BinarySearchTree<>(Comparator.comparingInt((MemoryBlock blk) -> blk.key));
        freeBlkList.forEach((MemoryBlock blk) -> this.freeBlk.insert(new MemoryBlock(blk.address, blk.size, blk.size)));
    }

    // public static void main(String[] args) {
    //     DynamicMem memSys = new A2DynamicMem(1000000,2);
    //     memSys.Allocate(5);
    //     memSys.Allocate(10);
    //     memSys.Allocate(15);
    //     memSys.Free(5);
    //     memSys.Free(0);
    //     memSys.Defragment();
    //     memSys.Allocate(12);
    //     System.out.println("Free Memory Block -----------");
    //     memSys.freeBlk.inOrder().forEach(System.out::println);
    //     System.out.println("Allocated Memory Block ------");
    //     memSys.allocBlk.inOrder().forEach(System.out::println);
    // }

}