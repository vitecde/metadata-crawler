package org.cognitus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; */


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class PostgressInterface {
	
	private String databaseServer="";
	private String databaseName="postgres";
	private String dataBaseUser="postgres";
	private String dataBasePassword="";
	private long port=5432;
	
	 private final static String QUEUE_NAME = "crawlerQueue";

	
	private String databaseUrl="";
	
public PostgressInterface(String server, String name, String user, String password, long ports) {

	databaseServer = server;
	databaseName = name;
	dataBaseUser = user;
	dataBasePassword = password;
	port = ports;
	
	
	databaseUrl="jdbc:postgresql://"+databaseServer+":"+ports+"/"+databaseName;
	
}

public void startTest() throws Exception {
	
	
	ConnectionFactory factory = new ConnectionFactory();
    
	
	 factory.setHost("192.168.99.100");
		// factory.setHost("hostRabbit");
		
		 factory.setUsername("guest");
		 factory.setPassword("guest");
		 factory.setPort(8000);
    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

 

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
        
        
           
        Map<String, Object> headers = new HashMap<String, Object>();
        headers= properties.getHeaders();
        
        for (Map.Entry<String, Object> e : headers.entrySet())
            System.out.println(" [x] "+ e.getKey() + ": " + e.getValue());
        
        
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);

	
	
	
	
	
	
	/*
	 * 
	 * 
	Connection con = null;
    Statement st = null;
    ResultSet rs = null;


    String url = "jdbc:postgresql://192.168.99.100:8000/postgres";
    String user = "postgres";
    String password = "toor1";

    try {
        con = DriverManager.getConnection(url, user, password);
        st = con.createStatement();
        rs = st.executeQuery("SELECT VERSION()");

        if (rs.next()) {
            System.out.println(rs.getString(1));
        }

    } catch (SQLException ex) {
        Logger lgr = Logger.getLogger(Version.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);

    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

	*/
	
	
	
	
}

/*
public void addAuthor() {
	
	 Connection con = null;
     PreparedStatement pst = null;

     String url = "jdbc:postgresql://192.168.99.100:8000/postgres";
     String user = "postgres";
     String password = "toor1";

     
     Statement stmt = null;
     
     try {

      
         con = DriverManager.getConnection(url, user, password);
         
         con.setAutoCommit(false);
         System.out.println("Opened database successfully");
  
         stmt = con.createStatement();
         
         String sql="COPY customer_reviews FROM '/home/example_M.csv' WITH CSV;";
         
        stmt.executeUpdate(sql);

         
        System.out.println("Database actualized with the file: /home/example_M.csv ");
         
         stmt.close();
         con.commit();
         con.close();
         

     } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
         lgr.log(Level.SEVERE, ex.getMessage(), ex);

     } finally {

         try {
             if (pst != null) {
                 pst.close();
             }
             if (con != null) {
                 con.close();
             }

         } catch (SQLException ex) {
             Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
             lgr.log(Level.SEVERE, ex.getMessage(), ex);
         }
	
}
}

public void getInfo() {
	
	 Connection con = null;
     PreparedStatement pst = null; 

     String url = "jdbc:postgresql://192.168.99.100:8000/postgres";
     String user = "postgres";
     String password = "toor1";

     
     Statement stmt = null;
     
     try {

         int i = 0;
        
         con = DriverManager.getConnection(url, user, password);
         
         con.setAutoCommit(false);
         System.out.println("Opened database successfully");
  
         stmt = con.createStatement();
         
         String sql="SELECT * FROM customer_reviews;";
         
        // stmt.executeUpdate(sql);
         
         ResultSet rs = stmt.executeQuery(sql);
         while ( rs.next() ) {
        	 System.out.println(" i = "+i+"  customer id = = "+rs.getString(1));
        	 
        	 System.out.println("    title = "+rs.getString(7));
        	 
        	 i++;
        	 
         }
         
         
         
         
         stmt.close();
         con.commit();
         con.close();
         

     } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
         lgr.log(Level.SEVERE, ex.getMessage(), ex);

     } finally {

         try {
             if (pst != null) {
                 pst.close();
             }
             if (con != null) {
                 con.close();
             }

         } catch (SQLException ex) {
             Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
             lgr.log(Level.SEVERE, ex.getMessage(), ex);
         }
	
}
	
	
}

*/
}
