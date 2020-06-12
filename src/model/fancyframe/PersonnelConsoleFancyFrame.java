package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.AcceptNewProfessionAction;
import model.actions.objectactions.ChangeJobAction;
import model.actions.objectactions.MarkForDemotionAction;
import model.actions.objectactions.SetWagesAction;
import model.characters.crew.CaptainCharacter;
import model.characters.general.GameCharacter;
import model.objects.consoles.Console;
import model.objects.consoles.PersonnelConsole;
import util.HTMLText;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonnelConsoleFancyFrame extends ConsoleFancyFrame {
    private final PersonnelConsole console;
    private final List<Actor> roster;
    private boolean applyClicked;
    private boolean showAdmin;
    private boolean showWageChange;
    private boolean showJobChange;
    private Actor wageChangePerson;
    private Actor jobChangePerson;
    private String selectedJobChange = "";

    public PersonnelConsoleFancyFrame(Player performingClient, Console console, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "blue", "white");
        this.console = (PersonnelConsole)console;
        this.applyClicked = false;
        this.showWageChange = false;
        this.roster = new ArrayList<>();
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
               roster.add(a);
            }
        }
        concreteRebuild(gameData, performingClient);

    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (showAdmin) {
            showAdminPage(gameData, player, content);
        } else {
            showAcceptJobChangePage(gameData, player, content);

        }
        setData(console.getPublicName(player), showWageChange, content.toString());
    }


    private void showAdminPage(GameData gameData, Player player, StringBuilder content) {
        content.append("_________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE BACK", "[back]"));
        if (!console.hasAdminPrivilege(player)) {
            content.append(HTMLText.makeCentered("<i>Permission Denied</i>"));
        } else {
            if (showWageChange) {
                showWageChangeView(gameData, player, content);
            } else if (showJobChange) {
                showJobChangeView(gameData, player, content);
            } else {
                showAdminTableView(gameData, player, content);
            }

        }
    }

    private void showWageChangeView(GameData gameData, Player player, StringBuilder content) {
        content.append("<br/><br/>Change Pay for: " + wageChangePerson.getBaseName() + "<br/>");
        content.append("Current pay is $$ " + console.getWageForActor(wageChangePerson) + "<br/>");
        content.append("Please enter new wage below. (Or \"cancel\" to go back without saving).");
    }


    private void showJobChangeView(GameData gameData, Player player, StringBuilder content) {
        content.append("<br/><br/>Change Job for: " + jobChangePerson.getBaseName() + "<br/>");
        content.append("Available jobs:<br/>");
        for (GameCharacter gc : console.getAvailableJobs(jobChangePerson.getCharacter(), console.getToBeDemoted().contains(jobChangePerson))) {
            content.append(HTMLText.makeFancyFrameLink("SETJOB_" + jobChangePerson.getBaseName() + "_" + gc.getBaseName(),
                    gc.getBaseName()));

            if (gc.getBaseName().equals(selectedJobChange)) {
                content.append(HTMLText.makeText("black", "white", "Times New Roman", 3," SELECTED"));
            }

            content.append("<br/>");
        }
    }

    private void showAdminTableView(GameData gameData, Player player, StringBuilder content) {
        content.append("<table bgcolor=\"#f2e8cd\" color=\"black\" border=\"1\"><th>Crew Roster</th><th>Pay</th><th>Jobchange/ Demote</th>");
        int i = 0;
        for (Actor a : roster) {
                String jobChange = "";
                if (player.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CaptainCharacter)) {
                    if (!console.getToBeDemoted().contains(a) && !console.getAcceptedActors().contains(a)) {
                        jobChange = HTMLText.makeFancyFrameLink("DEMOTE " + a.getBaseName(), "[DEMOTE]");
                    }
                }
                if (console.getAcceptedActors().contains(a) || console.getToBeDemoted().contains(a)) {
                    jobChange += HTMLText.makeFancyFrameLink("JOBCHANGE " + i, "[CHANGE JOB]");
                }
                content.append("<tr><td>" + a.getBaseName() + "</td><td align=\"center\">" +
                        HTMLText.makeFancyFrameLink("WAGECHANGE " + i, "" + console.getWageForActor(a)) +
                        "</td><td>" + jobChange + "</td></tr>");
                i++;

        }
        content.append("</table>");
    }


    private void showAcceptJobChangePage(GameData gameData, Player player, StringBuilder content) {
        content.append("___________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE ADMIN", "[admin]"));
        if (!console.getAcceptedActors().contains(player)) {
            content.append(HTMLText.makeCentered("<br/><br/><i>Sick of your job?<br/>Don't worry!" +
                    " There's always more work to be done on the station. Just click below to sign up for a job change.</i><br/><br/>"));
            String bgColor = "White";
            String fgColor = "Black";
            if (applyClicked) {
                bgColor = "Gray";
                fgColor = "White";
            }
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("APPLY",
                    HTMLText.makeText(fgColor, bgColor, "Courier", 4, "[APPLY HERE]"))));

        } else {
            content.append(HTMLText.makeCentered("<br/><br/><i>Thank you for your application!<br/><br/>It is pending review by a Command Officer. Please stay here until it can be processed."));
        }
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("CHANGEPAGE ADMIN")) {
            this.showAdmin = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("CHANGEPAGE BACK")) {
            if (showWageChange || showJobChange) {
                showWageChange = false;
                showJobChange = false;
            } else {
                this.showAdmin = false;
            }
            concreteRebuild(gameData, player);
        } else if (event.contains("WAGECHANGE")) {
            this.showWageChange = true;
            this.wageChangePerson = roster.get(new Scanner(event.replace("WAGECHANGE ", "")).nextInt());
            concreteRebuild(gameData, player);
        }  else if (event.contains("JOBCHANGE")) {
            String rest = event.replace("JOBCHANGE ", "");
            this.jobChangePerson = roster.get(new Scanner(rest).nextInt());
            this.showJobChange = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("APPLY")) {
            applyClicked = true;
            AcceptNewProfessionAction anpa = new AcceptNewProfessionAction(console);
            player.setNextAction(anpa);
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        } else if (event.contains("DEMOTE")) {
            String rest = event.replace("DEMOTE ", "");
            List<String> args = new ArrayList<>();
            args.add(rest);
            MarkForDemotionAction mfda = new MarkForDemotionAction(console, gameData);
            mfda.setActionTreeArguments(args, player);
            player.setNextAction(mfda);
            readyThePlayer(gameData, player);
        } else if (event.contains("SETJOB")) {
            String[] parts = event.split("_");
            ChangeJobAction cja = new ChangeJobAction(console, gameData);
            List<String> args = new ArrayList<>();
            args.add(parts[1]);
            args.add(parts[2]);
            cja.setActionTreeArguments(args, player);
            player.setNextAction(cja);
            showJobChange = false;
            this.selectedJobChange = parts[2];
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        }
        player.refreshClientData();
    }

    @Override
    protected void consoleHandleInput(GameData gameData, Player player, String data) {
        super.consoleHandleInput(gameData, player, data);
        Scanner scan = new Scanner(data);
        int newWage = -1;
        if (scan.hasNextInt()) {
            newWage = scan.nextInt();
        }

        if (newWage >= 0) {
            SetWagesAction swa = new SetWagesAction(console, gameData);
            List<String> args = new ArrayList<>();
            args.add(wageChangePerson.getBaseName());
            args.add(newWage + "");
            swa.setActionTreeArguments(args, player);
            player.setNextAction(swa);
            readyThePlayer(gameData, player);
        }

        this.showWageChange = false;
        concreteRebuild(gameData, player);
        player.refreshClientData();
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        applyClicked = false;
        selectedJobChange = "";
        this.showJobChange = false;

        //  concreteRebuild(gameData, actor);
    }
}
