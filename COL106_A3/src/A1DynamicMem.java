// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

import Tree.*;

public class A1DynamicMem extends DynamicMem {

    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {}

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // In A23, Allocate must be implemented using Best-Fit-Split Algorithm
    // for that you must modify your search function with `exact` parameter to return smallest size block >= blockSize (if exact is false)
    public int Allocate(int blockSize) {
        if (blockSize < 1) throw new IllegalArgumentException("BlockSize cannot be less than 1");
        BinarySearchTree<MemoryBlock> freeBlock = this.freeBlk;
        BinarySearchTree<MemoryBlock> allocatedBlock = this.allocBlk;
        MemoryBlock firstFitBlock = null;
        try {
            firstFitBlock = freeBlock.search(new MemoryBlock(0, 0, blockSize), false).getValue();
        } catch(Exception ignored) {}
        // above catch-block is never going to throw an exception

        if (firstFitBlock == null) return -1;
        else {
            if (firstFitBlock.size > blockSize) {
                // split the block
                allocatedBlock.insert(new MemoryBlock(firstFitBlock.address, blockSize, firstFitBlock.address));
                freeBlock.insert(new MemoryBlock(firstFitBlock.address+blockSize,
                        firstFitBlock.size-blockSize,
                        firstFitBlock.size-blockSize));
            } else {
                // no need to split the block
                // blockSize == firstFitBlock.size
                allocatedBlock.insert(new MemoryBlock(firstFitBlock.address, blockSize, firstFitBlock.address));
            }
            try {
                freeBlock.delete(firstFitBlock);    // firstFitBlock is a MemBlk i.e. part of this.freeBlk
            } catch (Exception ignored) {}
            // above try-catch block will never throw an exception as firstFitBlock is present in the freeBlk
            return firstFitBlock.address;   // successful allocation
        }
    }


    public int Free(int startAddr) {

        BinarySearchTree<MemoryBlock> freeBlock = this.freeBlk;
        BinarySearchTree<MemoryBlock> allocatedBlock = this.allocBlk;

        // find the block with start address startAddr in the allocated block dictionaryMemoryBlock block
        MemoryBlock block;
        try {
            block = allocatedBlock.search(new MemoryBlock(0, 0, startAddr), true).getValue();
        } catch (TreeEmptyException | ValueNotFoundException exc) {
            return -1;  // either allocatedBlk is empty or block with addr = startAddr doesn't exist
        }

        // block != null
        freeBlock.insert(new MemoryBlock(block.address, block.size, block.size));
        try {
            allocatedBlock.delete(block);
        } catch (Exception ignored) {}
        // delete the block you found by exact match
        return 0;   // successfully freed the memory

    }

    // public static void main(String[] args) {
    //     DynamicMem memSys = new A2DynamicMem(10000,3);
    //     memSys.Allocate(10);
    //     memSys.Allocate(10);
    //     memSys.Free(0);
    //     memSys.Allocate(20);
    //     memSys.Allocate(10);
    //     memSys.Defragment();
    //     memSys.Allocate(10);
    //     System.out.println("Free Memory Block -----------");
    //     memSys.freeBlk.inOrder().forEach(System.out::println);
    //     System.out.println("Allocated Memory Block ------");
    //     memSys.allocBlk.inOrder().forEach(System.out::println);
    // }

}