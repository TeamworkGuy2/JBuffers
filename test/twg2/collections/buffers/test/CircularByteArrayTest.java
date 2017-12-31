package twg2.collections.buffers.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.CircularByteArray;

public class CircularByteArrayTest {

	@Test
	public void circularByteArrayTest() {
		CircularByteArray array = new CircularByteArray(5);
		array.add(new byte[] {1, 2}, 0, 2);
		array.add(new byte[] {3, 4, 5, 6, 7}, 2, 3);
		array.remove(3);
		array.add(new byte[] {100, 101, 102}, 0, 3);
		array.remove(4);
		array.add(new byte[] {127}, 0, 1);
		byte[] ary = new byte[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new byte[] { 102, 127 });

		array.add(new byte[] {8, 9, 10, 11, 12}, 0, 5);
		ary = new byte[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new byte[] { 102, 127, 8, 9, 10, 11, 12 });

		array.remove(3);
		array.add(new byte[] {1, 2}, 0, 2);
		array.remove(6);
		array.add(new byte[] {3, 4, 5, 6}, 0, 4);
		array.add(new byte[] {7}, 0, 0);
		array.add(null, 0, 1);
		array.add(new byte[] {7}, 0, 1);
		ary = new byte[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new byte[] { 3, 4, 5, 6, 7 });
	}

}
