package fileWriters;

import java.nio.ByteOrder;

import wrappers.EndianConverter;

public class ShortWAVField extends WAVField {
	short value;
	
	public ShortWAVField(ByteOrder order) {
		this((short) 0, order);
	}
	
	public ShortWAVField(short value, ByteOrder order) {
		super(Short.BYTES, order);
		this.value = value;
	}
	
	public void setShort(short value) {
		super.setBytes(EndianConverter.convertEndian(order, value));
		this.value = value;
	}
	
	public short getShort() {
		return value;
	}

}
