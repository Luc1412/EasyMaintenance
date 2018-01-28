package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import noteblockapi.NBSDecoder;
import noteblockapi.RadioSongPlayer;
import noteblockapi.Song;
import noteblockapi.SongPlayer;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created by Luc1412 on 03.12.2017 at 15:27
 */
public class NoteblockSongPlayer {

	public NoteblockSongPlayer(Player p) {
		Song christmasSong = NBSDecoder.parse(new File(EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "Song.nbs"));
		if (christmasSong == null) System.out.println("null");
		SongPlayer sp = new RadioSongPlayer(christmasSong);
		sp.setAutoDestroy(true);
		sp.addPlayer(p);
		sp.setVolume((byte) 1000);
		sp.setPlaying(true);

	}

}
