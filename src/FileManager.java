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
		return getFileInput(ACT_VIEWS_FILE_NAME);
	}
	public static OutputStream getActViewsFileOutput() throws FileNotFoundException
	{
		return getFileOutput(ACT_VIEWS_FILE_NAME);
	}
	public static InputStream getAccountingFileInput() throws FileNotFoundException, IOException
	{
		return getFileInput(ACCOUNTING_FILE_NAME);
	}
	public static OutputStream getAccountingFileOutput() throws FileNotFoundException
	{
		return getFileOutput(ACCOUNTING_FILE_NAME);
	}
	
	private static InputStream getFileInput(String fileName) throws FileNotFoundException, IOException
	{
		File actViewsFile = new File(getResourceDirectoryName() + fileName);
		if (actViewsFile.exists())
		{
			return new FileInputStream(getResourceDirectoryName() + fileName);
		}
		else
		{
			byte emptyData[] = {};
			FileOutputStream out = new FileOutputStream(getResourceDirectoryName() + fileName);
			out.write(emptyData);
			out.close();
			return new FileInputStream(getResourceDirectoryName() + fileName);
		}
	}
	private static OutputStream getFileOutput(String fileName) throws FileNotFoundException
	{
		return new FileOutputStream(getResourceDirectoryName() + fileName);
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
	static String ACT_VIEWS_FILE_NAME = "actViews";
	static String ACCOUNTING_FILE_NAME = "accounting";
}
