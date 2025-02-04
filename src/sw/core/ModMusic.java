package sw.core;

import arc.audio.*;
import arc.struct.*;

import static mindustry.Vars.*;

public class ModMusic {
	public static Music adventurers;

	public static void load() {
		adventurers = loadMusic(tree.loadMusic("adventurers"), control.sound.ambientMusic);
	}

	public static Music loadMusic(Music music, Seq<Music> to) {
		to.add(music);
		return music;
	}
}
