package gcm;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Sending POST to GCM" );
        
        String apiKey = "AIzaSyAEzmOSvi0qqincoUR7UwRz971qRamnWPQ";
        Content content = createContent();
        
        POST2GCM.post(apiKey, content);
    }
    
    public static Content createContent(){
		
		Content c = new Content();
		
		c.addRegId("APA91bEc-6aQGaFE8srXskm4W9acfVhHeSr5yIcEDSyw7JIQh9iz1jBpv-zGJWCh6Zx7W0cIlI92oM5XxFBtPbfzD-4Qk0kw3BC90N8nTC-5zFreZvuLFo3tuLF_upkXLwLxJbl_q1kP");
		c.createData("Rami's Phone", "Hello Rami");
		
		return c;
	}
}