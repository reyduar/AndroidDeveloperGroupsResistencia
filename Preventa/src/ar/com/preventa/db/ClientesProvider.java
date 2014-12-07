package ar.com.preventa.db;

import java.util.List;

import android.content.Context;
import ar.com.preventa.data.entity.Cliente;


public class ClientesProvider extends Db4oHelper {

      public final static String TAG = "ClientesProvider";
      private static ClientesProvider provider = null;

      public ClientesProvider(Context ctx) {
            super(ctx);
      }

      public static ClientesProvider getInstance(Context ctx) {
            if (provider == null)
                          provider = new ClientesProvider(ctx);
            return provider;
      }

      public void store(Cliente cliente) {
            db().store(cliente);
      }

      public void delete(Cliente cliente) {
            db().delete(cliente);
      }

      public List findAll() {
            return db().query(Cliente.class);
      }              
}
