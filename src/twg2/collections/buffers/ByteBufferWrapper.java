package twg2.collections.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/** Wrapper for a {@link ByteBuffer} with the same API, except this also implements {@link ByteBufferInterface}
 * @author TeamworkGuy2
 * @since 2013-9-27
 */
public final class ByteBufferWrapper implements ByteBufferInterface {
	private ByteBuffer buf;
	@SuppressWarnings("unused")
	private boolean destroyed = false;


	public ByteBufferWrapper(ByteBuffer buf) {
		this.buf = buf;
	}


	public ByteBuffer getBuffer() {
		return buf;
	}


	public void destroy() {
		this.destroyed = true;
		this.buf = null;
	}


	@Override
	public boolean equals(Object buf) {
		if(buf instanceof ByteBufferWrapper) {
			return ((ByteBufferWrapper) buf).buf.equals(this.buf);
		}
		else if(buf instanceof ByteBufferInterface) {
			throw new IllegalArgumentException(buf.getClass() + " cannot be compared to a " + this.getClass());
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return buf.hashCode();
	}


	@Override
	public String toString() {
		return buf.toString();
	}


	@Override
	public int capacity() {
		return buf.capacity();
	}


	@Override
	public int position() {
		return buf.position();
	}


	@Override
	public ByteBufferWrapper position(int newPosition) {
		buf.position(newPosition);
		return this;
	}


	@Override
	public int limit() {
		return buf.limit();
	}


	@Override
	public ByteBufferWrapper limit(int newLimit) {
		buf.limit(newLimit);
		return this;
	}


	@Override
	public ByteBufferWrapper mark() {
		buf.mark();
		return this;
	}


	@Override
	public ByteBufferWrapper reset() {
		buf.reset();
		return this;
	}


	@Override
	public ByteBufferWrapper clear() {
		buf.clear();
		return this;
	}


	@Override
	public ByteBufferWrapper flip() {
		buf.flip();
		return this;
	}


	@Override
	public ByteBufferWrapper rewind() {
		buf.rewind();
		return this;
	}


	@Override
	public int remaining() {
		return buf.remaining();
	}


	@Override
	public boolean hasRemaining() {
		return buf.hasRemaining();
	}


	@Override
	public boolean isReadOnly() {
		return buf.isReadOnly();
	}


	@Override
	public byte get() {
		return buf.get();
	}


	@Override
	public ByteBufferWrapper put(byte b) {
		buf.put(b);
		return this;
	}


	@Override
	public byte get(int index) {
		return buf.get(index);
	}


	@Override
	public ByteBufferWrapper put(int index, byte b) {
		buf.put(index, b);
		return this;
	}


	@Override
	public ByteBufferWrapper get(byte[] dst, int offset, int length) {
		buf.get(dst, offset, length);
		return this;
	}


	@Override
	public ByteBufferWrapper get(byte[] dst) {
		buf.get(dst);
		return this;
	}


	@Override
	public ByteBufferWrapper put(ByteBufferInterface src) {
		if(src instanceof ByteBufferWrapper) {
			buf.put(((ByteBufferWrapper) src).buf);
		}
		else {
			throw new IllegalArgumentException(src.getClass() + " cannot be stored in a " + this.getClass());
		}
		return this;
	}


	@Override
	public ByteBufferWrapper put(byte[] src, int offset, int length) {
		buf.put(src, offset, length);
		return this;
	}


	@Override
	public ByteBufferWrapper put(byte[] src) {
		buf.put(src);
		return this;
	}


	@Override
	public boolean isDirect() {
		return buf.isDirect();
	}


	@Override
	public ByteOrder order() {
		return buf.order();
	}


	@Override
	public char getChar() {
		return buf.getChar();
	}


	@Override
	public char getChar(int index) {
		return buf.getChar(index);
	}


	@Override
	public ByteBufferWrapper putChar(char index) {
		buf.putChar(index);
		return this;
	}


	@Override
	public ByteBufferWrapper putChar(int index, char value) {
		buf.putChar(index, value);
		return this;
	}


	@Override
	public short getShort() {
		return buf.getShort();
	}


	@Override
	public short getShort(int index) {
		return buf.getShort(index);
	}


	@Override
	public ByteBufferWrapper putShort(short value) {
		buf.putShort(value);
		return this;
	}


	@Override
	public ByteBufferWrapper putShort(int index, short value) {
		buf.putShort(index, value);
		return this;
	}


	@Override
	public int getInt() {
		return buf.getInt();
	}


	@Override
	public int getInt(int index) {
		return buf.getInt(index);
	}


	@Override
	public ByteBufferWrapper putInt(int value) {
		buf.putInt(value);
		return this;
	}


	@Override
	public ByteBufferWrapper putInt(int index, int value) {
		buf.putInt(index, value);
		return this;
	}


	@Override
	public long getLong() {
		return buf.getLong();
	}


	@Override
	public long getLong(int index) {
		return buf.getLong(index);
	}


	@Override
	public ByteBufferWrapper putLong(long value) {
		buf.putLong(value);
		return this;
	}


	@Override
	public ByteBufferWrapper putLong(int index, long value) {
		buf.putLong(index, value);
		return this;
	}


	@Override
	public float getFloat() {
		return buf.getFloat();
	}


	@Override
	public float getFloat(int index) {
		return buf.getFloat(index);
	}


	@Override
	public ByteBufferWrapper putFloat(float value) {
		buf.putFloat(value);
		return this;
	}


	@Override
	public ByteBufferWrapper putFloat(int index, float value) {
		buf.putFloat(index, value);
		return this;
	}


	@Override
	public double getDouble() {
		return buf.getDouble();
	}


	@Override
	public double getDouble(int index) {
		return buf.getDouble(index);
	}


	@Override
	public ByteBufferWrapper putDouble(double value) {
		buf.putDouble(value);
		return this;
	}


	@Override
	public ByteBufferWrapper putDouble(int index, double value) {
		buf.putDouble(index, value);
		return this;
	}

}
