package twg2.collections.buffers.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.ByteArrayQueue;

/**
 * @author TeamworkGuy2
 * @since 2017-12-31
 */
public class ByteArrayQueueTest {

	@Test
	public void queueTest() {
		byte[] ary = new byte[] { 0, 5, 10, 15, 20, 25, 30, 35, 40 };
		ByteArrayQueue q = new ByteArrayQueue(ary, 2, 5);
		Assert.assertEquals(ary, q.get());
		Assert.assertEquals(2, q.offset());
		Assert.assertEquals(5, q.length());
		q.removeHead(1);
		Assert.assertEquals(3, q.offset());
		Assert.assertEquals(4, q.length());
		q.removeTail(2);
		Assert.assertEquals(3, q.offset());
		Assert.assertEquals(2, q.length());
		q.removeHead(2);
		Assert.assertEquals(5, q.offset());
		Assert.assertEquals(0, q.length());
	}

}
