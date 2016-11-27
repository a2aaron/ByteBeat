package fileWriters;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class WAVField {
	int size;
	ByteOrder order;
	byte[] bytes;

	public WAVField(int size, ByteOrder order) {
		this.size = size;
		this.order = order;
		this.bytes = new byte[size];
	}

	public WAVField(byte[] bytes, ByteOrder order) {
		this.size = bytes.length;
		this.order = order;
		this.bytes = bytes;
	}

	public WAVField(ByteOrder order) {
		this.order = order;
	}

	public void setBytes(byte[] bytes) {
		if (bytes.length != this.size) {
			throw new IllegalArgumentException(
					"Passed arguement has: " + bytes.length + " bytes. Expected: " + this.size + " bytes.");
		}
		this.bytes = bytes;
	}
	
	public void setBytes(String value) {
		setBytes(value.getBytes(StandardCharsets.US_ASCII));
	}

	public byte[] getBytes() {
		return this.bytes;
	}
}
