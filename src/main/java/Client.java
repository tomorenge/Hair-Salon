import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.util.Calendar;

public class Client {
  private int id;
  private String name;
  private String appointment;
  private int stylistId;

  public Client(String name, String appointment) {
    this.name = name;
    this.appointment = appointment;
  }

  public String getName() {
    return name;
  }

  public String getAppointment() {
    return appointment;
  }

  public int getId() {
    return id;
  }

  public static List<Client> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients";
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void saveClientToStylist(int inputId) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients(name, appointment, stylistId) VALUES (:name, :appointment, :stylistId)";
      this.stylistId = inputId;
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("appointment", this.appointment)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id=:id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
        return client;
    }
  }

  public static List<Client> getClients(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE stylistId=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Client.class);
    }
  }
}
