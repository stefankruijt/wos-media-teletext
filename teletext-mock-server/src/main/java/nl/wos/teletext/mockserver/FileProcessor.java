package nl.wos.teletext.mockserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FileProcessor {
    private static final Logger log = Logger.getLogger(FileProcessor.class.getName());

    private static FileProcessor fileProcessor = new FileProcessor( );
    private FileProcessor(){ }

    private Map<String, String> textFiles = new HashMap<>();
    private String controlDat = "";
    private List<TeletextPage> teletextPages = new ArrayList<>();

    private ConfigurationParser configurationParser = ConfigurationParser.getInstance();

    public static FileProcessor getInstance( ) {
        return fileProcessor;
    }

    public List<TeletextPage> getTeletextPagesToBroadcast() {
        return teletextPages;
    }

    protected void addFile(String fileName, String fileText) {
        List<TeletextPage> result = new ArrayList<>();

        if(fileName.startsWith("text")) {
            // Text file with contents of a page is received
            textFiles.put(fileName, fileText);
            log.info("The file: " + fileName + " is added to the teletext mock server.");
        }
        else if(fileName.equals("control.dat")) {
            controlDat = fileText;
            log.info("The file: " + fileName + " is received with instructions for the teletext server");
            configurationParser.parseConfiguration(fileText);
        }
        else if(fileName.equals("update.sem")) {
            teletextPages.clear();
            log.info("update.sem received. Execute and send instructions to teletext inserter and remove all received files.");

            configurationParser.parseConfiguration(controlDat).stream().filter(command -> command.getCommand().equals("addPage")).forEach(command -> {
                TeletextPage page = new TeletextPage();
                page.setPageNumber(command.getPageNumber());
                page.setSubPageNumber(command.getSubPageNumber());

                String[] fasttext = command.getLinks();
                String[] prompts = command.getPrompts();
                page.setFastText1(Integer.parseInt(fasttext[0]));
                page.setFastText2(Integer.parseInt(fasttext[1]));
                page.setFastText3(Integer.parseInt(fasttext[2]));
                page.setFastText4(Integer.parseInt(fasttext[3]));

                page.setFastTextLabel1(prompts[0]);
                page.setFastTextLabel2(prompts[1]);
                page.setFastTextLabel3(prompts[2]);
                page.setFastTextLabel4(prompts[3]);

                String content = textFiles.get(command.getTextFileName());
                page.setTextLines(content.split("\n"));

                teletextPages.add(page);
            });

            controlDat = "";
            textFiles.clear();
        }
    }
}