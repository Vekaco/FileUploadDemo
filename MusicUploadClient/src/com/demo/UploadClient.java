package com.demo;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadClient {
	public static void main(String[] args) {

		try {
			String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113"; // Could
																		// be
																		// any
																		// string
			String Enter = "\r\n";

			File xml = new File("/Users/KaikaiFu/Music/网易云音乐/2017-04-27~2~11:23~10.25~.mp3");
			FileInputStream fis = new FileInputStream(xml);

			URL url = new URL("http://112.74.198.75/wanan.v1/voice");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			conn.connect();

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			// part 1
			String part1 = "--" + boundary + Enter + "Content-Type: application/octet-stream" + Enter
					+ "Content-Disposition: form-data; filename=\"" + xml.getName() + "\"; name=\"file\"" + Enter
					+ Enter;
			// part 2
			String part2 = Enter + "--" + boundary + Enter + "Content-Type: text/plain" + Enter
					+ "Content-Disposition: form-data; name=\"dataFormat\"" + Enter + Enter + "hk" + Enter + "--"
					+ boundary + "--";

			byte[] xmlBytes = new byte[fis.available()];
			fis.read(xmlBytes);

			dos.writeBytes(part1);
			dos.write(xmlBytes);
			dos.writeBytes(part2);

			dos.flush();
			dos.close();
			fis.close();

			System.out.println("status code: " + conn.getResponseCode());
			System.out.println(conn.getInputStream().toString());

			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
