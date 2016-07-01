package twg2.collections.buffers.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * @author TeamworkGuy2
 * @since 2016-07-01
 */
public class DummyWritableByteChannel implements WritableByteChannel {
	private StringBuilder buf;
	private Charset charset;
	private boolean isOpen;


	public DummyWritableByteChannel(Charset charset) {
		this.charset = charset;
		this.isOpen = true;
		this.buf = new StringBuilder();
	}


	public String getString() {
		return this.buf.toString();
	}


	@Override
	public boolean isOpen() {
		return this.isOpen;
	}


	@Override
	public void close() throws IOException {
		this.isOpen = false;
	}


	@Override
	public int write(ByteBuffer src) throws IOException {
		byte[] bytes = new byte[src.remaining()];
		src.get(bytes);
		this.buf.append(new String(bytes, this.charset.name()));
		return bytes.length;
	}

}
