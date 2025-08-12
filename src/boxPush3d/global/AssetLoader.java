package boxPush3d.global;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class AssetLoader {
	
	
	public static List<File> LoadAllFiles(String path)
	{
		List<File> files = new LinkedList<File>();
		
		files.addAll(Arrays.asList(new File(path).listFiles()));
		
		File mods = new File("mods");
		
		for (final File fileEntry : mods.listFiles()) 
		{
			File items = new File(fileEntry.getPath() + "/" + path);
			if(items.exists())
			{
				files.addAll(Arrays.asList(items.listFiles()));
			}
	    }
		return files;
	}
	
	/*public static Iterable<KeyValuePair> loadKeyValueFile(File file)
	{
		TreeMap<String, String> map = loadKeyValueMap(file);
		List<KeyValuePair> pairs = new LinkedList<KeyValuePair>();
		
		for(String key : map.keySet())
		{
			pairs.add(new KeyValuePair(key, map.get(key)));
		}
		
		return pairs;
	}*/
	
	public static TreeMap<String, String> loadKeyValueMap(File file)
	{
		TreeMap<String, String> pairs = new TreeMap<String, String>();
		
		if(!file.exists()) {return pairs;}
		
		try {
			BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(file.getPath()));
			Scanner sc = new Scanner(fileReader);
			
			while(sc.hasNextLine())
			{
				String[] kv = sc.nextLine().split("=");
				if(kv.length != 2) {continue;}
				pairs.put(kv[0], kv[1]);
			}
			
			sc.close();
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pairs;
	}

}
