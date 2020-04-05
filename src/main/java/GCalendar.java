import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Set;

public class GCalendar {

    private static final String APPLICATION_NAME = "Sweet Cuppin Cake Bot Calendar";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String RAID_CALENDAR_ID = ConfigLoader.loadConfig("Google", "CALENDAR_ID");
    /**
     * Global instance of the scopes required
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final Set<String> SCOPES = gCalScope();
    private static final String CREDENTIALS_FILE_PATH = "Data/credentials.json";
    private Calendar service;

    public GCalendar() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();


    }

    //TODO: Return calendar specified
    public void getCalendar() throws IOException {

        // Retrieve the calendar
        com.google.api.services.calendar.model.Calendar calendar =
                service.calendars().get(RAID_CALENDAR_ID).execute();

        System.out.println(calendar.getSummary());

    }


    //TODO: Respond to an !add command to create a new [non]repeating event to Google GCalendar
    public void addEvent(){

    }

    //TODO: Respond to a !delete command to delete specific event from Google GCalendar
    public void deleteEvent(){

    }

    @Override
    //TODO: Print out calendar event
    public String toString() {
        return super.toString();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in  = new FileInputStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static java.util.Set<String> gCalScope() {
        java.util.Set<String> set = new java.util.HashSet<String>();
        set.add(CalendarScopes.CALENDAR);
        set.add(CalendarScopes.CALENDAR_EVENTS);
        return java.util.Collections.unmodifiableSet(set);
    }
}
