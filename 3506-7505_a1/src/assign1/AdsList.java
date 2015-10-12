package assign1;

/*
 * This class implements the List ADT.
 * A list is represented by a head (the first element of the list) and a tail (the list of the rest of the elements).
 */

public class AdsList<E> {

	private E head;
	private AdsList<E> tail;
	private E currentHead;

	// create an empty list
	public AdsList() {
		this.head = null;
		this.tail = null;
	}

	private AdsList(E head, AdsList<E> tail) {
		this.head = head;
		this.tail = tail;
	}

	// return the list where the element was added
	public AdsList<E> add(E element) {
		return new AdsList<E>(element, this);
		// Error 1: the tail will always be null.
	}

	public boolean isEmpty() {
		return (head == null && tail == null);
	}

	// remove and return the head (first element)
	public E headChop() {
		currentHead = this.head;
		this.head = this.tail.headPeek();
		this.tail = this.tail.getTail();
		// Error 2: new AdsList's head will always be null.	
		return currentHead;
	}

	// return the first element without removing it from the list
	public E headPeek() {
		return head;
	}

	public AdsList<E> getTail() {
		return tail;
	}

	// return the element in position i in the list without removing it
	public E get(int i) {
		if (i == 0)
			return head;
		return tail.get(i - 1);
	}

}
