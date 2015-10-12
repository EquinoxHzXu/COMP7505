package assign1_tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import assign1.AdsStack;

public class AdsStackTest {

	@Test
	public void testCount() {
		/***************************************************************
		 * 							TEST 1
		 *                  Test AdsStack.count()
		 ***************************************************************/
		// Add four elements and count them
		AdsStack<String> stack1 = new AdsStack<String>();
		System.out.println(stack1.count());  // Ensure it starts off empty
		
		stack1.push("bonjour");
		stack1.push("hello");
		stack1.push("guten tag");
		stack1.push("hola");
		
		// Ensure the stack is the correct size.
		assertEquals(4, stack1.count());
	}
	
	@Test
	public void testSimplePushPop() {
		/***************************************************************
		 * 							TEST 2
		 *          Simple AdsStack.push() and AdsStack.pop()
		 ***************************************************************/
		AdsStack<Integer> numberStack = new AdsStack<Integer>();
		numberStack.push(5);
		int res = numberStack.pop();
		assertEquals("Stack should have returned 5", 5, res);
		assertEquals("Stack should be empty", 0, numberStack.count());
		
		
	}
	
	@Test
	public void testComplexPushPop(){
		/***************************************************************
		 * 							TEST 2
		 * 			Complex AdsStack.push() and AdsStack.pop()
		 ***************************************************************/		
		// put the letters in a stack and then pop and print them
		AdsStack<Character> stack2 = new AdsStack<Character>();
		String favFood = "Guacamole";
		String favFoodReversed = "elomacauG";
		for (int i = 0; i < favFood.length(); i++) {  // Add chars to stack
			stack2.push(favFood.charAt(i));
		}
		String result = "";
		while(stack2.count() != 0){  // while stack not empty, pop and save items.
			result += stack2.pop();
		}
		assertEquals("Items coming from stack don't match what was added", favFoodReversed, result);
		
	}

}
