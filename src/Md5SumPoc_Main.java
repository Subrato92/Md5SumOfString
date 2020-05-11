import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Md5SumPoc_Main {
	public static void main(String[] args) {

		String ckSum = "61409aa1fd47d4a5332de23cbf59a36f";

		String scriptContent = "echo -n $1 | md5sum | cut -d ' ' -f 1";
		String scriptName = "/home/john/Desktop/tempMd5script.sh";
		String permissionCommand = "chmod 555 " + scriptName;

		try {

			File file = new File(scriptName);			 
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(scriptContent);
			writer.flush();
			writer.close();		

			System.out.println("Changing Permission of Script");
			Process processPermission = Runtime.getRuntime().exec(permissionCommand);
			
			String[] arr = new String[2];
			arr[0] = scriptName;			
			arr[1] = "John"; 

			ProcessBuilder pbuilder = new ProcessBuilder(arr);
			Process process = pbuilder.start();
			
			InputStream is = process.getInputStream();
			InputStreamReader isR = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isR);

			StringBuffer sb = new StringBuffer();
			String s = reader.readLine();
			while (s != null) {
				sb.append(s);
				s = reader.readLine();
				if(s!=null) {
					sb.append(System.lineSeparator());
				}
			}

			System.out.println("Req:" + ckSum);
			System.out.println("Computed:" + sb.toString());
			if (ckSum.equals(sb.toString())) {
				System.out.println("Matched!!");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
