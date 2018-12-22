package ch03;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;

public class StringContainsTest {
	private final String s = "yes we have no bananas today";

	@Test
	public void assertTrueFalse() {
		Matcher<String> containsBananas = new StringContains("bananas");
		Matcher<String> containsMangoes = new StringContains("mangoes");

		Assert.assertTrue(containsBananas.matches(s));
		// 왜 실패 했는지 알기 힘들다. 참을 원했지만 거짓이 나왔을 뿐이다.
		// Assert.assertTrue(containsMangoes.matches(s));
	}

	@Test
	public void assertThat() {
		// 왜 실패 했는지 자세히(?) 알려준다.
		// MatcherAssert.assertThat(s, CoreMatchers.containsString("antop"));
	}
}
