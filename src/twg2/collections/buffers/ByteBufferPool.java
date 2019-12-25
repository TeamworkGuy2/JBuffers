package twg2.collections.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;

/** A manager for a pool of {@link ByteBuffer ByteBuffers}.
 * Warning: Pool is not synchronized and should be synchronized externally for cross thread use.
 * @author TeamworkGuy2
 * @since 2014-0-0
 */
public class ByteBufferPool implements ByteBufferFactory {
	protected int maxSize;
	protected int capacity;
	protected boolean direct;
	protected ByteOrder byteOrder;
	protected int used;
	protected ByteBuffer[] pool;
	protected int poolSize;
	protected BitSet inUse;


	/** Create a byte buffer pool of objects
	 * @param maxBuffers the maximum number of objects that can be created by the pool before throwing an error, -1
	 * to allow for an unlimited size pool of objects
	 * @param bufferSize the size of the byte buffer to allocate
	 * @param direct whether the byte buffers should be direct or not
	 */
	public ByteBufferPool(int maxBuffers, int bufferSize, boolean direct, ByteOrder order) {
		this.maxSize = maxBuffers;
		this.capacity = bufferSize;
		this.direct = direct;
		this.byteOrder = order;
		this.pool = new ByteBuffer[maxSize > 100 || maxSize < 1 ? 100 : maxSize];
		this.inUse = new BitSet();
	}


	/**
	 * @return true if any buffers are available, false if not
	 */
	public boolean hasInstance() {
		return maxSize == -1 || used < maxSize;
	}


	/**
	 * @return the number of buffers supplied by this pool that have not been returned.
	 */
	public int getInUseCount() {
		return used;
	}



	/** Get an available byte buffer instance
	 * @return the available byte buffer instance
	 * @throws Exception if there is are no available instance and the maximum pool size has been reached
	 */
	public ByteBuffer getInstance() throws Exception {
		int unusedBufferIdx = findUnusedInstance();
		if(unusedBufferIdx > -1) {
			inUse.set(unusedBufferIdx, true);
			used++;
			return pool[unusedBufferIdx];
		}

		ByteBuffer[] objs = this.pool;
		int nextIdx = this.poolSize;
		// If there is no more room for a new instance, throw an error
		if(nextIdx >= objs.length) {
			if(objs.length == maxSize) {
				throw new IllegalStateException("Pool size limit reached");
			}
			objs = Arrays.copyOf(objs, Math.min(objs.length + (objs.length >>> 1) + 4, maxSize));
			this.pool = objs;
		}

		// Else there are no free instances, create a new one
		ByteBuffer newBuffer = objs[nextIdx];
		if(newBuffer == null) {
			objs[nextIdx] = newBuffer = create();
			poolSize++;
		}
		inUse.set(nextIdx, true);
		used++;
		return newBuffer;
	}


	/** Return an instance to this byte buffer pool
	 * @param instance the instance to return
	 */
	public boolean returnInstance(ByteBuffer instance) {
		int index = indexOf(instance);
		if(index > -1) {
			instance.clear();
			inUse.set(index, false);
			used--;
			return true;
		}
		return false;
	}


	@Override
	public int getBufferByteSize() {
		return this.capacity;
	}


	@Override
	public void setBufferByteSize(int bufferByteCapacity) {
		this.capacity = bufferByteCapacity;
	}


	@Override
	public ByteBuffer create() throws Exception {
		ByteBuffer newBuffer;
		if(direct == true) {
			newBuffer = ByteBuffer.allocateDirect(capacity);
		}
		else {
			newBuffer = ByteBuffer.allocate(capacity);
		}
		if(byteOrder != null) {
			newBuffer.order(byteOrder);
		}
		return newBuffer;
	}


	@Override
	public ByteBuffer recycle(ByteBuffer buffer) {
		buffer.clear();
		return buffer;
	}


	@Override
	public boolean isValid(ByteBuffer buffer) {
		return buffer.capacity() >= capacity;
	}


	@Override
	public void setup(ByteBuffer buffer) {
		buffer.limit(capacity);
	}


	protected int indexOf(ByteBuffer inst) {
		Object[] objs = this.pool;
		for(int i = 0, max = this.poolSize; i < max; i++) {
			if(objs[i] == inst) {
				return i;
			}
		}
		return -1;
	}


	protected int findUnusedInstance() {
		// Search for an unused/returned buffer
		int idx = inUse.nextClearBit(0);
		return idx < poolSize ? idx : -1;
	}

}
