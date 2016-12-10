package fileWriters;

import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

import wrappers.PrimativeConverter;
public class WAVWriter extends FileWriter {

	boolean wroteHeader = false;
	// RIFF header
	WAVField<Integer> ChunkID = new WAVField<Integer>(ByteOrder.BIG_ENDIAN); // "RIFF"
	WAVField<Integer> ChunkSize = new WAVField<Integer>(ByteOrder.LITTLE_ENDIAN); // 4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
	WAVField<Integer> Format = new WAVField<Integer>(ByteOrder.BIG_ENDIAN); // "WAVE"

	// fmt subchunk
	WAVField<Integer> Subchunk1ID = new WAVField<Integer>(ByteOrder.BIG_ENDIAN); // "fmt "
	WAVField<Integer> Subchunk1Size = new WAVField<Integer>(ByteOrder.LITTLE_ENDIAN); // 16 if PCM
	WAVField<Short> AudioFormatField = new WAVField<Short>(ByteOrder.LITTLE_ENDIAN); // 1 if PCM
	WAVField<Short> NumChannels = new WAVField<Short>(ByteOrder.LITTLE_ENDIAN); // 1 or 2
	WAVField<Integer> SampleRate = new WAVField<Integer>(ByteOrder.LITTLE_ENDIAN); // 8000, 44100
	WAVField<Integer> ByteRate = new WAVField<Integer>(ByteOrder.LITTLE_ENDIAN); // SampleRate * NumChannels * BitsPerSample/8
	WAVField<Short> BlockAlign = new WAVField<Short>(ByteOrder.LITTLE_ENDIAN); // NumChannels * BitsPerSample/8
	WAVField<Short> BitsPerSample = new WAVField<Short>(ByteOrder.LITTLE_ENDIAN); // 8, 16, etc

	// data subchunk
	WAVField<Integer> Subchunk2ID = new WAVField<Integer>(ByteOrder.BIG_ENDIAN); // "data"
	WAVField<Integer> Subchunk2Size = new WAVField<Integer>(ByteOrder.LITTLE_ENDIAN); // number of bytes in Sound field
	WAVBody Sound = new WAVBody(ByteOrder.LITTLE_ENDIAN); // Sound data, stored as 4 bytes per sample (if two channel, 2 bytes per channel) or 2 bytes per sample (if one channel)

	public WAVWriter(String path, AudioFormat format) {
		super(path);
		// RIFF header
		this.ChunkID.setBytes(PrimativeConverter.StringToBytes("RIFF"));
		// Do not set ChunkSize because value is unknown currently, equals 4 + (8 + Subchunk1Size.get()) + (8 + Subchunk2Size.get());
		this.Format.setBytes(PrimativeConverter.StringToBytes("WAVE"));
		
		// fmt subchunk
		this.Subchunk1ID.setBytes(PrimativeConverter.StringToBytes("fmt "));
		if (format.getEncoding() == Encoding.PCM_SIGNED || format.getEncoding() == Encoding.PCM_UNSIGNED) {
			this.Subchunk1Size.set(16); // Always PCM
			this.AudioFormatField.set((short) 1); // Always PCM
		} else {
			throw new RuntimeException("Currently, this class only supports PCM data");
		}
		this.NumChannels.set((short) format.getChannels()); 
		this.SampleRate.set((int) format.getSampleRate());
		this.BitsPerSample.set((short) format.getSampleSizeInBits());
		int byteRate = this.SampleRate.get() * this.NumChannels.get() * this.BitsPerSample.get() / 8;
		this.ByteRate.set(byteRate);
		short blockAlign = (short) (this.NumChannels.get() * this.BitsPerSample.get() / 8);
		this.BlockAlign.set(blockAlign);
		
		// data subchunk
		this.Subchunk2ID.setBytes(PrimativeConverter.StringToBytes("data"));
	}

	public void write(byte[] data, ByteOrder order) {
		this.Sound.appendBytes(data, order);
	}
	
	@Override
	public void close() {
		this.Subchunk2Size.set(this.Sound.getBytes().length);
		this.ChunkSize.set(4 + (8 + Subchunk1Size.get()) + (8 + Subchunk2Size.get()));
		System.out.println(ChunkSize.get());
		
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
		
		System.out.println("Wrote " + this.Subchunk2Size.get() + " bytes of sound data");
		super.close();
	}
}
