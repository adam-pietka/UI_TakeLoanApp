package com.example.application.views.customers;

import java.util.Optional;

import com.example.application.data.dto.CustomerDTO;
import com.example.application.data.entity.Customer;
import com.example.application.data.service.RestClientService;
import com.example.application.data.service.SamplePersonService;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;

@PageTitle("Customers")
@Route(value = "customers/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class CustomersView extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "customers/%d/edit";
    private final TextField filtrByName = new TextField();
    private final TextField filtrBySurname = new TextField();
    private final TextField filtrByPesel = new TextField();
    private final TextField filtrByIdNumber = new TextField();
    final Grid<JsonNode> postsGrid = new Grid<JsonNode>();
    final Button fetchCustomers = new Button("Fetch all customers");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private BeanValidationBinder<Customer> binder;
    private Customer customer;
    private CustomerDTO customerDTO;
    private SamplePersonService samplePersonService;

    public CustomersView(@Autowired RestClientService service) {
        this.samplePersonService = samplePersonService;
        addClassNames("customers-view", "flex", "flex-col", "h-full");
        // Create UI
            // TOP filter area
        HorizontalLayout topFieldsLoyot = new HorizontalLayout();
        topFieldsLoyot.setSpacing(true);
        topFieldsLoyot.setPadding(true);
        createTopArea(topFieldsLoyot);
            // button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        buttonLayout.addClassName("button-layout");

        fetchCustomers.addClickListener( e -> postsGrid.setItems(service.getAllCustomers()));
        fetchCustomers.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(fetchCustomers);
        add(buttonLayout);

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        // Configure Grid
        postsGrid.addColumn(node -> node.get("id")).setHeader("Id").setTextAlign(ColumnTextAlign.END);
        postsGrid.addColumn(node -> node.get("name")).setHeader("customer name");
        postsGrid.addColumn(node -> node.get("surname")).setHeader("Cust surname");
        postsGrid.addColumn(node -> node.get("phoneNumber")).setHeader("Cell phone");
        postsGrid.addColumn(node -> node.get("addressStreet")).setHeader("Street");
        postsGrid.addColumn(node -> node.get("addressNumber")).setHeader("no.");
        postsGrid.addColumn(node -> node.get("addressPostCode")).setHeader("Post code");
        postsGrid.addColumn(node -> node.get("addressCity")).setHeader("City");
        postsGrid.addColumn(node -> node.get("peselNumber")).setHeader("PESEL");
        postsGrid.addColumn(node -> node.get("nipNumber")).setHeader("NIP");
        postsGrid.addColumn(node -> node.get("mailAddress")).setHeader("e-mail");

        postsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        postsGrid.setHeightFull();

        // when a row is selected or deselected, populate form
        postsGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
//                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().get());
            } else {
                clearForm();
                UI.getCurrent().navigate(CustomersView.class);
            }
        });


        // Configure Form

        // Bind fields. This where you'd define e.g. validation rules
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.customer == null) {
                    this.customer = new Customer();
                }
                binder.writeBean(this.customer);

                samplePersonService.update(this.customer);
                clearForm();
                refreshGrid();
                Notification.show("SamplePerson details stored.");
                UI.getCurrent().navigate(CustomersView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> samplePersonId = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (samplePersonId.isPresent()) {
            Optional<Customer> samplePersonFromBackend = samplePersonService.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent()) {
                populateForm(samplePersonFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(CustomersView.class);
            }
        }
    }

    private void createTopArea( HorizontalLayout horizontalLayout){
        filtrByName.setPlaceholder("Filter by name");
        filtrByName.setClearButtonVisible(true);
        filtrByName.setValueChangeMode(ValueChangeMode.EAGER);
//        filtrByName.addValueChangeListener(e -> update());

        filtrBySurname.setPlaceholder("Filter by surname");
        filtrBySurname.setClearButtonVisible(true);
        filtrBySurname.setValueChangeMode(ValueChangeMode.EAGER);

        filtrByPesel.setPlaceholder("Filter by PESEL");
        filtrByPesel.setClearButtonVisible(true);
        filtrByPesel.setValueChangeMode(ValueChangeMode.EAGER);

        filtrByIdNumber.setPlaceholder("Filter by PESEL");
        filtrByIdNumber.setClearButtonVisible(true);
        filtrByIdNumber.setValueChangeMode(ValueChangeMode.EAGER);

        horizontalLayout.add(filtrByName, filtrBySurname, filtrByPesel, filtrByIdNumber);
        add(horizontalLayout);

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("Email");
        TextField phone = new TextField("Phone");
        DatePicker dateOfBirth = new DatePicker("Date Of Birth");
        TextField occupation = new TextField("Occupation");
        Checkbox important = new Checkbox("Important");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{firstName, lastName, email, phone, dateOfBirth, occupation, important};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(postsGrid);
    }

    private void refreshGrid() {
        postsGrid.select(null);
        postsGrid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Customer value) {
        this.customer = value;
        binder.readBean(this.customer);

    }
}
