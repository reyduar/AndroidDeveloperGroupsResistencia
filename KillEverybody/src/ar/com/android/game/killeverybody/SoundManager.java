package ar.com.android.game.killeverybody;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.MediaPlayer;
import ar.com.android.game.interfaces.SoundManagerInterface;

public class SoundManager implements SoundManagerInterface{

	private Context context;
	Map<Integer, MediaPlayer> sounds = new HashMap();
	
	
	 public SoundManager(Context paramContext)
	  {
	    this.context = paramContext;
	    addSound(1, R.raw.boink1, 0.25F);
	    addSound(2, R.raw.man, 0.05F);
	    addSound(3, R.raw.woman, 0.05F);
	  }
	 	 
	 
	 private void addSound(int paramInt1, int paramInt2, float paramFloat)
	  {
	    MediaPlayer localMediaPlayer = MediaPlayer.create(this.context, paramInt2);
	    localMediaPlayer.setVolume(paramFloat, paramFloat);
	    this.sounds.put(Integer.valueOf(paramInt1), localMediaPlayer);
	  }
	
	
	@Override
	public void play(int paramInt)
	  {
	    ((MediaPlayer)this.sounds.get(Integer.valueOf(paramInt))).start();
	  }

}
