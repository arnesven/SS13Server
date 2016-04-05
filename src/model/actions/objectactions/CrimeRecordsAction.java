package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.objects.consoles.CrimeRecordsConsole;
import model.actions.SensoryLevel;

public class CrimeRecordsAction extends Action {

	
	private CrimeRecordsConsole console;
	private ReportCrimeAction reportCrimeAction;
	private PardonAction pardonAction;
	private boolean report;

	public CrimeRecordsAction(CrimeRecordsConsole crimeRecordsConsole) {
		super("Crime Records", SensoryLevel.OPERATE_DEVICE);
		this.console = crimeRecordsConsole;
		reportCrimeAction = new ReportCrimeAction(console);
		pardonAction = new PardonAction(console);
//		commuteSentenceAction = new commuteSentenceAction(console);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with crime records console.";
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		opt.addOption(reportCrimeAction.getOptions(gameData, whosAsking));
		if (console.getReportedActors().size() > 0) {
			opt.addOption(pardonAction.getOptions(gameData, whosAsking));
		}
		return opt;
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (console.isInUse()) {
			performingClient.addTolastTurnInfo("You failed to activate the crime console.");
			return;
		}
		
		if (report) {
			reportCrimeAction.execute(gameData, performingClient);
		} else {
			pardonAction.execute(gameData, performingClient);
		}
	}
	
	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
		console.setInUse(false);
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		if (args.get(0).equals(reportCrimeAction.getName())) {
			this.report = true;
			reportCrimeAction.setArguments(args.subList(1, args.size()), performingClient);
		} else {
			this.report = false;
			pardonAction.setArguments(args.subList(1, args.size()), performingClient);
		}
	}

}
