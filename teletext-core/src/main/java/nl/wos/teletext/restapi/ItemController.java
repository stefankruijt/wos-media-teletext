package nl.wos.teletext.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.wos.teletext.dao.ItemDao;
import nl.wos.teletext.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringWriter;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class ItemController {
    @Autowired private ItemDao itemDao;

    @RequestMapping("/items")
    @ResponseBody
    private String getAllItems() {
        final StringWriter sw = new StringWriter();
        final List<Item> berichten = itemDao.getAllItems();
        final ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(sw, berichten);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sw.toString();
    }
}
