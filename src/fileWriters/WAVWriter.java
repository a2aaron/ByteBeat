package fileWriters;

import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
public class WAVWriter extends FileWriter {

	boolean wroteHeader = false;
	// RIFF header
	IntWAVField ChunkID = new IntWAVField(ByteOrder.BIG_ENDIAN); // "RIFF"
	IntWAVField ChunkSize = new IntWAVField(ByteOrder.LITTLE_ENDIAN); // 4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
	IntWAVField Format = new IntWAVField(ByteOrder.BIG_ENDIAN); // "WAVE"

	// fmt subchunk
	IntWAVField Subchunk1ID = new IntWAVField(ByteOrder.BIG_ENDIAN); // "fmt "
	IntWAVField Subchunk1Size = new IntWAVField(ByteOrder.LITTLE_ENDIAN); // 16 if PCM
	ShortWAVField AudioFormatField = new ShortWAVField(ByteOrder.LITTLE_ENDIAN); // 1 if PCM
	ShortWAVField NumChannels = new ShortWAVField(ByteOrder.LITTLE_ENDIAN); // 1 or 2
	IntWAVField SampleRate = new IntWAVField(ByteOrder.LITTLE_ENDIAN); // 8000, 44100
	IntWAVField ByteRate = new IntWAVField(ByteOrder.LITTLE_ENDIAN); // SampleRate * NumChannels * BitsPerSample/8
	ShortWAVField BlockAlign = new ShortWAVField(ByteOrder.LITTLE_ENDIAN); // NumChannels * BitsPerSample/8
	ShortWAVField BitsPerSample = new ShortWAVField(ByteOrder.LITTLE_ENDIAN); // 8, 16, etc

	// data subchunk
	IntWAVField Subchunk2ID = new IntWAVField(ByteOrder.BIG_ENDIAN); // "data"
	IntWAVField Subchunk2Size = new IntWAVField(ByteOrder.LITTLE_ENDIAN); // number of bytes in Sound field
	WAVBody Sound = new WAVBody(ByteOrder.LITTLE_ENDIAN); // Sound data, stored as 4 bytes per sample (if two channel, 2 bytes per channel) or 2 bytes per sample (if one channel)

	public WAVWriter(String path, AudioFormat format) {
		super(path);
		// RIFF header
		this.ChunkID.setBytes("RIFF");
		// Do not set ChunkSize because value is unknown currently, equals 4 + (8 + Subchunk1Size.getInt()) + (8 + Subchunk2Size.getInt());
		this.Format.setBytes("WAVE");
		
		// fmt subchunk
		this.Subchunk1ID.setBytes("fmt ");
		if (format.getEncoding() == Encoding.PCM_SIGNED || format.getEncoding() == Encoding.PCM_UNSIGNED) {
			this.Subchunk1Size.setInt(16); // Always PCM
			this.AudioFormatField.setShort((short) 1); // Always PCM
		} else {
			throw new RuntimeException("Currently, this class only supports PCM data");
		}
		this.NumChannels.setShort((short) format.getChannels()); 
		this.SampleRate.setInt((int) format.getSampleRate());
		this.BitsPerSample.setShort((short) format.getSampleSizeInBits());
		int byteRate = this.SampleRate.getInt() * this.NumChannels.getShort() * this.BitsPerSample.getShort() / 8;
		this.ByteRate.setInt(byteRate);
		short blockAlign = (short) (this.NumChannels.getShort() * this.BitsPerSample.getShort() / 8);
		this.BlockAlign.setShort(blockAlign);
		
		// data subchunk
		this.Subchunk2ID.setBytes("data");
	}

	@Override
	public void write(byte[] data) {
		this.Sound.appendBytes(data);
	}
	
	@Override
	public void close() {
		this.Subchunk2Size.setInt(this.Sound.getBytes().length);
		this.ChunkSize.setInt(4 + (8 + Subchunk1Size.getInt()) + (8 + Subchunk2Size.getInt()));
		System.out.println(ChunkSize.getInt());
		
		// RIFF Header
		super.write(this.ChunkID.getBytes());
		super.write(this.ChunkSize.getBytes());
		super.write(this.Format.getBytes());
		// ftm subchunk
		super.write(this.Subchunk1ID.getBytes());
		super.write(this.Subchunk1Size.getBytes());
		super.write(this.AudioFormatField.getBytes());
		super.write(this.NumChannels.getBytes());
		super.write(this.SampleRate.getBytes());
		super.write(this.ByteRate.getBytes());
		super.write(this.BlockAlign.getBytes());
		super.write(this.BitsPerSample.getBytes());
		// data subchunk
		super.write(this.Subchunk2ID.getBytes());
		super.write(this.Subchunk2Size.getBytes());
		super.write(this.Sound.getBytes());
		
		System.out.println("Wrote " + this.Subchunk2Size.getInt() + " bytes of sound data");
		super.close();
	}
}
