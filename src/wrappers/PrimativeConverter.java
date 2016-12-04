package wrappers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class PrimativeConverter {
	public static byte[] intToBytes(int value) {
		return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
	}
	
	public static byte[] shortToBytes(short value) {
		return ByteBuffer.allocate(Short.BYTES).putShort(value).array();
	}
	
	public static short bytesToShort(byte[] bytes, ByteOrder order) {
		return ByteBuffer.wrap(bytes).order(order).getShort();
	}
	
	public static int bytesToInt(byte[] bytes, ByteOrder order) {
		return ByteBuffer.wrap(bytes).order(order).getInt();
	}
	
	public static <T extends Number> byte[] TToBytes(T value) {
		if (value instanceof Integer) {
			return PrimativeConverter.intToBytes((int) value);
		} else if (value instanceof Short) {
			return PrimativeConverter.shortToBytes((short) value);
		} else {
			throw new IllegalArgumentException("Expected values were Integer and Short. Was passed: " + value.getClass().getName());
		}
	}
	
	public static byte[] StringToBytes(String string) {
		return string.getBytes(StandardCharsets.US_ASCII);
	}
}
