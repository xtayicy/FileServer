package harry.FileServer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * 
 * @author harry
 *
 */
public class MD5Util {
	@Test
	public void testSign() throws FileNotFoundException{
		System.out.println(sign(new FileInputStream(new File("sun.jpg"))));
	}
	
	public static String sign(InputStream in){
		try {
			byte[] b = new byte[1024];
			int len;
			StringBuffer sb = new StringBuffer();
			while((len = in.read(b)) != -1){
				sb.append(encode(b,0,len));
			}
			
			byte[] bytes = sb.toString().getBytes();
			return encode(bytes,0,bytes.length);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String encode(byte[] bytes,int start,int end){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(bytes);
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i < digest.length;i++){
				sb.append(Integer.toHexString(digest[i] & 0xFF));
			}
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
