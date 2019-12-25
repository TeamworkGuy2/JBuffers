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

		StringBuilder inst1 = insts.get(0);
		inst1.append('a');
		inst1.append("bc");
		Assert.assertEquals("abc", inst1.toString());

		Assert.assertTrue(pool.returnInstance(inst1));

		StringBuilder inst2 = insts.get(1);
		Assert.assertTrue(pool.returnInstance(inst2));

		Assert.assertTrue(pool.hasInstance());
		Assert.assertEquals(1, pool.getInUseCount());

		inst1 = pool.getInstance();
		Assert.assertEquals(insts.get(0), inst1);
		Assert.assertEquals(0, inst1.length()); // reset

		Assert.assertTrue(pool.hasInstance());
		Assert.assertEquals(2, pool.getInUseCount());

		inst1 = pool.getInstance();
		Assert.assertEquals(insts.get(1), inst2);

		Assert.assertFalse(pool.hasInstance());
		Assert.assertEquals(3, pool.getInUseCount());
	}

}
