package energyoptimizer;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Utils {
	public static String getHash(String text){
		try{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(text.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Insert zeros to get 32 chars
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
		}
		return hashtext;
		}catch(Exception e){}
		return null;
	}
}
