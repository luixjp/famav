
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

import java.util.ArrayList;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

import br.usp.icmc.lasdpc.famav.control.connection.Connection;

public class VirtualMachine {
	
	Connect conn;
	
	public VirtualMachine(Connection cnn)
	{
		this.conn = cnn.getConnection();
	}

	public String[] listInactiveDomains() throws LibvirtException
	{
		String[] domains; //Inactives VM's names
		domains = conn.listDefinedDomains();
		return domains;
	}
	
	public ArrayList<String> listActiveDomains () throws LibvirtException
	{
		ArrayList<String> vms = new ArrayList<String>();
		int[] ids;
		ids = conn.listDomains(); //Actives VM's IDs
		
		int size = ids.length; //Size of the vector with the IDs
		
		for(int i = 0 ; i < size ; i++)
		{
			Domain vm = conn.domainLookupByID(ids[i]); //Auxiliar Domain to find out the VM's name by the ID
			vms.add(vm.getName()+"\t"+ids[i]); //Adds the VM's name and ID to the ArrayList
		}
		
		return vms;
		
	}
	
}
