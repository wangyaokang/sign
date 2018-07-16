package com.wyk.opencv;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCV {

	public static void findTemplete(String inFile, String templateFile, String outFile) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread(inFile, 0);
		Mat templ = Imgcodecs.imread(templateFile, 0);
		int result_cols = img.cols() - templ.cols() + 1;
		int result_rows = img.rows() - templ.rows() + 1;
		Mat result = new Mat(result_rows, result_cols, CvType.CV_8UC1);
		Imgproc.matchTemplate(img, templ, result, Imgproc.TM_CCOEFF_NORMED);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc = mmr.minLoc;
		System.out.println("x: " + matchLoc.x + " ; y : " + matchLoc.y);
		Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()), new Scalar(0, 255, 0));
		System.out.println("Writing " + outFile);
		Imgcodecs.imwrite(outFile, img);
	}

	public static void main(String[] args) {
		String inFile = "D:/project/spider-os-v3/temp_file/open.captcha.qq.com/F99B505DCFEF6C0BA84B07E9A52E0112/bkg.jpg";
		String templateFile = "D:/project/spider-os-v3/temp_file/open.captcha.qq.com/F99B505DCFEF6C0BA84B07E9A52E0112/block.jpg";
		String outFile = "C:/Users/Win7/Desktop/result.jpg";
		findTemplete(inFile, templateFile, outFile);
	}
	
}
