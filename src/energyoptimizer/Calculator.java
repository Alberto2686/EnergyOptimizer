package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Calculator {
	Project project;

	public Calculator(Project project) {
		this.project = project;
	}

	public double[] calculateEnergyConsumption(SoftwareSystem system, FunctionalRequirement functionalRequirement, SequenceAlternative sequenceAlternative) {
		double consumption[] = { 0, 0 };
		for (Message message : sequenceAlternative.getMessages()) {
			double[] componentConsumption = calculateComponentConsumption(message.getReceiver(), system);
			consumption[0] += componentConsumption[0];
			if (componentConsumption[1] != -1)
				consumption[1] += componentConsumption[1];
			if (usesNetwork(message, system)) {
				double[] networkConsumption = calculateNetworkConsumption(message, system);
				consumption[0] += networkConsumption[0];
				if (networkConsumption[1] != -1)
					consumption[1] += networkConsumption[1];
				else
					project.setWReliable(false);
			}
		}
		consumption[0] *= functionalRequirement.getCoefficient();
		consumption[1] *= functionalRequirement.getCoefficient();
		return consumption;
	}

	public double[] calculatePlatformAndOtherConsuptions(SoftwareSystem system, FunctionalRequirement functionalRequirement) {
		double consumption[] = { 0, 0 };
		for (HardwareSetAlternative hardwareSetAlternative : system.getHardwareSystem().getHardwareSetAlternatives())
			if (system.getActuallyUsedHardwareSets().contains(hardwareSetAlternative.getHardwareSet())) {
				double consumptionOther[] = Utils.consumptionOther((Other) hardwareSetAlternative.getOtherAlternative().getHardwareComponents().toArray()[0]);
				double consumptionPlatform[] = Utils.consumptionPlatform((Platform) hardwareSetAlternative.getPlatformAlternative().getHardwareComponents().toArray()[0]);
				consumption[0] += consumptionOther[0] + consumptionPlatform[0];
				consumption[1] += consumptionOther[1] + consumptionPlatform[1];
			}
		consumption[0] *= functionalRequirement.getCoefficient();
		consumption[1] *= functionalRequirement.getCoefficient();

		return consumption;
	}

	private boolean usesNetwork(Message message, SoftwareSystem system) {
		try {
			Component sender = (Component) message.getSender();
			Component receiver = (Component) message.getReceiver();
			HardwareSet senderHS = null, receiverHS = null;
			for (DeployedComponent deployedComponent : system.getDeploymentAlternative().getDeployedComponents()) {
				if (deployedComponent.getComponent().equals(sender))
					senderHS = deployedComponent.getHardwareSet();
				if (deployedComponent.getComponent().equals(receiver))
					receiverHS = deployedComponent.getHardwareSet();
			}
			return (senderHS != null && receiverHS != null && !senderHS.equals(receiverHS));
		} catch (Exception e) {
		}
		return false;
	}

	// retrieves network devices and updates the used HWset list if needed
	private List<Network> getNetworkDevices(Component component, SoftwareSystem system) {
		HardwareSet hardwareSet = null;
		List<Network> networkDevices = new LinkedList<>();
		for (DeployedComponent deployedComponent : system.getDeploymentAlternative().getDeployedComponents())
			if (deployedComponent.getComponent().equals(component))
				hardwareSet = deployedComponent.getHardwareSet();
		if (!system.getActuallyUsedHardwareSets().contains(hardwareSet))
			system.getActuallyUsedHardwareSets().add(hardwareSet);
		for (HardwareSetAlternative hardwareSetAlternative : system.getHardwareSystem().getHardwareSetAlternatives())
			if (hardwareSetAlternative.getHardwareSet().equals(hardwareSet))
				for (HardwareComponent networkDevice : hardwareSetAlternative.getNetworkAlternative().getHardwareComponents())
					networkDevices.add((Network) networkDevice);
		return networkDevices;
	}

	private double[] calculateComponentConsumption(LifelineElement receiver, SoftwareSystem system) {
		double componentConsumption[] = { -1, -1 };
		try {
			Component component = (Component) receiver;
			HardwareSetAlternative hardwareSetAlternative = null;
			for (DeployedComponent dc : system.getDeploymentAlternative().getDeployedComponents())
				if (dc.getComponent().equals(component))
					for (HardwareSetAlternative hsa : system.getHardwareSystem().getHardwareSetAlternatives())
						if (dc.getHardwareSet().equals(hsa.getHardwareSet()))
							hardwareSetAlternative = hsa;
			double componentConsumptionCPU[] = calculateCPUConsumption(component, hardwareSetAlternative.getCpuAlternative());
			double componentConsumptionHDD[] = calculateHDDConsumption(component, hardwareSetAlternative.getHddAlternative());
			double componentConsumptionMemory[] = calculateMemoryConsumption(component, hardwareSetAlternative.getMemoryAlternative());
			componentConsumption[0] = componentConsumptionCPU[0] + componentConsumptionHDD[0] + componentConsumptionMemory[0];
			if (componentConsumptionCPU[1] == -1 || componentConsumptionHDD[1] == -1 || componentConsumptionMemory[1] == -1)
				project.setWReliable(false);
			if (componentConsumptionCPU[1] != -1)
				componentConsumption[1] += componentConsumptionCPU[1];
			if (componentConsumptionHDD[1] != -1)
				componentConsumption[1] += componentConsumptionHDD[1];
			if (componentConsumptionMemory[1] != -1)
				componentConsumption[1] += componentConsumptionMemory[1];
		} catch (Exception e) {
		}
		return componentConsumption;
	}

	private double[] calculateNetworkConsumption(Message message, SoftwareSystem system) {
		double consumption[] = { 0, -1 };
		for (Connector connector : project.getConnectors())
			if (connector.getComponent().equals(message.getSender()) && connector.getToInterface().equals(message.getSignature())) {
				consumption[0] = connector.getEnergyPoints();
				consumption[1] = Utils.consumptionNetwork(connector.getSize().getBites(), getNetworkDevices((Component) message.getSender(), system), getNetworkDevices((Component) message.getReceiver(), system));
			}
		return consumption;
	}

	private double[] calculateCPUConsumption(Component component, HardwareAlternative hardwareAlternative) {
		double consumption[] = { -1, -1 };
		consumption[0] = component.getUsageCPU().getEnergyPoints();
		for (HardwareComponent cpu : hardwareAlternative.getHardwareComponents()) {
			Double temp = Utils.consumptionCPU((Cpu) cpu, component.getUsageCPU(), project.getDefaultCpuScore(), component.getAtomicOperations(), component.getAtomicOperationConsumptions());
			if (temp != -1 && (temp < consumption[1] || consumption[1] == -1))
				consumption[1] = temp;
		}
		return consumption;
	}

	private double[] calculateHDDConsumption(Component component, HardwareAlternative hardwareAlternative) {
		double consumption[] = { -1, -1 };
		consumption[0] = component.getUsageHDD().getEnergyPoints();
		for (HardwareComponent hdd : hardwareAlternative.getHardwareComponents()) {
			Double temp = Utils.consumptionHDD((Hdd) hdd, component.getUsageHDD());
			if (temp != -1 && (temp < consumption[1] || consumption[1] == -1))
				consumption[1] = temp;
		}
		return consumption;
	}

	private double[] calculateMemoryConsumption(Component component, HardwareAlternative hardwareAlternative) {
		double consumption[] = { -1, -1 };
		consumption[0] = component.getUsageMemory().getEnergyPoints();
		for (HardwareComponent memory : hardwareAlternative.getHardwareComponents()) {
			Double temp = Utils.consumptionMemory((Memory) memory, component.getUsageMemory());
			if (temp != -1 && (temp < consumption[1] || consumption[1] == -1))
				consumption[1] = temp;
		}
		return consumption;
	}
}