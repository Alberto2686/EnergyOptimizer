package energyOptimizer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;

import energyOptimizer.elements.*;

public class Utils {

	public static String getHash(String text) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(text.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			// If needed, insert zeros to get 32 chars
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getVersionFromDate() {
		Calendar cal = Calendar.getInstance();
		String day = "0" + cal.get(Calendar.DATE);
		String month = "0" + (cal.get(Calendar.MONTH) + 1);
		String year = cal.get(Calendar.YEAR) + "";
		return year + month.substring(month.length() - 2, month.length()) + day.substring(day.length() - 2, day.length());
	}

	public static double consumptionCPU(Cpu cpu, UsageCPU usageCPU, double defaultCpuScore, List<AtomicOperation> atomicOperations, List<AtomicOperationConsumption> atomicOperationConsumptions) {
		double f1Min = Double.MAX_VALUE, f2Min = Double.MAX_VALUE, f3Min = Double.MAX_VALUE;
		for (FrequencyVoltage frequencyVoltage : cpu.getFrequenciesVoltages()) {
			double fv2 = frequencyVoltage.getFrequency() * Math.pow(frequencyVoltage.getVoltage(), 2)/(cpu.getFrequencyVoltageTDP().getFrequency() * Math.pow(cpu.getFrequencyVoltageTDP().getVoltage(), 2));
			double time1 = (usageCPU.getEstimatedMilliseconds() / 1000.0) * defaultCpuScore / frequencyVoltage.getPerformanceScore();
			double time2 = 0;
			for (ExactTime et : usageCPU.getExactTimes())
				if (et.getCpu().equals(cpu))
					time2 = et.getMilliseconds() / 1000.0;
			double f1 = 0.7 * cpu.getTdp() * fv2 * usageCPU.getUtilization() * time1;
			double f2 = 0.7 * cpu.getTdp() * fv2 * usageCPU.getUtilization() * time2;
			double f3 = 0;
			for (AtomicOperation ao : atomicOperations)
				for (AtomicOperationConsumption aoc : atomicOperationConsumptions)
					if (ao.getId().equals(aoc.getId()) && aoc.getCpu().equals(cpu))
						f3 += ao.getNumber() * aoc.getCost();
			if (f1 < f1Min & f1 != 0)
				f1Min = f1;
			if (f2 < f2Min & f2 != 0)
				f2Min = f2;
			if (f3 < f3Min & f3 != 0)
				f3Min = f3;
		}
		if (f3Min != Double.MAX_VALUE && f3Min != 0)
			return f3Min;
		if (f2Min != Double.MAX_VALUE && f2Min != 0)
			return f2Min;
		if (f1Min != Double.MAX_VALUE && f1Min != 0)
			return f1Min;
		return -1;
	}

	public static double consumptionHDD(Hdd hdd, UsageHDD usageHDD) {
		double f1 = (usageHDD.getEstimatedMilliseconds() / 1000) * (usageHDD.getUtilization() * hdd.getWorkConsumption() + (1 - usageHDD.getUtilization()) * hdd.getIdleConsumption());
		double f2 = (usageHDD.getSize().getMegaBites() / hdd.getBandwidth()) * hdd.getWorkConsumption();
		if (f2 != 0)
			return f2;
		if (f1 != 0)
			return f1;
		return -1;
	}

	public static double consumptionMemory(Memory memory, UsageMemory usageMemory) {
		double f1 = (usageMemory.getEstimatedMilliseconds() / 1000) * (usageMemory.getUtilization() * memory.getWorkConsumption() + (1 - usageMemory.getUtilization()) * memory.getIdleConsumption());
		double f2 = (usageMemory.getSize().getMegaBites() / memory.getBandwidth()) * memory.getWorkConsumption();
		if (f2 != 0)
			return f2;
		if (f1 != 0)
			return f1;
		return -1;
	}

	public static double consumptionNetwork(Size size, List<Network> networkDevicesSender, List<Network> networkDevicesReceiver) {
		double consumptionSender = consumptionNetwork(size, networkDevicesSender);
		double consumptionReceiver = consumptionNetwork(size, networkDevicesReceiver);
		if (consumptionSender != -1 && consumptionReceiver != -1)
			return consumptionSender + consumptionReceiver;
		return -1;
	}

	private static double consumptionNetwork(Size size, List<Network> networkDevices) {
		double consumptionMin = Double.MAX_VALUE, consumption = 0;
		for (Network networkDevice : networkDevices) {
			consumption = size.getMegaBites() * networkDevice.getMBConsumption();
			if (consumption != 0 && consumption < consumptionMin)
				consumptionMin = consumption;
		}
		if (consumptionMin == Double.MAX_VALUE)
			return -1;
		return consumptionMin;
	}

	public static double[] consumptionOther(Other otherAlternative) {
		double consumption[] = { 0, 0 };
		consumption[0] = otherAlternative.getEnergyPoints();
		consumption[1] = otherAlternative.getBusses() + otherAlternative.getCooling() + otherAlternative.getDisplay() + otherAlternative.getPeripheralDevices() + otherAlternative.getSensors() + otherAlternative.getUps();
		for (OtherConsumption otherConsumption : otherAlternative.getOtherConsumption())
			consumption[1] += otherConsumption.getConsumption();

		return consumption;
	}

	public static double[] consumptionPlatform(Platform platform) {
		double consumption[] = { 0, 0 };
		consumption[0] = platform.getEnergyPoints();
		consumption[1] = platform.getFramework() + platform.getGc() + platform.getJvm() + platform.getOs() + platform.getScheduling() + platform.getVirtualization();
		return consumption;
	}

	public static void writeFile(String savePath, String name, String content) {
		String path = savePath + "/" + name;

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			PrintStream printStream = new PrintStream(fileOutputStream);
			printStream.print(content);
			printStream.close();

		} catch (IOException e) {
			System.out.println("Error writing file: " + e);
		}
	}

	public static HardwareAlternative selectCheapest(List<HardwareAlternative> hardwareAlternatives) {
		HardwareAlternative cheapest = hardwareAlternatives.get(0);
		HardwareComponent cheapestComponent = hardwareAlternatives.get(0).getHardwareComponents().get(0);
		for (HardwareAlternative hardwareAlternative : hardwareAlternatives)
			for (HardwareComponent hardwareComponent : hardwareAlternative.getHardwareComponents())
				if (hardwareComponent.getConsumptionIndicator() < cheapestComponent.getConsumptionIndicator()) {
					cheapestComponent = hardwareComponent;
					cheapest = hardwareAlternative;
				}
		return cheapest;
	}
}