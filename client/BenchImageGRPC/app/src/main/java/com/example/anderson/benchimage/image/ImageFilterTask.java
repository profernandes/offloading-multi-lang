package com.example.anderson.benchimage.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import com.example.anderson.benchimage.dao.ResultDao;
import com.example.anderson.benchimage.dao.model.AppConfiguration;
import com.example.anderson.benchimage.dao.model.ResultImage;
import com.example.anderson.benchimage.util.ImageUtils;
import com.example.anderson.benchimage.util.TaskResultAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ImageFilterTask extends AsyncTask<String, String, ResultImage> {
	private final String clsName = ImageFilterTask.class.getName();
	private PowerManager.WakeLock wakeLock;

	private Context context;
	private Filter filter;
	private AppConfiguration config;
	private TaskResultAdapter<ResultImage> taskResult;

	private ResultDao dao;
	private ResultImage result = null;

	public ImageFilterTask(Context context, Filter filter, AppConfiguration config,
						   TaskResultAdapter<ResultImage> taskResult) {
		this.context = context;
		this.filter = filter;
		this.config = config;
		this.taskResult = taskResult;

		result = new ResultImage(config);
		dao = new ResultDao(context);
	}

	@Override
	protected void onPreExecute() {
		preventSleep();
	}

	@Override
	protected ResultImage doInBackground(String... params) {

		try {
			String image_sizes[] = new String[]{ "0.3MP", "1MP" , "2MP", "4MP", "8MP" };
			if (!config.getSize().equals("All")) { image_sizes = new String[]{config.getSize()}; }
			if (config.getBenchmarkMode()) {
				benchmarkTask(image_sizes);
			} else {
				if (config.getFilter().equals("Original")) {
					originalTask();
				} else if (config.getFilter().equals("GreyScale")) {
					greyTask(image_sizes[0]);
				}
			}
			return result;
		} catch (IOException e) {
			Log.w(clsName, e);
		} catch (InterruptedException e) {
			Log.w(clsName, e);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		if (values.length == 1)
			taskResult.taskOnGoing(0, values[0]);
		else if (values.length == 2)
			taskResult.taskOnGoing(0, values[0], values[1]);
		else
			taskResult.taskOnGoing(0);
	}

	@Override
	protected void onPostExecute(ResultImage result) {
		wakeLock.release();
		taskResult.completedTask(result);
	}

	private String sizeToPath(String size) {
		if (size.equals("8MP")) {
			return "images/8mp/";
		} else if (size.equals("4MP")) {
			return "images/4mp/";
		} else if (size.equals("2MP")) {
			return "images/2mp/";
		} else if (size.equals("1MP")) {
			return "images/1mp/";
		} else if (size.equals("0.3MP")) {
			return "images/0_3mp/";
		}

		return null;
	}

	private String generatePhotoFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(config.getImage().replace(".jpg", "")).append("_").append(config.getFilter()).append("_");
		if (config.getSize().equals("0.7MP")) {
			sb.append("0_7mp.jpg");
		} else if (config.getSize().equals("0.3MP")) {
			sb.append("0_3mp.jpg");
		} else {
			sb.append(config.getSize()).append(".jpg");
		}
		return sb.toString();
	}

	private File saveResultOnStorage(byte data[]) throws IOException {
		File file = new File(config.getOutputDirectory(), generatePhotoFileName());
		OutputStream output = new FileOutputStream(file);
		output.write(data);
		output.flush();
		output.close();
		return file;
	}

	private void preventSleep() {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "benchimage:PicFilter CPU");
		wakeLock.acquire();
	}

	private void originalTask() throws IOException {
		long initialTime = System.currentTimeMillis();

		publishProgress("Loading image!");

		Bitmap image = decodeSampledBitmapFromResource(context, sizeToPath(config.getSize()) + config.getImage(), config.getSize());
		result.setTotalTime(System.currentTimeMillis() - initialTime);
		result.setBitmap(image);

		publishProgress("Image loaded!");
	}

	private void greyTask(String image_size) throws IOException {
		System.gc();

		long initialTime = System.nanoTime();

		byte imageResult[], img[] = ImageUtils.streamToByteArray(context.getAssets().
				open(sizeToPath(image_size) + config.getImage()));

		if (config.getLocal().equals("Local")) {
			FilterStrategy grayScale = ImageFilterFactory.getLocalMethod("Normal");
			imageResult = ImageUtils.encodeRawToJpeg(grayScale.applyFilter(ImageUtils.decodeJpegToRaw(img)));
			result.setOffTime(((GrayScaleFilter) grayScale).getOffloadingTime());
		} else {
			//System.out.println(config.getRpcMethod());
			RemoteFilterStrategy grayScale = ImageFilterFactory.getRemoteMethod(config.getRpcMethod(), config.getIpCloudlet(), 50051);
			imageResult = grayScale.applyFilter(img);
			result.setOffTime(grayScale.getOffloadingTime());
			grayScale.closeChannel();
		}

		File fileSaved = saveResultOnStorage(imageResult);
		result.setBitmap(decodeSampledBitmapFromResource(context, new FileInputStream(fileSaved), config.getSize()));

		dao.add(result);
		fileSaved.delete();

		publishProgress("GreyScale Completed!");

		long endTime = System.nanoTime();
		result.setTotalTime((endTime - initialTime) / 1000000);

		System.gc();
	}
	
	private void benchmarkTask(String image_sizes[]) throws IOException, InterruptedException {
		long totalTime = 0L;
		String to_print = "Round,Method,PhotoSize,TimeCelProc,TimeCelTotal\n";

		for (String size : image_sizes) {
			for (int i = 1; i <= 50; i++) {
				if (config.getFilter().equals("GreyScale")) {
					greyTask(size);
				}

				publishProgress("Benchmark Image " + size + " [" + i + "/50]", String.valueOf(result.getTotalTime()) + "ns");
				totalTime += result.getTotalTime();

				String aux = i + "," + config.getRpcMethod() + "," + size + "," + result.getOffTime() + "," + result.getTotalTime() + "\n";
				System.out.print(aux);
				to_print = to_print.concat(aux);
			}
		}

		result.setTotalTime(totalTime);
		result.getConfig().setSize("Todos");

		dao.add(result);

		ImageUtils.sendToServer(to_print, config.getIpCloudlet(), 5000);
		Thread.sleep(1000);

		publishProgress("Benchmark Completed!");
	}

	private Bitmap decodeSampledBitmapFromResource(Context context, InputStream is, String size) throws IOException {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inMutable = true;

		if (size.equals("1MP") || size.equals("2MP")) {
			options.inSampleSize = 2;
		} else if (size.equals("4MP")) {
			options.inSampleSize = 4;
		} else if (size.equals("6MP")) {
			options.inSampleSize = 6;
		} else if (size.equals("8MP")) {
			options.inSampleSize = 8;
		} else {
			options.inSampleSize = 1;
		}

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, options);
	}

	private Bitmap decodeSampledBitmapFromResource(Context context, String path, String size) throws IOException {
		return decodeSampledBitmapFromResource(context, context.getAssets().open(path), size);
	}
}
