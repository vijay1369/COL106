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
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the dictionary class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        if(blockSize <= 0){
            return -1;
        }
        Dictionary req = freeBlk.Find(blockSize, false);
        if(req == null){
            return -1;
        }
        freeBlk.Delete(req);
        if(req.size > blockSize){
            int new_size = req.size - blockSize;
            int new_address = req.address + blockSize;
            freeBlk.Insert(new_address, new_size, new_size);
            
        }
        allocBlk.Insert(req.address, blockSize, req.address);
        return req.address;
    } 
    // return 0 if successful, -1 otherwise
    public int Free(int startAddr) {
        Dictionary req = allocBlk.Find(startAddr, true);
        if(req == null){
            return -1;
        }
        allocBlk.Delete(req);
        freeBlk.Insert(req.address, req.size, req.size);
        return 0;
    }
}