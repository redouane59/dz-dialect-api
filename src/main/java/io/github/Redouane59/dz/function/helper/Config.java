package io.github.Redouane59.dz.function.helper;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

public class Config {

  public static final String      APPLICATION_NAME      = "Google Sheets API Java Quickstart";
  public static final JsonFactory JSON_FACTORY          = GsonFactory.getDefaultInstance();
  public static final String      TOKENS_DIRECTORY_PATH = "tokens";
  public static       String      SPREADSHEET_ID        = "16q5EBZESgr-NdQRe6QgKyvg4Q3tMHt5_805mrpzBqg4";
}
