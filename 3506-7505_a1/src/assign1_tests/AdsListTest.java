package assign1_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import assign1.AdsList;
import assign1.AdsStack;

public class AdsListTest {

	@Test
	public void testList() {
		/* This class has been provided for you to use if you would like
		 * Create a new method to logically seperate out the different
		 * test cases so its easy to see whats failing and passing.
		 * 
		 * NOTE: Don't forget the @Test annotation above the method name,
		 * junit uses these to find tests to run. 
		 */
		assertTrue("Allways succeeds", true);
	}
	
	@Test
	public void addRemoveTest() {
		/* A simple test trying to add three items to the AdsList and then 
		 * to remove them.
		 */
		AdsList<String> list = new AdsList<String>();
		list = list.add("Hello");
		list = list.add("World");
		list = list.add("!");
		assertEquals("Was expecting !", "!", list.headChop());
		assertEquals("Was expecting World", "World", list.headChop());
		assertEquals("Was expecting Hello", "Hello", list.headChop());
	}
	
	@Test
	public void addRemoveSimpleTest() {
		/* A simple test trying to add three items to the AdsList and then 
		 * to remove them.
		 */
		AdsList<String> list = new AdsList<String>();
		list = list.add("Hello");
		assertEquals("Strings should be equal", "Hello", list.headChop());
	}
	
	@Test
	public void isEmptyTest() {
		/* Test the isEmpty method
		 */
		AdsList<String> list = new AdsList<String>();
		assertEquals("List should be empty at creation", true, list.isEmpty());
		list = list.add("Hello");
		list = list.add("World");
		list = list.add("!");
		assertEquals("List has items, should not be empty", false, list.isEmpty());
	}
	
	@Test
	public void getiTest() {
		/* Add some items to a list and get the item at position 2.
		 * Note: items are added at the start (head) of the list so a 
		 * visualisation of this list could be:
		 * [ "!", "World", "Hello" ]
		 */
		AdsList<String> list = new AdsList<String>();
		list = list.add("Hello");
		list = list.add("World");
		list = list.add("!");
		assertEquals("Strings should be equal", "Hello", list.get(2));
	}
		
}
