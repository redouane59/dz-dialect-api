package io.github.Redouane59.dz.function.googlesheets;

import static io.github.Redouane59.dz.function.helper.Config.APPLICATION_NAME;
import static io.github.Redouane59.dz.function.helper.Config.JSON_FACTORY;
import static io.github.Redouane59.dz.function.helper.Config.SPREADSHEET_ID;
import static io.github.Redouane59.dz.function.helper.GoogleSheetHelper.getCredentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class GoogleSheetsTest {

  NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  Sheets           service        = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
      .setApplicationName(APPLICATION_NAME)
      .build();

  public GoogleSheetsTest() throws GeneralSecurityException, IOException {
  }

  @Test
  public void testGenerateVerbPlaceWIthSentences() throws IOException, GeneralSecurityException {
    // 1. get all the infinitive verbs which have place & choose a random one
    // @todo replace object by classes
    List<List<Object>> verbPlaces = service.spreadsheets().values()
                                           .get(SPREADSHEET_ID, "verb_complement!A2:A")
                                           .execute().getValues();
    List<List<Object>> conjugatedVerbs = service.spreadsheets().values()
                                                .get(SPREADSHEET_ID, "verbs!A2:F")
                                                .execute().getValues();
    List<List<Object>> places = service.spreadsheets().values()
                                       .get(SPREADSHEET_ID, "places!A2:F")
                                       .execute().getValues();
    List<List<Object>> withs = service.spreadsheets().values()
                                      .get(SPREADSHEET_ID, "withs!A2:B")
                                      .execute().getValues();

    int nbSentences = 50;
    for (int i = 0; i < nbSentences; i++) {

      // choosing infinitive verb
      int                randomIndex    = new Random().nextInt(verbPlaces.size());
      String             infinitiveVerb = verbPlaces.get(randomIndex).get(0).toString();
      List<List<Object>> matchingVerbs  = new ArrayList<>();
      for (List<Object> row : conjugatedVerbs) {
        if (row.get(0).equals(infinitiveVerb)) {
          matchingVerbs.add(row);
        }
      }
      // choosing conjugated verb
      randomIndex = new Random().nextInt(matchingVerbs.size());
      String conjugatedVerbFr = matchingVerbs.get(randomIndex).get(4).toString();
      String conjugatedVerbDz = matchingVerbs.get(randomIndex).get(5).toString();
      // choosing place
      randomIndex = new Random().nextInt(places.size());
      String placeDz = (places.get(randomIndex).get(3).toString() + " " + places.get(randomIndex).get(5).toString()).replace("  ", " ");
      String placeFr = (places.get(randomIndex).get(0).toString() + " " + places.get(randomIndex).get(2).toString()).replace("  ", " ");

      System.out.println(conjugatedVerbDz + " " + placeDz + " -> " + conjugatedVerbFr + " " + placeFr);
    }
  }

}
