// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        if(this == null || this.next == null){
            return null;
        }
        A1List node = new A1List(address, size, key);
        A1List temp = this.next;
        this.next = node;
        node.prev = this;
        node.next = temp;
        temp.prev = node;
        return node;
    }

    public boolean Delete(Dictionary d) 
    {
        A1List temp = this;
        
        if(temp == null){
            return false;
        }
        if(d == null){
            return false;
        }
        while(temp.next != null){
            if(temp.key == d.key && temp.address == d.address && temp.size == d.size){
                if(temp.prev == null || temp.next == null){
                    return false;
                }
                (temp.prev).next = temp.next;
                (temp.next).prev = temp.prev;
                return true;        
            }
            temp = temp.next;
        }
        while(temp.prev != null){
            if(temp.key == d.key && temp.address == d.address && temp.size == d.size){
                if(temp.prev == null || temp.next == null){
                    return false;
                }
                (temp.prev).next = temp.next;
                (temp.next).prev = temp.prev;
                return true;
            }
            temp = temp.prev;
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
        A1List temp = this;
        if(temp == null){
            return null;
        }
        while(temp.prev != null){
            temp = temp.prev;
        }
        temp = temp.next;
        if(exact == true){
            while(temp.next != null){
                if(temp.key == k){
                    return temp;
                }
                temp = temp.next;
            }
        }
        else{
            while(temp.next != null){
                if(temp.key >= k){
                    return temp;
                }
                temp = temp.next;
            }
        }
        
        return null;
    }

    public A1List getFirst()
    {
        A1List temp = this;
        if(temp == null){
            return null;
        }
        while(temp.prev != null){
            temp = temp.prev;
        }

        temp = temp.next;
        if(temp.next == null){
            return null;
        }
        else{
            return temp;
        }
    }
    
    public A1List getNext() 
    {
        A1List temp = this;
        if(temp.next == null){
            return null;
        }
        if((temp.next).next == null){
            return null;
        }
        return temp.next;
    }

    public boolean sanity()
    {
        A1List temp = this;
        if(temp == null){
            return false;
        }
        //Node has no prev or next
        if(temp.prev == null && temp.next == null){
            return false;
        }
        temp = this;
        while(temp.prev != null){
            if((temp.prev).next != temp){
                return false;
            }
            temp = temp.prev;
            if(temp == this){
                return false;
            }
        }
        if(temp.prev == null){
            if(temp.key != -1 || temp.size != -1 || temp.address != -1 || temp.next == null){
                return false;
            }
        }
        temp = this;
        while(temp.next != null){
            if((temp.next).prev != temp){
                return false;
            }
            temp = temp.next;
            if(temp == this){
                return false;
            }
        }
        if(temp.next == null){
            if(temp.key != -1 || temp.size != -1 || temp.address != -1 || temp.prev == null){
                return false;
            }
        }
        return true;
    }
}


