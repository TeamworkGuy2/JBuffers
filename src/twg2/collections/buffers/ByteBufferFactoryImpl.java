package twg2.collections.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import twg2.interfaces.ReusableResourceFactory;

/** A factory for creating {@link ByteBuffer} objects
 * @author TeamworkGuy2
 * @since 2013-9-27
 */
public final class ByteBufferFactoryImpl implements ReusableResourceFactory<ByteBuffer>, ByteBufferFactory {
	private int bufferByteSize;
	private boolean direct;
	private ByteOrder byteOrder;


	/**
	 * @param direct true if this factory should create direct (native) byte
	 * buffers, false to create normal byte buffers
	 * @param order the byte order of data in the buffers created by this
	 * factory, or null to keep the default byte order when the buffers are
	 * created.
	 */
	public ByteBufferFactoryImpl(boolean direct, ByteOrder order) {
		this.direct = direct;
		this.byteOrder = order;
	}


	/** 
	 * @return a byte buffer interface based on this factory's current state
	 * variables which can be modified via its getter and setter methods.
	 */
	@Override
	public ByteBuffer create() throws Exception {
		ByteBuffer newBuf = null;
		if(direct) {
			newBuf = ByteBuffer.allocateDirect(bufferByteSize);
		}
		else {
			newBuf = ByteBuffer.allocate(bufferByteSize);
		}
		if(byteOrder != null) {
			newBuf.order(byteOrder);
		}
		return newBuf;
	}


	@Override
	public boolean isValid(ByteBuffer buffer) {
		return buffer.capacity() >= bufferByteSize;
	}


	@Override
	public void setup(ByteBuffer buffer) {
		buffer.limit(bufferByteSize);
	}


	@Override
	public ByteBuffer recycle(ByteBuffer buffer) {
		buffer.clear();
		return buffer;
	}


	@Override
	public int getBufferByteSize() {
		return bufferByteSize;
	}


	@Override
	public void setBufferByteSize(int byteCount) {
		bufferByteSize = byteCount;
	}

}
