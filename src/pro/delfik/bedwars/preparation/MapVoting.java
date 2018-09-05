package pro.delfik.bedwars.preparation;

import pro.delfik.lmao.outward.gui.VotingGUI;
import pro.delfik.bedwars.game.Map;
import implario.util.Converter;

import java.util.List;

public class MapVoting {

	private final int format;
	private final VotingGUI gui;

	public MapVoting(int format) {
		this.format = format;
		List<Map> matching = Map.getMaps(format);
		gui = new VotingGUI(Converter.transform(matching, map -> new VotingGUI.Entry(map.getMaterial(), map.getName(), map.getSchematic())));
	}

	public int getFormat() {return format;}
	public List<VotingGUI.Entry> result() {return gui.result();}
}
