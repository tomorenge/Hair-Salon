import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import org.sql2o.*;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientsQuery = "DELETE FROM clients *;";
      con.createQuery(deleteClientsQuery).executeUpdate();
    }
  }

  @Test
  public void Client_instantiatesCorrectly() {
    Client newClient = new Client("Betty", "10:00am");
    assertEquals(true, newClient instanceof Client);
  }

  @Test
  public void saveClientToStylist_savesById_int() {
    Client newClient = new Client("Betty", "10:00am");
    newClient.saveClientToStylist(1);
    assertEquals(Client.all().get(0).getId(), newClient.getId());
  }

  @Test
  public void saveClientToStylist_savesMultiple() {
    Client newClient1 = new Client("Betty", "10:00am");
    newClient1.saveClientToStylist(1);
    Client newClient2 = new Client("Botty", "10:01am");
    newClient2.saveClientToStylist(1);
    assertEquals(2, Client.all().size());
  }
}
