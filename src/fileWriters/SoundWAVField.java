package fileWriters;

import java.nio.ByteOrder;

import wrappers.EndianConverter;

public class SoundWAVField extends WAVField {

	byte[] bytes;
	
	public SoundWAVField(ByteOrder order) {
		super(order);
		bytes = new byte[0];
	}
	
	public void appendBytes(byte[] bytes) {
		if (getBytes() == null) {
			throw new RuntimeException("Your");
		}
		setBytes(concatBytes(getBytes(), bytes));
	}

	private byte[] concatBytes(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i];
		}
		for (int i = a.length; i < c.length; i++) {
			c[i] = b[i - a.length];
		}
		return c;
	}
	
	@Override
	public void setBytes(byte[] bytes) {
		this.bytes = EndianConverter.convertEndian(order, bytes);
	}
	
	@Override
	public byte[] getBytes() {
		return bytes;
	}
}
