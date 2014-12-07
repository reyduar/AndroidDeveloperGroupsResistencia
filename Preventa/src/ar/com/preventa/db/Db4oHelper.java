package ar.com.preventa.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import ar.com.preventa.activities.ClientesDb4oActivity;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.drs.Replication;
import com.db4o.drs.ReplicationSession;
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import com.db4o.ta.TransparentActivationSupport;

public class Db4oHelper implements ServerInfo {

	private static ObjectContainer oc = null;
	private Context context;

	/**
	 * @param ctx
	 */

	public Db4oHelper(Context ctx) {
		context = ctx;
	}

	public void storeClientes() {
		Cliente cliente = new Cliente();
		cliente.setCodigo("1");
		cliente.setNombre("nico");
		cliente.setDomicilio("domicilio");
		oc.store(cliente);
		//Person pilot = new Person("Mauro", 32);
		//oc.store(pilot);
		oc.commit();
	}

	/**
	 * Create, open and close the database
	 */
	public ObjectContainer db() {

		try {
			if (oc == null || oc.ext().isClosed()) {
				oc = Db4oEmbedded.openFile(dbConfig(), db4oDBFullPath(context));
				// We first load the initial data from the database
				// ExercisesLoader.load(context, oc);
			}

			return oc;

		} catch (Exception ie) {
			Log.e(Db4oHelper.class.getName(), ie.toString());
			return null;
		}
	}

	/**
	 * Configure the behavior of the database
	 */

	private EmbeddedConfiguration dbConfig() throws IOException {
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();

		// prueba para replicacion
		//Db4o.configure().generateUUIDs(Integer.MAX_VALUE);
		//Db4o.configure().generateVersionNumbers(Integer.MAX_VALUE);
		
		configuration.common().add(new TransparentActivationSupport());
       
		configuration.common().objectClass(Cliente.class).objectField("nombre").indexed(true);
		configuration.common().objectClass(Ruta.class).objectField("dia").indexed(true);
		configuration.common().objectClass(Producto.class).objectField("codigo").indexed(true);
		// configuration.common().objectClass(Cliente.class).cascadeOnUpdate(true);
		// configuration.common().objectClass(Cliente.class).cascadeOnActivate(true);
		configuration.common().objectClass(Cliente.class).generateUUIDs(true);
		configuration.common().objectClass(Cliente.class).generateVersionNumbers(true);
		configuration.common().objectClass(Ruta.class).generateUUIDs(true);
		configuration.common().objectClass(Ruta.class).generateVersionNumbers(true);
		configuration.common().objectClass(Producto.class).generateUUIDs(true);
		configuration.common().objectClass(Producto.class).generateVersionNumbers(true);
		
		
		
		return configuration;
	}

	/**
	 * Returns the path for the database location
	 */

	private String db4oDBFullPath(Context ctx) {
		return ctx.getDir("data", 0) + "/" + "preventa.db4o";
	}

	/**
	 * Closes the database
	 */
	public void close() {
		if (oc != null)
			oc.close();
	}

//	public void replicate2() {
//		Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService",128000);
//		thr.start();
//	}
//
//	class ServiceWorker implements Runnable {
//		public void run() {
//			// do background processing here...
//
//			try {
//				ObjectContainer client = Db4o.openClient("192.168.0.4", 4488,
//						"db4o", "db4o");
//
//				// ObjectContainer desktopDatabase =
//				// openDatabase(DESKTOP_DATABASE_NAME);
//				// ObjectContainer mobileDatabase =
//				// openDatabase(MOBILE_DATABASE_NAME);
//				ReplicationSession replicationSession = Replication.begin(oc,
//						client);
//				// set the replication-direction from the desktop database to
//				// the mobile database.
//				replicationSession.setDirection(replicationSession.providerA(),
//						replicationSession.providerB());
//
//				ObjectSet changes = replicationSession.providerA()
//						.objectsChangedSinceLastReplication();
//				for (Object changedObject : changes) {
//					replicationSession.replicate(changedObject);
//				}
//				replicationSession.commit();
//				replicationSession.close();
//
//				// Do something with this client, or open more clients
//				client.close();
//			} finally {
//				// server.close();
//				// client.close();
//			}
//		}
//		// stop the service when done...
//		// BackgroundService.this.stopSelf();
//	}
   
//	public static void replicateBiDirectional(){
//		02    ObjectContainer desktop=Db4o.openFile(DTFILENAME);
//		03    ObjectContainer handheld=Db4o.openFile(HHFILENAME);
//		04    ReplicationSession replication = Replication.begin(handheld, desktop);
//		05    ObjectSet changed =  replication.providerA().objectsChangedSinceLastReplication();
//		06    while (changed.hasNext()) {
//		07          replication.replicate(changed.next());
//		08    }
//		09    // Add one more loop for bi-directional replication
//		10    changed = replication.providerB().objectsChangedSinceLastReplication();
//		11    while(changed.hasNext()) {    
//		12      replication.replicate(changed.next());
//		13    }
//		14    
//		15    replication.commit();
//		16  }
	@SuppressWarnings("unchecked")
    public ArrayList<Cliente> getClientesList(){
        ArrayList<Cliente> ret = new ArrayList<Cliente>();
        ObjectSet result = getClientes();
        while (result.hasNext())
                ret.add((Cliente)result.next());
        return ret;
	}
		
	public List<Cliente> findAllClientes() {
		return db().query(Cliente.class);
	}
    
    @SuppressWarnings("unchecked")
    public ObjectSet getClientes(){
    	Query query = db().query();
    	query.constrain(Cliente.class);
    	//query.descend("name").orderAscending();
    	return query.execute();
	}
    
    public List<Cliente> findClientes(String find, Integer dia, Integer visita){
    	Query diaquery = null;
    	Query visitaquery = null;
    	Query rutasquery = null;
    	
    	Query query = db().query();
    	
        query.constrain(Cliente.class);

        if (find != null && find.length() > 0)
        	query.descend("nombre").constrain(find).like();
        
        if (dia != ClientesDb4oActivity.DIA_TODOS_ID){
        	rutasquery = query.descend("rutas");
        	rutasquery.constrain(Ruta.class);
            rutasquery.descend("dia").constrain(dia);
//        	diaquery.constrain(dia);
        	
//        	query.descend("rutas").constrain(Ruta.class);
//        	query.descend("dia").constrain(dia);
//        	
        }
        
        if (visita != ClientesDb4oActivity.VISITAS_TODOS_ID){
        	if (rutasquery != null)
        		visitaquery = (Query) rutasquery.descend("pedidos").constrain(0).greater();
        	else {
        			visitaquery = query.descend("rutas");
        			visitaquery.constrain(Ruta.class);
        			visitaquery.descend("pedidos").constrain(0).greater();
        	}
        }

        
        
        ObjectSet result = query.execute();
        return result;
    	
//    	List <Cliente> pilots = db().query(new Predicate<Cliente>() {
//    	    public boolean match(Cliente pilot) {
//    	        return pilot.getNombre().startsWith("ALTA");
//    	    	//return true;
//    	    }
//    	});
//    	return pilots;
    	
//    	List <Pilot> result = db.query(new Predicate<Pilot>() {
//    	    public boolean match(Pilot pilot) {
//    	        return pilot.getPoints() > 99
//    	            && pilot.getPoints() < 199
//    	            || pilot.getName().equals("Rubens Barrichello");
//    	   }
//    	});
    }
    
    public Cliente findCliente(String codigo, String comercio){
    
    	Query query=db().query();
    	query.constrain(Cliente.class);
    	Constraint constr=query.descend("codigo").constrain(codigo);
    	query.descend("comercio").constrain(comercio).and(constr);
    	ObjectSet result=query.execute();
    	//listResult(result);
    	return (Cliente)result.get(0);
    }
    
    
    public List<Ruta> findClientes2(String find, Integer dia, Integer visita){
    	Query clientequery = null;
   	
    	Query query = db().query();
    	
        query.constrain(Ruta.class);
        
        if (dia != ClientesDb4oActivity.DIA_TODOS_ID)
        	query.descend("dia").constrain(dia);

//        if (find != null && find.length() > 0)
//        	query.descend("dia").constrain(dia);
//        	
//        	constrain(find).like();
//        
//        if (dia != ClientesDb4oActivity.DIA_TODOS_ID){
//        	rutasquery = query.descend("rutas");
//        	rutasquery.constrain(Ruta.class);
//            rutasquery.descend("dia").constrain(dia);
////        	diaquery.constrain(dia);
//        	
////        	query.descend("rutas").constrain(Ruta.class);
////        	query.descend("dia").constrain(dia);
////        	
//        }
//        
        if (visita == ClientesDb4oActivity.VISITAS_CON_VENTAS_ID)
        	query.descend("pedidos").constrain(0).greater();
        else if (visita == ClientesDb4oActivity.VISITAS_SIN_VENTAS_ID)
        	query.descend("pedidos").constrain(0).smaller();
        else if (visita == ClientesDb4oActivity.VISITAS_SIN_VISITAR_ID)
        	query.descend("pedidos").constrain(0).equal();
        	
        	
        if (find != null && find.length() > 0){
        	clientequery = query.descend("cliente");
        	///clientequery.constrain(Cliente.class);
        	clientequery.descend("nombre").constrain(find).like();
        }
//        	query.descend("dia").constrain(dia);		
//        	if (rutasquery != null)
//        		visitaquery = (Query) rutasquery.descend("pedidos").constrain(0).greater();
//        	else {
//        			visitaquery = query.descend("rutas");
//        			visitaquery.constrain(Ruta.class);
//        			visitaquery.descend("pedidos").constrain(0).greater();
//        	}
//        }

        
        
        ObjectSet result = query.execute();
        return result;
    	
//    	List <Cliente> pilots = db().query(new Predicate<Cliente>() {
//    	    public boolean match(Cliente pilot) {
//    	        return pilot.getNombre().startsWith("ALTA");
//    	    	//return true;
//    	    }
//    	});
//    	return pilots;
    	
//    	List <Pilot> result = db.query(new Predicate<Pilot>() {
//    	    public boolean match(Pilot pilot) {
//    	        return pilot.getPoints() > 99
//    	            && pilot.getPoints() < 199
//    	            || pilot.getName().equals("Rubens Barrichello");
//    	   }
//    	});
    }
    
    
	
	public void replicate() {
		// TODO Auto-generated method stub
		ObjectContainer server = null;
		try {
			//ObjectContainer client = Db4o.openClient("192.168.0.4", 4488,"db4o", "db4o");
            
			//storeClientes();
			
			server = Db4oClientServer.openClient(HOST,PORT, USER, PASS);
			
			// ObjectContainer desktopDatabase =
			// openDatabase(DESKTOP_DATABASE_NAME);
			// ObjectContainer mobileDatabase =
			// openDatabase(MOBILE_DATABASE_NAME);
			ReplicationSession replicationSession = Replication.begin(oc,server);
			
			
			// set the replication-direction from the desktop database to the
			// mobile database.
			//replicationSession.setDirection(replicationSession.providerB(),
			//		replicationSession.providerA());

			ObjectSet changed = replicationSession.providerB().objectsChangedSinceLastReplication();
			
			while (changed.hasNext())
            {
                Object p = changed.next();
                if (p instanceof Producto)
                	replicationSession.replicate(p);
                else if (p instanceof Ruta)
                {
                    if (((Ruta)p).getVendedor() == 30729)
                    {
                    	replicationSession.replicate(p);
                    }
                }
            }
			
			
//			for (Object changedObject : changed) {
//				replicationSession.replicate(changedObject);
//			}
			
//			while (changed.hasNext()) {
//			      if (changed instanceof Cliente) {
//			        if (((Cliente)changed).getVendedor() == 30729){
//			        	replicationSession.replicate(changed.next());
//			        }
//			      }
//			}
			
			
			replicationSession.commit();
			replicationSession.close();

			// Do something with this client, or open more clients
			//client.close();
			
		} catch(Exception ex){
			ex.printStackTrace();
		} finally {
			// server.close();
			 server.close();
			
			//MessageSender messageSender = oc.ext().configure().clientServer().getMessageSender();
			//messageSender.send(new StopServer());
			
		}
	}
}