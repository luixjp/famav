
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

package br.usp.icmc.lasdpc.famav.control.status;

import java.util.ArrayList;

import br.usp.icmc.lasdpc.famav.control.connection.*;

import org.libvirt.*;

public class Restart {

	Connection connection;
	Connect conn;

	public Restart (Connection connection)
	{
		this.connection = connection;
	}
	
	public int restartVM(String name)
	{
		conn = connection.getConnection();
		
		try{
			Domain vm = conn.domainLookupByName(name);

			if(vm.isActive() != 1)
			{
				System.out.println("The requested virtual machine is not runing.");
				return -1;
			}
			
			vm.shutdown();
			
			while(vm.isActive() == 1){ //Verifies if the VM is still on
			}
			
			vm.create();

		}
	    catch(LibvirtException e){
            System.out.println("exception caught:"+e);
            System.out.println(e.getError());
	    }
		return 1;
	}
	
public int restartVMListByName (ArrayList<String> listVMS) {
		
		conn = connection.getConnection();
		
		int size = listVMS.size();
		
		for(int i = 0 ; i < size ; i++) 
		{
			try{
				Domain vm = conn.domainLookupByName(listVMS.get(i));
				vm.shutdown();	
				
				while(vm.isActive() == 1){ //Verifies if the VM is still on
				}
				
				vm.create();
				
			}catch(LibvirtException e){
	            System.out.println("exception caught:"+e);
	            System.out.println(e.getError());
	            return 0;
			}
	}
		return 1;
}
	
}