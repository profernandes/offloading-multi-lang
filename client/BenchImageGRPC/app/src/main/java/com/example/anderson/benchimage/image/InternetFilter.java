package com.example.anderson.benchimage.image;

import br.ufc.great.caos.api.offload.Offloadable;

public interface InternetFilter extends Filter {
	@Offloadable
	public byte[] mapTone(byte source[], byte map[]);

	@Offloadable
	public byte[] filterApply(byte source[], double filter[][], double factor, double offset);

	@Offloadable
	public byte[] cartoonizerImage(byte source[]);
}