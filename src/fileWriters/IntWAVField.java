package fileWriters;

import java.nio.ByteOrder;

import wrappers.EndianConverter;

public class IntWAVField extends WAVField {
	int value;
	
	public IntWAVField(ByteOrder order) {
		this(0, order);
	}
	
	public IntWAVField(int value, ByteOrder order) {
		super(Integer.BYTES, order);
		this.value = value;
	}
	
	public void setInt(int value) {
		super.setBytes(EndianConverter.convertEndian(order, value));
		this.value = value;
	}
	
	public int getInt() {
		return value;
	}

}
