/**
 * 
 */
package kp;

/**
 * 
 * From "A New Data Structure for Cumulative Frequency Tables" by Peter Fenwick (1994)
 * 
 * @author kpietrzak
 * 
 */
public class FenwickTree
{

	private int[] tree;
	
	private int largestRegion;
	
	public FenwickTree(int size)
	{
		tree = new int[size + 1];
		largestRegion = Integer.highestOneBit(size);
	}
	
	/**
	 * Add or remove values at index.  Negative value must be attached to 
	 * a previously positive value of equal or greater size, to ensure validity
	 * of tree.
	 * @param index
	 * @param value
	 */
	public void addValue(int index, int value)
	{
		++index;
		while (index < tree.length)
		{
			tree[index] += value;
			index += Integer.lowestOneBit(index);
		}
	}
	
	public int indexAtCumulativeFreqSmallest(int freq)
	{
		return indexAtCumulativeFreqGreatest(freq - 1) + 1;
	}
	
	/**
	 * Greatest index with cumulative frequency given.  No guarantees on frequencies
	 * outside of range.
	 * @param freq
	 * @return
	 */
	public int indexAtCumulativeFreqGreatest(int freq)
	{
		int index = 0;
		int mask = largestRegion;
		while (mask != 0)
		{
			int newIndex = index + mask;
			if (newIndex < tree.length && freq >= tree[newIndex])
			{
				index = newIndex;
				freq -= tree[newIndex];
			}
			mask /= 2;
		}
		return index - 1;
	}
	
	/**
	 * get Cumulative Frequency, from 0 to index, inclusive
	 * @param index
	 * @return
	 */
	public int getCumulativeFreq(int index)
	{
		++index;
		int sum = 0;
		while (index != 0)
		{
			sum += tree[index];
			index -= Integer.lowestOneBit(index);
		}
		return sum;
	}
}
