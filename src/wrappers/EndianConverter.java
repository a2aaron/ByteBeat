package wrappers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class EndianConverter {
	public static <T extends Number> byte[] convertEndian(ByteOrder order, T value, int size) {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.order(order);
		EndianConverter.putT(buffer, value);
		return buffer.array();
	}
	
	public static <T extends Number> byte[] convertEndian(ByteOrder order, T value) {
		if (value instanceof Integer) {
			return EndianConverter.convertEndian(order, (int) value, Integer.BYTES);
		} else if (value instanceof Short) {
			return EndianConverter.convertEndian(order, (short) value, Short.BYTES);
		} else {
			throw new IllegalArgumentException("Expected values were Integer and Short. Was passed: " + value.getClass().getName()); 
		}
	}
	
	public static <T extends Number> void putT(ByteBuffer buffer, T value) {
		if (value instanceof Integer) {
			buffer.putInt((int) value);
		} else if (value instanceof Short) {
			buffer.putShort((short) value);
		} else {
			throw new IllegalArgumentException("Expected values were Integer and Short. Was passed: " + value.getClass().getName());
		}
	}
	
	public static byte[] convertEndian(byte[] bytes) {
		for (int i = 0; i < bytes.length/2; i += 2) {
			byte temp = bytes[i];
			bytes[i] = bytes[i + 1];
			bytes[i + 1] = temp;
		}
		return bytes;
	}
}
