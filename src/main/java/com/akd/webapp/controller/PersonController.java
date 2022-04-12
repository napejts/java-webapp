package com.akd.webapp.controller;

import com.akd.webapp.entity.Person;
import com.akd.webapp.repository.PersonRepository;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.common.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*@RestController*/
@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // Database URL: http://localhost:8080/h2-console/
    // Web app url: http://localhost:8080/
    //Navigate to doc.html when URL: http://localhost:8080/ is entered
    @GetMapping("/")
    public String get(Model model) {
        /*model.addAttribute("docs", docs);*/
        return "doc";
    }


    @PostMapping("/uploadFile")
    public String uploadData(@RequestParam("file")MultipartFile file) throws Exception{
        List<Person> personList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);

        System.out.println("Podatci koje ste upravo upisali u bazu:");

        parseAllRecords.forEach(record -> {
            Person person = new Person();
            person.setIme(record.getString("Ime"));
            person.setPrezime(record.getString("Prezime"));
            person.setDatumRodjenja(record.getString("DatumRodjenja"));
            /*System.out.println(person.toString());*/
            personList.add(person);
            /*System.out.println("    " + personList.toString());*/
        });
        //Add data to persistent H2 database
        personRepository.saveAll(personList);
        //Get all data from Person table
        System.out.println(personRepository.findAll());

    //Redirect to upload new file
    return "redirect:/";

    }
}
