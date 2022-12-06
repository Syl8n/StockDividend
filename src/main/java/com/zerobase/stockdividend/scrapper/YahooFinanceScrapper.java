package com.zerobase.stockdividend.scrapper;

import com.zerobase.stockdividend.model.CompanyDto;
import com.zerobase.stockdividend.model.DividendDto;
import com.zerobase.stockdividend.model.ScrappedResult;
import com.zerobase.stockdividend.model.constants.Month;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class YahooFinanceScrapper implements Scrapper{

    private static final String URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final int START_TIME = 86400;

    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";

    @Override
    public ScrappedResult scrap(CompanyDto company) {
        var scrapResult = new ScrappedResult();
        scrapResult.setCompany(company);
        try {
            long end = System.currentTimeMillis() / 1000;
            Connection connection = Jsoup.connect(
                String.format(URL, company.getTicker(), START_TIME, end));
            Document document = connection.get();

            Elements parsedDiv = document.getElementsByAttributeValue("data-test",
                "historical-prices");
            Element tableEle = parsedDiv.get(0); // table 전체
            Element tbody = tableEle.children().get(1);

            List<DividendDto> dividends = new ArrayList<>();
            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }
                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.parseInt(splits[1].replace(",", ""));
                int year = Integer.parseInt(splits[2]);
                String dividend = splits[3];

                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
                }

                dividends.add(DividendDto.builder()
                    .date(LocalDate.of(year, month, day))
                    .dividend(dividend)
                    .build());

            }
            scrapResult.setDividendList(dividends);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return scrapResult;
    }

    @Override
    public CompanyDto scrapCompanyByTicker(String ticker) {
        try {
            Document document = Jsoup.connect(
                String.format(SUMMARY_URL, ticker, ticker)).get();
            Elements tags = document.getElementsByTag("h1");

            if(tags.size() == 0){
                return null;
            }

            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split(" \\(")[0].trim();

            return CompanyDto.builder()
                .ticker(ticker)
                .name(title)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
