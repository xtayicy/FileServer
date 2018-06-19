package harry.FileServer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * 
 * @author harry
 *
 */
public class MD5Util {
	public static String encode(byte[] bytes,int start,int end){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(bytes);
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i < digest.length;i++){
				int e = digest[i] & 0xFF;
				if(e < 16){
					sb.append(0);
				}
				
				sb.append(Integer.toHexString(e));
			}
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}