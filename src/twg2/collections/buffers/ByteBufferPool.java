package twg2.collections.buffers;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.naming.SizeLimitExceededException;

/** A manager for a pool of {@link ByteBuffer ByteBuffers}
 * @author TeamworkGuy2
 * @since 2014-0-0
 */
public class ByteBufferPool {
	private int maxInstances;
	private int capacity;
	private boolean direct;
	private List<ByteBuffer> buffers;
	private List<Boolean> inUse;


	/** Create a byte buffer pool of objects
	 * @param maxBuffers the maximum number of objects that can be created by the pool before throwing an error, -1
	 * to allow for an unlimited size pool of objects
	 * @param bufferSize the size of the byte buffer to allocate
	 * @param direct whether the byte buffers should be direct or not
	 */
	public ByteBufferPool(int maxBuffers, int bufferSize, boolean direct) {
		this.maxInstances = maxBuffers;
		this.capacity = bufferSize;
		this.direct = direct;
		this.buffers = new ArrayList<ByteBuffer>();
		this.inUse = new ArrayList<Boolean>();
	}


	/** Return an instance of this byte buffer pool
	 * @param instance the instance to return
	 */
	public boolean returnInstance(ByteBuffer instance) {
		synchronized(this.buffers) {
			int index = this.buffers.indexOf(instance);
			if(index > -1) {
				this.inUse.set(index, false);
				instance.clear();
				return true;
			}
		}
		return false;
	}


	public boolean hasInstance() {
		return this.maxInstances < 1 || this.buffers.size() < this.maxInstances || this.findUnusedBuffer() > -1;
	}


	/** Get an available byte buffer instance
	 * @return the available byte buffer instance
	 * @throws Exception if there is are no available instance and the maximum pool size has been reached
	 */
	public ByteBuffer getInstance() throws Exception {
		synchronized(this.buffers) {
			int unusedBufferIdx = this.findUnusedBuffer();
			if(unusedBufferIdx > -1) {
				inUse.set(unusedBufferIdx, true);
				return buffers.get(unusedBufferIdx);
			}

			// If there is no more room for a new instance, throw an error
			if(this.maxInstances > 0 && this.buffers.size() >= this.maxInstances) {
				throw new SizeLimitExceededException("Pool instance limit reached");
			}

			// Else there are no free instances, create a new one
			ByteBuffer newBuffer = null;
			if(direct == true) {
				newBuffer = ByteBuffer.allocateDirect(capacity);
			}
			else {
				newBuffer = ByteBuffer.allocate(capacity);
			}
			this.buffers.add(newBuffer);
			this.inUse.add(true);
			return newBuffer;
		}
	}


	private int findUnusedBuffer() {
		// Search for an unused/returned buffer
		int size = inUse.size();
		for(int i = 0; i < size; i++) {
			if(inUse.get(i) == false) {
				return i;
			}
		}
		return -1;
	}

}
