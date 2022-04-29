# Building a *Dynamic-Memory-Allocator* from scratch

## Static Memory Allocation
> The system knows the amount of memory required in advance.
From this, it can be inferred that memory allocation that would take place while defining an
Array would be static as we specify it’s size earlier.

## Dynamic Memory Allocation
> The system does not exactly know the amount of memory
required. And so in this case, it would get requested for memory Dynamically. Linked Lists
creation is an example of Dynamic Memory Allocation.

We will focus on Dynamic Allocation of Memory

### 1. First Fit Algorithm : 
Here the Allocation system will go through all the Free Memory
blocks, stored using any Data structure (here we focus on DLL, BST and RB Trees), and
first block that is big enough to fulfill the needs of the program will be returned.

### 2. Best Fit Algorithm :
Rather than finding the first adequate memory block, the system will
go through all the free blocks and will return the smallest block which will be big enough to fulfill the needs of the program. Here as it will need to go through the entire data structure
of free blocks so it will be slower (so it is bad in time).

Thus from the above two algorithms, you can see the trade-off between having the fastest algorithm for allocation and having the most efficient space utilisation algorithm for allocation. Note
that along with these two criterion, another criterion which is extremely important for allocation
of memory is that the *free blocks that are **contiguous** should be merged into one larger free block*

For optimal utilization of memory, it is extremely important to have all the free blocks that
are contiguous to be merge as one single larger free block. For example, suppose the total memory
size was 10KB and your program allocates 1KB blocks in a loop that runs ten times. Suppose
the allocate memory blocks were 0 → 1KB and 1KB → 2KB . . . , 9KB → 10KB. After this part
of computation is complete, the program frees all theses ten blocks of memory. Now your free
blocks list will contain ten blocks 0 → 1KB and 1KB → 2KB . . . , 9K → 10KB. Now suppose
your program requests for a memory allocation of 2KB. Even though the entire memory from 0
to 10KB is available, you will be unable to find a free block of size 2KB. This is so because your
memory has been fragmented into smaller sized free blocks and these blocks cannot be reallocated
even though the required contiguous free memory is available. In this case, fragmented memory
is more likely to remain unused and thus it will again lead to wastage to space. This problem is
called **Fragmentation**. Thus initially, the free memory will be long and contiguous. But over the
time of use, these long contiguous regions will be fragmented into smaller and smaller contiguous
areas. A **defragmentor** checks for free blocks that are next to each other and combines them into
larger free blocks. Running a defragmentor periodically reduces the fragmentation of memory and
avoids space wastage.

## Problem Situation
We will assume the memory of the system to be an array of size M (bytes), which is taken as 10 million by default. Each element of this array would be addressed using its index. We take the
size of one memory address as 1 Byte. The Memory Allocation System will be maintaining two
data structures, one for the free blocks and one for the allocated blocks. So initially, the
data structure for allocated blocks will be empty and the data structure for the free blocks will be
having just one element which is the entire memory.

Here, the system will be allocating memory using a variant of First Fit and Best Fit algorithm.
These variants will be called **First Split Fit** and **Best Split Fit** algorithm. Here during the
allocation, these algorithms will split the found free block into two segments; one block of size
k (that is requested by the program) and the other block of the remaining size.

## COL106_A1

* Following 6 functions are implemented `insert`, `delete`, `find`, `getFirst`,
`getNext`, `sanity` of the class `Dictionary` in the file `A1List.java` using **Doubly Linked Lists**.

* In order to write robust program to handle all the corner cases we implement a sanity function. (checks the neccessary invariants for our data-structure) For Ex:

    - If this list gets circular then it must fail the sanity test (or return False).
    -  If the prev of the head node or next of the tail node is not null, then the sanity test should
fail.
    -  For any node, which is not head or tail if node.next.prev != node, then sanity test should
fail.

    It is implemented with only O(1) extra space

* Having completed the Doubly Linked List definition, we will now use this data structure in order
to program our Dynamic Memory Allocation System. For this, we provide a class DynamicMem and
its associated java stub class file DynamicMem.java, which consists of the abstract class definitions
and the specifications of three functions `Allocate`, `Free`, `Defragment`. The Public Memory Array, Free Memory Blocks (FMB) freeBlk and Allocated Memory Blocks (AMB) allocBlk are members of this class.
`Defragment` is not implemented in COL106_A1

* Suppose that n operations have been performed on DLL, worst case complexity of :
    
    - `insert` : O(1) &nbsp; &nbsp; &nbsp; &nbsp; // insertion at the head
    - `delete` : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // search in a DLL
    - `find`   : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // search in a DLL
    - `getFirst` : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // traversing the list
    - `getNext`  : O(1) &nbsp; &nbsp; &nbsp; &nbsp; // `return current_node.next`
    - `sanity`   : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // atmost 2 traversals over the list to check it's sanity

* Suppose that n commands have been given to the memory allocator system, worst case complexity of :

    - `Allocate` : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // traversal over DLL for finding First-Fit block
    - `Free` : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // searching the block with the given address

## COL106_A2

* A complete **Binary Search Tree** implementation is done, implementing `insert`, `delete`, `search`, `getMin`, `iterator`, `successor`, `predecessor`, `BSTreeSanityCheck` and many more.

* `sanity` function checks for the key-invariant of BST i.e. for any node of the BST, `node.left.key <= node.key < node.right`

* We use the previous implementation of `A1DynamicMem.java` (however due to changes in overall structure we only make slight changes) and `Defragment` function is implemented.

* Suppose that n commands have been given to the memory allocator system, worst case complexity of :

    - `Allocate` : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // search for the Best-Fit-Block
    - `Free` : O(n) &nbsp; &nbsp; &nbsp; &nbsp; // searching the block with the given address
    - `Defragment` : O(n<sup>2</sup>) &nbsp; &nbsp; &nbsp; &nbsp; // traversing the free block list (ordered w.r.t key = memBlk.address) only once and combining two contiguous blocks (if found) which takes *O(n)* time, then constructing the tree back which takes *O(n<sup>2</sup>)*.
    Although the actual time complexity would be much less if significant defragmentation had happended

## COL106_A3

* class BinarySearchTree is extended by RBTree to implement **Red Black Tree** and overriding `insert`, `delete` and to check sanity we implemeted `RBTreeSanityCheck`

* `sanity` function checks for:
    - BST-invariant
    - Color-property (Red node cannot have a Red child)
    - Black Height (number of black nodes from leaves to root must be same)
* No changes made to `A1DynamicMem.java` and `A2DynamicMem.java`

* Suppose that n commands have been given to the memory allocator system, worst case complexity of :

    - `Allocate` : O(log n) &nbsp; &nbsp; &nbsp; &nbsp; // search for the Best-Fit-Block (balanced BST)
    - `Free` : O(log n) &nbsp; &nbsp; &nbsp; &nbsp; // searching the block with the given address (balanced BST)
    - `Defragment` : O(n log n) &nbsp; &nbsp; &nbsp; &nbsp; // traversing the free block list (ordered w.r.t key = memBlk.address) only once and combining two contiguous blocks (if found) which takes *O(n)* time, then constructing the tree back which takes *O(log 1) + O(log 2) + ... + O(log n) = O(n log n)*
 
    