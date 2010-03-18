package mail;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 
 */

/**
 * @author mc
 *
 */
public class mail {
	
	private String Subject = null;
	private String Msg = null;
	
	
	
	/**
	 * @param subject
	 * @param msg
	 */
	public mail(String subject, String msg) {
		super();
		Subject = subject;
		Msg = msg;
	}



	public boolean sendmail()
	{
		if (Subject != null && Msg != null)
		{
			try {
			    // Construct data
			    String data = URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(Subject, "UTF-8");
			    data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(Msg, "UTF-8");
	
			    // Create a socket to the host
			    String hostname = "i1.cs.hku.hk";
			    int port = 80;
			    InetAddress addr = InetAddress.getByName(hostname);
			    Socket socket = new Socket(addr, port);
	
			    // Send header
			    String path = "/fyp/2009/fyp09008/public_html/mail.php";
			    BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			    wr.write("POST "+path+" HTTP/1.0\r\n");
			    wr.write("Content-Length: "+data.length()+"\r\n");
			    wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
			    wr.write("\r\n");
	
			    // Send data
			    wr.write(data);
			    wr.flush();
	
			    // Get response
			    BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    String line;
			    while ((line = rd.readLine()) != null) {
			        // Process line...
			    }
			    wr.close();
			    rd.close();
			    return true;
			} catch (Exception e) {
				return false;
			}
		}
		else return false;
	}
	
	public static void main(String[] args)
	{
		mail m = new mail("Sub", "Msg");
		m.sendmail();
	}

}
