package tw.edu.ncnu.lsa;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import jline.ConsoleReader;

public class ShellDJ {
	
	static HashMap <Integer, AudioNode> nodeList;
	static HashMap <Integer, AudioTrack> playingList;
	public static void main(String[] args) {
		
		prepare();
		
		try {
			ConsoleReader reader = new ConsoleReader();
			System.out.println("Ready!");
			while (true) {
	            int k = reader.readVirtualKey();
	            //System.out.println(k + "(" + Ascii.asciiToNumAndLetter(k) + ")");
	            //play track
	            AudioNode node = nodeList.get(k);
            	if (node!=null) {
            		if (node.isMusic()) {
            			AudioTrack track = playingList.get(k);
            			if (track == null) {
                			//log("N1");
            				track = new AudioTrack(node);
            				playingList.put(k, track);
            				track.start();
            				System.out.println(Ascii.asciiToNumAndLetter(k) + " -> " + node.path + " is playing!");
            			} else {
                			//log("N2");
            				track.stopMusic();
            				playingList.remove(k);
            				System.out.println(Ascii.asciiToNumAndLetter(k) + " -> " + node.path + " is stopped!");
            			}
            		} else {
            			//log("N3");
            			AudioTrack track = new AudioTrack(node);
            			track.start();
            			System.out.println(Ascii.asciiToNumAndLetter(k) + " -> " + node.path + " is played!");
            		}
            	}
//           	if (k==27) {
//            		Set<Integer> kset= playingList.keySet();
//            		for (int key : kset) {
//            			AudioTrack track = playingList.get(key);
//            			track.stopMusic();
//        				playingList.remove(k);
//            		}
//            	}
            	if (k==63) {
            		String msgM = "";
            		String msgS = "";
	        		Set<Integer> kset= nodeList.keySet();
	        		for (int key : kset) {
	        			if(key>=65 && key <=90) {
	        				msgM += (Ascii.asciiToNumAndLetter(key) + ",");
	        			} else if (key>=97 && key<= 122){
	        				msgS += (Ascii.asciiToNumAndLetter(key) + ",");
	        			}
	        		}
	        		System.out.println("You can press: [Music] " + msgM.substring(0, msgM.length()-1) + " or [Sound] " + msgS.substring(0, msgS.length()-1));
            	}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public static void prepare() {
		nodeList = new HashMap <Integer, AudioNode> ();
		playingList = new HashMap <Integer, AudioTrack> ();
		
		File folder = new File(".");
		File[] filelist = folder.listFiles();
		String readyList = "";
		for(File file : filelist) {
			String filename = file.getName();
			String subName = filename.substring(filename.length()-3).toLowerCase();
			if (subName.equals("mp3") || subName.equals("wav")) 
			{
				int keyCode = Integer.parseInt(filename.substring(5, filename.lastIndexOf('.')));
				String path = filename;
				readyList += (path + ", ");
				PlayMode mode = (filename.substring(0, 1).toLowerCase().equals("m")) ? PlayMode.Music : PlayMode.Sound;
				AudioNode node = new AudioNode(keyCode, path, mode);
				nodeList.put(keyCode, node);
				
			}
		}
		System.out.println(readyList);
	}
	
}

class Ascii {
	static String asciiToNumAndLetter(int ascii) {
		if (ascii>=48 && ascii<=122) {
			char ch = (char)ascii;
			if (Character.isDigit(ch) || Character.isLetter(ch)) {
				return "" + ch;
			}
		}
		return "";
	}
}

class AudioNode {
	int keyCode;
	String symbol;
	String path;
	PlayMode mode;
	
	public AudioNode(int keyCode, String path, PlayMode mode) {
		this.keyCode = keyCode;
		this.symbol = Ascii.asciiToNumAndLetter(keyCode);
		this.path = path;
		this.mode = mode;
	}
	
	public AudioNode(String keyCodeStr, String pathStr, String modeStr) {
		if (modeStr.equals("m")) {
			this.keyCode = Integer.parseInt(keyCodeStr);
			this.symbol = Ascii.asciiToNumAndLetter(this.keyCode);
			this.path = pathStr;
			this.mode = PlayMode.Music; 
		} else if (modeStr.equals("s")) {
			this.keyCode = Integer.parseInt(keyCodeStr);
			this.path = pathStr;
			this.mode = PlayMode.Sound; 
		}
	}
	
	public boolean isMusic() {
		return mode==PlayMode.Music;
	}
}

class AudioTrack extends Thread{
	
	String path;
	
	public AudioTrack (AudioNode node) {
		this.path = node.path;
	}

	public AudioTrack(String path) {
		this.path = path;
	}
	
	
	Process p;
	boolean playStatus = false;
	
	public void run() {
		try {
			this.playMusic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	
	
	public void playMusic() throws IOException {
		String cmd = "";
		if (path.substring(0, 1).equals("m")) {
			cmd = "sudo omxplayer -o local " + path;
		} else if (path.substring(0, 1).equals("s")) {
			cmd = "sudo aplay " + path;
		}
		//ShellDJ.log(cmd.substring(cmd.length()-11));
		p = Runtime.getRuntime().exec(cmd);
		playStatus = false;
		
	}
	
	public void stopMusic() {
		if (p!=null)
		{
			String pid;
			try {
				//Get PID
				StringBuffer output = new StringBuffer();
				String cmd = "ps -u root";
				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = "";
				while ((line = reader.readLine())!= null) {
					output.append(line + "\n");
				}
				pid = output.toString();
				//Kill PID
				int tempIndex = pid.indexOf("omxplayer.bin");
				String part = pid.substring(tempIndex-23, tempIndex-19);
				String killCmd = "sudo kill -9 " + part;
				//#System.out.println("Shell: " + killCmd);
				Runtime.getRuntime().exec(killCmd);
			} catch (IOException | InterruptedException e) {
				//e.printStackTrace();
			}
			//System.out.println("Stop");
		}
	}
	
	public boolean isPlaying(){
		return playStatus;
	}
}

enum PlayMode {
//	A -> B, A -> A
//	Music: fade out A & fade in B; fade out A
//	Sound: 
	Music, Sound
}