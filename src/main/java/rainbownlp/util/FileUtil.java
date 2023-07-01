
package rainbownlp.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class FileUtil {
	public static final String DEBUG_FILE = "debug.log";
	/**
	 * Create new file if not exists
	 * @param path
	 * @return true if new file created
	 */
	public static boolean createFileIfNotExists(String path) {
		boolean result = false;
		File file = new File(path);
		if (!file.exists()) {
			try {
				result = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	 
	 public static void appendLine(String path, String line) throws UnsupportedEncodingException, FileNotFoundException
	 {
		 File file = new File(path);
		 Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream(file, true), "UTF-8"));
			
			try {
				writer.write(line+"\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	public static void createFilewithFormat(String path,
				List<String> contentLines,String format) throws UnsupportedEncodingException, FileNotFoundException {

			File file = new File(path);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), format));
			
			try {
				
				for(int i=0;i<contentLines.size();i++)
				{
					String line = contentLines.get(i);
					out.write(line+"\n");
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}