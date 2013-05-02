package kp;

import static org.junit.Assert.*;

import org.junit.Test;

public class FenwickTreeTest
{

	@Test
	public void testSimpleAdd()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(1, 1);
		assertEquals(1, ft.getCumulativeFrequency(1));
	}
	
	@Test
	public void testSimpleAddAtZeroPosition()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(0, 1);
		assertEquals(1, ft.getCumulativeFrequency(0));
	}

	@Test
	public void testMulipleAdd()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(1, 1);
		ft.addValue(1, 1);
		assertEquals(2, ft.getCumulativeFrequency(1));
	}

	@Test
	public void testMulipleAddToMulipleValues()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(0, 1);
		ft.addValue(1, 1);
		ft.addValue(1, 1);
		ft.addValue(2, 1);
		assertEquals(3, ft.getCumulativeFrequency(1));
		assertEquals(4, ft.getCumulativeFrequency(2));
		assertEquals(4, ft.getCumulativeFrequency(3));
	}

	@Test
	public void testLargeNumberOfAdds()
	{
		FenwickTree ft = new FenwickTree(256);
		int sum = 0;
		for (int i = 0; i != 10; ++i)
		{
			sum += i;
			ft.addValue(1, i);
			ft.addValue(2, i);
		}
		assertEquals(sum, ft.getCumulativeFrequency(1));
		assertEquals(sum * 2, ft.getCumulativeFrequency(2));
	}

	@Test
	public void testGetIndividualFrequency()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(0, 1);
		ft.addValue(1, 1);
		ft.addValue(1, 1);
		ft.addValue(2, 1);
		ft.addValue(9, 4);
		assertEquals(2, ft.getFrequency(1));
		assertEquals(1, ft.getFrequency(2));
	}

	@Test
	public void findElementCorrespondingToACumulativeFreq()
	{
		FenwickTree ft = new FenwickTree(6);
		ft.addValue(0, 1);
		ft.addValue(1, 2);
		ft.addValue(2, 4);
		ft.addValue(3, 8);
		ft.addValue(4, 11);
		ft.addValue(5, 100);

		// just making sure
		assertEquals(1 + 2 + 4 + 8 + 11 + 100, ft.getCumulativeFrequency(5));

		assertEquals(-1, ft.indexOfCumulativeFrequency(0));
		assertEquals(0, ft.indexOfCumulativeFrequency(1));
		assertEquals(1, ft.indexOfCumulativeFrequency(1 + 2));
		assertEquals(2, ft.indexOfCumulativeFrequency(1 + 2 + 4));
		assertEquals(3, ft.indexOfCumulativeFrequency(1 + 2 + 4 + 8));
		assertEquals(4, ft.indexOfCumulativeFrequency(1 + 2 + 4 + 8 + 11));
		assertEquals(5, ft.indexOfCumulativeFrequency(1 + 2 + 4 + 8 + 11 + 100));
		assertEquals(5, ft.indexOfCumulativeFrequency(1 + 2 + 4 + 8 + 11 + 100 + 1));
	}
	
	@Test
	public void verifyZeroes() throws Exception
	{
		FenwickTree ft = new FenwickTree(10);
		assertEquals(0, ft.getCumulativeFrequency(0));
		ft.addValue(0, 10);
		assertEquals(10, ft.getCumulativeFrequency(0));
		assertEquals(10, ft.getFrequency(0));
	}

	@Test
	public void testRescaling()
	{
		FenwickTree ft = new FenwickTree(10);

		ft.addValue(0, 10);
		ft.addValue(1, 2);
		ft.addValue(2, 4);
		ft.addValue(3, 8);
		ft.addValue(4, 100);
		ft.addValue(5, 124);

		// before rescaling
		assertEquals(10, ft.getFrequency(0));
		assertEquals(2, ft.getFrequency(1));
		assertEquals(4, ft.getFrequency(2));
		assertEquals(8, ft.getFrequency(3));
		assertEquals(100, ft.getFrequency(4));

		// rescale
		ft.rescale(2);

		// measure after rescaling
		assertEquals(1, ft.getFrequency(1));
		assertEquals(2, ft.getFrequency(2));
		assertEquals(4, ft.getFrequency(3));
		assertEquals(50, ft.getFrequency(4));
		assertEquals(124 / 2, ft.getFrequency(5));
	}

}
