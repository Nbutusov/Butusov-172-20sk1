package edu.nmu.fit.ButusovNYProgect.controllers;

import edu.nmu.fit.ButusovNYProgect.ReadWriter;
import edu.nmu.fit.ButusovNYProgect.SeleniumManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;



@Controller
public class MainController {
    SeleniumManager seleniumManager = new SeleniumManager();
    @GetMapping({"/","/index"})
    public String mainPage(Model model){
        model.addAttribute("item", new Item());
        return "index";
    }
    @PostMapping("/index")
    public ResponseEntity<ByteArrayResource> getFans(@ModelAttribute Item item) throws IOException, InterruptedException {
        ReadWriter readWriter = new ReadWriter();
        XSSFWorkbook workbook = readWriter.startFile();
        Elements webElements = seleniumManager.getHairdryers(item.getItemName());
        for (int i = 0; i< webElements.toArray().length; i++){
            readWriter.pushDataToFile(seleniumManager.getAttributes(webElements.get(i)),workbook);
        }



        byte[] arrayOfBytes = readWriter.saveToFile(workbook, "1");

        ByteArrayResource byteArrayResource= new ByteArrayResource(arrayOfBytes);
        ResponseEntity responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+DIRECTORY+".xls")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(arrayOfBytes.length)
                .body(byteArrayResource);
        return responseEntity;
    }

    @GetMapping({"/about"})
    public String gotoAboutPage(Model model){
        return "about";
    }

    private static final String DIRECTORY = "src/main/java/resources/Result";
}
