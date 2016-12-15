package org.cognitus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitWorker {
	
	
	private String rabbitServer="";
	private String rabbitUser="guest";
	private String rabbitPassword="guest";
	private int port=8000;
	
	private String apiKey="";
	
	private long numberOfVideos =1;
	private String downloadFlag="no";
	private String license="any";
	
	private String qeueName = "crawlerQueue";
	
	private String searchQueue="";
    private int connectTry=0;
	
public RabbitWorker() {
	
};


public void startWorker() {
	
	
	try {
	
	
	ConnectionFactory factory = new ConnectionFactory();
    
	
	 factory.setHost(rabbitServer);
		// factory.setHost("hostRabbit");
	 factory.setUsername(rabbitUser);
	 factory.setPassword(rabbitPassword);
	 factory.setPort(port); 
	
		 
    
    Connection connection = factory.newConnection();
    final Channel channel = connection.createChannel();

 

    channel.queueDeclare(qeueName, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
    channel.basicQos(1);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received search Queue '" + message + "'");
        
        searchQueue=message;
           
        Map<String, Object> headers = new HashMap<String, Object>();
        headers= properties.getHeaders();
        
        for (Map.Entry<String, Object> e : headers.entrySet()) {
           // System.out.println(" [x] Header recieved = "+ e.getKey() + ": " + e.getValue());
            if (e.getKey().contains("number")) {
               	long number =0;
    			try {
    				number=new Long(e.getValue().toString()).longValue();	
    			} catch (NumberFormatException nfe) {
    				System.out.println("ERROR \""+e.getValue()+"\"  must be a number!!!!");
    	            }
            	
            	
            	numberOfVideos=number;
            }
            if (e.getKey().contains("download")) {
            	downloadFlag=e.getValue().toString();
            }
            if(e.getKey().contains("licence")) {
            	license= e.getValue().toString();
            } 
            
            
        }
        
        System.out.println(" [x] download= "+downloadFlag);
        System.out.println(" [x] number = "+numberOfVideos);
        System.out.println(" [x] licence = "+license);
                
        
        
        try {
        	launchCrawler();
        } finally {
        	System.out.println(" [x] Search and download Done ");
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
        	
        
        
        
        
        
      }
    };
    boolean autoAck = false;
    channel.basicConsume(qeueName, autoAck, consumer);

	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
		
		
		connectTry++; 
		System.out.println(" [x] Wait for the RabbitMQ service !!!! try = "+connectTry);
		 try {
			 if(connectTry <= 10 ) {
				Thread.sleep(4000);
				startWorker();
			
			 } else {
			 
				 System.out.println(" [x]Conection to RabbitMQ service failed ");
				 e.printStackTrace();
			 }
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}



private void launchCrawler () {

	//** Lets make the first search 
		YoutubeApiInterface myAPI = new YoutubeApiInterface(apiKey,numberOfVideos,downloadFlag,license);
		myAPI.startSearch(searchQueue);
		
}


private static void launchTest() {
	
	try {
        Thread.sleep(3000);
      } catch (InterruptedException _ignored) {
        Thread.currentThread().interrupt();
      }
}



public String getRabbitServer() {
	return rabbitServer;
}


public void setRabbitServer(String rabbitServer) {
	this.rabbitServer = rabbitServer;
}


public String getRabbitUser() {
	return rabbitUser;
}


public void setRabbitUser(String rabbitUser) {
	this.rabbitUser = rabbitUser;
}


public String getRabbitPassword() {
	return rabbitPassword;
}


public void setRabbitPassword(String rabbitPassword) {
	this.rabbitPassword = rabbitPassword;
}


public int getPort() {
	return port;
}


public void setPort(int port) {
	this.port = port;
}

public void setQueueName(String queueName) {
	this.qeueName=queueName;
}

public String getQueueName() {
	return qeueName;
}


public String getApiKey() {
	return apiKey;
}


public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
}



}
