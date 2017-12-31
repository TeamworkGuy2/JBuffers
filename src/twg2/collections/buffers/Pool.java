package twg2.collections.buffers;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.naming.SizeLimitExceededException;

/** A manager for a pool of reusable objects
 * @param <T> the type of objects stored in this pool
 * @author TeamworkGuy2
 * @since 2014-0-0
 */
public class Pool<T> {
	private int maxInstances;
	private List<T> pool;
	private BitSet inUse;
	private Supplier<T> createInstance;
	private Consumer<T> resetInstance;


	/** Create a pool of objects
	 * @param maxSize the maximum number of objects that can be created by the pool before throwing an error, -1 to allow for
	 * an unlimited size pool of objects
	 * @param createInstance function which creates a new object instance
	 * @param resetInstance function which resets an object instance
	 */
	public Pool(int maxSize, Supplier<T> createInstance, Consumer<T> resetInstance) {
		this.maxInstances = maxSize;
		this.createInstance = createInstance;
		this.resetInstance = resetInstance;
		this.pool = new ArrayList<T>();
		this.inUse = new BitSet();
	}


	/** Return an instance to this pool
	 * @param instance the instance to return
	 */
	public boolean returnInstance(T instance) {
		synchronized(this.pool) {
			int index = this.pool.indexOf(instance);
			if(index > -1) {
				this.inUse.set(index, false);
				resetInstance.accept(instance);
				return true;
			}
		}
		return false;
	}


	/**
	 * @return true if any instances are available, false if not
	 */
	public boolean hasInstance() {
		return this.maxInstances == -1 || this.pool.size() < this.maxInstances || this.findUnusedInstance() > -1;
	}


	/** Get an available instance
	 * @return the available object instance
	 * @throws Exception if there is are no available instance and the maximum pool size has been reached
	 */
	public T getInstance() throws Exception {
		synchronized(this.pool) {
			int unusedIdx = this.findUnusedInstance();
			if(unusedIdx > -1) {
				inUse.set(unusedIdx, true);
				return pool.get(unusedIdx);
			}

			// If there is no more room for a new instance, throw an error
			if(this.maxInstances > 0 && this.pool.size() >= this.maxInstances) {
				throw new SizeLimitExceededException("Pool instance limit reached");
			}

			// Else there are no free instances, create a new one
			T inst = createInstance.get();
			this.inUse.set(this.pool.size(), true);
			this.pool.add(inst);
			return inst;
		}
	}


	private int findUnusedInstance() {
		// Search for an unused/returned instance
		int size = pool.size();
		for(int i = 0; i < size; i++) {
			if(inUse.get(i) == false) {
				return i;
			}
		}
		return -1;
	}

}
