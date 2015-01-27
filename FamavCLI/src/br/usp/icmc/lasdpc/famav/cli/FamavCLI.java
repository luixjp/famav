
/*
 * Copyright (C) 2014 FAMaV-CLI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.usp.icmc.lasdpc.famav.cli;

import java.util.ArrayList;

import org.libvirt.MemoryStatistic;

import br.usp.icmc.lasdpc.famav.control.connection.Connection;
import br.usp.icmc.lasdpc.famav.control.resources.*;
import br.usp.icmc.lasdpc.famav.control.status.*;
import br.usp.icmc.lasdpc.famav.monitor.MonitorVM;

public class FamavCLI {

	/**
	 * @author Yuri Toledo
	 */
	
	
	//args[0] = Command 
	//args[1] = Name/id vm
	//args[2] = parameter
	
	public static void main(String[] args) throws Throwable {
		
		Connection cnn = new Connection(); //Creates a new local connection
		cnn.createConnection();
		String param = (args.length < 3) ? "" : args[2];  
		
		int local = 0;
		
		//Creates a new non-local connection
		if(args[0].equals("-nl"))
		{
			local = 1;			
		}
		
		//Returns to a local connection
		if(args[0].equals("-local"))
		{		
				local = 2;			
		}
		
		//Case struct to select between a local and a non local connection
		switch(local)
		{
		case 1:
			cnn.createConnection(args[1]);
			param = (args.length < 5) ? "" : args[4];
			System.out.println("Conexão não local estabelecida");
			for(int i = 0 ; i < args.length - 2 ; i++)
			{
				args[i] = args[i+2];
				args[i+2] = null;
			}
			break;
		case 2:
			cnn.createConnection();
			System.out.println("Conexão local estabelecida");
			break;
		}
		
		//Starts the VM by name
		if(args[0].equals("-poweron")) 
		{
			PowerOn po = new PowerOn(cnn);
			po.startVMbyName(args[1]); // args[1] = VM's name
		}
		
		//Starts a VM's list by name
		//Example: -poweronlist VM1 VM2 VM3 VMn (Separete VM's names just with an space) - Equal in all VM's list methods
		else if(args[0].equals("-poweronlist")) //Feito
		{
			ArrayList<String> listVMS = new ArrayList<String>(); //Starts AraryList
			
			
			for(int i = 1; i<args.length; i++ )//Adds the parameters to the ArrayList
			{
				listVMS.add(args[i]); // args[i] = VM's name
			}
			
			PowerOn po = new PowerOn(cnn);
			po.startVMListByName(listVMS);
		}
		
		
		//Turn it off the VM by name
		else if(args[0].equals("-poweroff")) 
		{
			PowerOff po = new PowerOff(cnn);
			po.powerOffVMbyName(args[1]); // args[1] = VM's name
		}
		
		//Turn ir off the VM by its ID
		else if(args[0].equals("-poweroffid"))
		{
			PowerOff po = new PowerOff(cnn);
			po.powerOffVM(Integer.parseInt(args[1])); // args[1] = VM's ID
		}
		
		//Turn it off the VM's list 
		else if(args[0].equals("-powerofflist"))
		{
			ArrayList<String> listVMS = new ArrayList<String>(); //Starts AraryList
			
			
			for(int i = 1; i<args.length; i++ )//Adds the parameters ArrayList
			{
				listVMS.add(args[i]); // args[i] = VM's name
			}
			
			PowerOff po = new PowerOff(cnn);
			po.poweroffVMListByName(listVMS);
		}
		
		//Hibernates the VM by name
		else if(args[0].equals("-hibernate"))
		{
			Hibernate hib = new Hibernate(cnn);
			hib.suspendVMbyName(args[1]); // args[1] = VM's name
		}
		
		//Hibernates the VM by ID
		else if(args[0].equals("-hibernateid"))
		{
			Hibernate hib = new Hibernate(cnn);
			hib.suspendVM(Integer.parseInt(args[1])); // args[1] = VM's ID
		}
		
		//Hibernates a VM's list
		else if(args[0].equals("-hibernatelist"))
		{
			ArrayList<String> listVMS = new ArrayList<String>(); //It starts a AraryList
			
			
			for(int i = 1; i<args.length; i++ )//Adds the parameters to the ArrayList
			{
				listVMS.add(args[i]); // args[i] = vm's name
			}
			
			Hibernate hib = new Hibernate(cnn);
			hib.hibernateVMListByName(listVMS);
		}
		
		// Calls back the vm from de hibernates state
		else if(args[0].equals("-resume"))
		{
			Resume res = new Resume(cnn);
			res.resumeVMbyName(args[1]);
		}
		
		
		//Restarts the VM
		else if(args[0].equals("-restart"))
		{
			Restart rest = new Restart(cnn);
			rest.restartVM(args[1]); // args[1] = vm's name 
		}
		
		//Restart a list of VMs
		else if(args[0].equals("-restartlist"))
		{
			ArrayList<String> listVMS = new ArrayList<String>(); //It starts the AraryList
			
			
			for(int i = 1; i<args.length; i++ )//Adds the parameters to the ArrayList
			{
				listVMS.add(args[i]); // args[i] = vm's name
			}
			
			Restart rest = new Restart(cnn);
			rest.restartVMListByName(listVMS);
		}
		
		//Change the VM's memory be the name
		//<changeMemory><name_vm><new_memory>
		else if(args[0].equals("-changememory")) 
		{
			Memory mem = new Memory(cnn, Long.parseLong(param)); // param = new vm's memory in kb
			mem.setMemorybyName(args[1]); // args[1] = vm's name
		}
		
		//Change the VM's memory be the ID
		else if(args[0].equals("-changememoryid")) 
		{
			Memory mem = new Memory(cnn, Long.parseLong(param)); // param = new vm's memory in kb
			mem.setMemory(Integer.parseInt(args[1])); // args[1] = vm's name 
		}
		
		//Percentual of the allocated memory
		else if(args[0].equals("-memoryalloc")) 
		{			
			MemoryStatistic[] memory;
			MonitorVM monitor = new MonitorVM(cnn, args[1], param);
			memory = monitor.allocMemory();
			System.out.println("Alloc Memory:");
			for (int i = 0; i < memory.length; i++ ) {
				System.out.println("Memory Tag: " + memory[i].getTag());
				System.out.println("Memory Value: " + memory[i].getValue() + "K");
				System.out.println("");
			}
		}
		
		//Domain Infos  (VM)
		else if(args[0].equals("-infostatus")) 
		{			
			StringBuilder info;
			MonitorVM monitor = new MonitorVM(cnn, args[1], param);
			info = monitor.getInfo();
			System.out.println("Info Status: ");
			System.out.println(info.toString());
		}
		
		
		//Maximum memory quantity
		else if(args[0].equals("-maxmemory"))
		{
			long memory;
			MonitorVM monitor = new MonitorVM(cnn, args[1], param); // args[1] = vm's name
			memory = monitor.maxMemory();			
			System.out.println("Max memory: " + memory); // Prints the maximum memory in kb
		}
		
		//Returns the percentual of the CPUs use 
		else if(args[0].equals("-usecpu")) 
		{
			double use_cpu;
			MonitorVM monitor = new MonitorVM(cnn, args[1], param);
			use_cpu = monitor.percentualOfUsage();
			System.out.println("Use of CPUs " + use_cpu +"%");
			
		}
		
		//Returns informations about the node
		else if(args[0].equals("-nodeinfo")) 
		{
			RealMachine rm = new RealMachine();
			System.out.println("Number of active cpus: "+rm.cpuNumber()+"\nCPU expected frequency: "+rm);
			System.out.println("CPU model: "+rm.cpuModel()+"\nMemory Size in Kb: "+rm.memorySize());
			System.out.println("Cores per socket: "+rm.coresPerSocket()+"\nSockets: "+rm.sockets());
		}
		
		//Returns the disk allocation 
		else if(args[0].equals("-diskallocation")) 
		{
			MonitorVM monitor = new MonitorVM(cnn, args[1], param);
			long disk = monitor.diskAllocation();
			System.out.println("Disk Allocation: "+ disk);
		}
		
		//The maximum disk 
		else if(args[0].equals("-maxdisk"))
		{
			MonitorVM monitor = new MonitorVM(cnn, args[1], param);
			long diskmax = monitor.diskCapacity();
			System.out.println("Max Disk: "+ diskmax);
		}
		
		//Lists the inactives VMs 
		else if(args[0].equals("-listinactives")) 
		{
			VirtualMachine vm = new VirtualMachine(cnn);
			String[] domains = vm.listInactiveDomains(); //String vector that receives the inactives vms names 
			int size = domains.length; //Size of the vector 
			System.out.println("Inactive Domains\n------------------------\nName:");
			
			for(int i = 0 ; i < size ; i++){ 
			System.out.println(domains[i]);
			}
			System.out.println("\n");
		}
		
		//Lists the actives VMs
		else if(args[0].equals("-listactives")) 
		{
			VirtualMachine vm = new VirtualMachine(cnn);
			ArrayList<String> domains = vm.listActiveDomains(); //ArrayList with name and ID of the actives VMs
			int size = domains.size();
			System.out.println("Active Domains\n---------------------------\nName:\t\tID:");
			
			for(int i = 0 ; i < size ; i++){
			System.out.println(domains.get(i));
			}
			System.out.println("\n");
		}
		
		//Lists all the VMs
		else if(args[0].equals("-listall")) //Feito
		{
			VirtualMachine vm = new VirtualMachine(cnn);
			
			String[] inactive = vm.listInactiveDomains(); ////String vector that receives the inactives vms names 
			ArrayList<String> active = vm.listActiveDomains(); //ArrayList with name and ID of the actives VMs
			
			int size = active.size();
			System.out.println("Active Domains\n---------------------------\nName:\t\tID:");
			// Printing the actives vms 
			for(int i = 0 ; i < size ; i++){
			System.out.println(active.get(i));
			}
			System.out.println("\n");
			
			size = inactive.length;
			System.out.println("Inactive Domains\n------------------------\nName:");
			// Printing the inactives vms
			for(int i = 0 ; i < size ; i++){
			System.out.println(inactive[i]);
			}
			System.out.println("\n");
		}
		
		else if(args[0].equals("-listnetworks")) 
		{
			Network nw = new Network(cnn);
			nw.activeNetworks();
		}
		
		/*
		 * args[1] = origins machine's name
		 * ar1gs[2] = destiny uri  
		 * args[3] = migration maximum bandwidth
		 */
		else if(args[0].equals("-migrate"))
			{			
				Migrate vm = new Migrate(cnn, args[2]);
				long bandwidth = Long.parseLong(args[3]);
				vm.MigrateDomain(args[1], args[2], bandwidth);
			
			}
		else
		{
			System.out.println("Command has not been found");
		}
		
	}

}
