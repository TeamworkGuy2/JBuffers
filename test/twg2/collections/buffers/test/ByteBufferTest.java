package twg2.collections.buffers.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.buffers.ByteBufferArray;

public class ByteBufferTest {

	@Test
	public void testByteBufferArray() throws Exception {
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

}
