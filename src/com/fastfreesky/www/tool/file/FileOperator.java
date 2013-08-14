package com.fastfreesky.www.tool.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;

import com.fastfreesky.www.interfaces.DialLineI;

public class FileOperator {

	public static void readFromHadoopFile(String srcFile, DialLineI lineMethod) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static void readFromLocalFile(String srcFile, DialLineI lineMethod) {

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

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
