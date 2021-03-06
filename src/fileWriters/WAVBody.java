package fileWriters;

import java.nio.ByteOrder;

import wrappers.EndianConverter;

public class WAVBody {

	byte[] bytes;
	int size; // total number of values in the buffer 
	int capacity;  // actual total possible values that can be stored in this buffer
	ByteOrder order;
	
	public WAVBody(ByteOrder order) {
		this.bytes = new byte[256];
		this.size = 0;
		this.capacity = 256;
		this.order = order;
	}
	
	
	public void appendBytes(byte[] bytes) {
		EndianConverter.convertEndian(order, bytes);
		if (bytesRemaining() < bytes.length) {
			expandBytes(capacity * 2);
		}
		
		for(int i = 0; i < bytes.length; i++) {
			this.bytes[size + i] = bytes[i];
		}
		size += bytes.length;
	}

	private int bytesRemaining() {
		return capacity - size;
	}
	
	private void expandBytes(int newCapacity) {
		byte[] newBytes=  new byte[newCapacity];
		for (int i = 0; i < size; i++) {
			newBytes[i] = this.bytes[i];
		}
		capacity = newCapacity;
		this.bytes = newBytes;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
}
