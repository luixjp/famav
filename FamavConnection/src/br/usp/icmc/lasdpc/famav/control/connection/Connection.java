
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

package br.usp.icmc.lasdpc.famav.control.connection;

import org.libvirt.*;

public class Connection {

	public Connect conn; 
	
	public Connect createConnection()
	{
		 try{
	         this.conn = new Connect ("qemu:///system", false);
	     } catch (LibvirtException e){
	         System.out.println("exception caught:"+e);
	         System.out.println(e.getError());
	     }
	    
		return this.conn;
		
	}
	/*Exemplo uriHost:
	 * xen://oirase/ -> Conecta ao hypervisor Xen rodando no host oirase
	 */
	//Creates a non local connection
	public Connect createConnection(String uriHost)
	{
		 try{
	         this.conn = new Connect (uriHost, false);
	     } catch (LibvirtException e){
	         System.out.println("exception caught:"+e);
	         System.out.println(e.getError());
	     }
	    
		return this.conn;
		
	}
		
	public void setConnection(Connect conn)
	{
		this.conn = conn;
	}
	
	public Connect getConnection()
	{
		return this.conn;
	}


}