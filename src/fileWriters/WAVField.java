package fileWriters;

import java.nio.ByteOrder;

import wrappers.EndianConverter;
import wrappers.PrimativeConverter;

public class WAVField<T extends Number> {
	ByteOrder order;
	byte[] bytes;
	T value;
	
	public WAVField(byte[] bytes, ByteOrder order) {
		this.order = order;
		this.bytes = bytes;
	}

	public WAVField(ByteOrder order) {
		this.order = order;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return this.bytes;
	}
	
	public void set(T value) {
		this.value = value;
		this.bytes = EndianConverter.convertEndian(order, value);
	}
	
	public T get() {
		return this.value;
	}
}
