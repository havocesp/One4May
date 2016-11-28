import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScriptCreator {

	public static int FREEZETIME = 5000;
	public static int INTERVAL = 1000;
	
	public static final String DEFAULT_OUTPUT_FILE = "autoscript.sh";
	
	private String scriptText = "";
	
	private String outputFile = null;
	
	public ScriptCreator()
	{
		this(null);
	}
	
	public ScriptCreator(String outputFile)
	{
		this.outputFile = outputFile;
		scriptText = "#!/bin/bash\n\n";
		scriptText = "sleep " + (FREEZETIME/1000) + "\n\n";
	}
	
	public void addCommand(String cmd, int interval)
	{
		scriptText += cmd + "\n";
		scriptText += "sleep " + (INTERVAL/1000) + "\n\n";
	}
	
	public void addMouseClickAction(int x, int y)
	{
		addCommand("xdotool mousemove " + x + " " + y + " click 1", INTERVAL);
	}
	
	public void create()
	{
		if (outputFile == null)
			outputFile = DEFAULT_OUTPUT_FILE;
		
		try {
			
			File file = new File(outputFile);
			if ( ! file.exists()) file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(scriptText);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
