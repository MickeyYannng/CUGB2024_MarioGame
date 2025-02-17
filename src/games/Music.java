package games;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
 
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

 
public class Music {
	 public Music(int type) throws FileNotFoundException, InterruptedException, JavaLayerException {
	        switch (type) {
	            case 1:
	                playMusic("src/music/music.mp3");
	                break;
	            case 2:
	                playMusicInThread("src/music/MarioTopGoldCoins.mp3");
	                break;
	            case 3:
	                playMusicInThread("src/music/jump.mp3");
	                break;
	            case 4:
	                playMusicInThread("src/music/death.mp3");
	                break;
	            case 5:
	                playMusicInThread("src/music/AccelerateToTheCastle.mp3");
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported music type: " + type);
	        }
	 }
	    private void playMusic(String filePath) throws JavaLayerException, FileNotFoundException {
	        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
	            Player player = new Player(bis);
	            player.play();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void playMusicInThread(String filePath) {
	        new Thread(() -> {
	            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
	                try {
	                	 Player player = new Player(bis);
	                    player.play();
	                } catch (JavaLayerException e) {
	                    e.printStackTrace();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }).start();
	    }

}
 
 