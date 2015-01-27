
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

import br.usp.icmc.lasdpc.famav.control.connection.*;

import org.libvirt.*;

public class Migrate {
	
	private static final long LIVE_MIGRATION = 1;	//Constant to the Live migratrion
	Connection connection; 
	Connection destConnection;
	Connect conn, destConn;
	String uriDest;
	
	public Migrate(Connection connection, String uriDest)
	{
		this.connection = connection;
		this.uriDest = uriDest;
		this.destConnection = new Connection();
		this.destConnection.createConnection(uriDest);	//Creates the connection with the destiny
		
	}
	
	
	public int MigrateDomain(String nameOrigin, String uri, long bandwidth)
	{
		 conn = connection.getConnection();
		 destConn = destConnection.getConnection();		//Connection with the destiny
		  
		try{
			Domain vm = conn.domainLookupByName(nameOrigin);
			
			 // dconn = Connection with the destiny host 
			 // flags = VIR_MIGRATE_LIVE - Try the live migration
			 // dname = (optional)New VM's name after the migration(In case of changing the name, if not it must to be NULL)
			 // uri = (optional)"way" of the origin to the destiny(Can be NULL and the libvirt tries to find the best) OR hostname OR IP from the destiny, must to be specify only if the migration be for a specific
			 // interface of the remot host. To Qemu/KVM must to be "tcp://hostname[:port]"
			 // bandwidth = (optional) Bandwidth of migration in Mps
			 // ATENTION - Take care with the possible limitation with the migration, in exemple: Can be not possible to migrate if the processors 
			 // been different even if the architecture be the same, or, between different hypervisors.
			 
			vm.migrate(destConn, LIVE_MIGRATION, null, null, bandwidth);

			return 1;
		
		}catch(LibvirtException e){
            System.out.println("exception caught:"+e);
            System.out.println(e.getError());
            return 0;
		}    
         
	}
	
	
}
