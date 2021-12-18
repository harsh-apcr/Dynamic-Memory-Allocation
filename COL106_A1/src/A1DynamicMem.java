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

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        Dictionary newMemBlk;
        if (blockSize < 1) return -1;
        if (this.freeBlk == null) return -1;
        else {
            newMemBlk = (this.freeBlk).Find(blockSize ,false);
            if (newMemBlk == null) return -1;
        }
        int adr = newMemBlk.address;
        if (newMemBlk.size > blockSize) {
           freeBlk.Delete(newMemBlk);
           allocBlk.Insert(adr,blockSize,adr);
           freeBlk.Insert(adr+blockSize, newMemBlk.size-blockSize ,newMemBlk.size-blockSize);
        }
        else {
            freeBlk.Delete(newMemBlk);
            allocBlk.Insert(adr,blockSize,adr);
        }
        return newMemBlk.address;
    }


    public int Free(int startAddr) {

        Dictionary allocBlk = this.allocBlk;
        Dictionary freeBlk = this.freeBlk;

        Dictionary newNode = allocBlk.Find(startAddr,true);

        if (newNode == null) return -1;

        else {
            allocBlk.Delete(newNode);
            freeBlk.Insert(newNode.address, newNode.size, newNode.size);
        }
        return 0;
    }

}