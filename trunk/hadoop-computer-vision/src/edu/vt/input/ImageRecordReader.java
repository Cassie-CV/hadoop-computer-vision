package edu.vt.input;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import static com.googlecode.javacv.jna.cxcore.v21.*;
import static com.googlecode.javacv.jna.highgui.v21.*;

public class ImageRecordReader extends RecordReader<Text, Image>{

	private static final Log LOG = LogFactory.getLog(ImageRecordReader.class);
	
	private float status;
	private Text key = null;
	private Image value = null;
	private String fileName = null;
	
	@Override
	public void close() throws IOException {
		
	}

	@Override
	public Text getCurrentKey() throws IOException,
			InterruptedException {

		return key;
	}

	@Override
	public Image getCurrentValue() throws IOException, InterruptedException {

		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {

		return status;
	}

	@Override
	public void initialize(InputSplit genericSplit, TaskAttemptContext context)
			throws IOException, InterruptedException {
		FileSplit split = (FileSplit) genericSplit;

		fileName = split.getPath().toString();
		
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		/*
		// load image
		IplImage img1 = null;
		
		// Calculate new ROI
		
		// sets the Region of Interest
		cvSetImageROI(img1, cvRect(10, 15, 150, 250));
		
		// create destination image
		IplImage img2 = cvCreateImage(cvGetSize(img1), img1.depth, img1.nChannels);
		
		// copy sub-image
		cvCopy(img1, img2, null);
		
		// reset the Region of Interest
		cvResetImageROI(img1);
		*/
		
		if(status != 100){
		
			key = new Text(fileName);
			value = new Image(cvLoadImage(fileName, 1));
			
			status = 100;
			return true;
		}
		
		return false;
	}

}