package model.actions.general;

import java.util.ArrayList;
import java.util.List;

public class ActionOption {
	
	private List<ActionOption> suboptions = new ArrayList<>();
	private String name;
	
	public ActionOption(String name2) {
		this.name = name2;
	}

	public void setName(String string) {
		this.name = string;
	}
	
	public void addOption(ActionOption opt) {
		suboptions.add(opt);
	}
	
	public void addOption(String str) {
		suboptions.add(new ActionOption(str));
	}
	
	public void addAll(List<ActionOption> optlist) {
		for (ActionOption o : optlist) {
			suboptions.add(o);
		}
	}

	public String makeBracketedString() {
		StringBuffer buf = new StringBuffer(name + "{");
		for (ActionOption opt : suboptions) {
			buf.append(opt.makeBracketedString());
		}
		buf.append("}");
		return buf.toString();
	}

	public int numberOfSuboptions() {
		return suboptions.size();
	}

	

	

}
