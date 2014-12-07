/*------------------------------------------------------------------------------
 **     Ident: Innovation en Inspiration > Google Android 
 **    Author: rene
 ** Copyright: (c) Jan 22, 2009 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 *
 *   This file is part of OpenGPSTracker.
 *
 *   OpenGPSTracker is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   OpenGPSTracker is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with OpenGPSTracker.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package ar.com.preventa.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The GPStracking provider stores all static information about GPStracking.
 * 
 * @version $Id: GPStracking.java 459 2010-03-19 14:11:12Z rcgroot $
 * @author rene (c) Jan 22, 2009, Sogeti B.V.
 */
public final class PreventaDB
{
   /** The authority of this provider */
   public static final String AUTHORITY = "preventa";
   /** The content:// style URL for this provider */
   public static final Uri CONTENT_URI = Uri.parse( "content://" + PreventaDB.AUTHORITY );
   /** The name of the database file */
   public static final String DATABASE_NAME = "Preventa.db";
   /** The version of the database schema */
   public static final int DATABASE_VERSION = 1;

   
   public static final class ClientesTable extends ClientesColumns implements android.provider.BaseColumns
   {
      /** The MIME type of a CONTENT_URI subdirectory of a single track. */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.ar.com.preventa.android.cliente";
      /** The MIME type of CONTENT_URI providing a directory of tracks. */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ar.com.preventa.android.cliente";
      /** The content:// style URL for this provider */
      public static final Uri CONTENT_URI = Uri.parse( "content://" + PreventaDB.AUTHORITY + "/" + ClientesTable.TABLE );
      
      public static final String DEFAULT_SORT_ORDER = ClientesTable.RAZON_SOCIAL;
      
      /** The name of this table */
      public static final String TABLE = "Clientes";
      static final String CREATE_STATEMENT = 
         "CREATE TABLE " + ClientesTable.TABLE + "(" + " " + ClientesTable._ID            + " " + ClientesTable._ID_TYPE + 
                                            	 "," + " " + ClientesTable.SUCURSAL            + " " + ClientesTable.SUCURSAL_TYPE + 
                                            	 "," + " " + ClientesTable.CODIGO              + " " + ClientesTable.CODIGO_TYPE +
                                            	 "," + " " + ClientesTable.COMERCIO            + " " + ClientesTable.COMERCIO_TYPE +
                                            	 "," + " " + ClientesTable.RAZON_SOCIAL        + " " + ClientesTable.RAZON_SOCIAL_TYPE +
                                            	 "," + " " + ClientesTable.DOMICILIO           + " " + ClientesTable.DOMICILIO_TYPE +
                                            	 "," + " " + ClientesTable.TELEFONO            + " " + ClientesTable.TELEFONO_TYPE +
                                            	 "," + " " + ClientesTable.TIPO_CONTRIBUYENTE  + " " + ClientesTable.TIPO_CONTRIBUYENTE_TYPE +
                                            	 "," + " " + ClientesTable.CUIT                + " " + ClientesTable.CUIT_TYPE +
                                            	 "," + " " + ClientesTable.CONDICION_VENTA     + " " + ClientesTable.CONDICION_VENTA_TYPE +
                                            	 "," + " " + ClientesTable.SALDO               + " " + ClientesTable.SALDO_TYPE +
                                            	 "," + " " + ClientesTable.LIMITE_CREDITO      + " " + ClientesTable.LIMITE_CREDITO_TYPE +
                                            	 "," + " " + ClientesTable.VENDEDOR            + " " + ClientesTable.VENDEDOR_TYPE +
                                            	 "," + " " + ClientesTable.DGR                 + " " + ClientesTable.DGR_TYPE +
                                            	 "," + " " + ClientesTable.RUBRO               + " " + ClientesTable.RUBRO_TYPE +
                                            	 "," + " " + ClientesTable.PROVINCIA           + " " + ClientesTable.PROVINCIA_TYPE +
                                            	 "," + " " + ClientesTable.LOCALIDAD           + " " + ClientesTable.LOCALIDAD_TYPE +
                                            	 "," + " " + ClientesTable.REPARTIDOR          + " " + ClientesTable.REPARTIDOR_TYPE +
                                            	 "," + " " + ClientesTable.LISTAS_PRECIO       + " " + ClientesTable.LISTAS_PRECIO_TYPE + 
                                            	 ");";
	
   }
   
   
   public static final class SecuenciaTable extends SecuenciaColumns implements android.provider.BaseColumns
   {
      /** The MIME type of a CONTENT_URI subdirectory of a single track. */
      //public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.ar.com.preventa.android.cliente";
      /** The MIME type of CONTENT_URI providing a directory of tracks. */
      //public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ar.com.preventa.android.cliente";
      /** The content:// style URL for this provider */
      public static final Uri CONTENT_URI = Uri.parse( "content://" + PreventaDB.AUTHORITY + "/" + SecuenciaTable.TABLE );
      
      public static final String DEFAULT_SORT_ORDER = SecuenciaTable.ORDEN + " ASC";
      
      /** The name of this table */
      public static final String TABLE = "Secuencia";
      static final String CREATE_STATEMENT = 
         "CREATE TABLE " + SecuenciaTable.TABLE + "(" + " " + SecuenciaTable._ID                  + " " + SecuenciaTable._ID_TYPE + 
                                            	  "," + " " + SecuenciaTable.VENDEDOR             + " " + SecuenciaTable.VENDEDOR_TYPE + 
                                            	  "," + " " + SecuenciaTable.SUCURSAL             + " " + SecuenciaTable.SUCURSAL_TYPE +
                                            	  "," + " " + SecuenciaTable.CLIENTE              + " " + SecuenciaTable.CLIENTE_TYPE +
                                            	  "," + " " + SecuenciaTable.COMERCIO             + " " + SecuenciaTable.COMERCIO_TYPE +
                                            	  "," + " " + SecuenciaTable.DIA                  + " " + SecuenciaTable.DIA_TYPE +
                                            	  "," + " " + SecuenciaTable.ORDEN                + " " + SecuenciaTable.ORDEN_TYPE +
                                            	  "," + " " + SecuenciaTable.PEDIDOS              + " " + SecuenciaTable.PEDIDOS_TYPE +
                                            	  "," + " " + SecuenciaTable.ID_CLIENTE           + " " + SecuenciaTable.ID_CLIENTE_TYPE + 
                                            	  ");";
	
   }
   
   /**
    * This table contains segments.
    * 
    * @author rene
    */
   public static final class ProductosTable extends ProductosColumns implements android.provider.BaseColumns
   {

      /** The MIME type of a CONTENT_URI subdirectory of a single segment. */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.ar.com.preventa.android.producto";
      /** The MIME type of CONTENT_URI providing a directory of segments. */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ar.com.preventa.android.producto";
      /** The content:// style URL for this provider */
      public static final Uri CONTENT_URI = Uri.parse( "content://" + PreventaDB.AUTHORITY + "/" + ProductosTable.TABLE );

      /** The name of this table */
      static final String TABLE = "Productos";
      static final String CREATE_STATMENT = 
         "CREATE TABLE " + ProductosTable.TABLE + "(" + " " + ProductosTable._ID     + " " + ProductosTable._ID_TYPE + 
                                             	  "," + " " + ProductosTable.CODIGO       + " " + ProductosTable.CODIGO_TYPE +
                                             	  "," + " " + ProductosTable.DESCRIPCION  + " " + ProductosTable.DESCRIPCION_TYPE + 
                                             	  "," + " " + ProductosTable.SEGMENTO     + " " + ProductosTable.SEGMENTO_TYPE +
                                             	  "," + " " + ProductosTable.SUBRUBRO     +  " "+ ProductosTable.SUBRUBRO_TYPE + 
                                             	  "," + " " + ProductosTable.LINEA        + " " + ProductosTable.LINEA_TYPE +
                                             	  "," + " " + ProductosTable.UXB          + " " + ProductosTable.UXB_TYPE + 
                                             	  "," + " " + ProductosTable.DGR          + " " + ProductosTable.DGR_TYPE +
                                             	  "," + " " + ProductosTable.STOCK        + " " + ProductosTable.STOCK_TYPE + 
                                             	  "," + " " + ProductosTable.IVA          + " " + ProductosTable.IVA_TYPE +
                                             	  "," + " " + ProductosTable.PESABLE      + " " + ProductosTable.PESABLE_TYPE + 
                                             	  "," + " " + ProductosTable.KILOS        + " " + ProductosTable.KILOS_TYPE +
                                             	  "," + " " + ProductosTable.VALOR_ENTERO + " " + ProductosTable.VALOR_ENTERO_TYPE +
                                             	  ");";
   }
   
   
   
   
   /**
    * Columns from the clientes table.
    * 
    * @author rene
    */
   public static class ClientesColumns
   {
      /** The end time */
      public static final String SUCURSAL           = "sucursal";
      static final String SUCURSAL_TYPE             = "INTEGER";
      public static final String CODIGO             = "cliente";
      static final String CODIGO_TYPE               = "INTEGER";
      public static final String COMERCIO           = "comercio";
      static final String COMERCIO_TYPE             = "INTEGER";
      public static final String RAZON_SOCIAL       = "razonsocial";
      static final String RAZON_SOCIAL_TYPE         = "TEXT";
      public static final String DOMICILIO          = "domicilio";
      static final String DOMICILIO_TYPE            = "TEXT";
      public static final String TELEFONO           = "telefono";
      static final String TELEFONO_TYPE             = "TEXT";
      public static final String TIPO_CONTRIBUYENTE = "tipocontribuyente";
      static final String TIPO_CONTRIBUYENTE_TYPE   = "CHAR(2)";
      public static final String CUIT               = "cuit";
      static final String CUIT_TYPE                 = "CHAR(11)";
      public static final String CONDICION_VENTA    = "condicionventa";
      static final String CONDICION_VENTA_TYPE      = "CHAR(2)";
      public static final String SALDO              = "saldo";
      static final String SALDO_TYPE                = "MONEY";
      public static final String LIMITE_CREDITO     = "limitecredito";
      static final String LIMITE_CREDITO_TYPE       = "MONEY";
      public static final String VENDEDOR           = "vendedor";
      static final String VENDEDOR_TYPE             = "INTEGER";
      public static final String DGR                = "dgr";
      static final String DGR_TYPE                  = "REAL";
      public static final String RUBRO              = "rubro";
      static final String RUBRO_TYPE                = "INTEGER";
      public static final String PROVINCIA          = "provincia";
      static final String PROVINCIA_TYPE            = "INTEGER";
      public static final String LOCALIDAD          = "localidad";
      static final String LOCALIDAD_TYPE            = "INTEGER";
      public static final String REPARTIDOR         = "repartidor";
      static final String REPARTIDOR_TYPE           = "INTEGER";
      public static final String LISTAS_PRECIO      = "listas";
      static final String LISTAS_PRECIO_TYPE        = "TEXT";
//      public static final String LUNES			    = "lunes";
//      static final String LUNES_TYPE                = "INTEGER";
//      public static final String MARTES      		= "martes";
//      static final String MARTES_TYPE        		= "INTEGER";
//      public static final String MIERCOLES     	    = "miercoles";
//      static final String MIERCOLES_TYPE        	= "INTEGER";
//      public static final String JUEVES      		= "jueves";
//      static final String JUEVES_TYPE        		= "INTEGER";
//      public static final String VIERNES      		= "viernes";
//      static final String VIERNES_TYPE       		= "INTEGER";
//      public static final String SABADO      		= "sabado";
//      static final String SABADO_TYPE        		= "INTEGER";
//      public static final String DOMINGO      		= "domingo";
//      static final String DOMINGO_TYPE        		= "INTEGER";
      static final String _ID_TYPE                  = "INTEGER PRIMARY KEY AUTOINCREMENT";    
   }
   
   public static class SecuenciaColumns
   {
      /** The end time */
      public static final String VENDEDOR     = "vendedor";
      static final String VENDEDOR_TYPE       = "INTEGER";
      public static final String SUCURSAL     = "sucursal";
      static final String SUCURSAL_TYPE       = "INTEGER";
      public static final String CLIENTE      = "cliente";
      static final String CLIENTE_TYPE        = "INTEGER";
      public static final String COMERCIO     = "comercio";
      static final String COMERCIO_TYPE       = "INTEGER";
      public static final String DIA          = "dia";
      static final String DIA_TYPE            = "INTEGER";
      public static final String ORDEN        = "orden";
      static final String ORDEN_TYPE          = "INTEGER";
      public static final String PEDIDOS      = "pedidos";
      static final String PEDIDOS_TYPE        = "CHAR(2)";
      public static final String ID_CLIENTE   = "id_Clientes";
      static final String ID_CLIENTE_TYPE     = "INTEGER";
      static final String _ID_TYPE            = "INTEGER PRIMARY KEY AUTOINCREMENT";    
   }
   
   /**
    * Columns from the segments table.
    * 
    * @author rene
    */

   public static class ProductosColumns
   {
      /** The track _id to which this segment belongs */
      public static final String CODIGO = "codigo";     
      static final String CODIGO_TYPE   = "INTEGER";
      public static final String DESCRIPCION = "descripcion";     
      static final String DESCRIPCION_TYPE   = "CHAR(40)";
      public static final String SEGMENTO = "segmento";     
      static final String SEGMENTO_TYPE   = "INTEGER";
      public static final String SUBRUBRO = "subrubro";     
      static final String SUBRUBRO_TYPE   = "INTEGER";
      public static final String LINEA = "linea";     
      static final String LINEA_TYPE   = "INTEGER";
      public static final String UXB = "unidadesxbulto";     
      static final String UXB_TYPE   = "INTEGER";
      public static final String DGR = "DGR";     
      static final String DGR_TYPE   = "REAL";
      public static final String STOCK = "stock";     
      static final String STOCK_TYPE   = "REAL";
      public static final String IVA = "iva";     
      static final String IVA_TYPE   = "REAL";
      public static final String PESABLE = "pesable";     
      static final String PESABLE_TYPE   = "CHAR(1)";
      public static final String KILOS = "kilos";     
      static final String KILOS_TYPE   = "REAL";
      public static final String VALOR_ENTERO = "valorentero";     
      static final String VALOR_ENTERO_TYPE   = "CHAR(1)";
      static final String _ID_TYPE     = "INTEGER PRIMARY KEY AUTOINCREMENT";
   }

   public static class AuxiliaryTableColumns
   {
      /** The track _id to which this segment belongs */
      public static final String CODIGO = "codigo";     
      static final String CODIGO_TYPE   = "INTEGER";
      public static final String DESCRIPCION = "descripcion";     
      static final String DESCRIPCION_TYPE   = "TEXT";
   }

   
   
}
