package com.fastfreesky.www.tool.file;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;

import com.fastfreesky.www.interfaces.DialLineI;

public class FileOperator {

	public static void readFromFile(String srcFile, DialLineI lineMethod) {
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
}
