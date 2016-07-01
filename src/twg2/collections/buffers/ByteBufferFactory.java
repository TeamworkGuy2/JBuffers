package twg2.collections.buffers;

import java.nio.ByteBuffer;

import twg2.interfaces.ReusableResourceFactory;

/**
 * @author TeamworkGuy2
 * @since 2014-0-0
 */
public interface ByteBufferFactory extends ReusableResourceFactory<ByteBuffer> {

	public int getBufferByteSize();

	public void setBufferByteSize(int byteCount);

}