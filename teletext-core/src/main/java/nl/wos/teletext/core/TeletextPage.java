package nl.wos.teletext.core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeletextPage {
    private static final Logger log = Logger.getLogger(TeletextPage.class.getName());

    private int pageNumber;
    private FastText fastText;
    private ArrayList<TeletextSubpage> teletextSubPages = new ArrayList<>();

    public ArrayList<TeletextSubpage> getTeletextSubPages() {
        return teletextSubPages;
    }

    /***
     * @param pageNumber
     * @return first subpage of teletext page
     */
    public TeletextPage (int pageNumber) {
        log.log(Level.INFO, "New page created: " + pageNumber);
        this.pageNumber = pageNumber;
        initializeFastText();
    }

    public TeletextSubpage addNewSubpage() {
        TeletextSubpage subpage = new TeletextSubpage();
        teletextSubPages.add(subpage);
        return subpage;
    }

    private void initializeFastText() {
        this.fastText = new FastText();
    }

    public String getConfigurationString() {
        String result = "[" + pageNumber + ".*]\n\n";

        for (TeletextSubpage page : teletextSubPages) {
            String textFileName = "text-" + pageNumber + "-" + teletextSubPages.indexOf(page) + ".txt";
            int subpageNr = (teletextSubPages.size() == 1) ? teletextSubPages.indexOf(page) : teletextSubPages.indexOf(page) + 1;

            result += "[" + pageNumber + ".000" + subpageNr + "]\n";
            result += "TemplateFilename=" + page.getLayoutTemplateFileName() + "\n";
            result += "TextFilename=" + textFileName + "\n";
            result += "Descr= Automatische pagina\n";
            result += "prompts=" + getFastTextTexts() + "\n";
            result += "links=" + getFastTextLinks() + "\n";
            result += "\n";
        }
        return result;
    }

    private String getFastTextTexts() {
        return String.format("%s %s %s %s", fastText.getRedButtonText(), fastText.getGreenButtonText(), fastText.getYellowButtonText(), fastText.getBlueButtonText());
    }

    private String getFastTextLinks() {
        return String.format("%d %d %d %d", fastText.getRedButtonLink(), fastText.getGreenButtonLink(), fastText.getYellowButtonLink(), fastText.getBlueButtonLink());
    }

    public int getTeletextPagenumber() {
        return this.pageNumber;
    }

    public int getSubpageNumber(TeletextSubpage s) {
        return teletextSubPages.indexOf(s);
    }
}