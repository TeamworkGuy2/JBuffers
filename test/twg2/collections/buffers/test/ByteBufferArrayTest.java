package twg2.collections.buffers.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.ByteBufferArray;

public class ByteBufferArrayTest {

	@Test
	public void byteBufferArrayTest() throws Exception {
		ByteBufferArray buf = new ByteBufferArray(16);
		String str1 = "A string longer than 16 bytes";
		byte[] bytesOriginal = {100, 101, 102, 103, 104};
		byte[] bytes = new byte[5];
		int temp = 0;

		try {
			// Write a string
			buf.writeUTF(str1);
			// Reposition to the beginning of the buffer
			buf.position(0);
			// Read the string and ensure it matches the original
			Assert.assertTrue(buf.readUTF().equals(str1));
			// Save the current position
			temp = buf.position();
			// Write 4 integers into the buffer, 16 bytes
			buf.writeInt(-1);
			buf.writeInt(1);
			buf.writeInt(2);
			buf.writeInt(3);
			// Skip back 2 integers
			buf.position(buf.position()-8);
			// Read the second to last integer (which should be 2)
			Assert.assertTrue(buf.readInt() == 2);
			// Check if the position after reading the second to last integer is now three 12 bytes (3 integers) from the
			// original position
			Assert.assertTrue(buf.position() == temp + 12);
			// Write a group of bytes
			buf.write(bytesOriginal);
			// Reposition to the beginning of the byte group and read them again
			buf.position(buf.position()-bytes.length);
			buf.read(bytes, 0, bytes.length);
			// Ensure the read bytes match the original bytes
			for(int i = 0; i < bytesOriginal.length; i++) {
				Assert.assertTrue(bytesOriginal[i] == bytes[i]);
			}
			// Reposition to the beginning of the buffer
			buf.position(0);
			// Ensure the first string and integer written still match
			Assert.assertTrue(buf.readUTF().equals(str1));
			Assert.assertTrue(buf.readInt() == -1);
			// Clear the buffer
			buf.clear();
			// Ensure that trying to position outside of the buffer's range throws an exception
			temp = 0;
			try {
				buf.position(1);
			} catch(Exception e) {
				temp = 1;
			}
			Assert.assertTrue(temp != 0);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			buf.close();
		}
	}


	@Test
	public void dataConstructorTest() {
		byte[] ary = new byte[] { (byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE };
		ByteBufferArray buf = new ByteBufferArray(ary, false); // use provided array without copying
		Assert.assertEquals(0xCAFEBABE, buf.readInt());
		buf.position(0);
		ary[1] = (byte)0xAA; // modify the underlying array, should be reflected in the buffer
		Assert.assertEquals(0xCAAABABE, buf.readInt());
		buf.close();

		ary = new byte[] { 0, 1, 1, 2, 3, 5, 7 };
		buf = new ByteBufferArray(ary, 2, 3);
		ary[2] = 55; // modify the original array, should not be reflected in the buffer
		Assert.assertEquals(3, buf.remaining());
		// read remaining into array
		byte[] ary2 = new byte[5];
		int rl = buf.read(ary2);
		Assert.assertEquals(3, rl);
		Assert.assertArrayEquals(new byte[] { 1, 2, 3, 0, 0 }, ary2);
		buf.position(0);
		// read byte-by-byte
		Assert.assertEquals(1, buf.readByte());
		Assert.assertEquals(2, buf.readByte());
		Assert.assertEquals(3, buf.readByte());
		Assert.assertEquals(0, buf.remaining());
		Assert.assertEquals(3, buf.position());
		Assert.assertEquals(3, buf.size());
		buf.close();
	}


	@Test
	public void varIntTest() throws Exception {
		ByteBufferArray buf = new ByteBufferArray(16);

		try {
			buf.writeShort(32000);
			Assert.assertEquals(2, buf.position());

			buf.writeVarInt(125);
			Assert.assertEquals(3, buf.position());

			buf.writeVarInt(255);
			Assert.assertEquals(5, buf.position());

			buf.writeBoolean(true);
			Assert.assertEquals(6, buf.position());

			buf.writeVarInt(Integer.MAX_VALUE);
			Assert.assertEquals(11, buf.position());

			buf.writeVarInt(0x7FFFFF);
			Assert.assertEquals(15, buf.position());

			buf.writeVarInt(-5);
			Assert.assertEquals(20, buf.position());

			buf.position(0);

			Assert.assertEquals(32000, buf.readShort());
			Assert.assertEquals(125, buf.readVarInt());
			Assert.assertEquals(255, buf.readVarInt());
			Assert.assertEquals(true, buf.readBoolean());
			Assert.assertEquals(Integer.MAX_VALUE, buf.readVarInt());
			Assert.assertEquals(0x7FFFFF, buf.readVarInt());
			Assert.assertEquals(-5, buf.readVarInt());
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			buf.close();
		}
	}

}
