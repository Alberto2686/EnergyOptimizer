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
			// Eventually, insert zeros to get 32 chars
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
		}
		return hashtext;
		}catch(Exception e){}
		return null;
	}
	
	public static double ConsumptionCPU(Cpu cpu, UsageCPU usageCPU){
		double fv2Min=Double.MAX_VALUE;
		for(FrequencyVoltage frequencyVoltage:cpu.getFrequenciesVoltages()){
			
			double fv2=frequencyVoltage.getFrequency()*Math.pow(frequencyVoltage.getVoltage(), 2);
			if (fv2<fv2Min)
				fv2Min=fv2;
		}
		double f1=0.7*cpu.getTdp()*fv2Min*usageCPU.getUtilization()*usageCPU.getEstimatedMilliseconds();
		return 0;
	}
}
