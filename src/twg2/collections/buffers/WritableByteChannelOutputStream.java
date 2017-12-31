package twg2.collections.buffers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/** Convert a {@link WritableByteChannel} to an {@link OutputStream}.
 * Bridge the gap between byte[] and {@link ByteBuffer}
 * @author TeamworkGuy2
 * @since 2014-1-20
 */
public class WritableByteChannelOutputStream extends OutputStream {
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private WritableByteChannel channel;
	private ByteBuffer tempWriteBuf;


	public WritableByteChannelOutputStream(WritableByteChannel channel) {
		this(channel, DEFAULT_BUFFER_SIZE);
	}


	public WritableByteChannelOutputStream(WritableByteChannel channel, int bufferSize) {
		this.channel = channel;
		if(bufferSize < 1) {
			bufferSize = DEFAULT_BUFFER_SIZE;
		}
		// Disputable whether this should allocated a direct buffer or not
		// some research seems to indicate the file and socket channels are faster
		// with direct buffers, but this may vary by JVM
		this.tempWriteBuf = ByteBuffer.allocateDirect(bufferSize);
	}


	public WritableByteChannelOutputStream(WritableByteChannel channel, ByteBuffer writeBuf) {
		this.channel = channel;
		this.tempWriteBuf = writeBuf;
	}


	@Override
	public void write(int b) throws IOException {
		tempWriteBuf.rewind();
		tempWriteBuf.put((byte)b);
		tempWriteBuf.rewind();
		tempWriteBuf.limit(1);

		channel.write(tempWriteBuf);
		tempWriteBuf.clear();
	}


	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}


	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		int writeCount = 0;
		for(int i = 0; i < len; i += writeCount) {
			tempWriteBuf.rewind();
			// Calculate how many bytes are left to write
			if(len - i > tempWriteBuf.remaining()) {
				writeCount = tempWriteBuf.remaining();
			}
			else {
				writeCount = len - i;
			}
			tempWriteBuf.put(b, off + i, writeCount);
			tempWriteBuf.rewind();
			tempWriteBuf.limit(writeCount);

			channel.write(tempWriteBuf);
			tempWriteBuf.clear();
		}
	}


	@Override
	public void close() throws IOException {
		tempWriteBuf = null;
		super.close();
	}

}
