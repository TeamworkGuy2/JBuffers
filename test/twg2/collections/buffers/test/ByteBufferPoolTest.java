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
		ByteBufferPool pool = new ByteBufferPool(maxPoolSize, bufSize, false);
		List<ByteBuffer> insts = new ArrayList<>();

		while(pool.hasInstance()) {
			insts.add(pool.getInstance());
		}

		Assert.assertEquals(maxPoolSize, insts.size());

		ByteBuffer inst = insts.get(0);
		inst.put((byte)21);
		inst.put((byte)42);
		inst.rewind();
		Assert.assertEquals(21, inst.get());
		Assert.assertEquals(42, inst.get());

		Assert.assertTrue(pool.returnInstance(inst));
		insts.remove(0);

		Assert.assertTrue(pool.hasInstance());

		inst = pool.getInstance();
		insts.add(inst);

		Assert.assertEquals(0, inst.position());
		Assert.assertEquals(bufSize, inst.remaining());
	}

}
