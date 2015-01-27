
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

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

import br.usp.icmc.lasdpc.famav.control.connection.Connection;

public class Resume {
	
	Connection connection; 
	Connect conn;
	
	public Resume(Connection connection)
	{
		this.connection = connection;
	}
	
	public int resumeVMbyName (String name) {
		
		  conn = connection.getConnection();
		
		try{
          Domain vm = conn.domainLookupByName(name);
          vm.resume();
          return 1;
          
      } catch (LibvirtException e){
          System.out.println("exception caught:"+e);
          System.out.println(e.getError());
          return 0;
      }
		
	}
	
	
	
}
