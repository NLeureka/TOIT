package modulescript.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openbravo.modulescript.ModuleScript;

public class FileReadWritePermissionUpdate extends ModuleScript {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		String basePath=System.getProperty("user.dir")+"/../../" ;
		String modulePath=  basePath+ "modules/org.openbravo.localization.india.taxes/referencedata/standard/";
		String cmd = "chmod -R 777  "+modulePath ;
		Runtime run = Runtime.getRuntime() ;
		try {
			Process pr = run.exec(cmd) ;
			
				pr.waitFor() ;
			
			BufferedReader buf = new BufferedReader( new InputStreamReader( pr.getInputStream() ) ) ;
			 String line ; 	
			while((line = buf.readLine() ) != null ) 
			{
			System.out.println(line) ;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			//Runtime.getRuntime().exec("chmod 777 myfile");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
	}

}
