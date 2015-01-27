
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

import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.libvirt.VcpuInfo;

public class MonitorProcessor {
	
		Domain vm;
		//DomainInfo domInfo;
		//VcpuInfo[] cpuInfo;
		
		public MonitorProcessor(Domain vm) throws LibvirtException {
			this.vm = vm;
			//cpuInfo = vm.getVcpusInfo();
		}
		
	
	public double usage() throws Throwable {
		long cpu_t_diff, cpu_t_break;
		int nCores;
		double percentual=0;
		double final_percentual=0;
				
		VcpuInfo[] cpuInfo = this.vm.getVcpusInfo();
		nCores = this.vm.getVcpusInfo().length;	
		
		for (int i = 0; i < nCores; i++) {
		
			cpu_t_break = cpuInfo[i].cpuTime;	// CPU's time at the moment
					
			Thread.sleep(1000); 				// Wait one second before catching the new time
			
			cpuInfo = this.vm.getVcpusInfo();
			cpu_t_diff = cpuInfo[i].cpuTime - cpu_t_break; // Calculates the diference between the times in 1 second
					
			percentual += (100 * cpu_t_diff)/1e9;
			
		}
		
		//Usage's percentual
		final_percentual = percentual/nCores; 
		
		if(final_percentual <= 100)
			return final_percentual;
		else
			return 100;
	}
	
	public int nrVirtCpu() throws Throwable {
		
		int nCores;
				
		nCores = this.vm.getVcpusInfo().length;				// Number os cores in the system 
		
		return nCores;
		
	}
	
	 
	
}
