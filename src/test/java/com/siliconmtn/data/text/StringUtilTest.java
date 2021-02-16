package com.siliconmtn.data.text;

// Junit 5
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

// Mockito 3.7.0
import org.mockito.Mock;
import org.mockito.Mockito;

// Space Libs 1.x
import com.siliconmtn.data.tree.Node;


/****************************************************************************
 * <b>Title</b>: StringUtilTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Unit test for the StringUtil class.  Added a mock to
 * test an internal exception thrown in the getToString() method
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 13, 2021
 * @updates:
 ****************************************************************************/
class StringUtilTest {

	@Mock
	Node mockNode = Mockito.mock(Node.class);

	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#everyIndexOf(java.lang.CharSequence, java.lang.CharSequence)}.
	 */
	@Test
	void testEveryIndexOf() {
		int[] indexes = StringUtil.everyIndexOf("james camire", "a");
		assertEquals(2, indexes.length);
		assertEquals(1, indexes[0]);
		assertEquals(7, indexes[1]);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "the");
		assertEquals(2, indexes.length);
		assertEquals(0, indexes[0]);
		assertEquals(20, indexes[1]);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "");
		assertEquals(0, indexes.length);
		
		indexes = StringUtil.everyIndexOf("", "test");
		assertEquals(0, indexes.length);
		
		indexes = StringUtil.everyIndexOf("the time for all of the good things", "exact");
		assertEquals(0, indexes.length);
	}
	
	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#removeNonAlphaNumeric(java.lang.String)}.
	 */
	@Test
	void testRemoveNonAlphaNumeric() {
		assertEquals("silicon", StringUtil.removeNonAlphaNumeric("sili-con"));
		assertEquals("silicon1234", StringUtil.removeNonAlphaNumeric("silicon1234"));
		assertEquals("JamesMary", StringUtil.removeNonAlphaNumeric("James & Mary"));
		assertEquals("", StringUtil.removeNonAlphaNumeric(""));
		assertEquals(null, StringUtil.removeNonAlphaNumeric(null));
	}
	
	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#removeNonAlphaNumeric(java.lang.String, java.lang.Boolean)}.
	 */
	@Test
	void testRemoveNonAlphaNumericStringBoolean() {
		assertEquals("silicon", StringUtil.removeNonAlphaNumeric("sili-con", false));
		assertEquals("silicon1234", StringUtil.removeNonAlphaNumeric("silicon1234", false));
		assertEquals("James  Mary", StringUtil.removeNonAlphaNumeric("James & Mary", false));
		assertEquals("", StringUtil.removeNonAlphaNumeric("", false));
	}
	
	
	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#obfuscateEmail(java.lang.String)}.
	 */
	@Test
	void testObfuscateEmail() {
		assertEquals("j****@siliconmtn.com", StringUtil.obfuscateEmail("james@siliconmtn.com"));
		assertEquals("", StringUtil.obfuscateEmail(""));
	}

	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#removeNonNumeric(java.lang.String)}.
	 */
	@Test
	void testRemoveNonNumeric() {
		assertEquals("12367", StringUtil.removeNonNumeric("123--67"));
		assertEquals(null, StringUtil.removeNonNumeric(null));
	}

	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#getToString(java.lang.Array, char)}.
	 */
	@Test
	public void testGetToStringObjectArrayChar() throws Exception {
		assertEquals("[1,2,3,4]", StringUtil.getToString(new Integer[] {1,2,3,4}, ","));
		assertEquals("[one|two|three]", StringUtil.getToString(new String[] {"one", "two", "three"}, "|"));
		assertEquals("[one^^three]", StringUtil.getToString(new String[] {"one", null, "three"}, "^"));
		assertEquals("", StringUtil.getToString(new String[0], "^"));
	}

	/**
	 * Test method for {@link com.siliconmtn.data.text.StringUtil#getToString(java.lang.Object, java.lang.String)}.
	 */
	@Test
	public void testGetToStringObjectString() throws Exception {
		Node n = new Node("one", "two", "three");
		assertTrue(StringUtil.getToString(n, "|").contains("one"));
		assertTrue(StringUtil.getToString(n, "|").contains("two"));
		assertTrue(StringUtil.getToString(n, "|").contains("three"));
		assertTrue(StringUtil.getToString(n, "|").contains("|"));
		assertFalse(StringUtil.getToString(n, null).contains("|"));
		assertEquals("", StringUtil.getToString(null, null));

		when(mockNode.getNodeId()).thenThrow(new IllegalArgumentException("Test"));
		assertTrue(StringUtil.getToString(mockNode, "|").contains("|"));
	}
}
