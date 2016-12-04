package wrappers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class EndianConverter {
	public static byte[] convertEndian(ByteOrder order, short value) {
		ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
		buffer.order(order);
		buffer.putShort(value);
		return buffer.array();
	}
	
	public static byte[] convertEndian(ByteOrder order, int value) {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.order(order);
		buffer.putInt(value);
		return buffer.array();
		
	}
	
	public static <T extends Number> byte[] convertEndian(ByteOrder order, T value) {
		if (value instanceof Integer) {
			return EndianConverter.convertEndian(order, (int) value);
		} else if (value instanceof Short) {
			return EndianConverter.convertEndian(order, (short) value);
		} else {
			throw new IllegalArgumentException("Expected values were Integer and Short. Was passed: " + value.getClass().getName()); 
		}
	}
	
	public static byte[] convertEndian(ByteOrder order, String bytes) {
		return convertEndian(order, bytes.getBytes(StandardCharsets.US_ASCII));
	}
	
	public static byte[] convertEndian(ByteOrder order, byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		buffer.order(order);
		buffer.put(bytes);
		bytes = buffer.array();
		return bytes;
	}
}
