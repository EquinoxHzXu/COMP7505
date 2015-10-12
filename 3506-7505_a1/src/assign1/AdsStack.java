package assign1;

/*
 * This class implement the Stack ATD using the AdsList type.
 */

public class AdsStack<E> {

	private AdsList<E> stack;

	public AdsStack() {
		this.stack = new AdsList<E>();
		// Error 1: Type mismatch - cannot convert AdsList<Integer> to
		// AdsList<E>. The constructor should create the same abstract data type
		// as defined before.
	}

	public void push(E element) {
		this.stack = this.stack.add(element);
		// Error 2: the private valuable "stack" would not receive the element.
	}

	public E pop() {
		return stack.headChop();
		// Error 3: get(i) would not remove the element at the position of i.
	}

	// count the element in the stack: move all the elements to another storage
	// stack (incrementing a counter), then put them back in the stack and
	// output the counter.
	public int count() {
		AdsStack<E> storage = new AdsStack<E>();
		int count = 0;
		while (!stack.isEmpty()) {
			storage.push(this.pop());
			count++;
		}
		for (int i = 0; i < count; i++)
			this.push(storage.pop());
		return count;
	}
}
