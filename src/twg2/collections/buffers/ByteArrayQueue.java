package twg2.collections.buffers;

/** A wrapper for an array of bytes with an API similar to a buffer/queue with an offset and length.<br>
 * Contains methods to remove data from the front or back of the buffer, which shifts the buffer's internal offset and length.<br/>
 * This class is not thread safe and an instance should not be accessed by more than one object in case a function
 * gets data from this buffer using {@link #get} and then waits, leaving time for another function to call {@link #get}
 * before the first function has time to call {@link #removeFront} or {@link #removeBack}, meaning the second object sees
 * stale data.
 * The data array returned by {@link #get} is not a copy, changes to that array will be reflected in this buffer,
 * and changes to this buffer will be reflected in any references to the array returned by {@link #get}
 * @author TeamworkGuy2
 * @since 2013-4-6
 */
public class ByteArrayQueue {
	private final byte[] data;
	private int offset;
	private int limit;


	/** Create a byte array queue
	 * @param data the data to store in this buffer
	 * @param offset the offset into the data array at which valid data starts
	 * @param length the number of elements of valid data in the array starting at the array offset
	 */
	public ByteArrayQueue(byte[] data, int offset, int length) {
		this.data = data;
		this.offset = offset;
		this.limit = offset + length;
	}


	/** get the array of data left in this buffer.<br/>
	 * This method must be used on conjunction with {@link #getOffset} and {@link #length}
	 * @return the data stored by this buffer
	 */
	public byte[] get() {
		return this.data;
	}


	/** get the offset into the array returned by {@link #get} at which valid data starts
	 * @return the offset into this buffer's array at which valid data begins
	 */
	public int offset() {
		return this.offset;
	}


	/** get the number of valid elements starting at {@link #offset} from the array returned by {@link #get}
	 * @return the number of valid elements remaining in this buffer
	 */
	public int length() {
		return this.limit - this.offset;
	}


	/** remove the specified number of elements from the beginning of this buffer
	 * @param length the number of elements to remove from the beginning of this buffer
	 */
	public void removeHead(int length) {
		this.offset += length;
		// If number of elements it remove is larger than the number of elements in the array,
		// only remove the number of elements in the array
		if(this.offset > this.limit) {
			this.offset = this.limit;
		}
	}


	/** remove the specified number of elements from the end of this buffer
	 * @param length the number of elements to remove from the end of this buffer
	 */
	public void removeTail(int length) {
		this.limit -= length;
		// If number of elements it remove is larger than the number of elements in the array,
		// only remove the number of elements in the array
		if(this.limit < this.offset) {
			this.limit = this.offset;
		}
	}

}
