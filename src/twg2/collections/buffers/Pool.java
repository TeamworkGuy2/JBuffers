package twg2.collections.buffers;

import java.util.Arrays;
import java.util.BitSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** A manager for a pool of reusable objects.
 * Warning: Pool is not synchronized and should be synchronized externally for cross thread use.
 * @param <T> the type of objects stored in this pool
 * @author TeamworkGuy2
 * @since 2014-0-0
 */
public class Pool<T> {
	protected int maxSize;
	protected int used;
	protected Object[] pool;
	protected int poolSize;
	protected BitSet inUse;
	protected Supplier<T> createInstance;
	protected Consumer<T> resetInstance;


	/** Create a pool of objects
	 * @param maxSize the maximum number of objects that can be created by the pool before throwing an error, -1 to allow for
	 * an unlimited size pool of objects
	 * @param createInstance function which creates a new object instance
	 * @param resetInstance function which resets an object instance
	 */
	public Pool(int maxSize, Supplier<T> createInstance, Consumer<T> resetInstance) {
		this.maxSize = maxSize;
		this.createInstance = createInstance;
		this.resetInstance = resetInstance;
		this.pool = new Object[maxSize > 100 || maxSize < 1 ? 100 : maxSize];
		this.poolSize = 0;
		this.inUse = new BitSet();
	}


	/**
	 * @return true if any instances are available, false if not
	 */
	public boolean hasInstance() {
		return maxSize == -1 || used < maxSize;
	}


	/**
	 * @return the number of objects supplied by this pool that have not been returned.
	 */
	public int getInUseCount() {
		return used;
	}


	/** Get an available instance
	 * @return the available object instance
	 * @throws Exception if there is are no available instance and the maximum pool size has been reached
	 */
	public T getInstance() throws Exception {
		int unusedIdx = findUnusedInstance();
		if(unusedIdx > -1) {
			inUse.set(unusedIdx, true);
			used++;
			@SuppressWarnings("unchecked")
			T res = (T)pool[unusedIdx];
			return res;
		}

		Object[] objs = this.pool;
		int nextIdx = this.poolSize;
		// If the pool needs to expand
		if(nextIdx >= objs.length) {
			// If there is no more room for a new instance, throw an error
			if(objs.length == maxSize) {
				throw new IllegalStateException("Pool size limit reached");
			}
			objs = Arrays.copyOf(objs, Math.min(objs.length + (objs.length >>> 1) + 4, maxSize));
			this.pool = objs;
		}

		// Else there are no free instances, create a new one
		@SuppressWarnings("unchecked")
		T res = (T)objs[nextIdx];
		if(res == null) {
			objs[nextIdx] = res = createInstance.get();
			poolSize++;
		}
		inUse.set(nextIdx, true);
		used++;
		return res;
	}


	/** Return an instance to this pool
	 * @param instance the instance to return
	 */
	public boolean returnInstance(T instance) {
		int index = indexOf(instance);
		if(index > -1) {
			resetInstance.accept(instance);
			inUse.set(index, false);
			used--;
			return true;
		}
		return false;
	}


	protected int indexOf(Object inst) {
		Object[] objs = this.pool;
		for(int i = 0, max = this.poolSize; i < max; i++) {
			if(objs[i] == inst) {
				return i;
			}
		}
		return -1;
	}


	protected int findUnusedInstance() {
		// Search for an unused/returned instance
		int idx = inUse.nextClearBit(0);
		return idx < this.poolSize ? idx : -1;
	}

}
