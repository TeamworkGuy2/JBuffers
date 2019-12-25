package twg2.collections.buffers.test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.ByteBufferPool;

/**
 * @author TeamworkGuy2
 * @since 2016-07-01
 */
public class ByteBufferPoolTest {

	@Test
	public void usePoolTest() throws Exception {
		int maxPoolSize = 3;
		int bufSize = 2;
		ByteBufferPool pool = new ByteBufferPool(maxPoolSize, bufSize + 1, false, null);
		List<ByteBuffer> insts = new ArrayList<>();

		Assert.assertEquals(bufSize + 1, pool.getBufferByteSize());
		pool.setBufferByteSize(bufSize);
		Assert.assertEquals(bufSize, pool.getBufferByteSize());

		while(pool.hasInstance()) {
			insts.add(pool.getInstance());
		}

		Assert.assertEquals(maxPoolSize, insts.size());
		Assert.assertEquals(bufSize, pool.getBufferByteSize());
		try {
			pool.getInstance();
			Assert.fail("espected full pool fail");
		} catch(Exception ex) {
			// success
		}

		ByteBuffer inst1 = insts.get(0);
		inst1.put((byte)21);
		inst1.put((byte)42);
		inst1.rewind();
		Assert.assertEquals(21, inst1.get());
		Assert.assertEquals(42, inst1.get());

		Assert.assertTrue(pool.returnInstance(inst1));

		ByteBuffer inst2 = insts.get(1);
		Assert.assertTrue(pool.returnInstance(inst2));

		Assert.assertTrue(pool.hasInstance());
		Assert.assertEquals(1, pool.getInUseCount());

		inst1 = pool.getInstance();
		Assert.assertEquals(insts.get(0), inst1);
		Assert.assertEquals(0, inst1.position());
		Assert.assertEquals(bufSize, inst1.remaining());

		Assert.assertTrue(pool.hasInstance());
		Assert.assertEquals(2, pool.getInUseCount());

		inst2 = pool.getInstance();
		Assert.assertEquals(insts.get(1), inst2);
		Assert.assertEquals(0, inst2.position());
		Assert.assertEquals(bufSize, inst2.remaining());

		Assert.assertFalse(pool.hasInstance());
		Assert.assertEquals(3, pool.getInUseCount());
	}

}
