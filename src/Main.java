import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.Encoding;

public class Main {
	public static void main(String[] args) {
		float sampleRate = 8000.0F;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean isSigned = true;
		boolean bigEndian = true;

		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, isSigned, bigEndian);

		// Create file
		File file = null;
		OutputStream out = null;
		try {
			file = new File("test.wav");			
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Speaker speaker = new Speaker(format);
		Microphone mic = new Microphone(format);
		speaker.open();
		mic.open();		
		byte[] data = new byte[mic.getBufferSize()/10];
		int start = 0;
		int end = data.length;
		for (int i = 0; i < 100000; i++) {
			
//				mic.readBytes(data);
				
				byte[] outputData = bytebeat(start, end);
//				byte[] outputData = Arrays.copyOf(data, data.length/4);
//				outputData = repeat(outputData, data.length);
				start = end;
				end += data.length;
				speaker.writeBytes(outputData);
			try {
				out.write(outputData, 0, outputData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mic.close();
		speaker.close();
		//		System.out.println(Arrays.toString(b));

	}

	public static byte[] repeat(byte[] data, int length) {
		int i = 0;
		byte[] newData = new byte[length];
		while (i < length) {
			for (int j = 0; j < data.length; j++) {
				if (i + 1 > length) {
					return newData;
				}
				int thisValue = data[j];
				newData[i] = (byte) thisValue;
				i++;
			}
		}
		return newData;
	}
	
	public static byte[] bytebeat(int start, int end) {
		byte[] data = new byte[end - start];
		for (int t = start; t < end; t++) {
			int value = 0;
			if (isPrime(t)) {
				value = 64;
			}
			data[t - start] = (byte) value;
		}
		return data;
	}
	
	public static boolean isPrime(int num) {
		if (num == 2) {
			return true;
		}
		if (num % 2 == 0) {
			return false;
		}
		
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {
            	return false;
            }
        }
        
        return true;
    }
}
