package twg2.collections.buffers.test;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.WritableByteChannelOutputStream;

/**
 * @author TeamworkGuy2
 * @since 2016-07-01
 */
public class WritableByteChannelOutputStreamTest {

	@Test
	public void writeTest() throws IOException {
		Charset charset = Charset.forName("utf-8");
		DummyWritableByteChannel byteChannel = new DummyWritableByteChannel(charset);
		WritableByteChannelOutputStream stream = new WritableByteChannelOutputStream(byteChannel, 16);
		stream.write("[0-9]".getBytes(charset));
		stream.write("WritableByteChannel".getBytes(charset));
		for(byte b : "ToOutput".getBytes(charset)) {
			stream.write(b);
		}
		stream.write("Stream".getBytes(charset));

		Assert.assertEquals("[0-9]WritableByteChannelToOutputStream", byteChannel.getString());

		stream.close();
	}

}
