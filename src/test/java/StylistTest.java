import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import org.sql2o.*;

public class StylistTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      con.createQuery(deleteStylistsQuery).executeUpdate();
    }
  }

  @Test
  public void Stylist_instantiatesCorrectly_true() {
    Stylist newStylist = new Stylist("Trixy", 15);
    assertEquals(true, newStylist instanceof Stylist);
  }

  @Test
  public void getName_returnsName_String() {
    Stylist newStylist = new Stylist("Trixy", 15);
    assertEquals("Trixy", newStylist.getName());
  }

  @Test
  public void getRate_returnsRate_int() {
    Stylist newStylist = new Stylist("Trixy", 15);
    assertEquals(15, newStylist.getRate());
  }

  @Test
  public void all_returnList_List() {
    Stylist newStylist = new Stylist("Trixy", 15);
    newStylist.save();
    assertTrue(Stylist.all() instanceof List);
  }

  @Test
  public void getId_assignsId_int() {
    Stylist newStylist = new Stylist("Trixy", 15);
    newStylist.save();
    assertEquals(Stylist.all().get(0).getId(), newStylist.getId());
  }

  @Test
  public void find_returnsStylistById_String() {
    Stylist newStylist = new Stylist("Trixy", 15);
    newStylist.save();
    assertEquals("Trixy", Stylist.find(newStylist.getId()).getName());
  }
}
