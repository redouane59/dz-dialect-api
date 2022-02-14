package io.github.Redouane59.dz.function;

import static io.github.Redouane59.dz.function.helper.Config.APPLICATION_NAME;
import static io.github.Redouane59.dz.function.helper.Config.JSON_FACTORY;
import static io.github.Redouane59.dz.function.helper.Config.SPREADSHEET_ID;
import static io.github.Redouane59.dz.function.helper.GoogleSheetHelper.getCredentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VerbTest {

  NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  Sheets           service        = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
      .setApplicationName(APPLICATION_NAME)
      .build();

  public VerbTest() throws GeneralSecurityException, IOException {
  }

  @Test
  public void deserializeVerb() throws IOException {
    List<List<Object>> conjugatedVerbs = service.spreadsheets().values()
                                                .get(SPREADSHEET_ID, "verbs!A2:F")
                                                .execute().getValues();
    System.out.println(conjugatedVerbs);
  }

}
