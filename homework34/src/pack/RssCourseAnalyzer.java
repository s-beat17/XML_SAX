package pack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.TreeMap;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

public class RssCourseAnalyzer {
    final String baseAdr = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    public TreeMap<String, Double> courseOnDate(String date, Currency[] currency) {
        EnumSet<Currency> val = EnumSet.allOf(Currency.class);
        TreeMap<String, Double> resMap = new TreeMap<>();
        //
        String adr = baseAdr + date;
        XMLStreamReader reader = null;
        for (int i = 0; i < currency.length; i++) {
            try {
                reader = XMLInputFactory.newFactory().createXMLStreamReader(
                        URI.create(adr).toURL().openStream()
                );
                //parser
                int event = reader.getEventType();
                boolean rightCurrency = false;
                String s = null;
                while (true) {
                    switch (event) {
                        case START_ELEMENT:
                            if (reader.getName().getLocalPart().equals("CharCode")) {
                                event = reader.next();
                                if (reader.isWhiteSpace()) continue;
                                for (Currency currency1 : val) {
                                    if (currency1.name().equals(reader.getText())) {
                                        s = currency1.toString();
                                        rightCurrency = true;
                                    }
                                }
                            } else if (rightCurrency && reader.getName().getLocalPart().equals("Value")) {
                                event = reader.next();
                                if (reader.isWhiteSpace()) {
                                    rightCurrency = false;
                                    continue;
                                }
                                //RES
                                resMap.put(s, Double.parseDouble(reader.getText()
                                        .replace(',', '.')));
                                rightCurrency = false;
                            }
                            break;
                    }
                    if (!reader.hasNext())
                        break;
                    event = reader.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }
        return resMap;
    }
}
