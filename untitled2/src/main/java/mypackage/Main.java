package mypackage;

import mypackage.model.Invoice;
import mypackage.model.Price;
import mypackage.model.PriceList;
import mypackage.model.User;
import mypackage.repository.InvoiceRepository;
import mypackage.repository.UserRepository;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Main {

    public static void main(final String[] args)
            throws IOException, ParseException, NoSuchFieldException, IllegalAccessException, java.text.ParseException {
        final String reportDate = "21-03";
        //        main sample1
        //        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        //        String outPath="C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
        //        main sample2
        //        final String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        final String outPath = "C:\\Users\\user\\IdeaProjects\\untitled2\\src\\test\\resources\\sample2\\output\\";

        final String currency = "EUR";
        final String key = "3b14c37cbcca1d0ff2fca003";
        final ApplicationContext run = SpringApplication.run(Main.class, args);
        final UserRepository userRepository = run.getBean(UserRepository.class);
        final List<User> userList = userRepository.findAll();
        System.out.println(userList);
        final Map<String, User> userMap = new LinkedHashMap<>();
        final int counter = userList.size();
        for (int i = 1; i <= counter; i++) {
            for (final User user : userList) {
                if (Integer.parseInt(user.getRef()) == i) {
                    final String ref = user.getRef();
                    userMap.put(ref, user);
                }
            }
        }
        final InvoiceRepository invoiceRepository = run.getBean(InvoiceRepository.class);

        for (final Map.Entry<String, User> userFromMap : userMap.entrySet()) {
            final User user = userFromMap.getValue();
            final PriceList priceList = user.getPriceList();
            final List<Price> price = priceList.getPrices();
            final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, user.getReadingList());
            final Collection<Measurement> measurements = measurementGenerator.generate();
            final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, reportDate,
                    currency, key);
            final Invoice invoice = invoiceGenerator.generate();
            final FolderGenerator folderGenerator = new FolderGenerator(user, outPath);
            final String folderPath = folderGenerator.folderGenerate();
            final JsonGenerator jsonGenerator = new JsonGenerator(invoice, folderPath, currency, user);
            final JSONObject json = jsonGenerator.generateJson();
            final JsonFileGenerator jsonFileGenerator = new JsonFileGenerator(json, folderPath);
            invoiceRepository.saveAndFlush(invoice);
            jsonFileGenerator.save();
        }

    }
}
