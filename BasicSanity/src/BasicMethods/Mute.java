package BasicMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import CallRelatedTestCases.GlobalDataStore;
/**
 * 
 *  @author gboyina
 *	@version 1.0
 *	@since
 */
public class Mute 
{
	public static Socket socket;
	public void Mute(String s) throws UnknownHostException, IOException, InterruptedException
	{
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("Entering Mute: Mute()");
		socket = new Socket(s, 24);
	    socket.setKeepAlive(true);
	    final BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    final PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
	    /* inserting command */
	   w.append(GlobalDataStore.lit);
	   // w.append("\r");
	   	CharSequence mute_near_off="mute near off \r";
	   	CharSequence mute_near_on="mute near on \r";
	    w.append(mute_near_off);
	    w.flush();
	    Thread.sleep(10000);
	    w.append(GlobalDataStore.lit);
	    w.append(mute_near_on);
	    w.append(GlobalDataStore.lit);
	    w.append(mute_near_off);
	    w.flush();
	    w.append(GlobalDataStore.lit);
	    w.append(GlobalDataStore.Xit);
	    String userInput;
	    while ((userInput = r.readLine()) != null) 
	    {
	    	w.println(userInput);
	    }
	    r.close();
	    w.close();
	    LogRegister.logger.info("Exiting Mute: Mute()");
	}
}
