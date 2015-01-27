
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

import org.libvirt.*;

public class MonitorDisk {

	DomainBlockInfo blockInfo;
	String devicePath; // = "/var/lib/libvirt/images/udontq-clone.img";
	
	public MonitorDisk(Domain vm, String param) throws LibvirtException 
	{
		//blockInfo = vm.blockInfo(sv.getPath());
		if(param != "") {
			this.devicePath = param;
			blockInfo = vm.blockInfo(this.devicePath);			
		}	
	}
	
	public long capacity()
	{
		long capacity = Long.MIN_VALUE;
		if(this.devicePath != "") {
			capacity = blockInfo.getCapacity();	
		}
		
		return capacity;
	}
	
	public long allocation() throws LibvirtException
	{
		long allocation = Long.MIN_VALUE;
		if(this.devicePath != "") {
			allocation = blockInfo.getAllocation();
		}
		return allocation;
	}

}
