package pro.delfik.bedwars.preparation;

import pro.delfik.lmao.outward.gui.VotingGUI;
import pro.delfik.bedwars.game.Map;
import implario.util.Converter;

import java.util.List;

public class MapVoting {

	private final GameFormat format;
	private final VotingGUI gui;

	public MapVoting(GameFormat format) {
		this.format = format;
		List<Map> matching = Map.getMaps(format.getTeams());
		for (Map map : matching) System.out.println("Matching map: " + map);
		gui = new VotingGUI(Converter.transform(matching, map -> new VotingGUI.Entry(map.getMaterial(), map.getName(), map.getSchematic())));
	}

	public GameFormat getFormat() {return format;}
	public List<VotingGUI.Entry> result() {return gui.result();}

	public VotingGUI getGui() {
		return gui;
	}
}
