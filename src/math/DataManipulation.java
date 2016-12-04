package math;

public class DataManipulation {
	public static void repeat(byte[] data, int samplesToRepeat) {
		int i = 0;
		while (i < data.length) {
			for (int j = 0; j < samplesToRepeat; j++) {
				if (i + 1 > data.length) {
					return;
				}
				data[i] = (byte) data[j];
				i++;
			}
		}
	}
	
	public static void amplify(byte[] data, double d) {
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (((int) data[i]) * d);
		}
	}
	
	public static void amplify8Bit(byte[] data, double d) {
		for (int i = 0; i < data.length; i++) {
			data[i] =  (byte) ((data[i] * d) + 127);
		}
	}
	
	public static void fillWithBytebeat(byte[] data, int start, Bytebeat bytebeat) {
		for (int i = 0; i < data.length; i++) {
			try {
				data[i] = (byte) bytebeat.get(start);
			} catch (Exception e) {
				data[i] = (byte) 127;
			}
			start++;
		}
	}
	
	public static void fillWithBytebeat(byte[] data, Bytebeat bytebeat) {
		fillWithBytebeat(data, 0, bytebeat);
	}
}
