package wrappers;

import java.nio.ByteBuffer;

public class PrimativeConverter {
	public static byte[] intToBytes(int value) {
		return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
	}
	
	public static byte[] shortToBytes(short value) {
		return ByteBuffer.allocate(Short.BYTES).putShort(value).array();
	}
}
