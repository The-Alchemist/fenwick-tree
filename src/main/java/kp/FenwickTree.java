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
	/**
	 * The data structure that actually holds the data.
	 * 
	 * Peter Fenwick uses the same variable name in his paper.
	 */
	protected int[] tree;
	/**
	 * Keeps track of the largest index that contains data in the tree variable.
	 * 
	 * The entire tree does not have to be full
	 */
	protected int size;

	public FenwickTree(int N)
	{
		tree = new int[N];
	}

	/**
	 * add val to the k-th element
	 */
	public void addValue(int idx, int val)
	{
		validateIdx(idx);
		int previousIdx = 0;
		do
		{
			tree[idx] += val;
			// add least-significant one
			previousIdx = idx;
			idx += bitAnd(idx, -idx);
		} while (idx < tree.length && idx > 0);
		// update the length; NOTE: must be done at the end of this method because it modifies the idx variable
		this.size = Math.max(this.size, previousIdx);
	}

	private void validateIdx(int idx)
	{
		if (idx < 0)
		{
			throw new IllegalArgumentException("Cannot start with < 1, " + idx);
		}
	}

	/**
	 * get sum of elements 1 thru k
	 */
	public int getCumulativeFrequency(int idx)
	{
		validateIdx(idx);
		int sum = tree[0];
		while (idx > 0)
		{
			sum += tree[idx];
			// remove least significant one
			idx = bitAnd(idx, idx - 1);
		}
		return sum;
	}

	private int bitAnd(int i, int j)
	{
		return i & j;
	}

	/**
	 * Read an individual frequency.
	 * 
	 * @param idx
	 * @return
	 */
	public int getFrequency(int idx)
	{
		validateIdx(idx);
		int value = this.tree[idx];
		if (idx >= 0)
		{
			int parent = bitAnd(idx, idx - 1);
			idx = idx - 1;
			while (parent != idx)
			{
				value -= this.tree[idx];
				idx = bitAnd(idx, idx - 1);
			}
		}
		return value;

	}

	public int indexOfCumulativeFrequency(int freq)
	{
		int idx = 0;
		int mask = this.size;
		if (freq > tree[0])
		{
			while (mask != 0 && freq != 0)
			{
				// get trial index
				final int testIdx = idx + mask;
				final int cumFreq = this.tree[testIdx];
				// we found it already!
				if (freq >= cumFreq)
				{
					// update current index
					idx = testIdx;
					// revise frequency
					freq -= cumFreq;
				}
				mask /= 2;

			}
		}
		// did we accumulate all the frequencies?
		if (freq != 0)
		{
			return -1;
		}
		else
		{
			return idx;
		}
	}

	/**
	 * 
	 * @param scaleFactor
	 *            integer to divide all the propabilities by
	 */
	public void rescale(int scaleFactor)
	{
		for (int i = this.size; i != 0; --i)
		{
			addValue(i, -getFrequency(i) / scaleFactor);
		}
	}
	
	public int size()
	{
		return this.size;
	}
}