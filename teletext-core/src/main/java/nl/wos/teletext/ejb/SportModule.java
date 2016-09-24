package nl.wos.teletext.ejb;

import nl.wos.teletext.dao.SportPouleDao;
import nl.wos.teletext.entity.PropertyManager;
import nl.wos.teletext.entity.SportPoule;
import nl.wos.teletext.util.SportModuleDataParser;
import nl.wos.teletext.util.Web;
import org.apache.http.util.EntityUtils;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class SportModule extends TeletextModule {
    private static final Logger log = Logger.getLogger(SportModule.class.getName());

    @Inject private PropertyManager propertyManager;
    @Inject private PhecapConnector phecapConnector;
    @Inject private SportPouleDao sportPouleDao;

    private SportModuleDataParser parser = new SportModuleDataParser();

    @Schedule(minute="*",hour="*/100", persistent=false)
    public void doTeletextUpdate() {
        log.info("Sport module is going to update teletext.");

        List<SportPoule> poules = sportPouleDao.findAllOrderedByProperty("naam");
        try {
            String data = EntityUtils.toString(Web.doWebRequest("http://sportstanden.infothuis.nl/public/internet-rss.php"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if(data.isEmpty()) {
            log.severe("Gedownloade sportdata is null or leeg!, teletext update will be cancelled");
            return;
        }

        List<SportPouleRssItem> teletextData = parser.parsePoules(data, poules);
        log.info(teletextData.toString());

        TeletextUpdatePackage updatePackage = new TeletextUpdatePackage();
        updatePackage.addRemovePagesTask(611, 645);

        for(int i = 0; i < poules.size(); i++)
        {
            TeletextPage sportPage = new TeletextPage(i + 611);
            TeletextSubpage page1 = sportPage.addNewSubpage();
            page1.setLayoutTemplateFileName("template-sport1.tpg");

            TeletextSubpage page2 = sportPage.addNewSubpage();
            page2.setLayoutTemplateFileName("sportUitslagen2Template");


            // TODO Inhoud aan pagina's toevoegen

            //renderer.AddContentToTextPage1(sportPoules[i], ref subPage1);
            //renderer.AddContentToTextPage2(sportPoules[i], ref subPage2);

            //sportPage.AddNewTextPage(subPage1);
            //sportPage.AddNewTextPage(subPage2);
        }

        updatePackage.generateTextFiles();
        phecapConnector.uploadFilesToTeletextServer(updatePackage);*/
    }
}