package pro.delfik.bedwars.preparation;

import lib.gui.VotingGUI;
import pro.delfik.bedwars.game.Map;
import pro.delfik.util.Converter;

import java.util.List;

public class MapVoting {

	private final GameSize format;
	private final VotingGUI gui;

	public MapVoting(GameSize format) {
		this.format = format;
		List<Map> matching = Map.getMaps(format);
		gui = new VotingGUI(Converter.transform(matching, map -> new VotingGUI.Entry(map.getMaterial(), map.getName(), map.getSchematic())));
	}

	public GameSize getFormat() {
		return format;
	}

	public List<VotingGUI.Entry> result() {return gui.result();}
}
