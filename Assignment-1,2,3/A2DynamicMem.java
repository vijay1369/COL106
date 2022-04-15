// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        Dictionary new_tree;
        if(type == 2){
            new_tree = new BSTree();
        }
        else{
            new_tree = new AVLTree();
        }
        for(Dictionary d = freeBlk.getFirst(); d != null; d = d.getNext()){
            new_tree.Insert(d.address, d.size, d.address);
        }
        Dictionary temp = new_tree.getFirst();
        Dictionary to_del = temp;
        Dictionary to_del2;
        while(temp != null){
            int start = temp.address;
            int size = temp.size;
            while(temp != null){
                if(temp.getNext() != null){
                    if(temp.address + temp.size == (temp.getNext()).address){
                        to_del2 = temp;
                        to_del.address = temp.address;
                        to_del.key = temp.size;
                        to_del.size = temp.size;
                        temp = temp.getNext();
                        size = size + temp.size;
                        freeBlk.Delete(to_del);
                        new_tree.Delete(to_del2);
                    }
                    else{
                        break;
                    }
                }
                else{
                    break;
                }
                
            }
            if(temp == null){
                return;
            }
            to_del2 = temp;
            to_del.address = temp.address;
            to_del.key = temp.size;
            to_del.size = temp.size;
            temp = temp.getNext();
            freeBlk.Delete(to_del);
            new_tree.Delete(to_del2);
            freeBlk.Insert(start, size, size);
        }
        return ;
    }
}