package workspaces;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.*;

public class VarnaBuslines
{
    private static final String DATABASE_TAG = "Database";
    private static final String WEB_BROWSER_TAG = "Web Browser";

    public Workspace getWorkspace()
    {
        Workspace workspace = new Workspace(
                "Varna bus lines",
                "This is a model of my software system.");

        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();
        Person user = model.addPerson(
                "User",
                "A user of my software system.");


        //// System context view (C1)
        SoftwareSystem busLineSystem = model.addSoftwareSystem(
                "Bus line overview system",
                "Allows users to view, search and filter information about the bus lines of the city of Varna, Bulgaria.");

        user.uses(busLineSystem, "Views, searches and/or filters information using");

        SystemContextView contextView = views.createSystemContextView(
                busLineSystem,
                "System Context",
                "");

        contextView.add(busLineSystem);
        contextView.add(user);

        contextView.enableAutomaticLayout();

        //// Container view (C2)
        Container singlePageApplication = busLineSystem.addContainer(
                "Single-Page application",
                "",
                "React");
        singlePageApplication.addTags(WEB_BROWSER_TAG);

        Container apiApplication = busLineSystem.addContainer(
                "API Application",
                "",
                "Java and Spring Boot");

        Container database = busLineSystem.addContainer(
                "Database",
                "",
                "MySQL and Hibernate");
        database.addTags(DATABASE_TAG);

        user.uses(singlePageApplication, "Views, searches and/or filters information using");
        singlePageApplication.uses(apiApplication, "Sends requests to");
        apiApplication.uses(database, "Reads from and writes to");

        ContainerView containerView = views.createContainerView(
                busLineSystem,
                "Containers",
                "");

        containerView.add(user);
        containerView.add(singlePageApplication);
        containerView.add(apiApplication);
        containerView.add(database);

        containerView.enableAutomaticLayout();

        // Component view (C3) - API application
        Component busController = apiApplication.addComponent(
                "Bus Controller",
                "Handles requests for bus information.",
                "Spring MVC Rest Controller");

        Component busComponent = apiApplication.addComponent(
                "Bus Component",
                "Provides CRUD for buses.",
                "Spring Bean");

        busController.uses(busComponent, "Uses");
        busComponent.uses(database, "Reads from and writes to");

        Component stationController = apiApplication.addComponent(
                "Station Controller",
                "Handles requests for station information.",
                "Spring MVC Rest Controller");

        Component stationComponent = apiApplication.addComponent(
                "Station Component",
                "Provides CRUD for stations.",
                "Spring Bean");


        stationController.uses(stationComponent, "Uses");
        stationComponent.uses(database, "Reads from and writes to");

        Component alertcontroller = apiApplication.addComponent(
                "Alert Controller",
                "Handles requests for alert information.",
                "Spring MVC Rest Controller");

        Component alertWScontroller = apiApplication.addComponent(
                "Alert Websockets Controller",
                "Handles WebSocket requests.",
                "Spring MVC Rest Controller");

        Component alertComponent = apiApplication.addComponent(
                "Alert Component",
                "Provides CRUD for alerts.",
                "Spring Bean");


        alertcontroller.uses(alertComponent, "Uses");
        alertWScontroller.uses(alertComponent, "Uses");
        alertComponent.uses(database, "Reads from and writes to");

        Component ticketController = apiApplication.addComponent(
                "Ticket Controller",
                "Handles requests for ticket information.",
                "Spring MVC Rest Controller");

        Component ticketComponent = apiApplication.addComponent(
                "Ticket Component",
                "Provides CRUD for tickets.",
                "Spring Bean");


        ticketController.uses(ticketComponent, "Uses");
        ticketComponent.uses(database, "Reads from and writes to");

        Component userController = apiApplication.addComponent(
                "User Controller",
                "Handles requests for user information.",
                "Spring MVC Rest Controller");

        Component userComponent = apiApplication.addComponent(
                "User Component",
                "Provides CRUD for users.",
                "Spring Bean");


        userController.uses(userComponent, "Uses");
        userComponent.uses(database, "Reads from and writes to");

        Component busLineController = apiApplication.addComponent(
                "Bus Line Controller",
                "Handles requests for bus information.",
                "Spring MVC Rest Controller");

        Component busLineComponent = apiApplication.addComponent(
                "Bus Line Component",
                "Provides CRUD for bus lines.",
                "Spring Bean");
        busLineController.uses(busLineComponent, "Uses");
        busLineComponent.uses(database, "Reads from and writes to");

        Component statisticsController = apiApplication.addComponent(
                "Statistics Controller",
                "Handles requests for statistics.",
                "Spring MVC REST Controller"
        );

        statisticsController.uses(busLineComponent, "Uses");
        statisticsController.uses(stationComponent, "Uses");


        Component adminController = apiApplication.addComponent(
                "Admin Controller",
                "Handles requests for admins of the system.",
                "Spring MVC Rest Controller");

        adminController.uses(userComponent, "Uses");

        Component loginController = apiApplication.addComponent(
                "Login Controller",
                "Handles user authentication.",
                "Spring MVC Rest Controller");

        Component authenticationComponent = apiApplication.addComponent(
                "Authentication Component",
                "Provides authentication services.",
                "Spring Bean");

        Component accessTokenComponent = apiApplication.addComponent(
                "Access Token Component",
                "Manages access tokens.",
                "Spring Bean");

        loginController.uses(authenticationComponent, "Uses");
        authenticationComponent.uses(accessTokenComponent, "Uses");
        authenticationComponent.uses(userComponent, "Uses");

        Component busLineTicketController = apiApplication.addComponent(
                "Bus Line Ticket Controller",
                "Handles linking of bus lines and tickets.",
                "Spring MVC Rest Controller");

        busLineTicketController.uses(busLineComponent, "Uses");

        Component busLineRouteController = apiApplication.addComponent(
                "Bus Line Route Controller",
                "Handles linking of bus lines and routes.",
                "Spring MVC Rest Controller");
        busLineRouteController.uses(busLineComponent, "Uses");

        Component busLineBusController = apiApplication.addComponent(
                "Bus Line Bus Controller",
                "Handles linking of bus lines and buses.",
                "Spring MVC Rest Controller");
        busLineBusController.uses(busLineComponent, "Uses");


        ComponentView apiAppComponentView = views.createComponentView(
                apiApplication,
                "API application components",
                "");


        apiAppComponentView.add(busController);
        apiAppComponentView.add(busComponent);

        apiAppComponentView.add(busLineController);
        apiAppComponentView.add(busLineRouteController);
        apiAppComponentView.add(busLineBusController);
        apiAppComponentView.add(busLineTicketController);
        apiAppComponentView.add(busLineComponent);

        apiAppComponentView.add(stationController);
        apiAppComponentView.add(stationComponent);

        apiAppComponentView.add(statisticsController);

        apiAppComponentView.add(alertcontroller);
        apiAppComponentView.add(alertWScontroller);
        apiAppComponentView.add(alertComponent);

        apiAppComponentView.add(ticketController);
        apiAppComponentView.add(ticketComponent);

        apiAppComponentView.add(userController);
        apiAppComponentView.add(userComponent);

        apiAppComponentView.add(adminController);

        apiAppComponentView.add(loginController);
        apiAppComponentView.add(authenticationComponent);
        apiAppComponentView.add(accessTokenComponent);

        apiAppComponentView.add(database);


        // Styles
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.CONTAINER).background("#438dd5").color("#ffffff");
        styles.addElementStyle(Tags.COMPONENT).background("#85bbf0").color("#000000");
        styles.addElementStyle(DATABASE_TAG).shape(Shape.Cylinder);
        styles.addElementStyle(WEB_BROWSER_TAG).shape(Shape.WebBrowser);


        return workspace;
    }
}
