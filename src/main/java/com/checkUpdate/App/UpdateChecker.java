package com.checkUpdate.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UpdateChecker {
	
	public static boolean readJsonFile() throws FileNotFoundException, IOException, ParseException, XmlPullParserException {
		
		File file = getVersionFile();
		if(file.exists()) {
			System.out.println("File Found");
			
			try(FileReader reader = new FileReader(file)){
				JSONObject object = getLatestVersionData(reader);
				
				String version = (String) object.get("version");
				String path = (String) object.get("path");
				
				System.out.println(version+" : "+path);
				
				MavenXpp3Reader mavenReader = new MavenXpp3Reader();
				Model model = mavenReader.read(new FileReader("pom.xml"));
				
				String pomVersion = model.getVersion();
				
				if(!version.equals(pomVersion)) {
					return true;
				}
			}	
		}else {
			System.out.println("File Not Found");
		}
		
		return false;
	}

	public static JSONObject getLatestVersionData(FileReader reader)
			throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(reader);
		JSONArray versionList = (JSONArray) obj;
		JSONObject object = (JSONObject) versionList.get(versionList.size()-1);
		return object;
	}

	public static File getVersionFile() {
		return new File("C:\\Users\\grvsg\\eclipse-workspace\\App\\version.json");
	}
	
}
