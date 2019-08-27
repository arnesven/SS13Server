package model.actions.general;

import util.Logger;
import util.MyRandom;

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

    public List<ActionOption> getSuboptions() {
        return suboptions;
    }

    public String getName() {
        return name;
    }

    public ActionOption getRandomOption() {
        return MyRandom.sample(suboptions);
    }

    public void uniquefy() {
        // Logger.log("     Uniqifying action options... " + this.getName());
        for (ActionOption a : suboptions) {
            int dup = 2;
            for (ActionOption b : suboptions) {
                if (a != b && a.getName().equals(b.getName())) {
                    // Logger.log("Found same : " + a.getName() + " - fixing.");
                    b.setName(b.getName() + " [" + (dup++) + "]");
                }
            }
            if (dup != 2) {
                a.setName(a.getName() + " [1]");
            }
            a.uniquefy();
        }
    }

    public void clearAll() {
        suboptions.clear();
    }
}
