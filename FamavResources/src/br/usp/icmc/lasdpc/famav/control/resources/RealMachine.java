	
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

package br.usp.icmc.lasdpc.famav.control.resources;

import org.libvirt.*;

public class RealMachine {
	
	NodeInfo info;
	
	public RealMachine ()
	{
		this.info = new NodeInfo();
	}
	
	//Number of actives CPUs
	public int cpuNumber() 
	{
		return info.cpus;
	}
	
	//Expected frequency
	public int cpuFrequency()  
	{
		return info.mhz; 
	}
	
	//CPU's model
	public String cpuModel() 
	{
		return info.model;
	}
	
	//Memory's size in Kb
	public long memorySize() 
	{
		return info.memory;
	}
	
	public int coresPerSocket()
	{
		return info.cores;
	}
	
	public int sockets()
	{
		return info.sockets;
	}
	
}
