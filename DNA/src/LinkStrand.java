import java.util.List;

public class LinkStrand implements IDnaStrand {

    private class Node {
        String info;
        Node next;

        Node(String s, Node n) {
            info = s;
            next = n;
        }
    }

    private Node myFirst; //references the first node in a linked list of nodes.
    private Node myLast; //references the last node in a linked list of nodes.
    private long mySize; //represents the total number of characters stored in all nodes together.
    private int myAppends; //is the number of times that the append method has been called.
    private int myIndex = 0; //tracks the last character we accessed with the charAt() method
    private Node myCurrent = myFirst; // tracks the last node holding the character at position myIndex
    private int myLocalIndex = 0; //tracks the last character we accessed within the Node


	public LinkStrand(){
        this("");
	}

    	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 * 
	 * @param s
	 *            is the source of cgat data for this strand
	 */

    public LinkStrand(String s) {
		initialize(s);
	}

    @Override 
	public void initialize(String source) {
		Node node = new Node(source, null);
        myFirst = node;
        myLast = node;
        mySize = node.info.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;


	}


    @Override
    public long size() {
        return mySize;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        mySize += dna.length();
        myAppends += 1;
        myLast.next = new Node(dna, null);
        myLast = myLast.next;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node node = myFirst;
		while (node != null) {
            str.append(node.info);
            node = node.next;
        }
        return str.toString();
	}

    private Node add(String s) {
        Node rev = new Node(s, myFirst);
        myFirst = rev;
        mySize += s.length();
        return rev;
    }

    @Override
    public IDnaStrand reverse() {
        LinkStrand reverse = new LinkStrand();
        Node curr = myFirst;
        while (curr != null) {
            StringBuilder copy = new StringBuilder(curr.info);
            reverse.add(copy.reverse().toString());
            curr = curr.next;
        }

        return reverse;
        
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index > mySize - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (myIndex > index) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }

        while (myIndex != index) {
            myIndex += 1;
            myLocalIndex += 1;

            if (myLocalIndex >= myCurrent.info.length()) {
                myCurrent = myCurrent.next;
                myLocalIndex = 0;

            }
        }

        return myCurrent.info.charAt(myLocalIndex);
        
    
}
}
