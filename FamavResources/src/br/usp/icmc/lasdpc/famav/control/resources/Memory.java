
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

import br.usp.icmc.lasdpc.famav.control.connection.*;

import org.libvirt.*;

public class Memory {

	Connection connection; 
	Connect conn;
	long memory;

	public Memory(Connection connection, long memory)
	{
		this.connection = connection;
		this.memory = memory;
	}
	
	//Changes the VM's memory in Kb
	public int setMemory(int ID) 
	{
		conn = connection.getConnection();
		
		try{
		Domain vm = conn.domainLookupByID(ID);
		vm.setMemory(memory);
		return 1;
		}
		catch(LibvirtException e){
            System.out.println("exception caught:"+e);
            System.out.println(e.getError());
            return 0;
		}
	}
	
	//Changes the VM's memory in Kb
	public int setMemorybyName(String name) 
	{
		conn = connection.getConnection();
		
		try{
		Domain vm = conn.domainLookupByName(name);
		vm.setMemory(memory);
		return 1;
		}
		catch(LibvirtException e){
            System.out.println("exception caught:"+e);
            System.out.println(e.getError());
            return 0;
		}
	}
	
}