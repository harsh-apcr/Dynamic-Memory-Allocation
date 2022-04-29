// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

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

    public void Defragment() { }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).


    public int Allocate(int blockSize) throws IllegalArgumentException {
        if (blockSize < 1) throw new IllegalArgumentException("BlockSize cannot be less than 1");
        Dictionary freeBlock = this.freeBlk;
        Dictionary allocatedBlock = this.allocBlk;

        Dictionary firstFitBlock = freeBlock.find(blockSize, false);
        if (firstFitBlock == null) return -1;
        else {
            int returnAddr = firstFitBlock.address;
            if (firstFitBlock.size > blockSize) {
                // split the block
                allocatedBlock.insert(firstFitBlock.address, blockSize, firstFitBlock.address);
                freeBlock.insert(firstFitBlock.address+blockSize,
                        firstFitBlock.size-blockSize,
                        firstFitBlock.size-blockSize);
            } else {
                // no need to split the block
                // blockSize == firstFitBlock.size
                allocatedBlock.insert(firstFitBlock.address, blockSize, firstFitBlock.address);
            }
            firstFitBlock.delete(firstFitBlock);    // firstFitBlock is a Dictionary i.e. part of this.freeBlk
            return returnAddr;   // successful allocation
        }
    }


    public int Free(int startAddr) {
        Dictionary freeBlock = this.freeBlk;
        Dictionary allocatedBlock = this.allocBlk;

        // find the block with start address startAddr in the allocated block dictionary
        Dictionary block = allocatedBlock.find(startAddr, true);
        if (block == null) return -1;
        else {
            // block != null
            freeBlock.insert(startAddr, block.size, block.size);
            block.delete(block);    // block is in allocatedBlock (`this` could very well point to this block)
            return 0;   // successful memory free
        }
    }

//     public static void main(String[] args) {
// //        DynamicMem memSystem = new A1DynamicMem(100);
// //        memSystem.Allocate(5);
// //        memSystem.Allocate(10);
// //        memSystem.Allocate(15);
// //        memSystem.Free(5);
// //        memSystem.Free(0);
// //        memSystem.Allocate(12);
// //        Dictionary allocBlk = memSystem.allocBlk;
// //        Dictionary freeBlk = memSystem.freeBlk;
// //
// //        System.out.println("Alloc Block Traversal --------");
// //        for (Dictionary itr = allocBlk.getFirst(); itr != null && itr.key != -1; itr = itr.getNext()) {
// //            System.out.println(itr);
// //        }
// //        System.out.println("Free Block Traversal ---------");
// //        for (Dictionary itr = freeBlk.getFirst(); itr != null && itr.key != -1; itr = itr.getNext()) {
// //            System.out.println(itr);
// //        }

//         DynamicMem memSys = new A1DynamicMem(500);
//         System.out.println(memSys.Allocate(6));

//         System.out.println("Alloc Block Traversal --------");
//         for (Dictionary itr = memSys.allocBlk.getFirst(); itr != null && itr.key != -1; itr = itr.getNext()) {
//             System.out.println(itr);
//         }
//         System.out.println("Free Block Traversal ---------");
//         for (Dictionary itr = memSys.freeBlk.getFirst(); itr != null && itr.key != -1; itr = itr.getNext()) {
//             System.out.println(itr);
//         }
//     }

}