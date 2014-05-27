import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileManager {
	public static InputStream getActViewsFileInput() throws FileNotFoundException, IOException
	{
		File actViewsFile = new File(getResourceDirectoryName() + "actViews");
		if (actViewsFile.exists())
		{
			return new FileInputStream(getResourceDirectoryName() + "actViews");
		}
		else
		{
			byte emptyData[] = {};
			FileOutputStream out = new FileOutputStream(getResourceDirectoryName() + "actViews");
			out.write(emptyData);
			out.close();
			return new FileInputStream(getResourceDirectoryName() + "actViews");
		}
	}
	public static OutputStream getActViewsFileOutput() throws FileNotFoundException
	{
		return new FileOutputStream(getResourceDirectoryName() + "actViews");
	}
	static private String getResourceDirectoryName()
	{
		String resourceDirectoryName;
        String osName = System.getProperty("os.name").toLowerCase();
   		if (osName.startsWith("windows")){
        	resourceDirectoryName = "resources\\";
   		}
   		else if (osName.startsWith("linux")){
        	resourceDirectoryName = "resources/";
        }
   		else
   		{
        	resourceDirectoryName = "resources\\";
   		}
   		File resourceDirectory = new File(resourceDirectoryName);
   		resourceDirectory.mkdir();
   		return resourceDirectoryName;
	}
}
