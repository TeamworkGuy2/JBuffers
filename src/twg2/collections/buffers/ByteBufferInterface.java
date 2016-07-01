package twg2.collections.buffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/** A {@link ByteBuffer} interface wrapper
 * @author TeamworkGuy2
 * @since 2013-9-27
 */
public interface ByteBufferInterface {

	public byte get();

	public ByteBufferInterface put(byte b);

	public byte get(int index);

	public ByteBufferInterface put(int index, byte b);

	public ByteBufferInterface get(byte[] dst, int offset, int length);

	public ByteBufferInterface get(byte[] dst);

	public ByteBufferInterface put(ByteBufferInterface src);

	public ByteBufferInterface put(byte[] src, int offset, int length);

	public ByteBufferInterface put(byte[] src);

	public boolean isDirect();

	public int capacity();

	public int position();

	public ByteBufferInterface position(int newPosition);

	public int limit();

	public ByteBufferInterface limit(int newLimit);

	public ByteBufferInterface mark();

	public ByteBufferInterface reset();

	public ByteBufferInterface clear();

	public ByteBufferInterface flip();

	public ByteBufferInterface rewind();

	public int remaining();

	public boolean hasRemaining();

	public boolean isReadOnly();

	@Override
	public String toString();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);

	public ByteOrder order();

	public char getChar();

	public char getChar(int index);

	public ByteBufferInterface putChar(char index);

	public ByteBufferInterface putChar(int index, char value);

	public short getShort();

	public short getShort(int index);

	public ByteBufferInterface putShort(short value);

	public ByteBufferInterface putShort(int index, short value);

	public int getInt();

	public int getInt(int index);

	public ByteBufferInterface putInt(int value);

	public ByteBufferInterface putInt(int index, int value);

	public long getLong();

	public long getLong(int index);

	public ByteBufferInterface putLong(long value);

	public ByteBufferInterface putLong(int index, long value);

	public float getFloat();

	public float getFloat(int index);

	public ByteBufferInterface putFloat(float value);

	public ByteBufferInterface putFloat(int index, float value);

	public double getDouble();

	public double getDouble(int index);

	public ByteBufferInterface putDouble(double value);

	public ByteBufferInterface putDouble(int index, double value);

}
