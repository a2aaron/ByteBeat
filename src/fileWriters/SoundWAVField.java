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
		EndianConverter.convertEndian(order, bytes);
		this.bytes = concatBytes(getBytes(), bytes);
	}

	private byte[] concatBytes(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
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
