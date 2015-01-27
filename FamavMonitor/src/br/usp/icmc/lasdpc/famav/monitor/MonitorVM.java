
/*
 * Copyright (C) 2014 FAMaV
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

package br.usp.icmc.lasdpc.famav.monitor;

import br.usp.icmc.lasdpc.famav.control.connection.*;

import org.libvirt.*;

public class MonitorVM {
	
		MonitorDisk md;
		MonitorMemory mm;
		MonitorNetwork mn;
		MonitorProcessor mp;		
		MemoryStatistic[] ms;
		Connection connection;
		Connect conn;
		Domain vm;
		DomainInfo domInfo; 
		DomainJobInfo domJobInfo;
		
		
		
		public MonitorVM(Connection connection, String name, String devicePath) throws LibvirtException
		{
			this.connection = connection;
			
			conn = connection.getConnection();
			try{
				
			vm = conn.domainLookupByName(name);
						
			this.domInfo = vm.getInfo();
			this.domJobInfo = vm.getJobInfo();		
			this.ms = vm.memoryStats(vm.getID());
			
			}
			catch(LibvirtException e){
	            System.out.println("exception caught:"+e);
	            System.out.println(e.getError());
	            
			}
			
			md = new MonitorDisk(vm, devicePath);
			mm = new MonitorMemory(vm);
			mp = new MonitorProcessor(vm);
			
		}
		
		
		public MemoryStatistic[] allocMemory() 
		{
			mm.allocMemory();
			return ms; //Allocated memory in %
		}
	
		public long maxMemory()
		{
			return mm.maxMemory(); //Maximum memory in Kb
		}
		
		public int cpuNumber() throws Throwable
		{
			return mp.nrVirtCpu(); //Virtual CPS's number
		}
		
		
		public long diskCapacity()
		{
			return md.capacity();	
		}
		
		public long diskAllocation() throws LibvirtException
		{
			return md.allocation();
		}
		
		public double percentualOfUsage() throws Throwable
		{
			return mp.usage();  //CPU's usage in %
		}
		
		public StringBuilder getInfo() throws LibvirtException  {
			
			StringBuilder info = new StringBuilder();		
			
			info.append("Domain\r\n");			
			
			//		usedMem = domJobInfo.getMemTotal();
			info.append("getAutostart: " + this.vm.getAutostart() + "\r\n");
			info.append("getID: " + this.vm.getID() + "\r\n");
			info.append("getMaxMemory: " + this.vm.getMaxMemory() + "\r\n");
			info.append("getMaxVcpus: " + this.vm.getMaxVcpus() + "\r\n");
			info.append("getName: " + this.vm.getName() + "\r\n");
			info.append("getOSType: " + this.vm.getOSType() + "\r\n");
			info.append("getUUIDString: " + this.vm.getUUIDString() + "\r\n");
			info.append("\r\n");
			
			info.append("DomainInfo\r\n");
			info.append("\r\n");		
			//this.domInfo.state.VIR_DOMAIN_BLOCKED
			//this.domInfo.state.VIR_DOMAIN_CRASHED
			//this.domInfo.state.VIR_DOMAIN_NOSTATE
			//this.domInfo.state.VIR_DOMAIN_PAUSED
			//this.domInfo.state.VIR_DOMAIN_RUNNING
			//this.domInfo.state.VIR_DOMAIN_SHUTDOWN
			//this.domInfo.state.VIR_DOMAIN_SHUTOFF
			
			info.append("state: " + this.domInfo.state.toString() + "\r\n") ;	
			info.append("cpuTime: " + this.domInfo.cpuTime + "\r\n");
			info.append("maxMem: " + this.domInfo.maxMem + "\r\n");
			info.append("memory: " + this.domInfo.memory + "\r\n");
			info.append("nrVirtCpu: " + this.domInfo.nrVirtCpu + "\r\n");
			info.append("\r\n");
			
			info.append("Memory Stats\r\n");
			info.append("");
			for (int i = 0; i < this.ms.length; i++) {
			
				info.append("MemoryStats["+i+"]:\r\n");
				info.append("getTag: " + this.ms[i].getTag() + "\r\n");
				info.append("getValue: " + this.ms[i].getValue() + "\r\n");
				info.append("\r\n");			
			}
					
			info.append("DomJobInfo\r\n");
			info.append("\r\n");
			info.append("getMemTotal: " + domJobInfo.getMemTotal() + "\r\n");
			info.append("getMemRemaining: " + domJobInfo.getMemRemaining() + "\r\n");
			info.append("getMemProcessed: " + domJobInfo.getMemProcessed() + "\r\n");
			info.append("\r\n");
			info.append("getDataTotal: " + domJobInfo.getDataTotal() + "\r\n");
			info.append("getDataRemaining: " + domJobInfo.getDataRemaining() + "\r\n");
			info.append("getDataProcessed: " + domJobInfo.getDataProcessed() + "\r\n");
			info.append("\r\n");
			info.append("getFileTotal: " + domJobInfo.getFileTotal() + "\r\n");
			info.append("getFileRemaining: " + domJobInfo.getFileRemaining() + "\r\n");
			info.append("getFileRemaining: " + domJobInfo.getFileRemaining() + "\r\n");
			info.append("\r\n");
			info.append("getTimeElapsed: " + domJobInfo.getTimeElapsed() + "\r\n");
			info.append("getTimeRemaining: " + domJobInfo.getTimeRemaining() + "\r\n");			
				
			return info;
		}
}
