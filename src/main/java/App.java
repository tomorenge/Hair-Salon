import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;


public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //port manager//
    ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
     
      //  model.put("stylists", Stylist.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist-form", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new/stylist", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String rateString = request.queryParams("rate");
      int rate = Integer.parseInt(rateString);
      Stylist newStylist = new Stylist(name, rate);
      newStylist.save();
      model.put("stylist", newStylist);
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new/stylist-name", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int idGet = Integer.parseInt(request.queryParams("stylistid"));
      Stylist stylistUp = Stylist.find(idGet);
      String name = request.queryParams("name");
      stylistUp.update(name);
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int idGet = Integer.parseInt(request.params(":id"));
      // model.put("id", idGet);
      model.put("stylist", Stylist.find(idGet));
      model.put("clients", Client.getClients(idGet));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/form/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      int idGet = Integer.parseInt(request.params(":id"));
      model.put("id", idGet);
      model.put("stylist", stylist);
      model.put("template", "templates/form-client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new/client", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String appointment = request.queryParams("appointment");
      int idGet = Integer.parseInt(request.queryParams("stylistid"));
      Stylist stylist = Stylist.find(idGet);
      model.put("id", idGet);
      model.put("stylists", Stylist.all());
      Client newClient = new Client(name, appointment);
      newClient.saveClientToStylist(idGet);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylistDel = Stylist.find(Integer.parseInt(request.params(":id")));
      stylistDel.delete();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
