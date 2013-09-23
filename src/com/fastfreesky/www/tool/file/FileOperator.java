package com.fastfreesky.www.tool.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;

import com.fastfreesky.www.area.interfaces.DialLineI;

public class FileOperator {

	public static void readFromHadoopFile(String srcFile, DialLineI lineMethod)
			throws IOException {
		Configuration conf = new Configuration();
		Path inPath = new Path(srcFile);

		FileSystem hdfs;
		LineReader in = null;
		try {
			hdfs = FileSystem.get(conf);
			FSDataInputStream dis = hdfs.open(inPath);
			in = new LineReader(dis, conf);
			Text line = new Text();
			while (in.readLine(line) > 0) {
				// 行读数据,进行处理
				lineMethod.dialLine(line.toString());
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void readFromLocalFile(String srcFile, DialLineI lineMethod)
			throws IOException {

		File file = new File(srcFile);
		FileReader reader;
		BufferedReader in = null;
		try {
			reader = new FileReader(file);
			in = new BufferedReader(reader);
			String line;
			while ((line = in.readLine()) != null) {
				// 行读数据,进行处理
				lineMethod.dialLine(line.toString());
			}

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void mkDirs(String dirPath, boolean isLocal) {
		if (isLocal) {
			File file = new File(dirPath);
			file.mkdirs();

		} else {
			try {
				Configuration conf = new Configuration();
				FileSystem fs = FileSystem.get(conf);

				fs.mkdirs(new Path(dirPath));
				fs.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {

		mkDirs(args[0], Boolean.parseBoolean(args[1]));
		// try {
		// readFromHadoopFile("D:\\input111.txt", null);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
