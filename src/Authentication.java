import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;


public class Authentication {
	static boolean isAuthentified()
	{
        String osName = System.getProperty("os.name").toLowerCase();
		boolean authenticationVerified = false;
   		if (osName.startsWith("windows"))
   		{
			int answer = JOptionPane.CANCEL_OPTION;
			do
			{
				for (File root : File.listRoots())
				{
			        String driveName = FileSystemView.getFileSystemView().getSystemDisplayName (root);
			        if (driveName.startsWith("CLASSIF_EXP"))
			        {
			        	authenticationVerified = true;
			        	File[] filesInRoot = root.listFiles();
		        		for(File file : filesInRoot)
		        		{
		        		    if(file.getName().equals("Authentication"))
		        		    	authenticationVerified = isAuthenticationFileValid(file);
		        		    break;
		    		    }
			        	break;
			        }
			    }
				if (!authenticationVerified)
				{
					answer = JOptionPane.showConfirmDialog(null, "<html>Veuillez ins&#233rer la cl&#233 USB pour d&#233marrer le programme.</html>", "Cle non detectee", JOptionPane.OK_CANCEL_OPTION);
				}
			}
			while (!authenticationVerified && answer == JOptionPane.OK_OPTION);
   		}
   		else
   		{ // autres OS que Windows
   			authenticationVerified = true;
   		}
		return authenticationVerified;
	}
	static private boolean isAuthenticationFileValid(File file)
	{
		boolean valid = true;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			valid = valid && reader.readLine().equals("a0ddbb907be0a8623a8ec30e6355aa5fac86c52b93ae1c2c0180fccba4df2d19122ae7ede10c99d092a69f37");
			valid = valid && reader.readLine().equals("49eddba321eafb9a71fa377e274a7bf8724986cd8a61cb72b3d071efbde1b9ccc9e9d7a56fe55e5bf8d0c781");
			valid = valid && reader.readLine().equals("9ef8ff9e6f69016c814f159d153fbab2c70d212dd489631e3240d18933caf134488bb3f725e440adb0935c59");
			valid = valid && reader.readLine().equals("194bd4c7109a498d2d268935fcc8909f9824e601ebde4d22cf4bb7e1dbe31c8d0b2f191d8f25ea444befe73e");
			valid = valid && reader.readLine().equals("98cb729f7bfdbe7f27fea6dcb29f0892fd4174f7684ff23a46093768d1457a73dcc81c42c492a77c899b1aa1");
			valid = valid && reader.readLine().equals("e006dbe34163de09d506e83b18e7b527336b1b64f2c74ae115d9dd28a5257fa69e4ec129f89f44544f4a6fcb");
			valid = valid && reader.readLine().equals("fc0578baf4eda9023917af76d9d19c16f0a209f4b3289d76e8f9f029c3c21439b629f14d93216d2a798ccd96");
			valid = valid && reader.readLine().equals("2f8c7a2320428f489201412cc7fef7cfc3efa44b74b74a2c9af07a265d3a669cfbf9d2b7a9ca4e7d3f93da42");
			valid = valid && reader.readLine().equals("fa55888506f512cf088bdfd183373a946f1114a83c48a16e43fa1ffb1dfe8f66ed04e3bba2fe79e9cb6bf6dd");
			valid = valid && reader.readLine().equals("30ff5777164ccb8b5dafc999e0536d5e7be7d7b4b6235da21cf125bcc17b38fb6b7aabff91ab4e221f5a7ff4");
			valid = valid && reader.readLine().equals("51be54f9f28460fa9b7666e45fb3bef69fe323253e843deb23a0a5d5c9f739bae8688a315b7851ce73aabbc4");
			valid = valid && reader.readLine().equals("900ef952cf32a5b5e3db7851e35c2f12fff2cdbaec207585a61dde2d9e3efa2ab383532f5133a4f2e0db1ffb");
			valid = valid && reader.readLine().equals("025c3b492683547af3762a6fdd17bf27aac77cce0f0438e024f473cae4fe43b8b315849aa67964e1f0038522");
			valid = valid && reader.readLine().equals("d639e24e138542b57f1f9a84fbb4774fff677562179ef13e0376cfdf1b973faffd58d12dbd152f4f50aed369");
			valid = valid && reader.readLine().equals("3c71a7f4787e9d0f9ad2ce8aff1dfd84f418af7ae29376fb03a8eaf3ca52db514b32e7aa1c2dfc8a8358a995");
			valid = valid && reader.readLine().equals("0c7f9a407a7a3f90c3601fe120e5d4a86d4dba975a95f6cdf8db74267a9062e837051fcbeb20f3f9ce2c95de");
			valid = valid && reader.readLine().equals("c3328a8681626f23ae9c5aeebc6b77013cc283ba86bf27d80f76fc2f05f45dfb70542df32c2f342c37d9bc81");
			valid = valid && reader.readLine().equals("2f58e1d99e29afab30ae30c8053e5d81b4e25cef49e5142b27df02c53037833ad2cf3cf3e79c0d43c1d24314");
			valid = valid && reader.readLine().equals("bc4bfc27d48a13c84e096f2ed23c6663a68f358ab85e9eac7b4278acfd49d6d92a21edf881d7c82516b171ff");
			valid = valid && reader.readLine().equals("b7367562bcdb4f29566fc7e12bf914b1f7f72236486f02cd0d3846493f736fb1b229ff7c9ff736d4ced91e4e");
			valid = valid && reader.readLine().equals("ffc2c5d704332537702b8f47974a08d129caa84ce19c99669d51ab9bdba4b9aa5373d1ae0ef77cd3acb062f4");
			valid = valid && reader.readLine().equals("9b96532611bc87e41e8e6a6e351d446bcdfd9334234b461a1cba60268a3c147eadaf2a433f63c89d1e237599");
			valid = valid && reader.readLine().equals("fe0afdbbaa5f4a9f9829");
		}
		catch (FileNotFoundException e)	{
			e.printStackTrace();
			valid = true;
		}
		catch (IOException e)	{
			e.printStackTrace();
			valid = true;
		}
		return valid;
	}
	private void generateKey()
	{
		for (int i = 0; i < 1957; ++i)
		{
			int a = (int) Math.round(16 * Math.random());
			if (a <= 9)
				System.out.print(a);
			else
			{
				char c = 'f';
				switch (a)
				{
				case 10:
				c = 'a';
				break;
				case 11:
				c = 'b';
				break;
				case 12:
				c = 'c';
				break;
				case 13:
				c = 'd';
				break;
				case 14:
				c = 'e';
				break;
				case 15:
				c = 'f';
				break;
				}
				System.out.print(c);
			}
			if (i % 88 == 87)
				System.out.print("\n");
		}
	}
}
