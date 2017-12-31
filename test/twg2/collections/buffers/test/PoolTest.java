package twg2.collections.buffers.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.Pool;

/**
 * @author TeamworkGuy2
 * @since 2016-07-01
 */
public class PoolTest {

	@Test
	public void usePoolTest() throws Exception {
		int maxPoolSize = 3;
		Pool<StringBuilder> pool = new Pool<StringBuilder>(maxPoolSize, () -> new StringBuilder(), (sb) -> sb.setLength(0));
		List<StringBuilder> insts = new ArrayList<>();

		while(pool.hasInstance()) {
			insts.add(pool.getInstance());
		}

		Assert.assertEquals(maxPoolSize, insts.size());

		StringBuilder inst = insts.get(0);
		inst.append('a');
		inst.append("bc");
		Assert.assertEquals("abc", inst.toString());

		Assert.assertTrue(pool.returnInstance(inst));
		insts.remove(0);

		Assert.assertTrue(pool.hasInstance());

		inst = pool.getInstance();
		insts.add(inst);

		Assert.assertEquals(0, inst.length());
	}

}
