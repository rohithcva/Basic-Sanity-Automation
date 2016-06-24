package BasicMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * The Info class has methods to get the basic details of the SUT
 * 
 *  @author gboyina
 *	@version 1.0
 *	@since
 */

public class Info {
		public static Socket socket;
		
		/*ModelName method gets the model name of the SUT through telnet*/
		
		public static  String ModelName(String s1) throws UnknownHostException, IOException, InterruptedException
		{
		/* inserting command */
			LogRegister.setLogger();
			LogRegister.setPropertyPaths();
			LogRegister.setLogFile();
			LogRegister.logger.info("Entering Info:ModelName()");
			try
			{
				socket = new Socket(s1, 24);
			}
			catch(IOException e) 
			{ 
				LogRegister.logger.error("In Info: ModelName() "+e.toString());
				e.printStackTrace();
			}
			final BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			final PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
			/* inserting command */
			String u;
			while ((u = r.readLine()) != null) 
			{
				if(u.contains("HDX"))
				{
					u=u.substring(u.lastIndexOf(':') + 1).trim();
					return u;
				}
			}
			r.close();
			w.close();
			socket.close();
			LogRegister.logger.info("Exiting Info :ModelName()");
			return u;
		}
}
