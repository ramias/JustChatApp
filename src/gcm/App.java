package gcm;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Sending POST to GCM" );
        
        String apiKey = "AIzaSyCFXySenMZ_A_Dfn3scjO9X9-YXpqQgMMw";
        Content content = createContent();
        
        POST2GCM.post(apiKey, content);
    }
    
    public static Content createContent(){
		
		Content c = new Content();
		
		c.addRegId("APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93hMJQUYEdRLpBOpmAGkjckuoVFt6icRjgXKuOpqZEedWUvVsKOqRruXuAe3mDbkimcNAMpc7XMF8M");
		c.createData("Test Title", "Test Message");
		
		return c;
	}
}