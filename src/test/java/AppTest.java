import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hair Salonz");
  }

  @Test
  public void addWordPage() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a stylist"));
    assertThat(pageSource()).contains("Add a stylist:");
  }

  @Test
  public void addStylist() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a stylist"));
    fill("#name").with("Charles");
    fill("#rate").with("10");
    submit(".btn");
    assertThat(pageSource()).contains("Charles");
  }

  @Test
  public void addClientToStylist() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a stylist"));
    fill("#name").with("Charles");
    fill("#rate").with("10");
    submit(".btn");
    click("a", withText("Charles"));
    click("a", withText("Add a client"));
    fill("#name").with("Riff Raff");
    fill("#appointment").with("10:00am");
    submit(".btn");
    click("a", withText("Charles"));
    assertThat(pageSource()).contains("Riff Raff");
  }

  @Test
  public void addAppointmentToStylist() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a stylist"));
    fill("#name").with("Charles");
    fill("#rate").with("10");
    submit(".btn");
    click("a", withText("Charles"));
    click("a", withText("Add a client"));
    fill("#name").with("Riff Raff");
    fill("#appointment").with("10:00am");
    submit(".btn");
    click("a", withText("Charles"));
    assertThat(pageSource()).contains("10:00am");
  }
}
