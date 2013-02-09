package energyoptimizer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

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
	
	public static double ConsumptionCPU(Cpu cpu, UsageCPU usageCPU,double defaultCpuScore){
		double f1Min=Double.MAX_VALUE;
		double f2Min=Double.MAX_VALUE;
		for(FrequencyVoltage frequencyVoltage:cpu.getFrequenciesVoltages()){
			double fv2=frequencyVoltage.getFrequency()*Math.pow(frequencyVoltage.getVoltage(), 2);
			double time1=usageCPU.getEstimatedMilliseconds()*defaultCpuScore/frequencyVoltage.getPerformanceScore();
			double time2=0;
			for(ExactTime et:usageCPU.getExactTimes())
				if(et.getCpu().equals(cpu))
					time2=et.getMilliseconds();
			double f1=0.7*cpu.getTdp()*fv2*usageCPU.getUtilization()*time1;
			double f2=0.7*cpu.getTdp()*fv2*usageCPU.getUtilization()*time2;
			if(f1<f1Min)
				f1Min=f1;
			if(f2<f2Min)
				f2Min=f2;
		}
		if(f2Min!=Double.MAX_VALUE&&f2Min!=0)
			return f2Min;
		if(f1Min!=Double.MAX_VALUE&&f1Min!=0)
			return f1Min;
		return -1;
	}

	public static double ConsumptionHDD(Hdd hdd, UsageHDD usageHDD) {
		double f1=usageHDD.getEstimatedMilliseconds()*(usageHDD.getUtilization()*hdd.getWorkConsumption()+(1-usageHDD.getUtilization())*hdd.getIdleConsumption());
		double f2=usageHDD.getSize().getSize()*hdd.getBandwidth()*hdd.getWorkConsumption();
		if(f2!=0)
			return f2;
		if(f1!=0)
			return f1;
		return -1;
	}

	public static double ConsumptionMemory(Memory memory, UsageMemory usageMemory) {
		double f1=usageMemory.getEstimatedMilliseconds()*(usageMemory.getUtilization()*memory.getWorkConsumption()+(1-usageMemory.getUtilization())*memory.getIdleConsumption());
		double f2=usageMemory.getSize().getSize()*memory.getBandwidth()*memory.getWorkConsumption();
		if(f2!=0)
			return f2;
		if(f1!=0)
			return f1;
		return -1;
	}

	public static double ConsumptionNetwork(long size, List<Network> networkDevicesSender, List<Network> networkDevicesReceiver) {
		double consumptionSender=ConsumptionNetwork(size, networkDevicesSender);
		double consumptionReceiver=ConsumptionNetwork(size, networkDevicesReceiver);
		if(consumptionSender!=-1&&consumptionReceiver!=-1)
			return consumptionSender+consumptionReceiver;
		return -1;
	}
	
	private static double ConsumptionNetwork(long size, List<Network> networkDevices){
		double consumptionMin=Double.MAX_VALUE,consumption=0;
		for(Network networkDevice : networkDevices){
			consumption=size*networkDevice.getBandwidth()*networkDevice.getWorkConsumption();
			if(consumption!=0&&consumption<consumptionMin)
				consumptionMin=consumption;
		}
		if(consumptionMin==Double.MAX_VALUE)
			return -1;
		return consumptionMin;
	}
}