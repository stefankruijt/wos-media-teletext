package nl.wos.teletext.restapi;

import nl.wos.teletext.components.NewsModule;
import nl.wos.teletext.components.PublicTransportModule;
import nl.wos.teletext.components.SportModule;
import nl.wos.teletext.components.WeatherModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@EnableAutoConfiguration
public class TeletextUpdateActionController {
    @Autowired private PublicTransportModule publicTransportModule;
    @Autowired private NewsModule newsModule;
    @Autowired private SportModule sportModule;
    @Autowired private WeatherModule weatherModule;

    private static final String SUCCESS_RESULT="<result>success</result>";
    private static final String FAILURE_RESULT="<result>failure, teletext modules allowed are: train_departures, news, sport or weather</result>";

    @RequestMapping(value = "/module/{moduleName}", method = GET)
    @ResponseBody
    public String doTeletextUpdate(@PathVariable("moduleName") String moduleName) {
        switch (moduleName) {
            case "train_departures":
                publicTransportModule.doTeletextUpdate();
                return SUCCESS_RESULT;
            case "news":
                newsModule.doTeletextUpdate();
                return SUCCESS_RESULT;
            case "sport":
                sportModule.doTeletextUpdate();
                return SUCCESS_RESULT;
            case "weather":
                weatherModule.doTeletextUpdate();
                return SUCCESS_RESULT;
            default:
                return FAILURE_RESULT;
        }
    }
}
