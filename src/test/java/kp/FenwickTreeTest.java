package kp;

import junit.framework.Assert;

import org.junit.Test;

public class FenwickTreeTest
{

	@Test
	public void testSimpleAdd()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(1, 1);
		Assert.assertEquals(1, ft.getCumulativeFrequency(1));
	}
	
	@Test
	public void testSimpleAddAtZeroPosition()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(0, 1);
		Assert.assertEquals(1, ft.getCumulativeFrequency(0));
	}

	@Test
	public void testMulipleAdd()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(1, 1);
		ft.addValue(1, 1);
		Assert.assertEquals(2, ft.getCumulativeFrequency(1));
	}

	@Test
	public void testMulipleAddToMulipleValues()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(1, 1);
		ft.addValue(1, 1);
		ft.addValue(2, 1);
		Assert.assertEquals(2, ft.getCumulativeFrequency(1));
		Assert.assertEquals(3, ft.getCumulativeFrequency(2));
		Assert.assertEquals(3, ft.getCumulativeFrequency(3));
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
		Assert.assertEquals(sum, ft.getCumulativeFrequency(1));
		Assert.assertEquals(sum * 2, ft.getCumulativeFrequency(2));
	}

	@Test
	public void testGetIndividualFrequency()
	{
		FenwickTree ft = new FenwickTree(256);
		ft.addValue(1, 1);
		ft.addValue(1, 1);
		ft.addValue(2, 1);
		ft.addValue(9, 4);
		Assert.assertEquals(2, ft.getFrequency(1));
		Assert.assertEquals(1, ft.getFrequency(2));
	}

	@Test
	public void findElementCorrespondingToACumulativeFreq()
	{
		FenwickTree ft = new FenwickTree(10);
		ft.addValue(1, 2);
		ft.addValue(2, 4);
		ft.addValue(3, 8);
		ft.addValue(4, 11);
		ft.addValue(5, 100);

		// just making sure
		Assert.assertEquals(2 + 4 + 8 + 11 + 100, ft.getCumulativeFrequency(5));

		Assert.assertEquals(1, ft.indexOfCumulativeFrequency(2));
		Assert.assertEquals(2, ft.indexOfCumulativeFrequency(2 + 4));
		Assert.assertEquals(3, ft.indexOfCumulativeFrequency(2 + 4 + 8));
		Assert.assertEquals(4, ft.indexOfCumulativeFrequency(2 + 4 + 8 + 11));
		// NOTE: 8 instead of 5 because that's where the algorithm puts it
		Assert.assertEquals(8, ft.indexOfCumulativeFrequency(2 + 4 + 8 + 11 + 100));
	}

	@Test
	public void testRescaling()
	{
		FenwickTree ft = new FenwickTree(10);
		ft.addValue(1, 2);
		ft.addValue(2, 4);
		ft.addValue(3, 8);
		ft.addValue(4, 100);
		ft.addValue(5, 124);

		// before rescaling
		Assert.assertEquals(2, ft.getFrequency(1));
		Assert.assertEquals(4, ft.getFrequency(2));
		Assert.assertEquals(8, ft.getFrequency(3));
		Assert.assertEquals(100, ft.getFrequency(4));

		// rescale
		ft.rescale(2);

		// measure after rescaling
		Assert.assertEquals(1, ft.getFrequency(1));
		Assert.assertEquals(2, ft.getFrequency(2));
		Assert.assertEquals(4, ft.getFrequency(3));
		Assert.assertEquals(50, ft.getFrequency(4));
		Assert.assertEquals(124 / 2, ft.getFrequency(5));
	}

}
