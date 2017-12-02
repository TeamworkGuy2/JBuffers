package twg2.collections.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/** Same API as {@link ByteBuffer}, also implements interface {@link ByteBufferInterface}
 * @author TeamworkGuy2
 * @since 2013-9-27
 */
public final class ByteBufferWrapper implements ByteBufferInterface {
	private ByteBuffer buf;
	@SuppressWarnings("unused")
	private boolean isDestroyed = false;


	public ByteBufferWrapper(ByteBuffer buf) {
		this.buf = buf;
	}


	public ByteBuffer getBuffer() {
		return buf;
	}


	public void destroy() {
		this.isDestroyed = true;
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
	public ByteBufferInterface position(int newPosition) {
		buf.position(newPosition);
		return this;
	}


	@Override
	public int limit() {
		return buf.limit();
	}


	@Override
	public ByteBufferInterface limit(int newLimit) {
		buf.limit(newLimit);
		return this;
	}


	@Override
	public ByteBufferInterface mark() {
		buf.mark();
		return this;
	}


	@Override
	public ByteBufferInterface reset() {
		buf.reset();
		return this;
	}


	@Override
	public ByteBufferInterface clear() {
		buf.clear();
		return this;
	}


	@Override
	public ByteBufferInterface flip() {
		buf.flip();
		return this;
	}


	@Override
	public ByteBufferInterface rewind() {
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
	public ByteBufferInterface put(byte b) {
		buf.put(b);
		return this;
	}


	@Override
	public byte get(int index) {
		return buf.get(index);
	}


	@Override
	public ByteBufferInterface put(int index, byte b) {
		buf.put(index, b);
		return this;
	}


	@Override
	public ByteBufferInterface get(byte[] dst, int offset, int length) {
		buf.get(dst, offset, length);
		return this;
	}


	@Override
	public ByteBufferInterface get(byte[] dst) {
		buf.get(dst);
		return this;
	}


	@Override
	public ByteBufferInterface put(ByteBufferInterface src) {
		if(src instanceof ByteBufferWrapper) {
			buf.put(((ByteBufferWrapper) src).buf);
		}
		else {
			throw new IllegalArgumentException(src.getClass() + " cannot be stored in a " + this.getClass());
		}
		return this;
	}


	@Override
	public ByteBufferInterface put(byte[] src, int offset, int length) {
		buf.put(src, offset, length);
		return this;
	}


	@Override
	public ByteBufferInterface put(byte[] src) {
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
	public ByteBufferInterface putChar(char index) {
		buf.putChar(index);
		return this;
	}


	@Override
	public ByteBufferInterface putChar(int index, char value) {
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
	public ByteBufferInterface putShort(short value) {
		buf.putShort(value);
		return this;
	}


	@Override
	public ByteBufferInterface putShort(int index, short value) {
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
	public ByteBufferInterface putInt(int value) {
		buf.putInt(value);
		return this;
	}


	@Override
	public ByteBufferInterface putInt(int index, int value) {
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
	public ByteBufferInterface putLong(long value) {
		buf.putLong(value);
		return this;
	}


	@Override
	public ByteBufferInterface putLong(int index, long value) {
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
	public ByteBufferInterface putFloat(float value) {
		buf.putFloat(value);
		return this;
	}


	@Override
	public ByteBufferInterface putFloat(int index, float value) {
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
	public ByteBufferInterface putDouble(double value) {
		buf.putDouble(value);
		return this;
	}


	@Override
	public ByteBufferInterface putDouble(int index, double value) {
		buf.putDouble(index, value);
		return this;
	}

}
