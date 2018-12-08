package ch03;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatalogTest {
	final Catalog catalog = new Catalog();
	final Entry entry = new Entry("fish", "chips");

	// 테스트 픽스처
	@Before
	public void fillTheCatalog() {
		catalog.add(entry);
	}

	@Test
	public void containsAnAddedEntry() {
//		@Before
//		Entry entry = new Entry("fish", "chips");
//		catalog.add(entry);

		assertTrue(catalog.contains(entry));
	}

	@Test
	public void indexesEntriesByName() {
//		@Before
//		Entry entry = new Entry("fish", "chips");
//		catalog.add(entry);

		assertEquals(entry, catalog.entryFor("fish"));
		assertNull(catalog.entryFor("missing name"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void cannotAddTwoEntriesWithTheSameName() {
		catalog.add(new Entry("fish", "chips"));
		catalog.add(new Entry("fish", "peas"));
	}


}
